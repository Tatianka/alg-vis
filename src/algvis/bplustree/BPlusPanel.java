package algvis.bplustree;

import algvis.core.DataStructure;
import algvis.core.Settings;
import algvis.core.VisPanel;

public class BPlusPanel extends VisPanel {
	private static final long serialVersionUID = 8114215268825709283L;
	public static Class<? extends DataStructure> DS = BPlusTree.class;

	public BPlusPanel(Settings S) {
		super(S);
	}

	@Override
	public void initDS() {
		D = new BPlusTree(this);
		B = new BPlusTreeButtons(this);
	}

}
