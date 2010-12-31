package relationenalgebra;

import java.util.HashSet;
import java.util.Set;

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

    @Override
    public String toString() {
        return value;
    }

    @Override
    public Set<String> getAttributes() {
        Set<String> result = new HashSet<String>();
        if (!constant) {
            result.add(value);
        }
        return result;
    }

}
