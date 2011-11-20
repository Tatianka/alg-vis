package algvis.fingertree;

import algvis.core.DataStructure;
import algvis.core.DictButtons;
import algvis.core.VisPanel;
import algvis.internationalization.Languages;

public class FingerTreePanel extends VisPanel {
	private static final long serialVersionUID = 570226365393418572L;
	public static Class<? extends DataStructure> DS = FingerTree.class;

	public FingerTreePanel(Languages L) {
		super(L);
	}

	@Override
	public String getTitle() {
		return "fingertree";
	}

	@Override
	public void initDS() {
		D = new FingerTree(this);
		B = new DictButtons(this);		
	}
	 
	
}
