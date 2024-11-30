import javax.swing.*;

public class LargeFood extends Item {
    public LargeFood() {
        super("Large Food: A feast for your pet!");
        setFullAmount(30); // Adds 30 to fullness
        setHappyAmount(20); // Adds 20 to happiness
        setType(0); // Type 0 for food
        setName("Large Food");
        setAmount(1);
        setImage(new ImageIcon(getClass().getResource("LargeFood.jpg")));
    }
}
