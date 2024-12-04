package Testing;

import misc.LargeGift;
import misc.SmallFood;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestLargeGift
{
    @Test
    void testLargeGiftConstructor()
    {
        LargeGift largeGift = new LargeGift();

        // Verify the inherited attributes from Item
        assertEquals("Large Gift: A grand present to delight your pet!", largeGift.getDescription());
        assertEquals(30, largeGift.getHappyAmount());
        assertEquals(15, largeGift.getMaxHpControl());
        assertEquals(1, largeGift.getType());
        assertEquals("Large Gift", largeGift.getName());
        assertEquals(1, largeGift.getAmount());

        // Verify the image is correctly set (resource file should exist)
        assertNotNull(largeGift.getImg(), "Image should be initialized");
    }
}
