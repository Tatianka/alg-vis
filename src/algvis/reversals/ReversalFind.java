package algvis.reversals;

import algvis.core.NodeColor;

public class ReversalFind extends ReversalAlg {
	Reversal T;
	int K;
	
	public ReversalFind(Reversal T, int x) {
		super(T, x);
		this.T = T;
		K = x;
	}

	public void run() {
		T.setColorOfNodeArray(K, NodeColor.FOUND);
		addStep("rev-find-start");
		find(K);
		T.setColorOfNodeArray(K, NodeColor.NORMAL);
	}
}
