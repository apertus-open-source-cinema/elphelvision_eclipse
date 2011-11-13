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
import javax.swing.JPanel;

public class Histogram extends JPanel implements Runnable, java.io.Serializable {

    int width, height;
    int i = 0;
    Thread animator;
    int fps = 10;
    ElphelVision Parent = null;

    public Histogram() {
        //width = width + 2;
        //height = 50;
    }

    public Histogram(ElphelVision parent) {
        this.Parent = parent;
        //width = width + 2;
        //height = 50;
    }

    public void SetParent(ElphelVision parent) {
        this.Parent = parent;
    }

    public void startAnimator() {
        animator = new Thread(this);
        animator.start();
        //setSize(width, height);
    }

    public void StopAnimator() {
        animator.interrupt();
        //setSize(width, height);
    }

    public void run() {
        while (Thread.currentThread() == animator) {

            repaint();

            try {
                Thread.sleep((int) (1.0f / fps * 1000.0f));
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    public int GetMin(Color channel) {
        int treshhold = 5; // if value is below treshhold we do not account for it to minimize noise influence
        int[][] hist_value = Parent.Camera.GetHistogram(0);

        int ret = 255;
        if (channel == Color.red) {
            for (int i = 0; i < hist_value[0].length; i++) {
                if (hist_value[0][i] > treshhold) {
                    ret = i;
                    break;
                }
            }
        }
        if (channel == Color.green) {
            for (int i = 0; i < hist_value[1].length; i++) {
                if (hist_value[1][i] > treshhold) {
                    ret = i;
                    break;
                }
            }
        }
        if (channel == Color.blue) {
            for (int i = 0; i < hist_value[2].length; i++) {
                if (hist_value[2][i] > treshhold) {
                    ret = i;
                    break;
                }
            }
        }
        return ret;
    }

    public int GetMax(Color channel) {
        int treshhold = 5; // if value is above treshhold we do not account for it to minimize noise influence
        int[][] hist_value = Parent.Camera.GetHistogram(0);

        int ret = 255;
        if (channel == Color.red) {
            for (int i = hist_value[0].length - 1; i >= 0; i--) {
                if (hist_value[0][i] > treshhold) {
                    ret = i;
                    break;
                }
            }
        }
        if (channel == Color.green) {
            for (int i = hist_value[1].length - 1; i >= 0; i--) {
                if (hist_value[1][i] > treshhold) {
                    ret = i;
                    break;
                }
            }
        }
        if (channel == Color.blue) { // blue 
            for (int i = hist_value[2].length - 1; i >= 0; i--) {
                if (hist_value[2][i] > treshhold) {
                    ret = i;
                    break;
                }
            }
        }
        return ret;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        width = this.getWidth();
        height = this.getHeight();

        if (this.Parent != null) {

            int[][] hist_value = Parent.Camera.GetHistogram(0);

            Graphics2D g2 = (Graphics2D) g;
            g.setPaintMode();

            //range background
            //10%
            g.setColor(new Color(5, 5, 5));
            g.fillRect(0, 0, width / 10, height - 9);
            // 30%
            g.setColor(new Color(15, 15, 15));
            g.fillRect(width / 10, 0, 3 * width / 10, height - 9);
            // 50%
            g.setColor(new Color(20, 20, 20));
            g.fillRect(3 * width / 10, 0, 5 * width / 10, height - 9);
            // 70%
            g.setColor(new Color(25, 25, 25));
            g.fillRect(5 * width / 10, 0, 7 * width / 10, height - 9);
            // 90%
            g.setColor(new Color(30, 30, 30));
            g.fillRect(7 * width / 10, 0, 9 * width / 10, height - 9);
            // 100%
            g.setColor(new Color(50, 50, 50));
            g.fillRect(9 * width / 10, 0, 10 * width / 10, height - 9);

            // range indicators
            g.setColor(new Color(35, 35, 35));
            g2.drawLine(width / 10, 0, width / 10, height - 9);
            g.setColor(new Color(40, 40, 40));
            g2.drawLine(3 * width / 10, 0, 3 * width / 10, height - 9);
            g.setColor(new Color(45, 45, 45));
            g2.drawLine(5 * width / 10, 0, 5 * width / 10, height - 9);
            g.setColor(new Color(50, 50, 50));
            g2.drawLine(7 * width / 10, 0, 7 * width / 10, height - 9);
            g.setColor(new Color(70, 70, 70));
            g2.drawLine(9 * width / 10, 0, 9 * width / 10, height - 9);

            // gradient
            int GradientHeight = 2;
            Color c1 = Color.black;
            Color c2 = Color.white;
            GradientPaint gradient = new GradientPaint(0, 0, c1, width, 0, c2, false);
            g2.setPaint(gradient);
            g2.fillRect(0, height - 8, width, GradientHeight);


            // min/max indicators
            //red
            g2.setStroke(new BasicStroke(2));
            g.setColor(new Color(255, 0, 0));
            g2.drawLine(GetMin(Color.red) * width / hist_value[2].length, height - 6, GetMax(Color.red) * width / hist_value[2].length, height - 6);
            //green
            g.setColor(new Color(0, 255, 0));
            g2.drawLine(GetMin(Color.green) * width / hist_value[2].length, height - 4, GetMax(Color.green) * width / hist_value[2].length, height - 4);
            //blue
            g.setColor(new Color(0, 0, 255));
            g2.drawLine(GetMin(Color.blue) * width / hist_value[2].length, height - 2, GetMax(Color.blue) * width / hist_value[2].length, height - 2);


            // coordinate system
            g2.setStroke(new BasicStroke(1));
            g2.setColor(Color.darkGray);
            g2.drawLine(0, height - 9, width - 1, height - 9);
            g2.drawLine(width - 1, height - 9, width - 1, 0);
            g2.drawLine(width - 1, 0, 0, 0);
            g2.drawLine(0, 0, 0, height - 9);


            //draw actual histogram bars
            // Why does this not work in a single loop? weird behaviour...
            g2.setStroke(new BasicStroke(width / hist_value[2].length));
            if (Parent.Camera.GetHistogramColorMode() == HistogramColorMode.RGB) {
                for (int i = 0; i < hist_value[2].length / 2; i++) {
                    int helper = 127 + i / 2;
                    // RED
                    Color RedGradient = new Color(helper, 0, 0);
                    g2.setColor(RedGradient);
                    g2.drawLine(i * width / hist_value[2].length, height - 9, i * width / hist_value[2].length, height - 9 - (hist_value[0][i] * (height - 9) / hist_value[2].length));

                    // GREEN
                    Color GreenGradient = new Color(0, helper, 0);
                    g2.setColor(GreenGradient);
                    g2.drawLine(i * width / hist_value[2].length, height - 9, i * width / hist_value[2].length, height - 9 - (hist_value[1][i] * (height - 9) / hist_value[2].length));

                    // BLUE
                    Color BlueGradient = new Color(0, 0, helper);
                    g2.setColor(BlueGradient);
                    g2.drawLine(i * width / hist_value[2].length, height - 9, i * width / hist_value[2].length, height - 9 - (hist_value[2][i] * (height - 9) / hist_value[2].length));

                    //YELLOW
                    if ((hist_value[1][i] > hist_value[2][i]) && (hist_value[0][i] > hist_value[2][i])) {
                        Color YellowGradient = new Color(helper, helper, 0);
                        g2.setColor(YellowGradient);
                        if (hist_value[1][i] > hist_value[0][i]) {
                            int valueheight = hist_value[0][i] * height / 256;
                            g2.drawLine(i * width / hist_value[2].length, height - 9, i * width / hist_value[2].length, height - 9 - (valueheight * (height - 9) / hist_value[2].length));
                        } else {
                            int valueheight = hist_value[1][i] * height / 256;
                            g2.drawLine(i * width / hist_value[2].length, height - 9, i * width / hist_value[2].length, height - 9 - (valueheight * (height - 9) / hist_value[2].length));
                        }
                    }

                    //PINK
                    if ((hist_value[2][i] > hist_value[1][i]) && (hist_value[0][i] > hist_value[1][i])) {
                        Color PinkGradient = new Color(helper, 0, helper);
                        g2.setColor(PinkGradient);
                        if (hist_value[2][i] > hist_value[0][i]) {
                            g2.drawLine(i * width / hist_value[2].length, height - 9, i * width / hist_value[2].length, height - 9 - (hist_value[0][i] * (height - 9) / hist_value[2].length));
                        } else {
                            g2.drawLine(i * width / hist_value[2].length, height - 9, i * width / hist_value[2].length, height - 9 - (hist_value[2][i] * (height - 9) / hist_value[2].length));
                        }
                    }

                    //CYAN
                    if ((hist_value[2][i] > hist_value[0][i]) && (hist_value[1][i] > hist_value[0][i])) {
                        Color CyanGradient = new Color(0, helper, helper);
                        g2.setColor(CyanGradient);
                        if (hist_value[2][i] > hist_value[1][i]) {
                            g2.drawLine(i * width / hist_value[2].length, height - 9, i * width / hist_value[2].length, height - 9 - (hist_value[1][i] * (height - 9) / hist_value[2].length));
                        } else {
                            g2.drawLine(i * width / hist_value[2].length, height - 9, i * width / hist_value[2].length, height - 9 - (hist_value[2][i] * (height - 9) / hist_value[2].length));
                        }
                    }

                    // WHITE
                    int lowest = 999999999;
                    if ((hist_value[0][i] * (height - 9) / width) < lowest) {
                        lowest = hist_value[0][i] * (height - 9) / hist_value[2].length;
                    }
                    if ((hist_value[1][i] * (height - 9) / width) < lowest) {
                        lowest = hist_value[1][i] * (height - 9) / hist_value[2].length;
                    }
                    if ((hist_value[2][i] * (height - 9) / width) < lowest) {
                        lowest = hist_value[2][i] * (height - 9) / hist_value[2].length;
                    }
                    Color WhiteGradient = new Color(helper, helper, helper);
                    g2.setColor(WhiteGradient);
                    g2.drawLine(i * width / hist_value[2].length, height - 9, i * width / hist_value[2].length, height - 9 - lowest);
                }
                for (int j = 128; j < hist_value[2].length; j++) {
                    int helper = 127 + j / 2;
                    // RED
                    Color RedGradient = new Color(helper, 0, 0);
                    g2.setColor(RedGradient);
                    g2.drawLine(j * width / hist_value[2].length, height - 9, j * width / hist_value[2].length, height - 9 - (hist_value[0][j] * (height - 9) / hist_value[2].length));

                    // GREEN
                    Color GreenGradient = new Color(0, helper, 0);
                    g2.setColor(GreenGradient);
                    g2.drawLine(j * width / hist_value[2].length, height - 9, j * width / hist_value[2].length, height - 9 - (hist_value[1][j] * (height - 9) / hist_value[2].length));

                    // BLUE
                    Color BlueGradient = new Color(0, 0, helper);
                    g2.setColor(BlueGradient);
                    g2.drawLine(j * width / hist_value[2].length, height - 9, j * width / hist_value[2].length, height - 9 - (hist_value[2][j] * (height - 9) / hist_value[2].length));

                    //YELLOW
                    if ((hist_value[1][j] > hist_value[2][j]) && (hist_value[0][j] > hist_value[2][j])) {
                        Color YellowGradient = new Color(helper, helper, 0);
                        g2.setColor(YellowGradient);
                        if (hist_value[1][j] > hist_value[0][j]) {
                            g2.drawLine(j * width / hist_value[2].length, height - 9, j * width / hist_value[2].length, height - 9 - (hist_value[0][j] * (height - 9) / hist_value[2].length));
                        } else {
                            g2.drawLine(j * width / hist_value[2].length, height - 9, j * width / hist_value[2].length, height - 9 - (hist_value[1][j] * (height - 9) / hist_value[2].length));
                        }
                    }


                    //PINK
                    if ((hist_value[2][j] > hist_value[1][j]) && (hist_value[0][j] > hist_value[1][j])) {
                        Color PinkGradient = new Color(helper, 0, helper);
                        g2.setColor(PinkGradient);
                        if (hist_value[2][j] > hist_value[0][j]) {
                            g2.drawLine(j * width / hist_value[2].length, height - 9, j * width / hist_value[2].length, height - 9 - (hist_value[0][j] * (height - 9) / hist_value[2].length));
                        } else {
                            g2.drawLine(j * width / hist_value[2].length, height - 9, j * width / hist_value[2].length, height - 9 - (hist_value[2][j] * (height - 9) / hist_value[2].length));
                        }
                    }

                    //CYAN
                    if ((hist_value[2][j] > hist_value[0][j]) && (hist_value[1][j] > hist_value[0][j])) {
                        Color CyanGradient = new Color(0, helper, helper);
                        g2.setColor(CyanGradient);
                        if (hist_value[2][j] > hist_value[1][j]) {
                            g2.drawLine(j * width / hist_value[2].length, height - 9, j * width / hist_value[2].length, height - 9 - (hist_value[1][j] * (height - 9) / hist_value[2].length));
                        } else {
                            g2.drawLine(j * width / hist_value[2].length, height - 9, j * width / hist_value[2].length, height - 9 - (hist_value[2][j] * (height - 9) / hist_value[2].length));
                        }
                    }

                    // WHITE
                    int lowest = 999999999;
                    if ((hist_value[0][j] * (height - 9) / width) < lowest) {
                        lowest = hist_value[0][j] * (height - 9) / hist_value[2].length;
                    }
                    if ((hist_value[1][j] * (height - 9) / width) < lowest) {
                        lowest = hist_value[1][j] * (height - 9) / hist_value[2].length;
                    }
                    if ((hist_value[2][j] * (height - 9) / width) < lowest) {
                        lowest = hist_value[2][j] * (height - 9) / hist_value[2].length;
                    }
                    Color WhiteGradient = new Color(helper, helper, helper);
                    g2.setColor(WhiteGradient);
                    g2.drawLine(j * width / hist_value[2].length, height - 9, j * width / hist_value[2].length, height - 9 - lowest);
                }
            } else if (Parent.Camera.GetHistogramColorMode() == HistogramColorMode.LUMINOSITY) {
                for (int i = 0; i < hist_value[2].length; i++) {
                    int helper = 127 + i / 2;
                    Color WhiteGradient = new Color(helper, helper, helper);
                    g2.setColor(WhiteGradient);
                    g2.drawLine(i * width / hist_value[2].length, height - 9, i * width / hist_value[2].length, height - 9 - (hist_value[0][i] * (height - 9) / hist_value[2].length));
                }
            }
        } else {
            // draw something so the area in the GUI designer isnt just black
            Graphics2D g2 = (Graphics2D) g;
            g.setPaintMode();

            g.setColor(new Color(5, 5, 5));
            g.fillRect(0, 0, width, height);
            // TODO doesnt work....why not
        }
    }

    public void stop() {
        animator = null;
    }
}
