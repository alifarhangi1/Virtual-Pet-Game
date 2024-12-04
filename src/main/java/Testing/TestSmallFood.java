package Testing;

import misc.MediumFood;
import misc.SmallFood;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestSmallFood
{
    @Test
    void testSmallFoodConstructor()
    {
        SmallFood smallFood = new SmallFood();

        // Verify the inherited attributes from Item
        assertEquals("Small Food: A light snack for your pet.", smallFood.getDescription());
        assertEquals(10, smallFood.getFullAmount());
        assertEquals(5, smallFood.getHappyAmount());
        assertEquals(0, smallFood.getType());
        assertEquals("Small Food", smallFood.getName());
        assertEquals(1, smallFood.getAmount());

        // Verify the image is correctly set (resource file should exist)
        assertNotNull(smallFood.getImg(), "Image should be initialized");
    }
}
