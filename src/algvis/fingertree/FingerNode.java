package algvis.fingertree;

import algvis.core.*;
import algvis.btree.BNode;

public class FingerNode extends BNode {

	int width, leftw, rightw;
	FingerNode parent = null, leftNeigbour = null, rightNeighbour = null; 
	int numKeys = 1, numChildren = 0;
	int[] key;
	FingerNode[] c;

	public FingerNode(DataStructure D, int key, int x, int y) {
		super(D,key,x,y);
	//	setNeighbour();
	}
	
	public FingerNode(DataStructure D, int key) {
		super(D,key);
	//	setNeighbour();
	}
	
	public FingerNode(FingerNode v) {
		super( (BNode) v);
	//	setNeighbour();
	}
	
	public FingerNode(FingerNode u, FingerNode v, FingerNode w) {
		super( (BNode) u, (BNode) v, (BNode) w);
	//	setNeighbour();
	}
	
	public FingerNode split() {
		FingerNode ch1 = new FingerNode(D, key[0], x, y), ch2 = new FingerNode(D, key[3], x, y), p = new FingerNode(
				D, key[2], x, y);
		ch1.addLeaf(key[1]);
		if (isLeaf()) {
			ch1.numChildren = ch2.numChildren = 0;
		} else {
			/*ch1.c[0] = c[0];
			ch1.c[1] = c[1];
			ch1.c[2] = c[2];
			ch2.c[0] = c[3];
			ch2.c[1] = c[4];*/
			//////????????????????????//////////////
		}
		ch1.parent = ch2.parent = p;
		p.numChildren = 2;
		p.parent = parent;
		p.c[0] = ch1;
		p.c[1] = ch2;
		
		ch1.width = ch1._width();
		ch2.width = ch2._width();
		ch1.x = x - ch1.width / 2 - D.radius;
		ch2.x = x + ch2.width / 2 + D.radius;
		
		return p;
	}
	
///// if x belongs to this undertree //////////
	public boolean belongsHere(int x) {
		if ((x >= key[0]) && (x <= key[numKeys-1])) {
			return true;
		}
		return false;
	}
	public void setNeighbour() {
		if (parent.isLeaf()) {return;}
		if (parent.leftNeigbour.c[0] != null) {
			leftNeigbour = parent.leftNeigbour.c[0];
		}
		if (parent.rightNeighbour.c[parent.leftNeigbour.numKeys-1] != null) {
			rightNeighbour = parent.rightNeighbour.c[parent.leftNeigbour.numKeys-1];
		}
	}
	
	int _width() {
		if (key[0] != Node.NOKEY && numKeys > 0) {
			return Math.max(Fonts.fm[9].stringWidth(toString()) + 4,
					2 * D.radius);
		} else {
			return 2 * D.radius;
		}
	}	
			
}
