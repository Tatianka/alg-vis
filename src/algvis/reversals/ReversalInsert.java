package algvis.reversals;

public class ReversalInsert extends ReversalAlg {
	Reversal T;

	public ReversalInsert(Reversal T) {
		super(T);
		this.T = T;
	}
	
	public ReversalNode setTree() {
		ReversalNode v1 = new ReversalNode(T, T.max+1);
		ReversalNode v2 = new ReversalNode(T, T.max+2);
		ReversalNode v3 = new ReversalNode(T, T.max+3);
		ReversalNode v4 = new ReversalNode(T, T.max+4);
		ReversalNode v5 = new ReversalNode(T, T.max+5);
		ReversalNode v6 = new ReversalNode(T, T.max+6);
		ReversalNode v7 = new ReversalNode(T, T.max+7);
		ReversalNode v8 = new ReversalNode(T, T.max+8);
		ReversalNode v9 = new ReversalNode(T, T.max+9);
		ReversalNode v10 = new ReversalNode(T, T.max+10);
		v5.setLeft(v3);
		v3.setParent(v5);
		v5.setRight(v8);
		v8.setParent(v5);
		v3.setLeft(v2);
		v2.setParent(v3);
		v2.setLeft(v1);
		v1.setParent(v2);
		v3.setRight(v4);
		v4.setParent(v3);
		v8.setLeft(v6);
		v6.setParent(v8);
		v6.setRight(v7);
		v7.setParent(v6);
		v8.setRight(v10);
		v10.setParent(v8);
		v10.setLeft(v9);
		v9.setParent(v10);
		v5.reposition();	
		return v5;
	}

	@Override
	public void run() {
		if (T.getRoot() == null) {
			T.setRoot( setTree() );
			return;
		}
		ReversalNode w = find(T.max);
		mysuspend();
		if (w == null) {
			System.out.print("trololo");
		}
		splay(w);
		T.insertToArray();
		T.rootR = setTree();
		T.reposition();
		mysuspend();
		T.getRoot().setRight(T.rootR);
		T.rootR.setParent(T.getRoot());
		T.rootR = null;
		T.getRoot().calcTree();
		T.reposition();
	}

}
