package algvis.reversals;

import algvis.splaytree.SplayAlg;

public class ReversalAlg extends SplayAlg {

	public ReversalAlg(Reversal T, int x) {
		super(T, x);
	}
	
	// I just need splay(inf) in subtree T1
	public ReversalNode splayINF(ReversalNode newRoot) {
		ReversalNode w = (ReversalNode) find(100000000);
		while (w != newRoot) {
			if (w.parent == newRoot) {
				addStep("splayroot");
				w.setArc(w.parent);
				mysuspend();
				w.noArc();
				T.rotate(w);
			} else {
				if (w.isLeft() == w.parent.isLeft()) {
					if (w.isLeft()) {
						addStep("splayzigzigleft");
					} else {
						addStep("splayzigzigright");
					}
					w.parent.setArc(w.parent.parent);
					mysuspend();
					w.parent.noArc();
					T.rotate(w.parent);
					w.setArc(w.parent);
					mysuspend();
					w.noArc();
					T.rotate(w);
				} else {
					if (!w.isLeft()) {
						addStep("splayzigzagleft");
					} else {
						addStep("splayzigzagright");
					}
					w.setArc(w.parent);
					mysuspend();
					w.noArc();
					T.rotate(w);
					w.setArc(w.parent);
					mysuspend();
					w.noArc();
					T.rotate(w);
				}
			}
		}
		if (T.root == newRoot) {
			T.root = w;
		}
		return w;		
	}
	
	public void concat(ReversalNode u, ReversalNode v) {
		v = splayINF(v);
		v.right = u;
		if (u.isLeft()) {
			u.parent.left = null;
		} else {
			u.parent.right = null;
		}
		u.parent = v;
	}

}
