

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class MusicPlayer {
    private Clip musicClip; // Clip for background music
    private Clip soundEffectClip; // Clip for sound effects

    // Play background music
    public void playMusic(String filepath) {
        try {
            // Stop and close existing music clip, if any
            if (musicClip != null && musicClip.isRunning()) {
                musicClip.stop();
                musicClip.close();
            }

            // Load the audio file from resources
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(
                    getClass().getResource(filepath)
            );

            musicClip = AudioSystem.getClip();
            musicClip.open(audioStream);
            musicClip.loop(Clip.LOOP_CONTINUOUSLY); // Loop music indefinitely
            musicClip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    // Play sound effect
    public void playSound(String filepath) {
        try {
            InputStream audioStream = getClass().getResourceAsStream(filepath);
            if (audioStream == null) {
                throw new IOException("Resource not found: " + filepath);
            }
            AudioInputStream stream = AudioSystem.getAudioInputStream(audioStream);
            soundEffectClip = AudioSystem.getClip();
            soundEffectClip.open(stream);
            soundEffectClip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // Stop background music
    public void stopMusic() {
        if (musicClip != null) {
            musicClip.stop();
            musicClip.close();
            musicClip = null; // Clear the reference to free resources
        }
    }
}


