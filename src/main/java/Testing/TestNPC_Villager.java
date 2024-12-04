package Testing;

import misc.GamePanel;
import misc.NPC_Villager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestNPC_Villager {

    private NPC_Villager villager;
    private GamePanel gamePanel;

    @BeforeEach
    void setUp() {
        gamePanel = new GamePanel("Maze Madness");
        gamePanel.ui = new misc.MinigameUI(gamePanel);
        villager = new NPC_Villager(gamePanel);
    }

    @Test
    void testConstructor_InitializesAttributes() {
        assertEquals("down", villager.direction, "Villager's initial direction should be 'down'");
        assertEquals(0, villager.speed, "Villager's speed should be initialized to 0");
        assertNotNull(villager.down1, "Villager's sprite should be loaded");
    }

    @Test
    void testSpeak_SetsDialogueInUI() {
        villager.speak();
        assertEquals(villager.dialogues[1], gamePanel.ui.currentDialogue, "Speak method should set the correct dialogue in the UI");
    }
}
