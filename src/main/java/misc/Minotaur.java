package misc;

import javax.swing.*;
import java.net.URL;

public class Minotaur extends Pet
{
    public Minotaur(String name)
    {
        super(name, "misc.Minotaur");

        // Set custom values for the misc.Minotaur's stats
        this.setMaxHP(220); // Minotaurs have the highest max HP
        this.setMaxEnergy(130); // Minotaurs have moderate energy
        this.setHP(220); // Initial HP
        this.setFullness(85); // Minotaurs start less full
        this.setHappiness(80); // Minotaurs are less happy but powerful
        this.setEnergy(120); // Initial energy

        // Load the minotaur's images
        this.setImages(new String[]{
                ("/visuals/minotaur.png"), // Happy state
                ("/visuals/minotaurDead.png"),  // Dead state
                ("/visuals/minotaurSad.png"),  // Sad state
                ("/visuals/minotaurSleep.png")  // Sleep state
        });
    }
}
