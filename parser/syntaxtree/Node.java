//
// Generated by JTB 1.2.2
//

package parser.syntaxtree;

/**
 * The interface which all syntax tree classes must implement.
 */
public interface Node extends java.io.Serializable {
   public void accept(parser.visitor.Visitor v);
   public Object accept(parser.visitor.ObjectVisitor v, Object argu);

   // It is the responsibility of each implementing class to call
   // setParent() on each of its child Nodes.
   public void setParent(Node n);
   public Node getParent();
}

