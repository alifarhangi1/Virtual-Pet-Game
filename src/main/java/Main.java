import javax.swing.*;
import java.util.HashMap;


public class Main {
    public static void main(String[] args)
    {
        JFrame mainFrame = new JFrame("Pet Game");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(800, 600);

        Player player = new Player();
        player.finishMinigame(0);
        player.finishMinigame(1);
        player.finishMinigame(2);
        player.setPlayerPet(0);
        GameManager gameManager = GameManager.getInstance(player);
        Player currentPlayer = gameManager.getPlayer();
        currentPlayer.finishMinigame(0);

        // Mock inventory with items
        HashMap<String, Item> inventory = currentPlayer.getInventory();
        inventory.put("Small Food", new SmallFood());
        inventory.put("Small Gift", new SmallGift());
        inventory.put("Large Food", new LargeFood());
        inventory.put("Evolution Fruit", new EvolutionFruit());

        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(800, 600);

        GameScreenManager manager = new GameScreenManager(mainFrame, currentPlayer);

        // Create the PetStatusScreen and pass the manager and player
        PetStatusScreen petStatusScreen = new PetStatusScreen(manager, currentPlayer);

        // Add the PetStatusScreen to the frame
        mainFrame.add(petStatusScreen);
        mainFrame.setVisible(true);

        // Set the initial screen
        manager.showPetStatusScreen();

        // Create and display the pet status screen
        SwingUtilities.invokeLater(() -> new PetStatusScreen(manager, currentPlayer));
    }
}
