package Testing;

import misc.Dragon;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestDragon
{
    private Dragon testDragon;

    @BeforeEach
    void setUp()
    {
        testDragon = new Dragon("TestDragon");
    }

    @Test
    void testInitialStats() {
        assertEquals("TestDragon", testDragon.getName());
        assertEquals("misc.Dragon", testDragon.getType());
        assertEquals(200, testDragon.getMaxHP());
        assertEquals(150, testDragon.getMaxEnergy());
        assertEquals(100, testDragon.getHunger());
        assertEquals(95, testDragon.getHappiness());
        assertEquals(140, testDragon.getEnergy());
    }

    @Test
    void testImagesLoaded()
    {
        assertNotNull(testDragon.getImages());
        assertEquals(4, testDragon.getImages().length);  // Should have 4 state images
    }
}
