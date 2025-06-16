// most code taken from https://www.youtube.com/watch?v=PmOgruSPy3s

package heckintaikoyo;
import java.io.*;
import javax.sound.sampled.*;

public class Music {
    Clip clip;

    public void play(String filePath) {
        try {
            if (clip != null && clip.isRunning()) {
                clip.stop();
                clip.close();
            }
            File file = new File(filePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
            audioStream.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
            clip.close();
        }
    }
}