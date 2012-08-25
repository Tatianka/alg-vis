package algvis.linkcuttree;

public class Link extends LinkCutAlg {
	LinkCutDS D;
	LinkCutDSNode v, w;
	//SplayNodeM vv,ww;
	LCTree vv,ww;
	int indexx, indexy;
	
	public Link(LinkCutDS D, LinkCutDSNode v, LinkCutDSNode w, int indexx, int indexy,
			//SplayNodeM vv, SplayNodeM ww) {
			LCTree vv, LCTree ww) {
		super(D);
		this.D = D;
		this.v = v;
		this.w = w;
		this.indexx = indexx;
		this.indexy = indexy;
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
			evert(v, indexx);
			D.tree.set(indexx, v);
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
		
		Access2(vv, indexx);
	/*	if (vv.pgetRight() != null) {
			vv.psetLeft(vv.pgetRight());
			vv.psetRight(null);
			mysuspend();
		}
*/ //		mysuspend();
		Access(ww, indexy);
//		mysuspend();
//NEW	vv.addChild(ww);
		vv.unpref(vv.pgetLeft());
		vv.psetLeft(ww);
		ww.setParent(vv);
		D.reposition();
		mysuspend();
		
		D.tree.remove(indexx);
		D.lctree.set(indexy, D.lctree.get(indexx)); 
		D.lctree.remove(indexx);
	}
}
