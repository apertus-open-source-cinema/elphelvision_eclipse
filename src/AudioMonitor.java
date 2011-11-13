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

import java.awt.*;
import java.awt.geom.Rectangle2D;
import javax.swing.JPanel;

public class AudioMonitor extends JPanel implements Runnable, java.io.Serializable {

    double RMS = 0;
    Thread animator;
    int fps = 10;
    int VUMeterWidth = 8;
    ElphelVision Parent = null;

    public AudioMonitor() {
    }

    public AudioMonitor(ElphelVision parent) {
        this.Parent = parent;
    }

    public void SetParent(ElphelVision parent) {
        this.Parent = parent;
    }

    public void startAnimator() {
        animator = new Thread(this);
        animator.start();
    }

    public void run() {
        if (!Parent.GetNoCameraParameter()) {
            while (Thread.currentThread() == animator) {
                RMS = Parent.Utils.SoundRecorder.getRmsDB();
                repaint();

                try {
                    Thread.sleep((int) (1.0f / fps * 1000.0f));
                } catch (InterruptedException e) {
                    break;
                }
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        if (this.Parent != null) {
            Graphics2D g2 = (Graphics2D) g;
            g.setPaintMode();

            // Bar Border
            g2.setStroke(new BasicStroke(1));
            g2.setColor(this.getForeground());
            g2.drawLine(4, 4, 4 + VUMeterWidth + 1, 4); // top
            g2.drawLine(4, 4, 4, this.getHeight() - 4); // left
            g2.drawLine(4, this.getHeight() - 4, 4 + VUMeterWidth + 1, this.getHeight() - 4); // bottom
            g2.drawLine(4 + VUMeterWidth + 1, 4, 4 + VUMeterWidth + 1, this.getHeight() - 4); // right

            // Outline Border
            g2.draw(new Rectangle2D.Double(0, 0, this.getWidth() - 1, this.getHeight() - 1));

            // Bar Filling
            float bar_length = (float) (RMS / Parent.Utils.SoundRecorder.getMaxDB());
            if (bar_length > 1) {
                bar_length = 1;
            }
            if (bar_length > 0.90) {
                g2.setColor(Color.red);
            } else if (bar_length > 0.70) {
                g2.setColor(Color.yellow);
            } else {
                g2.setColor(Color.green);
            }
            g2.fill(new Rectangle2D.Double(5, this.getHeight() - (bar_length * (this.getHeight() - 8)) - 4, VUMeterWidth, (bar_length * (this.getHeight() - 8))));


            // Peak
            float peak_length = (float) (Parent.Utils.SoundRecorder.getPeakDB() / Parent.Utils.SoundRecorder.getMaxDB());
            g2.setColor(Color.red);
            g2.fill(new Rectangle2D.Double(5, this.getHeight() - (peak_length * (this.getHeight() - 8 - 3) - 3) - 4, VUMeterWidth, 3));
        }
    }

    public void stop() {
        animator = null;
    }
}
