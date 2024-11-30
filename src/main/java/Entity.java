

import java.awt.*;
import java.awt.image.BufferedImage;

// Parent class for all characters
public class Entity {

    GamePanel gamePanel;
    public int worldX, worldY;
    public int speed;

    public BufferedImage up1, up2, down1, down2, right1, right2, left1, left2; // Use BufferedImage class to store image files

    public String direction;

    public int spriteCounter = 0;
    public int spriteNum = 1;
    public Rectangle solidArea = new Rectangle(0,0,48,48);
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn;
    public int actionLockCounter = 0; //
    String dialogues[] = new String[10]; // Store npc dialogues
    public boolean attackable;

    public Entity(GamePanel gamePanel){
        this.gamePanel = gamePanel;
    }

    public void setAction(){

    }

    public void speak(){

    }

    public void update(){
         setAction(); //call the subclass setAction method

        collisionOn = false;
        gamePanel.collisionChecker.checkTile(this); // pass npc class
        gamePanel.collisionChecker.checkObject(this, false);
        gamePanel.collisionChecker.checkPlayer(this);

        // IF COLLISION IS FALSE, PLAYER CAN MOVE
        if(!collisionOn){
            switch (direction){
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
        spriteCounter++;
        if(spriteCounter > 10){
            if(spriteNum == 1){
                spriteNum = 2;
            }
            else if(spriteNum == 2){
                spriteNum = 1;
            }
            spriteCounter = 0;
        }
    }


    public void draw(Graphics2D g2D){
        BufferedImage image = null;
        int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
        int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;


        //EFFICIENT GAME RENDERING
        if(worldX + gamePanel.tileSize> gamePanel.player.worldX - gamePanel.player.screenX &&
                worldX - gamePanel.tileSize < gamePanel.player.worldX + gamePanel.player.screenX &&
                worldY + gamePanel.tileSize > gamePanel.player.worldY - gamePanel.player.screenY &&
                worldY - gamePanel.tileSize < gamePanel.player.worldY + gamePanel.player.screenY){

            switch (direction){
                case "up":
                    if(spriteNum == 1){
                        image = up1;
                    }
                    if(spriteNum == 2){
                        image = up2;
                    }

                    break;
                case "down":
                    if(spriteNum == 1){
                        image = down1;
                    }
                    if(spriteNum == 2){
                        image = down2;
                    }
                    break;
                case "left":
                    if(spriteNum == 1){
                        image = left1;
                    }
                    if(spriteNum == 2){
                        image = left2;
                    }
                    break;
                case "right":
                    if(spriteNum == 1){
                        image = right1;
                    }
                    if(spriteNum == 2){
                        image = right2;
                    }
                    break;
            }

            g2D.drawImage(image,screenX,screenY,gamePanel.tileSize, gamePanel.tileSize, null);
        }
    }

    public void setDialogue(){

        dialogues[0] = "Minotaur: YOU DARE ENTER MY MAZE?! \nMany men... or monkeys greater than you have FAILED.\nENTER IF YOU DARE!\n\n (Press 'C' to Continue)";
        dialogues[1] = "Villager: Help! Our Village is Being Raided!\nMonkey Martial Artist: Finally a Chance to put my Martial Arts to the Test!\n\nTip: Make Contact With Enemies to Attack Them\n(Press 'C' to Continue)";
        dialogues[2] = "";

    }


}
