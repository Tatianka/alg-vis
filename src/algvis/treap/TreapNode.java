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
package algvis.treap;

import java.awt.Color;

import algvis.bst.BSTNode;
import algvis.core.DataStructure;
import algvis.core.NodeColor;

public class TreapNode extends BSTNode {
	final double p;

	public TreapNode(DataStructure D, int key, int x, int y) {
		super(D, key, x, y);
		p = Math.random();
		bgPColor();
	}

	public TreapNode(DataStructure D, int key) {
		this(D, key, 0, 0);
		getReady();
	}

	@Override
	public TreapNode getLeft() {
		return (TreapNode) super.getLeft();
	}

	@Override
	public TreapNode getRight() {
		return (TreapNode) super.getRight();
	}

	@Override
	public TreapNode getParent() {
		return (TreapNode) super.getParent();
	}

	public void bgPColor() {
		bgColor(new Color(255, 255 - (int) Math.round(100 * p), 0));
	}

	@Override
	public void setColor(NodeColor color) {
		if (color == NodeColor.NORMAL) {
			bgPColor();
		} else {
			super.setColor(color);
		}
	}
}
