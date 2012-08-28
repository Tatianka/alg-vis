package algvis.linkcuttree;

import algvis.core.Algorithm;
import algvis.core.NodeColor;
import algvis.core.NodePair;

public class LinkCutAlg extends Algorithm {
	LinkCutDS D;
	LinkCutDSNode s;
	
	public LinkCutAlg(LinkCutDS D) {
		super(D);
		this.D = D;
	}
	
	public NodePair<LinkCutDSNode> split(LinkCutDSNode v) {
		if (v == null) {return null;}
		NodePair<LinkCutDSNode> p = new NodePair<LinkCutDSNode>();
		p.left = null;
		p.right = null;
		if (v.getParent() != null) {
			p.left = v.getParent();		//vrchol nad
			if (v.getParent().preffered == v) {
				v.getParent().preffered = null;
			}
		} 
		if (v.preffered != null) {
			p.right = v.preffered;		//vrchol pod, ak je nejaky preferovany
			v.preffered = null;			
		}
		D.reposition();
		addStep("lct-split", v.getKey());
		mysuspend();
		return p;
	}
	
	public void splice(LinkCutDSNode p) {	//p je vrch path(p)
		LinkCutDSNode v = p.getParent();
		if (v == null) {
			addStep("lct-root");
			return;
		}
		if (v.preffered == p) {
			addStep("lct-splice",p.getKey());  
			return;
		}
		addStep("lct-splice", p.getKey());
		NodePair<LinkCutDSNode> np = split(v);
		if (np.left != null) {
			np.left.preffered = p;
		}
		v.preffered = p;
		D.reposition();
	}
	
	public void expose(LinkCutDSNode v) {
		NodePair<LinkCutDSNode> np = split(v);
		if (np.left != null) {
			np.left.preffered = v;
		}
		while (v != null) {
			v.setColor(NodeColor.CACHED);
			splice(v);
			mysuspend();
			v.setColor(NodeColor.NORMAL);
			v = v.getParent();
		}
	}
	
	
	public void evert(LinkCutDSNode v, int index) {
		expose(v);
		mysuspend();
		LinkCutDSNode w = v.getParent(), x;
		v.setParent(null);
		D.tree.set(index, v);
		addStep("lct-echange");
		while (w != null) {
			v.setColor(NodeColor.CACHED);
			x = w.getParent();
			w.setParent(v);
			w.deleteChild(v);
			v.addChild(w);
			v.preffered = w;
			w.preffered = null;
//			D.reposition();
//			mysuspend();
			v.setColor(NodeColor.NORMAL);
			v = w;
			w = x;			
		}
	}
	
	public void link(LinkCutDSNode v, LinkCutDSNode w) {
		expose(w);
		w.addChild(v);
		w.preffered = v;
		v.setParent(w);
		D.reposition();
		addStep("lct-link", v.getKey(), w.getKey());
	}

	public void cut(LinkCutDSNode v) {
		if (v.isRoot()) {return;}
		expose(v);
		NodePair<LinkCutDSNode> np = split(v);
		np.left.deleteChild(v);
		v.setParent(null);
		addStep("lct-cut", v.getKey());
	}
	
	//////// second tree /////////////////////////
			
	public void Access(LCTree v, int index) {
		splay(v,index);
		D.reposition();
		addStep("lct-up", v.getKey());
		mysuspend();
		v.unpref(v.pgetRight());
		v.psetRight(null);
		mysuspend();
		LCTree vt = v;
		while (vt != D.lctree.get(index)) {
			LCTree w = vt.getParent();
			addStep("lct-next");
			splay(w,index);
			mysuspend();
			w.unpref(w.pgetRight());
			w.psetRight(vt);
			vt = w;
			addStep("lct-prefhim", vt.getKey());
			mysuspend();
		}
		addStep("lct-up", v.getKey());
		splay(v, index);
		D.reposition();
		mysuspend();
	}
	
	public void Access2(LCTree v, int index) {
		addStep("lct-up", v.getKey());
		splay(v,index);
		D.reposition();
		mysuspend();
		LCTree vt = v;
		while (vt != D.lctree.get(index)) {
			LCTree w = vt.getParent();
			addStep("lct-next");
			splay(w,index);
			mysuspend();
			w.unpref(w.pgetRight());
			w.psetRight(vt);
			vt = w;
			addStep("lct-prefhim", vt.getKey());
			mysuspend();
		}
		addStep("lct-up", v.getKey());
		splay(v, index);
		D.reposition();
		mysuspend();
		if (!v.pisHead()) {
			v.changeFlag();
			v.pgetRight().changeFlagPath();
			D.reposition();
			mysuspend();
		}
	}
	
	
	public void splay(LCTree w, int index) {
		while (!w.pisRoot()) {
			D.setW1(w);
			D.setW2(w.pgetParent());
			if (w.pgetParent().pisRoot()) {
				addNote("splay-root");
				w.setArc(w.pgetParent());
				mysuspend();
				w.noArc();
				D.rotate(w, index);
			} else {
				if (w.isLeft() == w.pgetParent().isLeft()) {
					if (w.isLeft()) {
						addNote("splay-zig-zig-left", w.getKey(), w.pgetParent().getKey());
					} else {
						addNote("splay-zig-zig-right", w.getKey(), w.pgetParent().getKey());
					}
					addStep("rotate", w.pgetParent().getKey());
					w.pgetParent().setArc(w.pgetParent().pgetParent());
					mysuspend();
					w.pgetParent().noArc();
					D.setW2(w.pgetParent().pgetParent());
					D.rotate(w.pgetParent(), index);
					w.setArc(w.pgetParent());
					addStep("rotate", w.getKey());
					mysuspend();
					w.noArc();
					D.setW1(w.pgetParent());
					D.rotate(w, index);
					mysuspend();
				} else {
					if (!w.isLeft()) {
						addNote("splay-zig-zag-left", w.getKey(), w.pgetParent().getKey());
					} else {
						addNote("splay-zig-zag-right", w.getKey(), w.pgetParent().getKey());
					}
					w.setArc(w.pgetParent());
					addStep("rotate", w.getKey());
					mysuspend();
					w.noArc();
					D.rotate(w, index);
					w.setArc(w.pgetParent());
					addStep("rotate", w.getKey());
					mysuspend();
					w.noArc();
					D.setW1(w.pgetParent());
					D.rotate(w, index);
					mysuspend();
				}
			}
		}
		D.setW1(null);
		D.setW2(null);
		if (w.isRoot()) {
			D.lctree.set(index, w);
		}
		
	}

}
