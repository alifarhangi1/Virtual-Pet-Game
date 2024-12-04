package misc;

/**
 * Represents a large Gift
 * Extends Item
 * @see Item
 * @author Sangjae
 * @version 1
 */
public class LargeGift extends Item
{
    /**
     * constructor for large gift
     */
    public LargeGift()
    {
        super("Large Gift: A grand present to delight your pet!");
        setHappyAmount(30); // Adds 30 to happiness
        setMaxHpControl(15); // Increases max HP by 15
        setType(1); // Type 1 for gift
        setName("Large Gift");
        setAmount(1);
        setImage("/visuals/LargeGift.jpg");
    }
}
