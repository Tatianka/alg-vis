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
			T.prst.setFinger(T.root);
		//	T.finger = v;
		//	T.reposition();
		} else {
		//	FingerNode w = T.finger;
			// idem pomocou prsta
		//	v.goAbove(w);
			v.goAbove(T.root);
			addStep("bstinsertstart");
			mysuspend();
			
			////////like in FingerFind///////////////
			//T.finger
			while (T.prst.getNode() != T.root) {
				if (T.prst.getNode().belongsHere(K)) {break;}
			/*	if (T.prst.getNode().leftNeigbour != null) {
					if (T.prst.getNode().leftNeigbour.belongsHere(K)) {
						//w = w.leftNeigbour;
						T.prst.moveUp();
					//	T.finger = w;
					//	v.goTo(w);
						mysuspend();
					//	f.goAbove(w);
						break;
					}
				}
				if (T.prst.getNode().rightNeighbour != null) {
					if (T.prst.getNode().rightNeighbour.belongsHere(K)) {
						w = w.rightNeighbour;
						T.finger = w;
						v.goTo(w);
						mysuspend();
					//	f.goAbove(w);
						break;
					}
				}*/
				if (T.prst.moveToNeighbour(K)) {break;}
			//	w = w.parent;
				T.prst.moveUp();
			//	T.finger = T.finger.parent;
			//	v.goTo(w);
				mysuspend();
			//	T.reposition();
			}
			//// now i have to go down /////////////
			while (true) {
				if (T.prst.getNode().isIn(K)) {
					addStep("alreadythere");
					v.bgColor(Colors.DELETE);
					v.goDown();
					return;
				}
				
				if (T.prst.getNode().isLeaf()) {
					break;
				}
				
				//w = w.way(K);
				T.prst.move(K);
				//T.finger = w;
			//	v.goTo(w);
				mysuspend();
			}
			//// I have a leaf and I want to insert an element /////////////////
			FingerNode w = T.prst.getNode();
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
						T.prst.moveTo(w.c[0]);
						moveFinger = false;
					} else {
						if (w.c[1].isIn(K)) {
							//T.finger = w.c[1];
							T.prst.moveTo(w.c[1]);
							moveFinger = false;
						} else {
							//T.finger = w;
							T.prst.moveTo(w);
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
				if (T.prst.getNode() == w) {
					//T.finger = w.parent;
					T.prst.moveUp();
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