package Testing;

import managers.GameManager;

import javax.swing.*;

import misc.Owl;
import misc.Pet;
import misc.Player;
import misc.Wolf;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        public int getHunger() { return fullness; }

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
        private List<Pet> petList;  // Override the parent's petList
        private Pet currentPet;

        public TestPlayer() {
            super();  // Call parent constructor
        }

        @Override
        public List<Pet> getPetList() {
            return this.petList;  // Return our local petList
        }

        @Override
        public void addPet(Pet pet) {
            super.addPet(pet);
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
        //testPlayer = (TestPlayer) gameManager.getPlayer();
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
    void testGameManagerInitialization() {
        // Test initialization
        assertNotNull(gameManager, "GameManager should not be null after initialization.");
        assertEquals(testPlayer, gameManager.getPlayer(), "GameManager should initialize with the given player.");
        assertEquals(testPet1, gameManager.getPlayer().getPet(), "Active pet should match player's active pet.");
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
        assertEquals(testPet2, gameManager.getPetArr()[1], "Pet should be set correctly.");
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