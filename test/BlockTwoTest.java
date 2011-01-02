package test;

import main.Main;

import optimization.CascadeSelects;

import org.junit.Test;

import relationenalgebra.CrossProduct;
import relationenalgebra.ITreeNode;
import relationenalgebra.Projection;
import relationenalgebra.Relation;
import relationenalgebra.Selection;


public class BlockTwoTest {
	
	@Test
	public void TestEasy() {
		TreeNodeTester test;
		ITreeNode plan = Main.sqlToRelationenAlgebra("select B.Titel " +
				"from Buch as B " +
				"where B.Titel=\"blubb\"");
		
		test = new TreeNodeTester(plan);
		test.nodeIs(Projection.class).followFirst()
		.nodeIs(Selection.class).followFirst()
		.nodeIs(Relation.class).reset();
	}
	
	@Test
	public void TestCascadeSelectionWithOrExpression() {
		TreeNodeTester test;
		ITreeNode plan = Main.sqlToRelationenAlgebra("select B.Titel " +
				"from Buch as B, Kunde as K, Buch_Bestellung as BB, Kunde_Bestellung as KB " +
				"where K.Name=\"KName1\" or K.ID=KB.K_ID");
		
		test = new TreeNodeTester(plan);
		test.nodeIs(Projection.class).followFirst()
		.nodeIs(Selection.class).firstIs(CrossProduct.class).followFirst()
		.firstIs(Relation.class).secondIs(CrossProduct.class).followSecond()
		.firstIs(Relation.class).secondIs(CrossProduct.class).followSecond()
		.firstIs(Relation.class).secondIs(Relation.class).reset();
		
		plan = new CascadeSelects().optimize(plan);
		// should be the same because cascading works only with and expressions
		test = new TreeNodeTester(plan);
		test.nodeIs(Projection.class).followFirst()
		.nodeIs(Selection.class).firstIs(CrossProduct.class).followFirst()
		.firstIs(Relation.class).secondIs(CrossProduct.class).followSecond()
		.firstIs(Relation.class).secondIs(CrossProduct.class).followSecond()
		.firstIs(Relation.class).secondIs(Relation.class).reset();
	}
	
	@Test
	public void TestCascadeSelectionWithAndExpression() {
		TreeNodeTester test;
		ITreeNode plan = Main.sqlToRelationenAlgebra("select B.Titel " +
				"from Buch as B, Kunde as K, Buch_Bestellung as BB, Kunde_Bestellung as KB " +
				"where K.Name=\"KName1\" and K.ID=KB.K_ID and KB.B_ID=BB.Be_ID and BB.Bu_ID=B.ID");
		
		test = new TreeNodeTester(plan);
		test.nodeIs(Projection.class).followFirst()
		.nodeIs(Selection.class).firstIs(CrossProduct.class).followFirst()
		.firstIs(Relation.class).secondIs(CrossProduct.class).followSecond()
		.firstIs(Relation.class).secondIs(CrossProduct.class).followSecond()
		.firstIs(Relation.class).secondIs(Relation.class).reset();
		
		plan = new CascadeSelects().optimize(plan);
		test = new TreeNodeTester(plan);
		test.nodeIs(Projection.class).followFirst()
		// after optimization -> there are 4 selections
		.nodeIs(Selection.class).followFirst()
		.nodeIs(Selection.class).followFirst()
		.nodeIs(Selection.class).followFirst()
		.nodeIs(Selection.class).firstIs(CrossProduct.class).followFirst()
		.firstIs(Relation.class).secondIs(CrossProduct.class).followSecond()
		.firstIs(Relation.class).secondIs(CrossProduct.class).followSecond()
		.firstIs(Relation.class).secondIs(Relation.class).reset();
	}

}
