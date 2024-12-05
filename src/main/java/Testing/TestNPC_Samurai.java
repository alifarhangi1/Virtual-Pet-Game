package Testing;

import managers.GameManager;
import misc.GamePanel;
import misc.NPC_Samurai;
import misc.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestNPC_Samurai {

    private NPC_Samurai samurai;
    private GamePanel gamePanel;
    private Player player;
    private GameManager manager;

    @BeforeEach
    void setUp() {
        player = new Player();
        manager = GameManager.getInstance(player);
        gamePanel = new GamePanel("Kung Fu Chaos");
        samurai = new NPC_Samurai(gamePanel);
    }

    @Test
    void testConstructor_InitializesAttributes() {
        assertEquals("down", samurai.direction, "Samurai's initial direction should be 'down'");
        assertEquals(3, samurai.speed, "Samurai's speed should be initialized to 3");
        assertNotNull(samurai.down1, "Samurai's sprite should be loaded");
    }

    @Test
    void testSetAction_ChangesDirectionRandomly() {
        samurai.actionLockCounter = 120;
        samurai.setAction();

        assertNotNull(samurai.direction, "Samurai's direction should be set during action");
        assertTrue(samurai.actionLockCounter == 0, "Action lock counter should reset after setting action");
    }
}
