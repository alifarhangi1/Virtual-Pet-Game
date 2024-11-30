

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Random;

public class NPC_Samurai extends Entity {

    public NPC_Samurai(GamePanel gamePanel){
        super(gamePanel);

        direction = "down";
        speed = 3;

        getNPCImage_Samurai();

    }

    public void getNPCImage_Samurai(){

        try {

            up1 = ImageIO.read(getClass().getResourceAsStream("right_samurai_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("right_samurai_2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("left_samurai_1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("left_samurai_2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("left_samurai_1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("left_samurai_2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("right_samurai_1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("right_samurai_2.png"));


        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void setAction(){

        actionLockCounter++;

        if(actionLockCounter == 120){
            Random random = new Random();
            int i = random.nextInt(100)+1; //pick a number from 1-100

            if(i <= 25){
                direction = "up";
            }
            else if(i > 25 && i <= 50){
                direction = "down";
            }
            else if(i > 50 && i <= 75){
                direction = "left";
            }
            else{
                direction = "right";
            }

            actionLockCounter = 0;
        }
    }
}
