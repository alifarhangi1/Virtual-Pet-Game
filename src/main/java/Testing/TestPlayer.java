package Testing;

import misc.Item;
import misc.MediumGift;
import misc.Pet;
import misc.Player;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TestPlayer {

    // Test stub for Pet
    class TestPet extends Pet
    {
        private String name;
        private String Type;
        private int hunger = 50;
        private int happiness = 50;
        private int maxHP = 100;
        private int energy = 100;
        private int maxEnergy = 100;

        public TestPet(String name, String Type) {
            super(name, Type);
            this.name = name;
            this.Type = Type;
        }

        @Override
        public int getHunger() { return hunger; }
        @Override
        public void setFullness(int value) { this.hunger = value; }
        @Override
        public int getHappiness() { return happiness; }
        @Override
        public void setHappiness(int value) { this.happiness = value; }
        @Override
        public int getMaxHP() { return maxHP; }
        @Override
        public void setMaxHP(int value) { this.maxHP = value; }
        @Override
        public int getEnergy() { return energy; }
        @Override
        public void setEnergy(int value) { this.energy = value; }
    }

    // Test stub for Item
    class TestItem extends Item
    {
        private String name;
        private int fullAmount;
        private int happyAmount;
        private int maxHpControl;
        private int amount;

        public TestItem(String name)
        {
            super("This is a test Item");
            this.name = name;
        }

        public void setupTestValues(int full, int happy, int maxHp, int amt)
        {
            setFullAmount(full);     // Parent's setter
            setHappyAmount(happy);   // Parent's setter
            setMaxHpControl(maxHp);  // Parent's setter
            setAmount(amt);          // Parent's setter
        }
    }

    @Test
    void testPlayerInitialization() {
        // Arrange
        TestPet pet1 = new TestPet("Buddy", "Dog");
        TestPet pet2 = new TestPet("Whiskers", "Cat");
        Player player = new Player("testUser", "testPassword".toCharArray());
        player.addPet(pet1);
        player.addPet(pet2);

        // Assert
        assertAll(
                "Player initialization",
                () -> assertEquals("testUser", player.getUsername(), "Username mismatch"),
                () -> assertEquals("testPassword", player.getPasswordString(), "Password mismatch"),
                () -> assertEquals(0, player.getScore(), "Initial score should be 0"),
                () -> assertEquals(2, player.getPetList().size(), "Pet list size mismatch"),
                () -> assertNotNull(player.getInventory(), "Inventory should not be null"),
                () -> assertNull(player.getPet(), "Current pet should be null")
        );
    }

    @Test
    void testGiveGiftToPet()
    {
        // Arrange
        TestPet pet = new TestPet("Buddy", "Dog");
        TestItem item = new TestItem("Bone");
        item.setupTestValues(30, 20, 10, 1);  // Use our new helper method

        Player player = new Player();
        player.addItem(item);

        // Act
        player.giveGift(pet, item);

        // Assert
        assertAll(
                "Gift effects on pet",
                () -> assertEquals(80, pet.getHunger(), "Fullness should increase by 30"),
                () -> assertEquals(70, pet.getHappiness(), "Happiness should increase by 20"),
                () -> assertEquals(110, pet.getMaxHP(), "MaxHP should increase by 10")
        );
    }

    @Test
    void testPetInteraction()
    {
        // Arrange
        Pet pet = new Pet("Buddy", "Dog");
        Player player = new Player();

        // Act
        player.exercisePet(pet);

        // Assert
        assertAll(
                "Exercise effects on pet",
                () -> assertEquals(105, pet.getMaxHP(), "Max HP should increase by 5"),
                () -> assertEquals(110, pet.getMaxEnergy(), "Max Energy should increase by 10"),
                () -> assertEquals(85, pet.getEnergy(), "Energy should decrease by 15")
        );
    }

    @Test
    void testSetPlayerPet()
    {
        // Arrange
        Pet pet1 = new Pet("Buddy", "Dog");
        Pet pet2 = new Pet("Whiskers", "Cat");
        Player player = new Player("testUser", "password".toCharArray());
        player.addPet(pet1);
        player.addPet(pet2);

        // Act
        player.setPlayerPet(1);

        // Assert
        assertEquals(pet2, player.getPet(), "Selected pet mismatch");
    }

    @Test
    void testInventoryManagement()
    {
        // Arrange
        Player player = new Player();
        Pet pet = new Pet("Whiskers", "Cat");
        pet.setHappiness(50);
        pet.setFullness(50);
        pet.setMaxHP(100);
        player.setPet(pet);
        Item item = new MediumGift();
        item.setName("Potion");
        item.setAmount(5);
        player.addItem(item);


        // Act
        player.giveGift(player.getPet(), item);

        // Assert
        assertTrue(pet.getHunger() == 50);
        assertTrue(pet.getHappiness()  == 70);
        assertTrue(pet.getMaxHP()  == 110);
    }

    @Test
    void testToString() {
        // Arrange
        Player player = new Player("testUser", "password".toCharArray());
        player.setScore(50);

        // Act
        String result = player.toString();

        // Assert
        assertTrue(result.contains("username='testUser'"), "ToString should include username");
        assertTrue(result.contains("score=50"), "ToString should include score");
    }
}