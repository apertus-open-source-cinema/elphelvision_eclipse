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

//import java.awt.*;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JLayeredPane;

public class Guides extends javax.swing.JPanel {

    private ElphelVision Parent = null;
    private boolean DrawCenterX = false;
    private boolean DrawOuterX = false;
    private boolean DrawThirds = false;
    private boolean DrawSafeArea = false;

    public Guides() {
    }

    public Guides(ElphelVision parent) {
        this.Parent = parent;
    }

    public void SetParent(ElphelVision parent) {
        this.Parent = parent;
    }

    public void SetOptions(boolean drawCenterX, boolean drawOuterX, boolean drawThirds, boolean drawSafeArea) {
        this.DrawCenterX = drawCenterX;
        this.DrawOuterX = drawOuterX;
        this.DrawThirds = drawThirds;
        this.DrawSafeArea = drawSafeArea;
        //this.invalidate();
    }

    public void SetOptions(boolean[] inval) {
        this.DrawCenterX = inval[0];
        this.DrawOuterX = inval[1];
        this.DrawThirds = inval[2];
        this.DrawSafeArea = inval[3];
        this.invalidate();
    }

    public void run() {
    }
    int crosshair_length = 3; // in percent of image

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D g2 = (Graphics2D) g;
        g.setPaintMode();

        if (this.Parent != null) {
            int cross_x = (int) (crosshair_length / 100.0f * this.getWidth());
            int cross_y = (int) (crosshair_length / 100.0f * this.getHeight());

            if (DrawCenterX) {

                DrawShadowedLine(g2, this.getWidth() / 2 - cross_x / 2, this.getHeight() / 2, this.getWidth() / 2 + cross_x / 2, this.getHeight() / 2); //vertical
                DrawShadowedLine(g2, this.getWidth() / 2, this.getHeight() / 2 - cross_x / 2, this.getWidth() / 2, this.getHeight() / 2 + cross_x / 2); // horizontal
            }
            if (DrawOuterX) {
                DrawShadowedLine(g2, 0, this.getHeight() / 2, cross_x, this.getHeight() / 2); // left
                DrawShadowedLine(g2, this.getWidth() - cross_x, this.getHeight() / 2, this.getWidth(), this.getHeight() / 2); // right
                DrawShadowedLine(g2, this.getWidth() / 2, 0, this.getWidth() / 2, cross_y); // top
                DrawShadowedLine(g2, this.getWidth() / 2, this.getHeight() - cross_y, this.getWidth() / 2, this.getHeight()); // bottom
            }
            if (DrawThirds) {
                DrawShadowedLine(g2, 0, this.getHeight() / 3, this.getWidth(), this.getHeight() / 3);
                DrawShadowedLine(g2, 0, this.getHeight() * 2 / 3, this.getWidth(), this.getHeight() * 2 / 3);

                DrawShadowedLine(g2, this.getWidth() / 3, 0, this.getWidth() / 3, this.getHeight());
                DrawShadowedLine(g2, this.getWidth() * 2 / 3, 0, this.getWidth() * 2 / 3, this.getHeight());
            }
            if (DrawSafeArea) {
                // The official BBC suggestion is 3.5% / 5% per side
                DrawShadowedLine(g2, this.getWidth() * 5 / 100, this.getHeight() * 5 / 100, this.getWidth() * 95 / 100, this.getHeight() * 5 / 100); // top
                DrawShadowedLine(g2, this.getWidth() * 5 / 100, this.getHeight() * 95 / 100, this.getWidth() * 95 / 100, this.getHeight() * 95 / 100); // bottom
                DrawShadowedLine(g2, this.getWidth() * 5 / 100, this.getHeight() * 5 / 100, this.getWidth() * 5 / 100, this.getHeight() * 95 / 100); // left
                DrawShadowedLine(g2, this.getWidth() * 95 / 100, this.getHeight() * 5 / 100, this.getWidth() * 95 / 100, this.getHeight() * 95 / 100); // right
            }
        }
    }

    private void DrawShadowedLine(Graphics2D g2, int StartX, int StartY, int EndX, int EndY) {
        g2.setStroke(new BasicStroke(1));

        g2.setColor(new Color(0, 0, 0, 255));

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

        g2.setColor(new Color(255, 255, 255, 255));
        g2.drawLine(StartX, StartY, EndX, EndY);
    }
}
