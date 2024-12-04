package screens;

import misc.Player;

import javax.swing.*;

public class GameScreenManager
{
    private JFrame frame;
    private PetStatusScreen petStatusScreen;
    private ItemInventoryScreen itemInventoryScreen;
    private Player player;

    public GameScreenManager(JFrame frame, Player player)
    {
        this.frame = frame;
        this.petStatusScreen = new PetStatusScreen(this, player);
        this.itemInventoryScreen = new ItemInventoryScreen(this, player);
        this.player = player;// Pass this manager to allow navigation
    }

    public void showPetStatusScreen() {
        frame.getContentPane().removeAll(); // Clear the current screen
        frame.add(petStatusScreen);
        frame.revalidate(); // Refresh the frame
        frame.repaint();
    }

    public void showItemInventoryScreen()
    {
        frame.getContentPane().removeAll(); // Clear the current screen
        frame.add(itemInventoryScreen);
        frame.revalidate(); // Refresh the frame
        frame.repaint();
        itemInventoryScreen.displayInventory(player.getInventory());
    }
}