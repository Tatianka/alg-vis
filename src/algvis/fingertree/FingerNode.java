package algvis.fingertree;

import algvis.core.*;

import java.awt.Color;

public class FingerNode extends Node {
	
	int width, leftw, rightw;
	FingerNode parent = null, leftNeigbour = null, rightNeighbour = null; 
	int numKeys = 1, numChildren = 0;
	int[] key;
	FingerNode[] c;

	int nkeys = 1, nnodes = 1, height = 1;
	
	public FingerNode(DataStructure D, int key, int x, int y) {
		this.key = new int[9];
		c = new FingerNode[9];
		this.key[0] = key;
		numKeys = 1;
		this.D = D;
		// this.V = D.M.S.V;
		this.x = tox = x;
		this.y = toy = y;
		steps = 0;
		setColor(Color.black, Colors.NORMAL);
		width = _width();
		//super(D,key,x,y);
	}
	
	public FingerNode(DataStructure D, int key) {
		this(D, key, 0, 0);
		setState(Node.UP);
		//super(D,key);
	}
	
	public FingerNode(FingerNode v) {
		this(v.D, v.key[0], v.x, v.y);
	//	super(v);
	//	setNeighbour();
		leftNeigbour = v.leftNeigbour;
		rightNeighbour = v.rightNeighbour;
	}
	
	public FingerNode(FingerNode u, FingerNode v, FingerNode w) {
		//super(u,v,w);
		this(u.D, Node.NOKEY, v.x, v.y);
		int n1 = u.numKeys, n2 = w.numKeys;
		numKeys = n1 + 1 + n2;
		for (int i = 0; i < n1; ++i) {
			key[i] = u.key[i];
		}
		key[n1] = v.key[0];
		for (int i = 0; i < n2; ++i) {
			key[n1 + 1 + i] = w.key[i];
		}
		n1 = u.numChildren;
		n2 = w.numChildren;
		numChildren = n1 + n2;
		for (int i = 0; i < n1; ++i) {
			c[i] = u.c[i];
		}
		for (int i = 0; i < n2; ++i) {
			c[n1 + i] = w.c[i];
		}
		for (int i = 0; i < numChildren; ++i) {
			c[i].parent = this;
		}
		width = _width();
	//	setNeighbour();
		
		leftNeigbour = u.leftNeigbour;
		rightNeighbour = w.rightNeighbour;
	}
	
	public int getNumKeys() {
		return numKeys;
	}
		
/*	public FingerNode split() {
		FingerNode ch1 = new FingerNode(D, key[0], x, y), ch2 = new FingerNode(D, key[3], x, y), p = new FingerNode(
				D, key[2], x, y);
		ch1.addLeaf(key[1]);
		if (isLeaf()) {
			ch1.numChildren = ch2.numChildren = 0;
		} else {
			ch1.c[0] = c[0];
			ch1.c[0].parent = ch1;
			ch1.c[1] = c[1];
			ch1.c[1].parent = ch1;
			ch1.c[2] = c[2];
			ch1.c[2].parent = ch1;
			ch2.c[0] = c[3];
			ch1.c[0].parent = ch2;
			ch2.c[1] = c[4];
			ch1.c[1].parent = ch2;
		}
		ch1.parent = ch2.parent = p;
		p.numChildren = 2;
		p.parent = parent;
		p.c[0] = ch1;
		p.c[1] = ch2;
	// what is it??	-from FingerNode.split
		ch1.width = ch1._width();
		ch2.width = ch2._width();
		ch1.x = x - ch1.width / 2 - D.radius;
		ch2.x = x + ch2.width / 2 + D.radius;
		
		return p;
	}*/
	
///// if x belongs to this undertree //////////
	public boolean belongsHere(int x) {
		if ((x >= key[0]) && (x <= key[numKeys-1])) {
			return true;
		}
		return false;
	}
	public void setNeighbour() {
		if (parent.isLeaf()) {return;}
		if (parent.leftNeigbour.c[0] != null) {
			leftNeigbour = parent.leftNeigbour.c[0];
		}
		if (parent.rightNeighbour.c[parent.leftNeigbour.numKeys-1] != null) {
			rightNeighbour = parent.rightNeighbour.c[parent.leftNeigbour.numKeys-1];
		}
	}
//////////////////////////////////////////////////////////////////	
	public void add(int k, FingerNode v) {
		for (int i = numKeys; i > k; --i) {
			key[i] = key[i - 1];
			c[i + 1] = c[i];
		}
		++numKeys;
		++numChildren;
		key[k] = v.key[0];
		c[k] = v.c[0];
		c[k].parent = this;
		c[k + 1] = v.c[1];
		c[k + 1].parent = this;
		width = _width();
	}

	public void addLeaf(int x) {
		key[numKeys++] = x;
		for (int i = numKeys - 1; i > 0; --i) {
			if (key[i] < key[i - 1]) {
				int tmp = key[i];
				key[i] = key[i - 1];
				key[i - 1] = tmp;
			}
		}
		width = _width();
	}

	public void calcTree() {
		nkeys = numKeys;
		nnodes = 1;
		for (int i = 0; i < numChildren; ++i) {
			c[i].calcTree();
			nkeys += c[i].nkeys;
			nnodes += c[i].nnodes;
		}
		height = 1 + (isLeaf() ? 0 : c[0].height);
	}

	public FingerNode del(int k) {
		int i = -1;
		while (key[++i] != k) {
		}
		int p = i;
		for (--numKeys; i < numKeys; i++) {
			key[i] = key[i + 1];
		}
		width = _width();
		return new FingerNode(D, k, x - (numKeys + 1 - 2 * p) * D.radius, y);
	}

	public FingerNode delMin() {
		int r = key[0];
		--numKeys;
		for (int i = 0; i < numKeys; ++i) {
			key[i] = key[i + 1];
		}
		width = _width();
		return new FingerNode(D, r, x - (numKeys - 1) * D.radius, y);
	}

	public FingerNode delMinCh() {
		FingerNode r = c[0];
		--numChildren;
		for (int i = 0; i < numChildren; ++i) {
			c[i] = c[i + 1];
		}
		width = _width();
		return r;
	}

	public FingerNode delMax() {
		FingerNode r = new FingerNode(D, key[--numKeys], x + (numKeys - 1) * D.radius, y);
		width = _width();
		return r;
	}

	public FingerNode delMaxCh() {
		FingerNode r = c[--numChildren];
		width = _width();
		return r;
	}

	@Override
	public void drawBg(View V) {
		V.setColor(bgcolor);
		V.fillRoundRectangle(x, y, width / 2, D.radius, 2 * D.radius,
				2 * D.radius);
		V.setColor(fgcolor);
		V.drawRoundRectangle(x, y, width / 2, D.radius, 2 * D.radius,
				2 * D.radius);
		// g.drawLine (x-leftw, y+2, x+rightw, y-2);
	}

	@Override
	public void drawKey(View V) {
		if (key[0] != Node.NOKEY && numKeys > 0) {
			V.drawString(toString(), x, y, 9);
		}
	}

	public void drawTree(View v) {
		for (int i = 0; i < numChildren; ++i) {
			v.setColor(Color.black);
			/*
			 * int xx, yy; if (i==0 || i==numChildren-1) { xx = x; yy = y; }
			 * else { xx = (pos(i-1)+pos(i))/2; yy = y+D.radius; }
			 */
			v.drawLine(x, y, c[i].x, c[i].y - D.radius);
			c[i].drawTree(v);
		}
		
		if (leftNeigbour != null) {
			v.drawLine(x, y-2, leftNeigbour.x + leftNeigbour.width/2, leftNeigbour.y-2);
		}
		if (rightNeighbour != null) {
			v.drawLine(x, y+2, rightNeighbour.x - rightNeighbour.width/2, rightNeighbour.y+2);
		}
		
		draw(v);
	}
	
	public void insMin(int k) {
		for (int i = numKeys++; i > 0; --i) {
			key[i] = key[i - 1];
		}
		key[0] = k;
		width = _width();
	}

	public void insMinCh(FingerNode v) {
		for (int i = numChildren++; i > 0; --i) {
			c[i] = c[i - 1];
		}
		c[0] = v;
		width = _width();
	}

	public void insMax(int k) {
		key[numKeys++] = k;
		width = _width();
	}

	public void insMaxCh(FingerNode v) {
		c[numChildren++] = v;
		width = _width();
	}
	
	public boolean isIn(int x) {
		for (int i = 0; i < numKeys; ++i) {
			if (key[i] == x) {
				return true;
			}
		}
		return false;
	}

	public boolean isLeaf() {
		return numChildren == 0;
	}

	public boolean isRoot() {
		return parent == null;
	}

	public void moveTree() {
		for (int i = 0; i < numChildren; ++i) {
			c[i].moveTree();
		}
		move();
	}
	
	public int order() {
		for (int i = 0; i < parent.numChildren; ++i) {
			if (parent.c[i] == this) {
				return i;
			}
		}
		return -5; // TODO: vypindat exception
	}

	public int pos(int i) {
		if (i < 0) {
			return tox - D.M.S.V.stringWidth(toString(), 9) / 2 - D.radius;
		}
		if (i >= numKeys) {
			return tox + D.M.S.V.stringWidth(toString(), 9) / 2 + D.radius;
		}
		if (numKeys <= 1) {
			return x;
		}
		String s = toString(i), t;
		if (i == 0) {
			t = "" + key[0];
		} else {
			t = "  " + key[i];
		}
		return tox - D.M.S.V.stringWidth(toString(), 9) / 2
				+ D.M.S.V.stringWidth(s, 9) + D.M.S.V.stringWidth(t, 9) / 2;
	}
	
	public void rebox() {
		if (numChildren == 0) {
			leftw = rightw = width / 2 + ((FingerTree) D).xspan; // numKeys *
			// D.radius +
			// D.xspan;
		} else {
			if (numChildren % 2 == 0) {
				leftw = rightw = 0;
			} else {
				leftw = c[numChildren / 2].leftw;
				rightw = c[numChildren / 2].rightw;
			}
			for (int i = 0; i < numChildren / 2; ++i) {
				leftw += c[i].leftw + c[i].rightw;
			}
			for (int i = (numChildren + 1) / 2; i < numChildren; ++i) {
				rightw += c[i].leftw + c[i].rightw;
			}
		}
	}

	public void reboxTree() {
		for (int i = 0; i < numChildren; ++i) {
			c[i].reboxTree();
		}
		rebox();
	}
	
	public void replace(int x, int y) {
		int i = -1;
		while (key[++i] != x) {
		}
		key[i] = y;
		width = _width();
	}
	
	private void repos() {
		if (isRoot()) {
			goToRoot();
			D.x1 = -leftw;
			D.x2 = rightw;
			D.y2 = this.toy;
		}
		if (this.toy > D.y2) {
			D.y2 = this.toy;
		}
		int x = this.tox, x2 = this.tox, y = this.toy + 2 * D.radius
				+ ((FingerTree) D).yspan;
		if (numChildren == 0) {
			return;
		}
		if (numChildren % 2 == 0) {
			int k = numChildren / 2 - 1;
			c[k].goTo(x -= c[k].rightw, y);
			c[k].repos();
			for (int i = k - 1; i >= 0; --i) {
				c[i].goTo(x -= c[i + 1].leftw + c[i].rightw, y);
				c[i].repos();
			}
			c[++k].goTo(x2 += c[k].leftw, y);
			c[k].repos();
			for (int i = k + 1; i < numChildren; ++i) {
				c[i].goTo(x2 += c[i - 1].rightw + c[i].leftw, y);
				c[i].repos();
			}
		} else {
			int k = numChildren / 2;
			c[k].goTo(x, y);
			c[k].repos();
			for (int i = 1; i <= k; ++i) {
				c[k - i].goTo(x -= c[k - i].rightw + c[k - i + 1].leftw, y);
				c[k - i].repos();
				c[k + i].goTo(x2 += c[k + i].leftw + c[k + i - 1].rightw, y);
				c[k + i].repos();
			}
		}
	}

	public void _reposition() {
		reboxTree();
		repos();
	}
	
	public int search(int x) {
		if (x < key[0]) {
			return 0;
		}
		for (int i = 1; i < numKeys; ++i) {
			if (x < key[i]) {
				return i;
			}
		}
		return numKeys;
	}

public FingerNode split() {
	int k = numKeys, ku = numKeys / 2; // , kw = numKeys - ku - 1;
	FingerNode u = new FingerNode(D, key[0], x, y), v = new FingerNode(D, key[ku], x, y), w = new FingerNode(
			D, key[k - 1], x, y);
	for (int i = 1; i < ku; ++i) {
		u.addLeaf(key[i]);
	}
	for (int i = ku + 1; i < k - 1; ++i) {
		w.addLeaf(key[i]);
	}
	if (isLeaf()) {
		u.numChildren = w.numChildren = 0;
	} else {
		u.numChildren = (numChildren + 1) / 2;
		w.numChildren = numChildren / 2;
		for (int i = 0; i < u.numChildren; ++i) {
			u.c[i] = c[i];
			u.c[i].parent = u;
		}
		for (int i = 0; i < w.numChildren; ++i) {
			w.c[i] = c[u.numChildren + i];
			w.c[i].parent = w;
		}
	}
	u.parent = w.parent = v;
	v.numChildren = 2;
	v.parent = parent;
	v.c[0] = u;
	v.c[1] = w;
	u.width = u._width();
	w.width = w._width();
	u.x = x - u.width / 2 - D.radius;
	w.x = x + w.width / 2 + D.radius;
	
	u.leftNeigbour = leftNeigbour;
	u.rightNeighbour = w;
	w.leftNeigbour = u;
	w.rightNeighbour = rightNeighbour;
	
	if (leftNeigbour != null) {
		leftNeigbour.rightNeighbour = u;
	}
	if (rightNeighbour != null) {
		rightNeighbour.leftNeigbour = w;
	}
	return v;
}
	
	public String toString(int max) {
		if (numKeys == 0 || max == 0) {
			return "";
		}
		String str = "";
		if (key[0] == INF) {
			str = "\u221e";
		} else if (key[0] == -INF) {
			str = "-\u221e";
		} else {
			str = "" + key[0];
		}
		for (int i = 1; i < Math.min(numKeys, max); ++i) {
			str = str + "  " + key[i];
		}
		return str;
	}

	@Override
	public String toString() {
		return toString(numKeys);
	}

	int _width() {
		if (key[0] != Node.NOKEY && numKeys > 0) {
			return Math.max(Fonts.fm[9].stringWidth(toString()) + 4,
					2 * D.radius);
		} else {
			return 2 * D.radius;
		}
	}
	
	public FingerNode way(int x) {
		if (x < key[0]) {
			return c[0];
		}
		for (int i = 1; i < numKeys; ++i) {
			if (x < key[i]) {
				return c[i];
			}
		}
		return c[numKeys];
	}

	public int _goToX(FingerNode v) {
		int x = key[0], p = v.numKeys;
		for (int i = 0; i < p; ++i) {
			if (x <= v.key[i]) {
				p = i;
			}
		}
		return (v.pos(p - 1) + v.pos(p)) / 2;
	}

	public void goTo(FingerNode v) {
		goTo(_goToX(v), v.toy);
	}

	public void goAbove(FingerNode v) {
		goTo(_goToX(v), v.toy - 2 * D.radius + 2);
	}

	public void goBelow(FingerNode v) {
		goTo(_goToX(v), v.toy + 2 * D.radius - 2);
	}

	
/*	public FingerNode way(int x) {
		if (x<key[0]) { return c[0]; }
		for(int i=0; i<numKeys; i++) {
			if (x>key[i]) { return c[i+1]; }
		}
		return null;
	}
	
	public int search(int x) {
		if (x < key[0]) {
			return 0;
		}
		for (int i = 1; i < numKeys; ++i) {
			if (x < key[i]) {
				return i;
			}
		}
		return numKeys;
	}

	
	int _width() {
		if (key[0] != Node.NOKEY && numKeys > 0) {
			return Math.max(Fonts.fm[9].stringWidth(toString()) + 4,
					2 * D.radius);
		} else {
			return 2 * D.radius;
		}
	}	
	
	public void addLeaf(int x) {
		key[numKeys++] = x;
		for (int i = numKeys - 1; i > 0; --i) {
			if (key[i] < key[i - 1]) {
				int tmp = key[i];
				key[i] = key[i - 1];
				key[i - 1] = tmp;
			}
		}
		width = _width();
	}
	
	public void add(int k, FingerNode v) {
		for (int i = numKeys; i > k; --i) {
			key[i] = key[i - 1];
			c[i + 1] = c[i];
		}
		++numKeys;
		++numChildren;
		key[k] = v.key[0];
		c[k] = v.c[0];
		c[k].parent = this;
		c[k + 1] = v.c[1];
		c[k + 1].parent = this;
		width = _width();
	}

	public boolean isLeaf() {
		if (numChildren == 0) {return true;}
		return false;
	}
	
	public boolean isRoot() {
		if (parent == null) {return true;}
		return false;
	}
	
	public boolean isIn(int x) {
		for(int i=0; i<numKeys; i++) 
			if (key[i] == x) { return true; }
		return false;
	}

	public void moveTree() {
		for (int i = 0; i < numChildren; ++i) {
			c[i].moveTree();
		}
		move();	
	}

	public void drawTree(View v) {
		for (int i = 0; i < numChildren; ++i) {
			v.setColor(Color.black);
			/*
			 * int xx, yy; if (i==0 || i==numChildren-1) { xx = x; yy = y; }
			 * else { xx = (pos(i-1)+pos(i))/2; yy = y+D.radius; }
			 */
		/*	v.drawLine(x, y, c[i].x, c[i].y - D.radius);
			c[i].drawTree(v);
		}
		draw(v);
	}
	
	@Override
	public void drawBg(View V) {
		V.setColor(bgcolor);
		V.fillRoundRectangle(x, y, width / 2, D.radius, 2 * D.radius,
				2 * D.radius);
		V.setColor(fgcolor);
		V.drawRoundRectangle(x, y, width / 2, D.radius, 2 * D.radius,
				2 * D.radius);
		// g.drawLine (x-leftw, y+2, x+rightw, y-2);
	}

	@Override
	public void drawKey(View V) {
		if (key[0] != Node.NOKEY && numKeys > 0) {
			V.drawString(toString(), x, y, 9);
		}
	}

	public void rebox() {
		if (numChildren == 0) {
			leftw = rightw = width / 2 + ((FingerTree) D).xspan; // numKeys *
			// D.radius +
			// D.xspan;
		} else {
			if (numChildren % 2 == 0) {
				leftw = rightw = 0;
			} else {
				leftw = c[numChildren / 2].leftw;
				rightw = c[numChildren / 2].rightw;
			}
			for (int i = 0; i < numChildren / 2; ++i) {
				leftw += c[i].leftw + c[i].rightw;
			}
			for (int i = (numChildren + 1) / 2; i < numChildren; ++i) {
				rightw += c[i].leftw + c[i].rightw;
			}
		}
	}

	public void reboxTree() {
		for (int i = 0; i < numChildren; ++i) {
			c[i].reboxTree();
		}
		rebox();
	}

	private void repos() {
		if (isRoot()) {
			goToRoot();
			D.x1 = -leftw;
			D.x2 = rightw;
			D.y2 = this.toy;
		}
		if (this.toy > D.y2) {
			D.y2 = this.toy;
		}
		int x = this.tox, x2 = this.tox, y = this.toy + 2 * D.radius
				+ ((FingerTree) D).yspan;
		if (numChildren == 0) {
			return;
		}
		if (numChildren % 2 == 0) {
			int k = numChildren / 2 - 1;
			c[k].goTo(x -= c[k].rightw, y);
			c[k].repos();
			for (int i = k - 1; i >= 0; --i) {
				c[i].goTo(x -= c[i + 1].leftw + c[i].rightw, y);
				c[i].repos();
			}
			c[++k].goTo(x2 += c[k].leftw, y);
			c[k].repos();
			for (int i = k + 1; i < numChildren; ++i) {
				c[i].goTo(x2 += c[i - 1].rightw + c[i].leftw, y);
				c[i].repos();
			}
		} else {
			int k = numChildren / 2;
			c[k].goTo(x, y);
			c[k].repos();
			for (int i = 1; i <= k; ++i) {
				c[k - i].goTo(x -= c[k - i].rightw + c[k - i + 1].leftw, y);
				c[k - i].repos();
				c[k + i].goTo(x2 += c[k + i].leftw + c[k + i - 1].rightw, y);
				c[k + i].repos();
			}
		}
	}

	public void _reposition() {
		reboxTree();
		repos();
	}

	public void calcTree() {
		nkeys = numKeys;
		nnodes = 1;
		for (int i = 0; i < numChildren; ++i) {
			c[i].calcTree();
			nkeys += c[i].nkeys;
			nnodes += c[i].nnodes;
		}
		height = 1 + (isLeaf() ? 0 : c[0].height);	
	}
	
	public String toString(int max) {
		if (numKeys == 0 || max == 0) {
			return "";
		}
		String str = "";
		if (key[0] == INF) {
			str = "\u221e";
		} else if (key[0] == -INF) {
			str = "-\u221e";
		} else {
			str = "" + key[0];
		}
		for (int i = 1; i < Math.min(numKeys, max); ++i) {
			str = str + "  " + key[i];
		}
		return str;
	}
	@Override
	public String toString() {
		return toString(numKeys);
	}
	
	public int pos(int i) {
		if (i < 0) {
			return tox - D.M.S.V.stringWidth(toString(), 9) / 2 - D.radius;
		}
		if (i >= numKeys) {
			return tox + D.M.S.V.stringWidth(toString(), 9) / 2 + D.radius;
		}
		if (numKeys <= 1) {
			return x;
		}
		String s = toString(i), t;
		if (i == 0) {
			t = "" + key[0];
		} else {
			t = "  " + key[i];
		}
		return tox - D.M.S.V.stringWidth(toString(), 9) / 2
				+ D.M.S.V.stringWidth(s, 9) + D.M.S.V.stringWidth(t, 9) / 2;
	}
	
	public int _goToX(FingerNode v) {
		int x = key[0], p = v.numKeys;
		for (int i = 0; i < p; ++i) {
			if (x <= v.key[i]) {
				p = i;
			}
		}
		return (v.pos(p - 1) + v.pos(p)) / 2;
	}

	public void goTo(FingerNode v) {
		goTo(_goToX(v), v.toy);
	}

	public void goAbove(FingerNode v) {
		goTo(_goToX(v), v.toy - 2 * D.radius + 2);
	}

	public void goBelow(FingerNode v) {
		goTo(_goToX(v), v.toy + 2 * D.radius - 2);
	}
	
	public FingerNode del(int k) {
		int i = -1;
		while (key[++i] != k) {
		}
		int p = i;
		for (--numKeys; i < numKeys; i++) {
			key[i] = key[i + 1];
		}
		width = _width();
		return new FingerNode(D, k, x - (numKeys + 1 - 2 * p) * D.radius, y);
	}

	public FingerNode delMin() {
		int r = key[0];
		--numKeys;
		for (int i = 0; i < numKeys; ++i) {
			key[i] = key[i + 1];
		}
		width = _width();
		return new FingerNode(D, r, x - (numKeys - 1) * D.radius, y);
	}

	public FingerNode delMinCh() {
		FingerNode r = c[0];
		--numChildren;
		for (int i = 0; i < numChildren; ++i) {
			c[i] = c[i + 1];
		}
		width = _width();
		return r;
	}

	public FingerNode delMax() {
		FingerNode r = new FingerNode(D, key[--numKeys], x + (numKeys - 1) * D.radius, y);
		width = _width();
		return r;
	}

	public FingerNode delMaxCh() {
		FingerNode r = c[--numChildren];
		width = _width();
		return r;
	}

	public void insMin(int k) {
		for (int i = numKeys++; i > 0; --i) {
			key[i] = key[i - 1];
		}
		key[0] = k;
		width = _width();
	}

	public void insMinCh(FingerNode v) {
		for (int i = numChildren++; i > 0; --i) {
			c[i] = c[i - 1];
		}
		c[0] = v;
		width = _width();
	}

	public void insMax(int k) {
		key[numKeys++] = k;
		width = _width();
	}

	public void insMaxCh(FingerNode v) {
		c[numChildren++] = v;
		width = _width();
	}

	public void replace(int x, int y) {
		int i = -1;
		while (key[++i] != x) {
		}
		key[i] = y;
		width = _width();
	}*/

}
