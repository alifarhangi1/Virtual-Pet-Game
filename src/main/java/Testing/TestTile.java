package Testing;

import misc.Tile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestTile {

    private Tile tile;

    @BeforeEach
    void setUp() {
        tile = new Tile();
    }

    @Test
    void testConstructor_InitializesAttributes() {
        assertNull(tile.image, "Tile's image should initially be null");
        assertFalse(tile.collision, "Tile's collision property should default to false");
    }

    @Test
    void testSetCollision_ChangesCollisionProperty() {
        tile.collision = true;
        assertTrue(tile.collision, "Tile's collision property should reflect the set value");
    }
}
