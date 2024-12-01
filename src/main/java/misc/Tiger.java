package misc;

import javax.swing.*;
public class Tiger extends Pet {
    public Tiger(String name) {
        super(name, "misc.Tiger");

        // Set custom values for the misc.Tiger's stats
        this.setMaxHP(180); // Tigers have high max HP
        this.setMaxEnergy(140); // Tigers have great energy
        this.setHP(180); // Initial HP
        this.setFullness(90); // Tigers start moderately full
        this.setHappiness(85); // Tigers are happy but fierce
        this.setEnergy(130); // Initial energy

        // Load the tiger's images
        this.setImages(new ImageIcon[]{
                new ImageIcon(getClass().getResource("/visuals/tiger.png")), // Happy state
                new ImageIcon(getClass().getResource("/visuals/tigerDead.png")),  // Dead state
                new ImageIcon(getClass().getResource("/visuals/tigerSad.png")),  // Sad state
                new ImageIcon(getClass().getResource("/visuals/tigerSleep.png"))  // Sleep state
        });
    }
}

