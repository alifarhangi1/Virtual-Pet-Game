import javax.sound.sampled.*;
import java.io.IOException;
import java.io.InputStream;

/**
 * The MusicPlayerMinigame class handles background music and sound effects for the game.
 * It uses the Java Sound API to play, stop, and manage audio clips.
 */
public class MusicPlayerMinigame {

    /** Clip for playing background music. */
    private Clip musicClip;

    /** Clip for playing sound effects. */
    private Clip soundEffectClip;

    /**
     * Plays a background music track from the specified file.
     * The music loops continuously until stopped.
     *
     * @param filepath the path to the music file in the resources directory
     */
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

    /**
     * Plays a sound effect from the specified file.
     * The sound effect is played once and does not loop.
     *
     * @param filepath the path to the sound effect file in the resources directory
     */
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

    /**
     * Stops the currently playing background music.
     * This also closes the audio resources associated with the music.
     */
    public void stopMusic() {
        if (musicClip != null) {
            musicClip.stop();
            musicClip.close();
            musicClip = null; // Clear the reference to free resources
        }
    }
}

//import javax.sound.sampled.*;
//import java.io.IOException;
//import java.io.InputStream;
//
//public class MusicPlayerMinigame {
//    private Clip musicClip; // Clip for background music
//    private Clip soundEffectClip; // Clip for sound effects
//
//    // Play background music
//    public void playMusic(String filepath) {
//        try {
//            // Stop and close existing music clip, if any
//            if (musicClip != null && musicClip.isRunning()) {
//                musicClip.stop();
//                musicClip.close();
//            }
//
//            // Load the audio file from resources
//            AudioInputStream audioStream = AudioSystem.getAudioInputStream(
//                    getClass().getResource(filepath)
//            );
//
//            musicClip = AudioSystem.getClip();
//            musicClip.open(audioStream);
//            musicClip.loop(Clip.LOOP_CONTINUOUSLY); // Loop music indefinitely
//            musicClip.start();
//        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
//            e.printStackTrace();
//        }
//    }
//
//    // Play sound effect
//    public void playSound(String filepath) {
//        try {
//            InputStream audioStream = getClass().getResourceAsStream(filepath);
//            if (audioStream == null) {
//                throw new IOException("Resource not found: " + filepath);
//            }
//            AudioInputStream stream = AudioSystem.getAudioInputStream(audioStream);
//            soundEffectClip = AudioSystem.getClip();
//            soundEffectClip.open(stream);
//            soundEffectClip.start();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    // Stop background music
//    public void stopMusic() {
//        if (musicClip != null) {
//            musicClip.stop();
//            musicClip.close();
//            musicClip = null; // Clear the reference to free resources
//        }
//    }
//}
//
//
