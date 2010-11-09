package relationenalgebra;

public interface IBooleanExpression {
	
	public Object evaluate(Relation r);
	
	public Object evaluate(Relation a, Relation b);

}
