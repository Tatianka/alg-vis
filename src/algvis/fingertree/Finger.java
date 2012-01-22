package algvis.fingertree;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import algvis.core.*;


public class Finger extends FingerNode {
	
	public FingerNode f, tmp, kresli;
	public static BufferedImage img;
//	FingerTree T;
	
	public Finger(FingerTree T) {
		super(T,47,50,50);
		img = load("../images/pfleft.png");
		f = null;
	//	this.T = T;
		tmp = new FingerNode(T,0);
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
	//	goTo(N);
		f = N;
	}
	
	@Override
	public void goTo(FingerNode N) {
		goTo(N.tox, N.toy);
	/*	FingerNode fn = new FingerNode(T,5,f.x,f.y);
		tmp = fn; 
		tmp.bgColor(Colors.CACHED);
		kresli = tmp;
		tmp.goTo(N);	*/
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
		FingerNode N = f.way(num);
	//	goTo(N);
		f = N;
	}
	
	public boolean moveToNeighbour(int num) {
		if (f.leftNeigbour != null)
			if (f.leftNeigbour.belongsHere(num)) {
//				goTo(f.leftNeigbour);
				f = f.leftNeigbour;
				return true;
			}
		if (f.rightNeighbour != null)
			if (f.rightNeighbour.belongsHere(num)) {
//				goTo(f.rightNeighbour);
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
	/*	if (kresli == null) {
			v.drawImage(img, 50, 50, 20, 20);
		} else {*/
			if /*((x != f.x) || (y != f.y))*/ (f != null) {goTo(f);}
			v.drawImage(img, x+5, y+5, 20, 20);
		//}
		//kresli = f;
	}

}
