/*! Copyright (C) 2010 Apertus, All Rights Reserved
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

import java.net.*;
import java.applet.*;

public class Audio {

    public class Sound // Holds one audio file
    {

        private AudioClip song; // Sound player
        private URL songPath; // Sound path

        Sound(String filename) {
            try {
                songPath = new URL(filename); // Get the Sound URL
                song = Applet.newAudioClip(songPath); // Load the Sound
            } catch (Exception e) {
            } // Satisfy the catch
        }

        public void playSound() {
            song.loop(); // Play
        }

        public void stopSound() {
            song.stop(); // Stop
        }

        public void playSoundOnce() {
            song.play(); // Play only once
        }
    }

    public void PlaySoundFile(String Filename) {
        Sound soundfile = new Sound(Filename);
        soundfile.playSoundOnce();
    }
}

