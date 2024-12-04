package misc;

/**
 * Represents a small gift item.
 * extends Item
 * @see Item
 * @author Sangjae
 * @version 1.0
 */
public class SmallGift extends Item
{
    /**
     * Constructs a Small Gift item.
     */
    public SmallGift() {
        super("Small Gift: A modest present to cheer up your pet.");
        setHappyAmount(10); // Adds 10 to happiness
        setMaxHpControl(5); // Increases max HP by 5
        setType(1); // Type 1 for gift
        setName("Small Gift");
        setAmount(1);

        setImage("/visuals/SmallGift.png");
    }
}

