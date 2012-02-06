package algvis.reversals;

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
		T.L = new Reversal(T.M);
		T.L.setRoot(T.root);
		T.setRoot(T.getRoot().getRight());
		T.L.getRoot().setRight(null);
		T.getRoot().setParent(null);
		
		T.reposition();
		mysuspend();
		
		w = find(to-from+1);
		splay(w);
		T.R = new Reversal(T.M);
		T.R.setRoot(T.getRoot());
		T.R.getRoot().setParent(null);
		T.setRoot(T.getRoot().getLeft());
		
		T.reposition();
		mysuspend();
		/// let's stick it together
	//	T.getRoot().revflag = true;
		T.getRoot().changeFlag();
		T.L.getRoot().setRight(T.R.getRoot());
		T.R.getRoot().setParent(T.L.getRoot());
		T.R.getRoot().setLeft(T.getRoot());
		T.getRoot().setParent(T.R.getRoot());
		T.setRoot(T.L.getRoot());
		T.reposition();
	}

}

