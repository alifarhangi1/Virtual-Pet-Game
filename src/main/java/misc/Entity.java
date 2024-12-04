package misc;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * The misc.Entity class serves as a parent class for all characters in the game, including
 * the player, NPCs, and other interactable characters.
 */
public class Entity {

    /** The game panel associated with this entity. */
    GamePanel gamePanel;

    /** The world coordinates of the entity. */
    public int worldX, worldY;

    /** The movement speed of the entity. */
    public int speed;

    /** Buffered images for different sprite animations of the entity. */
    public BufferedImage up1, up2, down1, down2, right1, right2, left1, left2;

    /** The current direction the entity is facing. */
    public String direction;

    /** Counter for managing sprite animation. */
    public int spriteCounter = 0;

    /** Indicates the current sprite number being displayed. */
    public int spriteNum = 1;

    /** The solid area of the entity used for collision detection. */
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);

    /** Default X and Y coordinates of the solid area. */
    public int solidAreaDefaultX, solidAreaDefaultY;

    /** Flag to indicate if the entity is currently colliding with something. */
    public boolean collisionOn;

    /** Counter to lock actions for a period of time. */
    public int actionLockCounter = 0;

    /** Array to store dialogues for NPC interactions. */
    public String dialogues[] = new String[10];

    /** Flag to indicate if the entity is attackable. */
    public boolean attackable;

    /**
     * Constructs an misc.Entity instance with the specified game panel.
     *
     * @param gamePanel the game panel associated with this entity
     */
    public Entity(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    /**
     * Sets the action of the entity. To be overridden by subclasses.
     */
    public void setAction() {
        // Intentionally left empty for subclass implementation
    }

    /**
     * Triggers dialogue or interaction behavior for the entity.
     * To be overridden by subclasses.
     */
    public void speak() {
        // Intentionally left empty for subclass implementation
    }

    /**
     * Updates the entity's state, including movement, collision detection,
     * and sprite animation.
     */
    public void update() {
        setAction(); // Call the subclass setAction method

        collisionOn = false;
        gamePanel.collisionChecker.checkTile(this); // Check tile collisions
        gamePanel.collisionChecker.checkObject(this, false); // Check object collisions
        gamePanel.collisionChecker.checkPlayer(this); // Check player collisions

        // If no collision occurs, update the entity's position
        if (!collisionOn) {
            switch (direction) {
                case "up":
                    worldY -= speed;
                    break;
                case "down":
                    worldY += speed;
                    break;
                case "left":
                    worldX -= speed;
                    break;
                case "right":
                    worldX += speed;
                    break;
            }
        }

        // Handle sprite animation
        spriteCounter++;
        if (spriteCounter > 10) {
            spriteNum = (spriteNum == 1) ? 2 : 1;
            spriteCounter = 0;
        }
    }

    /**
     * Draws the entity on the screen using the Graphics2D object.
     *
     * @param g2D the Graphics2D object used to render the entity
     */
    public void draw(Graphics2D g2D) {
        BufferedImage image = null;
        int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
        int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;

        // Efficient rendering: only draw the entity if it is within the visible screen bounds
        if (worldX + gamePanel.tileSize > gamePanel.player.worldX - gamePanel.player.screenX &&
                worldX - gamePanel.tileSize < gamePanel.player.worldX + gamePanel.player.screenX &&
                worldY + gamePanel.tileSize > gamePanel.player.worldY - gamePanel.player.screenY &&
                worldY - gamePanel.tileSize < gamePanel.player.worldY + gamePanel.player.screenY) {

            switch (direction) {
                case "up":
                    image = (spriteNum == 1) ? up1 : up2;
                    break;
                case "down":
                    image = (spriteNum == 1) ? down1 : down2;
                    break;
                case "left":
                    image = (spriteNum == 1) ? left1 : left2;
                    break;
                case "right":
                    image = (spriteNum == 1) ? right1 : right2;
                    break;
            }

            g2D.drawImage(image, screenX, screenY, gamePanel.tileSize, gamePanel.tileSize, null);
        }
    }

    /**
     * Sets dialogue for the entity to use during interactions.
     * Each dialogue is stored in the dialogues array.
     */
    public void setDialogue() {
        dialogues[0] = "Minotaur: YOU DARE ENTER MY MAZE?! \nMany men... or monkeys greater than you have FAILED.\nENTER IF YOU DARE!\n\n (Press 'C' to Continue)";
        dialogues[1] = "Villager: Help! Our Village is Being Raided!\nMonkey Martial Artist: Finally a Chance to put my Martial Arts to the Test!\n\nTip: Make Contact With Enemies to Attack Them\n(Press 'C' to Continue)";
        dialogues[2] = "";
    }
}

