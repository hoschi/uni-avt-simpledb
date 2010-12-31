package relationenalgebra;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AndExpression implements IBooleanExpression {
    private List<OrExpression> exprs;

    public AndExpression(List<OrExpression> exprs) {
        this.exprs = Collections.unmodifiableList(
                new ArrayList<OrExpression>(exprs));
    }

    public AndExpression(OrExpression... exprs) {
        List<OrExpression> list = new ArrayList<OrExpression>(exprs.length);
        for (OrExpression or : exprs) {
            list.add(or);
        }
        this.exprs = Collections.unmodifiableList(list);
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

    public List<OrExpression> getExprs() {
        return exprs;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < exprs.size(); i++) {
            if (i != 0) {
                sb.append(" and ");
            }
            sb.append(exprs.get(i));
        }
        return sb.toString();
    }
}
