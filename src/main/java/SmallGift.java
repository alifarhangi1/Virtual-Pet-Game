import javax.swing.*;

public class SmallGift extends Item {
    public SmallGift() {
        super("Small Gift: A modest present to cheer up your pet.");
        setHappyAmount(10); // Adds 10 to happiness
        setMaxHpControl(5); // Increases max HP by 5
        setType(1); // Type 1 for gift
        if (getAmount() <= 0)
            setAmount(1);
        else
            setAmount(getAmount() + 1);
        setImage(new ImageIcon(getClass().getResource("SmallGift.png")));
    }
}

