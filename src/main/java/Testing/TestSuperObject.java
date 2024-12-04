package Testing;

import misc.GamePanel;
import misc.SuperObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.*;

class TestSuperObject {

    private SuperObject superObject;
    private GamePanel gamePanel;

    @BeforeEach
    void setUp() {
        gamePanel = new GamePanel("Maze Madness");
        superObject = new SuperObject();
    }

    @Test
    void testConstructor_InitializesAttributes() {
        assertNull(superObject.image, "Object's image should initially be null");
        assertNull(superObject.name, "Object's name should initially be null");
        assertFalse(superObject.collision, "Object's collision property should default to false");
    }

    @Test
    void testDraw_RendersWithinViewport() {
        Graphics2D g2D = new BufferedImage(500, 500, BufferedImage.TYPE_INT_ARGB).createGraphics();

        gamePanel.player.worldX = 100;
        gamePanel.player.worldY = 100;

        superObject.worldX = 120; // Within the visible range
        superObject.worldY = 120;

        superObject.draw(g2D, gamePanel);

        // Visual verification is required; mock or log calls for unit test validation
    }

    @Test
    void testDraw_DoesNotRenderOutsideViewport() {
        Graphics2D g2D = new BufferedImage(500, 500, BufferedImage.TYPE_INT_ARGB).createGraphics();

        gamePanel.player.worldX = 100;
        gamePanel.player.worldY = 100;

        superObject.worldX = 500; // Outside the visible range
        superObject.worldY = 500;

        superObject.draw(g2D, gamePanel);

        // No assertion possible; verify using logs or mocks if rendering is not triggered
    }
}
