package algvis.fingertree;

public class Finger {
	
	public FingerNode f;
	
	public void setFinger(FingerNode N) {
		f = N;
	}
	
	public void moveTo (FingerNode N) {
		f = N;
	}
	
	public void moveUp()	{
		f = f.parent;		// if finger != root
	}
	
	public void moveDown(int num) {
		f = f.way(num); 
	}
	
	public void move(int num) {
		if (f.leftNeigbour.belongsHere(num)) {
			f = f.leftNeigbour;
			return;
		}
		if (f.rightNeighbour.belongsHere(num)) {
			f = f.rightNeighbour;
			return;
		}
		f = f.way(num);
	}
	
	public void draw() {
		
	}

}
