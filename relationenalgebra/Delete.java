package relationenalgebra;

import java.util.Set;

import database.Table;

public class Delete extends TableOperation {

    protected AndExpression where;

    @Override
    public void execute(Table table) {
        // TODO Auto-generated method stub

    }

    @Override
    public Table execute() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Set<String> getAttributes() {
        return where.getAttributes();
    }

}
