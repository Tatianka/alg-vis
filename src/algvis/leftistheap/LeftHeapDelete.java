package algvis.leftistheap;

public class LeftHeapDelete extends LeftHeapAlg {
	int i;

	public LeftHeapDelete(LeftHeap H, int i) {
		super(H);
		this.i = i;
		setHeader("deletion");
	}

	@Override
	public void run() {
		if (H.root[i] == null) {
			return;
		}

		if (!H.minHeap) {
			addStep("maximum", H.root[i].key);
		} else {
			addStep("minimum", H.root[i].key);
		}

		mysuspend();

		LeftHeapNode tmp = H.root[i];
		H.root[i] = tmp.getLeft();
		H.root[0] = tmp.getRight();
		tmp = null;

		if (H.root[i] == null) {
			H.root[i] = H.root[0];
			H.root[0] = null;
			if (H.root[i] != null) {
				H.root[i].highlightTree();
				H.root[i].repos(H.root[i].x, H.root[i].y
						- (LeftHeap.minsepy));// + 2 * LeftHeapNode.radius));
			}
			// heap #1 is empty; done;
			return;
		}
		H.root[i].setParent(null);

		if (H.root[0] == null) {
			H.root[i].repos(H.root[i].x, H.root[i].y - (LeftHeap.minsepy));// + 2 * LeftHeapNode.radius));
			// heap #2 is empty; done;
			return;
		}
		H.root[0].setParent(null);

		H.root[i].repos(H.root[i].x, H.root[i].y - (LeftHeap.minsepy));// + 2 * LeftHeapNode.radius));
		H.root[0].repos(H.root[0].x, H.root[0].y - (LeftHeap.minsepy));// + 2 * H.radius));

		mysuspend();
		meld(i);

	}

}
