package algvis.reversals;

import algvis.core.DataStructure;
import algvis.scenario.commands.bstnode.LinkLeftCommand;
import algvis.scenario.commands.bstnode.LinkRightCommand;
import algvis.splaytree.SplayNode;

public class ReversalNode extends SplayNode {
	int snum; //bigness of subtrees
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

	public void setNum() {
		snum = 1;
		if (getLeft() != null) {
			getLeft().setNum();
			snum += getLeft().snum;
		}
		if (getRight() != null) {
			getRight().setNum();
			snum += getRight().snum;
		}
	}
	
	//////////////////////////////////////////////////////////////
	public void linkLeft(ReversalNode newLeft) {
		if (getLeft() != newLeft) {
			if (getLeft() != null) {
				// remove edge between this and left
				unlinkLeft();
			}
			if (newLeft != null) {
				if (newLeft.getParent() != null) {
					// remove edge between newLeft and its parent
					newLeft.unlinkParent();
				}
				// create new edge between this and newLeft
				newLeft.setParent(this);
			}
			setLeft(newLeft);
			D.scenario.add(new LinkLeftCommand(this, newLeft, true));
		}
	}

	/**
	 * removes edge between this and left
	 */
	public void unlinkLeft() {
		getLeft().setParent(null);
		D.scenario.add(new LinkLeftCommand(this, getLeft(), false));
		setLeft(null);
	}

	/**
	 * removes edge between this and right; removes edge between newRight and
	 * its parent; creates new edge between this and newRight
	 */
	public void linkRight(ReversalNode newRight) {
		if (getRight() != newRight) {
			if (getRight() != null) {
				// remove edge between this and right
				unlinkRight();
			}
			if (newRight != null) {
				if (newRight.getParent() != null) {
					// remove edge between newRight and its parent
					newRight.unlinkParent();
				}
				// create new edge between this and newRight
				newRight.setParent(this);
			}
			setRight(newRight);
			D.scenario.add(new LinkRightCommand(this, newRight, true));
		}
	}

	/**
	 * removes edge between this and right
	 */
	public void unlinkRight() {
		getRight().setParent(null);
		D.scenario.add(new LinkRightCommand(this, getRight(), false));
		setRight(null);
	}
	
	private void unlinkParent() {
		if (isLeft()) {
			getParent().unlinkLeft();
		} else {
			getParent().unlinkRight();
		}
	}


}
