package optimization;

import relationenalgebra.IOneChildNode;
import relationenalgebra.ITreeNode;
import relationenalgebra.ITwoChildNode;
import relationenalgebra.Projection;

public class MoveProjection implements IOptimization {

    @Override
    public ITreeNode optimize(ITreeNode plan) {
        if (!(plan instanceof Projection)) {
            if (plan instanceof ITwoChildNode) {
                ((ITwoChildNode) plan).setSecondChild(
                        optimize(((ITwoChildNode) plan).getSecondChild()));
            }
            if (plan instanceof IOneChildNode) {
                ((IOneChildNode) plan).setChild(
                        optimize(((IOneChildNode) plan).getChild()));
            }
            return plan;
        }
        Projection p = (Projection)plan;
        p.setChild(optimize(p.getChild()));

        return moveProjection(p);
    }

    private static ITreeNode moveProjection(Projection p) {
        return null;
    }

}
