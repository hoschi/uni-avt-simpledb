package main;

import parser.syntaxtree.CompilationUnit;
import parser.syntaxtree.Query;
import parser.visitor.ObjectDepthFirst;
import relationenalgebra.ITreeNode;
import relationenalgebra.Projection;
import relationenalgebra.Selection;

public class AlgebraVisitor extends ObjectDepthFirst {
	   /**
	    * f0 -> Query()
	    *       | Update()
	    *       | Delete()
	    *       | Insert()
	    *       | CreateTable()
	    *       | DropTable()
	    */
	   public Object visit(CompilationUnit n, Object argu) {
	      return n.f0.accept(this, argu);
	   }
	   
	   /**
	    * f0 -> <SELECT>
	    * f1 -> Items()
	    * f2 -> <FROM>
	    * f3 -> Tables()
	    * f4 -> [ Where() ]
	    */
	   public Object visit(Query n, Object argu) {
	      //n.f0.accept(this, argu);
	      String[] columns = (String[]) n.f1.accept(this, argu);
	      //n.f2.accept(this, argu);
	      ITreeNode tables = (ITreeNode) n.f3.accept(this, argu);
	      //ITreeNode child = (ITreeNode) n.f4.accept(this, argu);
	      
	      Selection where = (Selection) n.f4.accept(this, argu);
	      Projection projection = new Projection(columns);
	      
	      if (where != null) {
	    	  projection.setChild(where);
	    	  where.setChild(tables);
	      } else {
	    	  projection.setChild(tables);
	      }
	      
	      return projection;
	   }
}
