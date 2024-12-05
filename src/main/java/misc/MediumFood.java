package misc;

/**
 * Represents a medium food
 * Extends Item
 * @see Item
 * @author Sangjae
 * @version 1
 */
public class MediumFood extends Item
{
    /**
     * constructor for medium food
     */
    public MediumFood()
    {
        super("Medium Food: A decent meal for your pet.");
        setFullAmount(20); // Adds 20 to fullness
        setHappyAmount(10); // Adds 10 to happiness
        setType(0); // Type 0 for food
        setName("Medium Food");
        setAmount(1);
        setImage("/visuals/MiddleFood.jpg");
    }
}
