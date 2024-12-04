package Testing;

import misc.LargeFood;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;

class TestLargeFood {

    @Test
    void testLargeFoodConstructor() {
        LargeFood largeFood = new LargeFood();

        // Verify the inherited attributes from Item
        assertEquals("Large Food: A feast for your pet!", largeFood.getDescription());
        assertEquals(30, largeFood.getFullAmount());
        assertEquals(20, largeFood.getHappyAmount());
        assertEquals(0, largeFood.getType());
        assertEquals("Large Food", largeFood.getName());
        assertEquals(1, largeFood.getAmount());

        // Verify the image is correctly set (resource file should exist)
        assertNotNull(largeFood.getImage(), "Image should be initialized");
    }
}
