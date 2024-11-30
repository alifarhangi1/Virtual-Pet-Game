import javax.swing.*;

public class Owl extends Pet
{
    boolean isEvolve;
    public Owl(String name)
    {
        super(name, "Owl");

        // Set custom values for the Owl's stats
        this.setMaxHP(100); // Owls have moderate max HP
        this.setMaxEnergy(120); // Owls are highly energetic
        this.setHP(100); // Initial HP
        this.setFullness(90); // Owls start relatively full
        this.setHappiness(85); // Owls are generally happy
        this.setEnergy(110); // Initial energy
        this.isEvolve = false;

        // Load the owl's images
        this.setImages(new ImageIcon[]{
                new ImageIcon(getClass().getResource("owl.png")), // Happy state
                new ImageIcon(getClass().getResource("owlDead.png")),  // Dead state
                new ImageIcon(getClass().getResource("owlSad.png")),  // Sad state
                new ImageIcon(getClass().getResource("owlSleep.png"))  // Sleep state
        });
    }

    public void Evolve()
    {
        // Increase stats
        this.setMaxHP(this.getMaxHP() * 2); // Double HP
        this.setMaxEnergy(this.getMaxEnergy() * 2); // Double Energy
        this.setHP(this.getMaxHP()); // Fully heal the pet

        // Update to evolved images
        this.setImages(new ImageIcon[]{
                new ImageIcon(getClass().getResource("owlEvo.png")),      // Happy state
                new ImageIcon(getClass().getResource("owlEvoDead.png")), // Dead state
                new ImageIcon(getClass().getResource("owlEvoSad.png")),  // Sad state
                new ImageIcon(getClass().getResource("owlEvoSleep.png")) // Sleep state
        });

        // Set isEvolve to true
        this.isEvolve = true;
    }

    boolean getIsEvolve()
    {

        return isEvolve;
    }
}
