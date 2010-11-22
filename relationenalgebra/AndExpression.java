package relationenalgebra;

import java.util.List;

public class AndExpression implements IBooleanExpression {
	private OrExpression expr;
	private List<OrExpression> exprs;

	public AndExpression(List<OrExpression> exprs) {
		if (!exprs.isEmpty()) {
			if (exprs.size() == 1) // or
				this.expr = exprs.get(0);
			else // and
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

	public OrExpression getExpr() {
		return expr;
	}

	public List<OrExpression> getExprs() {
		return exprs;
	}
}
