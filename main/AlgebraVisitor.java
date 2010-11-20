package main;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import parser.syntaxtree.CompilationUnit;
import parser.syntaxtree.Item;
import parser.syntaxtree.Items;
import parser.syntaxtree.Name;
import parser.syntaxtree.Query;
import parser.syntaxtree.Table;
import parser.syntaxtree.Tables;
import parser.visitor.ObjectDepthFirst;
import relationenalgebra.CrossProduct;
import relationenalgebra.ITreeNode;
import relationenalgebra.Projection;
import relationenalgebra.Relation;
import relationenalgebra.Selection;

public class AlgebraVisitor extends ObjectDepthFirst {

	/**
	 * f0 -> Query() | Update() | Delete() | Insert() | CreateTable() |
	 * DropTable()
	 */
	public Object visit(CompilationUnit n, Object argu) {
		Logger.debug("call: cu");
		Object _ret = n.f0.accept(this, argu);
		Logger.debug("return: cu");
		return _ret;
	}

	/**
	 * f0 -> <SELECT> f1 -> Items() f2 -> <FROM> f3 -> Tables() f4 -> [ Where()
	 * ]
	 */
	public Object visit(Query n, Object argu) {
		Logger.debug("  call: query");

		// get column names
		List<String> columns = new ArrayList<String>();
		n.f1.accept(this, columns);

		// get tables
		ITreeNode tables = (ITreeNode) n.f3.accept(this, argu);

		// get where ... if one exists
		Selection where = (Selection) n.f4.accept(this, argu);
		Projection projection = new Projection(columns);

		if (where != null) {
			projection.setChild(where);
			where.setChild(tables);
		} else {
			projection.setChild(tables);
		}
		Logger.debug("  return: query");
		return projection;
	}

	/**
	 * f0 -> Table() f1 -> ( "," Table() )*
	 */
	public Object visit(Tables n, Object argu) {
		Logger.debug("    call: tables");
		// collect all table names
		List<String> tables = new ArrayList<String>();
		n.f0.accept(this, tables);
		n.f1.accept(this, tables);

		ITreeNode _ret = null;
		if (tables.isEmpty() == false) {
			if (tables.size() == 1) {
				_ret = new Relation(tables.get(0));
			} else { // min 2 names -> min one cross product
				Iterator<String> iter = tables.iterator();
				CrossProduct root = new CrossProduct(new Relation(iter.next()));
				CrossProduct current = root;
				while (iter.hasNext()) {
					String name = iter.next();
					if (iter.hasNext()) {
						// node
						CrossProduct node = new CrossProduct(new Relation(name));
						current.setSecondChild(node);
						current = node;
					} else {
						// leaf
						current.setSecondChild(new Relation(name));
					}
				}
				_ret = root;
			}
		}

		Logger.debug("    return: tables");
		return _ret;
	}

	/**
	 * f0 -> Name() f1 -> [ <AS> Name() ]
	 */
	public Object visit(Table n, Object argu) {
		Logger.debug("      call: table");
		Object _ret = null;
		n.f0.accept(this, argu);
		n.f1.accept(this, argu);
		Logger.debug("      call: table");
		return _ret;
	}

	/**
	 * f0 -> Item() f1 -> ( "," Item() )* WICHTIG --->>> das braucht man nur
	 * wenn man am comma interesiert ist
	 */
	public Object visit(Items n, Object argu) {
		Logger.debug("    call: items");
		Object _ret = null;
		_ret = n.f0.accept(this, argu);
		n.f1.accept(this, argu);
		Logger.debug("    return: items");
		return _ret;
	}

	/**
	 * f0 -> Name() f1 -> [ "." Name() ]
	 */
	public Object visit(Item n, Object argu) {
		Logger.debug("      call: item");
		Object _ret = null;
		_ret = n.f0.accept(this, argu);
		n.f1.accept(this, argu);
		Logger.debug("      return: item");
		return _ret;
	}

	/**
	 * f0 -> <IDENTIFIER>
	 */
	public Object visit(Name n, Object argu) {
		Logger.debug("        call: name");
		Object _ret = null;
		_ret = n.f0.accept(this, argu);
		((List<String>) argu).add(n.f0.toString());
		_ret = n.f0.toString();
		Logger.debug("        return: name");
		return _ret;
	}
}
