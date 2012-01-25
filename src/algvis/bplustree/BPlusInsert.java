package algvis.bplustree;

import algvis.btree.BNode;
import algvis.core.Algorithm;
import algvis.core.NodeColor;

public class BPlusInsert extends Algorithm {
	BPlusTree T;
	//BPlusNode v;
	//BTree T;
	BNode v;
	int K;

	public BPlusInsert(BPlusTree T, int x) {
		super(T);
		this.T = T;
		v = new BNode(T, K = x);
		T.v = v;
		v.setColor(NodeColor.INSERT);
		setHeader("insertion");
	}

	@Override
	public void run() {
		if (T.root == null) {
			T.root = new BPlusNode(v.D, K, v.x, v.y);
			v.goAboveRoot();
			addStep("newroot");
			mysuspend();
			v.setColor(NodeColor.NORMAL);
			T.reposition();
			T.v = null;
		} else {
			BPlusNode w = (BPlusNode) T.root;
			v.goAbove(w);
			addStep("bstinsertstart");
			mysuspend();

			while (true) {
				if (w.isIn(K)) {
					addStep("alreadythere");
					v.goDown();
					return;
				}
				if (w.isLeaf()) {
					break;
				}
				int p = w.search(K);
				if (p == 0) {
					addStep("bfind0", K, w.key[0]);
				} else if (p == w.numKeys) {
					addStep("bfindn", w.key[w.numKeys - 1], K, w.numKeys + 1);
				} else {
					addStep("bfind", w.key[p - 1], K, w.key[p], p + 1);
				}
				w = (BPlusNode) w.c[p];
				v.goAbove(w);
				mysuspend();
			}

			addStep("binsertleaf");
			w.addLeaf(K);
			if (w.numKeys >= T.order) {
				w.setColor(NodeColor.NOTFOUND);
			}
			T.v = null;
			mysuspend();

			while (w.numKeys >= T.order) {
				addStep("bsplit");
				int o = (w.parent != null) ? w.order() : -1;
				w = w.split();
				if (w.parent == null) {
					break;
				}
				w.parent.c[o] = w;
				mysuspend();
				w.goBelow(w.parent);
				mysuspend();
				w.parent.add(o, w);
				w = (BPlusNode) w.parent; ///////////?????????????
				if (w.numKeys >= T.order) {
					w.setColor(NodeColor.NOTFOUND);
				}
				T.reposition();
				mysuspend();
			}
			if (w.isRoot()) {
				T.root = w;
			}
			T.reposition();
		}
	}

}
