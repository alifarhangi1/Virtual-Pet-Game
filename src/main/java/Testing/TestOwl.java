package Testing;

import misc.Owl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestOwl
{
    private Owl testOwl;

    @BeforeEach
    void setUp() {
        testOwl = new Owl("TestOwl");
    }

    @Test
    void testInitialStats() {
        assertEquals("TestOwl", testOwl.getName());
        assertEquals("misc.Owl", testOwl.getType());
        assertEquals(100, testOwl.getMaxHP());
        assertEquals(120, testOwl.getMaxEnergy());
        assertEquals(90, testOwl.getHunger());
        assertEquals(85, testOwl.getHappiness());
        assertEquals(110, testOwl.getEnergy());
        assertFalse(testOwl.getIsEvolve());
    }

    @Test
    void testEvolution()
    {
        int initialMaxHP = testOwl.getMaxHP();
        int initialMaxEnergy = testOwl.getMaxEnergy();

        testOwl.Evolve();

        assertTrue(testOwl.getIsEvolve());
        assertEquals(initialMaxHP * 2, testOwl.getMaxHP());
        assertEquals(initialMaxEnergy * 2, testOwl.getMaxEnergy());
        assertEquals(testOwl.getMaxHP(), testOwl.getHP());  // Should be fully healed
    }

    @Test
    void testImagesLoaded()
    {
        assertNotNull(testOwl.getImages());
        assertEquals(4, testOwl.getImages().length);  // Should have 4 state images
    }
}