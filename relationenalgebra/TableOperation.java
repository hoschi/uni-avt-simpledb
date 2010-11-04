package relationenalgebra;

import database.Table;

public abstract class TableOperation implements ITreeNode {
	protected String name;
	public abstract void execute(Table table);
	public abstract Table execute();

}
