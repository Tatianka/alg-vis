package algvis.fingertree;

import algvis.btree.BNode;
import algvis.core.Algorithm;
import algvis.core.NodeColor;
import algvis.core.Node;

public class FingerDelete extends Algorithm {

	FingerTree T;
	BNode v;
	int K;

	public FingerDelete(FingerTree T, int x) {
		super(T);
		this.T = T;
		K = x;
		v = T.v = new BNode(T, x);
		v.setColor(NodeColor.DELETE);
		setHeader("deletion");
	}
	
	public void run() {
		if (T.root == null) {
			v.goToRoot();
			addStep("empty");
			mysuspend();
			v.goDown();
			v.setColor(NodeColor.NOTFOUND);
			addStep("notfound");
		} else {
//			FingerNode w = T.finger;
			// idem pomocou prsta
			v.goAboveRoot();
			addStep("bstinsertstart");
			mysuspend();
			
			////////like in FingerFind///////////////
			//T.finger
			while (T.prst.getNode() != T.root) {
				if (T.prst.getNode().belongsHere(K)) {break;}
				if (T.prst.moveToNeighbour(K)) {mysuspend(); break;}
				T.prst.moveUp();
				mysuspend();
			}
			//// now i have to go down /////////////
			while (true) {
				if (T.prst.getNode().isIn(K)) {
					break;
				}
				
				if (T.prst.getNode().isLeaf()) {
					addStep("notfound");
					v.goDown();
					return;
				}
				
				T.prst.moveDown(K);
				mysuspend();
			}
			
			// now I have an element and I want to delete it
			// deleting:
//			FingerNode d = T.prst.getNode();
			T.prst.getNode().setColor(NodeColor.FOUND);
			boolean isInLeaf;
			FingerNode b = T.prst.getNode();
			if (T.prst.getNode().isLeaf()) {
				isInLeaf = true;
			} else {
				isInLeaf = false;
			}
			if (! isInLeaf) {
				int o = T.prst.getNode().search(K);
				T.prst.moveTo(T.prst.getNode().c[o]); // once to the right
		//		v.goAbove(d);
				mysuspend();
				o = 0;
				while (! T.prst.getNode().isLeaf()) {
					// and then still to the left
					T.prst.moveTo(T.prst.getNode().c[0]);
					o++;
					mysuspend();
				}
			}
			
			FingerNode d = T.prst.getNode();

			boolean seen = ((isInLeaf) && (K == d.key[0])) ? false : true;
			if (! seen) {
				b = b.parent;
				while (! b.isIn(K)) {
					b = b.parent;
				}
				b.setColor(NodeColor.FOUND);
				isInLeaf = false;
			}

			d.setColor(NodeColor.FOUND);
			mysuspend();
			d.setColor(NodeColor.NORMAL);
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
					s = (FingerNode) s.c[0];
					v.goAbove(s);
					mysuspend();
				}
				v = T.v = s.delMin();
				v.goTo(d);
				mysuspend();
				d.replace(K, v.key[0]);
				T.v = null;
				mysuspend();
				d.setColor(NodeColor.NORMAL);
				d = s;
			}*/
			while (!d.isRoot() && d.numKeys < (T.order - 1) / 2) {
				d.setColor(NodeColor.NOTFOUND);
				FingerNode s, s1 = null, s2 = null, p = (FingerNode) d.parent;
				boolean lefts = true;
				int k = d.order(), n1 = 0, n2 = 0;
				if (k > 0) {
					s1 = (FingerNode) p.c[k - 1];
					n1 = s1.numKeys;
				}
				if (k + 1 < p.numChildren) {
					s2 = (FingerNode) p.c[k + 1];
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
					if (d.isLeaf()) {
						int pkey;
						if (lefts) {
							pkey = T.v.key[0];
						} else {
							pkey = s.key[0];
						}
						p.key[k] = pkey;
						T.v = new FingerNode(T, T.v.key[0], p.x, p.y);
						T.v.goTo(d);
						mysuspend();
						
						if (lefts) {
							d.insMin(T.v.key[0]);
						} else {
							d.insMax(T.v.key[0]);
						}
						d.setColor(NodeColor.NORMAL);
					} else {
						int pkey = p.key[k];
						p.key[k] = T.v.key[0];
						T.v = new FingerNode(T, pkey, p.x, p.y);
						T.v.goTo(d);
						mysuspend();
						if (lefts) {
							d.insMin(pkey);
//							if (!d.isLeaf()) {
								d.insMinCh(s.delMaxCh());
								d.c[0].parent = d;
	//						}
						} else {
							d.insMax(pkey);
		//					if (!d.isLeaf()) {
								d.insMaxCh(s.delMinCh());
								d.c[d.numChildren - 1].parent = d;
			//				}
						}
						d.setColor(NodeColor.NORMAL);
					}
					T.v = null;
					break;
				} else {
					// treba spojit vrchol d + p.key[k] + s
					// zmenit p.c[k] na novy vrchol a posunut to
					addStep("bmerge");
					if (p.isRoot() && p.numKeys == 1) {
						T.v = new BNode(T.root);
						T.root.key[0] = Node.NOKEY;
						T.v.goTo((d.tox + s.tox) / 2, d.y);
						mysuspend();
						if (lefts) {
							T.root = new FingerNode((FingerNode)s, (FingerNode)T.v, d);
						} else {
							T.root = new FingerNode(d, (FingerNode)T.v, (FingerNode)s);
						}
						break;
					} else {
						if (d.isLeaf()) {
							T.v = p.del(p.key[k]);
							T.v.goDown();
							mysuspend();
							if (lefts) {
								p.c[k] = new FingerNode(s, d);
							} else {
								p.c[k] = new FingerNode(d, s);
							}
							p.c[k].parent = p;
							--p.numChildren;
							for (int i = k + 1; i < p.numChildren; ++i) {
								p.c[i] = p.c[i + 1];
							}
							d = p;							
						} else {
							T.v = p.del(p.key[k]);
							T.v.goTo((d.tox + s.tox) / 2, d.y);
							mysuspend();
							if (lefts) {
								p.c[k] = new FingerNode(s, (FingerNode) T.v, d);
							} else {
								p.c[k] = new FingerNode(d, (FingerNode) T.v, s);
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
			/// now I will fix the case when the key is also in the index node			
			// b je moj vrchol
			d = b;
			if (! d.isIn(K)) {
				isInLeaf = true; //tzn, ze uz je to cislo prepisane
				d.setColor(NodeColor.NORMAL);
			}
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
		//			mysuspend();
					addStep("bdelete2");
					FingerNode s = (FingerNode) d.way(K + 1);
					v = T.v = new FingerNode(T, -Node.INF, d.x, d.y);
					v.goAbove(s);
					mysuspend();
					while (!s.isLeaf()) {
						s = (FingerNode) s.c[0];
						v.goAbove(s);
						mysuspend();
					}
				//	v = T.v = s.delMin();
					v = T.v = new FingerNode(s.D,s.key[0], s.x - (s.numKeys - 1) * s.D.radius, s.y);
					v.goTo(d);
					mysuspend();
					d.replace(K, v.key[0]);
					T.v = null;
					mysuspend();
					d.setColor(NodeColor.NORMAL);
					d = s;
			//	}

				while (!d.isRoot() && d.numKeys < (T.order - 1) / 2) {
					d.setColor(NodeColor.NOTFOUND);
					FingerNode /*s,*/ s1 = null, s2 = null, p = (FingerNode) d.parent;
					boolean lefts = true;
					int k = d.order(), n1 = 0, n2 = 0;
					if (k > 0) {
						s1 = (FingerNode) p.c[k - 1];
						n1 = s1.numKeys;
					}
					if (k + 1 < p.numChildren) {
						s2 = (FingerNode) p.c[k + 1];
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
						T.v = new FingerNode(T, pkey, p.x, p.y);
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
						d.setColor(NodeColor.NORMAL);
						T.v = null;
						break;
					} else {
						// treba spojit vrchol d + p.key[k] + s
						// zmenit p.c[k] na novy vrchol a posunut to
						addStep("bmerge");
						if (p.isRoot() && p.numKeys == 1) {
							T.v = new BNode(T.root);
							T.root.key[0] = Node.NOKEY;
							T.v.goTo((d.tox + s.tox) / 2, d.y);
							mysuspend();
							if (lefts) {
								T.root = new FingerNode((FingerNode)s, (FingerNode)T.v, d);
							} else {
								T.root = new FingerNode(d, (FingerNode)T.v, (FingerNode)s);
							}
							break;
						} else {
							T.v = p.del(p.key[k]);
							T.v.goTo((d.tox + s.tox) / 2, d.y);
							mysuspend();
							if (lefts) {
								p.c[k] = new FingerNode(s, (FingerNode) T.v, d);
							} else {
								p.c[k] = new FingerNode(d, (FingerNode) T.v, s);
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

			
			
/*			FingerNode w = T.prst.getNode();
			w.setColor(NodeColor.FOUND);
			mysuspend();
			w.setColor(NodeColor.NORMAL);
			if (w.isLeaf()) {
				addStep("bdelete1");
				if (w.isRoot() && w.numKeys == 1) {
					T.v = w;
					T.root = null;
					T.prst.moveTo(T.v);
					T.v.goDown();
				} else {
					T.v = (FingerNode) w.del(K);
					T.reposition();
					T.v.goDown();
					mysuspend();
				}
			} else {
				addStep("bdelete2");
				FingerNode s = (FingerNode) w.way(K + 1);
				v = T.v = new FingerNode(T, -Node.INF, w.x, w.y);
				v.goAbove(s);
				mysuspend();
				while (!s.isLeaf()) {
					s = (FingerNode) s.c[0];
					v.goAbove(s);
					mysuspend();
				}
				v = T.v = (FingerNode) s.delMin();
				v.goTo(w);
				mysuspend();
				w.replace(K, v.key[0]);
				T.v = null;
				mysuspend();
				w.setColor(NodeColor.NORMAL);
				w = s;
			}

			while (!w.isRoot() && w.numKeys < 1) {
				w.setColor(NodeColor.NOTFOUND);
				FingerNode s, s1 = null, s2 = null, p = w.parent;
				boolean lefts = true;
				int k = w.order(), n1 = 0, n2 = 0;
				if (k > 0) {
					s1 = (FingerNode) p.c[k - 1];
					n1 = s1.numKeys;
				}
				if (k + 1 < p.numChildren) {
					s2 = (FingerNode) p.c[k + 1];
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
						addStep("bleft");
					} else {
						addStep("bright");
					}
					T.v = lefts ? (FingerNode) s.delMax() : (FingerNode) s.delMin();
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
					w.setColor(NodeColor.NORMAL);
					T.v = null;
					break;
				} else {
					// treba spojit vrchol d + p.key[k] + s
					// zmenit p.c[k] na novy vrchol a posunut to
					addStep("bmerge");
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
						T.prst.moveTo(T.root);
						break;
					} else {
						T.v = (FingerNode) p.del(p.key[k]);
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
						if (p.c[k].leftNeighbour != null) {
							p.c[k].leftNeighbour.rightNeighbour = p.c[k];
						}
						if (p.c[k].rightNeighbour != null) {
							p.c[k].rightNeighbour.leftNeighbour = p.c[k];
						}
						w = p;
						T.prst.moveTo(w);
					}
				}
			}
			T.v = null;*/
////////////////////////////////////////////////
		}
		T.reposition();
	}
	
}
