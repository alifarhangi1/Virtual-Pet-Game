package Testing;

import misc.Minotaur;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestMinotaur
{
    private Minotaur testMinotaur;

    @BeforeEach
    void setUp()
    {
        testMinotaur = new Minotaur("TestMinotaur");
    }

    @Test
    void testInitialStats() {
        assertEquals("TestMinotaur", testMinotaur.getName());
        assertEquals("misc.Minotaur", testMinotaur.getType());
        assertEquals(220, testMinotaur.getMaxHP());
        assertEquals(130, testMinotaur.getMaxEnergy());
        assertEquals(85, testMinotaur.getHunger());
        assertEquals(80, testMinotaur.getHappiness());
        assertEquals(120, testMinotaur.getEnergy());
    }

    @Test
    void testImagesLoaded()
    {
        assertNotNull(testMinotaur.getImages());
        assertEquals(4, testMinotaur.getImages().length);  // Should have 4 state images
    }
}