/*******************************************************************************
 * Copyright (c) 2012 Jakub Kov��, Katar�na Kotrlov�, Pavol Luk�a, Viktor Tomkovi�, Tatiana T�thov�
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package algvis.leftistheap;

public class LeftHeapInsert extends LeftHeapAlg {
	int K;
	int i;

	public LeftHeapInsert(LeftHeap H, int i, int x) {
		super(H);
		this.i = i;
		H.root[0] = new LeftHeapNode(H, K = x);
		setHeader("insertion");
	}

	@Override
	public void run() {

		H.reposition();

		if (H.root[i] == null) {
			H.root[i] = H.root[0];
			H.root[0] = null;
			if (H.root[i] != null) {
				addStep("newroot");
				H.root[i].highlightTree();
			}
			H.reposition();
			// heap #1 is empty; done;
			return;
		}

		if (H.root[0] == null) {
			// heap #2 is empty; done;
			return;
		}

		H.active = i;
		H.root[0].highlightTree();
		H.reposition();

		mysuspend();
		meld(i);
	}

}
