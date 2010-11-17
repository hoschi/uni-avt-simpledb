package relationenalgebra;

public interface IOneChildNode extends ITreeNode {
	
	public ITreeNode getChild();
	
	public void setChild(ITreeNode child);

}
