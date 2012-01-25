package algvis.fingertree;

import algvis.bplustree.BPlusTree;
import algvis.core.View;
import algvis.core.VisPanel;

public class FingerTree extends BPlusTree {
	
	public static String dsName = "fingertree";
	final int order = 4;
/*	FingerNode root = null, v = null;
	int xspan = 5, yspan = 15;*/
	FingerNode root = null;
	Finger prst = new Finger(this);
	
	public FingerTree(VisPanel M) {
		super(M);
	}
	
	public void insert(int x) {
		start(new FingerInsert(this, x));
	}

	public void find(int x) {
		start(new FingerFind(this, x));
	}

	public void delete(int x) {
		start(new FingerDelete(this, x));
	}
	
	@Override
	public void draw(View V) {
		if (root != null) {
			root.moveTree();
			root.drawTree(V);
		}
		if (v != null) {
			v.move();
			v.draw(V);
		}
		prst.move();
		prst.draw(V);
	}
	
	public void reposition() {
		if (root != null) {
			root._reposition();
			M.screen.V.setBounds(x1, y1, x2, y2);
		}
	}
	
	@Override
	public void clear() {
		root = null;
		prst.setFinger(null);
		setStats();
	}
/*
	@Override
	public String stats() {
		if (root == null) {
			return "#" + M.S.L.getString("nodes") + ": 0;   #"
					+ M.S.L.getString("keys") + ": 0 = 0% "
					+ M.S.L.getString("full") + ";   " + M.S.L.getString("height")
					+ ": 0";
		} else {
			root.calcTree();
			return "#" + M.S.L.getString("nodes") + ": " + root.nnodes + ";   "
					+ "#" + M.S.L.getString("keys") + ": " + root.nkeys + " = "
					+ (100 * root.nkeys) / (root.nnodes * (order - 1)) + "% "
					+ M.S.L.getString("full") + ";   " + M.S.L.getString("height")
					+ ": " + root.height;
		}
	}
*/
	@Override
	public String getName() {
		return "fingertree";
	}

}
