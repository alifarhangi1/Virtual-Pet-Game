package screens;

import managers.GameManager;
import misc.PetInventory;
import misc.PetInventory;
import misc.Player;

import javax.swing.*;

/**
 * Manages the navigation and display of different game screens.
 * Controls transitions between Pet Status, Item Inventory, and Pet Inventory screens
 * while maintaining the game state.
 *
 * @version 2.0
 * @author Robin Lee
 */
public class GameScreenManager
{
    /** Main application window */
    private JFrame frame;
    /** Screen displaying pet status information */
    private PetStatusScreen petStatusScreen;
    /** Screen displaying player's item inventory */
    private ItemInventoryScreen itemInventoryScreen;
    /** Screen displaying player's pet inventory */
    private PetInventory petInventoryScreen;
    /** Current player instance */
    private Player player;
    private GameManager gm;

    /**
     * Initializes the game screen manager with necessary components.
     *
     * @param frame The main application window
     * @param gm The current gamemanager instance
     */
    public GameScreenManager(JFrame frame, GameManager gm) {
        this.player = gm.getPlayer();
        this.frame = frame;
        this.gm = gm;
        this.player = gm.getPlayer(); // Pass this manager to allow navigation
    }

    /**
     * Switches display to the pet status screen and refreshes pet information.
     */
    public void showPetStatusScreen() {
        this.petStatusScreen = new PetStatusScreen(this, gm);
        frame.getContentPane().removeAll(); // Clear the current screen
        frame.add(petStatusScreen);
        petStatusScreen.refreshPetDisplay();
        frame.revalidate(); // Refresh the frame
        frame.repaint();
    }

    /**
     * Switches display to the item inventory screen and updates inventory contents.
     */
    public void showItemInventoryScreen() {
        this.itemInventoryScreen = new ItemInventoryScreen(this, gm);
        frame.getContentPane().removeAll(); // Clear the current screen
        frame.add(itemInventoryScreen);
        frame.revalidate(); // Refresh the frame
        frame.repaint();
        itemInventoryScreen.displayInventory(player.getInventory());
    }

    /**
     * Switches display to the pet inventory screen.
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