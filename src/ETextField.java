/*! Copyright (C) 2009 Apertus, All Rights Reserved
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

import java.awt.BasicStroke;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Paint;
import java.awt.RenderingHints;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.Icon;
import javax.swing.JTextField;

public class ETextField extends JTextField implements java.io.Serializable, FocusListener {

    private ElphelVision Parent;
    private Color DefaultBorderColor = new Color(255, 255, 255);
    private Color DefaultBorderColorActive = new Color(120, 170, 255);
    private Color DefaultTextColor = new Color(255, 255, 255);
    private Color DefaultTextColorActive = new Color(60, 0, 0);
    private Color DefaultBackgroundColorGradientStart = new Color(200, 200, 200);
    private Color DefaultBackgroundColorGradientEnd = new Color(255, 255, 255);
    private Color DefaultBackgroundColorActiveGradientStart = new Color(200, 200, 200);
    private Color DefaultBackgroundColorActiveGradientEnd = new Color(255, 255, 255);
    private int BorderWidth = 2;
    private int CornerRadius = 12;
    private int FontSize = 11;
    private int FontWeight = Font.PLAIN;
    private Icon KeyBoardIcon;
    private EButton KeyboardButton;

    public ETextField() {
        this.setBackground(Color.BLACK);
        this.setForeground(DefaultTextColor);
        this.setMargin(new Insets(0, 6, 0, 0));
        this.addFocusListener(this);
        this.setLayout(new FlowLayout(RIGHT));
        KeyboardButton = new EButton();
        KeyboardButton.setForeground(new java.awt.Color(217, 2, 2));
        KeyboardButton.setSize(45, 20);
        KeyboardButton.setMargin(new Insets(4, 4, 4, 4));
        KeyboardButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/keyboard.png")));
        KeyboardButton.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OpenOnScreenKeyboard();
            }
        });
        this.add(KeyboardButton);
    }

    public ETextField(ElphelVision parent) {
        Parent = parent;
        this.setBackground(Color.BLACK);
        this.setForeground(DefaultTextColor);
        this.setMargin(new Insets(0, 6, 0, 0));
        this.addFocusListener(this);
        this.setLayout(new FlowLayout(RIGHT));
        KeyboardButton = new EButton();
        KeyboardButton.setForeground(new java.awt.Color(217, 2, 2));
        KeyboardButton.setText("Keyboard");
        KeyboardButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/keyboard.png")));
        KeyboardButton.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OpenOnScreenKeyboard();
            }
        });
        this.add(KeyboardButton);
    }

    private void OpenOnScreenKeyboard() {
        Parent.NumberPanelFloat.Load("FPS", 1, this, "Text");
        CardLayout cl = (CardLayout) (Parent.GetCardManager().getLayout());
        cl.show(Parent.GetCardManager(), "NumberpanelFloat");
    }

    public void focusGained(FocusEvent e) {
        this.repaint();
    }

    public void focusLost(FocusEvent e) {
        this.repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (this.KeyBoardIcon != null) {
            if (!this.hasFocus()) {
                int iconWidth = KeyBoardIcon.getIconWidth();
                int iconHeight = KeyBoardIcon.getIconHeight();
                int x = this.getWidth() - iconWidth - 3;
                int y = (this.getHeight() - iconHeight) / 2;
                KeyBoardIcon.paintIcon(this, g, x, y);
            }
        }
    }

    @Override
    public void paintBorder(Graphics g) {
        setBackground(Color.BLACK);
        Graphics2D g2 = (Graphics2D) g;

        Paint oldPaint = g2.getPaint();
        g2.setPaint(oldPaint);

        g2.setStroke(new BasicStroke(2));
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        g2.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);

        int x = this.getWidth();
        int y = this.getHeight();
        // Button Border
        if (this.hasFocus()) {
            g2.setPaint(DefaultBorderColorActive);
        } else {
            g2.setPaint(DefaultBorderColor);
        }
        g2.setStroke(new BasicStroke(1));
        g2.drawRoundRect(BorderWidth - 1, BorderWidth - 1, x - 2 * (BorderWidth - 1), y - 2 * (BorderWidth - 1), this.CornerRadius, this.CornerRadius);
        //super.paintBorder(g);
    }
    /*@Override
    public void paint(Graphics g) {
    
    setBackground(Parent.Utils.GetPanelBackgroundColor());
    Graphics2D g2 = (Graphics2D) g;
    
    Paint oldPaint = g2.getPaint();
    g2.setPaint(oldPaint);
    
    g2.setStroke(new BasicStroke(2));
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
    g2.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
    g2.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
    g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
    g2.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
    
    int x = this.getWidth();
    int y = this.getHeight();
    
    // Gradients
    GradientPaint DarkGradient = new GradientPaint(0, 0, DefaultBackgroundColorGradientStart, 0, 25, DefaultBackgroundColorGradientEnd, false);
    GradientPaint CheckedGradient = new GradientPaint(0, 0, DefaultBackgroundColorActiveGradientStart, 0, 25, DefaultBackgroundColorActiveGradientEnd, true);
    
    if (this.Active) {
    // Button Fill
    g2.setPaint(CheckedGradient);
    g2.fillRoundRect(BorderWidth * 2, BorderWidth * 2, x - BorderWidth * 4, y - BorderWidth * 4, this.CornerRadius - BorderWidth * 2, this.CornerRadius - BorderWidth * 2);
    // Button Border
    } else {
    // Button Fill
    g2.setPaint(DarkGradient);
    g2.fillRoundRect(1, 1, x - 2, y - 2, this.CornerRadius - 2, this.CornerRadius - 2);
    // Button Border
    }
    /*
    // Button Text
    if (Active) {
    g2.setPaint(DefaultTextColorActive);
    } else {
    g2.setPaint(DefaultTextColor);
    }
    
    //Draw Text
    FontMetrics fm = g2.getFontMetrics();
    Rectangle2D area = fm.getStringBounds(this.getText(), g2);
    int textx = 0, texty = 0;
    if (this.getHorizontalAlignment() == 0) { // center
    textx = (int) (getWidth() / 2 - area.getWidth() / 2);
    texty = (int) (getHeight() / 2 + area.getHeight() / 2 - 2);
    } else if (this.getHorizontalAlignment() == 2) { // left
    textx = this.getMargin().left;
    texty = (int) (getHeight() / 2 + area.getHeight() / 2 - 2);
    }
    g2.drawString(this.getText(), textx, texty);
     */
    //super.paint(g);
    //super.paintComponent(g);
    //}*/
}
