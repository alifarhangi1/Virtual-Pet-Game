package managers;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class AudioManager {
    private static AudioManager instance;
    private Clip bgmClip;
    private static boolean introPlayed = false;
    private float currentVolume = 1.0f;

    public AudioManager() {
        // Private constructor for singleton
    }

    public static AudioManager getInstance() {
        if (instance == null) {
            instance = new AudioManager();
        }
        return instance;
    }

    public void playIntroSound() {
        if (!introPlayed) {
            try {
                File audioFile = new File("src/assets/audio/pet_quest_intro.wav");
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
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

    public void startBackgroundMusic() {
        if (bgmClip == null || !bgmClip.isRunning()) {
            try {
                File bgmFile = new File("src/assets/audio/menu_bgm.wav");
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
    public void playHoverSound() {
        try {
            File audioFile = new File("src/assets/audio/menu_hover.wav");
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void playButtonClickSound() {
        try {
            File audioFile = new File("src/assets/audio/button_click.wav");
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public static void playBackButtonClickSound() {
        try {
            File audioFile = new File("src/assets/audio/back_button_click.wav");
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void stopBackgroundMusic() {
        if (bgmClip != null && bgmClip.isRunning()) {
            bgmClip.stop();
        }
    }

    public void setVolume(float volume) {
        currentVolume = Math.max(0.0f, Math.min(1.0f, volume));
        if (bgmClip != null) {
            setVolume(bgmClip, currentVolume);
        }
    }

    private void setVolume(Clip clip, float volume) {
        if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            float dB = (float) (Math.log(volume) / Math.log(10.0) * 20.0);
            dB = Math.max(gainControl.getMinimum(), Math.min(gainControl.getMaximum(), dB));
            gainControl.setValue(dB);
        }
    }

    public void cleanup() {
        if (bgmClip != null) {
            bgmClip.close();
        }
    }
}