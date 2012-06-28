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
package algvis.pairingheap;

public class PairHeapInsert extends PairHeapAlg{
	int K;
	int i;
	PairHeapNode w;
	
	public PairHeapInsert(PairingHeap H, int i, int x) {
		super(H);
		this.i = i;
		H.root[0] = new PairHeapNode(H, K = x);
		setHeader("insertion");
	}
	
	
	@Override
	public void run() {

		H.reposition();

		if (H.root[i] == null) {
			H.root[i] = H.root[0];
			if (H.root[i] != null) {
				addStep("newroot");
				H.root[i].highlightTree();
			}			
		} else {
			H.root[i].highlightTree();
			H.root[i].mark();
			//kedze je <cislo> viac/menej ako <cislo> tak to prilinkujeme k tomu
			if (H.root[i].getKey() < H.root[0].getKey()){
				if(H.minHeap){
					addStep("pairlinkmin", H.root[i].getKey(), H.root[0].getKey());
				} else {
					addStep("pairlinkmax", H.root[i].getKey(), H.root[0].getKey());
				}
			} else {
				if(H.minHeap){
					addStep("pairlinkmin", H.root[0].getKey(), H.root[i].getKey());
				} else {
					addStep("pairlinkmax", H.root[0].getKey(), H.root[i].getKey());
				}
			}
			mysuspend();
			H.root[i].unmark();
			link(i,0);
			H.reposition();
		}
		H.root[0] = null;
		H.reposition();
		addNote("done");
	}
}
