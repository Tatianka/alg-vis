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

import org.jdom.Element;

import algvis.bst.BSTNode;
import algvis.core.DataStructure;
import algvis.scenario.Command;

public class GBNode extends BSTNode {
	private boolean deleted = false;

	public GBNode(DataStructure D, int key, int x, int y) {
		super(D, key, x, y);
	}

	public GBNode(DataStructure D, int key) {
		super(D, key);
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		if (this.deleted != deleted) {
			if (D.scenario.isAddingEnabled()) {
				D.scenario.add(new SetDeletedCommand(deleted));
			}
			this.deleted = deleted;
		}
	}

	@Override
	public GBNode getLeft() {
		return (GBNode) super.getLeft();
	}

	@Override
	public GBNode getRight() {
		return (GBNode) super.getRight();
	}

	@Override
	public GBNode getParent() {
		return (GBNode) super.getParent();
	}

	private class SetDeletedCommand implements Command {
		private final boolean oldDeleted, newDeleted;

		public SetDeletedCommand(boolean newDeleted) {
			oldDeleted = isDeleted();
			this.newDeleted = newDeleted;
		}

		@Override
		public Element getXML() {
			Element e = new Element("setDeleted");
			e.setAttribute("key", Integer.toString(key));
			e.setAttribute("wasDeleted", Boolean.toString(oldDeleted));
			e.setAttribute("isDeleted", Boolean.toString(newDeleted));
			return e;
		}

		@Override
		public void execute() {
			setDeleted(newDeleted);
		}

		@Override
		public void unexecute() {
			setDeleted(oldDeleted);
		}
	}
}
