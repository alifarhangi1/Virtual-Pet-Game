package Testing;
import misc.Owl;
import misc.Wolf;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestWolf
{
    private Wolf testWolf;

    @BeforeEach
    void setUp()
    {
        testWolf = new Wolf("TestWolf");
    }

    @Test
    void testInitialStats() {
        assertEquals("TestWolf", testWolf.getName());
        assertEquals("misc.Wolf", testWolf.getType());
        assertEquals(120, testWolf.getMaxHP());
        assertEquals(110, testWolf.getMaxEnergy());
        assertEquals(80, testWolf.getHunger());
        assertEquals(70, testWolf.getHappiness());
        assertEquals(100, testWolf.getEnergy());
        assertFalse(testWolf.getIsEvolve());
    }

    @Test
    void testEvolution()
    {
        int initialMaxHP = testWolf.getMaxHP();
        int initialMaxEnergy = testWolf.getMaxEnergy();

        testWolf.Evolve();

        assertTrue(testWolf.getIsEvolve());
        assertEquals(initialMaxHP * 2, testWolf.getMaxHP());
        assertEquals(initialMaxEnergy * 2, testWolf.getMaxEnergy());
        assertEquals(testWolf.getMaxHP(), testWolf.getHP());  // Should be fully healed
    }

    @Test
    void testImagesLoaded()
    {
        assertNotNull(testWolf.getImages());
        assertEquals(4, testWolf.getImages().length);  // Should have 4 state images
    }
}
