package relationenalgebra;

import database.FileSystemDatabase;
import database.Table;
import java.util.List;

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

}
