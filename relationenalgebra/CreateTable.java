package relationenalgebra;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import database.FileSystemDatabase;
import database.Table;

public class CreateTable extends TableOperation {

    protected List<String> columnNames;

    public CreateTable(String name, List<String> columnNames) {
        super(name);
        this.columnNames = columnNames;
    }

    @Override
    public void execute(Table table) {
    }

    @Override
    public Table execute() {
        Table t = new database.Table(this.name, this.columnNames);
        FileSystemDatabase.getInstance().addTable(t);
        return null;
    }

    @Override
    public Set<String> getAttributes() {
        Set<String> result = new HashSet<String>(columnNames);
        return result;
    }

}
