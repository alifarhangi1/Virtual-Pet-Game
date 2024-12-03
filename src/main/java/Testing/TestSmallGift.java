package Testing;

import misc.MediumGift;
import misc.SmallGift;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestSmallGift
{
    @Test
    void testSmallGiftConstructor()
    {
        SmallGift smallGift = new SmallGift();

        // Verify the inherited attributes from Item
        assertEquals("Small Gift: A modest present to cheer up your pet.", smallGift.getDescription());
        assertEquals(10, smallGift.getHappyAmount());
        assertEquals(5, smallGift.getMaxHpControl());
        assertEquals(1, smallGift.getType());
        assertEquals("Small Gift", smallGift.getName());
        assertEquals(1, smallGift.getAmount());

        // Verify the image is correctly set (resource file should exist)
        assertNotNull(smallGift.getImg(), "Image should be initialized");
    }
}
