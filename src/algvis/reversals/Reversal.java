package algvis.reversals;

import algvis.core.View;
import algvis.core.VisPanel;
import algvis.splaytree.Splay;
import algvis.splaytree.SplayInsert;

public class Reversal extends Splay {
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
