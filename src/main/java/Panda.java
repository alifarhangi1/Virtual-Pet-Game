import javax.swing.*;

public class Panda extends Pet {
    boolean isEvolve;
    public Panda(String name) {
        super(name, "Panda");

        // Set custom values for the Panda's stats
        this.setMaxHP(110); // Pandas have higher max HP
        this.setMaxEnergy(90); // Pandas are less energetic
        this.setHP(110); // Initial HP
        this.setFullness(95); // Pandas start very full
        this.setHappiness(90); // Pandas are quite happy
        this.setEnergy(80); // Initial energy
        this.isEvolve = false;
        // Load the panda's images
        this.setImages(new ImageIcon[]{
                new ImageIcon(getClass().getResource("panda.png")), // Happy state
                new ImageIcon(getClass().getResource("pandaDead.png")),  // Dead state
                new ImageIcon(getClass().getResource("pandaSad.png")),  // Sad state
                new ImageIcon(getClass().getResource("pandaSleep.png"))  // Sleep state
        });
    }

    void Evolve()
    {
        // Increase stats
        this.setMaxHP(this.getMaxHP() * 2); // Double HP
        this.setMaxEnergy(this.getMaxEnergy() * 2); // Double Energy
        this.setHP(this.getMaxHP()); // Fully heal the pet

        // Update to evolved images
        this.setImages(new ImageIcon[]{
                new ImageIcon(getClass().getResource("pandaEvo.png")),      // Happy state
                new ImageIcon(getClass().getResource("pandaEvoDead.png")), // Dead state
                new ImageIcon(getClass().getResource("pandaEvoSad.png")),  // Sad state
                new ImageIcon(getClass().getResource("pandaEvoSleep.png")) // Sleep state
        });

        // Set isEvolve to true
        this.isEvolve = true;
    }

    boolean getIsEvolve()
    {
        return isEvolve;
    }
}
