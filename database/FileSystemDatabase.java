package database;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class FileSystemDatabase {
	private String dbDirectory;
	private Map<String, Table> tables;
	private static FileSystemDatabase instance;

	private FileSystemDatabase() {
		tables = new HashMap<String, Table>();
	}

	public synchronized static FileSystemDatabase getInstance() {
		if (instance == null) {
			instance = new FileSystemDatabase();
		}
		return instance;
	}

	public String getDbDirectory() {
		return dbDirectory;
	}

	public void setDbDirectory(String dbDirectory) throws IOException,
			ClassNotFoundException {
		this.dbDirectory = dbDirectory;
		tables = new HashMap<String, Table>();
		File dir = new File(dbDirectory);

		File[] children = dir.listFiles();
		if (children == null) {
			// Either dir does not exist or is not a directory
			System.err.println("no files");
		} else {
			for (int i = 0; i < children.length; i++) {
				// Get filename of file or directory
				File file = children[i];
				if (!file.isDirectory()) {
					FileSystemDatabase.getInstance().loadTable(file.getName());
				}
			}
		}
	}

	public Table getTable(String name) {
		return this.tables.get(name);
	}

	public void addTable(Table t) throws IOException {
		this.tables.put(t.name, t);
		this.tables.put(t.alias, t);
		t.write();
	}

	public void deleteTable(String name) {
		this.tables.remove(name);
	}

	/**
	 * Loads a Table from dir by its name.
	 * 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public Table loadTable(String name) {
		Table t = null;
		try {
			FileInputStream fis = new FileInputStream(this.dbDirectory
					+ java.io.File.separator + name);
			ObjectInputStream ois = new ObjectInputStream(fis);
			t = (Table) ois.readObject();
			ois.close();
			this.addTable(t);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return t;
	}

	public void printDb() {
		List<Table> printed = new ArrayList<Table>();
		for (Table t : this.tables.values()) {
			if (!printed.contains(t)) {
				printed.add(t);
				System.out.println(t);
			}
		}

	}

}
