package algvis.fingertree;

import algvis.core.Algorithm;
import algvis.core.Colors;


public class FingerFind extends Algorithm {

	FingerTree T;
	FingerNode v;
	int K;
	
	// z BFind
	public FingerFind(FingerTree T, int x) {
		super(T);
		this.T = T;
		v = T.v = new FingerNode(T, x);
		v.bgColor(Colors.FIND);
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
			v.bgColor(Colors.NOTFOUND);
			addStep("notfound");
		} else {
			FingerNode w = T.finger;
			// idem pomocou prsta
			v.goAbove(w);
			addStep("fstart");
			mysuspend();
			
			////////like in FingerFind///////////////
			//T.finger
			while (w != T.root) {
				if (w.isIn(K)) {
					addStep("found");
					v.goDown();
					v.bgColor(Colors.FOUND);
					mysuspend();
					return;				
				}
				if (w.belongsHere(K)) {
					addStep("fbelongs");
					break;
				}
				if (w.leftNeigbour != null) {
					if (w.leftNeigbour.belongsHere(K)) {
						addStep("flneighbour");
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
						addStep("frneighbour");
						w = w.rightNeighbour;
						T.finger = w;
						v.goTo(w);
						mysuspend();
					//	f.goAbove(w);
						break;
					}
				}
				addStep("fup");
				w = w.parent;
				T.finger = T.finger.parent;
				v.goTo(w);
				mysuspend();
				T.reposition();
			}
			//// now i have to go down /////////////
			while (true) {
				if (w.isIn(K)) {
					addStep("found");
					v.goDown();
					v.bgColor(Colors.FOUND);
					mysuspend();
					return;
				}
				
				if (w.isLeaf()) {
					addStep("notfound");
					v.goDown();
					return;
				}
				
				w = w.way(K);
				T.finger = w;
				v.goTo(w);
				mysuspend();
			}
		}
		v.goDown();
	}

}
