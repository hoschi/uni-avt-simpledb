package relationenalgebra;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class OrExpression implements IBooleanExpression {
    private List<EqualityExpression> exprs;

    public OrExpression(List<EqualityExpression> exprs) {
        this.exprs = Collections.unmodifiableList(
                new ArrayList<EqualityExpression>(exprs));
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


    public List<EqualityExpression> getExprs() {
        return exprs;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < exprs.size(); i++) {
            if (i != 0) {
                sb.append(" or ");
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
        for (EqualityExpression e : exprs) {
            result.addAll(e.getAttributes());
        }
        return result;
    }

}
