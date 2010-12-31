package relationenalgebra;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Projection implements IOneChildNode {

    private List<String> columnnames;

    private ITreeNode child;

    public Projection(List<String> columns) {
        this.columnnames = columns;
    }

    @Override
    public ITreeNode getChild() {
        return child;
    }

    @Override
    public void setChild(ITreeNode child) {
        this.child = child;
    }

    public List<String> getColumnnames() {
        return columnnames;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("projection(");
        sb.append(columnnames);
        sb.append(", ");
        sb.append(child);
        sb.append(")");
        return sb.toString();
    }

    @Override
    public Set<String> getAttributes() {
        Set<String> result = new HashSet<String>(columnnames);
        result.addAll(child.getAttributes());
        return result;
    }

}
