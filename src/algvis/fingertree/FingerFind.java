package algvis.fingertree;

import algvis.core.*;

public class FingerFind extends Algorithm {
	FingerTree T;
	FingerNode v;
	
	// z BFind
	public FingerFind(FingerTree T, int x) {
		super(T.M);
		this.T = T;
		v = T.v = new FingerNode(T, x);
		v.bgColor(Colors.FIND);
		setHeader("search");
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
			setText("empty");
			mysuspend();
			v.goDown();
			v.bgColor(Colors.NOTFOUND);
			setText("notfound");	
		} else {
			FingerNode w = T.finger;
			v.goTo(w);
			setText("fingerfindstart");
			mysuspend();
			
			if (w.isIn(v.key[0])) {
				setText("found");
				v.goDown();
				v.bgColor(Colors.FOUND);
				return;
			}
			
			//zistujem podstrom
		/*	if ( v.key[0] > w.key[w.numKeys-1]) {
				while (w != T.root) {
					if (w.rightNeighbour.belongsHere(v.key[0])) {
						w = w.rightNeighbour;
						break;
					}
					w = w.parent;
					if (w.isIn((v.key[0]))) {break;}
				}
			} else {
				while (w != T.root) {
					if (w.leftNeigbour.belongsHere(v.key[0])) {
						w = w.leftNeigbour;
						break;
					}
					w = w.parent;
					if (w.isIn(v.key[0])) {break;}
				}
			}*/
			while (w != T.root) {
				w = w.parent;
				v.goTo(w);
				if (w.belongsHere(v.key[0])) {break;}
				mysuspend();
			}
			
			//teraz musim zist dole
			while (true) {
				if (w.isIn(v.key[0])) {
					setText("found");
					v.goDown();
					v.bgColor(Colors.FOUND);
					T.fingerMoveTo(w);
					break;
				}
				if (w.isLeaf()) {
					setText("notfound");
					v.bgColor(Colors.NOTFOUND);
					v.goDown();
					break;
				}
				w = (FingerNode) w.way(v.key[0]);
				v.goTo(w);
				mysuspend();
			}
		}		
	}	
}
