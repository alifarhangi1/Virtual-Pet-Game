package Testing;

import misc.Tiger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestTiger
{
    private Tiger testTiger;

    @BeforeEach
    void setUp()
    {
        testTiger = new Tiger("TestTiger");
    }

    @Test
    void testInitialStats() {
        assertEquals("TestTiger", testTiger.getName());
        assertEquals("misc.Tiger", testTiger.getType());
        assertEquals(180, testTiger.getMaxHP());
        assertEquals(140, testTiger.getMaxEnergy());
        assertEquals(90, testTiger.getFullness());
        assertEquals(85, testTiger.getHappiness());
        assertEquals(130, testTiger.getEnergy());
        assertEquals(180, testTiger.getHP());
    }

    @Test
    void testImagesLoaded()
    {
        assertNotNull(testTiger.getImages());
        assertEquals(4, testTiger.getImages().length);  // Should have 4 state images
    }
}
