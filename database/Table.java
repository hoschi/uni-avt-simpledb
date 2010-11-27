package database;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
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

    private static File tableNameToFile(String name) {
        File dir = new File(databaseDirectory);
        String extension = ".tbl";
        return new File(dir, name + extension);
    }

    /**
     * Loads a Table from dir by its name.
     */
    public static Table loadTable(String name) {
        try {
            ObjectInputStream stream = new ObjectInputStream(
                    new FileInputStream(tableNameToFile(name)));
            Object obj = stream.readObject();
            stream.close();
            return (Table)obj;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *  Writes the actual instance to the filesystem.
     */
    public void write() {
        try {
            ObjectOutputStream stream = new ObjectOutputStream(
                    new FileOutputStream(tableNameToFile(name)));
            stream.writeObject(this);
            stream.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *  Returns one row from privat row repository.
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
                throw new IllegalArgumentException(
                        "no column with name '"+param.get(i)+"'");
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
            List<String> projectedRow =
                new ArrayList<String>(projection.length);
            for (int i = 0; i < projection.length; i++) {
                projectedRow.add(row.get(projection[i]));
            }
        }

        return createAlteredClone(newColumnNames, newRows);
    }

    private Table createAlteredClone(
            List<String> newColumnNames,
            List<List<String>> newRows) {
        Table result = new Table();
        result.alias = this.alias;
        result.columnNames = newColumnNames;
        result.drop = false;
        result.name = this.name;
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
