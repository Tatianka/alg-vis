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
		setHeader("reversal");
	}
	
	@Override
	public void run() {
		if (to > T.max-1) {
			addStep("toobig");
			return;
		}
		addStep("revfindl", from);
		T.setColorOfNodeArray(from-1, NodeColor.FOUND);
		mysuspend();
		ReversalNode w = find(from-1);
		addStep("splayfound");
		mysuspend();
		w.setColor(NodeColor.FOUND);
		splay(w);
		T.reposition();
		mysuspend();
		w.setColor(NodeColor.NORMAL);
		T.setColorOfNodeArray(from-1, NodeColor.NORMAL);
		
		T.rootL = T.getRoot();
		T.setRoot(T.getRoot().getRight());
		T.rootL.setRight(null);
		T.rootL.calcTree();
		T.getRoot().setParent(null);
		
		T.rootL.goTo(T.rootL.x-20, T.rootL.y);
		T.reposition();
		T.goToPartOfArray(1, from-1, -20);
		addStep("revft", 0, from-1);
		mysuspend();
		
		addStep("revfindr", to);
		T.setColorOfNodeArray(to-from+1, NodeColor.FOUND);
		mysuspend();
		T.v = s = new ReversalNode(T, K = to);
		s.setColor(NodeColor.FIND);
		w = find(to-from+1);
		addStep("splayfound");
		mysuspend();
		w.setColor(NodeColor.FOUND);
		splay(w);
		T.reposition();
		mysuspend();
		w.setColor(NodeColor.NORMAL);
		T.setColorOfNodeArray(to-from+1, NodeColor.NORMAL);

		T.rootR = T.getRoot();
		T.setRoot(T.getRoot().getLeft());
		T.getRoot().setParent(null);
		T.rootR.setLeft(null);
		T.rootR.calcTree();

		T.rootR.goTo(T.rootR.x+20, T.rootR.y);
		T.reposition();
		T.goToPartOfArray(to+1, T.max-1, 20);
		addStep("revft", to+1, T.max);
		mysuspend();
		/// let's stick it together
		if (T.getRoot().revflag) {
			addStep("rev-rev-notice");
		} else {
			addStep("rev-reverse");
		}
		mysuspend();
		T.getRoot().changeFlag();
		T.reverseArray(from, to);
		mysuspend();
		
		addStep("rev-together");
		mysuspend();
		addStep("revstickr");
		T.getRoot().pointTo(T.rootR);
		mysuspend();
		T.getRoot().noArrow();
		
		T.rootR.goTo(T.rootR.x-20, T.rootR.y);
		T.rootR.setLeft(T.getRoot());
		T.getRoot().setParent(T.rootR);
		T.setRoot(T.rootR);
		T.rootR = null;
		T.getRoot().calcTree();
		T.goToPartOfArray(to+1, T.max-1, -20);
		T.reposition();
		mysuspend();
		addStep("revstickl");
		T.getRoot().pointTo(T.rootL);
		mysuspend();
		T.getRoot().noArrow();
		
		T.rootL.goTo(T.rootL.x+20, T.rootL.y);
		T.rootL.setRight(T.getRoot());
		T.getRoot().setParent(T.rootL);
		T.setRoot(T.rootL);
		T.rootL = null;
		T.getRoot().calcTree();
		T.goToPartOfArray(1, from-1, 20);
		T.reposition();
		mysuspend();
	}

}

