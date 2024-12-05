package Testing;

import managers.GameManager;
import misc.GamePanel;
import misc.NPC_Minotaur;
import misc.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestNPC_Minotaur {

    private NPC_Minotaur minotaur;
    private GamePanel gamePanel;
    private Player player;
    private GameManager manager;

    @BeforeEach
    void setUp() {
        player = new Player();
        manager = GameManager.getInstance(player);
        gamePanel = new GamePanel("Maze Madness");
        gamePanel.ui = new misc.MinigameUI(gamePanel);
        minotaur = new NPC_Minotaur(gamePanel);

    }

    @Test
    void testConstructor_InitializesAttributes() {
        assertEquals("down", minotaur.direction, "Minotaur's initial direction should be 'down'");
        assertEquals(0, minotaur.speed, "Minotaur's speed should be initialized to 0");
        assertNotNull(minotaur.down1, "Minotaur's sprite should be loaded");
    }

    @Test
    void testSpeak_SetsDialogueInUI() {
        minotaur.speak();
        assertEquals(minotaur.dialogues[0], gamePanel.ui.currentDialogue, "Speak method should set the correct dialogue in the UI");
    }
}
