import javax.swing.*;
import java.awt.*;

public class SmallFood extends Item
{
    public SmallFood()
    {
        super("Small Food: A light snack for your pet.");
        setFullAmount(10); // Adds 10 to fullness
        setHappyAmount(5); // Adds 5 to happiness
        setType(0); // Type 0 for food
        setImage(new ImageIcon(getClass().getResource("SmallFood.jpg")));
    }
}

