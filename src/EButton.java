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
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Paint;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;
import net.java.balloontip.BalloonTip;
import net.java.balloontip.BalloonTip.AttachLocation;
import net.java.balloontip.BalloonTip.Orientation;
import net.java.balloontip.styles.BalloonTipStyle;
import net.java.balloontip.styles.MinimalBalloonStyle;

public class EButton extends JButton implements java.io.Serializable {
    //private static final long serialVersionUID = 21L;

    private ElphelVision Parent;
    private Color DefaultBorderColor = new Color(70, 70, 70);
    private Color DefaultBorderColorChecked = new Color(255, 255, 255);
    private Color DefaultBorderColorDisabled = new Color(30, 30, 30);
    private Color DefaultBorderColorHighlighted = new Color(20, 80, 130);
    private Color DefaultTextColor = new Color(255, 255, 255);
    private Color DefaultTextColorChecked = new Color(0, 0, 0);
    private Color DefaultTextColorHighlighted = new Color(200, 200, 255);
    private Color DefaultTextColorDisabled = new Color(100, 100, 100);
    private Color DefaultBackgroundColorGradientStart = new Color(40, 40, 40);
    private Color DefaultBackgroundColorGradientEnd = new Color(0, 0, 0);
    private Color DefaultBackgroundColorCheckedGradientStart = new Color(200, 200, 200);
    private Color DefaultBackgroundColorCheckedGradientEnd = new Color(255, 255, 255);
    private Color DefaultBackgroundColorDisabledGradientStart = new Color(20, 20, 20);
    private Color DefaultBackgroundColorDisabledGradientEnd = new Color(0, 0, 0);
    private Color DefaultBackgroundColorHighlightedGradientStart = new Color(50, 60, 100);
    private Color DefaultBackgroundColorHighlightedGradientEnd = new Color(0, 0, 0);
    private int BorderWidth = 2;
    private int CornerRadius = 12;
    private int FontSize = 11;
    private int FontWeight = Font.PLAIN;
    private boolean Checked = false;
    private boolean Highlighted = false;
    private String ParameterName = "";
    private String AdditionalValue;
    private BalloonTip BalloonToolTip = null;
    private Timer ToolTipDelayTimer;
    private float ToolTipDelayLength = 1; //seconds
    private String ToolTipText = null;

    public EButton() {
        this.setPreferredSize(new Dimension(80, 35));

        // This constructor is just to maintain compatibility, use EButton(ElphelVision parent) instead.
        // To get rid of these implementations
        System.out.println("Warning: called EButton Constructor without parent parameter - use new EButton(Parent); instead - " + this.getClass());
    }

    public EButton(ElphelVision parent) {
        final EButton me = this;
        Parent = parent;
        this.setRolloverEnabled(false);
        this.setPreferredSize(new Dimension(80, 35));
        this.setContentAreaFilled(false);
        this.setFocusPainted(false);
        this.setMargin(new Insets(0, 0, 0, 0));
        this.setBorderPainted(false);
        this.DefaultBorderColor = Parent.Settings.GetButtonBorderColor();
        this.DefaultBorderColorChecked = Parent.Settings.GetButtonBorderColorChecked();
        this.DefaultTextColor = Parent.Settings.GetButtonTextColor();
        this.DefaultTextColorChecked = Parent.Settings.GetButtonTextColorChecked();
        this.setForeground(DefaultTextColor);
        this.DefaultBackgroundColorGradientStart = Parent.Settings.GetButtonBackgroundColorGradientStart();
        this.DefaultBackgroundColorGradientEnd = Parent.Settings.GetButtonBackgroundColorGradientEnd();
        this.DefaultBackgroundColorCheckedGradientStart = Parent.Settings.GetButtonBackgroundColorCheckedGradientStart();
        this.DefaultBackgroundColorCheckedGradientEnd = Parent.Settings.GetButtonBackgroundColorCheckedGradientEnd();
        this.DefaultBorderColorDisabled = Parent.Settings.getButtonBorderColorDisabled();
        this.DefaultBorderColorHighlighted = Parent.Settings.getButtonBorderColorHighlighted();
        this.DefaultTextColorHighlighted = Parent.Settings.getButtonTextColorHighlighted();
        this.DefaultTextColorDisabled = Parent.Settings.getButtonTextColorDisabled();
        this.DefaultBackgroundColorDisabledGradientStart = Parent.Settings.getButtonBackgroundColorDisabledGradientStart();
        this.DefaultBackgroundColorDisabledGradientEnd = Parent.Settings.getButtonBackgroundColorDisabledGradientEnd();
        this.DefaultBackgroundColorHighlightedGradientStart = Parent.Settings.getButtonBackgroundColorHighlightedGradientStart();
        this.DefaultBackgroundColorHighlightedGradientEnd = Parent.Settings.getButtonBackgroundColorHighlightedGradientEnd();
        this.CornerRadius = Parent.Settings.GetButtonCornerRadius();
        this.BorderWidth = Parent.Settings.GetButtonBorderWidth();
        this.FontSize = Parent.Settings.GetButtonFontSize();
        this.FontWeight = Parent.Settings.GetButtonFontWeight();
        this.setFont(new Font(Parent.Settings.GetButtonFontName(), this.FontWeight, this.FontSize));

        addMouseListener(new java.awt.event.MouseAdapter() {

            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                setHighlighted(true);
                ToolTipDelayTimer = new Timer();
                ToolTipDelayTimer.schedule(new ToolTipTask(me), (int) (ToolTipDelayLength * 1000.0f));
                SetToolTipVisible(false);
            }

            @Override
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                setHighlighted(false);
                SetToolTipVisible(false);
                ToolTipDelayTimer.cancel();
            }

            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                setHighlighted(false);
                SetToolTipVisible(false);
                ToolTipDelayTimer.cancel();
                repaint();
                super.mouseClicked(evt);
            }
        });
    }

    public float getToolTipDelayLength() {
        return ToolTipDelayLength;
    }

    public void setToolTipDelayLength(float ToolTipDelayLength) {
        this.ToolTipDelayLength = ToolTipDelayLength;
    }

    class ToolTipTask extends TimerTask {

        EButton Parent = null;

        public ToolTipTask(EButton parent) {
            Parent = parent;
        }

        public void run() {
            if ((!"".equals(Parent.getToolTipText())) && (Parent.getToolTipText() != null)) {
                Parent.SetToolTipVisible(true);
            }
            ToolTipDelayTimer.cancel();
        }
    }

    public void SetToolTipVisible(boolean showtooltip) {
        if (BalloonToolTip != null) {
            BalloonToolTip.setVisible(showtooltip);
        }
    }

    @Override
    public void setToolTipText(String text) {
        this.ToolTipText = text;

        JLabel Tooltiptext = new JLabel(text);
        Tooltiptext.setFont(new Font(Parent.Settings.GetButtonFontName(), this.FontWeight, 10));
        BalloonTipStyle style = new MinimalBalloonStyle(new Color(210, 210, 210), 4);
        BalloonToolTip = new BalloonTip(this, Tooltiptext, style, Orientation.LEFT_ABOVE, AttachLocation.NORTH, 20, 8, false);
        BalloonToolTip.setOpaque(true);
        BalloonToolTip.setPadding(5);
        BalloonToolTip.setDoubleBuffered(true);
        BalloonToolTip.setVisible(false);
        BalloonToolTip.setBackground(Color.black);
    }

    @Override
    public String getToolTipText() {
        return this.ToolTipText;
    }

    public void ToggleChecked() {
        if (this.Checked) {
            setChecked(false);
        } else {
            setChecked(true);
        }
        this.repaint();
    }

    public void setParameterName(String name) {
        this.ParameterName = name;
    }

    public String getParameterName() {
        return this.ParameterName;
    }

    public void setValue(String val) {
        this.AdditionalValue = val;
        this.repaint();
    }

    public String getValue() {
        return this.AdditionalValue;
    }

    public void setCornerRadius(int newradius) {
        this.CornerRadius = newradius;
        this.repaint();
    }

    public void setChecked(boolean checked) {
        this.Checked = checked;
        this.repaint();
    }

    public boolean isChecked() {
        return this.Checked;
    }

    @Override
    public void setForeground(java.awt.Color newcolor) {
        if (newcolor != null) { // Evil hack to override default colors but maintain custom color settings
            if ((newcolor.getRed() == 51) && (newcolor.getBlue() == 51) && (newcolor.getGreen() == 51)) {
                newcolor = DefaultTextColor;
            }
        }
        super.setForeground(newcolor);
    }

    @Override
    public void paint(Graphics g) {
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

        // Gradients
        GradientPaint DarkGradient = new GradientPaint(0, 0, DefaultBackgroundColorGradientStart, 0, 25, DefaultBackgroundColorGradientEnd, false);
        GradientPaint CheckedGradient = new GradientPaint(0, 0, DefaultBackgroundColorCheckedGradientStart, 0, 25, DefaultBackgroundColorCheckedGradientEnd, true);
        GradientPaint DisabledGradient = new GradientPaint(0, 0, DefaultBackgroundColorDisabledGradientStart, 0, 25, DefaultBackgroundColorDisabledGradientEnd, true);
        GradientPaint HighlightGradient = new GradientPaint(0, 0, DefaultBackgroundColorHighlightedGradientStart, 0, 25, DefaultBackgroundColorHighlightedGradientEnd, true);

        if (this.isEnabled() == false) {
            // Button Fill
            g2.setPaint(DisabledGradient);
            g2.fillRoundRect(1, 1, x - 2, y - 2, this.CornerRadius - 2, this.CornerRadius - 2);
            // Button Border
            g2.setPaint(DefaultBorderColorDisabled);
            g2.setStroke(new BasicStroke(1));
            g2.drawRoundRect(BorderWidth - 1, BorderWidth - 1, x - 2 * (BorderWidth - 1), y - 2 * (BorderWidth - 1), this.CornerRadius, this.CornerRadius);
        } else if (this.isHighlighted()) {
            // Button Fill
            g2.setPaint(HighlightGradient);
            g2.fillRoundRect(BorderWidth * 2, BorderWidth * 2, x - BorderWidth * 4, y - BorderWidth * 4, this.CornerRadius - BorderWidth * 2, this.CornerRadius - BorderWidth * 2);
            // Button Border
            g2.setPaint(DefaultBorderColorHighlighted);
            g2.setStroke(new BasicStroke(BorderWidth));
            g2.drawRoundRect(BorderWidth, BorderWidth, x - 2 * BorderWidth, y - 2 * BorderWidth, this.CornerRadius, this.CornerRadius);
        } else if (this.Checked) {
            // Button Fill
            g2.setPaint(CheckedGradient);
            g2.fillRoundRect(BorderWidth * 2, BorderWidth * 2, x - BorderWidth * 4, y - BorderWidth * 4, this.CornerRadius - BorderWidth * 2, this.CornerRadius - BorderWidth * 2);
            // Button Border
            g2.setPaint(DefaultBorderColorChecked);
            g2.setStroke(new BasicStroke(BorderWidth));
            g2.drawRoundRect(BorderWidth, BorderWidth, x - 2 * BorderWidth, y - 2 * BorderWidth, this.CornerRadius, this.CornerRadius);
        } else {
            // Button Fill
            g2.setPaint(DarkGradient);
            g2.fillRoundRect(1, 1, x - 2, y - 2, this.CornerRadius - 2, this.CornerRadius - 2);
            // Button Border
            g2.setPaint(DefaultBorderColor);
            g2.setStroke(new BasicStroke(1));
            g2.drawRoundRect(BorderWidth - 1, BorderWidth - 1, x - 2 * (BorderWidth - 1), y - 2 * (BorderWidth - 1), this.CornerRadius, this.CornerRadius);
        }

        // Button Text
        if (this.isEnabled() == false) {
            g2.setPaint(DefaultTextColorDisabled);
        } else if (this.isHighlighted()) {
            g2.setPaint(DefaultTextColorHighlighted);
        } else if (this.Checked) {
            g2.setPaint(DefaultTextColorChecked);
        } else {
            g2.setPaint(this.getForeground());
        }

        //Draw Text
        FontMetrics fm = g2.getFontMetrics();
        boolean multiline = false;
        if (this.getText().contains("\\n")) {
            multiline = true;
        }
        if (multiline) {
            String linecontent[] = this.getText().split("\\\\n");
            int lines = linecontent.length;

            for (int i = 0; i < lines; i++) {
                Rectangle2D area = fm.getStringBounds(linecontent[i], g2);
                int textx = 0, texty = 0;
                int totalmulitlineheight = (int) area.getHeight() * lines;
                if (this.getHorizontalAlignment() == 0) { // center
                    textx = (int) (getWidth() / 2 - area.getWidth() / 2);
                    texty = (int) (getHeight() / 2 - totalmulitlineheight / 2 + area.getHeight() - 2 + i * (int) area.getHeight());
                } else if (this.getHorizontalAlignment() == 2) { // left
                    textx = this.getMargin().left;
                    texty = (int) (getHeight() / 2 - totalmulitlineheight / 2 + area.getHeight() - 2 + i * (int) area.getHeight());
                }

                g2.drawString(linecontent[i], textx, texty);
            }
        } else {

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
        }

        // Draw the Icon Image
        if (this.getIconImage() != null) {
            //this.getIcon().paintIcon(this, g2, (int) (getWidth() / 2 - this.getIcon().getIconWidth() / 2), (int) (getHeight() / 2 - this.getIcon().getIconHeight() / 2));
            //setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/rgb.png")));

            BufferedImage img = null;
            try {
                img = ImageIO.read(this.getIconImage());
                int w = img.getWidth(null);
                int h = img.getHeight(null);
                BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
                Graphics gbi = bi.getGraphics();
                gbi.drawImage(img, 0, 0, null);

                float[] scales = {1f, 1f, 1f, 1f};
                float[] scales_highlighted = {1f, 1f, 1f, 1f};
                DefaultBorderColorHighlighted.getComponents(scales_highlighted);
                float[] scales_checked = {0f, 0f, 0f, 1f};
                float[] offsets = {0f, 0f, 0f, 0f};

                RescaleOp rop = null;

                // tint image depending on state
                if (this.isHighlighted()) {
                    rop = new RescaleOp(scales_highlighted, offsets, null);
                } else if (this.isChecked()) {
                    rop = new RescaleOp(scales_checked, offsets, null);
                }

                // draw at center (hardcoded for now)
                g2.drawImage(bi, rop, this.getWidth() / 2 - w / 2, this.getHeight() / 2 - h / 2);
            } catch (IOException ex) {
                Parent.WriteErrortoConsole(this.getIconImage().getPath() + " not found!");
                Logger.getLogger(EButton.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //Draw Additional Value
        if (this.AdditionalValue != null) {
            Rectangle2D area2 = fm.getStringBounds(this.AdditionalValue, g2);
            g2.drawString(this.AdditionalValue, (int) (getWidth() - area2.getWidth() - 6), (int) (getHeight() / 2 + area2.getHeight() / 2 - 2));
        }

        // We do this all ourselves now so no need to call:
        //super.paint(g);
    }
    URL IconPath = null;

    public void setIconImage(URL icon) {
        this.IconPath = icon;
    }

    public URL getIconImage() {
        return this.IconPath;
    }

    public boolean isHighlighted() {
        return Highlighted;
    }

    public void setHighlighted(boolean Highlighted) {
        this.Highlighted = Highlighted;
        this.repaint();
    }
}
