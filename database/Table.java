package database;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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
