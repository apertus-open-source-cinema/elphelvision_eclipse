/*! Copyright (C) 2009 Apertus, All Rights Reserved
 *! Author : Apertus Team
 *! Description:
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

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.GeneralPath;

public class Bezier {

    private Point[] controlPoints;
    private Point[] curvePoints;

    public Bezier(int order, int segments) {
        controlPoints = new Point[order + 1];
        for (int i = 0; i < controlPoints.length; i++) {
            controlPoints[i] = new Point(0, 0);
        }
        curvePoints = new Point[segments + 1];
        for (int i = 0; i < curvePoints.length; i++) {
            curvePoints[i] = new Point(0, 0);
        }
    }

    public void SetControlPoints(double X1, double Y1, double X2, double Y2, double X3, double Y3, double X4, double Y4) {
        controlPoints[0].setXY(X1, Y1);
        controlPoints[1].setXY(X2, Y2);
        controlPoints[2].setXY(X3, Y3);
        controlPoints[3].setXY(X4, Y4);
    }

    public int[] GetGammaTable() {
        int[] return_value = new int[255];

        for (int i = 0; i < 255; i++) {
            return_value[i] = (int)(256 - curvePoints[i].y);
        }
        return return_value;
    }

    public Point ClosestControlPoint(Point p) {
        double temp;
        double distance = p.dist(controlPoints[0]);
        Point closest = controlPoints[0];
        for (int i = 1; i < controlPoints.length; i++) {
            temp = p.dist(controlPoints[i]);
            if (distance > temp) {
                distance = temp;
                closest = controlPoints[i];
            }
        }
        return closest;
    }

    private void SubDivide(Point p1, Point p2, double t) {
        if (p1.x > p2.x) {
            p1.x -= Math.abs(p1.x - p2.x) * t;
        } else {
            p1.x += Math.abs(p1.x - p2.x) * t;
        }
        if (p1.y > p2.y) {
            p1.y -= Math.abs(p1.y - p2.y) * t;
        } else {
            p1.y += Math.abs(p1.y - p2.y) * t;
        }
    }

    public void Compute() {
        Point[] tmp = new Point[controlPoints.length];
        for (int i = 0; i < tmp.length; i++) {
            tmp[i] = new Point(0, 0);
        }
        for (int i = 0; i < curvePoints.length; i++) {
            double t = ((double) i) / (curvePoints.length - 1);
            for (int j = 0; j < controlPoints.length; j++) {
                tmp[j].setXY(controlPoints[j].x, controlPoints[j].y);
            }
            int Depth = tmp.length;
            while (Depth > 1) {
                for (int j = 0; j < Depth - 1; j++) {
                    SubDivide(tmp[j], tmp[j + 1], t);
                }
                Depth--;
            }
            System.out.println();
            curvePoints[i].setXY(tmp[0].x, tmp[0].y);
        }
    }

    private boolean AllPointsAreOnLeft(Point a, Point b) {
        /*
         * Area of the triangle ABC
         * 1 |a1 b1 c1|   1
         * -*|a2 b2 c2| = -*(a1b2 + b1c2 + c1a2 - a1c2 - b1a2 - c1b2)
         * 2 |1  1  1 |   2
         *
         * If the area is positive, then C is to the left of AB. If it is
         * negative, C is to the right of AB. If it is 0, A B and C are
         * collinear.
         */
        for (int i = 0; i < controlPoints.length; i++) {
            if ((controlPoints[i] == a) || (controlPoints[i] == b)) {
                continue;
            }
            if ((a.x * (-b.y) + b.x * (-controlPoints[i].y) + controlPoints[i].x * (-a.y) - a.x * (-controlPoints[i].y) - b.x * (-a.y) - controlPoints[i].x * (-b.y)) <= 0) {
                return false;
            }
        }
        return true;
    }

    public void Draw(Graphics2D g2d) {
        // Convex hull

        g2d.setColor(Color.LIGHT_GRAY);
        Point minX = controlPoints[0];
        for (int i = 1; i < controlPoints.length; i++) {
            minX = minX.x > controlPoints[i].x ? controlPoints[i] : minX;
        }
        GeneralPath path = new GeneralPath();
        path.moveTo(minX.x, minX.y);
        Point current = minX;
        for (int i = 0; i < controlPoints.length; i++) {
            if (controlPoints[i] != minX) {
                if (AllPointsAreOnLeft(minX, controlPoints[i])) {
                    current = controlPoints[i];
                }
            }
        }
        path.lineTo(current.x, current.y);
        while (current != minX) {
            for (int i = 0; i < controlPoints.length; i++) {
                if (controlPoints[i] != current) {
                    if (AllPointsAreOnLeft(current, controlPoints[i])) {
                        current = controlPoints[i];
                        break;
                    }
                }
            }
            path.lineTo(current.x, current.y);
        }
        /*g2d.draw(path);

        // Connected control points
        
        g2d.setColor(Color.BLACK);
        for (int i = 0; i < controlPoints.length - 1; i++) {
        g2d.drawLine(
        (int) controlPoints[i].x,
        (int) controlPoints[i].y,
        (int) controlPoints[i + 1].x,
        (int) controlPoints[i + 1].y);
        }*/

        // Bezier curve
        g2d.setColor(Color.RED);
        path = new GeneralPath();
        path.moveTo(curvePoints[0].x, curvePoints[0].y);
        for (int i = 1; i < curvePoints.length; i++) {
            path.lineTo(curvePoints[i].x, curvePoints[i].y);
        }
        g2d.draw(path);
    }
}
