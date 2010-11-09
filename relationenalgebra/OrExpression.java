package relationenalgebra;

import java.util.List;

public class OrExpression implements IBooleanExpression {
	
	private List<EqualityExpression> exprs;
	
	public OrExpression(List<EqualityExpression> exprs) {
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
