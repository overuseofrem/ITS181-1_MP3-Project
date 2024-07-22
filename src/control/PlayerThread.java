package control;

import java.io.File;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

public class PlayerThread extends Thread{
    
    private String fileLocation;
    private boolean loop;
    private AudioInputStream audioStream;
    private Clip clip;
    private boolean isPaused;
    private boolean isStopped;
    private long pausePosition;
    
    public PlayerThread(String fileLocation, boolean loop) {
        this.fileLocation = fileLocation;
        this.loop = loop;
        isPaused = false;
        isStopped = false;
        pausePosition = 0;
    }
    
    public void run() {
        try {
            do {
                File audioFile = new File(fileLocation);
                audioStream = AudioSystem.getAudioInputStream(audioFile);

                AudioFormat format = audioStream.getFormat();
                DataLine.Info info = new DataLine.Info(Clip.class, format);

                if (!AudioSystem.isLineSupported(info)) {
                    System.err.println("Audio format not supported: " + format);
                    return;
                }

                clip = (Clip) AudioSystem.getLine(info);
                clip.open(audioStream);
                clip.start();

                while (clip.getMicrosecondLength() > clip.getMicrosecondPosition() && !isStopped) {
                    if (!isPaused) {
                        Thread.sleep(10);
                    } else {
                        pausePosition = clip.getMicrosecondPosition();
                        clip.stop();
                    }
                }

                clip.stop();
                clip.close();
                audioStream.close();
                if (loop && !isStopped) {
                    isPaused = false;
                    pausePosition = 0;
                }
            } while (loop && !isStopped);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException | InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    public void resumeSong() {
        if (isPaused && clip != null && clip.isOpen()) {
            isPaused = false;
            clip.setMicrosecondPosition(pausePosition);
            clip.start();
        }
    }
    
    public void stopSong() {
        isStopped = true;
        isPaused = false;
    }
    
    public void pauseSong() {
        if (!isPaused && clip != null && clip.isOpen()) {
            isPaused = true;
        }
    }
    
    public boolean isPaused() {
        return isPaused;
    }

    public boolean isStopped() {
        return isStopped;
    }
}

