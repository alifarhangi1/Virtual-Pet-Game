import javax.swing.*;
import java.util.HashMap;

public class Main {
    public static void main(String[] args)
    {
        JFrame mainFrame = new JFrame("Pet Game");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(800, 600);

        Pet pet = new Pet("JohnDo", "Human");
        Player player = new Player();
        player.finishMinigame(0);
        player.setPlayerPet(0);

        // Mock inventory with items
        HashMap<String, Item> inventory = player.getInventory();
        inventory.put("Bone", new Item(new ImageIcon("logo.png"), "A tasty bone for your pet!"));
        inventory.put("Ball", new Item(new ImageIcon("logo.png"), "A fun toy for your pet."));
        inventory.put("Fish", new Item(new ImageIcon("logo.png"), "A delicious fish treat."));

        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(800, 600);

        GameScreenManager manager = new GameScreenManager(mainFrame, player);

        // Create the PetStatusScreen and pass the manager and player
        PetStatusScreen petStatusScreen = new PetStatusScreen(manager, player);

        // Add the PetStatusScreen to the frame
        mainFrame.add(petStatusScreen);
        mainFrame.setVisible(true);

        // Set the initial screen
        manager.showPetStatusScreen();

        // Create and display the pet status screen
        SwingUtilities.invokeLater(() -> new PetStatusScreen(manager, player));
    }
}
