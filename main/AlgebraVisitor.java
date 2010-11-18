package main;

import java.util.ArrayList;
import java.util.List;

import parser.syntaxtree.CompilationUnit;
import parser.syntaxtree.Item;
import parser.syntaxtree.Items;
import parser.syntaxtree.Name;
import parser.syntaxtree.Query;
import parser.syntaxtree.Table;
import parser.syntaxtree.Tables;
import parser.visitor.ObjectDepthFirst;
import relationenalgebra.ITreeNode;
import relationenalgebra.Projection;
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
		Object _ret = null;
		// collect all table names
		n.f0.accept(this, argu);
		n.f1.accept(this, argu);
		Logger.debug("    return: tables");
		return _ret;
	}

	/**
	 * f0 -> Name() f1 -> [ <AS> Name() ]
	 */
	public Object visit(Table n, Object argu) {
		Object _ret = null;
		n.f0.accept(this, argu);
		n.f1.accept(this, argu);
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
