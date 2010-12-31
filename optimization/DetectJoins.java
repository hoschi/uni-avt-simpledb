package optimization;

import relationenalgebra.CrossProduct;
import relationenalgebra.IOneChildNode;
import relationenalgebra.ITreeNode;
import relationenalgebra.ITwoChildNode;
import relationenalgebra.Join;
import relationenalgebra.Selection;

public class DetectJoins implements IOptimization {

    @Override
    public ITreeNode optimize(ITreeNode plan) {
        if (plan instanceof Selection) {
            Selection s = (Selection)plan;
            if (s.getChild() instanceof CrossProduct) {
                CrossProduct cp = (CrossProduct)s.getChild();
                Join join = new Join(s.getExpr());
                join.setChild(optimize(cp.getChild()));
                join.setSecondChild(optimize(cp.getSecondChild()));
                return join;
            }
        }
        if (plan instanceof ITwoChildNode) {
            ITwoChildNode plan2 = (ITwoChildNode)plan;
            plan2.setSecondChild(optimize(plan2.getSecondChild()));
        }
        if (plan instanceof IOneChildNode) {
            IOneChildNode plan1 = (IOneChildNode)plan;
            plan1.setChild(optimize(plan1.getChild()));
        }
        return plan;
    }

}
