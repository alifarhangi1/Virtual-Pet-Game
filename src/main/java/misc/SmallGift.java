package misc;

import javax.swing.*;

public class SmallGift extends Item {
    public SmallGift() {
        super("Small Gift: A modest present to cheer up your pet.");
        setHappyAmount(10); // Adds 10 to happiness
        setMaxHpControl(5); // Increases max HP by 5
        setType(1); // Type 1 for gift
        setName("Small Gift");
        setAmount(1);

        setImage(new ImageIcon(getClass().getResource("/visuals/SmallGift.png")));
    }
}

