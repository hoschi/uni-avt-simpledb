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
	
	public AndExpression getExpr() {
		return expr;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("selection(");
		sb.append(expr);
		sb.append(", ");
		sb.append(child);
		sb.append(")");
		return sb.toString();
	}

}
