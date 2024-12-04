package Testing;

import misc.OBJ_EvolutionFruit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestOBJ_EvolutionFruit {

    private OBJ_EvolutionFruit evolutionFruit;

    @BeforeEach
    void setUp() {
        evolutionFruit = new OBJ_EvolutionFruit();
    }

    @Test
    void testConstructor_InitializesAttributes() {
        assertEquals("Evolution Fruit", evolutionFruit.name, "The object's name should be 'Evolution Fruit'");
        assertTrue(evolutionFruit.collision, "The object should have collision enabled");
        assertNotNull(evolutionFruit.image, "The object's image should be loaded");
    }
}
