package algvis.reversals;

import algvis.core.View;
import algvis.core.VisPanel;
import algvis.splaytree.SplayTree;

public class Reversal extends SplayTree {
	public static String dsName = "reversal";

	public Reversal(VisPanel M) {
		super(M);
		setTree();
	}
	
	public void revers(int x, int y) {
		start(new Revers(this, x, y));
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
		root = v5;
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

	@Override
	public String getName() {
		return "reversal";
	}
	
	@Override
	public void draw(View V) {
		if (w1 != null && w1.getParent() != null) {
			V.drawWideLine(w1.x, w1.y, w1.getParent().x, w1.getParent().y);
		}
		if (w2 != null && w2.getParent() != null) {
			V.drawWideLine(w2.x, w2.y, w2.getParent().x, w2.getParent().y);
		}

		if (root != null) {
			root.moveTree();
			root.drawTree(V);
		}
		if (root2 != null) {
			root2.moveTree();
			root2.drawTree(V);
		}
		if (v != null) {
			v.move();
			v.draw(V);
		}
		if (vv != null) {
			vv.move();
			vv.draw(V);
		}
	}
	
	@Override
	public void clear() {
		super.clear();
		setTree();
	}

}
