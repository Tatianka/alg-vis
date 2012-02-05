package algvis.reversals;

import algvis.core.DataStructure;
import algvis.core.View;
import algvis.splaytree.SplayNode;

public class ReversalNode extends SplayNode {
	boolean revflag = false;  

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
	
	public void setParent(ReversalNode u) {
		super.setParent(u);
	}

	public void setLeft(ReversalNode u) {
		super.setLeft(u);
	}

	public void setRight(ReversalNode u) {
		super.setRight(u);
	}

	public void changeFlag() {
		if (revflag) {
			revflag = false;
		} else {
			revflag = true;
		}
	}
	
	public void flagDown() {
		revflag = false;
		if (isLeaf()) {
			return;
		}
		// I replace the childnodes
		ReversalNode l = getLeft(), r = getRight();
		setRight(l);
		setLeft(r);
		if (l != null) {
			l.setParent(this);
			l.changeFlag();
		}
		if (r != null) {
			r.setParent(this);
			r.changeFlag();
		}
		reposition();
	}
	
	@Override
	public void draw(View V) {
		if (revflag) {
			marked = true;
		}
		super.draw(V);
	}

}
