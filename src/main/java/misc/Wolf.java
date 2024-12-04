package misc;

import javax.swing.*;
import java.net.URL;

public class Wolf extends Pet
{
    boolean isEvolve;
    public Wolf(String name) {
        super(name, "misc.Wolf"); // Pass name and type to the parent class

        // Set custom values for the misc.Wolf's stats
        this.setMaxHP(120); // Wolves have higher max HP
        this.setMaxEnergy(110); // Wolves have more energy
        this.setHP(120); // Initial HP
        this.setFullness(80); // Wolves start a bit less full
        this.setHappiness(70); // Wolves start slightly less happy
        this.setEnergy(100); // Initial energy
        this.isEvolve = false; // Initial evolve status
        // Load the wolf's images (assuming images are stored in a folder named "images")
        this.setImages(new String[]{
                ("/visuals/wolf.png"), // Happy state
                ("/visuals/wolfDead.png"),  // Dead state
                ("/visuals/wolfSad.png"),  // Sad State
                ("/visuals/wolfSleep.png")  // Sleep State
        });
    }

    public boolean getIsEvolve()
    {
        return isEvolve;
    }

    public void Evolve()
    {
        // Increase stats
        this.setMaxHP(this.getMaxHP() * 2); // Double HP
        this.setMaxEnergy(this.getMaxEnergy() * 2); // Double Energy
        this.setHP(this.getMaxHP()); // Fully heal the pet

        // Update to evolved images
        this.setImages(new String[]{
                ("/visuals/wolfEvo.png"),      // Happy state
                ("/visuals/wolfEvoDead.png"), // Dead state
                ("/visuals/wolfEvoSad.png"),  // Sad state
                ("/visuals/wolfEvoSleep.png") // Sleep state
        });

        // Set isEvolve to true
        this.isEvolve = true;
    }

}
