package algvis.linkcuttree;

import java.awt.Color;

import algvis.core.DataStructure;
import algvis.core.TreeNode;
import algvis.gui.view.View;

public class LCTree extends TreeNode {
	LinkCutDS D;
	LCTree prefLeft = null, prefRight = null;
	int revflag;
	
	public void rebox() {
		leftw = (pgetLeft() == null) ? DataStructure.minsepx / 2
				: pgetLeft().leftw + pgetLeft().rightw;
		if (isLeft() && getRight()==null && rightw < DataStructure.minsepx) {
			leftw = DataStructure.minsepx;
		}
		rightw = (pgetRight() == null) ? DataStructure.minsepx / 2
				: pgetRight().leftw + pgetRight().rightw;
		if (isRight() && getChild()==this && leftw < DataStructure.minsepx) {
			leftw = DataStructure.minsepx;
		}
		LCTree w = getUnprefChild();
		while (w != null) {
			rightw += w.leftw + w.rightw;
			w = w.getRight();
		}
	}

	public void reboxTree() {
		if (pgetLeft() != null) {
			pgetLeft().reboxTree();
		}
		if (pgetRight() != null) {
			pgetRight().reboxTree();
		}
		LCTree w = getUnprefChild();
		while (w != null) {
			w.reboxTree();
			w = w.getRight();
		}
		rebox();
	}

	
	private void repos() {
		int gap = this.tox;
		if (pgetLeft() != null) {
			pgetLeft().goTo(this.tox - pgetLeft().rightw,
					this.toy + DataStructure.minsepy);
			pgetLeft().repos();
			gap = pgetLeft().tox + pgetLeft().rightw;
		}
		if (pgetRight() != null) {
			pgetRight().goTo(this.tox + pgetRight().leftw,
					this.toy + DataStructure.minsepy);
			pgetRight().repos();
			gap = pgetRight().tox + pgetRight().rightw;
		}
		LCTree w = getUnprefChild();
		while (w != null) {
			w.goTo(gap + w.leftw, this.toy + DataStructure.minsepy);
			w.repos();
			gap = w.tox + w.rightw;
			w = w.getRight();
		}
	}

	public void repos(int x, int y) {
		goTo(x, y);
		int gap = this.tox;
		if (pgetLeft() != null) {
			pgetLeft().repos(this.tox - pgetLeft().rightw,
					this.toy + DataStructure.minsepy);
			gap = pgetLeft().tox + pgetLeft().rightw;
		}
		if (pgetRight() != null) {
			pgetRight().repos(this.tox + pgetRight().leftw,
					this.toy + DataStructure.minsepy);
			gap = pgetRight().tox + pgetRight().rightw;
		}
		LCTree w = getUnprefChild();
		while (w != null) {
			w.repos(gap + w.leftw, this.toy + DataStructure.minsepy);
			gap = w.tox + w.rightw;
			w = w.getRight();
		}
	}
	
	public void reposition() {
		super.reposition();
		repos();
	}
	
	public LCTree(LinkCutDS D, int key) {
		super(D, key);
		this.D = D;
		revflag = 0;
	}
	
	public LCTree getRight() {
		LCTree w = getParent();
		if (w==null) {return null;}
		if (this == w.pgetLeft()) {
			if (w.pgetRight() != null) {
				return w.pgetRight();
			} else {
				return w.getUnprefChild();
			}
		}
		if (this == w.pgetRight()) {
			return w.getUnprefChild();
		}
		return (LCTree) super.getRight();
	}
	
	public LCTree getChild() {
		if (pgetLeft() != null) {
			return pgetLeft();
		}
		if (pgetRight() != null) {
			return pgetRight();
		}
		return (LCTree) super.getChild();
	}
	
	public LCTree getUnprefChild() {
		return (LCTree) super.getChild();
	}
	
	public boolean pisHead() {
		if (pisRoot()) {
			return (pgetLeft()==null)?true:false;
		} else {
			return (pgetParent()==null)?true:false;
		}
	}
	
	public void changeFlagPath() {
		changeFlag();
		if (pgetRight() != null) {
			pgetRight().changeFlagPath();
		}
		if (pgetLeft() != null) {
			pgetLeft().changeFlagPath();
		}
	}
	
	public void changeFlag() {
		if (revflag == 0) {
			revflag = 1;
			mark();
		} else {
			revflag = 0;
			unmark();
		}
	}
	
	public LCTree getNode(int x) {
		if (getKey() == x) {return this;}
		LCTree w = (LCTree) getChild();
		LCTree res = null;
		while ((w != null) && (res == null)) {
			res = w.getNode(x);
			w = w.getRight();
		}
		return res;
	}
	
	@Override
	public void drawTree(View v) {
		super.drawTree(v);
	}
	
	@Override
	public void drawEdges(View v) {
		if (state != INVISIBLE) {
			if (thread) {
				v.setColor(Color.red);
				if (pgetLeft() != null) {
					v.drawWideLine(x, y, pgetLeft().x, pgetLeft().y);
				}
				if (pgetRight() != null) {
					v.drawWideLine(x, y, pgetRight().x, pgetRight().y);
				}
				v.setColor(Color.black);
				if (getUnprefChild() != null) {
					v.drawLine(x, y, getUnprefChild().x, getUnprefChild().y);
				}
			} else {
				TreeNode w = getChild();
				while (w != null) {
					v.setColor(Color.black); // TODO maybe these lines would
												// make problems
					if (w == pgetLeft() || w == pgetRight()) {
						v.drawWideLine(x, y, w.x, w.y, 5.0f, Color.RED);
					} else {
						v.drawLine(x, y, w.x, w.y);
					}
					w.drawEdges(v);
					w = w.getRight();//getUnprefChild!!!
				}
			}
		}
	}
	
	public LCTree pgetLeft() {
		return (revflag==0)?prefLeft:prefRight;
	}	
	
	public LCTree pgetRight() {
		return (revflag==0)?prefRight:prefLeft;
	}	
	
	public void unpref(LCTree v) {
		if (v == null) {return;}
		if (v == pgetLeft()) {
			v.setRight(getUnprefChild());
			setChild(v);
			psetLeft(null);
			return;
		}
		if (v == pgetRight()) {
			v.setRight(getUnprefChild());
			setChild(v);
			psetRight(null);
		}
	}
	
	public void deleteChild(LCTree w) {
		if (w == getUnprefChild()) {
			setChild(getUnprefChild().getRight());
			w.setRight(null);
		} else {
			TreeNode v = getUnprefChild();
			while ((v != null) && (v.getRight() != w)) {
				v = v.getRight();
			}
			if (v != null) {
				v.setRight(w.getRight());
			}
			w.setRight(null);
		}
	}
	
	public void psetLeft(LCTree v) {
		if (v == null) {
			if (revflag==0) {
				prefLeft = v;
			} else {
				prefRight = v;
			}
			return;
		}
		deleteChild(v);
		if (revflag==0) {
			prefLeft = v;
		} else {
			prefRight = v;
		}
	}
	
	public void psetRight(LCTree v) {
		if (v == null) {
			if (revflag==0) {
				prefRight = v;
			} else {
				prefLeft = v;
			}	
			return;
		}
		deleteChild(v);
		if (revflag==0) {
			prefRight = v;
		} else {
			prefLeft = v;
		}
	}
	
	public LCTree pgetParent() {
		if (getParent() == null) {return null;}
		return (getParent().prefLeft == this || getParent().prefRight == this)?getParent():null;
	}
		
	public LCTree getParent() {
		return (LCTree) super.getParent();
	}
	
	public void psetParent(LCTree v) { //TODO
		if (v == null) {
			if (getParent() == null) {return;}
			if (getParent().getParent() != null) {
				setParent(getParent().getParent());
				return;
			}
			setParent(null);
		} else {
			setParent(v);
		}
	}
	
	public boolean isLeft() {
		return pgetParent() != null && pgetParent().pgetLeft() == this;
	}
	
	public boolean isRight() {
		return pgetParent() != null && pgetParent().pgetRight() == this;
	}
	

	/**
	 * removes edge between this and left; removes edge between newLeft and its
	 * parent; creates new edge between this and newLeft
	 */
	public void linkLeft(LCTree newLeft) {
		if (pgetLeft() != newLeft) {
			if (pgetLeft() != null) {
				// remove edge between this and left
				unlinkLeft();
			}
			if (newLeft != null) {
				if (newLeft.pgetParent() != null) {
					// remove edge between newLeft and its parent
					newLeft.unlinkParent();
				}
				// create new edge between this and newLeft
				newLeft.psetParent(this); 
			}
			psetLeft(newLeft);
		}
	}

	/**
	 * removes edge between this and left
	 */
	public void unlinkLeft() {
		pgetLeft().psetParent(null);
		psetLeft(null);
	}

	/**
	 * removes edge between this and right; removes edge between newRight and
	 * its parent; creates new edge between this and newRight
	 */
	public void linkRight(LCTree newRight) {
		if (pgetRight() != newRight) {
			if (pgetRight() != null) {
				// remove edge between this and right
				unlinkRight();
			}
			if (newRight != null) {
				if (newRight.pgetParent() != null) {
					// remove edge between newRight and its parent
					newRight.unlinkParent();
				}
				// create new edge between this and newRight
				newRight.psetParent(this);
			}
			psetRight(newRight);
		}
	}

	/**
	 * removes edge between this and right
	 */
	public void unlinkRight() {
		pgetRight().psetParent(null);
		psetRight(null);
	}

	private void unlinkParent() {
		if (isLeft()) {
			pgetParent().unlinkLeft();
		} else {
			pgetParent().unlinkRight();
		}
	}
	
	public boolean pisRoot() {
		return (pgetParent() == null)?true:false;
	}


}
