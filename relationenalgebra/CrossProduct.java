package relationenalgebra;

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

}
