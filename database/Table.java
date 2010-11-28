package database;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import relationenalgebra.AndExpression;

public class Table implements Serializable {

	static final long serialVersionUID = 1234;
	protected String name;
	protected String alias;
	protected boolean drop;
	protected List<String> columnNames;
	protected List<List<String>> rows;

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
	 */
	public void write() {
		FileSystemDatabase.getInstance().write(this);
	}

	/**
	 * Returns one row from privat row repository.
	 */
	public List<String> getRow(int number) {
		return Collections.unmodifiableList(rows.get(number));
	}

	public void addRow(List<String> names) {
		if (names.size() != columnNames.size()) {
			throw new IllegalArgumentException("bad row size");
		}
		rows.add(cloneList(names));
	}

	private static <T> List<T> cloneList(List<T> list) {
		List<T> copy = new ArrayList<T>(list.size());
		Collections.copy(copy, list);
		return copy;
	}

	public void deleteRow(int number) {
		rows.remove(number);
	}

	private int getColumnIndex(String name) {
		for (int i = 0; i < columnNames.size(); i++) {
			if (columnNames.get(i).equals(name)) {
				return i;
			}
		}
		return -1;
	}

	private static List<List<String>> newRowsList() {
		return new LinkedList<List<String>>();
	}

	public Table projectTo(List<String> param) {
		// compute indices for projection
		int[] projection = new int[param.size()];
		for (int i = 0; i < projection.length; i++) {
			int index = getColumnIndex(param.get(i));
			if (index < 0) {
				throw new IllegalArgumentException("no column with name '"
						+ param.get(i) + "'");
			}
			projection[i] = index;
		}

		// generate column names list
		List<String> newColumnNames = new ArrayList<String>(projection.length);
		for (int i = 0; i < projection.length; i++) {
			newColumnNames.add(columnNames.get(projection[i]));
		}

		// generate new rows list
		List<List<String>> newRows = newRowsList();
		for (List<String> row : rows) {
			List<String> projectedRow = new ArrayList<String>(projection.length);
			for (int i = 0; i < projection.length; i++) {
				projectedRow.add(row.get(projection[i]));
			}
		}

		return createAlteredClone(newColumnNames, newRows);
	}

	private Table createAlteredClone(List<String> newColumnNames,
			List<List<String>> newRows) {
		Table result = new Table(this.name);
		result.alias = this.alias;
		result.columnNames = newColumnNames;
		result.drop = false;
		result.rows = newRows;
		return result;
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
			for (List<String> row : this.rows) {
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
