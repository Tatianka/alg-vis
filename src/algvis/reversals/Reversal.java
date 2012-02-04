package algvis.reversals;

import algvis.core.View;
import algvis.core.VisPanel;
import algvis.splaytree.SplayTree;
import algvis.splaytree.SplayInsert;

public class Reversal extends SplayTree {
	public static String dsName = "reversal";

	public Reversal(VisPanel M) {
		super(M);
		start(new SplayInsert(this, 1));
		start(new SplayInsert(this, 2));
		start(new SplayInsert(this, 3));
		start(new SplayInsert(this, 4));
		start(new SplayInsert(this, 5));
		start(new SplayInsert(this, 6));
		start(new SplayInsert(this, 7));
		start(new SplayInsert(this, 8));
		start(new SplayInsert(this, 9));
		start(new SplayInsert(this, 10));
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

}
