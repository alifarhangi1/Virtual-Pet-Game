import javax.imageio.ImageIO;
import java.io.IOException;

public class NPC_Villager extends Entity{

    public NPC_Villager(GamePanel gamePanel){
        super(gamePanel);

        direction = "down";
        speed = 0;


        getNPCImage_Minotaur();
        setDialogue();
    }

    public void getNPCImage_Minotaur(){

        try {
            down1 = ImageIO.read(getClass().getResourceAsStream("npcVillager.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("npcVillager.png"));
            up1 = ImageIO.read(getClass().getResourceAsStream("npcVillager.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("npcVillager.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("npcVillager.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("npcVillager.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("npcVillager.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("npcVillager.png"));
        }catch(IOException e){
            e.printStackTrace();
        }
    }



    public void speak(){

        gamePanel.ui.currentDialogue = dialogues[1];
    }
}
