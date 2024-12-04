package Testing;

import managers.GameManager;

import javax.swing.*;

import misc.Owl;
import misc.Pet;
import misc.Player;
import misc.Wolf;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class TestGameManager
{

    class TestPet extends Pet
    {
        private String name;
        private int fullness = 100;
        private int energy = 100;

        public TestPet(String name)
        {
            super(name, "Test");
            this.name = name;
        }

        @Override
        public String getName() { return name; }

        @Override
        public int getFullness() { return fullness; }

        @Override
        public void setFullness(int value) { this.fullness = value; }

        @Override
        public int getEnergy() { return energy; }

        @Override
        public void setEnergy(int value) { this.energy = value; }
    }

    // Test stub for Player
    class TestPlayer extends Player
    {
        private ArrayList<Pet> petList = new ArrayList<Pet>();  // Override the parent's petList
        private Pet currentPet;

        public TestPlayer()
        {
            super();  // Call parent constructor
        }

        @Override
        public ArrayList<Pet> getPetList()
        {
            return this.petList;  // Return our local petList
        }

        @Override
        public void addPet(Pet pet)
        {
            this.petList.add(pet);
        }

        @Override
        public void setPlayerPet(int index)
        {
            List<Pet> pets = getPetList();  // Get the pet list using the getter
            if (index == 0 && index < petList.size())
            {
                this.currentPet = pets.get(index);
            }
        }

        @Override
        public Pet getPet()
        {
            return this.currentPet;
        }
    }

    private TestPlayer testPlayer;
    private TestPet testPet1;
    private TestPet testPet2;
    private GameManager gameManager;

    @BeforeEach
    void setup()
    {
        // Create test pets with minimal required data
        testPet1 = new TestPet("Fluffy");
        testPet2 = new TestPet("Sparky");

        // Initialize test player
        testPlayer = new TestPlayer();
        testPlayer.addPet(testPet1);
        testPlayer.addPet(testPet2);
        testPlayer.setPlayerPet(0);  // Set first pet as active

        // Initialize GameManager with test player
        gameManager = GameManager.getInstance(testPlayer);
    }

    @Test
    void testSingletonInstance()
    {
        GameManager gm1 = GameManager.getInstance(testPlayer);
        GameManager gm2 = GameManager.getInstance(testPlayer);

        // Singleton instance test
        assertSame(gm1, gm2, "GameManager should be a singleton.");
    }

    @Test
    void testGameManagerInitializationOnly() {
    // Create fresh instances just for this test
        TestPet testPet = new TestPet("Fluffy");
        TestPlayer testPlayer = new TestPlayer();
        testPlayer.addPet(testPet);
        testPlayer.setPlayerPet(0);

        GameManager gameManager = GameManager.getInstance(testPlayer);

        assertNotNull(gameManager);
        assertEquals(testPlayer, gameManager.getPlayer());
        assertEquals(testPet, gameManager.getPlayer().getPet());
    }

    @Test
    void testUpdatePlayTime()
    {
        long initialPlayTime = testPlayer.getPlayTime();

        // Simulate time passage
        try {
            Thread.sleep(1000); // Wait for 1 second to simulate playtime
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        gameManager.updatePlayTime();
        assertTrue(gameManager.getPlayer().getPlayTime() > initialPlayTime, "Playtime should increase over time.");
    }

    @Test
    void testSetPet() {
        // Set a new pet
        gameManager.setPet(1);
        assertEquals(testPet2, gameManager.getPetArr().get(1), "Pet should be set correctly.");
    }

    @Test
    void testCheckPetStatus() {
        // Healthy pet
        gameManager.checkPetStatus();

        // Pet starvation
        testPet1.setFullness(0);
        gameManager.checkPetStatus(); // Should print starving message to the console

        // Pet exhaustion
        testPet1.setEnergy(0);
        gameManager.checkPetStatus(); // Should print exhausted message to the console
    }

    @Test
    void testResizeIcon() {
        ImageIcon icon = new ImageIcon(new byte[0]); // Empty icon for test purposes
        ImageIcon resizedIcon = gameManager.resizeIcon(icon);

        assertNotNull(resizedIcon, "Resize icon should return a non-null ImageIcon.");
    }
}