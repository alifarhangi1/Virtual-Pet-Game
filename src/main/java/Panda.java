import javax.swing.*;

public class Panda extends Pet {
    public Panda(String name) {
        super(name, "Panda");

        // Set custom values for the Panda's stats
        this.setMaxHP(110); // Pandas have higher max HP
        this.setMaxEnergy(90); // Pandas are less energetic
        this.setHP(110); // Initial HP
        this.setFullness(95); // Pandas start very full
        this.setHappiness(90); // Pandas are quite happy
        this.setEnergy(80); // Initial energy

        // Load the panda's images
        this.setImages(new ImageIcon[]{
                new ImageIcon(getClass().getResource("panda.png")), // Happy state
                new ImageIcon(getClass().getResource("pandaDead.png")),  // Dead state
                new ImageIcon(getClass().getResource("pandaSad.png")),  // Sad state
                new ImageIcon(getClass().getResource("pandaSleep.png"))  // Sleep state
        });
    }
}
