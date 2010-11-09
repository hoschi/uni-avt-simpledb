package relationenalgebra;

public class EqualityExpression implements IBooleanExpression {
	
	public enum Operator {
		Equal("="),
		NotEqual("!="),
		Greater(">"),
		Lower("<"),
		LowerEqual("<="),
		GreaterEqual(">="),
		;
		
		private String opString;
		
		Operator(String opString) {
			this.opString = opString;
		}
		
		public static Operator parseOperator(String str) {
			for (Operator op : Operator.values()) {
				if (op.opString.equals(str)) {
					return op;
				}
			}
			throw new IllegalArgumentException("no Operator matching the string '"+str+"' found");
		}
	}
	
	private PrimaryExpression expr1, expr2;
	
	private Operator operator;

	public EqualityExpression(
			Operator operator, 
			PrimaryExpression expr1,
			PrimaryExpression expr2) {
		this.operator = operator;
		this.expr1 = expr1;
		this.expr2 = expr2;
	}

	@Override
	public Object evaluate(Relation a, Relation b) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object evaluate(Relation r) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
