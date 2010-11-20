package relationenalgebra;

public class Relation implements ITreeNode {
	
	private String name;
	
	private String alias;
	
	private String[] tuple;
	
	private String[] tuplenames;
	
	public Relation(String name) {
		this.name = name;
	}

	public Relation(String name, String alias) {
		this.name = name;
		this.alias = alias;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String[] getTuple() {
		return tuple;
	}

	public void setTuple(String[] tuple) {
		this.tuple = tuple;
	}

	public String[] getTuplenames() {
		return tuplenames;
	}

	public void setTuplenames(String[] tuplenames) {
		this.tuplenames = tuplenames;
	}

}
