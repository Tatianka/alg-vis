package algvis.fingertree;

import algvis.core.Colors;
import algvis.core.Dictionary;
import algvis.core.View;
import algvis.core.VisPanel;

public class FingerTree extends Dictionary {
	
	public static String dsName = "fingertree";
	final int order = 4;
	FingerNode root = null, v = null, finger = null;
	int xspan = 5, yspan = 15;
	
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
	//	start(new FingerDelete(this, x));
	}
	
	public void draw(View V) {
		if (root != null) {
			root.moveTree();
			root.drawTree(V);
			// draw neighbours - change Draw from Node or add another function
		}
		if (v != null) {
			if (v == finger) {
				v.bgColor(Colors.FINGER);
			} else {
				v.bgColor(Colors.NORMAL);
			}
			v.move();
			v.draw(V);
		}
		//draw finger
	}
	
	public void reposition() {
		if (root != null) {
			root._reposition();
			M.S.V.setBounds(x1, y1, x2, y2);
		}
	}
	
	@Override
	public void clear() {
		root = null;
		setStats();
	}

	@Override
	public String stats() {
		if (root == null) {
			return "#" + M.L.getString("nodes") + ": 0;   #"
					+ M.L.getString("keys") + ": 0 = 0% "
					+ M.L.getString("full") + ";   " + M.L.getString("height")
					+ ": 0";
		} else {
			root.calcTree();
			return "#" + M.L.getString("nodes") + ": " + root.nnodes + ";   "
					+ "#" + M.L.getString("keys") + ": " + root.nkeys + " = "
					+ (100 * root.nkeys) / (root.nnodes * (order - 1)) + "% "
					+ M.L.getString("full") + ";   " + M.L.getString("height")
					+ ": " + root.height;
		}
	}

}
