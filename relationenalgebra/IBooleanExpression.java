package relationenalgebra;

import java.util.Set;

public interface IBooleanExpression {

    public Object evaluate(Relation r);

    public Object evaluate(Relation a, Relation b);

    public Set<String> getAttributes();

}
