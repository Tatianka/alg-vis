package algvis.bplustree;

import algvis.btree.BNode;
import algvis.core.DataStructure;

public class BPlusNode extends BNode {
//	BPlusNode[] c;

	public BPlusNode(BNode v) {
		super(v);
		c = new BPlusNode[((BPlusTree) D).order + 5];
	}
	
	public BPlusNode(BPlusNode v) {
		super(v);
		c = new BPlusNode[((BPlusTree) D).order + 5];
	}
	
	public BPlusNode(DataStructure D, int key, int x, int y) {
		super(D,key,x,y);
		c = new BPlusNode[((BPlusTree) D).order + 5];
	}
	
	public BPlusNode(DataStructure D, int key) {
		super(D,key);
		c = new BPlusNode[((BPlusTree) D).order + 5];
	}
	
	public BPlusNode(BPlusNode u, BPlusNode v, BPlusNode w) {
		super(u,v,w);
	}
	
///////////////////////////////////////////////////////////////////////////////////
	public BPlusNode copyUp() {
		int k = numKeys, ku = numKeys / 2; // , kw = numKeys - ku - 1;
		BPlusNode u = new BPlusNode(D, key[0], x, y), v = new BPlusNode(D, key[ku], x, y), 
			w = new BPlusNode(D, key[k - 1], x, y);
		for (int i = 1; i < ku; ++i) {
			u.addLeaf(key[i]);
		}
		for (int i = ku; i < k - 1; ++i) { //!!!!
			w.addLeaf(key[i]);
		}
		if (isLeaf()) {
			u.numChildren = w.numChildren = 0;
		} else {
			u.numChildren = (numChildren + 1) / 2;
			w.numChildren = numChildren / 2;
			for (int i = 0; i < u.numChildren; ++i) {
				u.c[i] = c[i];
				u.c[i].parent = u;
			}
			for (int i = 0; i < w.numChildren; ++i) {
				w.c[i] = c[u.numChildren + i];
				w.c[i].parent = w;
			}
		}
		u.parent = w.parent = v;
		v.numChildren = 2;
		v.parent = parent;
		v.c[0] = u;
		v.c[1] = w;
		u.width = u._width();
		w.width = w._width();
		u.x = x - u.width / 2 - D.radius;
		w.x = x + w.width / 2 + D.radius;
		return v;
	}
	
	public BPlusNode pushUp() {
		int k = numKeys, ku = numKeys / 2; // , kw = numKeys - ku - 1;
		BPlusNode u = new BPlusNode(D, key[0], x, y), v = new BPlusNode(D, key[ku], x, y), 
			w = new BPlusNode(D, key[k - 1], x, y);
		for (int i = 1; i < ku; ++i) {
			u.addLeaf(key[i]);
		}
		for (int i = ku+1; i < k - 1; ++i) { //!!!!
			w.addLeaf(key[i]);
		}
		if (isLeaf()) {
			u.numChildren = w.numChildren = 0;
		} else {
			u.numChildren = (numChildren + 1) / 2;
			w.numChildren = numChildren / 2;
			for (int i = 0; i < u.numChildren; ++i) {
				u.c[i] = c[i];
				u.c[i].parent = u;
			}
			for (int i = 0; i < w.numChildren; ++i) {
				w.c[i] = c[u.numChildren + i];
				w.c[i].parent = w;
			}
		}
		u.parent = w.parent = v;
		v.numChildren = 2;
		v.parent = parent;
		v.c[0] = u;
		v.c[1] = w;
		u.width = u._width();
		w.width = w._width();
		u.x = x - u.width / 2 - D.radius;
		w.x = x + w.width / 2 + D.radius;		
		return v;
	}
	
////////////////split 1 a split 2 - copy up a push up /////////////////////////////
	@Override
	public BPlusNode split() {
		if (isLeaf()) {
			return copyUp();
		}
		return pushUp();
	}
//////////////////////////////////////////////////////////////////////////
/*	@Override
	public void drawTree(View v) {
		for (int i = 0; i < numChildren; ++i) {
			v.setColor(Color.black);
			/*
			 * int xx, yy; if (i==0 || i==numChildren-1) { xx = x; yy = y; }
			 * else { xx = (pos(i-1)+pos(i))/2; yy = y+D.radius; }
			 */
		/*	v.drawLine(x, y, c[i].x, c[i].y - D.radius);
			c[i].drawTree(v);
		}
		draw(v);
	}
	
	@Override
	public void moveTree() {
		for (int i = 0; i < numChildren; ++i) {
			c[i].moveTree();
		}
		move();
	}*/


}
