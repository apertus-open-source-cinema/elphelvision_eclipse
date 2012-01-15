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

public class FloatInputDialog extends javax.swing.JPanel {
    private JTextField FieldParent;
    private String TargetCard;
    private ElphelVision Parent;
    private JTextField NumberField;
    private JLabel VarNameLabel;

    public FloatInputDialog(ElphelVision parent) {
    	setBackground(Color.BLACK);
	setPreferredSize(new Dimension(1024, 600));
	setMinimumSize(new Dimension(1024, 600));
	this.Parent = parent;
	setLayout(new BorderLayout(0, 0));

	JPanel bg = new JPanel();
	bg.setBackground(Color.BLACK);
	add(bg, BorderLayout.WEST);
	GridBagLayout gbl_bg = new GridBagLayout();
	gbl_bg.columnWidths = new int[] { 341, 0 };
	gbl_bg.rowHeights = new int[] { 0, 170, 0 };
	gbl_bg.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
	gbl_bg.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
	bg.setLayout(gbl_bg);

	JPanel NumberPanel = new JPanel();
	GridBagConstraints gbc_NumberPanel = new GridBagConstraints();
	gbc_NumberPanel.anchor = GridBagConstraints.NORTHWEST;
	gbc_NumberPanel.insets = new Insets(15, 15, 15, 15);
	gbc_NumberPanel.gridx = 0;
	gbc_NumberPanel.gridy = 0;
	bg.add(NumberPanel, gbc_NumberPanel);
	NumberPanel.setBackground(Color.BLACK);
	NumberPanel.setLayout(new MigLayout("", "[110px,grow,center][110px,grow,center][110px,grow,center][110px,grow,center]", "[70px,grow,center][70px,grow,center][54.00px,grow,center]"));

	this.VarNameLabel = new JLabel("New label");
	VarNameLabel.setForeground(Color.WHITE);
	NumberPanel.add(this.VarNameLabel, "cell 0 0 4 1,growx,aligny center");

	NumberField = new JTextField();
	NumberField.setFont(new Font("DejaVu Sans", Font.PLAIN, 18));
	NumberField.setForeground(Color.WHITE);
	NumberField.setCaretColor(Color.WHITE);
	NumberField.setHorizontalAlignment(SwingConstants.LEFT);
	NumberField.setMinimumSize(new Dimension(200, 20));
	NumberField.setMargin(new Insets(5, 5, 5, 5));
	NumberField.setBackground(Color.BLACK);
	NumberPanel.add(NumberField, "cell 0 1 4 1,growx,aligny center");
	NumberField.setColumns(10);

	EButton ButtonPos1 = new EButton(Parent);
	ButtonPos1.setPreferredSize(new Dimension(100, 60));
	ButtonPos1.setFont(new java.awt.Font("DejaVu Sans", 0, 18));
	NumberPanel.add(ButtonPos1, "cell 0 2,alignx left,aligny top");
	ButtonPos1.setText("Pos1");
	ButtonPos1.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent arg0) {
		NumberField.setCaretPosition(0);
		NumberField.getCaret().setVisible(true);
	    }
	});

	EButton ButtonLeft = new EButton(Parent);
	ButtonLeft.setPreferredSize(new Dimension(100, 60));
	ButtonLeft.setFont(new java.awt.Font("DejaVu Sans", 0, 18));
	NumberPanel.add(ButtonLeft, "cell 1 2,alignx left,aligny top");
	ButtonLeft.setText("<-");
	ButtonLeft.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		NumberField.setCaretPosition(NumberField.getCaretPosition() - 1);
		NumberField.getCaret().setVisible(true);
	    }
	});

	EButton ButtonRight = new EButton(Parent);
	ButtonRight.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		NumberField.setCaretPosition(NumberField.getCaretPosition() + 1);
		NumberField.getCaret().setVisible(true);
	    }
	});
	ButtonRight.setPreferredSize(new Dimension(100, 60));
	ButtonRight.setFont(new java.awt.Font("DejaVu Sans", 0, 18));
	NumberPanel.add(ButtonRight, "cell 2 2,alignx left,aligny top");
	ButtonRight.setText("->");

	EButton ButtonEnd = new EButton(Parent);
	ButtonEnd.setPreferredSize(new Dimension(100, 60));
	ButtonEnd.setFont(new java.awt.Font("DejaVu Sans", 0, 18));
	ButtonEnd.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		NumberField.setCaretPosition(NumberField.getText().length());
		NumberField.getCaret().setVisible(true);
	    }
	});
	ButtonEnd.setText("End");
	NumberPanel.add(ButtonEnd, "cell 3 2");

	JPanel KeyPad = new JPanel();
	GridBagConstraints gbc_KeyPad = new GridBagConstraints();
	gbc_KeyPad.anchor = GridBagConstraints.NORTHWEST;
	gbc_KeyPad.ipadx = 10;
	gbc_KeyPad.insets = new Insets(10, 10, 10, 10);
	gbc_KeyPad.gridx = 0;
	gbc_KeyPad.gridy = 1;
	bg.add(KeyPad, gbc_KeyPad);
	KeyPad.setBackground(Color.BLACK);
	KeyPad.setLayout(new MigLayout("", "[110px,grow,center][110px,grow,center][110px,grow,center][110px,grow,center][110px,grow,center]", "[70px,grow,center][70px,grow,center][70px,grow,center][70px,grow,center]"));

	EButton KeyPad1 = new EButton(Parent);
	KeyPad1.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		AppendCharacter("1");
	    }
	});
	KeyPad1.setFont(new java.awt.Font("DejaVu Sans", 0, 18));
	KeyPad1.setPreferredSize(new Dimension(100, 60));
	KeyPad1.setText("1");
	KeyPad.add(KeyPad1, "cell 0 0");

	EButton KeyPad2 = new EButton(Parent);
	KeyPad2.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		AppendCharacter("2");
	    }
	});
	KeyPad2.setFont(new java.awt.Font("DejaVu Sans", 0, 18));
	KeyPad2.setPreferredSize(new Dimension(100, 60));
	KeyPad2.setText("2");
	KeyPad.add(KeyPad2, "cell 1 0");

	EButton KeyPad3 = new EButton(Parent);
	KeyPad3.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		AppendCharacter("3");
	    }
	});
	KeyPad3.setFont(new java.awt.Font("DejaVu Sans", 0, 18));
	KeyPad3.setPreferredSize(new Dimension(100, 60));
	KeyPad3.setText("3");
	KeyPad.add(KeyPad3, "cell 2 0");

	EButton KeyPad4 = new EButton(Parent);
	KeyPad4.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		AppendCharacter("4");
	    }
	});

	EButton KeyPadPlus10 = new EButton(Parent);
	KeyPadPlus10.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		Float temp = Float.parseFloat(NumberField.getText()) + 10.0f;
		if (temp < 0) {
		    temp = 0.0f;
		}
		NumberField.setText(String.valueOf(temp));
	    }
	});
	KeyPadPlus10.setFont(new java.awt.Font("DejaVu Sans", 0, 18));
	KeyPadPlus10.setPreferredSize(new Dimension(100, 60));
	KeyPadPlus10.setText("+10");
	KeyPad.add(KeyPadPlus10, "cell 3 0");

	EButton ButtonBackspace = new EButton(Parent);
	ButtonBackspace.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		if (NumberField.getCaretPosition() != 0) {
		    // only do something if the caret is not the at the
		    // beginning
		    int tmpcaretpos = NumberField.getCaretPosition();
		    NumberField.setText(NumberField.getText().substring(0, NumberField.getCaretPosition() - 1)
			    + NumberField.getText().substring(NumberField.getCaretPosition(), NumberField.getText().length()));
		    NumberField.setCaretPosition(tmpcaretpos - 1);
		}
	    }
	});
	ButtonBackspace.setText("Backspace");
	ButtonBackspace.setPreferredSize(new Dimension(100, 60));
	ButtonBackspace.setFont(new Font("DejaVu Sans", Font.PLAIN, 18));
	KeyPad.add(ButtonBackspace, "cell 4 0");

	KeyPad4.setFont(new java.awt.Font("DejaVu Sans", 0, 18));
	KeyPad4.setPreferredSize(new Dimension(100, 60));
	KeyPad4.setText("4");
	KeyPad.add(KeyPad4, "cell 0 1");

	EButton KeyPad5 = new EButton(Parent);
	KeyPad5.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		AppendCharacter("5");
	    }
	});
	KeyPad5.setFont(new java.awt.Font("DejaVu Sans", 0, 18));
	KeyPad5.setPreferredSize(new Dimension(100, 60));
	KeyPad5.setText("5");
	KeyPad.add(KeyPad5, "cell 1 1");

	EButton KeyPad6 = new EButton(Parent);
	KeyPad6.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		AppendCharacter("6");
	    }
	});
	KeyPad6.setFont(new java.awt.Font("DejaVu Sans", 0, 18));
	KeyPad6.setPreferredSize(new Dimension(100, 60));
	KeyPad6.setText("6");
	KeyPad.add(KeyPad6, "cell 2 1");

	EButton KeyPad7 = new EButton(Parent);
	KeyPad7.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		AppendCharacter("7");
	    }
	});

	EButton KeyPadPlus1 = new EButton(Parent);
	KeyPadPlus1.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		Float temp = Float.parseFloat(NumberField.getText()) + 1.0f;
		if (temp < 0) {
		    temp = 0.0f;
		}
		NumberField.setText(String.valueOf(temp));
	    }
	});
	KeyPadPlus1.setFont(new java.awt.Font("DejaVu Sans", 0, 18));
	KeyPadPlus1.setPreferredSize(new Dimension(100, 60));
	KeyPadPlus1.setText("+1");
	KeyPad.add(KeyPadPlus1, "cell 3 1");

	EButton ButtonDel = new EButton(Parent);
	ButtonDel.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		if (NumberField.getCaretPosition() != NumberField.getText().length()) {
		    // only do something if the caret is not the at the end
		    int tmpcaretpos = NumberField.getCaretPosition();
		    NumberField.setText(NumberField.getText().substring(0, NumberField.getCaretPosition())
			    + NumberField.getText().substring(NumberField.getCaretPosition() + 1, NumberField.getText().length()));
		    NumberField.setCaretPosition(tmpcaretpos);
		}
	    }
	});
	ButtonDel.setText("Del");
	ButtonDel.setPreferredSize(new Dimension(100, 60));
	ButtonDel.setFont(new Font("DejaVu Sans", Font.PLAIN, 18));
	KeyPad.add(ButtonDel, "cell 4 1");

	KeyPad7.setFont(new java.awt.Font("DejaVu Sans", 0, 18));
	KeyPad7.setPreferredSize(new Dimension(100, 60));
	KeyPad7.setText("7");
	KeyPad.add(KeyPad7, "cell 0 2");

	EButton KeyPad8 = new EButton(Parent);
	KeyPad8.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		AppendCharacter("8");
	    }
	});
	KeyPad8.setFont(new java.awt.Font("DejaVu Sans", 0, 18));
	KeyPad8.setPreferredSize(new Dimension(100, 60));
	KeyPad8.setText("8");
	KeyPad.add(KeyPad8, "cell 1 2");

	EButton KeyPad9 = new EButton(Parent);
	KeyPad9.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		AppendCharacter("9");
	    }
	});
	KeyPad9.setFont(new java.awt.Font("DejaVu Sans", 0, 18));
	KeyPad9.setPreferredSize(new Dimension(100, 60));
	KeyPad9.setText("9");
	KeyPad.add(KeyPad9, "cell 2 2");

	EButton KeyPadMinus1 = new EButton(Parent);
	KeyPadMinus1.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		Float temp = Float.parseFloat(NumberField.getText()) - 1;
		if (temp < 0) {
		    temp = 0.0f;
		}
		NumberField.setText(String.valueOf(temp));
	    }
	});
	KeyPadMinus1.setFont(new java.awt.Font("DejaVu Sans", 0, 18));
	KeyPadMinus1.setPreferredSize(new Dimension(100, 60));
	KeyPadMinus1.setText("-1");
	KeyPad.add(KeyPadMinus1, "cell 3 2");

	EButton KeyPad0 = new EButton(Parent);
	KeyPad0.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		AppendCharacter("0");
	    }
	});
	KeyPad0.setFont(new java.awt.Font("DejaVu Sans", 0, 18));
	KeyPad0.setPreferredSize(new Dimension(100, 60));
	KeyPad0.setText("0");
	KeyPad.add(KeyPad0, "cell 0 3");

	EButton KeyPadComma = new EButton(Parent);
	KeyPadComma.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		AppendCharacter(".");
	    }
	});
	KeyPadComma.setFont(new java.awt.Font("DejaVu Sans", 0, 18));
	KeyPadComma.setPreferredSize(new Dimension(100, 60));
	KeyPadComma.setText(".");
	KeyPad.add(KeyPadComma, "cell 1 3");

	EButton ButtonClear = new EButton(Parent);
	ButtonClear.setFont(new java.awt.Font("DejaVu Sans", 0, 18));
	ButtonClear.setPreferredSize(new Dimension(100, 60));
	ButtonClear.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		NumberField.setText("");
	    }
	});
	ButtonClear.setText("Clear");
	KeyPad.add(ButtonClear, "cell 2 3");

	EButton KeyPadMinus10 = new EButton(Parent);
	KeyPadMinus10.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		Float temp = Float.parseFloat(NumberField.getText()) - 10.0f;
		if (temp < 0) {
		    temp = 0.0f;
		}
		NumberField.setText(String.valueOf(temp));
	    }
	});
	KeyPadMinus10.setFont(new java.awt.Font("DejaVu Sans", 0, 18));
	KeyPadMinus10.setPreferredSize(new Dimension(100, 60));
	KeyPadMinus10.setText("-10");
	KeyPad.add(KeyPadMinus10, "cell 3 3");

	JPanel ConfirmationPanel = new JPanel();
	FlowLayout fl_ConfirmationPanel = (FlowLayout) ConfirmationPanel.getLayout();
	fl_ConfirmationPanel.setAlignment(FlowLayout.RIGHT);
	add(ConfirmationPanel, BorderLayout.SOUTH);
	ConfirmationPanel.setBackground(Color.BLACK);

	EButton btnOk = new EButton(Parent);
	btnOk.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		FieldParent.setText(NumberField.getText());

		CardLayout cl = (CardLayout) (Parent.GetCardManager().getLayout());
		cl.show(Parent.GetCardManager(), TargetCard);
	    }
	});
	btnOk.setText("OK");
	ConfirmationPanel.add(btnOk);

	EButton btnCancel = new EButton(Parent);
	btnCancel.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		CardLayout cl = (CardLayout) (Parent.GetCardManager().getLayout());
		cl.show(Parent.GetCardManager(), TargetCard);
	    }
	});
	btnCancel.setText("Cancel");
	ConfirmationPanel.add(btnCancel);
    }

    public void Load(String Varname, float value, JTextField parent, String targetCard) {
	this.FieldParent = parent;
	this.VarNameLabel.setText(Varname);
	this.TargetCard = targetCard;
	this.NumberField.setText(String.valueOf(value));
	NumberField.getCaret().setVisible(true);
    }

    void AppendCharacter(String character) {
	int tmpcaretpos = NumberField.getCaretPosition(); // save position of
							  // the
							  // caret so we can use
							  // it again after
							  // replacing the
							  // string
	NumberField.setText(NumberField.getText().substring(0, NumberField.getCaretPosition()) + character
		+ NumberField.getText().substring(NumberField.getCaretPosition(), NumberField.getText().length()));
	NumberField.setCaretPosition(tmpcaretpos + 1); // set the save caret
						       // position again
	NumberField.getCaret().setVisible(true);
    }
}
