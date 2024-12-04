package screens;

import managers.GameManager;
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
    private GameManager gm;

    public GameScreenManager(JFrame frame, GameManager gm) {
        this.player = gm.getPlayer();
        this.frame = frame;
        this.gm = gm;
        this.player = gm.getPlayer(); // Pass this manager to allow navigation
    }

    public void showPetStatusScreen() {
        this.petStatusScreen = new PetStatusScreen(this, gm);
        frame.getContentPane().removeAll(); // Clear the current screen
        frame.add(petStatusScreen);
        petStatusScreen.refreshPetDisplay();
        frame.revalidate(); // Refresh the frame
        frame.repaint();
    }

    public void showItemInventoryScreen() {
        this.itemInventoryScreen = new ItemInventoryScreen(this, gm);
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
        this.petInventoryScreen = new PetInventory(this, gm); // Initialize the pet inventory screen
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