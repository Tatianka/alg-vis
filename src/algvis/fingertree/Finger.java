package algvis.fingertree;

public class Finger {
	
	FingerNode N = null;
	
	public void moveTo (FingerNode N) {
		this.N = N;
	}
	
	public void moveUp()	{
		N = N.parent;		// if N != root
	}
	
	public void moveDown(int num) {
		N = (FingerNode) N.way(num); //prerobit na left, center, right ??
	}

}
