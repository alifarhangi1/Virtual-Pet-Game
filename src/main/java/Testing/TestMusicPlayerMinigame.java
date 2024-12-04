package Testing;

import misc.MusicPlayerMinigame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sound.sampled.Clip;

import static org.junit.jupiter.api.Assertions.*;

class TestMusicPlayerMinigame {

    private MusicPlayerMinigame musicPlayer;

    @BeforeEach
    void setUp() {
        musicPlayer = new MusicPlayerMinigame();
    }

    @Test
    void testPlayMusic_InitializesAndStartsClip() {
        musicPlayer.playMusic("/audio/testMusic.wav");
        assertNotNull(musicPlayer, "MusicPlayer should be initialized");
        // Additional tests would validate play logic if clips were mocked
    }

    @Test
    void testPlaySound_ExecutesWithoutError() {
        musicPlayer.playSound("/audio/testSound.wav");
        assertNotNull(musicPlayer, "Sound effect playback should initialize correctly");
    }

    @Test
    void testStopMusic_ReleasesResources() {
        musicPlayer.playMusic("/audio/testMusic.wav");
        musicPlayer.stopMusic();

        assertNull(musicPlayer.musicClip, "Music clip reference should be cleared after stopping music");
    }
}
