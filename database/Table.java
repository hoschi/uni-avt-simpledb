package database;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;

import relationenalgebra.AndExpression;

public class Table implements Serializable {

	static final long serialVersionUID = 1234;
	protected static String databaseDirectory;
	protected String name;
	protected String alias;
	protected boolean drop;
	protected List<String> columnNames;
	protected List<List<String>> rows;

	/**
	 * Writes the actual instance to the filesystem.
	 * @throws IOException 
	 */
	public void write() {
		try {
			FileOutputStream fos = new FileOutputStream(FileSystemDatabase.getInstance()
					.getDbDirectory() + java.io.File.separator + this.name);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(this);
			oos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Returns one row from privat row repository.
	 */
	public List<String> getRow(int number) {
		// TODO implement this
		return null;
	}

	public void addRow(List<String> names) {
		// TODO implement this
	}

	public void deleteRow(int number) {
		// TODO implement this
	}

	public Table projectTo(List<String> param) {
		// TODO implement this
		return null;
	}

	public Table select(AndExpression exp) {
		// TODO implement this
		return null;
	}

	public Table join(Table table, AndExpression exp) {
		// TODO implement this
		return null;
	}

	public Table cross(Table table) {
		// TODO implement this
		return null;
	}

	public String toString() {
		// TODO implement this
		String s = "table '" + this.name + "'\ncolumns '";
		for (String c : this.columnNames) {
			s += c + "\t";
		}
		s += "'\nrows\n";

		for (List<String> row : this.rows) {
			for (String value : row) {
				s += value + "\t";
			}
			s += "\n";
		}
		return s;
	}

}
