package algvis.fingertree;

import algvis.core.Algorithm;
import algvis.core.NodeColor;

public class FingerInsert extends Algorithm {

	FingerTree T;
	FingerNode v;
	int K;

	public FingerInsert(FingerTree T, int x) {
		super(T);
		this.T = T;
		v = T.v = new FingerNode(T, K = x); 
		v.setColor(NodeColor.INSERT);
		setHeader("insertion");
	}	
	
	@Override
	public void run() {
		if (T.root == null) {   
			T.root = v;
			v.goAboveRoot();
			addStep("newroot");
			mysuspend();
			v.setColor(NodeColor.NORMAL);
			T.prst.setFinger(T.root);
		} else {
			// idem pomocou prsta
			v.goAbove(T.root);
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
					addStep("alreadythere");
					v.setColor(NodeColor.DELETE);
					v.goDown();
					return;
				}
				
				if (T.prst.getNode().isLeaf()) {
					break;
				}
				
				T.prst.move(K);
				mysuspend();
			}
			//// I have a leaf and I want to insert an element /////////////////
			FingerNode w = T.prst.getNode();
			w.addLeaf(K); //just adds x into this leaf
			if (w.numKeys >= 4) {
				w.setColor(NodeColor.NOTFOUND); //if too much keys, that's not good
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
							T.prst.moveTo(w.c[1]);
							moveFinger = false;
						} else {
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
					T.prst.moveUp();
				}
				w = w.parent;
				if (w.numKeys >= 4) {
					w.setColor(NodeColor.NOTFOUND);
				}				
				T.reposition();
				mysuspend();
			}
			if (w.isRoot()) {
				T.root = w;
			}
		}
		T.reposition();
	}


}