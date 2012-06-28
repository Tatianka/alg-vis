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
package algvis.daryheap;

import algvis.core.DataStructure;
import algvis.core.Settings;
import algvis.gui.VisPanel;

public class DaryHeapPanel extends VisPanel{
	private static final long serialVersionUID = 5387116424458217311L;
	public static Class<? extends DataStructure> DS = DaryHeap.class;

	public DaryHeapPanel(Settings S) {
		super(S);
	}

	@Override
	public void initDS() {
		D = new DaryHeap(this);
		B = new DaryHeapButtons(this);
		D.random(14);
	}
}
