package algvis.linkcuttree;

import java.util.ArrayList;

import algvis.core.DataStructure;
import algvis.gui.VisPanel;
import algvis.gui.view.ClickListener;
import algvis.gui.view.View;

public class LinkCutDS extends DataStructure implements ClickListener {
	public static String adtName = "lct";
	public static String dsName = "lct";
	public ArrayList<LinkCutDSNode> tree;
	public ArrayList<LCTree> lctree;
	int max, treeHeight;
	
	public LinkCutDSNode firstSelected = null;
	public LinkCutDSNode secondSelected = null;

	public LinkCutDS(VisPanel M) {
		super(M);
		addNodes(3);
		max = 1;
		treeHeight = DataStructure.minsepy;
		tree = new ArrayList<LinkCutDSNode>();
		lctree = new ArrayList<LCTree>();
		addElements(10);
	}
		
	public void addElements(int x) {
		LinkCutDSNode node;
		LCTree l;
		for(int i=0; i<x; i++) {
			node = new LinkCutDSNode(this, max+i);
			tree.add(node);

			l = new LCTree(this, max+i);
			lctree.add(l);
		}
		max += x;
		reposition();
	}
	
	public void link(int x, int y) {
		int indexx = -1, indexy = -1;
		LinkCutDSNode N1 = null, N2 = null, M;
		for(int i=0; i<tree.size(); i++) {
			M = tree.get(i).getNode(x);
			if (M != null) {N1 = M; indexx = i;}
			M = tree.get(i).getNode(y);
			if (M != null) {N2 = M; indexy = i;}
		}
		LCTree S1 = lctree.get(indexx).getNode(x);
		LCTree S2 = lctree.get(indexy).getNode(y);
		start(new Link(this, N1, N2, indexx, indexy, S1, S2));
		calcHeight();
		reposition();
	}
	
	public void cut(int x) {
		LinkCutDSNode N = null, M;
		int index = 0;
		for(int i=0; i<tree.size(); i++) {
			M = tree.get(i).getNode(x);
			if (M != null) {N = M; index = i; break;}
		}
		LCTree L = lctree.get(index).getNode(x);
		start(new Cut(this, N, L, index));
		calcHeight();
		reposition();
	}

	@Override
	public void clear() {
		max = 1;
		treeHeight = DataStructure.minsepy;
		tree = new ArrayList<LinkCutDSNode>();
		lctree = new ArrayList<LCTree>();
		setStats();
		addElements(10);
	}

	@Override
	public void draw(View V) {
		if (tree != null) {
			for (int i = 0; i < tree.size(); i++) {
				tree.get(i).moveTree();
				tree.get(i).drawTree(V);
			}
		}
		if (lctree != null) {
			for (int i = 0; i < lctree.size(); i++) {
				lctree.get(i).moveTree();
				lctree.get(i).drawTree(V);
				lctree.get(i).draw(V);
			}
		}
/*		if (v != null) {
			if (isSelected(v) && (v.marked == false)) {
				// v.mark(); // TODO
			}
			if (!isSelected(v) && (v.marked != false)) {
				// v.unmark(); // TODO are these lines needed?
			}
			v.move();
			v.draw(V);
		}*/
		if (getW1() != null && getW1().pgetParent() != null) {
			V.drawWideLine(getW1().x, getW1().y, getW1().pgetParent().x, getW1()
					.pgetParent().y);
		}
		if (getW2() != null && getW2().pgetParent() != null) {
			V.drawWideLine(getW2().x, getW2().y, getW2().pgetParent().x, getW2()
					.pgetParent().y);
		}
		if (getVV() != null) {
			getVV().move();
			getVV().draw(V);
		}

	}

	@Override
	public String getName() {
		return "lct";
	}

	@Override
	public void insert(int x) {
	}

	@Override
	public String stats() {
		return "";
	}
	
	private int MAX(int a, int b) {
		return a>b?a:b;
	}
	
	public void reposition() {
		y1 = y2 = 0;
		if (tree != null) {
			int ey2 = -9999999;
			int ey1 = 9999999;
			for (int i = 0; i < tree.size(); i++) {
				tree.get(i).reposition();
				lctree.get(i).reposition();
				if (y1 < ey1) {
					ey1 = y1;
				}
				if (y2 > ey2) {
					ey2 = y2;
				}
			}
			y1 = ey1;
			y2 = ey2;

			x1 = x2 = 0;
			int shift = MAX(-tree.get(0).leftw, -lctree.get(0).leftw);
			x1 = shift;
			for (int i = 0; i < tree.size(); i++) {
				shift += MAX(tree.get(i).leftw, lctree.get(i).leftw);
				tree.get(i).shift(shift, 0);
				lctree.get(i).shift(shift, DataStructure.rooty+treeHeight/*-lctree.get(i).toy*/);
				shift += MAX(tree.get(i).rightw, lctree.get(i).rightw);
			}
			x2 = shift;
			M.screen.V.setBounds(x1, y1, x2, y2);
		}
		
	}
		
	public void calcHeight() {
		int m = 0;
		for(int i=0; i<tree.size(); i++) {
			if (tree.get(i).height > m) {
				tree.get(i).calcTree();
				m = tree.get(i).height;
			}
		}
		treeHeight = m*DataStructure.minsepy;
	}
		
	public boolean isSelected(LinkCutDSNode u) {
		if ((u == firstSelected) || (u == secondSelected))
			return true;
		else
			return false;
	}
	
	@Override
	public void mouseClicked(int x, int y) {
		LinkCutDSNode u = null;
		int i = 0;
		int j = tree.size();
		do {
			u = (LinkCutDSNode) tree.get(i).find(x, y);
			i++;
		} while ((u == null) && (i < j));
		if (u != null) {
			if (isSelected(u)) {
				scenario.enableAdding(false);
				u.unmark();
				scenario.enableAdding(true);
				if (u == secondSelected) {
					secondSelected = null;
				} else if (u == firstSelected) {
					firstSelected = secondSelected;
					secondSelected = null;
				}
			} else {
				scenario.enableAdding(false);
				u.mark();
				scenario.enableAdding(true);
				if (firstSelected == null) {
					firstSelected = u;
				} else if (secondSelected == null) {
					secondSelected = u;
				} else {
					scenario.enableAdding(false);
					firstSelected.unmark();
					scenario.enableAdding(true);
					firstSelected = secondSelected;
					secondSelected = u;
				}
			}
		}
	}
	
	protected void exchange(LCTree u, LCTree v) {
		LCTree w = u.getParent().getUnprefChild();
		if (w == u) {
			u.getParent().setChild(v);
			v.setParent(u.getParent());
			u.setParent(null);
			return;
		}
		while (w.getRight() != u) {
			w = w.getRight();
		}
		w.setRight(v);
		v.setRight(u.getRight());
		u.setRight(null);
		v.setParent(u.getParent());
		u.setParent(null);		
	}

	protected void leftrot(LCTree v, int index) {
		LCTree u = v.pgetParent();
		if (v.pgetLeft() == null) {
			u.unlinkRight();
		} else {
			u.linkRight(v.pgetLeft());
		}
		if (u.pisRoot()) {
			if (u.isRoot()) {
				lctree.set(index,v);
			} else {
				exchange(u,v);
			}
		} else {
			if (u.isLeft()) {
				u.pgetParent().linkLeft(v);
			} else {
				u.pgetParent().linkRight(v);
			}
		}
		v.linkLeft(u);
	}

	protected void rightrot(LCTree v, int index) {
		LCTree u = v.pgetParent();
		if (v.pgetRight() == null) {
			u.unlinkLeft();
		} else {
			u.linkLeft(v.pgetRight());
		}
		if (u.pisRoot()) {
			if (u.isRoot()) {
				lctree.set(index,v);
			} else {
				exchange(u,v);
			}
		} else {
			if (u.isLeft()) {
				u.pgetParent().linkLeft(v);
			} else {
				u.pgetParent().linkRight(v);
			}
		}
		v.linkRight(u);
	}

	/**
	 * Rotation is specified by a single vertex v; if v is a left child of its
	 * parent, rotate right, if it is a right child, rotate lef This method also
	 * recalculates positions of all nodes and their statistics.
	 */
	public void rotate(LCTree v, int index) {
		if (v.isLeft()) {
			rightrot(v,index);
		} else {
			leftrot(v,index);
		}
		if (lctree.get(index)!=null) {
			reposition();
		}
		if (v.pgetLeft() != null) {
			v.pgetLeft().calc();
		}
		if (v.pgetRight() != null) {
			v.pgetRight().calc();
		}
		v.calc();
	}
	
	public LCTree getVV() {
		return (LCTree) getNode(0);
	}

	public void setVV(LCTree vv) {
		setNode(0, vv, true);
	}

	public LCTree getW1() {
		return (LCTree) getNode(1);
	}

	public void setW1(LCTree w1) {
		setNode(1, w1, true);
	}

	public LCTree getW2() {
		return (LCTree) getNode(2);
	}

	public void setW2(LCTree w2) {
		setNode(2, w2, false);
	}



}
