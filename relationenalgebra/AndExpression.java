package relationenalgebra;

import java.util.List;

public class AndExpression implements IBooleanExpression {

	private List<OrExpression> exprs;

	public AndExpression(List<OrExpression> exprs) {
		this.exprs = exprs;
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
	
}
