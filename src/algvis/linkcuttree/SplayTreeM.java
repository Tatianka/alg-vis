package algvis.linkcuttree;

import java.awt.Color;
import java.util.Vector;

import algvis.core.DataStructure;
import algvis.core.Node;
import algvis.gui.VisPanel;
import algvis.gui.view.View;
import algvis.splaytree.SplayTree;

public class SplayTreeM extends SplayTree {
	LinkCutTree D;
	SplayNodeM pathparent = null, root = null, leftMost = null;
	private Vector<SplayTreeM> c;
	private Vector<SplayNodeM> d;

	public SplayTreeM(VisPanel M, LinkCutTree D) {
		super(M);
		this.D = D;
		c = new Vector<SplayTreeM>();
		d = new Vector<SplayNodeM>();
	}
	
	public void add(SplayNodeM v, SplayTreeM t) {
		c.add(t);
		d.add(v);
	}
	
	public void remove(SplayTreeM T) {
		for(int i=0; i<c.size(); i++) {
			if (c.get(i) == T) {
				remove(i);
				return;
			}
		}
	}
	
	public void remove(SplayNodeM N) {
		for(int i=0; i<c.size(); i++) {
			if (d.get(i) == N) {
				remove(i);
				return;
			}
		}
	}
	
	public void remove(int index) {
		c.remove(index);
		d.remove(index);
	}
	
	public SplayNodeM search(int x) {
		SplayNodeM n = getRoot().search(x);
		if (n!=null) {
			return n;
		}
		for(int i=0; i<c.size(); i++) {
			n = c.get(i).search(x);
			if (n!= null) {return n;}
		}
		return null;
	/*	SplayNodeM w = leftMost;
		while (w != null && n==null) {
			if (w.pathtree != null) {
				for(int i=0; i<w.pathtree.size(); i++) {
					n = w.pathtree.get(i).search(x);
				}
			}
			w = w.next();
		}
		return n;*/
	}
	
	public boolean tisRoot() {
		return (pathparent == null)?true:false;
	}
	
	public SplayNodeM getRoot() {
		return this.root; //(SplayNodeM) super.getRoot();
	}
	
	public void setRoot(SplayNodeM v) {
		//super.setRoot(v);
		this.root = v;
	}
	
	public void draw(View V) {
		super.draw(V);
		if (pathparent != null) {
			V.setColor(Color.gray);
			V.drawArrow(getRoot().x, getRoot().y - Node.radius,
					pathparent.x, pathparent.y + Node.radius);
		}
		for(int i=0; i<c.size(); i++) {
			c.get(i).draw(V);
		}
	}
	
	//////////////////////////////////////
	public void rebox() {
		int numChildren = c.size();
		if (numChildren == 0) {
			getRoot().leftw = getRoot().rightw = getRoot().leftw + getRoot().rightw + 19; 
			// D.radius +
			// D.xspan;
		} else {
			if (numChildren % 2 == 0) {
				getRoot().leftw = getRoot().rightw = 0;
			} else {
				getRoot().leftw = c.get(numChildren / 2).getRoot().leftw;
				getRoot().rightw = c.get(numChildren / 2).getRoot().rightw;
			}
			for (int i = 0; i < numChildren / 2; ++i) {
				getRoot().leftw += c.get(i).getRoot().leftw + c.get(i).getRoot().rightw;
			}
			for (int i = (numChildren + 1) / 2; i < numChildren; ++i) {
				getRoot().rightw += c.get(i).getRoot().leftw + c.get(i).getRoot().rightw;
			}
		}
	}

	public void reboxTree() {
	for (int i = 0; i < c.size(); ++i) {
		c.get(i).reboxTree();
	}
		rebox();
	}
	
	private void repos() {
	/*	if (isRoot()) {
			goToRoot();
			D.x1 = -leftw;
			D.x2 = rightw;
			D.y2 = this.toy;
		}*/
		int numChildren = c.size();
		
		if (getRoot().toy > D.y2) {
			D.y2 = getRoot().toy;
		}
		int x = getRoot().tox, x2 = getRoot().tox, y = getRoot().toy + 2 * Node.radius
				+ 19; //+ ((BTree) D).yspan;
		if (numChildren == 0) {
			return;
		}
		if (numChildren % 2 == 0) {
			int k = numChildren / 2 - 1;
			c.get(k).getRoot().shift(x -= c.get(k).getRoot().rightw, y);
			c.get(k).repos();
			for (int i = k - 1; i >= 0; --i) {
				c.get(i).getRoot().shift(x -= c.get(i + 1).getRoot().leftw + c.get(i).getRoot().rightw, y);
				c.get(i).repos();
			}
			c.get(++k).getRoot().shift(x2 += c.get(k).getRoot().leftw, y);
			c.get(k).repos();
			for (int i = k + 1; i < numChildren; ++i) {
				c.get(i).getRoot().shift(x2 += c.get(i - 1).getRoot().rightw + c.get(i).getRoot().leftw, y);
				c.get(i).repos();
			}
		} else {
			int k = numChildren / 2;
			c.get(k).getRoot().shift(x, y);
			c.get(k).repos();
			for (int i = 1; i <= k; ++i) {
				c.get(k - i).getRoot().shift(x -= c.get(k - i).getRoot().rightw + c.get(k - i + 1).getRoot().leftw, y);
				c.get(k - i).repos();
				c.get(k + i).getRoot().shift(x2 += c.get(k + i).getRoot().leftw + c.get(k + i - 1).getRoot().rightw, y);
				c.get(k + i).repos();
			}
		}
	}

////////////////////////////////////////
	public void reposition() {
		super.reposition();
		getRoot().shift(0, DataStructure.rooty+D.D.treeHeight-getRoot().toy);
		for(int i=0; i<c.size(); i++) {
			c.get(i).reposition();
		}
		
		reboxTree();
		repos();
	}
	
	
}
