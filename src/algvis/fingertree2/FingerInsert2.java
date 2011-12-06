package algvis.fingertree2;

import algvis.core.Algorithm;
import algvis.core.Colors;

public class FingerInsert2 extends Algorithm {

	FingerTree2 T;
	FingerNode2 v;
	int K;

	public FingerInsert2(FingerTree2 T, int x) {
		super(T.M);
		this.T = T;
		v = T.v = new FingerNode2(T, K = x); 
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
			T.finger = v;
			T.reposition();
		} else {
			// idem pomocou prsta
			v.goAbove(T.finger);
			setText("bstinsertstart");
			mysuspend();
			
			////////like in FingerFind///////////////
			//T.finger
			while (T.finger != T.root) {
				if (T.finger.belongsHere(K)) {break;}
				//w = w.parent;
				T.finger = T.finger.parent;
				v.goTo(T.finger);
				mysuspend();
				T.reposition();
			}
			//// now i have to go down /////////////
			while (true) {
				if (T.finger.isIn(K)) {
					setText("alreadythere");
					v.bgColor(Colors.DELETE);
					v.goDown();
					return;
				}
				
				if (T.finger.isLeaf()) {
					break;
				}
				
				T.finger = T.finger.way(K);	
			}
			//// I have a leaf and I want to insert an element /////////////////
			T.finger.addLeaf(K); //just adds x into this leaf
//			setText("fingertree");
			if (T.finger.numKeys >= 4) {
				T.finger.bgColor(Colors.NOTFOUND); //if too much keys, that's not good
				setText("fingertree");
			}
			T.v = null;
			T.reposition();
			mysuspend();

			// spliting node, if necessary
			//FingerNode2 w = T.finger;
			while (T.finger.getNumKeys() >= 4) {
				setText("bsplit");
				T.finger = T.finger.split();
				if (T.finger.isRoot()) {break;}
				if (T.finger.numKeys >= 4) { T.finger.bgColor(Colors.NOTFOUND);}
				
				T.reposition();
			}
			if (T.finger.isRoot()) {
				T.root = T.finger;
			}
			T.reposition();
		}
	}


}
