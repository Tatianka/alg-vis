package algvis.fingertree;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import algvis.core.*;


public class Finger {
	
	public FingerNode f;
	public static BufferedImage img;
	
	public Finger() {
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
	
	public void setFinger(FingerNode N) {
		f = N;
	}
	
	public void moveTo(FingerNode N) {
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
	
	public void draw(View v) {
		if (f == null) {
			v.drawImage(img, 50, 50, 20, 20);
		} else {
			v.drawImage(img, f.x+5, f.y+5, 20, 20);
		}
	}

}
