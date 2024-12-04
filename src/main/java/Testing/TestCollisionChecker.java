package Testing;

import misc.*;
import misc.GamePanel;
import misc.Tile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestCollisionChecker {

    private CollisionChecker collisionChecker;
    private GamePanel gamePanel;
    private Entity entity;

    @BeforeEach
    void setUp() {
        gamePanel = new GamePanel("Maze Madness");
        gamePanel.tileManager = new managers.TileManager(gamePanel, "Maze Madness");
        collisionChecker = new CollisionChecker(gamePanel);

        entity = new Entity(gamePanel);
        entity.worldX = 100;
        entity.worldY = 100;
        entity.solidArea.setBounds(0, 0, 48, 48);
        entity.direction = "up";
        entity.speed = 5;
    }

    @Test
    void testCheckTile_NoCollision() {
        gamePanel.tileManager.tile = new Tile[2];
        gamePanel.tileManager.tile[0] = new Tile();
        gamePanel.tileManager.tile[1] = new Tile();
        gamePanel.tileManager.tile[1].collision = false;

        gamePanel.tileManager.mapTileNum[2][1] = 0; // Non-collision tile
        collisionChecker.checkTile(entity);
        assertFalse(entity.collisionOn, "Entity should not collide with a non-collision tile");
    }

    @Test
    void testCheckTile_WithCollision() {
        gamePanel.tileManager.tile = new Tile[2];
        gamePanel.tileManager.tile[0] = new Tile();
        gamePanel.tileManager.tile[1] = new Tile();
        gamePanel.tileManager.tile[1].collision = true;

        gamePanel.tileManager.mapTileNum[2][1] = 1; // Collision tile
        collisionChecker.checkTile(entity);
        assertTrue(entity.collisionOn, "Entity should collide with the tile");
    }

    @Test
    void testCheckObject_NoCollision() {
        gamePanel.obj = new misc.SuperObject[1];
        misc.SuperObject object = new misc.SuperObject();
        object.collision = false;
        gamePanel.obj[0] = object;

        int result = collisionChecker.checkObject(entity, true);
        assertEquals(999, result, "There should be no collision with objects");
    }

    @Test
    void testCheckObject_WithCollision() {
        gamePanel.obj = new misc.SuperObject[1];
        misc.SuperObject object = new misc.SuperObject();
        object.collision = true;
        object.worldX = 100;
        object.worldY = 100;
        gamePanel.obj[0] = object;

        int result = collisionChecker.checkObject(entity, true);
        assertEquals(0, result, "Entity should collide with the object");
        assertTrue(entity.collisionOn, "Collision flag should be set to true");
    }

    @Test
    void testCheckPlayer_WithCollision() {
        gamePanel.player = new misc.PlayerMinigame(gamePanel, null);
        gamePanel.player.worldX = 100;
        gamePanel.player.worldY = 100;

        collisionChecker.checkPlayer(entity);
        assertTrue(entity.collisionOn, "Entity should collide with the player");
    }
}
