package algvis.reversals;

import algvis.core.DataStructure;
import algvis.core.Node;
import algvis.core.NodeColor;
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
			this.setColor(NodeColor.NORMAL);
		} else {
			revflag = true;
			this.setColor(NodeColor.GREEN);
		}
	}
	
	public void flagDown() {
		revflag = false;
		this.setColor(NodeColor.NORMAL);
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
	}
	
	public void reboxRootL(ReversalNode root) {
		leftw = root.leftw + root.rightw;
		rightw = (getRight() == null) ? DataStructure.minsepx/2 : getRight().leftw
				+ getRight().rightw;	
	}
	
	public void reboxRootR(ReversalNode root) {
		leftw = (getLeft() == null) ? DataStructure.minsepx/2 : getLeft().leftw
				+ getLeft().rightw;
		rightw = root.leftw + root.rightw;
	}
	
	private void reposL(ReversalNode root) {
		if (isRoot()) {
		//	goToRoot();
			goTo(DataStructure.rootx - rightw - root.leftw, DataStructure.rooty);
	/*		D.x1 = -leftw;
			D.x2 = rightw;
			D.y2 = this.toy;*/
			// System.out.println ("r" + key + " " +leftw +"  "+ rightw);
		}
		if (this.toy > D.y2) {
			D.y2 = this.toy;
		}
		if (getLeft() != null) {
			getLeft().goTo(this.tox - getLeft().rightw,
					this.toy + DataStructure.minsepy);
			getLeft().reposL(root);
		}
		if (getRight() != null) {
			getRight().goTo(this.tox + getRight().leftw,
					this.toy + DataStructure.minsepy);
			getRight().reposL(root);
		}
	}

	private void reposR(ReversalNode root) {
		if (isRoot()) {
		//	goToRoot();
			goTo(DataStructure.rootx + leftw + root.rightw, DataStructure.rooty);
		/*	D.x1 = -leftw;
			D.x2 = rightw;
			D.y2 = this.toy;*/
			// System.out.println ("r" + key + " " +leftw +"  "+ rightw);
		}
		if (this.toy > D.y2) {
			D.y2 = this.toy;
		}
		if (getLeft() != null) {
			getLeft().goTo(this.tox - getLeft().rightw,
					this.toy + DataStructure.minsepy);
			getLeft().reposR(root);
		}
		if (getRight() != null) {
			getRight().goTo(this.tox + getRight().leftw,
					this.toy + DataStructure.minsepy);
			getRight().reposR(root);
		}
	}

	public void repositionL(ReversalNode root) {
		//	reboxTree();
			if (getLeft() != null) {
				getLeft().reboxTree();
			}
			if (getRight() != null) {
				getRight().reboxTree();
			}
	//		reboxRootL(root);
			rebox();

			reposL(root);
		}
		
	public void repositionR(ReversalNode root) {
		//	reboxTree();
			if (getLeft() != null) {
				getLeft().reboxTree();
			}
			if (getRight() != null) {
				getRight().reboxTree();
			}
			reboxRootR(root);

			reposR(root);
		}
	
	@Override
	public void draw(View V) {
		super.draw(V);
		V.drawStringLeft("" + size, x - Node.radius, y - Node.radius, 8);
	}

}
