package algvis.linkcuttree;

import java.util.ArrayList;

import algvis.core.DataStructure;
import algvis.core.NodeColor;
import algvis.gui.VisPanel;
import algvis.gui.view.ClickListener;
import algvis.gui.view.View;


public class LinkCutDS extends DataStructure implements ClickListener {
	public static String adtName = "lct";
	public static String dsName = "lct";
	public ArrayList<LinkCutDSNode> tree;
	public ArrayList<LinkCutTree> lctree;
	int max, treeHeight;
	
	public LinkCutDSNode firstSelected = null;
	public LinkCutDSNode secondSelected = null;

	public LinkCutDS(VisPanel M) {
		super(M);
		max = 1;
		treeHeight = DataStructure.minsepy;
		tree = new ArrayList<LinkCutDSNode>();
		lctree = new ArrayList<LinkCutTree>();
		addElements(10);
	}
		
	public void addElements(int x) {
		LinkCutDSNode node;
		LinkCutTree l;
		for(int i=0; i<x; i++) {
			node = new LinkCutDSNode(this, max+i);
			tree.add(node);
			
			l = new LinkCutTree(M,this);
			l.setRoot(new SplayTreeM(M, l));
			l.getRoot().setRoot(new SplayNodeM(l.getRoot(), max+i));
			lctree.add(l);
		}
		max += x;
		reposition();
	}
	
	public void link(int x, int y) {
		int indexx = 0, indexy = 0;;
		LinkCutDSNode N1 = null, N2 = null, M;
		for(int i=0; i<tree.size(); i++) {
			M = tree.get(i).getNode(x);
			if (M != null) {N1 = M; indexx = i;}
			M = tree.get(i).getNode(y);
			if (M != null) {N2 = M; indexy = i;}
		}
		SplayNodeM S1, S2;
		S1 = lctree.get(indexx).find(x);
		S2 = lctree.get(indexy).find(y);
		S1.setColor(NodeColor.NORMAL);
		S2.setColor(NodeColor.NORMAL);
		start(new Link(this, N1, N2, indexx, indexy, S1, S2));
		tree.remove(indexx);
		lctree.remove(indexx);
		calcHeight();
		reposition();
	}
	
	public void cut(int x) {
		LinkCutDSNode N = null, M;
		for(int i=0; i<tree.size(); i++) {
			M = tree.get(i).getNode(x);
			if (M != null) {N = M;}
		}
		start(new Cut(this, N));
		tree.add(N);
		calcHeight();
		reposition();
	}

	@Override
	public void clear() {
		max = 1;
		treeHeight = DataStructure.minsepy;
		tree = new ArrayList<LinkCutDSNode>();
		lctree = new ArrayList<LinkCutTree>();
		addElements(10);
		setStats();
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
	
	public void reposition() {
		y1 = y2 = 0;
		if (tree != null) {
			int ey2 = -9999999;
			int ey1 = 9999999;
			for (int i = 0; i < tree.size(); i++) {
				tree.get(i).reposition();
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
			int shift = -tree.get(0).leftw;
			x1 = shift;
			for (int i = 0; i < tree.size(); i++) {
				shift += tree.get(i).leftw;
				tree.get(i).shift(shift, 0);
				shift += tree.get(i).rightw;
			}
			x2 = shift;
//			M.screen.V.setBounds(x1, y1, x2, y2);
		}
		
		if (lctree != null) {
			int ey2 = -9999999;
			int ey1 = 9999999;
			for (int i = 0; i < lctree.size(); i++) {
				y1 = y2 = 0;
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
			int shift = -lctree.get(0).getRoot().getRoot().leftw;
			x1 = shift;
			for (int i = 0; i < lctree.size(); i++) {
				shift += lctree.get(i).getRoot().getRoot().leftw;
				lctree.get(i).getRoot().getRoot().shift(shift, 0);
				shift += lctree.get(i).getRoot().getRoot().rightw;
			}
			x2 = shift;
		}
		M.screen.V.setBounds(x1, y1, x2, y2);

		/*		if (lctree != null) {
		for (int i = 0; i < lctree.size(); i++) {
			tree.get(i).reposition();
		}
	}*/

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


}
