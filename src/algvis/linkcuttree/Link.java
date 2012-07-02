package algvis.linkcuttree;

public class Link extends LinkCutAlg {
	LinkCutDS D;
	LinkCutDSNode v, w;
	int index;
	
	public Link(LinkCutDS D, LinkCutDSNode v, LinkCutDSNode w, int index) {
		super(D);
		this.D = D;
		this.v = v;
		this.w = w;
		this.index = index;
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
	}
}
