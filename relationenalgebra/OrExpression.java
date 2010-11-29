package relationenalgebra;

import java.util.List;

public class OrExpression implements IBooleanExpression {
	private EqualityExpression expr;
	private List<EqualityExpression> exprs;
	
	public OrExpression(List<EqualityExpression> exprs) {
		if (!exprs.isEmpty()) {
			if (exprs.size() == 1) // and
				this.expr = exprs.get(0);
			else // or
				this.exprs = exprs;
		}
	}

	@Override
	public Object evaluate(Relation r) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object evaluate(Relation a, Relation b) {
		// TODO Auto-generated method stub
		return null;
	}

	public EqualityExpression getExpr() {
		return expr;
	}

	public List<EqualityExpression> getExprs() {
		return exprs;
	}
	
	@Override
	public String toString() {
		if (exprs == null)
			return expr.toString();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < exprs.size(); i++) {
			if (i != 0) {
				sb.append(" or ");
			}
			sb.append(exprs.get(i));
		}
		return sb.toString();
	}

}
