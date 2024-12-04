package Testing;

import data.Database;
import managers.DatabaseManager;
import misc.Player;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TestDatabaseManager
{
    private DatabaseManager databaseManager;

    @BeforeEach
    void setUp()
    {
        // Ensure a fresh instance of DatabaseManager for each test
        databaseManager = new DatabaseManager();
    }

    @AfterEach
    void tearDown()
    {
        // Clean up or reset the state if needed
        databaseManager.close();
    }

    @Test
    void testAddPlayer()
    {
        Player player = new Player();
        player.setUsername("TestUser");

        databaseManager.addPlayer(player);
        Player retrievedPlayer = databaseManager.findPlayer("TestUser");

        assertNotNull(retrievedPlayer, "Player should be added and retrievable");
        assertEquals("TestUser", retrievedPlayer.getUsername(), "Player username should match");
    }

    @Test
    void testRemovePlayer()
    {
        Player player = new Player();
        player.setUsername("TestUser");

        databaseManager.addPlayer(player);
        databaseManager.removePlayer("TestUser");

        Player retrievedPlayer = databaseManager.findPlayer("TestUser");
        assertNull(retrievedPlayer, "Player should be removed and not retrievable");
    }

    @Test
    void testFindPlayer()
    {
        Player player1 = new Player();
        player1.setUsername("PlayerOne");

        Player player2 = new Player();
        player2.setUsername("PlayerTwo");

        databaseManager.addPlayer(player1);
        databaseManager.addPlayer(player2);

        Player retrievedPlayer = databaseManager.findPlayer("PlayerOne");
        assertNotNull(retrievedPlayer, "PlayerOne should exist");
        assertEquals("PlayerOne", retrievedPlayer.getUsername(), "Retrieved username should match");

        assertNull(databaseManager.findPlayer("NonExistentPlayer"), "Non-existent player should return null");
    }

    @Test
    void testGetAllPlayers()
    {
        Player player1 = new Player();
        player1.setUsername("PlayerOne");

        Player player2 = new Player();
        player2.setUsername("PlayerTwo");

        databaseManager.addPlayer(player1);
        databaseManager.addPlayer(player2);

        List<Player> allPlayers = databaseManager.getAllPlayers();
        assertTrue(allPlayers.stream().anyMatch(p -> p.getUsername().equals("PlayerOne")), "PlayerOne should exist");
        assertTrue(allPlayers.stream().anyMatch(p -> p.getUsername().equals("PlayerTwo")), "PlayerTwo should exist");
    }

    @Test
    void testParentalAuthentication()
    {
        databaseManager.updateParentalPassword("securePassword");

        assertTrue(databaseManager.authenticateParental("securePassword"), "Parental authentication should succeed with correct password");
        assertFalse(databaseManager.authenticateParental("wrongPassword"), "Parental authentication should fail with incorrect password");
    }
}
