//
// Generated by JTB 1.2.2
//

package parser.syntaxtree;

/**
 * Grammar production:
 * f0 -> Query()
 *       | Update()
 *       | Delete()
 *       | Insert()
 *       | CreateTable()
 *       | DropTable()
 */
public class CompilationUnit implements Node {
   private Node parent;
   public NodeChoice f0;

   public CompilationUnit(NodeChoice n0) {
      f0 = n0;
      if ( f0 != null ) f0.setParent(this);
   }

   public void accept(parser.visitor.Visitor v) {
      v.visit(this);
   }
   public Object accept(parser.visitor.ObjectVisitor v, Object argu) {
      return v.visit(this,argu);
   }
   public void setParent(Node n) { parent = n; }
   public Node getParent()       { return parent; }
}

