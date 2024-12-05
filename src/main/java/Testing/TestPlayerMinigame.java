package Testing;

import managers.GameManager;
import misc.GamePanel;
import misc.KeyHandler;
import misc.Player;
import misc.PlayerMinigame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestPlayerMinigame {

    private PlayerMinigame player;
    private GamePanel gamePanel;
    private KeyHandler keyHandler;
    private GameManager gameManager;
    private Player player1;

    @BeforeEach
    void setUp() {
        player1 = new Player();
        gameManager = GameManager.getInstance(player1);
        gamePanel = new GamePanel("Kung Fu Chaos");
        keyHandler = new KeyHandler(gamePanel);
        player = new PlayerMinigame(gamePanel, keyHandler);
    }

    @Test
    void testConstructor_InitializesAttributes() {
        assertEquals(3, player.speed, "Player's speed should be initialized to 3");
        assertEquals("down", player.direction, "Player's initial direction should be 'down'");
        assertNotNull(player.up1, "Player's sprite images should be loaded");
    }

    @Test
    void testSetDefaultValues_ResetsPlayerPositionAndDirection() {
        player.setDefaultValues();
        assertEquals(25 * gamePanel.tileSize - (gamePanel.tileSize / 2), player.worldX, "Player's X position should be reset correctly");
        assertEquals(43 * gamePanel.tileSize - (gamePanel.tileSize / 2), player.worldY, "Player's Y position should be reset correctly");
        assertEquals("down", player.direction, "Player's direction should be reset to 'down'");
    }

    @Test
    void testInteractWithObject_CollectsEvolutionFruit() {
        gamePanel.obj[0] = new misc.OBJ_EvolutionFruit();
        gamePanel.obj[0].worldX = player.worldX;
        gamePanel.obj[0].worldY = player.worldY;

        player.interactWithObject(0);

        assertNull(gamePanel.obj[0], "The collected object should be removed from the game");
        assertTrue(player.mazeMadnessWin, "Maze Madness win flag should be set to true");
    }

    @Test
    void testInteractNPC_AttacksEnemyNPC() {
        misc.NPC_Samurai samurai = new misc.NPC_Samurai(gamePanel);
        samurai.worldX = player.worldX;
        samurai.worldY = player.worldY;
        samurai.attackable = true;
        gamePanel.npc[0] = samurai;

        player.enemiesLeft = 1;
        player.interactNPC(0);

        assertNull(gamePanel.npc[0], "The attacked NPC should be removed from the game");
        assertEquals(0, player.enemiesLeft, "The enemies left counter should decrease after an attack");
        assertTrue(player.kungfuChaosWin, "Kung Fu Chaos win flag should be set to true when no enemies are left");
    }

    @Test
    void testPlayerWin_SetsGameFinishState() {
        player.playerWin();
        assertTrue(gamePanel.ui.gameFinished, "Game should be marked as finished after a win");
    }
}
