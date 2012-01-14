/*! Copyright (C) 2009-2012 Apertus, All Rights Reserved
 *! Author : Apertus Team
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

public class NumericalInputPanelFloat extends javax.swing.JPanel {

    JTextField FieldParent;
    String TargetCard;
    ElphelVision Parent;
    private Object VarName;

    public NumericalInputPanelFloat(ElphelVision parent) {
	this.Parent = parent;

	try {
	    java.awt.EventQueue.invokeAndWait(new Runnable() {

		public void run() {
		    initComponents();
		}
	    });
	} catch (Exception ex) {
	    ex.printStackTrace();
	}
    }

    public void Load(String Varname, float value, JTextField parent, String targetCard) {
	this.FieldParent = parent;
	//this.VarName.setText(Varname);
	this.TargetCard = targetCard;
	this.NumberField.setText(String.valueOf(value));

	NumberField.getCaret().setVisible(true);
    }

    private void initComponents() {

	bg = new javax.swing.JPanel();
	Keypad = new javax.swing.JPanel();
	NumberPanel = new javax.swing.JPanel();
	Validation = new javax.swing.JLabel();
	ConfirmationPanel = new javax.swing.JPanel();
	SettingsCancelButton = new EButton(Parent);
	SettingsOKButton = new EButton(Parent);

	bg.setBackground(new java.awt.Color(0, 0, 0));
	bg.setPreferredSize(new java.awt.Dimension(1024, 600));

	Keypad.setBackground(new java.awt.Color(0, 0, 0));

	NumberPanel.setBackground(java.awt.Color.black);

	Validation.setForeground(new java.awt.Color(180, 1, 1));

	ConfirmationPanel.setBackground(java.awt.Color.black);

	SettingsCancelButton.setText("Cancel");
	SettingsCancelButton.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		SettingsCancelButtonActionPerformed(evt);
	    }
	});

	SettingsOKButton.setText("OK");
	SettingsOKButton.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		SettingsOKButtonActionPerformed(evt);
	    }
	});
	setLayout(new MigLayout("", "[1024px]", "[600px]"));

	javax.swing.GroupLayout gl_bg = new javax.swing.GroupLayout(bg);
	gl_bg.setHorizontalGroup(
		gl_bg.createParallelGroup(Alignment.LEADING)
			.addGroup(gl_bg.createSequentialGroup()
				.addContainerGap()
				.addComponent(NumberPanel, GroupLayout.PREFERRED_SIZE, 571, GroupLayout.PREFERRED_SIZE)
				.addGap(6)
				.addComponent(Validation, GroupLayout.PREFERRED_SIZE, 220, GroupLayout.PREFERRED_SIZE)
				.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
			.addGroup(gl_bg.createSequentialGroup()
				.addContainerGap(649, Short.MAX_VALUE)
				.addComponent(ConfirmationPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
				.addContainerGap())
			.addGroup(gl_bg.createSequentialGroup()
				.addContainerGap()
				.addComponent(Keypad, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
				.addContainerGap(309, Short.MAX_VALUE))
	);
	gl_bg.setVerticalGroup(
		gl_bg.createParallelGroup(Alignment.LEADING)
			.addGroup(gl_bg.createSequentialGroup()
				.addContainerGap()
				.addGroup(gl_bg.createParallelGroup(Alignment.TRAILING)
					.addGroup(gl_bg.createSequentialGroup()
						.addComponent(Validation, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
						.addGap(141))
					.addGroup(gl_bg.createSequentialGroup()
						.addComponent(NumberPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)))
				.addComponent(Keypad, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addComponent(ConfirmationPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
				.addContainerGap())
	);
	ConfirmationPanel.setLayout(new GridLayout(0, 2, 0, 0));
	ConfirmationPanel.add(SettingsOKButton);
	ConfirmationPanel.add(SettingsCancelButton);
		GridBagLayout gbl_NumberPanel = new GridBagLayout();
		gbl_NumberPanel.columnWidths = new int[]{100, 100, 100, 25, 100, 0};
		gbl_NumberPanel.rowHeights = new int[]{89, 60, 0};
		gbl_NumberPanel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_NumberPanel.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		NumberPanel.setLayout(gbl_NumberPanel);
		RightButton = new EButton(Parent);
		
			RightButton.setText("->");
			RightButton.setFont(new java.awt.Font("DejaVu Sans", 0, 18));
			RightButton.setPreferredSize(new java.awt.Dimension(100, 60));
			RightButton.addActionListener(new java.awt.event.ActionListener() {
			    public void actionPerformed(java.awt.event.ActionEvent evt) {
				RightButtonActionPerformed(evt);
			    }
			});
			Pos1Button = new EButton(Parent);
			
				Pos1Button.setText("Pos1");
				Pos1Button.setFont(new java.awt.Font("DejaVu Sans", 0, 18));
				Pos1Button.setPreferredSize(new java.awt.Dimension(100, 60));
				Pos1Button.addActionListener(new java.awt.event.ActionListener() {
				    public void actionPerformed(java.awt.event.ActionEvent evt) {
					Pos1ButtonActionPerformed(evt);
				    }
				});
				NumberField = new javax.swing.JTextField();
				
					NumberField.setBackground(new java.awt.Color(0, 0, 0));
					NumberField.setFont(new java.awt.Font("Bitstream Vera Sans Mono", 0, 18));
					NumberField.setForeground(new java.awt.Color(255, 255, 255));
					NumberField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
					NumberField.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(148, 148, 148)));
					NumberField.setCaretColor(new java.awt.Color(255, 255, 255));
					NumberField.addCaretListener(new javax.swing.event.CaretListener() {
					    public void caretUpdate(javax.swing.event.CaretEvent evt) {
						NumberFieldCaretUpdate(evt);
					    }
					});
					GridBagConstraints gbc_NumberField = new GridBagConstraints();
					gbc_NumberField.fill = GridBagConstraints.BOTH;
					gbc_NumberField.insets = new Insets(0, 0, 5, 5);
					gbc_NumberField.gridwidth = 4;
					gbc_NumberField.gridx = 0;
					gbc_NumberField.gridy = 0;
					NumberPanel.add(NumberField, gbc_NumberField);
				GridBagConstraints gbc_Pos1Button = new GridBagConstraints();
				gbc_Pos1Button.anchor = GridBagConstraints.NORTHWEST;
				gbc_Pos1Button.insets = new Insets(0, 0, 0, 5);
				gbc_Pos1Button.gridx = 0;
				gbc_Pos1Button.gridy = 1;
				NumberPanel.add(Pos1Button, gbc_Pos1Button);
			LeftButton = new EButton(Parent);
			
				LeftButton.setText("<-");
				LeftButton.setFont(new java.awt.Font("DejaVu Sans", 0, 18));
				LeftButton.setPreferredSize(new java.awt.Dimension(100, 60));
				LeftButton.addActionListener(new java.awt.event.ActionListener() {
				    public void actionPerformed(java.awt.event.ActionEvent evt) {
					LeftButtonActionPerformed(evt);
				    }
				});
				GridBagConstraints gbc_LeftButton = new GridBagConstraints();
				gbc_LeftButton.anchor = GridBagConstraints.NORTHWEST;
				gbc_LeftButton.insets = new Insets(0, 0, 0, 5);
				gbc_LeftButton.gridx = 1;
				gbc_LeftButton.gridy = 1;
				NumberPanel.add(LeftButton, gbc_LeftButton);
			GridBagConstraints gbc_RightButton = new GridBagConstraints();
			gbc_RightButton.anchor = GridBagConstraints.NORTHWEST;
			gbc_RightButton.insets = new Insets(0, 0, 0, 5);
			gbc_RightButton.gridx = 2;
			gbc_RightButton.gridy = 1;
			NumberPanel.add(RightButton, gbc_RightButton);
			EndButton = new EButton(Parent);
			
				EndButton.setText("End");
				EndButton.setFont(new java.awt.Font("DejaVu Sans", 0, 18));
				EndButton.setPreferredSize(new java.awt.Dimension(100, 60));
				EndButton.addActionListener(new java.awt.event.ActionListener() {
				    public void actionPerformed(java.awt.event.ActionEvent evt) {
					EndButtonActionPerformed(evt);
				    }
				});
				GridBagConstraints gbc_EndButton = new GridBagConstraints();
				gbc_EndButton.anchor = GridBagConstraints.NORTHWEST;
				gbc_EndButton.insets = new Insets(0, 0, 0, 5);
				gbc_EndButton.gridx = 3;
				gbc_EndButton.gridy = 1;
				NumberPanel.add(EndButton, gbc_EndButton);
	GridBagLayout gbl_Keypad = new GridBagLayout();
	gbl_Keypad.columnWidths = new int[]{100, 100, 100, 100, 100, 0};
	gbl_Keypad.rowHeights = new int[]{60, 60, 60, 60, 0};
	gbl_Keypad.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
	gbl_Keypad.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
	Keypad.setLayout(gbl_Keypad);
	Number2 = new EButton(Parent);
	
		Number2.setText("2");
		Number2.setFont(new java.awt.Font("DejaVu Sans", 0, 18));
		Number2.setPreferredSize(new java.awt.Dimension(100, 60));
		Number2.addActionListener(new java.awt.event.ActionListener() {
		    public void actionPerformed(java.awt.event.ActionEvent evt) {
			Number2ActionPerformed(evt);
		    }
		});
		Number1 = new EButton(Parent);
		
			Number1.setText("1");
			Number1.setFont(new java.awt.Font("DejaVu Sans", 0, 18));
			Number1.setPreferredSize(new java.awt.Dimension(100, 60));
			Number1.addActionListener(new java.awt.event.ActionListener() {
			    public void actionPerformed(java.awt.event.ActionEvent evt) {
				Number1ActionPerformed(evt);
			    }
			});
			GridBagConstraints gbc_Number1 = new GridBagConstraints();
			gbc_Number1.fill = GridBagConstraints.BOTH;
			gbc_Number1.insets = new Insets(0, 0, 5, 5);
			gbc_Number1.gridx = 0;
			gbc_Number1.gridy = 0;
			Keypad.add(Number1, gbc_Number1);
		GridBagConstraints gbc_Number2 = new GridBagConstraints();
		gbc_Number2.fill = GridBagConstraints.BOTH;
		gbc_Number2.insets = new Insets(0, 0, 5, 5);
		gbc_Number2.gridx = 1;
		gbc_Number2.gridy = 0;
		Keypad.add(Number2, gbc_Number2);
	Number5 = new EButton(Parent);
	
		Number5.setText("5");
		Number5.setFont(new java.awt.Font("DejaVu Sans", 0, 18));
		Number5.setPreferredSize(new java.awt.Dimension(100, 60));
		Number5.addActionListener(new java.awt.event.ActionListener() {
		    public void actionPerformed(java.awt.event.ActionEvent evt) {
			Number5ActionPerformed(evt);
		    }
		});
		Number4 = new EButton(Parent);
		
			Number4.setText("4");
			Number4.setFont(new java.awt.Font("DejaVu Sans", 0, 18));
			Number4.setPreferredSize(new java.awt.Dimension(100, 60));
			Number4.addActionListener(new java.awt.event.ActionListener() {
			    public void actionPerformed(java.awt.event.ActionEvent evt) {
				Number4ActionPerformed(evt);
			    }
			});
			BackspaceButton = new EButton(Parent);
			
				BackspaceButton.setText("Backspace");
				BackspaceButton.setFont(new java.awt.Font("DejaVu Sans", 0, 18));
				BackspaceButton.setPreferredSize(new java.awt.Dimension(100, 60));
				BackspaceButton.addActionListener(new java.awt.event.ActionListener() {
				    public void actionPerformed(java.awt.event.ActionEvent evt) {
					BackspaceButtonActionPerformed(evt);
				    }
				});
				Number3 = new EButton(Parent);
				
					Number3.setText("3");
					Number3.setFont(new java.awt.Font("DejaVu Sans", 0, 18));
					Number3.setPreferredSize(new java.awt.Dimension(100, 60));
					Number3.addActionListener(new java.awt.event.ActionListener() {
					    public void actionPerformed(java.awt.event.ActionEvent evt) {
						Number3ActionPerformed(evt);
					    }
					});
					GridBagConstraints gbc_Number3 = new GridBagConstraints();
					gbc_Number3.fill = GridBagConstraints.BOTH;
					gbc_Number3.insets = new Insets(0, 0, 5, 5);
					gbc_Number3.gridx = 2;
					gbc_Number3.gridy = 0;
					Keypad.add(Number3, gbc_Number3);
				NumberPlusTen = new EButton(Parent);
				
					NumberPlusTen.setText("+10");
					NumberPlusTen.setFont(new java.awt.Font("DejaVu Sans", 0, 18));
					NumberPlusTen.setPreferredSize(new java.awt.Dimension(100, 60));
					NumberPlusTen.addActionListener(new java.awt.event.ActionListener() {
					    public void actionPerformed(java.awt.event.ActionEvent evt) {
						NumberPlusTenActionPerformed(evt);
					    }
					});
					GridBagConstraints gbc_NumberPlusTen = new GridBagConstraints();
					gbc_NumberPlusTen.fill = GridBagConstraints.BOTH;
					gbc_NumberPlusTen.insets = new Insets(0, 0, 5, 5);
					gbc_NumberPlusTen.gridx = 3;
					gbc_NumberPlusTen.gridy = 0;
					Keypad.add(NumberPlusTen, gbc_NumberPlusTen);
				GridBagConstraints gbc_BackspaceButton = new GridBagConstraints();
				gbc_BackspaceButton.fill = GridBagConstraints.BOTH;
				gbc_BackspaceButton.insets = new Insets(0, 0, 5, 0);
				gbc_BackspaceButton.gridx = 4;
				gbc_BackspaceButton.gridy = 0;
				Keypad.add(BackspaceButton, gbc_BackspaceButton);
			GridBagConstraints gbc_Number4 = new GridBagConstraints();
			gbc_Number4.fill = GridBagConstraints.BOTH;
			gbc_Number4.insets = new Insets(0, 0, 5, 5);
			gbc_Number4.gridx = 0;
			gbc_Number4.gridy = 1;
			Keypad.add(Number4, gbc_Number4);
		GridBagConstraints gbc_Number5 = new GridBagConstraints();
		gbc_Number5.fill = GridBagConstraints.BOTH;
		gbc_Number5.insets = new Insets(0, 0, 5, 5);
		gbc_Number5.gridx = 1;
		gbc_Number5.gridy = 1;
		Keypad.add(Number5, gbc_Number5);
	Number7 = new EButton(Parent);
	
		Number7.setText("7");
		Number7.setFont(new java.awt.Font("DejaVu Sans", 0, 18));
		Number7.setPreferredSize(new java.awt.Dimension(100, 60));
		Number7.addActionListener(new java.awt.event.ActionListener() {
		    public void actionPerformed(java.awt.event.ActionEvent evt) {
			Number7ActionPerformed(evt);
		    }
		});
		DelButton = new EButton(Parent);
		
			DelButton.setText("Del");
			DelButton.setFont(new java.awt.Font("DejaVu Sans", 0, 18));
			DelButton.setPreferredSize(new java.awt.Dimension(100, 60));
			DelButton.addActionListener(new java.awt.event.ActionListener() {
			    public void actionPerformed(java.awt.event.ActionEvent evt) {
				DelButtonActionPerformed(evt);
			    }
			});
			NumberPlusOne = new EButton(Parent);
			
				NumberPlusOne.setText("+1");
				NumberPlusOne.setFont(new java.awt.Font("DejaVu Sans", 0, 18));
				NumberPlusOne.setPreferredSize(new java.awt.Dimension(100, 60));
				NumberPlusOne.addActionListener(new java.awt.event.ActionListener() {
				    public void actionPerformed(java.awt.event.ActionEvent evt) {
					NumberPlusOneActionPerformed(evt);
				    }
				});
				Number6 = new EButton(Parent);
				
					Number6.setText("6");
					Number6.setFont(new java.awt.Font("DejaVu Sans", 0, 18));
					Number6.setPreferredSize(new java.awt.Dimension(100, 60));
					Number6.addActionListener(new java.awt.event.ActionListener() {
					    public void actionPerformed(java.awt.event.ActionEvent evt) {
						Number6ActionPerformed(evt);
					    }
					});
					GridBagConstraints gbc_Number6 = new GridBagConstraints();
					gbc_Number6.fill = GridBagConstraints.BOTH;
					gbc_Number6.insets = new Insets(0, 0, 5, 5);
					gbc_Number6.gridx = 2;
					gbc_Number6.gridy = 1;
					Keypad.add(Number6, gbc_Number6);
				GridBagConstraints gbc_NumberPlusOne = new GridBagConstraints();
				gbc_NumberPlusOne.fill = GridBagConstraints.BOTH;
				gbc_NumberPlusOne.insets = new Insets(0, 0, 5, 5);
				gbc_NumberPlusOne.gridx = 3;
				gbc_NumberPlusOne.gridy = 1;
				Keypad.add(NumberPlusOne, gbc_NumberPlusOne);
			GridBagConstraints gbc_DelButton = new GridBagConstraints();
			gbc_DelButton.fill = GridBagConstraints.BOTH;
			gbc_DelButton.insets = new Insets(0, 0, 5, 0);
			gbc_DelButton.gridx = 4;
			gbc_DelButton.gridy = 1;
			Keypad.add(DelButton, gbc_DelButton);
		GridBagConstraints gbc_Number7 = new GridBagConstraints();
		gbc_Number7.fill = GridBagConstraints.BOTH;
		gbc_Number7.insets = new Insets(0, 0, 5, 5);
		gbc_Number7.gridx = 0;
		gbc_Number7.gridy = 2;
		Keypad.add(Number7, gbc_Number7);
	NumberMinus10 = new EButton(Parent);
	
		NumberMinus10.setText("-10");
		NumberMinus10.setFont(new java.awt.Font("DejaVu Sans", 0, 18));
		NumberMinus10.setPreferredSize(new java.awt.Dimension(100, 60));
		NumberMinus10.addActionListener(new java.awt.event.ActionListener() {
		    public void actionPerformed(java.awt.event.ActionEvent evt) {
			NumberMinus10ActionPerformed(evt);
		    }
		});
		Number9 = new EButton(Parent);
		
			Number9.setText("9");
			Number9.setFont(new java.awt.Font("DejaVu Sans", 0, 18));
			Number9.setPreferredSize(new java.awt.Dimension(100, 60));
			Number9.addActionListener(new java.awt.event.ActionListener() {
			    public void actionPerformed(java.awt.event.ActionEvent evt) {
				Number9ActionPerformed(evt);
			    }
			});
			Number8 = new EButton(Parent);
			
				Number8.setText("8");
				Number8.setFont(new java.awt.Font("DejaVu Sans", 0, 18));
				Number8.setPreferredSize(new java.awt.Dimension(100, 60));
				Number8.addActionListener(new java.awt.event.ActionListener() {
				    public void actionPerformed(java.awt.event.ActionEvent evt) {
					Number8ActionPerformed(evt);
				    }
				});
				GridBagConstraints gbc_Number8 = new GridBagConstraints();
				gbc_Number8.fill = GridBagConstraints.BOTH;
				gbc_Number8.insets = new Insets(0, 0, 5, 5);
				gbc_Number8.gridx = 1;
				gbc_Number8.gridy = 2;
				Keypad.add(Number8, gbc_Number8);
			GridBagConstraints gbc_Number9 = new GridBagConstraints();
			gbc_Number9.fill = GridBagConstraints.BOTH;
			gbc_Number9.insets = new Insets(0, 0, 5, 5);
			gbc_Number9.gridx = 2;
			gbc_Number9.gridy = 2;
			Keypad.add(Number9, gbc_Number9);
		NumberClear = new EButton(Parent);
		
			NumberClear.setText("Clear");
			NumberClear.setFont(new java.awt.Font("DejaVu Sans", 0, 18));
			NumberClear.setPreferredSize(new java.awt.Dimension(100, 60));
			NumberClear.addActionListener(new java.awt.event.ActionListener() {
			    public void actionPerformed(java.awt.event.ActionEvent evt) {
				NumberClearActionPerformed(evt);
			    }
			});
			NumberMinusOne = new EButton(Parent);
			
				NumberMinusOne.setText("-1");
				NumberMinusOne.setFont(new java.awt.Font("DejaVu Sans", 0, 18));
				NumberMinusOne.setPreferredSize(new java.awt.Dimension(100, 60));
				NumberMinusOne.addActionListener(new java.awt.event.ActionListener() {
				    public void actionPerformed(java.awt.event.ActionEvent evt) {
					NumberMinusOneActionPerformed(evt);
				    }
				});
				GridBagConstraints gbc_NumberMinusOne = new GridBagConstraints();
				gbc_NumberMinusOne.fill = GridBagConstraints.BOTH;
				gbc_NumberMinusOne.insets = new Insets(0, 0, 5, 5);
				gbc_NumberMinusOne.gridx = 3;
				gbc_NumberMinusOne.gridy = 2;
				Keypad.add(NumberMinusOne, gbc_NumberMinusOne);
			
			label = new JLabel("");
			GridBagConstraints gbc_label = new GridBagConstraints();
			gbc_label.fill = GridBagConstraints.BOTH;
			gbc_label.insets = new Insets(0, 0, 5, 0);
			gbc_label.gridx = 4;
			gbc_label.gridy = 2;
			Keypad.add(label, gbc_label);
			Number0 = new EButton(Parent);
			
				Number0.setText("0");
				Number0.setFont(new java.awt.Font("DejaVu Sans", 0, 18));
				Number0.setPreferredSize(new java.awt.Dimension(100, 60));
				Number0.addActionListener(new java.awt.event.ActionListener() {
				    public void actionPerformed(java.awt.event.ActionEvent evt) {
					Number0ActionPerformed(evt);
				    }
				});
				GridBagConstraints gbc_Number0 = new GridBagConstraints();
				gbc_Number0.fill = GridBagConstraints.BOTH;
				gbc_Number0.insets = new Insets(0, 0, 0, 5);
				gbc_Number0.gridx = 0;
				gbc_Number0.gridy = 3;
				Keypad.add(Number0, gbc_Number0);
			NumberComma = new EButton(Parent);
			
				NumberComma.setText(".");
				NumberComma.setFont(new java.awt.Font("DejaVu Sans", 0, 18));
				NumberComma.setPreferredSize(new java.awt.Dimension(100, 60));
				NumberComma.addActionListener(new java.awt.event.ActionListener() {
				    public void actionPerformed(java.awt.event.ActionEvent evt) {
					NumberCommaActionPerformed(evt);
				    }
				});
				GridBagConstraints gbc_NumberComma = new GridBagConstraints();
				gbc_NumberComma.fill = GridBagConstraints.BOTH;
				gbc_NumberComma.insets = new Insets(0, 0, 0, 5);
				gbc_NumberComma.gridx = 1;
				gbc_NumberComma.gridy = 3;
				Keypad.add(NumberComma, gbc_NumberComma);
			GridBagConstraints gbc_NumberClear = new GridBagConstraints();
			gbc_NumberClear.fill = GridBagConstraints.BOTH;
			gbc_NumberClear.insets = new Insets(0, 0, 0, 5);
			gbc_NumberClear.gridx = 2;
			gbc_NumberClear.gridy = 3;
			Keypad.add(NumberClear, gbc_NumberClear);
		GridBagConstraints gbc_NumberMinus10 = new GridBagConstraints();
		gbc_NumberMinus10.fill = GridBagConstraints.BOTH;
		gbc_NumberMinus10.insets = new Insets(0, 0, 0, 5);
		gbc_NumberMinus10.gridx = 3;
		gbc_NumberMinus10.gridy = 3;
		Keypad.add(NumberMinus10, gbc_NumberMinus10);
	
	label_1 = new JLabel("");
	GridBagConstraints gbc_label_1 = new GridBagConstraints();
	gbc_label_1.fill = GridBagConstraints.BOTH;
	gbc_label_1.gridx = 4;
	gbc_label_1.gridy = 3;
	Keypad.add(label_1, gbc_label_1);
	bg.setLayout(gl_bg);
	add(bg, "cell 0 0,alignx left,aligny top");
    }// </editor-fold>//GEN-END:initComponents

    private void SettingsOKButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_SettingsOKButtonActionPerformed
	this.FieldParent.setText(NumberField.getText());

	CardLayout cl = (CardLayout) (Parent.GetCardManager().getLayout());
	cl.show(Parent.GetCardManager(), TargetCard);
    }// GEN-LAST:event_SettingsOKButtonActionPerformed

    private void SettingsCancelButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_SettingsCancelButtonActionPerformed
	CardLayout cl = (CardLayout) (Parent.GetCardManager().getLayout());
	cl.show(Parent.GetCardManager(), TargetCard);
    }// GEN-LAST:event_SettingsCancelButtonActionPerformed

    private void NumberFieldCaretUpdate(javax.swing.event.CaretEvent evt) {// GEN-FIRST:event_NumberFieldCaretUpdate
    }// GEN-LAST:event_NumberFieldCaretUpdate

    void AppendCharacter(String character) {
	int tmpcaretpos = NumberField.getCaretPosition(); // save positon of the
							  // caret so we can use
							  // it again after
							  // replacing the
							  // string
	NumberField.setText(NumberField.getText().substring(0, NumberField.getCaretPosition()) + character
		+ NumberField.getText().substring(NumberField.getCaretPosition(), NumberField.getText().length()));
	NumberField.setCaretPosition(tmpcaretpos + 1); // use the save caret
						       // position again
	NumberField.getCaret().setVisible(true);
    }

    private void Number1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_Number1ActionPerformed
	AppendCharacter("1");
    }// GEN-LAST:event_Number1ActionPerformed

    private void Number2ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_Number2ActionPerformed
	AppendCharacter("2");
    }// GEN-LAST:event_Number2ActionPerformed

    private void Number3ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_Number3ActionPerformed
	AppendCharacter("3");
    }// GEN-LAST:event_Number3ActionPerformed

    private void Number4ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_Number4ActionPerformed
	AppendCharacter("4");
    }// GEN-LAST:event_Number4ActionPerformed

    private void Number5ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_Number5ActionPerformed
	AppendCharacter("5");
    }// GEN-LAST:event_Number5ActionPerformed

    private void Number6ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_Number6ActionPerformed
	AppendCharacter("6");
    }// GEN-LAST:event_Number6ActionPerformed

    private void Number7ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_Number7ActionPerformed
	AppendCharacter("7");
    }// GEN-LAST:event_Number7ActionPerformed

    private void Number8ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_Number8ActionPerformed
	AppendCharacter("8");
    }// GEN-LAST:event_Number8ActionPerformed

    private void Number9ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_Number9ActionPerformed
	AppendCharacter("9");
    }// GEN-LAST:event_Number9ActionPerformed

    private void NumberClearActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_NumberClearActionPerformed
	NumberField.setText("");
    }// GEN-LAST:event_NumberClearActionPerformed

    private void Number0ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_Number0ActionPerformed
	AppendCharacter("0");
    }// GEN-LAST:event_Number0ActionPerformed

    private void DelButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_DelButtonActionPerformed
	if (NumberField.getCaretPosition() != NumberField.getText().length()) { // only
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
	    int tmpcaretpos = NumberField.getCaretPosition();
	    NumberField.setText(NumberField.getText().substring(0, NumberField.getCaretPosition())
		    + NumberField.getText().substring(NumberField.getCaretPosition() + 1, NumberField.getText().length()));
	    NumberField.setCaretPosition(tmpcaretpos);
	}
    }// GEN-LAST:event_DelButtonActionPerformed

    private void BackspaceButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_BackspaceButtonActionPerformed
	if (NumberField.getCaretPosition() != 0) { // only do something if the
						   // caret is not the at the
						   // beginning
	    int tmpcaretpos = NumberField.getCaretPosition();
	    NumberField.setText(NumberField.getText().substring(0, NumberField.getCaretPosition() - 1)
		    + NumberField.getText().substring(NumberField.getCaretPosition(), NumberField.getText().length()));
	    NumberField.setCaretPosition(tmpcaretpos - 1);
	}
    }// GEN-LAST:event_BackspaceButtonActionPerformed

    private void NumberPlusOneActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_NumberPlusOneActionPerformed
	Float temp = Float.parseFloat(NumberField.getText()) + 1.0f;
	if (temp < 0) {
	    temp = 0.0f;
	}
	NumberField.setText(String.valueOf(temp));
    }// GEN-LAST:event_NumberPlusOneActionPerformed

    private void Pos1ButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_Pos1ButtonActionPerformed
	NumberField.setCaretPosition(0);
	NumberField.getCaret().setVisible(true);
    }// GEN-LAST:event_Pos1ButtonActionPerformed

    private void EndButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_EndButtonActionPerformed
	NumberField.setCaretPosition(NumberField.getText().length());
	NumberField.getCaret().setVisible(true);
    }// GEN-LAST:event_EndButtonActionPerformed

    private void NumberPlusTenActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_NumberPlusTenActionPerformed
	Float temp = Float.parseFloat(NumberField.getText()) + 10.0f;
	if (temp < 0) {
	    temp = 0.0f;
	}
	NumberField.setText(String.valueOf(temp));
    }// GEN-LAST:event_NumberPlusTenActionPerformed

    private void NumberMinusOneActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_NumberMinusOneActionPerformed
	Float temp = Float.parseFloat(NumberField.getText()) - 1;
	if (temp < 0) {
	    temp = 0.0f;
	}
	NumberField.setText(String.valueOf(temp));
    }// GEN-LAST:event_NumberMinusOneActionPerformed

    private void NumberMinus10ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_NumberMinus10ActionPerformed
	Float temp = Float.parseFloat(NumberField.getText()) - 10.0f;
	if (temp < 0) {
	    temp = 0.0f;
	}
	NumberField.setText(String.valueOf(temp));
    }// GEN-LAST:event_NumberMinus10ActionPerformed

    private void NumberCommaActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_NumberCommaActionPerformed
	NumberField.setText(NumberField.getText() + ".");
    }// GEN-LAST:event_NumberCommaActionPerformed

    private void LeftButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_LeftButtonActionPerformed
	NumberField.setCaretPosition(NumberField.getCaretPosition() - 1);
	NumberField.getCaret().setVisible(true);
    }// GEN-LAST:event_LeftButtonActionPerformed

    private void RightButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_RightButtonActionPerformed
	NumberField.setCaretPosition(NumberField.getCaretPosition() + 1);
	NumberField.getCaret().setVisible(true);
    }// GEN-LAST:event_RightButtonActionPerformed
     // Variables declaration - do not modify//GEN-BEGIN:variables

    private EButton BackspaceButton;
    private javax.swing.JPanel ConfirmationPanel;
    private EButton DelButton;
    private EButton EndButton;
    private javax.swing.JPanel Keypad;
    private EButton LeftButton;
    private EButton Number0;
    private EButton Number1;
    private EButton Number2;
    private EButton Number3;
    private EButton Number4;
    private EButton Number5;
    private EButton Number6;
    private EButton Number7;
    private EButton Number8;
    private EButton Number9;
    private EButton NumberClear;
    private EButton NumberComma;
    private javax.swing.JTextField NumberField;
    private EButton NumberMinus10;
    private EButton NumberMinusOne;
    private javax.swing.JPanel NumberPanel;
    private EButton NumberPlusOne;
    private EButton NumberPlusTen;
    private EButton Pos1Button;
    private EButton RightButton;
    private EButton SettingsCancelButton;
    private EButton SettingsOKButton;
    private javax.swing.JLabel Validation;
    private javax.swing.JPanel bg;
    private JLabel label;
    private JLabel label_1;
}
