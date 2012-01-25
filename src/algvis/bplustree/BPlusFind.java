package algvis.bplustree;

import algvis.btree.BNode;
import algvis.core.Algorithm;
import algvis.core.NodeColor;

public class BPlusFind extends Algorithm {
	BPlusTree T;
	BNode v;

	public BPlusFind(BPlusTree T, int x) {
		super(T);
		this.T = T;
		v = T.v = new BPlusNode(T, x);
		v.setColor(NodeColor.FIND);
		setHeader("search");
	}

	@Override
	public void run() {
		if (T.root == null) {
			v.goToRoot();
			addStep("empty");
			mysuspend();
			v.goDown();
			v.setColor(NodeColor.NOTFOUND);
			addStep("notfound");
		} else {
			BNode w = T.root;
			v.goTo(w);
			addStep("bstfindstart");
			mysuspend();
			
			BNode d = null;

			while (true) {
				if (w.isIn(v.key[0])) {
					if (w.isLeaf()) {
						addStep("found");
						v.goDown();
						v.setColor(NodeColor.FOUND);
						if (d != null) {
							d.setColor(NodeColor.NORMAL);
						}
						break;
					} else {
						w.setColor(NodeColor.FOUND);
						d = w;
					}
				}
				if (w.isLeaf()) {
					addStep("notfound");
					v.setColor(NodeColor.NOTFOUND);
					v.goDown();
					break;
				}
				w = w.way(v.key[0]);
				v.goTo(w);
				mysuspend();
			}
		}
	}
}
