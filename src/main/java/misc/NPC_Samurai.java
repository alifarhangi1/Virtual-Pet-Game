package misc;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Random;

/**
 * The misc.NPC_Samurai class represents a non-player character (NPC) of type Samurai.
 * The Samurai moves randomly in the game world and has unique sprite animations.
 */
public class NPC_Samurai extends Entity {

    /**
     * Constructs an misc.NPC_Samurai instance and initializes its direction, speed,
     * and sprite images.
     *
     * @param gamePanel the misc.GamePanel instance associated with this NPC
     */
    public NPC_Samurai(GamePanel gamePanel) {
        super(gamePanel);

        direction = "down";
        speed = 3;

        getNPCImage_Samurai();
    }

    /**
     * Loads the sprite images for the Samurai from the resources directory.
     * Each direction has two frames for animation.
     */
    public void getNPCImage_Samurai() {
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("visuals/right_samurai_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("visuals/right_samurai_2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("visuals/left_samurai_1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("visuals/left_samurai_2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("visuals/left_samurai_1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("visuals/left_samurai_2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("visuals/right_samurai_1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("visuals/right_samurai_2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets the action for the Samurai. The Samurai randomly chooses a direction
     * to move every 120 game ticks.
     */
    public void setAction() {
        actionLockCounter++;

        if (actionLockCounter == 120) {
            Random random = new Random();
            int i = random.nextInt(100) + 1; // Pick a number from 1 to 100

            if (i <= 25) {
                direction = "up";
            } else if (i > 25 && i <= 50) {
                direction = "down";
            } else if (i > 50 && i <= 75) {
                direction = "left";
            } else {
                direction = "right";
            }

            actionLockCounter = 0;
        }
    }
}
