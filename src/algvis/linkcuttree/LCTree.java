package algvis.linkcuttree;

import java.awt.Color;

import algvis.core.TreeNode;
import algvis.gui.view.View;

public class LCTree extends TreeNode {
	LinkCutDS D;
	LCTree prefLeft = null, prefRight = null;

	public LCTree(LinkCutDS D, int key) {
		super(D, key);
		this.D = D;
	}
	
	public LCTree getNode(int x) {
		if (getKey() == x) {return this;}
		LCTree w = (LCTree) getChild();
		LCTree res = null;
		while ((w != null) && (res == null)) {
			res = w.getNode(x);
			w = (LCTree) w.getRight();
		}
		return res;
	}
	
	@Override
	public void drawEdges(View v) {
		if (state != INVISIBLE) {
			if (thread) {
				v.setColor(Color.red); // TODO
				if (getChild() != null) {
					if (prefLeft == getChild()) {
						v.drawLine(x, y, getChild().x, getChild().y);
					} else {
						v.drawDashedLine(x, y, getChild().x, getChild().y);							
					}
				}
				v.setColor(Color.black);
			} else {
				TreeNode w = getChild();
				while (w != null) {
					v.setColor(Color.black); // TODO maybe these lines would
												// make problems
					if (w == prefLeft || w == prefRight) {
						v.drawWideLine(x, y, w.x, w.y, 5.0f, Color.RED);
					} else {
						v.drawLine(x, y, w.x, w.y);
					}
					w.drawEdges(v);
					w = w.getRight();
				}
			}
		}
	}
	
	public LCTree pgetLeft() {
		return prefLeft;
	}	
	
	public LCTree pgetRight() {
		return prefRight;
	}	
	
	public void psetLeft(LCTree v) {
		prefLeft = v;
	}
	
	public void psetRight(LCTree v) {
		prefRight = v;
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
			if (pgetParent() == null) {return;}
			if (pgetParent().pgetLeft() == this) {
				pgetParent().psetLeft(null);
			} else {
				pgetParent().psetRight(null);
			}
		} else {
			setParent(v);
		}
	}
	
	public boolean isLeft() {
		return pgetParent() != null && pgetParent().pgetLeft() == this;
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
				newLeft.psetParent(this); //TODO
			}
			psetLeft(newLeft);
			addChild(newLeft);
		}
	}

	/**
	 * removes edge between this and left
	 */
	public void unlinkLeft() {
		deleteChild(pgetLeft());
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
			addChild(newRight); //TODO switch left & right
			exchange();
		}
	}

	/**
	 * removes edge between this and right
	 */
	public void unlinkRight() {
		deleteChild(pgetRight());
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
	
	public void exchange() {
		if (prefLeft == null || prefRight == null) {
			return;
		}
		LCTree w = (LCTree) getChild();
		while (w.getRight() != prefLeft) {
			w = (LCTree) w.getRight();
		}
		w.setRight(prefRight);
		w = (LCTree) prefRight.getRight();
		prefRight.setRight(prefLeft.getRight());
		prefLeft.setRight(w);
		this.setChild(prefLeft);
	}
	
	public boolean pisRoot() {
		return (pgetParent() == null)?true:false;
	}


}
