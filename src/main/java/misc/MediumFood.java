package misc;

import javax.swing.*;

public class MediumFood extends Item {
    public MediumFood() {
        super("Medium Food: A decent meal for your pet.");
        setFullAmount(20); // Adds 20 to fullness
        setHappyAmount(10); // Adds 10 to happiness
        setType(0); // Type 0 for food
        setName("Medium Food");
        setAmount(1);
        setImage(new ImageIcon(getClass().getResource("visuals/MiddleFood.jpg")));
    }
}
