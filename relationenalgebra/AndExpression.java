package relationenalgebra;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @Override
    public Set<String> getAttributes() {
        if (exprs.size() == 1) {
            return exprs.get(0).getAttributes();
        }
        Set<String> result = new HashSet<String>();
        for (OrExpression or : exprs) {
            result.addAll(or.getAttributes());
        }
        return result;
    }
}
