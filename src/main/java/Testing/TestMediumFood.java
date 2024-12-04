package Testing;

import misc.LargeFood;
import misc.MediumFood;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestMediumFood
{
    @Test
    void testMediumFoodConstructor()
    {
        MediumFood mediumFood = new MediumFood();

        // Verify the inherited attributes from Item
        assertEquals("Medium Food: A decent meal for your pet.", mediumFood.getDescription());
        assertEquals(20, mediumFood.getFullAmount());
        assertEquals(10, mediumFood.getHappyAmount());
        assertEquals(0, mediumFood.getType());
        assertEquals("Medium Food", mediumFood.getName());
        assertEquals(1, mediumFood.getAmount());

        // Verify the image is correctly set (resource file should exist)
        assertNotNull(mediumFood.getImage(), "Image should be initialized");
    }
}
