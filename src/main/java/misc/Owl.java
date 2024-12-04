package misc;

/**
 * Represents a Owl pet
 * Extends Pet
 * @see Pet
 * @author Sangjae
 * @version 1
 */
public class Owl extends Pet
{
    /**
    * Check if the pet has evolved
    */
    boolean isEvolve;

    /**
     * constructor for pet Owl
     * @param name name of the pet
     */
    public Owl(String name)
    {
        super(name, "misc.Owl");

        // Set custom values for the misc.Owl's stats
        this.setMaxHP(100); // Owls have moderate max HP
        this.setMaxEnergy(120); // Owls are highly energetic
        this.setHP(100); // Initial HP
        this.setFullness(90); // Owls start relatively full
        this.setHappiness(85); // Owls are generally happy
        this.setEnergy(110); // Initial energy
        this.isEvolve = false;

        // Load the owl's images
        this.setImages(new String[]{
                ("/visuals/owl.png"), // Happy state
                ("/visuals/owlDead.png"),  // Dead state
                ("/visuals/owlSad.png"),  // Sad state
                ("/visuals/owlSleep.png")  // Sleep state
        });
    }

    /**
     * Evolves the pet
     */
    public void Evolve()
    {
        // Increase stats
        this.setMaxHP(this.getMaxHP() * 2); // Double HP
        this.setMaxEnergy(this.getMaxEnergy() * 2); // Double Energy
        this.setHP(this.getMaxHP()); // Fully heal the pet

        // Update to evolved images
        this.setImages(new String[]{
                ("/visuals/owlEvo.png"),      // Happy state
                ("/visuals/owlEvoDead.png"), // Dead state
                ("/visuals/owlEvoSad.png"),  // Sad state
                ("/visuals/owlEvoSleep.png") // Sleep state
        });

        // Set isEvolve to true
        this.isEvolve = true;
    }

    /**
     * Getter for the is Evolve
     * @return is Evolve in boolean
     */
    public boolean getIsEvolve()
    {

        return isEvolve;
    }
}
