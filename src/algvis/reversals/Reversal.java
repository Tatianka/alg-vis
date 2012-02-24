package algvis.reversals;

import java.util.Random;

import algvis.core.ClickListener;
import algvis.core.View;
import algvis.core.VisPanel;
import algvis.splaytree.SplayTree;

public class Reversal extends SplayTree implements ClickListener {
	public static String dsName = "reversal";
	ReversalNode root = null, left = null, right = null;
	public int max = 0;
	ReversalNode rootL = null, rootR = null;
	int[] a;
	
	ReversalNode firstSelected = null, secondSelected = null;

	public Reversal(VisPanel M) {
		super(M);
		setTree();
		setArray();
	}
	
	public void reverse(int x, int y) {
		if (x>y) {
			int tmp = x; x=y; y=tmp;
		}
		start(new Reverse(this, x, y));
//		reset();
	}
	
	public void insert() {
		start(new ReversalInsert(this));
		max+=10;
	}
	
	public void find(int x) {
		start(new ReversalFind(this, x));
	}
	
	@Override
	public void random(int n) {
		Random g = new Random(System.currentTimeMillis());
		boolean p = M.pause;
		M.pause = false;
		for (int i = 0; i < n; ++i) {
			int a = g.nextInt(max-1)+1, b = g.nextInt(max-1)+1;
			reverse(a,b);
		}
		M.pause = p;
	}
	
	public void setTree() {
		ReversalNode v0 = new ReversalNode(this, 0);
		ReversalNode v1 = new ReversalNode(this, 1);
		ReversalNode v2 = new ReversalNode(this, 2);
		ReversalNode v3 = new ReversalNode(this, 3);
		ReversalNode v4 = new ReversalNode(this, 4);
		ReversalNode v5 = new ReversalNode(this, 5);
		ReversalNode v6 = new ReversalNode(this, 6);
		ReversalNode v7 = new ReversalNode(this, 7);
		ReversalNode v8 = new ReversalNode(this, 8);
		ReversalNode v9 = new ReversalNode(this, 9);
		ReversalNode v10 = new ReversalNode(this, 10);
		ReversalNode v11 = new ReversalNode(this, 11);
		setRoot(v5);
		v5.setLeft(v3);
		v3.setParent(v5);
		v5.setRight(v8);
		v8.setParent(v5);
		v3.setLeft(v1);
		v1.setParent(v3);
		v1.setLeft(v0);
		v0.setParent(v1);
		v1.setRight(v2);
		v2.setParent(v1);
		v3.setRight(v4);
		v4.setParent(v3);
		v8.setLeft(v6);
		v6.setParent(v8);
		v6.setRight(v7);
		v7.setParent(v6);
		v8.setRight(v10);
		v10.setParent(v8);
		v10.setLeft(v9);
		v9.setParent(v10);
		v10.setRight(v11);
		v11.setParent(v10);
		reposition();
		getRoot().calcTree();
		max = 11;
	}

	public void setArray() {
		a = new int[10];
		a[0] = 1;
		a[1] = 2;
		a[2] = 3;
		a[3] = 4;
		a[4] = 5;
		a[5] = 6;
		a[6] = 7;
		a[7] = 8;
		a[8] = 9;
		a[9] = 10;
	}
	
	@Override
	public String getName() {
		return "reversal";
	}
	
	public ReversalNode getRoot() {
		return root;
	}

	public void setRoot(ReversalNode root) {
		this.root = root;
	}
	
	public void drawArray() {
		
	}
	
	@Override
	public void draw(View V) {
		if (getRoot() != null) {
			getRoot().moveTree();
			getRoot().drawTree(V);
		}
		if (rootL != null) {
			rootL.moveTree();
			rootL.drawTree(V);
		}
		if (rootR != null) {
			rootR.moveTree();
			rootR.drawTree(V);
		}
		drawArray();
		super.draw(V);
	/*	if (L != null) {
			L.draw(V);
		}
		if (R != null) {
			R.draw(V);
		}*/
	}
	
	@Override
	public void clear() {
		super.clear();
		setTree();
		setArray();
//		L = null;
	//	R = null;
		rootL = null;
		rootR = null;
	}
	
	public void reset() {
		if (firstSelected != null) {
			firstSelected.unmark();			
		}
		firstSelected = null;
		if (secondSelected != null) {
			secondSelected.unmark();
		}
		secondSelected = null;
	}
		
	@Override
	public void reposition() {
		super.reposition();
		if (rootL != null) {
			rootL.repositionL(getRoot());
		}
		if (rootR != null) {
			rootR.repositionR(getRoot());
		}
	/*	//if (L != null) {
		if (rootL != null) {
			x1 = -20; 
			x2 = y1 = y2 = 0;
			if (getRoot() != null) {
				getRoot().reposition();
			}
			M.screen.V.setBounds(x1, y1, x2, y2);			
		}
		//if (R == null) {
		if (rootR == null) {
			x1 = 25;
			x2 = y1 = y2 = 0;
			if (getRoot() != null) {
				getRoot().reposition();
			}
			M.screen.V.setBounds(x1, y1, x2, y2);
		}*/
	}
	
	public boolean isSelected(ReversalNode u) {
		if ((u == firstSelected) || (u == secondSelected))
			return true;
		else
			return false;
	}
	
	public int order(ReversalNode w) {
		ReversalNode u = w;
		int count = 0;
		if (u.getLeft() != null) {
			count = u.getLeft().size;
		}
		/// i want different search - output = number of that node
		boolean ok;
		while (u != getRoot()) {
			ok = u.isLeft();
			u = u.getParent();
			if (! ok) {
				if (u.getLeft() != null) {
					count += u.getLeft().size + 1;
				} else {
					count++;
				}
			}
		}
		return count;
	}
	
	@Override
	public void mouseClicked(int x, int y) {
		ReversalNode w = (ReversalNode) getRoot().find(x, y);
		if (w != null) {
			if (isSelected(w)) {
				w.unmark();
				if (w == secondSelected) {
					secondSelected = null;
				} else if (w == firstSelected) {
					firstSelected = secondSelected;
					secondSelected = null;
				}
			} else {
				w.mark();
				if (firstSelected == null) {
					firstSelected = w;
				} else if (secondSelected == null) {
					secondSelected = w;
				} else {
					firstSelected.unmark();
					firstSelected = secondSelected;
					secondSelected = w;
				}
			}
		}
	}
	
}
