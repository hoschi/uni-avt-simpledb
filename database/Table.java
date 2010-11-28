package database;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import relationenalgebra.AndExpression;
import relationenalgebra.EqualityExpression;
import relationenalgebra.OrExpression;
import relationenalgebra.PrimaryExpression;

public class Table implements Serializable {

    private static class ValueLookup {
        private Map<String,Integer> indexMap;
        private Table[] tables;
        private int[] currentRowIndices;

        ValueLookup(Table...tables) {
            indexMap = new HashMap<String,Integer>();
            this.tables = tables;
            currentRowIndices = new int[tables.length];

            for (int i = 0; i < tables.length; i++) {
                indexMap.put(tables[i].getOfficialName(), Integer.valueOf(i));
            }
        }

        void setCurrentRowIndex(Table table, int rowIndex) {
            currentRowIndices[indexMap.get(table.getOfficialName())] = rowIndex;
        }

        void setCurrentRowIndex(int tableIndex, int rowIndex) {
            currentRowIndices[tableIndex] = rowIndex;
        }

        String lookupValue(String name) {
            int i = name.indexOf(".");
            String tableName = name.substring(0, i);
            String columnName = name.substring(i+1, name.length());
            Integer tableIndex = indexMap.get(tableName);

            if (tableIndex == null) {
                throw new IllegalArgumentException(
                        "table '"+tableName+"' not found");
            }

            Table table = tables[tableIndex];

            return table.rows.get(currentRowIndices[tableIndex]).get(
                    table.getColumnIndex(columnName));
        }
    }

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
        List<List<String>> newRows = newRowsList();
        ValueLookup vl = new ValueLookup(this);

        for (int i = 0; i < rows.size(); i++) {
            vl.setCurrentRowIndex(this, i);
            if (evaluate(exp, vl)) {
                newRows.add(cloneList(rows.get(i)));
            }
        }

        return createAlteredClone(null, newRows);
    }

    private boolean evaluate(AndExpression expr, ValueLookup vl) {
        if (expr.getExprs() == null) {
            return evaluate(expr.getExpr(), vl);
        }
        for (OrExpression or : expr.getExprs()) {
            if (!evaluate(or, vl)) {
                return false;
            }
        }
        return true;
    }

    private boolean evaluate(OrExpression expr, ValueLookup vl) {
        if (expr.getExprs() == null) {
            return evaluate(expr.getExpr(), vl);
        }
        for (EqualityExpression eq : expr.getExprs()) {
            if (evaluate(eq, vl)) {
                return true;
            }
        }
        return false;
    }

    private boolean evaluate(EqualityExpression expr, ValueLookup vl) {
        String valueA = retrieveValue(expr.getExpr1(), vl);
        String valueB = retrieveValue(expr.getExpr1(), vl);
        int cmp = valueA.compareTo(valueB);
        switch (expr.getOperator()) {
        case Equal:
            return cmp == 0;
        case Greater:
            return cmp > 0;
        case GreaterEqual:
            return cmp >= 0;
        case Lower:
            return cmp < 0;
        case LowerEqual:
            return cmp <= 0;
        case NotEqual:
            return cmp != 0;
        default:
            throw new RuntimeException("unknown operator: "+expr.getOperator());
        }
    }

    private String getOfficialName() {
        if (alias != null) {
            return alias;
        }
        return name;
    }

    private String retrieveValue(PrimaryExpression expr, ValueLookup vl) {
        if (expr.isConstant()) {
            return expr.getValue();
        }
        return vl.lookupValue(expr.getValue());
    }

    public Table join(Table table, AndExpression exp) {
        List<List<String>> newRows = newRowsList();
        ValueLookup vl = new ValueLookup(this, table);
        int newRowSize = columnNames.size() + table.columnNames.size();

        List<String> newColumnNames = new ArrayList<String>(newRowSize);
        newColumnNames.addAll(columnNames);
        newColumnNames.addAll(table.columnNames);

        for (int i = 0; i < rows.size(); i++) {
            vl.setCurrentRowIndex(0, i);
            for (int j = 0; j < table.rows.size(); j++) {
                vl.setCurrentRowIndex(1, j);
                List<String> newRow = new ArrayList<String>(newRowSize);
                newRow.addAll(rows.get(i));
                newRow.addAll(table.rows.get(j));

                if (exp == null || evaluate(exp, vl)) {
                    newRows.add(newRow);
                }
            }
        }

        return createAlteredClone(newColumnNames, newRows);
    }

    public Table cross(Table table) {
        return join(table, null);
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
