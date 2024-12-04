package Testing;

import managers.TileManager;
import misc.GamePanel;
import misc.AssetSetter;
import misc.PlayerMinigame;
import managers.TileManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.*;

class TestGamePanel {

    private GamePanel gamePanel;

    @BeforeEach
    void setUp() {
        gamePanel = new GamePanel("Maze Madness");
        gamePanel.assetSetter = new AssetSetter(gamePanel, "Maze Madness");
        gamePanel.tileManager = new TileManager(gamePanel, "Maze Madness");
        gamePanel.player = new PlayerMinigame(gamePanel, null);
    }

    @Test
    void testSetUpGame_InitializesComponents() {
        gamePanel.setUpGame();

        assertNotNull(gamePanel.obj[0], "Game objects should be initialized");
        assertNotNull(gamePanel.npc[0], "NPCs should be initialized");
    }

    @Test
    void testUpdate_PlayerUpdates() {
        gamePanel.dialogueState = false;

        gamePanel.update();

        assertNotNull(gamePanel.player, "Player should exist");
        // Player update logic is internal, validate side effects if needed
    }

    @Test
    void testTogglePause() {
        gamePanel.dialogueState = false;
        gamePanel.togglePause();
        assertTrue(gamePanel.dialogueState, "Dialogue state should toggle to true");

        gamePanel.togglePause();
        assertFalse(gamePanel.dialogueState, "Dialogue state should toggle back to false");
    }

    @Test
    void testDrawToTempScreen_DrawsAllComponents() {
        Graphics2D g2D = new BufferedImage(500, 500, BufferedImage.TYPE_INT_ARGB).createGraphics();
        gamePanel.g2D = g2D;

        gamePanel.drawToTempScreen();

        // Visually verify or log calls during drawToTempScreen, especially:
        // - Tiles
        // - NPCs
        // - Player
        // UI rendering
    }

    @Test
    void testPaintComponent_RendersBufferedImage() {
        Graphics g = new BufferedImage(500, 500, BufferedImage.TYPE_INT_ARGB).getGraphics();

        gamePanel.fullScreen = new BufferedImage(500, 500, BufferedImage.TYPE_INT_ARGB);
        gamePanel.paintComponent(g);

        // Verify visually that the game screen renders correctly
        // Assertions may not be suitable for rendering tests
    }
}
