package misc;

import javax.swing.*;

public class MediumGift extends Item {
    public MediumGift() {
        super("Medium Gift: A thoughtful gift for your pet.");
        setHappyAmount(20); // Adds 20 to happiness
        setMaxHpControl(10); // Increases max HP by 10
        setType(1); // Type 1 for gift
        setName("Medium Gift");
        setAmount(1);

        setImage(new ImageIcon(getClass().getResource("/visuals/MiddleGift.jpg")));
    }
}
