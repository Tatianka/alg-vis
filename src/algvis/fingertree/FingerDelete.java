package algvis.fingertree;

import algvis.core.Algorithm;
import algvis.core.Colors;
import algvis.core.Node;

public class FingerDelete extends Algorithm {

	FingerTree T;
	FingerNode v;
	int K;

	public FingerDelete(FingerTree T, int x) {
		super(T.M);
		this.T = T;
		K = x;
		v = T.v = new FingerNode(T, x);
		v.bgColor(Colors.DELETE);
		setHeader("deletion");
	}
	
	public void run() {
		if (T.root == null) {
			v.goToRoot();
			setText("empty");
			mysuspend();
			v.goDown();
			v.bgColor(Colors.NOTFOUND);
			setText("notfound");
		} else {
			FingerNode w = T.finger;
			// idem pomocou prsta
			v.goAbove(w);
			setText("bstinsertstart");
			mysuspend();
			
			////////like in FingerFind///////////////
			//T.finger
			while (w != T.root) {
				if (w.belongsHere(K)) {break;}
				if (w.leftNeigbour != null) {
					if (w.leftNeigbour.belongsHere(K)) {
						w = w.leftNeigbour;
						T.finger = w;
						v.goTo(w);
						mysuspend();
					//	f.goAbove(w);
						break;
					}
				}
				if (w.rightNeighbour != null) {
					if (w.rightNeighbour.belongsHere(K)) {
						w = w.rightNeighbour;
						T.finger = w;
						v.goTo(w);
						mysuspend();
					//	f.goAbove(w);
						break;
					}
				}
				w = w.parent;
				T.finger = T.finger.parent;
				v.goTo(w);
				mysuspend();
				T.reposition();
			}
			//// now i have to go down /////////////
			while (true) {
				if (w.isIn(K)) {
					break;
				}
				
				if (w.isLeaf()) {
					setText("notfound");
					v.goDown();
					return;
				}
				
				w = w.way(K);
				T.finger = w;
				v.goTo(w);
				mysuspend();
			}
			
			// now I have an element and I want to delete it
			// deleting:
			w.bgColor(Colors.FOUND);
			mysuspend();
			w.bgColor(Colors.NORMAL);
			if (w.isLeaf()) {
				setText("bdelete1");
				if (w.isRoot() && w.numKeys == 1) {
					T.v = w;
					T.root = null;
					T.finger = T.v;
					T.v.goDown();
				} else {
					T.v = w.del(K);
					T.reposition();
					T.v.goDown();
					mysuspend();
				}
			} else {
				setText("bdelete2");
				FingerNode s = w.way(K + 1);
				v = T.v = new FingerNode(T, -Node.INF, w.x, w.y);
				v.goAbove(s);
				mysuspend();
				while (!s.isLeaf()) {
					s = s.c[0];
					v.goAbove(s);
					mysuspend();
				}
				v = T.v = s.delMin();
				v.goTo(w);
				mysuspend();
				w.replace(K, v.key[0]);
				T.v = null;
				mysuspend();
				w.bgColor(Colors.NORMAL);
				w = s;
			}

			while (!w.isRoot() && w.numKeys < 1) {
				w.bgColor(Colors.NOTFOUND);
				FingerNode s, s1 = null, s2 = null, p = w.parent;
				boolean lefts = true;
				int k = w.order(), n1 = 0, n2 = 0;
				if (k > 0) {
					s1 = p.c[k - 1];
					n1 = s1.numKeys;
				}
				if (k + 1 < p.numChildren) {
					s2 = p.c[k + 1];
					n2 = s2.numKeys;
				}
				if (n1 >= n2) {
					s = s1;
					--k;
				} else {
					s = s2;
					lefts = false;
				}

				if (s.numKeys > 1) {
					// treba zobrat prvok z s, nahradit nim p.key[k]
					// a p.key[k] pridat do d
					// tiez treba prehodit pointer z s ku d
					if (lefts) {
						setText("bleft");
					} else {
						setText("bright");
					}
					T.v = lefts ? s.delMax() : s.delMin();
					T.v.goTo(p);
					mysuspend();
					int pkey = p.key[k];
					p.key[k] = T.v.key[0];
					T.v = new FingerNode(T, pkey, p.x, p.y);
					T.v.goTo(w);
					mysuspend();
					if (lefts) {
						w.insMin(pkey);
						if (!w.isLeaf()) {
							w.insMinCh(s.delMaxCh());
							w.c[0].parent = w;
						}
					} else {
						w.insMax(pkey);
						if (!w.isLeaf()) {
							w.insMaxCh(s.delMinCh());
							w.c[w.numChildren - 1].parent = w;
						}
					}
					w.bgColor(Colors.NORMAL);
					T.v = null;
					break;
				} else {
					// treba spojit vrchol d + p.key[k] + s
					// zmenit p.c[k] na novy vrchol a posunut to
					setText("bmerge");
					if (p.isRoot() && p.numKeys == 1) {
						T.v = new FingerNode(T.root);
						T.root.key[0] = Node.NOKEY;
						T.v.goTo((w.tox + s.tox) / 2, w.y);
						mysuspend();
						if (lefts) {
							T.root = new FingerNode(s, T.v, w);
						} else {
							T.root = new FingerNode(w, T.v, s);
						}
						T.finger = T.root; // ??????????????????????
						break;
					} else {
						T.v = p.del(p.key[k]);
						T.v.goTo((w.tox + s.tox) / 2, w.y);
						mysuspend();
						if (lefts) {
							p.c[k] = new FingerNode(s, T.v, w);
						} else {
							p.c[k] = new FingerNode(w, T.v, s);
						}
						p.c[k].parent = p;
						--p.numChildren;
						for (int i = k + 1; i < p.numChildren; ++i) {
							p.c[i] = p.c[i + 1];
						}
						if (p.c[k].leftNeigbour != null) {
							p.c[k].leftNeigbour.rightNeighbour = p.c[k];
						}
						if (p.c[k].rightNeighbour != null) {
							p.c[k].rightNeighbour.leftNeigbour = p.c[k];
						}
						w = p;
						T.finger = w;
					}
				}
			}
			T.v = null;
			T.reposition();
////////////////////////////////////////////////
		}
	}
	
}
