package algvis.linkcuttree;

import algvis.core.Algorithm;
import algvis.core.NodeColor;
import algvis.core.NodePair;
import algvis.splaytree.SplayAlg;

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
	
	public void Access(SplayNodeM v, LinkCutTree C) {
		SplayAlg alg = new SplayAlg(v.getDatastructure(), v.getKey());
		alg.splay(v);
		SplayTreeM T;
		if (v.getRight() != null) {
			T = new SplayTreeM(v.D.M, v.getDatastructure().D);
			T.setRoot(v.getRight());
			T.pathparent = v;
//			v.pathtree.add(T);
			v.getDatastructure().add(v, T);
			v.getRight().setParent(null);
			v.setRight(null);
		}
		SplayNodeM vt = v, w, r = v.getDatastructure().D.getRoot().getRoot();
		if (v.getDatastructure().pathparent == null) {
			System.out.println("Som null.");
		}
		System.out.println(r.getKey());
		while (/*vt != r*/!vt.getDatastructure().tisRoot()/* vt != C.getRoot().getRoot()*/) {
			w = vt.getDatastructure().pathparent;
			alg = new SplayAlg(w.getDatastructure(), w.getKey());
			alg.splay(w);
			T = new SplayTreeM(w.D.M, w.getDatastructure().D);
			T.setRoot(w.getRight());
			T.pathparent = w;
//			w.pathtree.add(T);
			w.getDatastructure().add(w, T);
			T.getRoot().setParent(null);
			w.setRight(vt);
//			vt.getDatastructure().pathparent.pathtree = null;
			vt.getDatastructure().pathparent.getDatastructure().remove(vt.getDatastructure().pathparent);
			vt.getDatastructure().pathparent = null;
			vt = w;
		}
		alg = new SplayAlg(v.getDatastructure(), v.getKey());
		alg.splay(v);		
	}
}
