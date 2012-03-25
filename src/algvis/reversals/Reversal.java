package algvis.reversals;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import algvis.bst.BSTNode;
import algvis.core.Buttons;
import algvis.core.ClickListener;
import algvis.core.DataStructure;
import algvis.core.Layout;
import algvis.core.Node;
import algvis.core.NodeColor;
import algvis.core.View;
import algvis.core.VisPanel;
import algvis.splaytree.SplayTree;

public class Reversal extends SplayTree implements ClickListener {
	public static String dsName = "reversal";
	ReversalNode root = null, left = null, right = null;
	public int max = 0;
	ReversalNode rootL = null, rootR = null;
	Node[] ra;
	private static BufferedImage img;
	public static int minsepx = 1;//, minsepy = 43;
	
	public Reversal(VisPanel M) {
		super(M);
		img = load("../images/flag.png");
		setTree();
		setArray();
	}
	
	public void reverse(int x, int y) {
		if (x>y) {
			int tmp = x; x=y; y=tmp;
		}
		start(new Reverse(this, x, y));
		posArray();
	}
	
	public void insert() {
		start(new ReversalInsert(this));
		max+=10;
		setColor(getRoot());
		posArray();
	}
	
	public void find(int x) {
		ra[x-1].setColor(NodeColor.FOUND);
		start(new ReversalFind(this, x));
		ra[x-1].setColor(NodeColor.NORMAL);
	}
	
	@Override
	public void random(int n) {
		Random g = new Random(System.currentTimeMillis());
		boolean p = M.pause;
		M.pause = false;
		for (int i = 0; i < n; ++i) {
			int a = g.nextInt(max-1)+1, b = g.nextInt(max-1)+1;
			reverse(a,b);
		}
		M.pause = p;
		posArray();
	}
	
	public void setTree() {
		ReversalNode v0 = new ReversalNode(this, 0);
		ReversalNode v1 = new ReversalNode(this, 1);
		ReversalNode v2 = new ReversalNode(this, 2);
		ReversalNode v3 = new ReversalNode(this, 3);
		ReversalNode v4 = new ReversalNode(this, 4);
		ReversalNode v5 = new ReversalNode(this, 5);
		ReversalNode v6 = new ReversalNode(this, 6);
		ReversalNode v7 = new ReversalNode(this, 7);
		ReversalNode v8 = new ReversalNode(this, 8);
		ReversalNode v9 = new ReversalNode(this, 9);
		ReversalNode v10 = new ReversalNode(this, 10);
		ReversalNode v11 = new ReversalNode(this, 11);
		setRoot(v5);
		v5.setLeft(v3);
		v3.setParent(v5);
		v5.setRight(v8);
		v8.setParent(v5);
		v3.setLeft(v1);
		v1.setParent(v3);
		v1.setLeft(v0);
		v0.setParent(v1);
		v1.setRight(v2);
		v2.setParent(v1);
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
		v10.setRight(v11);
		v11.setParent(v10);
		reposition();
		getRoot().calcTree();
		max = 11;
		setColor(getRoot());
	}
	
	public void setColor(ReversalNode v) {
		if ((v.key == 0) || (v.key == max)) {
			v.setColor(NodeColor.CACHED);
		} else {
			v.setColor(NodeColor.NORMAL);
		}
		if (v.getLeft() != null) {
			setColor(v.getLeft());
		}
		if (v.getRight() != null) {
			setColor(v.getRight());
		}
	}

	public void setArray() {
		ra = new Node[10];
		for(int i=0; i<10; i++) {
			ra[i] = new Node(this, i+1, getRoot().x - getRoot().leftw +19 + i*(Node.radius*2+3), -35);
		}
	}

	public void insertToArray() {
		Node[] rb = ra;
		ra = new Node[max+9];
		for(int i=0; i<max-1; i++) {
			ra[i] = rb[i];
		}
		for(int i=max-1; i<max+9; i++) {
			ra[i] = new Node(this, i+1, ra[i-1].x+(Node.radius*2+3), ra[i-1].y);
		}
	}
	
	public void reverseArray(int from, int to) {
		from--; to--;
		int fx,fy,tx,ty;
		Node tmp;
		while (from<to) {
			fx = ra[from].x;
			fy = ra[from].y;
			tx = ra[to].x;
			ty = ra[to].y;
			ra[from].goTo(tx, ty);
			ra[to].goTo(fx, fy);
			tmp = ra[from];
			ra[from] = ra[to];
			ra[to] = tmp;
			from++;
			to--;
		}
	}
	
	@Override
	public String getName() {
		return "reversal";
	}
	
	public ReversalNode getRoot() {
		return root;
	}

	public BSTNode setRoot(BSTNode root) {
		this.root = (ReversalNode) root;
		return root;
	}
	
	public void setRoot(ReversalNode root) {
		this.root = root;
	}
	
	public void posArray() {
		goToArray(getRoot().x - getRoot().leftw +19);
	}
	
	public void goToArray(int x) {
		for(int i=0; i<max-1; i++) {
			ra[i].goTo(x+i*(Node.radius*2+3), ra[i].y);
		}
	}
	
	public void goToPartOfArray(int from, int to, int x) {
		for(int i=from-1; i<to; i++) {
			ra[i].goTo(ra[i].x + x, ra[i].y);
		}
	}
	
	public void moveArray() {
		for(int i=0; i< max-1; i++) {
			ra[i].move();
		}
	}
	
	public void drawArray(View V) {
		for(int i=0; i<max-2; i++) {
			ra[i].draw(V);
			V.drawLine(ra[i].x+Node.radius, ra[i].y, ra[i+1].x-Node.radius, ra[i+1].y);
		}
		ra[max-2].draw(V);
	}
	
	@Override
	public void draw(View V) {
		if (getRoot() != null) {
			getRoot().moveTree();
			getRoot().drawTree(V);
		}
		if (rootL != null) {
			rootL.moveTree();
			rootL.drawTree(V);
		}
		if (rootR != null) {
			rootR.moveTree();
			rootR.drawTree(V);
		}
		moveArray();
		drawArray(V);
		super.draw(V);
	}
	
	@Override
	public void clear() {
		super.clear();
		setTree();
		setArray();
		rootL = null;
		rootR = null;
	}
	
	@Override
	public void reposition() {
		x1 = x2 = y1 = y2 = 0;
		super.reposition();
		if (rootL != null) {
			rootL.reboxTree();
			rootL.goTo(DataStructure.rootx - rootL.rightw - root.leftw, DataStructure.rooty);
			rootL.repositionN();
		}
		if (rootR != null) {
			rootR.reboxTree();
//			rootR.leftw = DataStructure.minsepx/2;
			rootR.goTo(DataStructure.rootx + rootR.leftw + root.rightw, DataStructure.rooty);
			rootR.repositionN();
		}
		M.screen.V.setBounds(x1, y1, x2, y2);
	}
	
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

	public BufferedImage getImg() {
		return img;
	}
	
    @Override
    public Layout getLayout() {
            return Layout.SIMPLE;
    }

	
}
