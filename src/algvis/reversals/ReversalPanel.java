package algvis.reversals;

import algvis.core.DataStructure;
import algvis.core.DictButtons;
import algvis.core.Settings;
import algvis.core.VisPanel;
import algvis.splaytree.Splay;

public class ReversalPanel extends VisPanel {
	private static final long serialVersionUID = 6838150190028844576L;
	public static Class<? extends DataStructure> DS = Splay.class;

	public ReversalPanel(Settings S) {
		super(S);
	}

	@Override
	public void initDS() {
		D = new Reversal(this);
		B = new DictButtons(this);
	}
}
