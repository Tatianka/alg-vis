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
package algvis.splaytree;

import algvis.bst.BSTNode;
import algvis.core.DataStructure;

public class SplayNode extends BSTNode {
	int pot = 0;

	public SplayNode(DataStructure D, int key, int x, int y) {
		super(D, key, x, y);
	}

	public SplayNode(DataStructure D, int key) {
		this(D, key, 0, 0);
		getReady();
	}

	@Override
	public SplayNode getLeft() {
		return (SplayNode) super.getLeft();
	}

	@Override
	public SplayNode getRight() {
		return (SplayNode) super.getRight();
	}

	@Override
	public SplayNode getParent() {
		return (SplayNode) super.getParent();
	}

	@Override
	public void calc() {
		super.calc();
		int lp = 0, rp = 0;
		if (getLeft() != null) {
			lp = getLeft().pot;
		}
		if (getRight() != null) {
			rp = getRight().pot;
		}
		pot = (int) Math.floor(D.lg(size)) + lp + rp;
	}

	@Override
	public void calcTree() {
		if (getLeft() != null) {
			getLeft().calcTree();
		}
		if (getRight() != null) {
			getRight().calcTree();
		}
		calc();
	}
}
