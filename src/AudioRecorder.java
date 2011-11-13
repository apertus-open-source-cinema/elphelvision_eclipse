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

import java.io.IOException;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.TargetDataLine;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.Line;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.Mixer.Info;

public class AudioRecorder extends Thread {

    private TargetDataLine Dataline;
    private AudioInputStream AudioInputStream;
    private AudioInputStream AudioMonitorStream;
    private AudioFileFormat.Type RecAudioFileFormat;
    private AudioFormat RecAudioFormat;
    private File AudioFile;
    ElphelVision Parent;
    private Thread Monitor;
    private Thread Recorder;
    private boolean MonitorRunning = false;
    private int MonitorCyclesPerSecond = 100;
    private int RecorderCyclesPerSecond = 5;
    private boolean Recording = false;
    private int MixerID = -1, FormatID = -1;

    public AudioRecorder(ElphelVision parent) {
        Parent = parent;
        Monitor = new Thread(this);
        Recorder = new Thread(this);
    }

    public void StartRecording() {
        MonitorRunning = true;
        Recording = true;

        if (!Dataline.isActive()) {
            Dataline.start();
        }
        if (!Monitor.isAlive()) {
            Monitor.start();
        }
        if (!Recorder.isAlive()) {
            Recorder.start();
        }

        Parent.WriteLogtoConsole("Audio Recording started");
    }

    public void StopRecording() {
        Dataline.stop();

        //Dataline.close();

        //MonitorRunning = false;
        //Recording = false;

        Parent.WriteLogtoConsole("Audio Recording stopped");
    }

    // called once all data has been read from Audiobuffer after stopping the recording
    private void PostStopRecording() {
        Dataline.close(); // once writing finished close the Dataline
        Recording = false; // set state after the above function finished when targetdataline does not provide any new data
        SetAudioOptions(); //reinit for next recording
        //We need to close the Dataline to end the recording, but we still want to monitor it afterwards so we need to start the monitor again
        StartMonitor();
    }

    public void StartMonitor() {
        if (!Dataline.isActive()) {
            Dataline.start();
        }
        if (!Monitor.isAlive()) {
            Monitor.start();
        }
        MonitorRunning = true;
        Parent.WriteLogtoConsole("Audio Monitoring started");
    }

    public synchronized void SetFilename(String filename) {
        AudioFile = new File(filename);
    }

    public String[] GetAvailableAudioMixers() {
        // Get all mixers from the system - USB audio device will have its own mixer
        Info[] mixerinfo = AudioSystem.getMixerInfo();
        String[] Return = new String[mixerinfo.length];
        for (int i = 0; i < mixerinfo.length; i++) {
            AudioFormat[] formats = GetMixerCapabilities(i);
            if (formats != null) {
                Return[i] = mixerinfo[i].getName() + " " + mixerinfo[i].getDescription();
            }
        }
        return Return;
    }

    public AudioFormat[] GetMixerCapabilities(int MixerIndex) {
        Info[] mixerinfo = AudioSystem.getMixerInfo();

        // select the Mixer to record from
        Mixer mixer = AudioSystem.getMixer(mixerinfo[MixerIndex]);
        Line.Info[] Infos = mixer.getTargetLineInfo();
        for (int i = 0; i < Infos.length; i++) {
            if (Infos[i] instanceof DataLine.Info) {
                DataLine.Info dataLineInfo = (DataLine.Info) Infos[i];
                // these are the available formats of the selected mixer
                AudioFormat[] supportedFormats = dataLineInfo.getFormats();
                return supportedFormats;
            }
        }
        return null;
    }

    public synchronized void SetAudioOptions() {
        if ((FormatID != -1) && (MixerID != -1)) {
            SetAudioOptions(MixerID, FormatID);
        }
    }

    public void SetAudioOptions(int MixerIndex, int FormatIndex) {
        FormatID = FormatIndex;
        MixerID = MixerIndex;

        Info[] mixerinfo = AudioSystem.getMixerInfo();

        // select the mixer to record from
        Mixer mixer = AudioSystem.getMixer(mixerinfo[MixerIndex]);

        // get all available audio formats on that device
        AudioFormat[] supportedFormats = GetMixerCapabilities(MixerIndex);

        // we use WAV by default
        RecAudioFileFormat = AudioFileFormat.Type.WAVE;

        // Create AudioFormat that the audio hardware supports
        // 48KHz is hardcoded for now until we create a custom field in the settings for it
        if (supportedFormats[FormatIndex].getSampleRate() == -1) {
            RecAudioFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 48000.0F, supportedFormats[FormatIndex].getSampleSizeInBits(), supportedFormats[FormatIndex].getChannels(), supportedFormats[FormatIndex].getFrameSize(), 48000.0F, supportedFormats[FormatIndex].isBigEndian());
        } else {
            RecAudioFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, supportedFormats[FormatIndex].getSampleRate(), supportedFormats[FormatIndex].getSampleSizeInBits(), supportedFormats[FormatIndex].getChannels(), supportedFormats[FormatIndex].getFrameSize(), supportedFormats[FormatIndex].getSampleRate(), supportedFormats[FormatIndex].isBigEndian());
        }

        TargetDataLine targetDataLine = null;
        try {
            targetDataLine = AudioSystem.getTargetDataLine(RecAudioFormat, mixerinfo[MixerIndex]);
            targetDataLine.open(RecAudioFormat);
        } catch (LineUnavailableException e) {
            Parent.WriteErrortoConsole("unable to get a recording line");
        }
        Dataline = targetDataLine;
        AudioInputStream = new AudioInputStream(Dataline);
        AudioMonitorStream = new AudioInputStream(Dataline);
    }
    private final float a2 = -1.9556f;
    private final float a3 = 0.9565f;
    private final float b1 = 0.9780f;
    private final float b2 = -1.9561f;
    private final float b3 = 0.9780f;
    private double peak;
    private static final double log10 = Math.log(10.0);
    private static final double maxDB = Math.max(0.0, 20.0 * Math.log((double) Short.MAX_VALUE) / log10);
    private final int peakHoldTime = 1000;
    private long then = System.currentTimeMillis();
    private double rms;
    private double average;

    @Override
    public void run() {
        while (Thread.currentThread() == Monitor) {
            if (MonitorRunning) {
                byte[] Byte = new byte[1024];
                int count = 0;
                try {
                    while ((count = AudioMonitorStream.read(Byte)) != -1) {
                        //Parent.WriteLogtoConsole("AudioMonitorStream.available(): " + AudioMonitorStream.available());
                        //Parent.WriteLogtoConsole("count: " + count);
                        short[] samples = new short[count / 2];
                        for (int i = 0; i < count / 2; i++) {
                            // 16 bit mode
                            int offset = i * 2;
                            samples[i] = (short) ((Byte[offset + 1] << 8) | (0x000000FF & Byte[offset]));
                            //Parent.WriteLogtoConsole("i: " + i + " sample: " + samples[i]);

                            // TODO: deal with any other than 16 bit mode
                        }

                        float energy = 0.0f;
                        average = 0.0f;
                        double y1 = 0.0f;
                        double y2 = 0.0f;

                        for (int i = 0; i < samples.length; i++) {
                            short i1 = samples[i];
                            double j = 0;
                            double k = 0;

                            if (i > 0) {
                                j = samples[i - 1];
                            }
                            if (i > 1) {
                                k = samples[i - 2];
                            }

                            double y = b1 * i1 + b2 * j + b3 * k - a2 * y1 - a3 * y2;

                            y2 = y1;
                            y1 = y;

                            double v2 = Math.abs(y);

                            long now = System.currentTimeMillis();

                            energy += v2 * v2;
                            average += v2;

                            if (v2 > peak) {
                                peak = v2;
                            } else if ((now - then) > peakHoldTime) {
                                peak = v2;
                                then = now;
                            }
                        }

                        rms = energy / samples.length;
                        rms = Math.sqrt(rms);
                        average /= samples.length;

                        //   Parent.WriteLogtoConsole("length: " + samples.length + " rms: " + rms + " average: " + average);
                    }
                } catch (IOException ex) {
                    Logger.getLogger(AudioRecorder.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    Thread.sleep((int) (1.0f / MonitorCyclesPerSecond * 1000.0f));
                } catch (InterruptedException e) {
                    break;
                }
            }
        }

        while (Thread.currentThread() == Recorder) {
            if (Recording) {
                try {
                    Parent.WriteLogtoConsole("Audio Writing started");
                    AudioSystem.write(AudioInputStream, RecAudioFileFormat, AudioFile);
                    PostStopRecording();
                } catch (IOException ex) {
                    Logger.getLogger(AudioRecorder.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                try {
                    Thread.sleep((int) (1.0f / RecorderCyclesPerSecond * 1000.0f));
                } catch (InterruptedException e) {
                    break;
                }
            }
        }
    }

    public final synchronized double getRmsDB() {
        return Math.max(0.0, 20.0 * Math.log(rms) / log10);
    }

    public final synchronized double getAverageDB() {
        return Math.max(0.0, 20.0 * Math.log(average) / log10);
    }

    public final synchronized double getPeakDB() {
        return Math.max(0.0, 20.0 * Math.log(peak) / log10);
    }

    public final synchronized boolean getIsClipping() {
        return (Short.MAX_VALUE) < (2 * peak);
    }

    public final synchronized double getMaxDB() {
        return maxDB;
    }
}

/*
 * How can I detect a buffer underrun or overrun?
The following is working reliably at least with the "Direct Audio Device" mixers:
SourceDataLine: underrun if (line.available() == line.getBufferSize())
SourceDataLine.available(): how much data can be written to the buffer. If the whole buffer can be written to, there is no data in the buffer to be rendered.
TargetDataLine: overrun if (line.available() == line.getBufferSize())
TargetDataLine.available(): how much data can be read from the buffer. If the whole buffer can be read, there is no space in the buffer for new data captured from the line.
 */
