/*! Copyright (C) 2009-2012 Apertus, All Rights Reserved
 *! Author : Sebastian Pichelhofer
-----------------------------------------------------------------------------**
 *!
 *!  This program is free software: you can redistribute it and/or modify
 *!  it under the terms of the GNU General Public License as published by
 *!  the Free Software Foundation, either version 3 of the License, or
 *!  (at your option) any later version.
 *!
 *!  This program is distributed in the hope that it will be useful,
 *!  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *!  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *!  GNU General Public License for more details.
 *!
 *!  You should have received a copy of the GNU General Public License
 *!  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *!
-----------------------------------------------------------------------------**/

import java.awt.CardLayout;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.text.Caret;
import net.miginfocom.swing.MigLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.GridLayout;
import javax.swing.JLabel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.Rectangle;

public class IPInputDialog extends javax.swing.JPanel {
    private JTextField FieldParent;
    private String TargetCard;
    private ElphelVision Parent;
    private int FocusOwnerIndex = 0;
    private JLabel VarNameLabel;
    private javax.swing.JTextField NumberField1;
    private javax.swing.JTextField NumberField2;
    private javax.swing.JTextField NumberField3;
    private javax.swing.JTextField NumberField4;
    private JLabel label;
    private JLabel label_1;
    private JLabel label_2;
    private EButton ButtonLeft;
    private EButton ButtonRight;
    private JPanel ConfirmationPanel;
    private EButton ButtonOK;
    private EButton ButtonCancel;
    private JPanel KeyPad;
    private EButton Button1;
    private EButton Button2;
    private EButton Button3;
    private EButton ButtonPlus10;
    private EButton ButtonBackSpace;
    private EButton Button4;
    private EButton Button5;
    private EButton Button6;
    private EButton ButtonPlus1;
    private EButton ButtonDel;
    private EButton ButtonClearAll;
    private EButton ButtonMinus1;
    private EButton ButtonMinus10;
    private EButton Button7;
    private EButton Button9;
    private EButton Button0;
    private EButton Button8;
    private EButton ButtonClear;

    public IPInputDialog(ElphelVision parent) {
	setBackground(Color.BLACK);
	setPreferredSize(new Dimension(1024, 600));
	setMinimumSize(new Dimension(1024, 600));
	this.Parent = parent;
	setLayout(new BorderLayout(0, 0));

	JPanel bg = new JPanel();
	bg.setBackground(Color.BLACK);
	add(bg);
	bg.setLayout(new MigLayout("", "[]", "[][][]"));

	JPanel NumberPanel = new JPanel();
	NumberPanel.setBackground(Color.BLACK);
	bg.add(NumberPanel, "cell 0 1,alignx left,aligny top");
	NumberPanel.setLayout(new MigLayout("", "[][][][][][][]", "[][][]"));

	NumberField1 = new javax.swing.JTextField();
	NumberField1.setMargin(new Insets(5, 5, 5, 5));
	NumberField1.setFont(new Font("DejaVu Sans", Font.PLAIN, 16));
	NumberField1.setBounds(new Rectangle(5, 5, 5, 5));
	NumberField1.setCaretColor(Color.WHITE);
	NumberField1.setForeground(Color.WHITE);
	NumberField1.setBackground(Color.BLACK);
	NumberField1.setMinimumSize(new Dimension(80, 40));

	NumberField2 = new javax.swing.JTextField();
	NumberField2.setMargin(new Insets(5, 5, 5, 5));
	NumberField2.setFont(new Font("DejaVu Sans", Font.PLAIN, 16));
	NumberField2.setBounds(new Rectangle(5, 5, 5, 5));
	NumberField2.setCaretColor(Color.WHITE);
	NumberField2.setForeground(Color.WHITE);
	NumberField2.setBackground(Color.BLACK);
	NumberField2.setMinimumSize(new Dimension(80, 40));

	NumberField3 = new javax.swing.JTextField();
	NumberField3.setMargin(new Insets(5, 5, 5, 5));
	NumberField3.setFont(new Font("DejaVu Sans", Font.PLAIN, 16));
	NumberField3.setBounds(new Rectangle(5, 5, 5, 5));
	NumberField3.setCaretColor(Color.WHITE);
	NumberField3.setForeground(Color.WHITE);
	NumberField3.setBackground(Color.BLACK);
	NumberField3.setMinimumSize(new Dimension(80, 40));

	NumberField4 = new javax.swing.JTextField();
	NumberField4.setMargin(new Insets(5, 5, 5, 5));
	NumberField4.setFont(new Font("DejaVu Sans", Font.PLAIN, 16));
	NumberField4.setBounds(new Rectangle(5, 5, 5, 5));
	NumberField4.setCaretColor(Color.WHITE);
	NumberField4.setForeground(Color.WHITE);
	NumberField4.setBackground(Color.BLACK);
	NumberField4.setMinimumSize(new Dimension(80, 40));

	VarNameLabel = new JLabel("Name");
	VarNameLabel.setForeground(Color.WHITE);
	VarNameLabel.setFont(new Font("DejaVu Sans", Font.BOLD, 16));
	NumberPanel.add(VarNameLabel, "cell 0 0 7 1,growx");

	NumberPanel.add(NumberField1, "cell 0 1");

	label = new JLabel(".");
	label.setForeground(Color.WHITE);
	label.setFont(new Font("DejaVu Sans", Font.BOLD, 16));
	NumberPanel.add(label, "cell 1 1,alignx trailing");
	NumberPanel.add(NumberField2, "cell 2 1");

	label_1 = new JLabel(".");
	label_1.setForeground(Color.WHITE);
	label_1.setFont(new Font("DejaVu Sans", Font.BOLD, 16));
	NumberPanel.add(label_1, "cell 3 1,alignx trailing");
	NumberPanel.add(NumberField3, "cell 4 1");

	label_2 = new JLabel(".");
	label_2.setForeground(Color.WHITE);
	label_2.setFont(new Font("DejaVu Sans", Font.BOLD, 16));
	NumberPanel.add(label_2, "cell 5 1,alignx trailing");
	NumberPanel.add(NumberField4, "cell 6 1");

	ButtonLeft = new EButton(this.Parent);
	ButtonLeft.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent arg0) {
		if (FocusOwnerIndex == 1) {
		    if (NumberField1.getCaretPosition() <= 0) { // do nothing
		    } else {
			NumberField1.setCaretPosition(NumberField1.getCaretPosition() - 1);
			NumberField1.getCaret().setVisible(true);
		    }
		} else if (FocusOwnerIndex == 2) {
		    if (NumberField2.getCaretPosition() <= 0) { // jump to
								// previous
								// textfield
			FocusOwnerIndex -= 1;
			NumberField1.requestFocus();
			NumberField1.setCaretPosition(NumberField1.getText().length()); // behind
											// last
											// character
			NumberField2.getCaret().setVisible(false);
		    } else {
			NumberField2.setCaretPosition(NumberField2.getCaretPosition() - 1);
			NumberField2.getCaret().setVisible(true);
		    }
		} else if (FocusOwnerIndex == 3) {
		    if (NumberField3.getCaretPosition() <= 0) { // jump to
								// previous
								// textfield
			FocusOwnerIndex -= 1;
			NumberField2.requestFocus();
			NumberField2.setCaretPosition(NumberField2.getText().length()); // behind
											// last
											// character
			NumberField3.getCaret().setVisible(false);
		    } else {
			NumberField3.setCaretPosition(NumberField3.getCaretPosition() - 1);
			NumberField3.getCaret().setVisible(true);
		    }
		} else if (FocusOwnerIndex == 4) {
		    if (NumberField4.getCaretPosition() <= 0) { // jump to
								// previous
								// textfield
			FocusOwnerIndex -= 1;
			NumberField3.requestFocus();
			NumberField3.setCaretPosition(NumberField3.getText().length()); // behind
											// last
											// character
			NumberField4.getCaret().setVisible(false);
		    } else {
			NumberField4.setCaretPosition(NumberField4.getCaretPosition() - 1);
			NumberField4.getCaret().setVisible(true);
		    }
		}
	    }
	});
	ButtonLeft.setText("<-");
	NumberPanel.add(ButtonLeft, "cell 0 2");

	ButtonRight = new EButton(this.Parent);
	ButtonRight.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		if (FocusOwnerIndex == 1) {
		    if (NumberField1.getCaretPosition() >= NumberField1.getText().length()) { // jump
											      // to
											      // next
											      // textfield
			FocusOwnerIndex += 1;
			NumberField2.requestFocus();
			NumberField2.setCaretPosition(0); // before first
							  // character
			NumberField1.getCaret().setVisible(false);
		    } else {
			NumberField1.setCaretPosition(NumberField1.getCaretPosition() + 1);
			NumberField1.getCaret().setVisible(true);
		    }
		} else if (FocusOwnerIndex == 2) {
		    if (NumberField2.getCaretPosition() >= NumberField2.getText().length()) { // jump
											      // to
											      // next
											      // textfield
			FocusOwnerIndex += 1;
			NumberField3.requestFocus();
			NumberField3.setCaretPosition(0); // before first
							  // character
			NumberField2.getCaret().setVisible(false);
		    } else {
			NumberField2.setCaretPosition(NumberField2.getCaretPosition() + 1);
			NumberField2.getCaret().setVisible(true);
		    }
		} else if (FocusOwnerIndex == 3) {
		    if (NumberField3.getCaretPosition() >= NumberField3.getText().length()) { // jump
											      // to
											      // next
											      // textfield
			FocusOwnerIndex += 1;
			NumberField4.requestFocus();
			NumberField4.setCaretPosition(0); // before first
							  // character
			NumberField3.getCaret().setVisible(false);
		    } else {
			NumberField3.setCaretPosition(NumberField3.getCaretPosition() + 1);
			NumberField3.getCaret().setVisible(true);
		    }
		} else if (FocusOwnerIndex == 4) {
		    if (NumberField4.getCaretPosition() >= NumberField4.getText().length()) { // do
											      // nothing
		    } else {
			NumberField4.setCaretPosition(NumberField4.getCaretPosition() + 1);
			NumberField4.getCaret().setVisible(true);
		    }
		}
	    }
	});
	ButtonRight.setText("->");
	NumberPanel.add(ButtonRight, "cell 6 2");

	KeyPad = new JPanel();
	KeyPad.setBackground(Color.BLACK);
	bg.add(KeyPad, "cell 0 2,grow");
	KeyPad.setLayout(new MigLayout("", "[100px][100px][100px][][][73.00]", "[][][][60px][]"));

	Button1 = new EButton(parent);
	Button1.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		AppendCharacter("1");
	    }
	});
	Button1.setText("1");
	Button1.setPreferredSize(new Dimension(100, 60));
	Button1.setFont(new Font("DejaVu Sans", Font.PLAIN, 18));
	KeyPad.add(Button1, "cell 0 0,alignx left,aligny top");

	Button2 = new EButton(parent);
	Button2.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		AppendCharacter("2");
	    }
	});
	Button2.setText("2");
	Button2.setPreferredSize(new Dimension(100, 60));
	Button2.setFont(new Font("DejaVu Sans", Font.PLAIN, 18));
	KeyPad.add(Button2, "cell 1 0,alignx left,aligny top");

	Button3 = new EButton(parent);
	Button3.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		AppendCharacter("3");
	    }
	});
	Button3.setText("3");
	Button3.setPreferredSize(new Dimension(100, 60));
	Button3.setFont(new Font("DejaVu Sans", Font.PLAIN, 18));
	KeyPad.add(Button3, "cell 2 0,alignx left,aligny top");

	ButtonPlus10 = new EButton(parent);
	ButtonPlus10.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		if (FocusOwnerIndex == 1) {
		    int temp = Integer.parseInt(NumberField1.getText()) + 10;
		    NumberField1.setText(String.valueOf(temp));
		}
		if (FocusOwnerIndex == 2) {
		    int temp = Integer.parseInt(NumberField2.getText()) + 10;
		    NumberField2.setText(String.valueOf(temp));
		}
		if (FocusOwnerIndex == 3) {
		    int temp = Integer.parseInt(NumberField3.getText()) + 10;
		    NumberField3.setText(String.valueOf(temp));
		}
		if (FocusOwnerIndex == 4) {
		    int temp = Integer.parseInt(NumberField4.getText()) + 10;
		    NumberField4.setText(String.valueOf(temp));
		}
		Validate();
	    }
	});
	ButtonPlus10.setText("+10");
	ButtonPlus10.setPreferredSize(new Dimension(100, 60));
	ButtonPlus10.setFont(new Font("DejaVu Sans", Font.PLAIN, 18));
	KeyPad.add(ButtonPlus10, "cell 3 0,alignx left,aligny top");

	ButtonBackSpace = new EButton(parent);
	ButtonBackSpace.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		if (FocusOwnerIndex == 1) {
		    if (NumberField1.getCaretPosition() != 0) { // only do
								// something if
								// the caret is
								// not the at
								// the beginning
			int tmpcaretpos = NumberField1.getCaretPosition();
			NumberField1.setText(NumberField1.getText().substring(0, NumberField1.getCaretPosition() - 1)
				+ NumberField1.getText().substring(NumberField1.getCaretPosition(), NumberField1.getText().length()));
			NumberField1.setCaretPosition(tmpcaretpos - 1);
		    }
		}
		if (FocusOwnerIndex == 2) {
		    if (NumberField2.getCaretPosition() != 0) { // only do
								// something if
								// the caret is
								// not the at
								// the beginning
			int tmpcaretpos = NumberField2.getCaretPosition();
			NumberField2.setText(NumberField2.getText().substring(0, NumberField2.getCaretPosition() - 1)
				+ NumberField2.getText().substring(NumberField2.getCaretPosition(), NumberField2.getText().length()));
			NumberField2.setCaretPosition(tmpcaretpos - 1);
		    }
		}
		if (FocusOwnerIndex == 3) {
		    if (NumberField3.getCaretPosition() != 0) { // only do
								// something if
								// the caret is
								// not the at
								// the beginning
			int tmpcaretpos = NumberField3.getCaretPosition();
			NumberField3.setText(NumberField3.getText().substring(0, NumberField3.getCaretPosition() - 1)
				+ NumberField3.getText().substring(NumberField3.getCaretPosition(), NumberField3.getText().length()));
			NumberField3.setCaretPosition(tmpcaretpos - 1);
		    }
		}
		if (FocusOwnerIndex == 4) {
		    if (NumberField4.getCaretPosition() != 0) { // only do
								// something if
								// the caret is
								// not the at
								// the beginning
			int tmpcaretpos = NumberField4.getCaretPosition();
			NumberField4.setText(NumberField4.getText().substring(0, NumberField4.getCaretPosition() - 1)
				+ NumberField4.getText().substring(NumberField4.getCaretPosition(), NumberField4.getText().length()));
			NumberField4.setCaretPosition(tmpcaretpos - 1);
		    }
		}
		Validate();
	    }
	});
	ButtonBackSpace.setText("Backspace");
	ButtonBackSpace.setPreferredSize(new Dimension(100, 60));
	ButtonBackSpace.setFont(new Font("DejaVu Sans", Font.PLAIN, 18));
	KeyPad.add(ButtonBackSpace, "cell 5 0");

	Button4 = new EButton(parent);
	Button4.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		AppendCharacter("4");
	    }
	});
	Button4.setText("4");
	Button4.setPreferredSize(new Dimension(100, 60));
	Button4.setFont(new Font("DejaVu Sans", Font.PLAIN, 18));
	KeyPad.add(Button4, "cell 0 1");

	Button5 = new EButton(parent);
	Button5.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		AppendCharacter("5");
	    }
	});
	Button5.setText("5");
	Button5.setPreferredSize(new Dimension(100, 60));
	Button5.setFont(new Font("DejaVu Sans", Font.PLAIN, 18));
	KeyPad.add(Button5, "cell 1 1");

	Button6 = new EButton(parent);
	Button6.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		AppendCharacter("6");
	    }
	});
	Button6.setText("6");
	Button6.setPreferredSize(new Dimension(100, 60));
	Button6.setFont(new Font("DejaVu Sans", Font.PLAIN, 18));
	KeyPad.add(Button6, "cell 2 1");

	ButtonPlus1 = new EButton(parent);
	ButtonPlus1.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		if (FocusOwnerIndex == 1) {
		    int temp = Integer.parseInt(NumberField1.getText()) + 1;
		    NumberField1.setText(String.valueOf(temp));
		}
		if (FocusOwnerIndex == 2) {
		    int temp = Integer.parseInt(NumberField2.getText()) + 1;
		    NumberField2.setText(String.valueOf(temp));
		}
		if (FocusOwnerIndex == 3) {
		    int temp = Integer.parseInt(NumberField3.getText()) + 1;
		    NumberField3.setText(String.valueOf(temp));
		}
		if (FocusOwnerIndex == 4) {
		    int temp = Integer.parseInt(NumberField4.getText()) + 1;
		    NumberField4.setText(String.valueOf(temp));
		}
		Validate();
	    }
	});
	ButtonPlus1.setText("+1");
	ButtonPlus1.setPreferredSize(new Dimension(100, 60));
	ButtonPlus1.setFont(new Font("DejaVu Sans", Font.PLAIN, 18));
	KeyPad.add(ButtonPlus1, "cell 3 1");

	ButtonDel = new EButton(parent);
	ButtonDel.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		if (FocusOwnerIndex == 1) {
		    if (NumberField1.getCaretPosition() != NumberField1.getText().length()) { // only
											      // do
											      // something
											      // if
											      // the
											      // caret
											      // is
											      // not
											      // the
											      // at
											      // the
											      // end
			int tmpcaretpos = NumberField1.getCaretPosition();
			NumberField1.setText(NumberField1.getText().substring(0, NumberField1.getCaretPosition())
				+ NumberField1.getText().substring(NumberField1.getCaretPosition() + 1, NumberField1.getText().length()));
			NumberField1.setCaretPosition(tmpcaretpos);
		    }
		}
		if (FocusOwnerIndex == 2) {
		    if (NumberField2.getCaretPosition() != NumberField2.getText().length()) { // only
											      // do
											      // something
											      // if
											      // the
											      // caret
											      // is
											      // not
											      // the
											      // at
											      // the
											      // end
			int tmpcaretpos = NumberField2.getCaretPosition();
			NumberField2.setText(NumberField2.getText().substring(0, NumberField2.getCaretPosition())
				+ NumberField2.getText().substring(NumberField2.getCaretPosition() + 1, NumberField2.getText().length()));
			NumberField2.setCaretPosition(tmpcaretpos);
		    }
		}
		if (FocusOwnerIndex == 3) {
		    if (NumberField3.getCaretPosition() != NumberField3.getText().length()) { // only
											      // do
											      // something
											      // if
											      // the
											      // caret
											      // is
											      // not
											      // the
											      // at
											      // the
											      // end
			int tmpcaretpos = NumberField3.getCaretPosition();
			NumberField3.setText(NumberField3.getText().substring(0, NumberField3.getCaretPosition())
				+ NumberField3.getText().substring(NumberField3.getCaretPosition() + 1, NumberField3.getText().length()));
			NumberField3.setCaretPosition(tmpcaretpos);
		    }
		}
		if (FocusOwnerIndex == 4) {
		    if (NumberField4.getCaretPosition() != NumberField4.getText().length()) { // only
											      // do
											      // something
											      // if
											      // the
											      // caret
											      // is
											      // not
											      // the
											      // at
											      // the
											      // end
			int tmpcaretpos = NumberField4.getCaretPosition();
			NumberField4.setText(NumberField4.getText().substring(0, NumberField4.getCaretPosition())
				+ NumberField4.getText().substring(NumberField4.getCaretPosition() + 1, NumberField4.getText().length()));
			NumberField4.setCaretPosition(tmpcaretpos);
		    }
		}
		Validate();
	    }
	});
	ButtonDel.setText("Del");
	ButtonDel.setPreferredSize(new Dimension(100, 60));
	ButtonDel.setFont(new Font("DejaVu Sans", Font.PLAIN, 18));
	KeyPad.add(ButtonDel, "cell 5 1");

	Button7 = new EButton(parent);
	Button7.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		AppendCharacter("7");
	    }
	});
	Button7.setText("7");
	Button7.setPreferredSize(new Dimension(100, 60));
	Button7.setFont(new Font("DejaVu Sans", Font.PLAIN, 18));
	KeyPad.add(Button7, "cell 0 2");

	Button8 = new EButton(parent);
	Button8.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		AppendCharacter("8");
	    }
	});
	Button8.setText("8");
	Button8.setPreferredSize(new Dimension(100, 60));
	Button8.setFont(new Font("DejaVu Sans", Font.PLAIN, 18));
	KeyPad.add(Button8, "cell 1 2");

	Button9 = new EButton(parent);
	Button9.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		AppendCharacter("9");
	    }
	});
	Button9.setText("9");
	Button9.setPreferredSize(new Dimension(100, 60));
	Button9.setFont(new Font("DejaVu Sans", Font.PLAIN, 18));
	KeyPad.add(Button9, "cell 2 2");

	ButtonMinus1 = new EButton(parent);
	ButtonMinus1.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		if (FocusOwnerIndex == 1) {
		    int temp = Integer.parseInt(NumberField1.getText()) - 1;
		    NumberField1.setText(String.valueOf(temp));
		}
		if (FocusOwnerIndex == 2) {
		    int temp = Integer.parseInt(NumberField2.getText()) - 1;
		    NumberField2.setText(String.valueOf(temp));
		}
		if (FocusOwnerIndex == 3) {
		    int temp = Integer.parseInt(NumberField3.getText()) - 1;
		    NumberField3.setText(String.valueOf(temp));
		}
		if (FocusOwnerIndex == 4) {
		    int temp = Integer.parseInt(NumberField4.getText()) - 1;
		    NumberField4.setText(String.valueOf(temp));
		}
		Validate();
	    }
	});
	ButtonMinus1.setText("-1");
	ButtonMinus1.setPreferredSize(new Dimension(100, 60));
	ButtonMinus1.setFont(new Font("DejaVu Sans", Font.PLAIN, 18));
	KeyPad.add(ButtonMinus1, "cell 3 2");

	ButtonClearAll = new EButton(parent);
	ButtonClearAll.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		NumberField1.setText("");
		NumberField2.setText("");
		NumberField3.setText("");
		NumberField4.setText("");
	    }
	});
	ButtonClearAll.setText("Clear All");
	ButtonClearAll.setPreferredSize(new Dimension(100, 60));
	ButtonClearAll.setFont(new Font("DejaVu Sans", Font.PLAIN, 18));
	KeyPad.add(ButtonClearAll, "cell 5 2");

	Button0 = new EButton(parent);
	Button0.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		AppendCharacter("0");
	    }
	});
	Button0.setText("0");
	Button0.setPreferredSize(new Dimension(100, 60));
	Button0.setFont(new Font("DejaVu Sans", Font.PLAIN, 18));
	KeyPad.add(Button0, "cell 0 3");

	ButtonClear = new EButton(parent);
	ButtonClear.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		if (FocusOwnerIndex == 1) {
		    NumberField1.setText("");
		}
		if (FocusOwnerIndex == 2) {
		    NumberField2.setText("");
		}
		if (FocusOwnerIndex == 3) {
		    NumberField3.setText("");
		}
		if (FocusOwnerIndex == 4) {
		    NumberField4.setText("");
		}
	    }
	});
	ButtonClear.setText("Clear");
	ButtonClear.setPreferredSize(new Dimension(100, 60));
	ButtonClear.setFont(new Font("DejaVu Sans", Font.PLAIN, 18));
	KeyPad.add(ButtonClear, "cell 2 3");

	ButtonMinus10 = new EButton(parent);
	ButtonMinus10.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		if (FocusOwnerIndex == 1) {
		    int temp = Integer.parseInt(NumberField1.getText()) - 10;
		    NumberField1.setText(String.valueOf(temp));
		}
		if (FocusOwnerIndex == 2) {
		    int temp = Integer.parseInt(NumberField2.getText()) - 10;
		    NumberField2.setText(String.valueOf(temp));
		}
		if (FocusOwnerIndex == 3) {
		    int temp = Integer.parseInt(NumberField3.getText()) - 10;
		    NumberField3.setText(String.valueOf(temp));
		}
		if (FocusOwnerIndex == 4) {
		    int temp = Integer.parseInt(NumberField4.getText()) - 10;
		    NumberField4.setText(String.valueOf(temp));
		}
		Validate();
	    }
	});
	ButtonMinus10.setText("-10");
	ButtonMinus10.setPreferredSize(new Dimension(100, 60));
	ButtonMinus10.setFont(new Font("DejaVu Sans", Font.PLAIN, 18));
	KeyPad.add(ButtonMinus10, "cell 3 3");

	ConfirmationPanel = new JPanel();
	ConfirmationPanel.setBackground(Color.BLACK);
	add(ConfirmationPanel, BorderLayout.SOUTH);
	ConfirmationPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));

	ButtonOK = new EButton(this.Parent);
	ButtonOK.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		FieldParent.setText(NumberField1.getText() + "." + NumberField2.getText() + "." + NumberField3.getText() + "."
			+ NumberField4.getText());

		CardLayout cl = (CardLayout) (Parent.GetCardManager().getLayout());
		cl.show(Parent.GetCardManager(), TargetCard);
	    }
	});
	ButtonOK.setText("OK");
	ConfirmationPanel.add(ButtonOK);

	ButtonCancel = new EButton(this.Parent);
	ButtonCancel.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		CardLayout cl = (CardLayout) (Parent.GetCardManager().getLayout());
		cl.show(Parent.GetCardManager(), TargetCard);
	    }
	});
	ButtonCancel.setText("Cancel");
	ConfirmationPanel.add(ButtonCancel);
    }

    public void Load(String Varname, String value, JTextField parent, String targetCard) {
	FieldParent = parent;
	VarNameLabel.setText(Varname);
	TargetCard = targetCard;

	String[] IPvalue = new String[4];

	IPvalue = value.split("\\.");
	NumberField1.setText(String.valueOf(IPvalue[0]));
	NumberField2.setText(String.valueOf(IPvalue[1]));
	NumberField3.setText(String.valueOf(IPvalue[2]));
	NumberField4.setText(String.valueOf(IPvalue[3]));

	NumberField1.requestFocus();
	NumberField1.setCaretPosition(0);
	NumberField1.getCaret().setVisible(true);
	FocusOwnerIndex = 1;
    }

    private void Validate() {
	if (!"".equals(NumberField1.getText())) {
	    if (Integer.parseInt(NumberField1.getText()) > 254) {
		NumberField1.setText("254");
	    }
	    if (Integer.parseInt(NumberField1.getText()) < 0) {
		NumberField1.setText("0");
	    }
	}
	if (!"".equals(NumberField2.getText())) {
	    if (Integer.parseInt(NumberField2.getText()) > 254) {
		NumberField2.setText("254");
	    }
	    if (Integer.parseInt(NumberField2.getText()) < 0) {
		NumberField2.setText("0");
	    }
	}
	if (!"".equals(NumberField3.getText())) {
	    if (Integer.parseInt(NumberField3.getText()) > 254) {
		NumberField3.setText("254");
	    }
	    if (Integer.parseInt(NumberField3.getText()) < 0) {
		NumberField3.setText("0");
	    }
	}
	if (!"".equals(NumberField4.getText())) {
	    if (Integer.parseInt(NumberField4.getText()) > 254) {
		NumberField4.setText("254");
	    }
	    if (Integer.parseInt(NumberField4.getText()) < 0) {
		NumberField4.setText("0");
	    }
	}
    }

    void AppendCharacter(String character) {
	if (FocusOwnerIndex == 1) {
	    int tmpcaretpos = NumberField1.getCaretPosition(); // save positon
							       // of the caret
							       // so we can use
							       // it again after
							       // replacing the
							       // string
	    NumberField1.setText(NumberField1.getText().substring(0, NumberField1.getCaretPosition()) + character
		    + NumberField1.getText().substring(NumberField1.getCaretPosition(), NumberField1.getText().length()));
	    NumberField1.setCaretPosition(tmpcaretpos + 1); // use the save
							    // caret position
							    // again
	    NumberField1.getCaret().setVisible(true);
	}
	if (FocusOwnerIndex == 2) {
	    int tmpcaretpos = NumberField2.getCaretPosition(); // save positon
							       // of the caret
							       // so we can use
							       // it again after
							       // replacing the
							       // string
	    NumberField2.setText(NumberField2.getText().substring(0, NumberField2.getCaretPosition()) + character
		    + NumberField2.getText().substring(NumberField2.getCaretPosition(), NumberField2.getText().length()));
	    NumberField2.setCaretPosition(tmpcaretpos + 1); // use the save
							    // caret position
							    // again
	    NumberField2.getCaret().setVisible(true);
	}
	if (FocusOwnerIndex == 3) {
	    int tmpcaretpos = NumberField3.getCaretPosition(); // save positon
							       // of the caret
							       // so we can use
							       // it again after
							       // replacing the
							       // string
	    NumberField3.setText(NumberField3.getText().substring(0, NumberField3.getCaretPosition()) + character
		    + NumberField3.getText().substring(NumberField3.getCaretPosition(), NumberField3.getText().length()));
	    NumberField3.setCaretPosition(tmpcaretpos + 1); // use the save
							    // caret position
							    // again
	    NumberField3.getCaret().setVisible(true);
	}
	if (FocusOwnerIndex == 4) {
	    int tmpcaretpos = NumberField4.getCaretPosition(); // save positon
							       // of the caret
							       // so we can use
							       // it again after
							       // replacing the
							       // string
	    NumberField4.setText(NumberField4.getText().substring(0, NumberField4.getCaretPosition()) + character
		    + NumberField4.getText().substring(NumberField4.getCaretPosition(), NumberField4.getText().length()));
	    NumberField4.setCaretPosition(tmpcaretpos + 1); // use the save
							    // caret position
							    // again
	    NumberField4.getCaret().setVisible(true);
	}
	Validate();
    }
}
