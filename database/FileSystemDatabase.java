package database;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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

	public void setDbDirectory(String dbDirectory) {
		this.dbDirectory = dbDirectory;
		tables = new HashMap<String, Table>();
		File dir = new File(dbDirectory);

		if (!dir.isDirectory()) {
			if (dir.isFile())
				System.err.println(dbDirectory + "is a file, not a dir");
			dir.mkdirs();
		}

		File[] children = dir.listFiles();
		if (children == null) {
			// Either dir does not exist or is not a directory
			System.err.println("no files");
		} else {
			for (int i = 0; i < children.length; i++) {
				// Get filename of file or directory
				File file = children[i];
				if (!file.isDirectory()) {
					this.loadTable(file.getName());
				}
			}
		}
	}

	public Table getTable(String name) {
		return this.tables.get(name);
	}

	public void addTable(Table t) {
		this.tables.put(t.name, t);
		this.tables.put(t.alias, t);
		t.write();
	}

	public void deleteTable(String name) {
		this.tables.remove(name);
	}

	/**
	 * Loads a Table from dir by its name.
	 */
	public Table loadTable(String name) {
		try {
			ObjectInputStream stream = new ObjectInputStream(
					new FileInputStream(this.dbDirectory + java.io.File.separator + name));
			Table t = (Table) stream.readObject();
			stream.close();
			this.addTable(t);
			return t;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Writes the actual instance to the filesystem.
	 */
	public void write(Table t) {
		try {
			ObjectOutputStream stream = new ObjectOutputStream(
					new FileOutputStream(this.dbDirectory
							+ java.io.File.separator + t.getName()));
			stream.writeObject(t);
			stream.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void printDb() {
		List<Table> printed = new ArrayList<Table>();
		System.out.println("\n\n---> this is the database <---");
		for (Table t : this.tables.values()) {
			if (!printed.contains(t)) {
				printed.add(t);
				System.out.println(t);
			}
		}

	}

}
