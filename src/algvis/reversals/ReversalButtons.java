package algvis.reversals;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Random;
import java.util.Vector;

import javax.swing.JPanel;

import algvis.core.Buttons;
import algvis.core.Dictionary;
import algvis.core.InputField;
import algvis.core.VisPanel;
import algvis.internationalization.IButton;

public class ReversalButtons extends Buttons {
	private static final long serialVersionUID = 2721786904758486239L;
	IButton insertB, findB, reverseB;	

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

		reverseB = new IButton(M.S.L, "button-reverse");
		reverseB.setMnemonic(KeyEvent.VK_R);
		reverseB.addActionListener(this);

		P.add(insertB);
		P.add(findB);
		P.add(reverseB);
	}
	
	@Override
	public void actionPerformed(ActionEvent evt) {
		super.actionPerformed(evt);
		final Reversal D = (Reversal) this.D;
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
		} else if (evt.getSource() == reverseB) {
			final Vector<Integer> args = I.getVI(1, D.max-1);
		/*	int count = D.max;
			if (D.firstSelected != null) {
				args.insertElementAt(D.order(D.firstSelected), 0); 
			//	D.firstSelected = null;
			}
			if (D.secondSelected != null) {
				args.insertElementAt(D.order(D.secondSelected), 1);
			//	D.secondSelected = null;
			}
			Random G = new Random(System.currentTimeMillis());
			switch (args.size()) {
			case 0:
				args.add(G.nextInt(count) + 1);
			case 1:
				int i;
				int ii = args.elementAt(0);
				do {
					i = G.nextInt(count) + 1;
				} while (i == ii);
				args.add(i);
			}*/

			if (args.size() > 0) {
				Thread t = new Thread(new Runnable() {
					@Override
					public void run() {
						System.out.println(args.elementAt(0));
						System.out.println(args.elementAt(1));
						if (args.elementAt(0) < args.elementAt(1))	{
							((Reversal) D).reverse(args.elementAt(0) ,args.elementAt(1) );
						} else {
							((Reversal) D).reverse(args.elementAt(1) ,args.elementAt(0) );							
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
		first.add(I);
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
		reverseB.setEnabled(true);
	}

	@Override
	public void disableAll() {
		super.disableAll();
		insertB.setEnabled(false);
		findB.setEnabled(false);
		reverseB.setEnabled(false);
	}

}
