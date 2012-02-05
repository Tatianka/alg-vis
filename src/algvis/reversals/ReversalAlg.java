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
	/*	if (K > w.snum) {*/ if (K > w.size-1) {
			addStep("toobig");
			mysuspend();
			return null;
		}
		s.goTo(w);
		addStep("splaystart");
		mysuspend();
		int l;
		while (true) {
			if (w.revflag) {
				w.flagDown();
			}
			if (w.getLeft() != null) {
				//l = w.getLeft().snum + seen;
				l = w.getLeft().size + seen;
			} else {
				l = seen;
			}
			if (K <= l) {
				w = (ReversalNode) w.getLeft();
				s.goTo(w);
				mysuspend();
			} else {
				if (K == l+1) {
					break;
				}
				w =  (ReversalNode) w.getRight();
				seen = l+1;
				s.goTo(w);
				mysuspend();
			}
		}
		w.setColor(NodeColor.FIND);
		s.goTo(w);
		T.v = null;
		addStep("found");
		mysuspend();
		w.setColor(NodeColor.NORMAL);
		s.goDown();
		return w;
	}

	
	public void splay(ReversalNode w) {
		while (!w.isRoot()) {
			if (w == null) {
				System.out.println("trololo");
			}
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

	// I just need splay(inf) in subtree T1
	public ReversalNode splayINF(ReversalNode newRoot) {
		ReversalNode w = (ReversalNode) find(100000000);
		// rather do with subtrees???
	
		while (!w.isRoot()) {
			T.w1 = w;
			T.w2 = w.getParent();
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
					T.w2 = w.getParent().getParent();
					T.rotate(w.getParent());
					w.setArc(w.getParent());
					mysuspend();
					w.noArc();
					T.w1 = w.getParent();
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
		if (T.root == newRoot) { T.root = w;}
		return w;
	}
	
	public void concat(ReversalNode u, ReversalNode v) {
		v = splayINF(v);
	/*	v.right = u;
		if (u.isLeft()) {
			u.parent.left = null;
		} else {
			u.parent.right = null;
		}
		u.parent = v;*/
	}

}
