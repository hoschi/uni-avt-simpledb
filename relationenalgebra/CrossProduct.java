package relationenalgebra;

public class CrossProduct implements ITwoChildNode {
	
	private ITreeNode child1;
	private ITreeNode child2;

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

}
