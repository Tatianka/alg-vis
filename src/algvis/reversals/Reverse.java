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
	}
	
	@Override
	public void run() {
		if (from == to) {
			return;
		}
		if (to > T.max-1) {
			addStep("toobig");
			return;
		}
		ReversalNode w = find(from-1);
		splay(w);
		mysuspend();
		
		T.rootL = T.getRoot();
		T.setRoot(T.getRoot().getRight());
		T.rootL.setRight(null);
		T.getRoot().setParent(null);
		
		T.reposition();
		mysuspend();
		
		T.v = s = new ReversalNode(T, K = to);
		s.setColor(NodeColor.FIND);
		w = find(to-from+1);
		splay(w);
		mysuspend();
		T.rootR = T.getRoot();
		T.setRoot(T.getRoot().getLeft());
		T.getRoot().setParent(null);
		T.rootR.setLeft(null);

		T.reposition();
		mysuspend();
		/// let's stick it together
		T.getRoot().changeFlag();
		T.rootL.setRight(T.rootR);
		T.rootR.setParent(T.rootL);
		
		T.reposition();
		mysuspend();
		
		T.rootR.setLeft(T.getRoot());
		T.getRoot().setParent(T.rootR);
		T.rootR = null;
		
	//	T.reposition();
		//mysuspend();
		
		T.setRoot(T.rootL);
		T.rootL = null;
		
		T.reposition();
	}

}

