package misc;

import javax.imageio.ImageIO;
import java.io.IOException;

/**
 * The misc.NPC_Minotaur class represents a non-player character (NPC) of type Minotaur.
 * It includes unique dialogue and image assets specific to the Minotaur.
 */
public class NPC_Minotaur extends Entity {

    /**
     * Constructs an misc.NPC_Minotaur instance and initializes its direction, speed,
     * sprite images, and dialogue.
     *
     * @param gamePanel the misc.GamePanel instance associated with this NPC
     */
    public NPC_Minotaur(GamePanel gamePanel) {
        super(gamePanel);

        direction = "down";
        speed = 0;

        getNPCImage_Minotaur();
        setDialogue();
    }

    /**
     * Loads the sprite images for the Minotaur from the resources directory.
     * Each direction uses the same image in this implementation.
     */
    public void getNPCImage_Minotaur() {
        try {
            down1 = ImageIO.read(getClass().getResourceAsStream("visuals/minotaur1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("visuals/minotaur1.png"));
            up1 = ImageIO.read(getClass().getResourceAsStream("visuals/minotaur1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("visuals/minotaur1.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("visuals/minotaur1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("visuals/minotaur1.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("visuals/minotaur1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("visuals/minotaur1.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Triggers the dialogue associated with the Minotaur NPC.
     * Sets the current dialogue in the UI to the first line of the Minotaur's dialogue.
     */
    public void speak() {
        gamePanel.ui.currentDialogue = dialogues[0];
    }
}


