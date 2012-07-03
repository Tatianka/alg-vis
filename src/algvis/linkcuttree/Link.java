package algvis.linkcuttree;

public class Link extends LinkCutAlg {
	LinkCutDS D;
	LinkCutDSNode v, w;
	SplayNodeM vv,ww;
	int index;
	
	public Link(LinkCutDS D, LinkCutDSNode v, LinkCutDSNode w, int index, SplayNodeM vv, SplayNodeM ww) {
		super(D);
		this.D = D;
		this.v = v;
		this.w = w;
		this.index = index;
		this.vv = vv;
		this.ww = ww;
		setHeader("link");
	}

	@Override
	public void run() {
		if (v==null || w==null) {return;}
		v.mark();
		w.mark();
		if (!v.isRoot()) {
			addStep("lct-evert");
			mysuspend();
			evert(v, index);
			D.tree.set(index, v);
			addStep("lct-eroot", v.getKey());
			D.reposition();
			mysuspend();
		}
		addStep("lct-prefpath", w.getKey());
		mysuspend();
		link(v,w);
		v.unmark();
		w.unmark();
		D.reposition();
		mysuspend();
		Access(vv, vv.getDatastructure().D);
		Access(ww, ww.getDatastructure().D);
		if (ww.getLeft() != null) {
			SplayTreeM T = new SplayTreeM(w.D.M, ww.getDatastructure().D);
			T.setRoot(ww.getLeft());
			T.pathparent = ww;
			ww.getDatastructure().add(ww, T);
			ww.getLeft().setParent(null);
		}
		ww.setLeft(vv);
		vv.setParent(ww);
		mysuspend();		
	}
}
