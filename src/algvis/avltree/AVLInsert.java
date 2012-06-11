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
package algvis.avltree;

import algvis.core.Algorithm;
import algvis.core.NodeColor;

public class AVLInsert extends Algorithm {
	AVL T;
	AVLNode v;
	int K;

	public AVLInsert(AVL T, int x) {
		super(T);
		this.T = T;
		v = (AVLNode) T.setV(new AVLNode(T, K = x));
		v.setColor(NodeColor.INSERT);
		setHeader("insert", K);
	}

	@Override
	public void run() {
		AVLNode w = (AVLNode) T.getRoot();
		if (T.getRoot() == null) {
			T.setRoot(v);
			v.goToRoot();
			addStep("newroot");
			mysuspend();
			v.setColor(NodeColor.NORMAL);
			T.setV(null);
		} else {
			v.goAboveRoot();
			addStep("bst-insert-start");
			mysuspend();

			while (true) {
				if (w.key == K) {
					addStep("alreadythere");
					v.goDown();
					v.setColor(NodeColor.NOTFOUND);
					return;
				} else if (w.key < K) {
					addStep("bst-insert-right", K, w.key);
					if (w.getRight() != null) {
						w = w.getRight();
					} else {
						w.linkRight(v);
						break;
					}
				} else {
					addStep("bst-insert-left", K, w.key);
					if (w.getLeft() != null) {
						w = w.getLeft();
					} else {
						w.linkLeft(v);
						break;
					}
				}
				v.goAbove(w);
				mysuspend();
			}

			v.setColor(NodeColor.NORMAL);
			T.reposition();
			addNote("avlinsertbal");
			mysuspend();

			v.setColor(NodeColor.NORMAL);
			T.setV(null);
			// bubleme nahor
			while (w != null) {
				w.mark();
				w.calc();
				addStep("avlupdatebal");
				mysuspend();
				if (w.balance() == -2) {
					if (w.getLeft().balance() != +1) { // R-rot
						addStep("avlr");
						w.unmark();
						w = w.getLeft();
						w.mark();
						w.setArc(w.getParent());
						mysuspend();
						w.noArc();
						T.rotate(w);
					} else { // LR-rot
						addStep("avllr");
						w.unmark();
						w = w.getLeft().getRight();
						w.mark();
						w.setArc(w.getParent());
						w.getParent().setArc(w.getParent().getParent());
						mysuspend();
						w.noArc();
						w.getParent().noArc();
						T.rotate(w);
						mysuspend();
						T.rotate(w);
					}
					mysuspend();
				} else if (w.balance() == +2) {
					if (w.getRight().balance() != -1) { // L-rot
						addStep("avll");
						w.unmark();
						w = w.getRight();
						w.mark();
						w.setArc(w.getParent());
						mysuspend();
						w.noArc();
						T.rotate(w);
					} else { // RL-rot
						addStep("avlrl");
						w.unmark();
						w = w.getRight().getLeft();
						w.mark();
						w.setArc(w.getParent());
						w.getParent().setArc(w.getParent().getParent());
						mysuspend();
						w.noArc();
						w.getParent().noArc();
						T.rotate(w);
						mysuspend();
						T.rotate(w);
					}
					mysuspend();
				}
				w.unmark();
				w = w.getParent();
			}
		}
		T.reposition();
		addNote("done");
	}
}
