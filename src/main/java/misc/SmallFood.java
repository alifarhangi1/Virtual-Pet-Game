package misc;

import javax.swing.*;

/**
 * Represents a small food item that can be used to feed a pet in the game.
 * extends Item
 * @see Item
 * @author Sangjae
 * @version 1.0
 */
public class SmallFood extends Item
{
    /**
     * Constructs a SmallFood item.
     */
    public SmallFood()
    {

        super("Small Food: A light snack for your pet.");
        setName("Small Food");
        setFullAmount(10); // Adds 10 to fullness
        setHappyAmount(5); // Adds 5 to happiness
        setType(0); // Type 0 for food
        setAmount(1);
        setImage("/visuals/SmallFood.jpg");
    }
}

