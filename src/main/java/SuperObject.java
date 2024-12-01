import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * The SuperObject class serves as a base class for all interactive objects in the game.
 * These objects can have a sprite, a name, collision behavior, and a position in the game world.
 */
public class SuperObject {

    /** The image representing the object's sprite. */
    public BufferedImage image;

    /** The name of the object. */
    public String name;

    /** Flag indicating whether the object has collision enabled. */
    public boolean collision;

    /** The X-coordinate of the object's position in the game world. */
    public int worldX;

    /** The Y-coordinate of the object's position in the game world. */
    public int worldY;

    /** The solid area of the object used for collision detection. */
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);

    /** The default X-offset for the solid area's position. */
    public int solidAreaDefaultX = 0;

    /** The default Y-offset for the solid area's position. */
    public int solidAreaDefaultY = 0;

    /**
     * Draws the object on the screen if it is within the player's viewport.
     *
     * @param g2D       the Graphics2D object used for rendering
     * @param gamePanel the GamePanel instance containing the player and game settings
     */
    public void draw(Graphics2D g2D, GamePanel gamePanel) {
        int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
        int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;

        // Efficient game rendering: only draw the object if it is within the viewport
        if (worldX + gamePanel.tileSize > gamePanel.player.worldX - gamePanel.player.screenX &&
                worldX - gamePanel.tileSize < gamePanel.player.worldX + gamePanel.player.screenX &&
                worldY + gamePanel.tileSize > gamePanel.player.worldY - gamePanel.player.screenY &&
                worldY - gamePanel.tileSize < gamePanel.player.worldY + gamePanel.player.screenY) {

            g2D.drawImage(image, screenX, screenY, gamePanel.tileSize, gamePanel.tileSize, null);
        }
    }
}


//import java.awt.*;
//import java.awt.image.BufferedImage;
//
//public class SuperObject {
//
//    public BufferedImage image;
//    public String name;
//    public boolean collision;
//    public int worldX, worldY;
//    public Rectangle solidArea = new Rectangle(0,0,48,48);
//    public int solidAreaDefaultX = 0;
//    public int solidAreaDefaultY = 0;
//
//
//
//
//    public void draw(Graphics2D g2D, GamePanel gamePanel){
//        int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
//        int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;
//
//
//        //EFFICIENT GAME RENDERING
//        if(worldX + gamePanel.tileSize> gamePanel.player.worldX - gamePanel.player.screenX &&
//                worldX - gamePanel.tileSize < gamePanel.player.worldX + gamePanel.player.screenX &&
//                worldY + gamePanel.tileSize > gamePanel.player.worldY - gamePanel.player.screenY &&
//                worldY - gamePanel.tileSize < gamePanel.player.worldY + gamePanel.player.screenY){
//
//            g2D.drawImage(image,screenX,screenY,gamePanel.tileSize, gamePanel.tileSize, null);
//        }
//    }
//
//
//}
