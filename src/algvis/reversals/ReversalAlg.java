package algvis.reversals;

import algvis.core.Algorithm;
import algvis.core.NodeColor;
import algvis.splaytree.SplayNode;

public class ReversalAlg extends Algorithm {
	Reversal T;
	public SplayNode s, v;
	public int K;

	public ReversalAlg(Reversal T, int x, int y) {
		super(T);
		this.T = T;
		if (T.getRoot() != null) {
			T.v = s = new SplayNode(T, K = x);
			s.setColor(NodeColor.FIND);
		}		
	}
	
	public ReversalAlg(Reversal T, int x) {
		super(T);
		this.T = T;
		if (T.getRoot() != null) {
			T.v = s = new SplayNode(T, K = x);
			s.setColor(NodeColor.FIND);
		}		
	}

	public ReversalAlg(Reversal T) {
		super(T);
		this.T = T;
		if (T.getRoot() != null) {
			T.v = s = new SplayNode(T, K = T.max+1);
			s.setColor(NodeColor.FIND);
		}			
	}
	
	public ReversalNode find(int K) {
		ReversalNode w = T.getRoot();
		int seen = -1;
		if (K > w.size) {
			addStep("toobig");
			mysuspend();
			return null;
		}
		s.goTo(w);
		int col;
		if (w.getLeft() != null) {
			col = w.getLeft().size;
		} else {
			col = 0;
		}
		T.setColorOfNodeArray(col, NodeColor.FIND);
		addStep("rev-find-start",K);
		mysuspend();
		int ll;
		while (true) {
			if (w.revflag) {
				ReversalNode r = w.getRight(), l = w.getLeft();
				if (l != null) {
					if (r != null) {
						l.pointTo(r);
						r.pointTo(l);
					} else {
						l.pointInDir(0);
					}
				} else {
					if (r!=null) {
						r.pointInDir(180);
					}
				}
				addStep("rev-flagdown");
				mysuspend();
				if (l != null) {
					l.noArrow();
				}
				if (r != null) {
					r.noArrow();			
				}
				w.flagDown();
				T.reposition();
				s.goTo(w);
				mysuspend();
			}
			if (w.getLeft() != null) {
				ll = w.getLeft().size + seen;
			} else {
				ll = seen;
			}
			if (K <= ll) {
				if (seen == -1) {
					addStep("rev-find-first-downl",w.getLeft().size-1, ll+1,K);
				} else {
					addStep("rev-find-downl",w.getLeft().size, seen+1, ll+1, K);
				}
				w = (ReversalNode) w.getLeft();
				s.pointTo(w);
				mysuspend();
				s.noArrow();
				s.goTo(w);
				T.setColorOfNodeArray(col, NodeColor.NORMAL);
				col--;
				T.setColorOfNodeArray(col, NodeColor.FIND);
				mysuspend();
			} else {
				if (K == ll+1) {
					T.setColorOfNodeArray(col, NodeColor.NORMAL);
					break;
				}
				int leftSize = (w.getLeft() == null)?0:w.getLeft().size;
				if (seen == -1) {
					if (leftSize>0) {leftSize--;}
					addStep("rev-find-first-downr",leftSize, ll+1,K);
				} else {
					addStep("rev-find-downr",leftSize, seen+1, ll+1, K);
				}
				w =  (ReversalNode) w.getRight();
				s.pointTo(w);
				mysuspend();
				s.noArrow();
				seen = ll+1;
				s.goTo(w);
				T.setColorOfNodeArray(col, NodeColor.NORMAL);
				col++;
				T.setColorOfNodeArray(col, NodeColor.FIND);
				mysuspend();
			}
		}
		w.setColor(NodeColor.FIND);
		s.goTo(w);
		T.v = null;
		int leftSize = (w.getLeft() == null)?0:w.getLeft().size;
		if (seen == -1) {
			addStep("rev-first-found", leftSize, K);
		} else {
			addStep("rev-found", leftSize, seen+1, K);
		}
		mysuspend();
		w.setColor(NodeColor.NORMAL);
		s.goDown();
		return w;
	}

	
	public void splay(ReversalNode w) {
		while (!w.isRoot()) {
			T.w1 = (SplayNode) w;
			T.w2 = (SplayNode) w.getParent();
			if (w.getParent().isRoot()) {
				addStep("splayroot");
				w.setArc(w.getParent());
				mysuspend();
				w.noArc();
				T.rotate(w);
			} else {
				if (w.isLeft() == w.getParent().isLeft()) {
					if (w.isLeft()) {
						addStep("splayzigzigleft");
					} else {
						addStep("splayzigzigright");
					}
					w.getParent().setArc(w.getParent().getParent());
					mysuspend();
					w.getParent().noArc();
					T.w2 = (SplayNode) w.getParent().getParent();
					T.rotate(w.getParent());
					w.setArc(w.getParent());
					mysuspend();
					w.noArc();
					T.w1 = (SplayNode) w.getParent();
					T.rotate(w);
					mysuspend();
				} else {
					if (!w.isLeft()) {
						addStep("splayzigzagleft");
					} else {
						addStep("splayzigzagright");
					}
					w.setArc(w.getParent());
					mysuspend();
					w.noArc();
					T.rotate(w);
					w.setArc(w.getParent());
					mysuspend();
					w.noArc();
					T.w1 = w.getParent();
					T.rotate(w);
					mysuspend();
				}
			}
		}
		T.w1 = null;
		T.w2 = null;
		T.setRoot(w);
	}

}
