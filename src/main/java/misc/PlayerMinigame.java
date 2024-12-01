package misc;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * The misc.PlayerMinigame class represents the player-controlled character in the game.
 * It manages movement, interactions with objects and NPCs, and player-specific logic
 * such as level-specific mechanics and winning conditions.
 */
public class PlayerMinigame extends Entity {

    /** The key handler for detecting player input. */
    KeyHandler keyHandler;

    /** The X-coordinate of the player's position on the screen. */
    public final int screenX;

    /** The Y-coordinate of the player's position on the screen. */
    public final int screenY;

    /** The number of enemies left in the level. */
    public int enemiesLeft;

    /** Flags indicating whether the player has won in each level. */
    public boolean mazeMadnessWin;
    public boolean kungfuChaosWin;
    public boolean dragonDuelWin;

    /**
     * Constructs a misc.PlayerMinigame instance and initializes its attributes and sprites.
     *
     * @param gamePanel the misc.GamePanel instance associated with the player
     * @param keyHandler the misc.KeyHandler instance for player input
     */
    public PlayerMinigame(GamePanel gamePanel, KeyHandler keyHandler) {
        super(gamePanel);
        this.keyHandler = keyHandler;

        screenX = gamePanel.screenWidth / 2;
        screenY = gamePanel.screenHeight / 2;

        // Create solid area for collision detection
        solidArea = new Rectangle(8, 16, gamePanel.tileSize - 8, gamePanel.tileSize - 8);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        setDefaultValues();
        getPlayerImage();
    }

    /**
     * Sets the default position, speed, and direction of the player.
     */
    public void setDefaultValues() {
        worldX = gamePanel.tileSize * 25 - (gamePanel.tileSize / 2);
        worldY = gamePanel.tileSize * 43 - (gamePanel.tileSize / 2);
        speed = 3;
        direction = "down";
    }

    /**
     * Loads the sprite images for the player character from the resources directory.
     */
    public void getPlayerImage() {
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("visuals/monkey_up1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("visuals/monkey_up2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("visuals/monkey_down1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("visuals/monkey_down2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("visuals/monkey_left.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("visuals/monkey_left3.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("visuals/monkey_right.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("visuals/monkey_right3.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates the player's position, interactions, and animations based on input and game state.
     */
    public void update() {
        if (keyHandler.upPressed || keyHandler.downPressed || keyHandler.leftPressed || keyHandler.rightPressed) {
            if (keyHandler.upPressed) direction = "up";
            if (keyHandler.downPressed) direction = "down";
            if (keyHandler.leftPressed) direction = "left";
            if (keyHandler.rightPressed) direction = "right";

            // Check for collisions
            collisionOn = false;
            gamePanel.collisionChecker.checkTile(this);
            int objIndex = gamePanel.collisionChecker.checkObject(this, true);
            interactWithObject(objIndex);
            int npcIndex = gamePanel.collisionChecker.checkEntity(this, gamePanel.npc);
            interactNPC(npcIndex);

            // Move the player if no collision occurred
            if (!collisionOn) {
                switch (direction) {
                    case "up" -> worldY -= speed;
                    case "down" -> worldY += speed;
                    case "left" -> worldX -= speed;
                    case "right" -> worldX += speed;
                }
            }

            // Handle sprite animation
            spriteCounter++;
            if (spriteCounter > 10) {
                spriteNum = (spriteNum == 1) ? 2 : 1;
                spriteCounter = 0;
            }
        }
    }

    /**
     * Interacts with an object in the game world when the player collides with it.
     *
     * @param i the index of the object in the game world
     */
    public void interactWithObject(int i) {
        if (i != 999) {
            String objectName = gamePanel.obj[i].name;
            if ("Evolution Fruit".equals(objectName)) {
                gamePanel.obj[i] = null;
                gamePanel.musicPlayer.playSound("/audio/evoFruit.wav");
                playerWin();
                mazeMadnessWin = true;
            }
        }
    }

    /**
     * Interacts with an NPC when the player collides with it.
     *
     * @param i the index of the NPC in the game world
     */
    public void interactNPC(int i) {
        if (i != 999) {
            if ("Kung Fu Chaos".equals(gamePanel.levelName)) {
                if (gamePanel.npc[i].attackable) {
                    // Handle attack logic
                    try {
                        up1 = ImageIO.read(getClass().getResourceAsStream("visuals/Monkey(11).png"));
                        up2 = ImageIO.read(getClass().getResourceAsStream("visuals/Monkey(11).png"));
                        down1 = ImageIO.read(getClass().getResourceAsStream("visuals/Monkey(10).png"));
                        down2 = ImageIO.read(getClass().getResourceAsStream("visuals/Monkey(10).png"));
                        left1 = ImageIO.read(getClass().getResourceAsStream("visuals/Monkey(6).png"));
                        left2 = ImageIO.read(getClass().getResourceAsStream("visuals/Monkey(6).png"));
                        right1 = ImageIO.read(getClass().getResourceAsStream("visuals/Monkey(7).png"));
                        right2 = ImageIO.read(getClass().getResourceAsStream("visuals/Monkey(7).png"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    enemiesLeft--;
                    gamePanel.musicPlayer.playSound("/audio/punch.wav");
                    gamePanel.npc[i] = null;

                    // Reset player sprites after a delay
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            getPlayerImage();
                        }
                    }, 500);

                    // Check for win condition
                    if (enemiesLeft == 0) {
                        playerWin();
                        kungfuChaosWin = true;
                    }
                } else {
                    gamePanel.dialogueState = true;
                    gamePanel.npc[i].speak();
                }
            } else if ("Maze Madness".equals(gamePanel.levelName)) {
                gamePanel.dialogueState = true;
                gamePanel.musicPlayer.playSound("audio/minotaurroar.wav");
                gamePanel.npc[i].speak();
            }
        }
    }

    /**
     * Draws the player character on the screen.
     *
     * @param g2D the Graphics2D object used for rendering
     */
    public void draw(Graphics2D g2D) {
        BufferedImage image = switch (direction) {
            case "up" -> (spriteNum == 1) ? up1 : up2;
            case "down" -> (spriteNum == 1) ? down1 : down2;
            case "left" -> (spriteNum == 1) ? left1 : left2;
            case "right" -> (spriteNum == 1) ? right1 : right2;
            default -> null;
        };
        g2D.drawImage(image, screenX, screenY, gamePanel.tileSize, gamePanel.tileSize, null);
    }

    /**
     * Handles the logic when the player wins a level.
     */
    public void playerWin() {
        System.out.println("Win!");
        gamePanel.musicPlayer.stopMusic();
        gamePanel.musicPlayer.playSound("/audio/levelup.wav");
        gamePanel.ui.gameFinished = true;
    }
}


