package algvis.reversals;

import algvis.splaytree.SplayAlg;

public class ReversalAlg extends SplayAlg {

	public ReversalAlg(Reversal T, int x) {
		super(T, x);
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
