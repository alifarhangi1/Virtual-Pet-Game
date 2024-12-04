package Testing;

import managers.TileManager;
import misc.GamePanel;
import misc.Tile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.*;

class TestTileManager {

    private TileManager tileManager;
    private GamePanel gamePanel;

    @BeforeEach
    void setUp() {
        gamePanel = new GamePanel("Maze Madness");
        tileManager = new TileManager(gamePanel, "Maze Madness");
    }

    @Test
    void testConstructor_InitializesTileArray() {
        assertNotNull(tileManager.tile, "Tile array should be initialized");
        assertEquals(10, tileManager.tile.length, "Tile array size should match the expected value");
    }

    @Test
    void testGetTileImage_LoadsTileImages() {
        tileManager.getTileImage();

        for (Tile tile : tileManager.tile) {
            if (tile != null) {
                assertNotNull(tile.image, "Tile images should be loaded");
            }
        }
    }

    @Test
    void testLoadMap_PopulatesMapTileNumArray() {
        tileManager.loadMap("map_maze.txt");

        assertNotNull(tileManager.mapTileNum, "Map tile numbers array should be populated");
        assertTrue(tileManager.mapTileNum.length > 0, "Map should contain rows of tile numbers");
    }

    @Test
    void testDraw_RendersWithinViewport() {
        // Cannot directly assert rendering; visually verify or use mocks/logging
        BufferedImage buffer = new BufferedImage(500, 500, BufferedImage.TYPE_INT_ARGB);
        assertDoesNotThrow(() -> tileManager.draw(buffer.createGraphics()), "Drawing tiles should not throw an exception");
    }
}
