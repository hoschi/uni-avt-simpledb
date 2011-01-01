package relationenalgebra;

import database.Table;

public abstract class TableOperation implements ITreeNode {
	protected String name;
	public TableOperation(String name) {
		this.name = name;
	}
	public TableOperation() {
	}
	public abstract void execute(Table table);
	public abstract Table execute();
	
	public ITreeNode getChild() {
        return null;
    }
	
	public ITreeNode getSecondChild() {
		return null;
	}

}
