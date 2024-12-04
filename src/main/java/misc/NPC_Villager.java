package misc;

import javax.imageio.ImageIO;
import java.io.IOException;

/**
 * The misc.NPC_Villager class represents a non-player character (NPC) of type Villager.
 * The Villager remains stationary and has unique dialogue and sprite assets.
 */
public class NPC_Villager extends Entity {

    /**
     * Constructs an misc.NPC_Villager instance and initializes its direction, speed,
     * sprite images, and dialogue.
     *
     * @param gamePanel the misc.GamePanel instance associated with this NPC
     */
    public NPC_Villager(GamePanel gamePanel) {
        super(gamePanel);

        direction = "down";
        speed = 0;

        getNPCImage_Villager();
        setDialogue();
    }

    /**
     * Loads the sprite images for the Villager from the resources directory.
     * Each direction uses the same image in this implementation.
     */
    public void getNPCImage_Villager() {
        try {
            down1 = ImageIO.read(getClass().getResourceAsStream("/visuals/npcVillager.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/visuals/npcVillager.png"));
            up1 = ImageIO.read(getClass().getResourceAsStream("/visuals/npcVillager.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/visuals/npcVillager.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/visuals/npcVillager.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/visuals/npcVillager.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/visuals/npcVillager.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/visuals/npcVillager.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Triggers the dialogue associated with the Villager NPC.
     * Sets the current dialogue in the UI to the Villager's dialogue.
     */
    public void speak() {
        gamePanel.ui.currentDialogue = dialogues[1];
    }
}

