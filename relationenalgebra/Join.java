package relationenalgebra;

import java.util.Set;

public class Join extends CrossProduct {

    private AndExpression expr;

    public Join(AndExpression expr) {
        this.expr = expr;
    }

    public AndExpression getExpr() {
        return expr;
    }

    @Override
    public Set<String> getAttributes() {
        Set<String> result = super.getAttributes();
        result.addAll(expr.getAttributes());
        return result;
    }
    
    @Override
    public String toString() {
    	return "join(expr("+expr+"), child1("+getChild()+"), child2("+getSecondChild()+"))";
    }

}
