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
package algvis.scapegoattree;

import algvis.core.NodeColor;

public class GBInsert extends GBAlg {
	public GBInsert(GBTree T, int x) {
		super(T, x);
		v.setColor(NodeColor.INSERT);
		setHeader("insert", K);
	}

	@Override
	public void run() {
		if (T.getRoot() == null) {
			T.setRoot(v);
			v.goToRoot();
			addStep("newroot");
			mysuspend();
			v.setColor(NodeColor.NORMAL);
		} else {
			GBNode w = (GBNode) T.getRoot();
			v.goAboveRoot();
			addStep("bst-insert-start");
			mysuspend();

			while (true) {
				if (w.key == K) {
					if (w.isDeleted()) {
						addStep("gbinsertunmark");
						w.setDeleted(false);
						w.setColor(NodeColor.NORMAL);
						T.setDel(T.getDel() - 1);
						T.setV(null);
					} else {
						addStep("alreadythere");
						v.goDown();
						v.setColor(NodeColor.NOTFOUND);
					}
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
			mysuspend();

			GBNode b = null;
			while (w != null) {
				w.calc();
				if (w.height > Math.ceil(T.alpha * T.lg(w.size)) && b == null) {
					b = w;
				}
				w = w.getParent();
			}

			// rebuilding
			if (b != null) {
				GBNode r = b;
				int s = 0;
				addStep("gbtoohigh");
				r.mark();
				mysuspend();
				// to vine
				addStep("gbrebuild1");
				while (r != null) {
					if (r.getLeft() == null) {
						r.unmark();
						if (r.isDeleted()) {
							T.setDel(T.getDel() - 1);
							if (b == r) {
								b = r.getRight();
							}
							T.setV(r);
							if (r.getParent() == null) {
								r = (GBNode) T.setRoot(r.getRight());
								if (r != null) {
									r.setParent(null);
								}
							} else {
								r.getParent().linkRight(r = r.getRight());
							}
							T.getV().goDown();
						} else {
							r = r.getRight();
							++s;
						}
						if (r != null) {
							r.mark();
						}
					} else {
						if (b == r) {
							b = r.getLeft();
						}
						r.unmark();
						r = r.getLeft();
						r.mark();
						T.rotate(r);
					}
					T.reposition();
					mysuspend();
				}

				// to tree
				addStep("gbrebuild2");
				int c = 1;
				for (int i = 0, l = (int) Math.floor(T.lg(s + 1)); i < l; ++i) {
					c *= 2;
				}
				c = s + 1 - c;

				b = compr(b, c);
				s -= c;
				while (s > 1) {
					b = compr(b, s /= 2);
				}
			}
		}
		T.reposition();
		addStep("done");
		T.setV(null);
	}
}
