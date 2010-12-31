package relationenalgebra;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import database.FileSystemDatabase;
import database.Table;

public class Insert extends TableOperation {
    protected String name;
    protected List<String> columnNames;
    protected List<String> values;

    public Insert(String name, List<String> columns, List<String> values) {
        this.name = name;
        this.columnNames = columns;
        this.values = values;
    }

    @Override
    public void execute(Table table) {
    }

    @Override
    public Table execute() {
        Table t = FileSystemDatabase.getInstance().getTable(this.name);
        t.addRow(this.values);

        return null;
    }

    @Override
    public Set<String> getAttributes() {
        return new HashSet<String>(columnNames);
    }

}
