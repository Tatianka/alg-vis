package algvis.linkcuttree;

import algvis.bst.BSTNode;
import algvis.gui.view.View;
import algvis.splaytree.SplayNode;

public class SplayNodeM extends SplayNode {
//	SplayNodeM parent = null;
//	Vector<SplayTreeM> pathtree;
	SplayTreeM D;
	boolean leftMost = false;

	public SplayNodeM(SplayTreeM D, int key) {
		super(D, key);
		this.D = D;
//		pathtree = new Vector<SplayTreeM>();
	}
	
	public SplayNodeM search(int x) {
		if (getKey() == x) {
			return this;
		}
		SplayNodeM n = null;
		if (getLeft() != null) {
			n = getLeft().search(x);
			if (n != null) {return n;}
		}
		if (getRight() != null) {
			n = getRight().search(x);
		}
		return n;
	}
	
	public SplayTreeM getDatastructure() {
		return this.D;
	}
	
/*	public boolean tIsLeaf() {
		return (pathtree == null)?true:false;
	}*/
	
	@Override
	public void drawTree(View v) {
		super.drawTree(v);
/*		if (pathtree != null) {
			for(int i=0; i<pathtree.size(); i++) {
				pathtree.get(i).draw(v);
			}
		}*/
	}
	
	public SplayNodeM getParent() {
		return (SplayNodeM) super.getParent();
	}
	
	public void setParent(SplayNodeM v) {
		//	parent = v;
			super.setParent(v);
		}
		
	public BSTNode setParent(BSTNode v) {
		//	parent = v;
			super.setParent(v);
			return v;
		}
		
	public SplayNodeM next() {
		if (getRight() != null) {
			return getRight();
		}
		if (getParent().getLeft() == this) {
			return getParent();
		}
		SplayNodeM v = this, w = getParent();
		while (w != null && w.getRight() == v) {
			v = w;
			w = w.getParent();
		}
		return w;
	}
	
	public void reposition() {
		super.reposition();
/*		if (pathtree != null) {
			for(int i=0; i<pathtree.size(); i++) {
				pathtree.get(i).reposition();
			}
		}*/
	}
	
	public void shift(int xamount, int yamount) {
		goTo(tox + xamount, toy + yamount);
		if (getLeft() != null) {
			getLeft().shift(xamount, yamount);
		}
		if (getRight() != null) {
			getRight().shift(xamount, yamount);
		}
	}
	
	public SplayNodeM getLeft() {
		return (SplayNodeM) super.getLeft();
	}
	
	public void setLeft(SplayNodeM v) {
		super.setLeft(v);
		if (this == getDatastructure().leftMost) {
			getDatastructure().leftMost = v;
		}
	}

	public BSTNode setLeft(BSTNode v) {
		super.setLeft(v);
		if (this == getDatastructure().leftMost) {
			getDatastructure().leftMost = (SplayNodeM) v;
		}
		return v;
	}

	public SplayNodeM getRight() {
		return (SplayNodeM) super.getRight();
	}
	
	public SplayNode setRight() {
		return (SplayNodeM) super.getRight();
	}
	
	public BSTNode setRight(BSTNode v) {
		return super.setRight(v);
	}
}
