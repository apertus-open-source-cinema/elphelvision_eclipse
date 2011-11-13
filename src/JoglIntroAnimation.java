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
/*
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

public class JoglIntroAnimation implements GLEventListener {

    public void init(GLAutoDrawable drawable) {
        // Use debug pipeline
        // drawable.setGL(new DebugGL(drawable.getGL()));

        GL gl = drawable.getGL();
        System.err.println("INIT GL IS: " + gl.getClass().getName());

        // Enable VSync
        gl.setSwapInterval(1);

        // Setup the drawing area and shading mode
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        gl.glShadeModel(GL.GL_SMOOTH); // try setting this to GL_FLAT and see what happens.
    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL gl = drawable.getGL();
        GLU glu = new GLU();

        if (height <= 0) { // avoid a divide by zero error!

            height = 1;
        }
        final float h = (float) width / (float) height;
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(45.0f, h, 1.0, 20.0);
        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity();
    }
    private double last_frame_time = 0;
    private int fps_counter = 0;
    private double last_log = 0;

    public void display(GLAutoDrawable drawable) {
        double now = System.currentTimeMillis();
        double delta_time = now - last_frame_time;
        last_frame_time = now;
        //System.out.println(delta_time);

        if ((last_log + 1000) <= now) {
            last_log = now;
            //System.out.println("fps: " + fps_counter);
            fps_counter = 0;
        }
        fps_counter++;

        GL gl = drawable.getGL();

        // Clear the drawing area
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        // Reset the current matrix to the "identity"
        gl.glLoadIdentity();

        // Move the "drawing cursor" around
        gl.glTranslatef(-1.5f, 0.0f, -6.0f);



        gl.glLineWidth(1.0f);

        gl.glColor4f(0.0f, 0.5f, 1.0f, 0.1f);
        gl.glEnable(GL.GL_LINE_SMOOTH); //Antialiasing

        gl.glBegin(GL.GL_LINES);
        for (int j = 0; j < 512; j++) {
            float heigth = (float) (Math.sin(last_frame_time / 10000 * j / 10) * 2.0f);
            gl.glVertex2f(-0.5f + j * 0.02f, 0);
            gl.glVertex2f(-0.5f + j * 0.02f, heigth);
        }

        gl.glEnd();

        // Flush all drawing operations to the graphics card
        gl.glFlush();
    }

    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
    }
}
*/