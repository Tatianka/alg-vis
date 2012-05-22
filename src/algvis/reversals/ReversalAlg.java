package algvis.reversals;

import algvis.core.Algorithm;
import algvis.core.NodeColor;
import algvis.splaytree.SplayNode;

public class ReversalAlg extends Algorithm {
	Reversal T;
	public ReversalNode s, v;
	public int K;

	public ReversalAlg(Reversal T, int x, int y) {
		super(T);
		this.T = T;
		if (T.getRoot() != null) {
			T.v = s = new ReversalNode(T, K = x);
			s.setColor(NodeColor.FIND);
		}		
	}
	
	public ReversalAlg(Reversal T, int x) {
		super(T);
		this.T = T;
		if (T.getRoot() != null) {
			T.v = s = new ReversalNode(T, K = x);
			s.setColor(NodeColor.FIND);
		}		
	}

	public ReversalAlg(Reversal T) {
		super(T);
		this.T = T;
		if (T.getRoot() != null) {
			T.v = s = new ReversalNode(T, K = T.max+1);
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
				addStep("rev-find-downl", ll+1, K);
				w = (ReversalNode) w.getLeft();
				s.pointTo(w);
				mysuspend();
				s.noArrow();
				s.goTo(w);
				mysuspend();
			} else {
				if (K == ll+1) {
					break;
				}
				addStep("rev-find-downr", ll+1, K);
				w =  (ReversalNode) w.getRight();
				s.pointTo(w);
				mysuspend();
				s.noArrow();
				seen = ll+1;
				s.goTo(w);
				mysuspend();
			}
		}
		w.setColor(NodeColor.FIND);
		s.goTo(w);
		T.v = null;
		addStep("rev-found", K);
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
