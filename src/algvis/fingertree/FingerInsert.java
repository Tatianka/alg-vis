package algvis.fingertree;

import algvis.core.Algorithm;
import algvis.core.Colors;

public class FingerInsert extends Algorithm {

	FingerTree T;
	FingerNode v;
	int K;

	public FingerInsert(FingerTree T, int x) {
		super(T);
		this.T = T;
		v = T.v = new FingerNode(T, K = x); 
		v.bgColor(Colors.INSERT);
		setHeader("insertion");
	}	
	
	@Override
	public void run() {
		if (T.root == null) {   
			T.root = v;
			v.goAboveRoot();
			addStep("newroot");
			mysuspend();
			v.bgColor(Colors.NORMAL);
		//	T.v = null;
		//	T.prst.moveTo(v);
			T.finger = v;
		//	T.reposition();
		} else {
			FingerNode w = T.finger;
			// idem pomocou prsta
			v.goAbove(w);
			addStep("bstinsertstart");
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
					addStep("alreadythere");
					v.bgColor(Colors.DELETE);
					v.goDown();
					return;
				}
				
				if (w.isLeaf()) {
					break;
				}
				
				w = w.way(K);
				T.finger = w;
				v.goTo(w);
				mysuspend();
			}
			//// I have a leaf and I want to insert an element /////////////////
			w.addLeaf(K); //just adds x into this leaf
//			addStep("fingertree");
			if (w.numKeys >= 4) {
				w.bgColor(Colors.NOTFOUND); //if too much keys, that's not good
				addStep("fingertree");
			}
			T.v = null;
			T.reposition();
			mysuspend();

			// spliting node, if necessary
			boolean moveFinger = true;
			while (w.getNumKeys() >= 4) {
				addStep("bsplit");
				int o = (w.parent != null) ? w.order() : -1;
				w = w.split();
				if (moveFinger) {
					if (w.c[0].isIn(K)) {
						T.finger = w.c[0];
						moveFinger = false;
					} else {
						if (w.c[1].isIn(K)) {
							T.finger = w.c[1];
							moveFinger = false;
						} else {
							T.finger = w;
						}
					}
				}
				if (w.parent == null) {
					break;
				}
				w.parent.c[o] = w;
				mysuspend();
				w.goBelow(w.parent);
				mysuspend();
				w.parent.add(o, w);
				if (T.finger == w) {
					T.finger = w.parent;
				}
				w = w.parent;
				if (w.numKeys >= 4) {
					w.bgColor(Colors.NOTFOUND);
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