package algvis.reversals;

import algvis.core.DataStructure;
import algvis.splaytree.SplayNode;

public class ReversalNode extends SplayNode {

	public ReversalNode(DataStructure D, int key) {
		super(D, key);
	}
	
	public ReversalNode(DataStructure D, int key, int x, int y) {
		super(D, key, x, y);
	}

	@Override
	public ReversalNode getLeft() {
		return (ReversalNode) super.getLeft();
	}

	@Override
	public ReversalNode getRight() {
		return (ReversalNode) super.getRight();
	}

	@Override
	public ReversalNode getParent() {
		return (ReversalNode) super.getParent();
	}

	

}
