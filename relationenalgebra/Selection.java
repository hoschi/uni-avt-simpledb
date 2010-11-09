package relationenalgebra;

public class Selection implements IOneChildNode {
	
	private AndExpression expr;
	
	private ITreeNode child;

	public Selection(AndExpression expr) {
		this.expr = expr;
	}

	@Override
	public ITreeNode getChild() {
		return child;
	}

	@Override
	public void setChild(ITreeNode child) {
		this.child = child;
	}

}
