/*! Copyright (C) 2011 Apertus, All Rights Reserved
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
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.RenderingHints;
import javax.swing.JPanel;

public class EPanel extends JPanel {

    private Color BorderColor = new Color(70, 70, 70);
    private int BorderCornerRadius = 12;
    private int BorderWidth = 1;
    private Color OutsideColor = new Color(0, 0, 0);

    public void SetOutsideColor(Color newcolor) {
        OutsideColor = newcolor;
    }

    public Color GetOutsideColor() {
        return OutsideColor;
    }

    public void paintBorder(Graphics g) {
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

        // Background Color Fill
        g2.setPaint(this.GetOutsideColor());
        g2.fillRect(0, 0, this.getWidth(), this.getHeight());

        // Background Color Fill
        if (this.isEnabled()) {
            int darkenvalue = 20;
            Color DarkerShade = new Color(Utils.MinMaxRange(this.getBackground().getRed() - darkenvalue, 0, 255), Utils.MinMaxRange(this.getBackground().getGreen() - darkenvalue, 0, 255), Utils.MinMaxRange(this.getBackground().getBlue() - darkenvalue, 0, 255));
            GradientPaint DarkGradient = new GradientPaint(0, 0, this.getBackground(), 0, this.getHeight(), DarkerShade, false);
            g2.setPaint(DarkGradient);
            g2.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), BorderCornerRadius, BorderCornerRadius);
        } else {
            int darkenvalue = 20;
            Color DarkerShade = new Color(Utils.MinMaxRange(this.getBackground().getRed() - darkenvalue, 0, 255), Utils.MinMaxRange(this.getBackground().getGreen() - darkenvalue, 0, 255), Utils.MinMaxRange(this.getBackground().getBlue() - darkenvalue, 0, 255));
            GradientPaint DarkGradient = new GradientPaint(0, 0, new Color(Utils.MinMaxRange(this.getBackground().getRed() - 40, 0, 255), Utils.MinMaxRange(this.getBackground().getGreen() - 40, 0, 255), Utils.MinMaxRange(this.getBackground().getBlue() - 40, 0, 255)), 0, this.getHeight(), DarkerShade, false);
            g2.setPaint(DarkGradient);
            g2.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), BorderCornerRadius, BorderCornerRadius);
        }

        // Border
        if (this.isEnabled()) {
            g2.setPaint(this.getForeground());
        } else {
            g2.setPaint(new Color(Utils.MinMaxRange(this.getForeground().getRed() - 40, 0, 255), Utils.MinMaxRange(this.getForeground().getGreen() - 40, 0, 255), Utils.MinMaxRange(this.getForeground().getBlue() - 40, 0, 255)));
        }
        g2.setStroke(new BasicStroke(BorderWidth));
        g2.drawRoundRect(0, 0, this.getWidth(), this.getHeight(), BorderCornerRadius, BorderCornerRadius);
    }

    @Override
    public void setEnabled(boolean enable) {
        super.setEnabled(enable);
        for (int i = 0; i < this.getComponentCount(); i++) {
            this.getComponent(i).setEnabled(enable);
        }
    }
}
