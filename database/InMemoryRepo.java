package database;

import java.util.HashMap;
import java.util.Map;

public final class InMemoryRepo {
	private String dbDirectory;
	private Map<String, Table> tables;
	private static InMemoryRepo instance;

	private InMemoryRepo() {
		tables = new HashMap<String, Table>();
	}

	public synchronized static InMemoryRepo getInstance() {
		if (instance == null) {
			instance = new InMemoryRepo();
		}
		return instance;
	}

	public String getDbDirectory() {
		return dbDirectory;
	}

	public void setDbDirectory(String dbDirectory) {
		this.dbDirectory = dbDirectory;
	}
	
	public Table getTable(String name){
		return this.tables.get(name);
	}
	
	public void addTable(Table t) {
		this.tables.put(t.name, t);
		this.tables.put(t.alias, t);
	}
	
	public void deleteTable(String name) {
		this.tables.remove(name);
	}

}
