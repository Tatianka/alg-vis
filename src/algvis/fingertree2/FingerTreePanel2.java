package algvis.fingertree2;

import algvis.core.DataStructure;
import algvis.core.DictButtons;
import algvis.core.VisPanel;
import algvis.internationalization.Languages;

public class FingerTreePanel2 extends VisPanel {
	private static final long serialVersionUID = 570226365393417572L;
	public static Class<? extends DataStructure> DS = FingerTree2.class;

	public FingerTreePanel2(Languages L) {
		super(L);
	}

	@Override
	public String getTitle() {
		return "fingertree";
	}

	@Override
	public void initDS() {
		D = new FingerTree2(this);
		B = new DictButtons(this);		
	}
	 
	
}
