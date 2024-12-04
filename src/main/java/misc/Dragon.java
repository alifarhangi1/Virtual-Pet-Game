package misc;

import javax.swing.*;
import java.net.URL;

/**
 * Represents a Dragon pet
 * Extends Pet
 * @see Pet
 * @author Sangjae
 * @version 1
 */
public class Dragon extends Pet
{
    /**
     * Constructor for Dragon
     * @param name name of the pet
     */
    public Dragon(String name)
    {
        super(name, "misc.Dragon");

        // Set custom values for the misc.Dragon's stats
        this.setMaxHP(200); // Dragons have very high max HP
        this.setMaxEnergy(150); // Dragons have immense energy
        this.setHP(200); // Initial HP
        this.setFullness(100); // Dragons start relatively full
        this.setHappiness(95); // Dragons are satisfied
        this.setEnergy(140); // Initial energy

        // Load the dragon's images
        this.setImages(new String[]{
                ("/visuals/dragon.png"), // Happy state
                ("/visuals/dragonDead.png"),  // Dead state
                ("/visuals/dragonSad.png"),  // Sad state
                ("/visuals/dragonSleep.png")  // Sleep state
        });
    }
}

