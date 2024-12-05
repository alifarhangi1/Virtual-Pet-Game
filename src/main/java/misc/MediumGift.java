package misc;

import javax.swing.*;

/**
 * Represents a medium Gift
 * Extends Item
 * @see Item
 * @author Sangjae
 * @version 1
 */
public class MediumGift extends Item
{
    /**
     * constructor for medium gift
     */
    public MediumGift() {
        super("Medium Gift: A thoughtful gift for your pet.");
        setHappyAmount(20); // Adds 20 to happiness
        setMaxHpControl(10); // Increases max HP by 10
        setType(1); // Type 1 for gift
        setName("Medium Gift");
        setAmount(1);

        setImage("/visuals/MiddleGift.jpg");
    }
}
