package misc;

import javax.swing.*;

public class Dragon extends Pet
{
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
        this.setImages(new ImageIcon[]{
                new ImageIcon(getClass().getResource("/visuals/dragon.png")), // Happy state
                new ImageIcon(getClass().getResource("/visuals/dragonDead.png")),  // Dead state
                new ImageIcon(getClass().getResource("/visuals/dragonSad.png")),  // Sad state
                new ImageIcon(getClass().getResource("/visuals/dragonSleep.png"))  // Sleep state
        });
    }
}

