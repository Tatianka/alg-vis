package algvis.fingertree;

import algvis.core.Algorithm;
import algvis.core.NodeColor;


public class FingerFind extends Algorithm {

	FingerTree T;
	FingerNode v;
	int K;
	
	// z BFind
	public FingerFind(FingerTree T, int x) {
		super(T);
		this.T = T;
		v = T.v = new FingerNode(T, x);
		v.setColor(NodeColor.FIND);
		setHeader("search");
		K = x;
	}
	
	/* teoria:
	 * 1. skontrolujem, ci prvok je vacsi alebo mensi ako ten, kam ukazujem
	 * 2. skontrolujem suseda na danej strane, ak je prvok v danom podstrome, prejdem na vrchol
	 * 3. ak nie je, idem prstom hore
	 * 
	 * pozn: technicke/graficke veci su skopirovane z BFind*/
	public void run() {
		if (T.root == null) {
			v.goToRoot();
			addStep("empty");
			mysuspend();
			v.goDown();
			v.setColor(NodeColor.NOTFOUND);
			addStep("notfound");
		} else {
			// idem pomocou prsta
			v.goAboveRoot();
			addStep("fstart");
			mysuspend();
			
			////////like in FingerFind///////////////
			while (T.prst.getNode() != T.root) {
				if (T.prst.getNode().isIn(K)) {
					addStep("found");
					v.goDown();
					v.setColor(NodeColor.FOUND);
					mysuspend();
					return;				
				}
				if (T.prst.getNode().belongsHere(K)) {
					addStep("fbelongs");
					break;
				}
				if (T.prst.moveToNeighbour(K)) {mysuspend(); break;}
				addStep("fup");
				T.prst.moveUp();
				mysuspend();
			}
			//// now i have to go down /////////////
			while (true) {
				if (T.prst.getNode().isIn(K)) {
					addStep("found");
					v.goDown();
					v.setColor(NodeColor.FOUND);
					mysuspend();
					return;
				}
				
				if (T.prst.getNode().isLeaf()) {
					addStep("notfound");
					v.goDown();
					return;
				}
				
				T.prst.moveDown(K);
				mysuspend();
			}
		}
		v.goDown();
	}

}
