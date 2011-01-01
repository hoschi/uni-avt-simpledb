package relationenalgebra;

import java.util.Set;

public interface ITreeNode {

    public Set<String> getAttributes();
    public ITreeNode getChild();
    public ITreeNode getSecondChild();

}
