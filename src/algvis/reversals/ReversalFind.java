package algvis.reversals;

public class ReversalFind extends ReversalAlg {
	Reversal T;
	int K;
	
	public ReversalFind(Reversal T, int x) {
		super(T, x);
		this.T = T;
		K = x;
	}

	public void run() {
		find(K);
	}
}
