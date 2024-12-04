package Testing;

import misc.Item;
import misc.MediumFood;
import misc.Pet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestPet
{
    private Pet testPet;

    @BeforeEach
    void setUp()
    {
        testPet = new Pet("TestPet", "misc.Wolf");
    }

    @Test
    void testInitialStats()
    {
        assertEquals("TestPet", testPet.getName());
        assertEquals("misc.Wolf", testPet.getType());
        assertEquals(100, testPet.getHP());
        assertEquals(100, testPet.getFullness());
        assertEquals(100, testPet.getHappiness());
        assertEquals(100, testPet.getEnergy());
    }

    @Test
    void testPlayDecreasesEnergyAndHunger()
    {
        testPet.play();
        assertEquals(85, testPet.getEnergy());
        assertEquals(95, testPet.getFullness());
        assertEquals(100, testPet.getHappiness());
    }

    @Test
    void testPlayDecreasesEnergyAndHungerBoundary()
    {
        testPet.setEnergy(0);
        testPet.setFullness(0);
        testPet.setHappiness(100);
        testPet.play();
        assertEquals(0, testPet.getEnergy());
        assertEquals(0, testPet.getFullness());
        assertEquals(100, testPet.getHappiness());
    }

    @Test
    void testFeedIncreasesStats()
    {
        Item testFood = new Item("TestFood");
        testFood.setFullAmount(1);
        testFood.setHappyAmount(1);
        testPet.setHappiness(50);
        testPet.setFullness(50);
        testPet.setMaxHP(100);
        testPet.giveGift(testFood.getFullAmount(), testFood.getHappyAmount(), testFood.getMaxHpControl());
        assertTrue(testPet.getFullness() == 51);
        assertTrue(testPet.getHappiness()  == 51);
        assertTrue(testPet.getMaxHP()  == 100);
    }

    @Test
    void testFeedIncreasesStatsAtMax()
    {
        Item testFood = new Item("TestFood");
        testFood.setFullAmount(1);
        testFood.setHappyAmount(1);
        testPet.setHappiness(100);
        testPet.setFullness(100);
        testPet.setMaxHP(100);
        testPet.giveGift(testFood.getFullAmount(), testFood.getHappyAmount(), testFood.getMaxHpControl());
        assertTrue(testPet.getFullness() == 100);
        assertTrue(testPet.getHappiness()  == 100);
        assertTrue(testPet.getMaxHP()  == 100);
    }

    @Test
    void testGiftIncreasesStats()
    {
        Item testFood = new Item("TestGift");
        testFood.setHappyAmount(1);
        testFood.setMaxHpControl(1);
        testPet.setHappiness(50);
        testPet.setFullness(50);
        testPet.setMaxHP(100);
        testPet.giveGift(testFood.getFullAmount(), testFood.getHappyAmount(), testFood.getMaxHpControl());
        assertTrue(testPet.getFullness() == 50);
        assertTrue(testPet.getHappiness()  == 51);
        assertTrue(testPet.getMaxHP()  == 101);
    }

    @Test
    void testUpdateDecreasesStats()
    {
        testPet.update();
        assertEquals(98, testPet.getFullness());
        assertEquals(99, testPet.getHappiness());
        assertEquals(99, testPet.getEnergy());
    }

    @Test
    void testHPLossWhenStarving()
    {
        testPet.setFullness(10);  // Critical hunger
        testPet.update();
        assertEquals(95, testPet.getHP());  // Should lose 5 HP
    }

    @Test
    void testVet()
    {
        testPet.setHP(50);
        testPet.setHappiness(100);
        testPet.setFullness(50);
        testPet.setEnergy(50);
        testPet.vet();
        assertEquals(60, testPet.getHP());        // HP should increase by 10
        assertEquals(80, testPet.getHappiness()); // Happiness decreases by 20
        assertEquals(60, testPet.getFullness());  // Fullness increases by 10
        assertEquals(65, testPet.getEnergy());    // Energy increases by 15
    }

    @Test
    void testVetMax()
    {
        testPet.setHP(95);
        testPet.setFullness(95);
        testPet.setEnergy(95);
        testPet.vet();
        assertEquals(100, testPet.getHP());       // Should not exceed 100
        assertEquals(100, testPet.getFullness()); // Should not exceed 100
        assertEquals(100, testPet.getEnergy());   // Should not exceed 100
    }

    @Test
    void testExercise()
    {
        int initialMaxHP = testPet.getMaxHP();
        int initialMaxEnergy = testPet.getMaxEnergy();
        testPet.setEnergy(100);
        testPet.exercise();
        assertEquals(initialMaxHP + 5, testPet.getMaxHP());       // MaxHP increases by 5
        assertEquals(initialMaxEnergy + 10, testPet.getMaxEnergy()); // MaxEnergy increases by 10
        assertEquals(85, testPet.getEnergy());    // Energy decreases by 15
    }

    @Test
    void testExerciseMax()
    {
        testPet.setEnergy(10);
        testPet.exercise();
        assertEquals(0, testPet.getEnergy());     // Energy shouldn't go below 0
    }

    @Test
    void testSleep()
    {
        testPet.setEnergy(50);
        testPet.setFullness(100);
        testPet.sleep();
        assertEquals(70, testPet.getEnergy());    // Energy increases by 20
        assertEquals(90, testPet.getFullness());  // Fullness decreases by 10
    }

    @Test
    void testSleepMax()
    {
        testPet.setMaxEnergy(100);
        testPet.setEnergy(90);
        testPet.sleep();
        assertEquals(100, testPet.getEnergy());   // Should not exceed maxEnergy
    }
}