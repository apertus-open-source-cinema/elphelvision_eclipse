/*! Copyright (C) 2009 Apertus, All Rights Reserved
 *! Author : Apertus Team
 *! Description: Mplayer class taking care of anything related to mplayer
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

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;

// List of available commands: http://www1.mplayerhq.hu/DOCS/tech/slave.txt
public class Mplayer {

    static boolean debug = false;
    private ElphelVision Parent;
    private Process mplayerProcess;
    private PrintStream mplayerIn;
    private BufferedReader mplayerOutErr;
    private static Logger logger = Logger.getLogger(ElphelVision.class.getName());
    private JTextArea debugout;

    Mplayer(ElphelVision parent) {
        this.Parent = parent;
    }

    public void open(String file, String parameters, String mplayerpath) throws IOException {
        if (mplayerProcess == null) {
            String command = mplayerpath + " " + parameters + " " + file;

            Parent.WriteLogtoConsole("Starting MPlayer process: " + command);

            mplayerProcess = Runtime.getRuntime().exec(command);

            // create the piped streams where to redirect the standard output and error of MPlayer
            // specify a bigger pipesize
            PipedInputStream readFrom = new PipedInputStream(1024 * 1024);
            PipedOutputStream writeTo = new PipedOutputStream(readFrom);
            mplayerOutErr = new BufferedReader(new InputStreamReader(readFrom));

            // create the threads to redirect the standard output and error of MPlayer
            new LineRedirecter(debugout, mplayerProcess.getInputStream(), writeTo, "MPlayer says: ").start();
            new LineRedirecter(debugout, mplayerProcess.getErrorStream(), writeTo, "MPlayer encountered an error: ").start();

            // the standard input of MPlayer
            mplayerIn = new PrintStream(mplayerProcess.getOutputStream());
        } else {
            execute("loadfile \"" + file + "\" 0");
        }
        // wait to start playing
        waitForAnswer("Starting playback...");
        Parent.WriteLogtoConsole("Started playing: " + file);
    }

    public void close() {
        if (mplayerProcess != null) {
            execute("quit");
            try {
                mplayerProcess.waitFor();
            } catch (InterruptedException e) {
            }
            mplayerProcess = null;
        }

    }

    public File getPlayingFile() {
        String path = getProperty("path");
        return path == null ? null : new File(path);
    }

    public void togglePlay() {
        execute("pause");
    }

    public boolean isPlaying() {
        return mplayerProcess != null;
    }

    public long getTimePosition() {
        return getPropertyAsLong("time_pos");
    }

    public void setTimePosition(long seconds) {
        setProperty("time_pos", seconds);
    }

    public long getTotalTime() {
        return getPropertyAsLong("length");
    }

    public float getVolume() {
        return getPropertyAsFloat("volume");
    }

    public void setVolume(float volume) {
        setProperty("volume", volume);
    }

    protected String getProperty(String name) {
        if (name == null || mplayerProcess == null) {
            return null;
        }

        String s = "ANS_" + name + "=";
        String x = execute("get_property " + name, s);
        if (x == null) {
            return null;
        }

        if (!x.startsWith(s)) {
            return null;
        }

        return x.substring(s.length());
    }

    protected long getPropertyAsLong(String name) {
        try {
            return Long.parseLong(getProperty(name));
        } catch (NumberFormatException exc) {
        } catch (NullPointerException exc) {
        }
        return 0;
    }

    protected float getPropertyAsFloat(String name) {
        try {
            return Float.parseFloat(getProperty(name));
        } catch (NumberFormatException exc) {
        } catch (NullPointerException exc) {
        }
        return 0f;
    }

    protected void setProperty(String name, String value) {
        execute("set_property " + name + " " + value);
    }

    protected void setProperty(String name, long value) {
        execute("set_property " + name + " " + value);
    }

    protected void setProperty(String name, float value) {
        execute("set_property " + name + " " + value);
    }

    protected void ExecuteCommand(String command) {
        execute(command, null);
    }

    /** Sends a command to MPlayer..
     * @param command the command to be sent
     */
    private void execute(String command) {
        execute(command, null);
    }

    /** Sends a command to MPlayer and waits for an answer.
     * @param command the command to be sent
     * @param expected the string with which has to start the line; if null don't wait for an answer
     * @return the MPlayer answer
     */
    private String execute(String command, String expected) {
        if (mplayerProcess != null) {
            logger.info("Send to MPlayer the command \"" + command + "\" and expecting " + (expected != null ? "\"" + expected + "\"" : "no answer"));
            mplayerIn.print(command);
            mplayerIn.print("\n");
            mplayerIn.flush();
            //logger.info("Command sent");
            if (expected != null) {
                String response = waitForAnswer(expected);
                logger.info("MPlayer command response: " + response);
                return response;
            }

        }
        return null;
    }

    /** Read from the MPlayer standard output and error a line that starts with the given parameter and return it.
     * @param expected the expected starting string for the line
     * @return the entire line from the standard output or error of MPlayer
     */
    private String waitForAnswer(String expected) {
        // todo add the possibility to specify more options to be specified
        // todo use regexp matching instead of the beginning of a string
        String line = null;
        if (expected != null) {
            try {
                while ((line = mplayerOutErr.readLine()) != null) {
                    logger.info("Reading line: " + line);
                    if (line.startsWith(expected)) {
                        //debugoutput.append(line);
                        return line;
                    }
                }
            } catch (IOException e) {
            }
        }
        return line;
    }

    /** A thread that reads from an input stream and outputs to another line by line. */
    private static class LineRedirecter extends Thread {

        /** The input stream to read from. */
        private InputStream in;
        /** The output stream to write to. */
        private OutputStream out;
        /** The prefix used to prefix the lines when outputting to the logger. */
        private String prefix;
        private JTextArea debugoutput;

        /**
         * @param in the input stream to read from.
         * @param out the output stream to write to.
         * @param prefix the prefix used to prefix the lines when outputting to the logger.
         */
        LineRedirecter(JTextArea debugout, InputStream in, OutputStream out, String prefix) {
            this.in = in;
            this.out = out;
            this.prefix = prefix;
            //this.debugoutput = debugout;
        }

        public void run() {

            try {
                // creates the decorating reader and writer
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                PrintStream printStream = new PrintStream(out);
                String line;

                // read line by line
                while ((line = reader.readLine()) != null) {
                    //logger.info((prefix != null ? prefix : "") + line);
                    printStream.println(line);
                    //this.debugoutput.append(line + "\n");
                    }

            } catch (IOException exc) {
                logger.log(Level.WARNING, "An error has occured while grabbing lines", exc);
            }
        }
    }
}
