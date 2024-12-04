package misc;

/**
 * Represents a large food
 * Extends Item
 * @see Item
 * @author Sangjae
 * @version 1
 */
public class LargeFood extends Item
{
    /**
     * constructor for large Food
     */
    public LargeFood()
    {
        super("Large Food: A feast for your pet!");
        setFullAmount(30); // Adds 30 to fullness
        setHappyAmount(20); // Adds 20 to happiness
        setType(0); // Type 0 for food
        setName("Large Food");
        setAmount(1);
        setImage("/visuals/LargeFood.jpg");
    }
}
