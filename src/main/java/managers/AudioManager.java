package managers;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * Class responsible for managing audio queues and music for the game
 *
 * @author Luca Duarte
 */
public class AudioManager {
    private static AudioManager instance;
    /** audio source */
    private Clip bgmClip;
    private static boolean introPlayed = false;
    /** volume quantity */
    private float currentVolume = 1.0f;

    public AudioManager() {
        /** Private constructor for singleton */
    }

    /**
     * @return instance of audio manager
     */
    public static AudioManager getInstance() {
        if (instance == null) {
            instance = new AudioManager();
        }
        return instance;
    }

    /**
     * Plays the intro music for the game when it first starts
     */
    public void playIntroSound() {
        if (!introPlayed) {
            try {
                URL audioURL = getClass().getResource("/audio/pet_quest_intro.wav");
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioURL);
                Clip clip = AudioSystem.getClip();
                clip.open(audioStream);
                setVolume(clip, currentVolume);
                clip.start();
                introPlayed = true;
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Starts background music for the title screen
     */
    public void startBackgroundMusic() {
        if (bgmClip == null || !bgmClip.isRunning()) {
            try {
                URL bgmFile = getClass().getResource("/audio/menu_bgm.wav");
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(bgmFile);
                bgmClip = AudioSystem.getClip();
                bgmClip.open(audioStream);
                setVolume(bgmClip, currentVolume);
                bgmClip.loop(Clip.LOOP_CONTINUOUSLY);
                bgmClip.start();
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Plays sound for when buttons are hovered
     */
    public void playHoverSound() {
        try {
            URL audioURL = getClass().getResource("/audio/menu_hover.wav");
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioURL);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    /**
     * Plays sound for when buttons are clicked
     */
    public void playButtonClickSound() {
        try {
            URL audioURL = getClass().getResource("/audio/button_click.wav");
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioURL);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    /**
     * Plays sound for when the back button is clicked
     */
    public void playBackButtonClickSound() {
        try {
            URL audioURL = AudioManager.class.getResource("/audio/back_button_click.wav");
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioURL);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    /**
     * Stops background music from playing
     */
    public void stopBackgroundMusic() {
        if (bgmClip != null && bgmClip.isRunning()) {
            bgmClip.stop();
        }
    }

    /**
     * Sets main volume for music
     *
     * @param volume quantity
     */
    public void setVolume(float volume) {
        currentVolume = Math.max(0.0f, Math.min(1.0f, volume));
        if (bgmClip != null) {
            setVolume(bgmClip, currentVolume);
        }
    }

    /**
     * Sets volume for a specific audio source
     *
     * @param clip audio source
     * @param volume quantity
     */
    private void setVolume(Clip clip, float volume) {
        if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            float dB = (float) (Math.log(volume) / Math.log(10.0) * 20.0);
            dB = Math.max(gainControl.getMinimum(), Math.min(gainControl.getMaximum(), dB));
            gainControl.setValue(dB);
        }
    }

    /**
     * Cleans up files used for audio
     */
    public void cleanup() {
        if (bgmClip != null) {
            bgmClip.close();
        }
    }
}