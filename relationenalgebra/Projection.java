package relationenalgebra;

import java.util.List;

public class Projection implements IOneChildNode {
	
	private List<String> columnnames;
	
	private ITreeNode child;

	public Projection(List<String> columns) {
		this.columnnames = columns;
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
