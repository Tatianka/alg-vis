package algvis.reversals;

import algvis.core.DataStructure;
import algvis.core.Settings;
import algvis.gui.VisPanel;

public class ReversalPanel extends VisPanel {
	private static final long serialVersionUID = 6838150190028844576L;
	public static Class<? extends DataStructure> DS = Reversal.class;

	public ReversalPanel(Settings S) {
		super(S);
	}

	@Override
	public void initDS() {
		D = new Reversal(this);
		B = new ReversalButtons(this);
	}
}
