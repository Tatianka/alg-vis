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
		T.rootL.setParent(T.rootL); ///////////////////
		T.setRoot(T.getRoot().getRight());
		T.rootL.setRight(null);
		T.rootL.calcTree();
		T.getRoot().setParent(null);
		
		T.reposition();
		mysuspend();
		
		T.v = s = new ReversalNode(T, K = to);
		s.setColor(NodeColor.FIND);
		w = find(to-from+1);
		splay(w);
		T.reposition();
		mysuspend();
		T.rootR = T.getRoot();
		T.rootR.setParent(T.rootR); ////////////////////
		T.setRoot(T.getRoot().getLeft());
		T.getRoot().setParent(null);
		T.rootR.setLeft(null);
		T.rootR.calcTree();

		T.reposition();
		mysuspend();
		/// let's stick it together
		T.getRoot().changeFlag();
		T.reverseArray(from, to);
		T.rootL.setRight(T.rootR);
		T.rootR.setParent(T.rootL);
		
		w = T.rootR;
		T.rootR = null;
		
		T.reposition();
		mysuspend();
		
		w.setLeft(T.getRoot());
		T.getRoot().setParent(w);
		
	//	T.reposition();
		//mysuspend();
		
		T.setRoot(T.rootL);
		T.rootL.setParent(null);
		T.rootL = null;
		
		T.reposition();
		T.getRoot().calcTree();
	}

}

