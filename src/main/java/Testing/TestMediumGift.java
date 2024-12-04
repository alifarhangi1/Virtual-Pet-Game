package Testing;

import misc.LargeGift;
import misc.MediumGift;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestMediumGift
{
    @Test
    void testMediumGiftConstructor()
    {
        MediumGift meidumGift = new MediumGift();

        // Verify the inherited attributes from Item
        assertEquals("Medium Gift: A thoughtful gift for your pet.", meidumGift.getDescription());
        assertEquals(20, meidumGift.getHappyAmount());
        assertEquals(10, meidumGift.getMaxHpControl());
        assertEquals(1, meidumGift.getType());
        assertEquals("Medium Gift", meidumGift.getName());
        assertEquals(1, meidumGift.getAmount());

        // Verify the image is correctly set (resource file should exist)
        assertNotNull(meidumGift.getImage(), "Image should be initialized");
    }
}
