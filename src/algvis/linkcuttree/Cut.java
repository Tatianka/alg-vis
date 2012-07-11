package algvis.linkcuttree;

public class Cut extends LinkCutAlg {
	LinkCutDSNode v;
	LCTree vv;
	int index;

	public Cut(LinkCutDS D, LinkCutDSNode v, LCTree vv, int index) {
		super(D);
		this.v = v;
		this.vv = vv;
		this.index = index;
		setHeader("cut");
	}
	
	public void run() {
		if (v == null) { return; }
		if (D.tree.get(index) == v) {
			return;
		}
		v.mark();
		addStep("lct-prefpath", v.getKey());
		mysuspend();
		cut(v);
		v.unmark();
		D.tree.add(v);
		
		mysuspend();
		Access(vv, index);
		if (vv.pgetLeft() != null) {
			D.lctree.set(index, vv.pgetLeft());
			D.lctree.add(vv);
			vv.pgetLeft().setParent(null);
			vv.deleteChild(vv.pgetLeft());
			vv.psetLeft(null);
		}
	}

}
