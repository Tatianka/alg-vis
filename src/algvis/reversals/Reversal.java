package algvis.reversals;

import algvis.bst.BSTNode;
import algvis.core.View;
import algvis.core.VisPanel;
import algvis.splaytree.SplayTree;

public class Reversal extends SplayTree {
	public static String dsName = "reversal";
	ReversalNode root = null, left = null, right = null;
	public int max = 0;

	public Reversal(VisPanel M) {
		super(M);
		setTree();
	//	insert();
		max = 10;
	}
	
	public void revers(int x, int y) {
		start(new Revers(this, x, y));
	}
	
	public void insert() {
		start(new ReversalInsert(this));
		max+=10;
	}
	
	public void find(int x) {
		start(new ReversalFind(this, x));
	}
	
	public void setTree() {
		ReversalNode v1 = new ReversalNode(this, 1);
		ReversalNode v2 = new ReversalNode(this, 2);
		ReversalNode v3 = new ReversalNode(this, 3);
		ReversalNode v4 = new ReversalNode(this, 4);
		ReversalNode v5 = new ReversalNode(this, 5);
		ReversalNode v6 = new ReversalNode(this, 6);
		ReversalNode v7 = new ReversalNode(this, 7);
		ReversalNode v8 = new ReversalNode(this, 8);
		ReversalNode v9 = new ReversalNode(this, 9);
		ReversalNode v10 = new ReversalNode(this, 10);
		setRoot(v5);
		v5.setLeft(v3);
		v3.setParent(v5);
		v5.setRight(v8);
		v8.setParent(v5);
		v3.setLeft(v2);
		v2.setParent(v3);
		v2.setLeft(v1);
		v1.setParent(v2);
		v3.setRight(v4);
		v4.setParent(v3);
		v8.setLeft(v6);
		v6.setParent(v8);
		v6.setRight(v7);
		v7.setParent(v6);
		v8.setRight(v10);
		v10.setParent(v8);
		v10.setLeft(v9);
		v9.setParent(v10);
		this.reposition();		
	}
	
	public void setTree2() {
		boolean p = M.pause;
		M.pause = false;
		for (int i = 1; i < 10; ++i) {
			insert(i);
		}
		M.pause = p;
		this.reposition();
	}

	@Override
	public String getName() {
		return "reversal";
	}
	
	public ReversalNode getRoot() {
		return root;
	}

	public void setRoot(ReversalNode root) {
		this.root = root;
	}

	@Override
	public void draw(View V) {
		if (getRoot() != null) {
			getRoot().moveTree();
			getRoot().drawTree(V);
		}
		super.draw(V);
	}
	
	@Override
	public void clear() {
		super.clear();
		setTree2();
	}
	
	@Override
	public void reposition() {
		super.reposition();
		if (getRoot() != null) {		
			this.getRoot().setNum();
		}
	}
	
	protected void rightrot(ReversalNode v) {
		ReversalNode u = v.getParent();
		if (v.getRight() == null) {
			u.unlinkLeft();
		} else {
			u.linkLeft(v.getRight());
		}
		if (u.isRoot()) {
			setRoot( v);
		} else {
			if (u.isLeft()) {
				u.getParent().linkLeft(v);
			} else {
				u.getParent().linkRight(v);
			}
		}
		v.linkRight(u);
	}
	
	protected void leftrot(ReversalNode v) {
		ReversalNode u = v.getParent();
		if (v.getLeft() == null) {
			u.unlinkRight();
		} else {
			u.linkRight(v.getLeft());
		}
		if (u.isRoot()) {
			setRoot(v);
		} else {
			if (u.isLeft()) {
				u.getParent().linkLeft(v);
			} else {
				u.getParent().linkRight(v);
			}
		}
		v.linkLeft(u);
	}

	public void rotate(ReversalNode v) {
		if (v.isLeft()) {
			rightrot(v);
		} else {
			leftrot(v);
		}
		reposition();
		if (v.getLeft() != null) {
			v.getLeft().calc();
		}
		if (v.getRight() != null) {
			v.getRight().calc();
		}
		v.calc();
	}


}
