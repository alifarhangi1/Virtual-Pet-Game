import javax.swing.*;

public class MediumGift extends Item {
    public MediumGift() {
        super("Medium Gift: A thoughtful gift for your pet.");
        setHappyAmount(20); // Adds 20 to happiness
        setMaxHpControl(10); // Increases max HP by 10
        setType(1); // Type 1 for gift
        setName("Medium Gift");
        if (getAmount() <= 0)
            setAmount(1);
        else
            setAmount(getAmount() + 1);
        setImage(new ImageIcon(getClass().getResource("MiddleGift.jpg")));
    }
}
