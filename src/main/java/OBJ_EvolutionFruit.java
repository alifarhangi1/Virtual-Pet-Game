

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_EvolutionFruit extends SuperObject {

    public OBJ_EvolutionFruit(){

        name="Evolution Fruit";

        try{
            image = ImageIO.read(getClass().getResourceAsStream("evolutionFruit.png"));
        }catch (IOException e){
            e.printStackTrace();
        }
        collision = true;
    }
}
