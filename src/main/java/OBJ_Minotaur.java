

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Minotaur extends SuperObject {

    public OBJ_Minotaur(){

      

        name = "Minotaur";
        try{
            image = ImageIO.read(getClass().getResourceAsStream("minotaur1.png"));
        }catch (IOException e){
            e.printStackTrace();
        }

        collision = true;


    }
}
