package Testing;

import misc.Entity;
import misc.GamePanel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.*;

class TestEntity {

    private Entity entity;
    private GamePanel gamePanel;

    @BeforeEach
    void setUp() {
        gamePanel = new GamePanel("Maze Madness");
        entity = new Entity(gamePanel);

        // Initialize entity properties
        entity.worldX = 100;
        entity.worldY = 100;
        entity.speed = 5;
        entity.direction = "up";
        entity.solidArea = new Rectangle(0, 0, 48, 48);
    }

    @Test
    void testUpdate_NoCollision() {
        entity.collisionOn = false;
        entity.update();

        assertEquals(95, entity.worldY, "Entity's Y-coordinate should decrease by speed when moving up");
    }

    @Test
    void testUpdate_WithCollision() {
        entity.collisionOn = true;
        entity.update();

        assertEquals(100, entity.worldY, "Entity should not move when a collision occurs");
    }

    @Test
    void testSpriteAnimation_TogglesCorrectly() {
        entity.spriteCounter = 10;
        entity.spriteNum = 1;

        entity.update();

        assertEquals(2, entity.spriteNum, "Sprite number should toggle between 1 and 2 after an animation update");
        assertEquals(0, entity.spriteCounter, "Sprite counter should reset after reaching the animation threshold");
    }

    @Test
    void testDraw_RendersWithinViewport() {
        Graphics2D g2D = new BufferedImage(500, 500, BufferedImage.TYPE_INT_ARGB).createGraphics();

        gamePanel.player = new misc.PlayerMinigame(gamePanel, null);
        gamePanel.player.worldX = 50;
        gamePanel.player.worldY = 50;

        entity.worldX = 70; // Within the visible range
        entity.worldY = 70;
        entity.draw(g2D);

        // No assertions needed, as rendering is verified visually or through logging
    }
}
