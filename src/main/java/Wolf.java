import javax.swing.*;
import java.awt.*;

public class Wolf extends Pet
{
    boolean isEvolve;
    public Wolf(String name) {
        super(name, "Wolf"); // Pass name and type to the parent class

        // Set custom values for the Wolf's stats
        this.setMaxHP(120); // Wolves have higher max HP
        this.setMaxEnergy(110); // Wolves have more energy
        this.setHP(120); // Initial HP
        this.setFullness(80); // Wolves start a bit less full
        this.setHappiness(70); // Wolves start slightly less happy
        this.setEnergy(100); // Initial energy
        this.isEvolve = false; // Initial evolve status
        // Load the wolf's images (assuming images are stored in a folder named "images")
        this.setImages(new ImageIcon[]{
                new ImageIcon(getClass().getResource("wolf.png")), // Happy state
                new ImageIcon(getClass().getResource("wolfDead.png")),  // Dead state
                new ImageIcon(getClass().getResource("wolfSad.png")),  // Sad State
                new ImageIcon(getClass().getResource("wolfSleep.png"))  // Sleep State
        });
    }

    boolean getIsEvolve()
    {
        return isEvolve;
    }

    void Evolve()
    {
        // Increase stats
        this.setMaxHP(this.getMaxHP() * 2); // Double HP
        this.setMaxEnergy(this.getMaxEnergy() * 2); // Double Energy
        this.setHP(this.getMaxHP()); // Fully heal the pet

        // Update to evolved images
        this.setImages(new ImageIcon[]{
                new ImageIcon(getClass().getResource("wolfEvo.png")),      // Happy state
                new ImageIcon(getClass().getResource("wolfEvoDead.png")), // Dead state
                new ImageIcon(getClass().getResource("wolfEvoSad.png")),  // Sad state
                new ImageIcon(getClass().getResource("wolfEvoSleep.png")) // Sleep state
        });

        // Set isEvolve to true
        this.isEvolve = true;
    }

}
