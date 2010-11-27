package database;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;

import relationenalgebra.AndExpression;

public class Table implements Serializable {

	static final long serialVersionUID = 1234;
	protected String name;
	protected String alias;
	protected List<String> columnNames;
	protected List<String[]> rows;

	public Table(String name) {
		super();
		this.name = name;
	}

	public Table(String name, List<String> columns) {
		super();
		this.name = name;
		this.columnNames = columns;
	}

	/**
	 * Writes the actual instance to the filesystem.
	 * 
	 * @throws IOException
	 */
	public void write() {
		try {
			FileOutputStream fos = new FileOutputStream(FileSystemDatabase
					.getInstance().getDbDirectory()
					+ java.io.File.separator
					+ this.name);
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
		String s = "table '" + this.name + "'\n\tcolumns: '";
		for (String c : this.columnNames) {
			s += c + ", ";
		}
		s += "'\n\trows:\n";

		if (this.rows != null) {
			for (String[] row : this.rows) {
				s += "\t\t";
				for (String value : row) {
					s += value + "\t";
				}
				s += "\n";
			}
		}
		return s;
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

}
