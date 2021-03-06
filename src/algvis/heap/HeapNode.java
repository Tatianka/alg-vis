/*******************************************************************************
 * Copyright (c) 2012 Jakub Kováč, Katarína Kotrlová, Pavol Lukča, Viktor Tomkovič, Tatiana Tóthová
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
package algvis.heap;

import algvis.bst.BSTNode;
import algvis.core.DataStructure;
import algvis.core.Node;
import algvis.core.PriorityQueue;

public class HeapNode extends BSTNode {
	// TODO not needed?
	// HeapNode left, right, parent;
	// Color color = Color.yellow;
	// int height = 1;

	public HeapNode(DataStructure D, int key, int x, int y) {
		super(D, key, x, y);
		bgKeyColor();
	}

	public HeapNode(DataStructure D, int key) {
		super(D, key);
		bgKeyColor();
	}

	public HeapNode(HeapNode v) {
		this(v.D, v.getKey(), v.tox, v.toy);
		// TODO !!! v tychto konstruktoroch (mozno aj na inych miestach v
		// programe) by malo byt tox a toy, lebo pri vykonavani algoritmu
		// (vytvarani scenara) este vrchol nie je na [tox,toy], lebo sa na nom
		// ani raz nezavola move(); je stale na [x,y]
		// - s tymto je problem (len) ked nekrokujeme prve spustenie algoritmu;
		// aj pri rychlom krokovani, ked sa v este nedostane na [tox,toy]
	}

	@Override
	public HeapNode getLeft() {
		return (HeapNode) super.getLeft();
	}

	@Override
	public HeapNode getRight() {
		return (HeapNode) super.getRight();
	}

	@Override
	public HeapNode getParent() {
		return (HeapNode) super.getParent();
	}

	/**
	 * v.prec(w) iff v precedes w in the heap order, i.e., should be higher in
	 * the heap v precedes w if v.key < w.key when we have a min heap, but v
	 * precedes w if v.key > w.key when we have a max heap
	 */
	public boolean prec(Node v) {
		if (((PriorityQueue) D).minHeap) {
			return this.getKey() < v.getKey();
		} else {
			return this.getKey() > v.getKey();
		}
	}

	/**
	 * Precedes or equals (see prec).
	 */
	public boolean preceq(Node v) {
		if (((PriorityQueue) D).minHeap) {
			return this.getKey() <= v.getKey();
		} else {
			return this.getKey() >= v.getKey();
		}
	}
}
