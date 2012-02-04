package algvis.reversals;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Vector;

import javax.swing.JPanel;

import algvis.core.Buttons;
import algvis.core.Dictionary;
import algvis.core.InputField;
import algvis.core.VisPanel;
import algvis.internationalization.IButton;

public class ReversalButtons extends Buttons {
	private static final long serialVersionUID = 2721786904758486239L;
	IButton insertB, findB, reversB;
	InputField I2;
	

	public ReversalButtons(VisPanel M) {
		super(M);
	}

	@Override
	public void actionButtons(JPanel P) {
		insertB = new IButton(M.S.L, "button-insert");
		insertB.setMnemonic(KeyEvent.VK_I);
		insertB.addActionListener(this);

		findB = new IButton(M.S.L, "find");
		findB.setMnemonic(KeyEvent.VK_F);
		findB.addActionListener(this);

		reversB = new IButton(M.S.L, "button-revers");
		reversB.setMnemonic(KeyEvent.VK_R);
		reversB.addActionListener(this);

		P.add(insertB);
		P.add(findB);
		P.add(reversB);
	}
	
	@Override
	public void actionPerformed(ActionEvent evt) {
		super.actionPerformed(evt);
		if (evt.getSource() == insertB) {
			Thread t = new Thread(new Runnable() {
				@Override
				public void run() {
						((Reversal) D).insert();
				}
			});
			t.start();
		} else if (evt.getSource() == findB) {
			final Vector<Integer> args = I.getVI();
			if (args.size() > 0) {
				Thread t = new Thread(new Runnable() {
					@Override
					public void run() {
						for (int x : args) {
							((Dictionary) D).find(x);
						}
					}
				});
				t.start();
			}
		} else if (evt.getSource() == reversB) {
			final Vector<Integer> args = I.getVI();
			final Vector<Integer> args2 = I2.getVI();
			if (args.size() > 0) {
				Thread t = new Thread(new Runnable() {
					@Override
					public void run() {
						for (int x : args) {
							for (int y : args2) {
								((Reversal) D).revers(x,y);
							}
						}
					}
				});
				t.start();
			}
		}
	}
	
	@Override
	public JPanel initFirstRow() {
		JPanel first = new JPanel();
		first.setLayout(new FlowLayout());

		I = new InputField(5, M.statusBar);
		I2 = new InputField(5, M.statusBar);
		first.add(I);
		first.add(I2);
		actionButtons(first);
		initPrevious();
		initNext();
		first.add(previous);
		first.add(next);
		actionButtons2(first);
		return first;		
	}
	
	@Override
	public void enableAll() {
		super.enableAll();
		insertB.setEnabled(true);
		findB.setEnabled(true);
		reversB.setEnabled(true);
	}

	@Override
	public void disableAll() {
		super.disableAll();
		insertB.setEnabled(false);
		findB.setEnabled(false);
		reversB.setEnabled(false);
	}

}
