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

import java.awt.*;
import java.net.*;
import java.io.*;
import javax.swing.JPanel;

public class GammaCurve extends JPanel implements Runnable, java.io.Serializable {

    int width, height;
    int i = 0;
    URLConnection conn = null;
    BufferedReader data = null;
    String line;
    String result;
    StringBuffer buf = new StringBuffer();
    URL theURL = null;
    ElphelVision Parent = null;
    private Bezier BezierCurve;

    public GammaCurve() {
        width = 256;
        height = 256;
        BezierCurve = new Bezier(3, 256);
        BezierCurve.SetControlPoints(6, height, 150, height, 150, 0, 6 + 256, 0);
    }

    public GammaCurve(ElphelVision parent) {
        this.Parent = parent;
        width = 256;
        height = 256;
        BezierCurve = new Bezier(3, 256);
    }

    public void SetControlPoints (double X1, double Y1, double X2, double Y2, double X3, double Y3, double X4, double Y4) {
        BezierCurve.SetControlPoints(X1, Y1, X2, Y2, X3, Y3, X4, Y4);
    }

    public int[] GetGammaTable() {
        return BezierCurve.GetGammaTable();
    }

    public void SetParent(ElphelVision parent) {
        this.Parent = parent;
    }

    public void run() {
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        if (this.Parent != null) {

            int[] gamma_values = Parent.Camera.GetGammaCurve();

            Graphics2D g2 = (Graphics2D) g;
            g.setPaintMode();

            //range background
            //10%
            g.setColor(new Color(5, 5, 5));
            g.fillRect(6, 0, 10 * 256 / 10, height);
            // 30%
            g.setColor(new Color(15, 15, 15));
            g.fillRect(256 / 10 + 6, 0, 9 * 256 / 10, height);
            // 50%
            g.setColor(new Color(20, 20, 20));
            g.fillRect(3 * 256 / 10 + 6, 0, 7 * 256 / 10, height);
            // 70%
            g.setColor(new Color(25, 25, 25));
            g.fillRect(5 * 256 / 10 + 6, 0, 5 * 256 / 10, height);
            // 90%
            g.setColor(new Color(30, 30, 30));
            g.fillRect(7 * 256 / 10 + 6, 0, 3 * 256 / 10, height);
            // 100%
            g.setColor(new Color(50, 50, 50));
            g.fillRect(9 * 256 / 10 + 6, 0, 256 / 10 + 1, height);

            // range indicators vertical
            g.setColor(new Color(35, 35, 35));
            g2.drawLine(256 / 10 + 6, 0, 256 / 10 + 6, height - 1);
            g.setColor(new Color(40, 40, 40));
            g2.drawLine(3 * 256 / 10 + 6, 0, 3 * 256 / 10 + 6, height - 1);
            g.setColor(new Color(45, 45, 45));
            g2.drawLine(5 * 256 / 10 + 6, 0, 5 * 256 / 10 + 6, height - 1);
            g.setColor(new Color(50, 50, 50));
            g2.drawLine(7 * 256 / 10 + 6, 0, 7 * 256 / 10 + 6, height - 1);
            g.setColor(new Color(70, 70, 70));
            g2.drawLine(9 * 256 / 10 + 6, 0, 9 * 256 / 10 + 6, height - 1);


            // range indicators horizontal
            g.setColor(new Color(70, 70, 70));
            g2.drawLine(6, height / 10, width + 6, height / 10);
            g2.drawLine(6, 3 * height / 10, width + 6, 3 * height / 10);
            g2.drawLine(6, 5 * height / 10, width + 6, 5 * height / 10);
            g2.drawLine(6, 7 * height / 10, width + 6, 7 * height / 10);
            g2.drawLine(6, 9 * height / 10, width + 6, 9 * height / 10);

            // gradient
            Color c1 = Color.black;
            Color c2 = Color.white;
            GradientPaint gradienthorizontal = new GradientPaint(0, 0, c1, 256, 0, c2, false);
            g2.setPaint(gradienthorizontal);
            g2.fillRect(6, height + 2, width, 5);
            GradientPaint gradientvertical = new GradientPaint(0, 0, c2, 0, 256, c1, false);
            g2.setPaint(gradientvertical);
            g2.fillRect(0, 0, 5, height);

            // coordinate system
            g2.setStroke(new BasicStroke(1));
            g2.setColor(Color.darkGray);
            g2.drawLine(6, 256, 261, 256); // bottom border
            g2.drawLine(261, 256, 261, 0); // right border
            g2.drawLine(261, 0, 6, 0); // top border
            g2.drawLine(6, 0, 6, 256); // left border

            // gamma curve
            g2.setStroke(new BasicStroke(1));
            /*
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
            g2.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
            g2.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
            g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
             */
            g2.setColor(Color.WHITE);
            for (int i = 0; i < gamma_values.length; i++) {
                if (i == 0) { // FIRST PIECE OF LINE
                    g2.drawLine(6, height, 7, height - (gamma_values[i] * (height) / 256));
                } else { // ALL OTHER PIECES
                    int Y_this = height - (gamma_values[i] * (height) / 256);
                    int Y_previous = height - (gamma_values[i - 1] * (height) / 256);
                    g2.drawLine(i + 6, Y_previous, i + 1 + 6, Y_this);
                }
            }

            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            BezierCurve.Compute();
            BezierCurve.Draw(g2);
            
            // draw black level
            g2.drawLine(6, 256, 6 + Parent.Camera.GetBlacklevel(), 256);
        }
    }
}
