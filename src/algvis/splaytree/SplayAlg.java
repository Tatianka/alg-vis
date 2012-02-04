package algvis.splaytree;

import algvis.core.Algorithm;
import algvis.core.NodeColor;

public class SplayAlg extends Algorithm {
	public SplayTree T;
	public SplayNode s, v;
	public int K;

	public SplayAlg(SplayTree T, int x) {
		super(T);
		this.T = T;
		if (T.root != null) {
			T.v = s = new SplayNode(T, K = x);
			s.setColor(NodeColor.FIND);
		}
	}

	public SplayNode find(int K) {
		SplayNode w = (SplayNode) T.root;
		s.goTo(w);
		addStep("splaystart");
		mysuspend();
		while (true) {
			if (w.key == K) {
				break;
			} else if (w.key < K) { // right
				if (w.getRight() == null) {
					break;
				}
				w = w.getRight();
				addStep("bstfindright", K, w.key);
			} else { // left
				if (w.getLeft() == null) {
					break;
				}
				w = w.getLeft();
				addStep("bstfindleft", K, w.key);
			}
			s.goTo(w);
			mysuspend();
		}
		w.setColor(NodeColor.FIND);
		T.v = null;
		addStep("splayfound");
		mysuspend();
		return w;
	}

	public void splay(SplayNode w) {
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
		T.root = w;
	}
}
