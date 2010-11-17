package relationenalgebra;

public class Projection implements IOneChildNode {
	
	private String[] columnnames;
	
	private ITreeNode child;

	public Projection(String[] columnnames) {
		this.columnnames = columnnames;
	}

	@Override
	public ITreeNode getChild() {
		return child;
	}

	@Override
	public void setChild(ITreeNode child) {
		this.child = child;
	}

	

}
