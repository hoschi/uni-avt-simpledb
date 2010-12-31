package relationenalgebra;

import java.util.HashSet;
import java.util.Set;

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

    public PrimaryExpression getExpr1() {
        return expr1;
    }

    public PrimaryExpression getExpr2() {
        return expr2;
    }

    public Operator getOperator() {
        return operator;
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

    @Override
    public String toString() {
        String s = "";
        if (expr1 != null && expr1.getValue() != null)
            s += expr1.getValue();
        if (this.operator != null)
            s += " " + this.operator + " ";
        if (expr2 != null && expr2.getValue() != null)
            s += expr2.getValue();
        return s;
    }

    @Override
    public Set<String> getAttributes() {
        Set<String> result = new HashSet<String>();
        result.addAll(expr1.getAttributes());
        result.addAll(expr2.getAttributes());
        return result;
    }

}
