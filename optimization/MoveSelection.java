package optimization;

import java.util.Set;

import relationenalgebra.CrossProduct;
import relationenalgebra.IOneChildNode;
import relationenalgebra.ITreeNode;
import relationenalgebra.ITwoChildNode;
import relationenalgebra.Projection;
import relationenalgebra.Selection;

public class MoveSelection implements IOptimization {

    @Override
    public ITreeNode optimize(ITreeNode plan) {
        if (!(plan instanceof Selection)) {
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
        Selection s = (Selection)plan;
        s.setChild(optimize(s.getChild()));

        return moveSelection(s);
    }


    private static ITreeNode moveSelection(Selection s) {
        ITreeNode child = null;
        ITreeNode result = s;
        ITreeNode parent = null;
        int switchWithChild = 0;

        do {
            switchWithChild = 0;
            child = s.getChild();

            // check if we can move the selection
            // 1 for first child, 2 for second child, 0 => don't move
            if (child instanceof Selection) {
                // selections can always be switched
                switchWithChild = 1;
            } else if (child instanceof Projection) {
                // check if the selection contains only attributes
                // from the projection
                Projection p = (Projection)child;
                Set<String> selectionAttributes = s.getExpr().getAttributes();
                Set<String> projectionAttributes = p.getAttributes();
                if (projectionAttributes.containsAll(selectionAttributes)) {
                    switchWithChild = 1;
                }
            } else if (child instanceof CrossProduct) { // or join
                CrossProduct cp = (CrossProduct)child;
                // check if one child contains all the attributes from the selection
                Set<String> selectionAttributes = s.getExpr().getAttributes();
                Set<String> childAttributes = cp.getChild().getAttributes();
                if (childAttributes.containsAll(selectionAttributes)) {
                    switchWithChild = 1;
                } else {
                	childAttributes = cp.getSecondChild().getAttributes();
                	if (childAttributes.containsAll(selectionAttributes)) {
                        switchWithChild = 2;
                    }
                }
            } else {
                switchWithChild = 0;
            }

            // move down, update parent
            if (switchWithChild > 0) {
                replaceChild(parent, s, child); // replace s with child in parent
                //parent = child;                 // new parent
            }

            // move down, update child (parent-to-be)
            switch (switchWithChild) {
	            case 1: {
	                s.setChild(child.getChild());
	                IOneChildNode c1 = (IOneChildNode)child;
	                c1.setChild(s);
	                parent = child;
	            } break;
	            case 2: {
	                replaceChild(parent, s, child);
	                ITwoChildNode c2 = (ITwoChildNode)child;
	                s.setChild(c2.getSecondChild());
	                c2.setSecondChild(s);
	                parent = child;
	            } break;
            }

            // after first swap -> set old child as return value
            // this child can't go more upwards in the tree!
            if (result == s && switchWithChild > 0) {
                result = child;
            }
        } while (switchWithChild > 0);

        return result;
    }

    /**
     * replaces oldChild with newChild in parent
     *
     * @param parent
     * @param oldChild
     * @param newChild
     */
    private static void replaceChild(
            ITreeNode parent, ITreeNode oldChild, ITreeNode newChild) {
        if (parent != null) {
            if (parent instanceof ITwoChildNode) {
                ITwoChildNode parent2 = (ITwoChildNode)parent;
                if (parent2.getSecondChild() == oldChild) {
                    parent2.setSecondChild(newChild);
                }
            }
            if (parent instanceof IOneChildNode) {
                IOneChildNode parent1 = (IOneChildNode)parent;
                if (parent1.getChild() == oldChild) {
                    parent1.setChild(newChild);
                }
            }
        }
    }

}
