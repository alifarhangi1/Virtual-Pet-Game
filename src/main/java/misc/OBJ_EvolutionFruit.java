package misc;

import javax.imageio.ImageIO;
import java.io.IOException;

/**
 * The misc.OBJ_EvolutionFruit class represents an interactable object in the game
 * known as the "Evolution Fruit." This object has a unique sprite and enables
 * specific interactions when encountered by the player.
 */
public class OBJ_EvolutionFruit extends SuperObject {

    /**
     * Constructs an misc.OBJ_EvolutionFruit instance, sets its name, loads its sprite image,
     * and defines its collision behavior.
     */
    public OBJ_EvolutionFruit() {
        name = "Evolution Fruit";

        try {
            // Load the sprite image for the Evolution Fruit
            image = ImageIO.read(getClass().getResourceAsStream("visuals/evolutionFruit.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Set collision to true, indicating this object blocks movement
        collision = true;
    }
}


