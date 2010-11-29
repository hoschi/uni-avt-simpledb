package relationenalgebra;

public class Join extends CrossProduct {
	
	private AndExpression expr;

	public Join(AndExpression expr) {
		this.expr = expr;
	}
	
	public AndExpression getExpr() {
		return expr;
	}

}
