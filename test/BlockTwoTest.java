package test;

import main.Logger;
import main.Main;

import optimization.CascadeSelects;
import optimization.DetectJoins;
import optimization.MoveSelection;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import database.FileSystemDatabase;

import relationenalgebra.CrossProduct;
import relationenalgebra.ITreeNode;
import relationenalgebra.Join;
import relationenalgebra.Projection;
import relationenalgebra.Relation;
import relationenalgebra.Selection;


public class BlockTwoTest {
	public static final String KUNDENDB = "db";
	
	@BeforeClass
	public static void setUpClass() throws Exception {
		Logger.debug = true;
		Logger.debug("DEBUGGING IS ENABLED");
		Logger.debug("load database");
		FileSystemDatabase.getInstance().setDbDirectory(KUNDENDB);
		Main.createKundenDB();
	}
	
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
	
	@Test
	@Ignore
	public void TestMoveSelection() {
		TreeNodeTester test;
		ITreeNode plan = Main.sqlToRelationenAlgebra("select B.Titel " +
				"from Buch as B, Kunde as K, Buch_Bestellung as BB, Kunde_Bestellung as KB " +
				"where K.Name=\"KName1\" and K.ID=KB.K_ID and KB.B_ID=BB.Be_ID and BB.Bu_ID=B.ID");
		
		plan = new CascadeSelects().optimize(plan);
		// after optimization -> there are 4 selections
		plan = new MoveSelection().optimize(plan);
		// after optimization -> move K.Name selection before relation
		
		test = new TreeNodeTester(plan);
		test.nodeIs(Projection.class).followFirst()
		.nodeIs(Selection.class).followFirst()
		.nodeIs(Selection.class).followFirst()
		.nodeIs(Selection.class).firstIs(CrossProduct.class).followFirst()
		.firstIs(Relation.class).secondIs(CrossProduct.class).followSecond();
		
		// left subtree
		test.firstIs(Selection.class).followFirst().firstIs(Relation.class);
		
		// right subtree
		test.secondIs(CrossProduct.class).followSecond()
		.firstIs(Relation.class).secondIs(Relation.class).reset();
	}
	
	@Test
	public void TestDetectJoins() {
		TreeNodeTester test;
		ITreeNode plan = Main.sqlToRelationenAlgebra("select B.ID, K.Name " +
				"from Bestellung as B, Kunde as K, Kunde_Bestellung as KB " +
				"where KB.K_ID=K.ID and KB.B_ID=B.ID and B.ID=\"Bestellung5\"");
		
		plan = new CascadeSelects().optimize(plan);
		plan = new MoveSelection().optimize(plan);
		
		// was
		test = new TreeNodeTester(plan);
		test.nodeIs(Projection.class).followFirst()
		.nodeIs(Selection.class).followFirst()
		.nodeIs(Selection.class).followFirst()
		.nodeIs(Selection.class).firstIs(CrossProduct.class).followFirst()
		.firstIs(Relation.class).secondIs(CrossProduct.class).followSecond()
		.firstIs(Relation.class).secondIs(Relation.class).reset();
		
		plan = new DetectJoins().optimize(plan);
				
		// now merged selection number 3
		test = new TreeNodeTester(plan);
		test.nodeIs(Projection.class).followFirst()
		.nodeIs(Selection.class).followFirst()
		.nodeIs(Selection.class).followFirst()
		.nodeIs(Join.class)
		.firstIs(Relation.class).secondIs(CrossProduct.class).followSecond()
		.firstIs(Relation.class).secondIs(Relation.class).reset();
		
	}

}
