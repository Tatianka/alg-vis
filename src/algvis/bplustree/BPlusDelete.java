package algvis.bplustree;

import algvis.btree.BNode;
import algvis.core.Algorithm;
import algvis.core.Colors;
import algvis.core.Node;

public class BPlusDelete extends Algorithm {
	BPlusTree T;
	BNode v;
	int K;

	public BPlusDelete(BPlusTree T, int x) {
		super(T);
		this.T = T;
		K = x;
		v = T.v = new BNode(T, x);
		v.bgColor(Colors.DELETE);
		setHeader("deletion");
	}

	@Override
	public void run() {
		if (T.root == null) {
			v.goToRoot();
			addStep("empty");
			mysuspend();
			v.goDown();
			v.bgColor(Colors.NOTFOUND);
			addStep("notfound");
		} else {
			BPlusNode d = (BPlusNode) T.root;
			v.goAbove(d);
			addStep("bstdeletestart");
			mysuspend();

			while (true) {
				if (d.isIn(K)) {
					break;
				}
				int p = d.search(K);
				if (p == 0) {
					addStep("bfind0", K, d.key[0]);
				} else if (p == d.numKeys) {
					addStep("bfindn", d.key[d.numKeys - 1], K, d.numKeys + 1);
				} else {
					addStep("bfind", d.key[p - 1], K, d.key[p], p + 1);
				}
				d = (BPlusNode) d.c[p];
				if (d == null) {
					break;
				}
				v.goAbove(d);
				mysuspend();
			}

			if (d == null) { // notfound
				addStep("notfound");
				v.goDown();
				return;
			}
			
	//now the key is found in some node..if it's index node, i have to find the key in a leaf
			d.bgColor(Colors.FOUND);
			boolean isParent = false, isInLeaf;
			BPlusNode b = d;
			if (d.isLeaf()) {
				isInLeaf = true;
			} else {
				isInLeaf = false;
			}
			if (! isInLeaf) {
				int o = d.search(K);
				d = (BPlusNode) d.c[o]; // once to the right
				v.goAbove(d);
				mysuspend();
				o = 0;
				while (! d.isLeaf()) {
					// and then still to the left
					d = (BPlusNode) d.c[0];
					o++;
					v.goAbove(d);
					mysuspend();
				}
				if (o == 1) {
					isParent = true;
				} else {
					isParent = false;
				}
			}

			d.bgColor(Colors.FOUND);
			mysuspend();
			d.bgColor(Colors.NORMAL);
	//		if (d.isLeaf()) {
				addStep("bdelete1");
				if (d.isRoot() && d.numKeys == 1) {
					T.v = d;
					T.root = null;
					T.v.goDown();
				} else {
					T.v = d.del(K);
					T.reposition();
					T.v.goDown();
					mysuspend();
				}
	/*		} else {
				addStep("bdelete2");
				BPlusNode s = (BPlusNode) d.way(K + 1);
				v = T.v = new BPlusNode(T, -Node.INF, d.x, d.y);
				v.goAbove(s);
				mysuspend();
				while (!s.isLeaf()) {
					s = (BPlusNode) s.c[0];
					v.goAbove(s);
					mysuspend();
				}
				v = T.v = s.delMin();
				v.goTo(d);
				mysuspend();
				d.replace(K, v.key[0]);
				T.v = null;
				mysuspend();
				d.bgColor(Colors.NORMAL);
				d = s;
			}*/
			while (!d.isRoot() && d.numKeys < (T.order - 1) / 2) {
				d.bgColor(Colors.NOTFOUND);
				BPlusNode s, s1 = null, s2 = null, p = (BPlusNode) d.parent;
				boolean lefts = true;
				int k = d.order(), n1 = 0, n2 = 0;
				if (k > 0) {
					s1 = (BPlusNode) p.c[k - 1];
					n1 = s1.numKeys;
				}
				if (k + 1 < p.numChildren) {
					s2 = (BPlusNode) p.c[k + 1];
					n2 = s2.numKeys;
				}
				if (n1 >= n2) {
					s = s1;
					--k;
				} else {
					s = s2;
					lefts = false;
				}

				if (s.numKeys > (T.order - 1) / 2) {
					// treba zobrat prvok z s, nahradit nim p.key[k]
					// a p.key[k] pridat do d
					// tiez treba prehodit pointer z s ku d
					if (lefts) {
						addStep("bleft");
					} else {
						addStep("bright");
					}
					T.v = lefts ? s.delMax() : s.delMin();
					T.v.goTo(p);
					mysuspend();
					int pkey = p.key[k];
					p.key[k] = T.v.key[0];
					T.v = new BNode(T, pkey, p.x, p.y);
					T.v.goTo(d);
					mysuspend();
					if (lefts) {
						d.insMin(pkey);
						if (!d.isLeaf()) {
							d.insMinCh(s.delMaxCh());
							d.c[0].parent = d;
						}
						/////////// odtialto chcem zobrat minimum a prepisat hodnotu vo vrchole
					} else {
						d.insMax(pkey);
						if (!d.isLeaf()) {
							d.insMaxCh(s.delMinCh());
							d.c[d.numChildren - 1].parent = d;
						}
					}
					d.bgColor(Colors.NORMAL);
					T.v = null;
					break;
				} else {
					// treba spojit vrchol d + p.key[k] + s
					// zmenit p.c[k] na novy vrchol a posunut to
					addStep("bmerge");
					if (p.isRoot() && p.numKeys == 1) {
						T.v = new BPlusNode(T.root);
						T.root.key[0] = Node.NOKEY;
						T.v.goTo((d.tox + s.tox) / 2, d.y);
						mysuspend();
						if (lefts) {
							T.root = new BPlusNode((BPlusNode)s, (BPlusNode)T.v, d);
						} else {
							T.root = new BPlusNode(d, (BPlusNode)T.v, (BPlusNode)s);
						}
						break;
					} else {
						T.v = p.del(p.key[k]);
						T.v.goTo((d.tox + s.tox) / 2, d.y);
						mysuspend();
						if (lefts) {
							p.c[k] = new BNode(s, T.v, d);
						} else {
							p.c[k] = new BNode(d, T.v, s);
						}
						p.c[k].parent = p;
						--p.numChildren;
						for (int i = k + 1; i < p.numChildren; ++i) {
							p.c[i] = p.c[i + 1];
						}
						d = p;
					}
				}
			}
			T.v = null;
			T.reposition();
			/// now I will fix the case when the key is also in the index node
			// b je moj vrchol
			d = b;
			if (! isInLeaf) {
				/*if (d.isLeaf()) {
					addStep("bdelete1");
					if (d.isRoot() && d.numKeys == 1) {
						T.v = d;
						T.root = null;
						T.v.goDown();
					} else {
						T.v = d.del(K);
						T.reposition();
						T.v.goDown();
						mysuspend();
					}
				} else {*/
					mysuspend();
					addStep("bdelete2");
					BPlusNode s = (BPlusNode) d.way(K + 1);
					v = T.v = new BPlusNode(T, -Node.INF, d.x, d.y);
					v.goAbove(s);
					mysuspend();
					while (!s.isLeaf()) {
						s = (BPlusNode) s.c[0];
						v.goAbove(s);
						mysuspend();
					}
				//	v = T.v = s.delMin();
					v = T.v = new BNode(s.D,s.key[0], s.x - (s.numKeys - 1) * s.D.radius, s.y);
					v.goTo(d);
					mysuspend();
					d.replace(K, v.key[0]);
					T.v = null;
					mysuspend();
					d.bgColor(Colors.NORMAL);
					d = s;
			//	}

				while (!d.isRoot() && d.numKeys < (T.order - 1) / 2) {
					d.bgColor(Colors.NOTFOUND);
					BPlusNode /*s,*/ s1 = null, s2 = null, p = (BPlusNode) d.parent;
					boolean lefts = true;
					int k = d.order(), n1 = 0, n2 = 0;
					if (k > 0) {
						s1 = (BPlusNode) p.c[k - 1];
						n1 = s1.numKeys;
					}
					if (k + 1 < p.numChildren) {
						s2 = (BPlusNode) p.c[k + 1];
						n2 = s2.numKeys;
					}
					if (n1 >= n2) {
						s = s1;
						--k;
					} else {
						s = s2;
						lefts = false;
					}

					if (s.numKeys > (T.order - 1) / 2) {
						// treba zobrat prvok z s, nahradit nim p.key[k]
						// a p.key[k] pridat do d
						// tiez treba prehodit pointer z s ku d
						if (lefts) {
							addStep("bleft");
						} else {
							addStep("bright");
						}
						T.v = lefts ? s.delMax() : s.delMin();
						T.v.goTo(p);
						mysuspend();
						int pkey = p.key[k];
						p.key[k] = T.v.key[0];
						T.v = new BNode(T, pkey, p.x, p.y);
						T.v.goTo(d);
						mysuspend();
						if (lefts) {
							d.insMin(pkey);
							if (!d.isLeaf()) {
								d.insMinCh(s.delMaxCh());
								d.c[0].parent = d;
							}
						} else {
							d.insMax(pkey);
							if (!d.isLeaf()) {
								d.insMaxCh(s.delMinCh());
								d.c[d.numChildren - 1].parent = d;
							}
						}
						d.bgColor(Colors.NORMAL);
						T.v = null;
						break;
					} else {
						// treba spojit vrchol d + p.key[k] + s
						// zmenit p.c[k] na novy vrchol a posunut to
						addStep("bmerge");
						if (p.isRoot() && p.numKeys == 1) {
							T.v = new BPlusNode(T.root);
							T.root.key[0] = Node.NOKEY;
							T.v.goTo((d.tox + s.tox) / 2, d.y);
							mysuspend();
							if (lefts) {
								T.root = new BPlusNode((BPlusNode)s, (BPlusNode)T.v, d);
							} else {
								T.root = new BPlusNode(d, (BPlusNode)T.v, (BPlusNode)s);
							}
							break;
						} else {
							T.v = p.del(p.key[k]);
							T.v.goTo((d.tox + s.tox) / 2, d.y);
							mysuspend();
							if (lefts) {
								p.c[k] = new BNode(s, T.v, d);
							} else {
								p.c[k] = new BNode(d, T.v, s);
							}
							p.c[k].parent = p;
							--p.numChildren;
							for (int i = k + 1; i < p.numChildren; ++i) {
								p.c[i] = p.c[i + 1];
							}
							d = p;
						}
					}
				}

			}
			
			T.v = null;
			T.reposition();
		}
	}
}
