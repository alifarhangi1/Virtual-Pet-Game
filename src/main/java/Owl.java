import javax.swing.*;

public class Owl extends Pet
{
    public Owl(String name) {
        super(name, "Owl");

        // Set custom values for the Owl's stats
        this.setMaxHP(100); // Owls have moderate max HP
        this.setMaxEnergy(120); // Owls are highly energetic
        this.setHP(100); // Initial HP
        this.setFullness(90); // Owls start relatively full
        this.setHappiness(85); // Owls are generally happy
        this.setEnergy(110); // Initial energy

        // Load the owl's images
        this.setImages(new ImageIcon[]{
                new ImageIcon(getClass().getResource("owl.png")), // Happy state
                new ImageIcon(getClass().getResource("owlDead.png")),  // Dead state
                new ImageIcon(getClass().getResource("owlSad.png")),  // Sad state
                new ImageIcon(getClass().getResource("owlSleep.png"))  // Sleep state
        });
    }
}
