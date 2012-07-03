package algvis.linkcuttree;

import algvis.core.DataStructure;
import algvis.gui.VisPanel;
import algvis.gui.view.View;

public class LinkCutTree extends DataStructure {
	LinkCutDS D;
	SplayTreeM root = null;

	public LinkCutTree(VisPanel M, LinkCutDS D) {
		super(M);
		this.D = D;
	}
	
	public SplayNodeM find(int x) {
		return getRoot().search(x);
	}

	public SplayTreeM getRoot() {
		return root;
	}
	
	public void setRoot(SplayTreeM v) {
		root = v;
	}
	
	@Override
	public void clear() {
		setRoot(null);
	}

	@Override
	public void draw(View v) {
		if (getRoot() != null) {
			getRoot().draw(v);
		}		
	}
	
	public void reposition() {
		if (getRoot() == null) {return;}
		getRoot().reposition();
/*		SplayNodeM w = getRoot().leftMost;
		Vector<SplayTreeM> s = new Vector<SplayTreeM>();
		while (w != null) {
			if (w.pathtree != null) {
				s.add(w.pathtree);
			}
			w = w.next();
		}
		for(int i=0; i<s.size(); i++) {
			s.get(i).reposition();
		}*/
	}

	@Override
	public String getName() {
		return null;
	}

	@Override
	public void insert(int x) {
	}

	@Override
	public String stats() {
		return null;
	}

}
