package algvis.bplustree;

import algvis.btree.BDelete;
import algvis.btree.BFind;
import algvis.btree.BTree;
import algvis.core.VisPanel;

public class BPlusTree extends BTree {
	public static String dsName = "bplustree";
	//public BPlusNode root = null;// v = null;

	public BPlusTree(VisPanel M) {
		super(M); // dsName sa zoberie moje alebo z Btree?
	}
	
	@Override
	public String getName() {
		return "bplustree";
	}

	@Override
	public void insert(int x) {
		start(new BPlusInsert(this, x));
	}

	@Override
	public void find(int x) {
		start(new BFind(this, x));
	}

	@Override
	public void delete(int x) {
		start(new BDelete(this, x));
	}
	
/*	@Override
	public void draw(View V) {
		if (root != null) {
			root.moveTree();
			root.drawTree(V);
		}
		if (v != null) {
			v.move();
			v.draw(V);
		}
	}*/

}
