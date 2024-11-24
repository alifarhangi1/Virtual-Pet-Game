import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;

public class ItemInventoryScreen extends JPanel
{
    private GameScreenManager manager;
    private Player player;

    public ItemInventoryScreen(GameScreenManager manager, Player player)
    {
        this.manager = manager;
        this.player = player;

        setLayout(new GridLayout(0, 1)); // Each item in a row
        setBackground(Color.LIGHT_GRAY);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }

    public void displayInventory(HashMap<String, Item> inventory) {
        removeAll(); // Clear existing components
        for (String itemName : inventory.keySet()) {
            Item item = inventory.get(itemName);

            // Create a panel for each item
            JPanel itemPanel = new JPanel(new BorderLayout());
            itemPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            itemPanel.setBackground(Color.WHITE);

            // Add an image (placeholder for now)
            JLabel imageLabel = new JLabel(item.getImg());
            itemPanel.add(imageLabel, BorderLayout.WEST);

            // Add the item description
            JLabel descriptionLabel = new JLabel(itemName + ": " + item.getDiscription());
            itemPanel.add(descriptionLabel, BorderLayout.CENTER);

            // Add click listener for the panel
            itemPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    player.giveGift(player.getPet(), item); // Give gift to the pet
                    JOptionPane.showMessageDialog(ItemInventoryScreen.this,
                            itemName + " has been given to the pet!");

                    // Navigate back to the pet status screen
                    manager.showPetStatusScreen();
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    itemPanel.setBackground(Color.YELLOW); // Highlight the panel on hover
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    itemPanel.setBackground(Color.WHITE); // Reset panel background
                }
            });

            add(itemPanel);
        }
        revalidate(); // Refresh the panel after adding components
        repaint();
    }
}