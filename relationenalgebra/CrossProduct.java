package relationenalgebra;

import java.util.HashSet;
import java.util.Set;

public class CrossProduct implements ITwoChildNode {

    private ITreeNode child1;
    private ITreeNode child2;

    public CrossProduct() {
    }

    public CrossProduct(ITreeNode first) {
        this.child1 = first;
    }

    public CrossProduct(ITreeNode first, ITreeNode second) {
        this.child1 = first;
        this.child2 = second;
    }

    @Override
    public ITreeNode getSecondChild() {
        return child2;
    }

    @Override
    public void setSecondChild(ITreeNode child) {
        child2 = child;
    }

    @Override
    public ITreeNode getChild() {
        return child1;
    }

    @Override
    public void setChild(ITreeNode child) {
        child1 = child;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("cross(").append(child1).append(", ")
        .append(child2).append(")");
        return sb.toString();
    }

    @Override
    public Set<String> getAttributes() {
        Set<String> result = new HashSet<String>();
        result.addAll(child1.getAttributes());
        result.addAll(child2.getAttributes());
        return result;
    }

}
