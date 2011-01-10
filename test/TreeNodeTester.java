package test;

import relationenalgebra.ITreeNode;
import relationenalgebra.Join;

public class TreeNodeTester {
	private ITreeNode node = null;
	private static int layer = 0;
	
	public TreeNodeTester(ITreeNode node) {
		this.node = node;
		layer++;
	}
	
	public TreeNodeTester nodeIs(Class expected) {
		if (!node.getClass().equals(expected)) {
			throw new RuntimeException("layer " + layer + ": expected "+ expected + 
					" but node is " + node.getClass());
		}
		return this;
	}

	public TreeNodeTester firstIs(Class expected) {
		if (!node.getChild().getClass().equals(expected)) {
			throw new RuntimeException("layer " + layer + ": expected "+ expected + 
					" but first is " + node.getChild().getClass());
		}
		return this;
	}
	
	public TreeNodeTester secondIs(Class expected) {
		if (!node.getSecondChild().getClass().equals(expected)) {
			throw new RuntimeException("layer " + layer + ": expected "+ expected + 
					" but second is " + node.getSecondChild().getClass());
		}
		return this;
	}
	
	public TreeNodeTester followSecond() {
		return new TreeNodeTester(node.getSecondChild());
	}
	
	public TreeNodeTester followFirst() {
		return new TreeNodeTester(node.getChild());
	}
	
	public void reset() {
		layer = 0;
	}

	public ITreeNode getPlan() {
		return this.node;
	}
}
