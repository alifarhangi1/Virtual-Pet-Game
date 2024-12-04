package misc;

import javax.swing.*;

/**
 * Represents a Item needed for evolution
 * Extends Item
 * @see Item
 * @author Sangjae
 * @version 1
 */
public class EvolutionFruit extends Item
{
    /**
     * constructor for Evolution Fruit
     */
    public EvolutionFruit() {
        super("Evolution Fruit: Something will change if used on a misc.Pet");
        setType(2); // Type 2 for evolution Fruit
        setName("Evolution Fruit");
        if (getAmount() <= 0)
        {
            setAmount(1);
        }
        else
        {
            setAmount(getAmount() + 1);
        }
        setImage("/visuals/evolutionFruit.png");
    }
}

