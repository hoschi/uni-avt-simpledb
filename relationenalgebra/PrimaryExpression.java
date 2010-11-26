package relationenalgebra;

public class PrimaryExpression implements IBooleanExpression {
	
	private boolean constant;
	
	private String value;
	
	public PrimaryExpression(boolean constant, String value) {
		this.constant = constant;
		this.value = value;
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

	public boolean isConstant() {
		return constant;
	}

	public String getValue() {
		return value;
	}

}
