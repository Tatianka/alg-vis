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
package algvis.btree;

import algvis.core.Algorithm;
import algvis.core.NodeColor;

public class BFind extends Algorithm {
	BTree T;
	BNode v;

	public BFind(BTree T, int x) {
		super(T);
		this.T = T;
		v = T.v = new BNode(T, x);
		v.setColor(NodeColor.FIND);
		setHeader("search");
	}

	@Override
	public void run() {
		if (T.root == null) {
			v.goToRoot();
			addStep("empty");
			mysuspend();
			v.goDown();
			v.setColor(NodeColor.NOTFOUND);
			addStep("notfound");
		} else {
			BNode w = T.root;
			v.goTo(w);
			addStep("bstfindstart");
			mysuspend();

			while (true) {
				if (w.isIn(v.key[0])) {
					addStep("found");
					v.goDown();
					v.setColor(NodeColor.FOUND);
					break;
				}
				if (w.isLeaf()) {
					addStep("notfound");
					v.setColor(NodeColor.NOTFOUND);
					v.goDown();
					break;
				}
				w = w.way(v.key[0]);
				v.goTo(w);
				mysuspend();
			}
		}
	}
}
