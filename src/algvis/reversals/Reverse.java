package algvis.reversals;

import algvis.core.NodeColor;

public class Reverse extends ReversalAlg {
	Reversal T;
	int from, to;

	public Reverse(Reversal T, int x, int y) {
		super(T, x, y);
		from = x;
		to = y;
		this.T = T;
		setHeader("reverse");
	}
	
	@Override
	public void run() {
		if (from == to) {
			addStep("reverse-equal");
			return;
		}
		if (to > T.max-1) {
			addStep("toobig");
			return;
		}
		ReversalNode w = find(from-1);
		splay(w);
		T.reposition();
		mysuspend();
		
		T.rootL = T.getRoot();
		T.setRoot(T.getRoot().getRight());
		T.rootL.setRight(null);
		T.rootL.calcTree();
		T.getRoot().setParent(null);
		T.goToPartOfArray(1, from-1, -20);
		
		T.reposition();
		addStep("revl");
		mysuspend();
		
		T.v = s = new ReversalNode(T, K = to);
		s.setColor(NodeColor.FIND);
		w = find(to-from+1);
		splay(w);
		T.reposition();
		mysuspend();
		T.rootR = T.getRoot();
		T.setRoot(T.getRoot().getLeft());
		T.getRoot().setParent(null);
		T.rootR.setLeft(null);
		T.rootR.calcTree();
		T.goToPartOfArray(to+1, T.max-1, 20);

		T.reposition();
		addStep("revr");
		mysuspend();
		/// let's stick it together
		T.getRoot().changeFlag();
		T.reverseArray(from, to);
		addStep("rev");
		mysuspend();

		T.rootR.setLeft(T.getRoot());
		T.getRoot().setParent(T.rootR);
		T.setRoot(T.rootR);
		T.rootR = null;
		T.getRoot().calcTree();
		T.goToPartOfArray(to+1, T.max-1, -20);
		
		T.reposition();
		addStep("revstickr");
		mysuspend();
		
		T.rootL.setRight(T.getRoot());
		T.getRoot().setParent(T.rootL);
		T.setRoot(T.rootL);
		T.rootL = null;
		T.getRoot().calcTree();
		T.goToPartOfArray(1, from-1, 20);
		
		T.reposition();
		addStep("revstickl");
		mysuspend();
	}

}

