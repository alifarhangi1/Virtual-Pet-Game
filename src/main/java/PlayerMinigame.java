import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * The PlayerMinigame class represents the player-controlled character in the game.
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
     * Constructs a PlayerMinigame instance and initializes its attributes and sprites.
     *
     * @param gamePanel the GamePanel instance associated with the player
     * @param keyHandler the KeyHandler instance for player input
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
            up1 = ImageIO.read(getClass().getResourceAsStream("monkey_up1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("monkey_up2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("monkey_down1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("monkey_down2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("monkey_left.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("monkey_left3.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("monkey_right.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("monkey_right3.png"));
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
                gamePanel.musicPlayer.playSound("/evoFruit.wav");
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
                        up1 = ImageIO.read(getClass().getResourceAsStream("Monkey(11).png"));
                        up2 = ImageIO.read(getClass().getResourceAsStream("Monkey(11).png"));
                        down1 = ImageIO.read(getClass().getResourceAsStream("Monkey(10).png"));
                        down2 = ImageIO.read(getClass().getResourceAsStream("Monkey(10).png"));
                        left1 = ImageIO.read(getClass().getResourceAsStream("Monkey(6).png"));
                        left2 = ImageIO.read(getClass().getResourceAsStream("Monkey(6).png"));
                        right1 = ImageIO.read(getClass().getResourceAsStream("Monkey(7).png"));
                        right2 = ImageIO.read(getClass().getResourceAsStream("Monkey(7).png"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    enemiesLeft--;
                    gamePanel.musicPlayer.playSound("/punch.wav");
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
                gamePanel.musicPlayer.playSound("minotaurroar.wav");
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
        gamePanel.musicPlayer.playSound("/levelup.wav");
        gamePanel.ui.gameFinished = true;
    }
}


//import javax.imageio.ImageIO;
//import java.awt.*;
//import java.awt.image.BufferedImage;
//import java.io.IOException;
//import java.util.Timer;
//import java.util.TimerTask;
//
//public class PlayerMinigame extends Entity {
//
//    KeyHandler keyHandler;
//
//    public final int screenX;
//    public final int screenY;
//    public int enemiesLeft;
//    public boolean mazeMadnessWin;
//    public boolean kungfuChaosWin;
//    public boolean dragonDuelWin;
//
//
//
//    public PlayerMinigame(GamePanel gamePanel, KeyHandler keyHandler){
//
//        super(gamePanel);
//        this.keyHandler = keyHandler;
//
//
//        screenX = gamePanel.screenWidth/2;
//        screenY = gamePanel.screenHeight/2;
//
//
//        // Create solid area for player
//        solidArea = new Rectangle(8,16,(gamePanel.tileSize)-8,(gamePanel.tileSize)-8);
//        solidAreaDefaultX = solidArea.x;
//        solidAreaDefaultY = solidArea.y;
//
//        setDefaultValues();
//        getPlayerImage();
//
//
//    }
//
//    public void setDefaultValues(){
//
//        worldX = gamePanel.tileSize * 25 - (gamePanel.tileSize/2);
//        worldY = gamePanel.tileSize * 43 - (gamePanel.tileSize/2);
//        speed = 3;
//        direction = "down";
//    }
//
//    public void getPlayerImage(){
//
//        try {
//
//            up1 = ImageIO.read(getClass().getResourceAsStream("monkey_up1.png"));
//            up2 = ImageIO.read(getClass().getResourceAsStream("monkey_up2.png"));
//            down1 = ImageIO.read(getClass().getResourceAsStream("monkey_down1.png"));
//            down2 = ImageIO.read(getClass().getResourceAsStream("monkey_down2.png"));
//            left1 = ImageIO.read(getClass().getResourceAsStream("monkey_left.png"));
//            left2 = ImageIO.read(getClass().getResourceAsStream("monkey_left3.png"));
//            right1 = ImageIO.read(getClass().getResourceAsStream("monkey_right.png"));
//            right2 = ImageIO.read(getClass().getResourceAsStream("monkey_right3.png"));
//
//
//        }catch(IOException e){
//            e.printStackTrace();
//        }
//    }
//
//    public void update(){
//
//        if(keyHandler.upPressed || keyHandler.downPressed || keyHandler.leftPressed || keyHandler.rightPressed){
//            if(keyHandler.upPressed){
//                direction = "up";
//
//            }
//            if(keyHandler.downPressed){
//                direction = "down";
//
//            }
//            if(keyHandler.leftPressed){
//                direction = "left";
//
//            }
//            if(keyHandler.rightPressed){
//                direction = "right";
//
//            }
//
//            // CHECK TILE COLLISION
//            collisionOn = false;
//            gamePanel.collisionChecker.checkTile(this);
//
//            // CHECK OBJECT COLLISION
//            int objIndex = gamePanel.collisionChecker.checkObject(this, true);
//            interactWithObject(objIndex);
//
//            // CHECK NPC COLLISION
//            int npcIndex = gamePanel.collisionChecker.checkEntity(this, gamePanel.npc);
//            interactNPC(npcIndex);
//
//            // IF COLLISION IS FALSE, PLAYER CAN MOVE
//            if(!collisionOn){
//                switch (direction){
//                    case "up":
//                        worldY -= speed;
//                       break;
//                    case "down":
//                        worldY += speed;
//                        break;
//                    case "left":
//                        worldX -= speed;
//                        break;
//                    case "right":
//                        worldX += speed;
//                        break;
//                }
//            }
//            spriteCounter++;
//            if(spriteCounter > 10){
//                if(spriteNum == 1){
//                    spriteNum = 2;
//                }
//                else if(spriteNum == 2){
//                    spriteNum = 1;
//                }
//                spriteCounter = 0;
//            }
//        }
//    }
//
//
//    // ADD SOUND EFFECTS FOR EACH OBJECT HERE
//    public void interactWithObject(int i){
//
//        if(i != 999){
//            String objectName = gamePanel.obj[i].name;
//
//            switch (objectName){
//                case "Evolution Fruit":
//                    gamePanel.obj[i] = null;
//                    gamePanel.musicPlayer.playSound("/evoFruit.wav");
//                    playerWin();
//                    mazeMadnessWin = true;
//            }
//
//        }
//    }
//
//    public void interactNPC(int i){
//        if(i != 999){
//
//            // Set NPC Logic for kung fu chaos level
//            if(gamePanel.levelName.equals("Kung Fu Chaos")){
//                // Hit npc
//                // Make npc disappear and update player sprite
//                if(gamePanel.npc[i].attackable){
//                    try {
//
//                        up1 = ImageIO.read(getClass().getResourceAsStream("Monkey(11).png"));
//                        up2 = ImageIO.read(getClass().getResourceAsStream("Monkey(11).png"));
//                        down1 = ImageIO.read(getClass().getResourceAsStream("Monkey(10).png"));
//                        down2 = ImageIO.read(getClass().getResourceAsStream("Monkey(10).png"));
//                        left1 = ImageIO.read(getClass().getResourceAsStream("Monkey(6).png"));
//                        left2 = ImageIO.read(getClass().getResourceAsStream("Monkey(6).png"));
//                        right1 = ImageIO.read(getClass().getResourceAsStream("Monkey(7).png"));
//                        right2 = ImageIO.read(getClass().getResourceAsStream("Monkey(7).png"));
//
//
//                    }catch(IOException e){
//                        e.printStackTrace();
//                    }
//
//                    // Make npc disappear after beating it
//                    enemiesLeft--;
//                    gamePanel.musicPlayer.playSound("/punch.wav");
//                    gamePanel.npc[i] = null;
//
//                    // Use a Timer to reset images back to normal after a delay
//                    new Timer().schedule(new TimerTask() {
//                        @Override
//                        public void run() {
//                            getPlayerImage(); // Reset to default images
//                        }
//                    }, 500); // Delay
//
//                    // Check if all NPCs are null
//                    boolean allNull = true;
//                    for (int index = 0; index < gamePanel.npc.length-1; index++) {
//                        if (gamePanel.npc[index] != null) {
//                            allNull = false;
//                            break;
//                        }
//                    }
//
//                    if (allNull) {
//                        playerWin();
//                        kungfuChaosWin = true;
//                    }
//                }else{
//                    gamePanel.dialogueState = true;
//                    gamePanel.npc[i].speak();
//                }
//
//            }
//
//            // Set maze madness level logic
//            else if(gamePanel.levelName.equals("Maze Madness")){
//                gamePanel.dialogueState = true;
//                gamePanel.musicPlayer.playSound("minotaurroar.wav");
//                gamePanel.npc[i].speak();
//            }
//        }
//    }
//
//    public void draw(Graphics2D g2D){
//
//        BufferedImage image = null;
//
//        switch (direction){
//            case "up":
//                if(spriteNum == 1){
//                    image = up1;
//                }
//                if(spriteNum == 2){
//                    image = up2;
//                }
//                break;
//            case "down":
//                if(spriteNum == 1){
//                    image = down1;
//                }
//                if(spriteNum == 2){
//                    image = down2;
//                }
//                break;
//            case "left":
//                if(spriteNum == 1){
//                    image = left1;
//                }
//                if(spriteNum == 2){
//                    image = left2;
//                }
//                break;
//            case "right":
//                if(spriteNum == 1){
//                    image = right1;
//                }
//                if(spriteNum == 2){
//                    image = right2;
//                }
//                break;
//        }
//
//        g2D.drawImage(image, screenX, screenY, gamePanel.tileSize, gamePanel.tileSize, null);
//
//
//
//    }
//
//    public void playerWin(){
//        System.out.println("Win!");
//        gamePanel.musicPlayer.stopMusic();
//        gamePanel.musicPlayer.playSound("/levelup.wav");
//        gamePanel.ui.gameFinished = true;
//    }
//}
