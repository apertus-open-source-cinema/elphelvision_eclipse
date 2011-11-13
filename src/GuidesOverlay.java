/*! Copyright (C) 2009 Apertus, All Rights Reserved
 *! Author : Apertus Team
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
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.Rectangle;


public class GuidesOverlay extends javax.swing.JFrame implements javax.swing.RootPaneContainer {

    private boolean DrawCenterX = false;
    private boolean DrawOuterX = false;
    private boolean DrawThirds = false;
    private boolean DrawSafeArea = false;
    int crosshair_length = 3; // in percent of image
    ElphelVision Parent;

    public GuidesOverlay(GraphicsConfiguration gc, ElphelVision parent) {
        super(gc);
        Parent = parent;
        initComponents();
        jPanel1.setOpaque(false);
        setAlwaysOnTop(true);
        setLocationRelativeTo(null);
    }

    public void SetOptions(boolean drawCenterX, boolean drawOuterX, boolean drawThirds, boolean drawSafeArea) {
        this.DrawCenterX = drawCenterX;
        this.DrawOuterX = drawOuterX;
        this.DrawThirds = drawThirds;
        this.DrawSafeArea = drawSafeArea;
        jPanel1.repaint();
    }

    public void SetOptions(boolean[] inval) {
        this.DrawCenterX = inval[0];
        this.DrawOuterX = inval[1];
        this.DrawThirds = inval[2];
        this.DrawSafeArea = inval[3];
        jPanel1.repaint();

    }

    public void SetVisibility(boolean visible) {
        this.setVisible(visible);
    }

    private void paintoverlay(Graphics g) {
        Parent.UpdateOverlayPosition();

        Graphics2D g2 = (Graphics2D) g;
        g.setPaintMode();

        float aspect_ratio = Parent.Camera.GetVideoAspectRatio();
        Rectangle bound = this.getBounds();
        // TODO the situation when video is taller than viewing area
        // video is wider than viewing area
        float correctionfactor_height = 1.0f - ((float) bound.width / aspect_ratio / (float) bound.height);

        int cross_x = (int) (crosshair_length / 100.0f * this.getWidth());
        int cross_y = (int) (crosshair_length / 100.0f * this.getHeight());

        // Apparently there is no official guideline for the size of the action safe area
        // The BBC suggests 5% per side so I guess that is good for us :)
        //http://en.wikipedia.org/wiki/Overscan
        int croppercentage = 95;


        float a = correctionfactor_height / 2.0f * (float) this.getHeight();
        float b = ((float) this.getHeight() * (1.0f - correctionfactor_height) * (100.0f - croppercentage) / 100.0f);

        if (DrawCenterX) {

            DrawShadowedLine(g2, this.getWidth() / 2 - cross_x / 2, this.getHeight() / 2, this.getWidth() / 2 + cross_x / 2, this.getHeight() / 2); //vertical
            DrawShadowedLine(g2, this.getWidth() / 2, this.getHeight() / 2 - cross_x / 2, this.getWidth() / 2, this.getHeight() / 2 + cross_x / 2); // horizontal
        }
        if (DrawOuterX) {
            DrawShadowedLine(g2, 0, this.getHeight() / 2, cross_x, this.getHeight() / 2); // left
            DrawShadowedLine(g2, this.getWidth() - cross_x, this.getHeight() / 2, this.getWidth(), this.getHeight() / 2); // right
            DrawShadowedLine(g2, this.getWidth() / 2, (int) (correctionfactor_height / 2 * this.getHeight()), this.getWidth() / 2, (int) (correctionfactor_height / 2 * this.getHeight() + cross_y)); // top
            DrawShadowedLine(g2, this.getWidth() / 2, (int) (this.getHeight() - (correctionfactor_height / 2 * this.getHeight())) - cross_y, this.getWidth() / 2, (int) (this.getHeight() - (correctionfactor_height / 2 * this.getHeight()))); // bottom
        }
        if (DrawThirds) {
            int Y1 = (int) (a + (1.0f / 3.0f * ((float) this.getHeight() - (2.0f * a))));
            int Y2 = (int) (a + (2.0f / 3.0f * ((float) this.getHeight() - (2.0f * a))));
            DrawShadowedLine(g2, 0, Y1, this.getWidth(), Y1); // horizontal
            DrawShadowedLine(g2, 0, Y2, this.getWidth(), Y2); // horizontal

            DrawShadowedLine(g2, this.getWidth() / 3, (int) a, this.getWidth() / 3, this.getHeight() - (int) a); // vertical
            DrawShadowedLine(g2, this.getWidth() * 2 / 3, (int) a, this.getWidth() * 2 / 3, this.getHeight() - (int) a); // vertical
        }
        if (DrawSafeArea) {

            int X1 = (int) (this.getWidth() * (100 - croppercentage) / 100);
            int Y1 = (int) (a + b);
            int X2 = (int) (this.getWidth() * croppercentage / 100);
            int Y2 = Y1;
            int X3 = X2;
            int Y3 = (int) (this.getHeight() - a - b);
            int X4 = X1;
            int Y4 = Y3;
            DrawShadowedLine(g2, X1, Y1, X2, Y2); // top
            DrawShadowedLine(g2, X2, Y2, X3, Y3); // right
            DrawShadowedLine(g2, X3, Y3, X4, Y4); // bottom
            DrawShadowedLine(g2, X4, Y4, X1, Y1); // left

            //DrawShadowedLine(g2, this.getWidth() * (100 - croppercentage) / 100, this.getHeight() * (100 - croppercentage) / 100, this.getWidth() * (100 - croppercentage) / 100, this.getHeight() * croppercentage / 100); // left
            //DrawShadowedLine(g2, this.getWidth() * croppercentage / 100, this.getHeight() * (100 - croppercentage) / 100, this.getWidth() * croppercentage / 100, this.getHeight() * croppercentage / 100); // right
        }
    }

    private void DrawShadowedLine(Graphics2D g2, int StartX, int StartY, int EndX, int EndY) {
        g2.setStroke(new BasicStroke(1));

        g2.setColor(new Color(0, 0, 0, 200));

        if (StartY == EndY) {
            // horizontal
            g2.drawLine(StartX, StartY + 1, EndX, EndY + 1);
            g2.drawLine(StartX, StartY - 1, EndX, EndY - 1);
        }
        if (StartX == EndX) {
            // vertical
            g2.drawLine(StartX + 1, StartY, EndX + 1, EndY);
            g2.drawLine(StartX - 1, StartY, EndX - 1, EndY);
        }

        g2.setColor(new Color(255, 255, 255, 200));
        g2.drawLine(StartX, StartY, EndX, EndY);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel() {
            protected void paintComponent(Graphics g) {
                if (g instanceof Graphics2D) {
                    paintoverlay(g);
                } else {
                    super.paintComponent(g);
                }
            }
        }
        ;

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(321, 250));
        setResizable(false);
        setUndecorated(true);

        jPanel1.setDoubleBuffered(false);
        jPanel1.setOpaque(false);
        jPanel1.setLayout(new java.awt.BorderLayout());
        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
