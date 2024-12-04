package Testing;

import misc.Panda;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestPanda
{
    private Panda testPanda;

    @BeforeEach
    void setUp()
    {
        testPanda = new Panda("TestPanda");
    }

    @Test
    void testInitialStats()
    {
        assertEquals("TestPanda", testPanda.getName());
        assertEquals("misc.Panda", testPanda.getType());
        assertEquals(110, testPanda.getMaxHP());
        assertEquals(90, testPanda.getMaxEnergy());
        assertEquals(95, testPanda.getHunger());
        assertEquals(90, testPanda.getHappiness());
        assertEquals(80, testPanda.getEnergy());
        assertFalse(testPanda.getIsEvolve());
    }

    @Test
    void testEvolution()
    {
        int initialMaxHP = testPanda.getMaxHP();
        int initialMaxEnergy = testPanda.getMaxEnergy();

        testPanda.Evolve();

        assertTrue(testPanda.getIsEvolve());
        assertEquals(initialMaxHP * 2, testPanda.getMaxHP());
        assertEquals(initialMaxEnergy * 2, testPanda.getMaxEnergy());
        assertEquals(testPanda.getMaxHP(), testPanda.getHP());  // Should be fully healed
    }

    @Test
    void testImagesLoaded()
    {
        assertNotNull(testPanda.getImages());
        assertEquals(4, testPanda.getImages().length);  // Should have 4 state images
    }
}
