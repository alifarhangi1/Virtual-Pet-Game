

import javax.imageio.ImageIO;
import java.io.IOException;

public class NPC_Minotaur extends Entity {


    public NPC_Minotaur(GamePanel gamePanel){
        super(gamePanel);

        direction = "down";
        speed = 0;


        getNPCImage_Minotaur();
        setDialogue();
    }

    public void getNPCImage_Minotaur(){

        try {
            down1 = ImageIO.read(getClass().getResourceAsStream("minotaur1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("minotaur1.png"));
            up1 = ImageIO.read(getClass().getResourceAsStream("minotaur1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("minotaur1.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("minotaur1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("minotaur1.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("minotaur1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("minotaur1.png"));
        }catch(IOException e){
            e.printStackTrace();
        }
    }



    public void speak(){

        gamePanel.ui.currentDialogue = dialogues[0];
    }



}
