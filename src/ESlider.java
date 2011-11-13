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

import java.awt.Color;
import javax.swing.JSlider;


public class ESlider extends JSlider implements java.io.Serializable {

    public ESlider() {
        this.setBackground(Color.BLACK);
        this.setOrientation(JSlider.VERTICAL);
        this.setFocusable(false);

        this.setMajorTickSpacing(10);
        this.setMinorTickSpacing(1);
        this.setPaintTicks(true);
        this.setPaintLabels(true);
    }
}
