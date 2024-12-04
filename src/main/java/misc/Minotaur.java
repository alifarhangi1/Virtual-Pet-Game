package misc;

/**
 * Represents a Minotaur pet
 * Extends Pet
 * @see Pet
 * @author Sangjae
 * @version 1
 */
public class Minotaur extends Pet
{
    /**
     * constructor fo the minotaur
     * @param name the name of the pet
     */
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
