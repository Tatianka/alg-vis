package algvis.fingertree;

import algvis.core.Algorithm;
import algvis.core.Colors;

public class FingerInsert extends Algorithm {
	FingerTree T;
	FingerNode v;
	int K;

	public FingerInsert(FingerTree T, int x) {
		super(T.M);
		this.T = T;
		v = T.v = new FingerNode(T, K = x); //preco je tam aj T.v???
		v.bgColor(Colors.INSERT);
		setHeader("insertion");
	}	
	
	@Override
	public void run() {
		if (T.root == null) {   
			T.root = v;
			v.goAboveRoot();
			setText("newroot");
			mysuspend();
			v.bgColor(Colors.NORMAL);
			T.v = null;
			T.setFinger(v);
		} else {
			FingerNode w = T.finger;
			v.goAbove(w); 
			// v.goAbove(T.finger);
			setText("bstinsertstart");
			mysuspend();
			
			////////like in FingerFind///////////////
			//T.finger
			while (w != T.root) {
				if (w.belongsHere(K)) {break;}
				w = w.parent;
				T.finger = T.finger.parent;
				v.goTo(w);
				mysuspend();
			}
			//// now i have to go down /////////////
			while (true) {
				if (w.isIn(K)) {
				setText("alreadythere");
				v.bgColor(Colors.DELETE);
				v.goDown();
				return;
				}
				
				if (w.isLeaf()) {
					break;
				}
////////////////////////////////////////////
				int p = w.search(K);
				if (p == 0) {
					setText("bfind0", K, w.key[0]);
				} else if (p == w.numKeys) {
					setText("bfindn", w.key[w.numKeys - 1], K, w.numKeys + 1);
				} else {
					setText("bfind", w.key[p - 1], K, w.key[p], p + 1);
				}
				w = w.c[p];
				T.finger = w;
				v.goAbove(w);
				mysuspend();
				//////////////////////////
			}
			//// I have a leaf and I want to insert an element /////////////////
			setText("binsertleaf");
			w.addLeaf(K); //just adds x into this leaf
			if (w.numKeys >= T.order) {
				w.bgColor(Colors.NOTFOUND);
			}
			T.v = null;
			T.reposition();
			mysuspend();

			// spliting node, if necessary
			while (w.numKeys >= T.order) {
				setText("bsplit");
				
			}
			if (w.isRoot()) {
				T.root = w;
			}
		}
		T.reposition();
	}

}
