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
package algvis.core;

import java.util.LinkedList;
import java.util.List;

import algvis.gui.view.Layout;
import algvis.gui.view.LayoutListener;
import algvis.internationalization.Languages;

public class Settings {
	public Layout layout;
	public Languages L;
	List<LayoutListener> listeners = new LinkedList<LayoutListener>();

	public Settings(Languages L) {
		this.L = L;
		layout = Layout.SIMPLE;
	}

	public void setLayout(String s) {
		if ("compact".equals(s)) {
			layout = Layout.COMPACT;
		} else {
			layout = Layout.SIMPLE;
		}
		for (LayoutListener l : listeners) {
			l.changeLayout();
		}
	}

	public void addLayoutListener(LayoutListener l) {
		listeners.add(l);
	}
}
