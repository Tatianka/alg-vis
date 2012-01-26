package algvis.fingertree;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import algvis.core.*;


public class Finger extends FingerNode {
	
	public FingerNode f;
	public static BufferedImage img;
	
	public Finger(FingerTree T) {
		super(T,47,50,50);
		img = load("../images/pfleft.png");
		f = null;
	}
	
	// from branch balls //
	public static BufferedImage load(String path) {
		java.net.URL imgURL = Buttons.class.getResource(path);
		if (imgURL != null) {
			try {
				return ImageIO.read(imgURL);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} 
		return null;
	}
	
	public FingerNode getNode() {
		return f;
	}
	
	public void setFinger(FingerNode N) {
		goTo(N);
		f = N;
	}
	
	public void goTo(FingerNode N) {
		if (N == null) {
			goTo(50,50);
			return;
		}
		goTo(N.x, N.y);
	}
	
	public void moveTo(FingerNode N) {
		goTo(N);
		f = N;
	}
	
	public void moveUp()	{
		goTo(f.parent);
		f = f.parent;		// if finger != root
	}
	
	public void moveDown(int num) {
		FingerNode N = (FingerNode) f.way(num);
		goTo(N);
		f = N;
	}
	
	public boolean moveToNeighbour(int num) {
		if (f.leftNeighbour != null)
			if (f.leftNeighbour.belongsHere(num)) {
				goTo(f.leftNeighbour);
				f = f.leftNeighbour;
				return true;
			}
		if (f.rightNeighbour != null)
			if (f.rightNeighbour.belongsHere(num)) {
				goTo(f.rightNeighbour);
				f = f.rightNeighbour;
				return true;
			}		
		return false;
	}
	
	public void move(int num) {
		if (moveToNeighbour(num)) {return;}
		moveDown(num);
	}
	
	@Override
	public void draw(View v) {
			if /*((x != f.x) || (y != f.y))*/ (f != null) {goTo(f);}
			v.drawImage(img, x+5, y+5, 20, 20);
	}

}
