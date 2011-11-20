package algvis.fingertree;

import algvis.btree.BTree;
import algvis.core.Colors;
import algvis.core.View;
import algvis.core.VisPanel;

public class FingerTree extends BTree {
		
	public static String dsName = "fingertree";
	final int order = 4;
	FingerNode root = null, v = null, finger = null;
//	Finger finger = null;
	
	public FingerTree(VisPanel M) {
		super(M);
	}

	@Override
	public void insert(int x) {
		start(new FingerInsert(this, x));
	}

	@Override
	public void find(int x) {
		start(new FingerFind(this, x));
	}

	/*@Override
	public void delete(int x) {
		start(new FingerDelete(this, x));
	}*/
	
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
	}

/////////////////////////copied from Finger/////////////
	public void setFinger(FingerNode N) {
		if (finger != null) {
			finger.bgColor(Colors.NORMAL);
		}
		finger = N;
//		finger.bgColor(Colors.FINGER);
	}
	
	public void fingerMoveTo (FingerNode N) {
		finger = N;
	}
	
	public void fingerMoveUp()	{
		finger = finger.parent;		// if finger != root
	}
	
	public void fingerMoveDown(int num) {
		finger = (FingerNode) finger.way(num); //prerobit na left, center, right ??
	}
///////////////////////// \copied from Finger/////////////

}
