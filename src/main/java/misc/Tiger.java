package misc;

/**
 * Represents a Tiger pet
 * Extends Pet
 * @see Pet
 * @author Sangjae
 * @version 1
 */
public class Tiger extends Pet
{
    /**
     * Constructor for Tiger
     * @param name name of the pet
     */
    public Tiger(String name)
    {
        super(name, "misc.Tiger");

        // Set custom values for the misc.Tiger's stats
        this.setMaxHP(180); // Tigers have high max HP
        this.setMaxEnergy(140); // Tigers have great energy
        this.setHP(180); // Initial HP
        this.setFullness(90); // Tigers start moderately full
        this.setHappiness(85); // Tigers are happy but fierce
        this.setEnergy(130); // Initial energy

        // Load the tiger's images
        this.setImages(new String[]{
                ("/visuals/tiger.png"), // Happy state
                ("/visuals/tigerDead.png"),  // Dead state
                ("/visuals/tigerSad.png"),  // Sad state
                ("/visuals/tigerSleep.png")  // Sleep state
        });
    }
}

