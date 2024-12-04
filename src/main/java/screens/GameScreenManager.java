package screens;

import misc.PetInventory;
import misc.PetInventory;
import misc.Player;

import javax.swing.*;

public class GameScreenManager {
    private JFrame frame;
    private PetStatusScreen petStatusScreen;
    private ItemInventoryScreen itemInventoryScreen;
    private PetInventory petInventoryScreen; // Reference to the Inventory screen
    private Player player;

    public GameScreenManager(JFrame frame, Player player) {
        this.frame = frame;
        this.petStatusScreen = new PetStatusScreen(this, player);
        this.itemInventoryScreen = new ItemInventoryScreen(this, player);
        this.petInventoryScreen = new PetInventory(this, player); // Initialize the pet inventory screen
        this.player = player; // Pass this manager to allow navigation
    }

    public void showPetStatusScreen() {
        frame.getContentPane().removeAll(); // Clear the current screen
        frame.add(petStatusScreen);
        petStatusScreen.refreshPetDisplay();
        frame.revalidate(); // Refresh the frame
        frame.repaint();
    }

    public void showItemInventoryScreen() {
        frame.getContentPane().removeAll(); // Clear the current screen
        frame.add(itemInventoryScreen);
        frame.revalidate(); // Refresh the frame
        frame.repaint();
        itemInventoryScreen.displayInventory(player.getInventory());
    }

    /**
     * Show the pet inventory screen.
     */
    public void showPetInventoryScreen() {
        frame.getContentPane().removeAll();
        frame.add(petInventoryScreen);  // Add the panel directly
        petInventoryScreen.setVisible(true);
        frame.revalidate();
        frame.repaint();
    }

    public void closeFrame() {
        if (frame != null) {
            frame.dispose();
        }
    }

}