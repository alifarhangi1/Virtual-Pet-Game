package Testing;

import managers.GameManager;
import misc.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestAssetSetter {

    private Player player;
    private GameManager manager;
    private AssetSetter assetSetter;
    private GamePanel gamePanel;

    @BeforeEach
    void setUp() {
        player = new Player();
        manager = GameManager.getInstance(player);
        gamePanel = new GamePanel("Maze Madness");
        gamePanel.obj = new misc.SuperObject[1];
        gamePanel.npc = new misc.Entity[11];
        assetSetter = new AssetSetter(gamePanel, "Maze Madness");
    }

    @Test
    void testSetObject_MazeMadness() {
        assetSetter.setObject();
        assertNotNull(gamePanel.obj[0], "Object should be set for Maze Madness level");
        assertInstanceOf(OBJ_EvolutionFruit.class, gamePanel.obj[0], "The object should be an instance of OBJ_EvolutionFruit");
        assertEquals(19 * gamePanel.tileSize, gamePanel.obj[0].worldX, "Object X position should match the expected value");
        assertEquals(10 * gamePanel.tileSize, gamePanel.obj[0].worldY, "Object Y position should match the expected value");
    }

    @Test
    void testSetNPC_KungFuChaos() {
        assetSetter = new AssetSetter(gamePanel, "Kung Fu Chaos");
        assetSetter.setNPC();

        assertNotNull(gamePanel.npc[0], "First NPC should be initialized for Kung Fu Chaos");
        assertInstanceOf(NPC_Samurai.class, gamePanel.npc[0], "The NPC should be an instance of NPC_Samurai");
        assertEquals(20 * gamePanel.tileSize, gamePanel.npc[0].worldX, "NPC X position should match the expected value");
        assertEquals(10 * gamePanel.tileSize, gamePanel.npc[0].worldY, "NPC Y position should match the expected value");
        assertTrue(gamePanel.npc[0].attackable, "NPC in Kung Fu Chaos should be attackable");

        assertNotNull(gamePanel.npc[10], "Last NPC should be initialized as a villager");
        assertInstanceOf(NPC_Villager.class, gamePanel.npc[10], "The last NPC should be an instance of NPC_Villager");
    }

    @Test
    void testSetNPC_MazeMadness()
    {
        assetSetter.setNPC();
        assertNotNull(gamePanel.npc[0], "NPC should be initialized for Maze Madness");
        assertInstanceOf(NPC_Minotaur.class, gamePanel.npc[0], "The NPC should be an instance of NPC_Minotaur");
        assertEquals(25 * gamePanel.tileSize, gamePanel.npc[0].worldX, "NPC X position should match the expected value");
        assertEquals(38 * gamePanel.tileSize, gamePanel.npc[0].worldY, "NPC Y position should match the expected value");
    }
}
