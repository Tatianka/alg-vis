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
	
	public void Access(SplayNodeM v, LinkCutTree C, int index) {
		SplayAlg alg = new SplayAlg(v.getDatastructure(), v.getKey());
		alg.splay(v);
		System.out.println(v.getDatastructure().getRoot().getKey());
		if (D.lctree.get(0) == C) {
			System.out.println("Tu nemame problem.");
		}
		D.lctree.set(index, C);
		D.reposition();
		mysuspend();
		SplayTreeM T;
	/*	if (v.getRight() != null) {
			T = new SplayTreeM(C.M, C);
			T.setRoot(v.getRight());
			T.pathparent = v;
//			v.pathtree.add(T);
			v.getDatastructure().add(v, T);
			v.getRight().setParent(null);
			v.setRight(null);
			
			D.lctree.set(index, C);
			D.reposition();
			mysuspend();
		/*	if (v.getDatastructure().tisRoot()) {
				v.getDatastructure().D.setRoot(v.getDatastructure());
			}*/
	//	}
		SplayNodeM vt = v, w, r = C.getRoot().getRoot();
		if (v.getDatastructure().pathparent == null) {
			System.out.println("Som null.");
		}
		System.out.println(r.getKey());
		System.out.println(v.getDatastructure().getRoot().getKey());
		System.out.println(v.getDatastructure().D.getRoot().getRoot().getKey());
		while (vt.getDatastructure() != C.getRoot()/*vt != r*//*!vt.getDatastructure().tisRoot()/* vt != C.getRoot().getRoot()*/) {
			System.out.println("Som tuuu.");
			w = vt.getDatastructure().pathparent;
			alg = new SplayAlg(w.getDatastructure(), w.getKey());
			alg.splay(w);
			T = new SplayTreeM(w.D.M, w.getDatastructure().D);
			if (w.getRight() != null) {
			T.setRoot(w.getRight());
				T.pathparent = w;
	//			w.pathtree.add(T);
				w.getDatastructure().add(w, T);
				T.getRoot().setParent(null);
			}
			w.setRight(vt);
			vt.setParent(w);
//			vt.getDatastructure().pathparent.pathtree = null;
			vt.getDatastructure().pathparent.getDatastructure().remove(vt.getDatastructure().pathparent);
			vt.getDatastructure().pathparent = null;
			vt = w;
			D.lctree.set(index, C);
			D.reposition();
			mysuspend();
		}
		alg = new SplayAlg(v.getDatastructure(), v.getKey());
		alg.splay(v);
		D.lctree.set(index, C);
		D.reposition();
		mysuspend();
	}
}
