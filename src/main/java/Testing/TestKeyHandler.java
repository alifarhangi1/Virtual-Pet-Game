package Testing;

import managers.GameManager;
import misc.GamePanel;
import misc.KeyHandler;
import misc.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.event.KeyEvent;

import static org.junit.jupiter.api.Assertions.*;

class TestKeyHandler {

    private Player player;
    private GameManager manager;
    private KeyHandler keyHandler;
    private GamePanel gamePanel;

    @BeforeEach
    void setUp()
    {
        player = new Player();
        manager = GameManager.getInstance(player);
        gamePanel = new GamePanel("Maze Madness");
        keyHandler = new KeyHandler(gamePanel);
    }

    @Test
    void testKeyPressed_MovementKeys() {
        keyHandler.keyPressed(new KeyEvent(new JPanel(), KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_W, 'W'));
        assertTrue(keyHandler.upPressed, "Up key should be pressed");

        keyHandler.keyPressed(new KeyEvent(new JPanel(), KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_S, 'S'));
        assertTrue(keyHandler.downPressed, "Down key should be pressed");

        keyHandler.keyPressed(new KeyEvent(new JPanel(), KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_A, 'A'));
        assertTrue(keyHandler.leftPressed, "Left key should be pressed");

        keyHandler.keyPressed(new KeyEvent(new JPanel(), KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_D, 'D'));
        assertTrue(keyHandler.rightPressed, "Right key should be pressed");
    }

    @Test
    void testKeyPressed_TogglePause() {
        keyHandler.keyPressed(new KeyEvent(new JPanel(), KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_C, 'C'));
        assertTrue(gamePanel.dialogueState, "Pause should toggle to true");
    }

    @Test
    void testKeyReleased_MovementKeys() {
        keyHandler.keyPressed(new KeyEvent(new JPanel(), KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_W, 'W'));
        keyHandler.keyReleased(new KeyEvent(new JPanel(), KeyEvent.KEY_RELEASED, System.currentTimeMillis(), 0, KeyEvent.VK_W, 'W'));
        assertFalse(keyHandler.upPressed, "Up key should be released");

        keyHandler.keyPressed(new KeyEvent(new JPanel(), KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_S, 'S'));
        keyHandler.keyReleased(new KeyEvent(new JPanel(), KeyEvent.KEY_RELEASED, System.currentTimeMillis(), 0, KeyEvent.VK_S, 'S'));
        assertFalse(keyHandler.downPressed, "Down key should be released");

        keyHandler.keyPressed(new KeyEvent(new JPanel(), KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_A, 'A'));
        keyHandler.keyReleased(new KeyEvent(new JPanel(), KeyEvent.KEY_RELEASED, System.currentTimeMillis(), 0, KeyEvent.VK_A, 'A'));
        assertFalse(keyHandler.leftPressed, "Left key should be released");

        keyHandler.keyPressed(new KeyEvent(new JPanel(), KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_D, 'D'));
        keyHandler.keyReleased(new KeyEvent(new JPanel(), KeyEvent.KEY_RELEASED, System.currentTimeMillis(), 0, KeyEvent.VK_D, 'D'));
        assertFalse(keyHandler.rightPressed, "Right key should be released");
    }
}
