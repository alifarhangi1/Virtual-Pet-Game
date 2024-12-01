package screens;

import misc.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;

public class ItemInventoryScreen extends JPanel {
    private GameScreenManager manager;
    private Player player;

    public ItemInventoryScreen(GameScreenManager manager, Player player) {
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

            // Add an image on the left
            JLabel imageLabelLeft = new JLabel(resizeIcon(item.getImg()));
            itemPanel.add(imageLabelLeft, BorderLayout.WEST);

            // Add the item description
            JLabel descriptionLabel;
            if (item.getType() == 0)
            {
                descriptionLabel = new JLabel(itemName + ": " + item.getDescription());
            }
            else
            {
                descriptionLabel = new JLabel(itemName + ": " + item.getDescription() + " (x" + item.getAmount() + ")");
            }
            itemPanel.add(descriptionLabel, BorderLayout.CENTER);

            // Add click listener for the panel
            itemPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    int itemAmount = item.getAmount();

                    if (itemAmount > 0) {
                        // type 2 is the Evolution Fruit
                        if (item.getType() == 2)
                        {
                            Pet pet = player.getPet();
                            // we need to check because the other 3 does not evolve
                            if (pet instanceof Wolf)
                            {
                                if (((Wolf) pet).getIsEvolve())
                                {
                                    JOptionPane.showMessageDialog(ItemInventoryScreen.this,
                                            "Your pet is already evolved.");
                                }
                                else
                                {
                                    player.giveGift(pet, item);
                                    JOptionPane.showMessageDialog(ItemInventoryScreen.this,
                                            itemName + " has been given to the pet!");
                                    item.setAmount(itemAmount - 1);
                                    if (item.getAmount() <= 0)
                                    {
                                        inventory.remove(itemName);
                                        JOptionPane.showMessageDialog(ItemInventoryScreen.this,
                                                itemName + " has been removed from the inventory!");
                                    }
                                }
                            }
                            else if (pet instanceof Owl)
                            {
                                if (((Owl) pet).getIsEvolve())
                                {
                                    JOptionPane.showMessageDialog(ItemInventoryScreen.this,
                                            "Your pet is already evolved.");

                                }
                                else
                                {
                                    player.giveGift(pet, item);
                                    JOptionPane.showMessageDialog(ItemInventoryScreen.this,
                                            itemName + " has been given to the pet!");
                                    item.setAmount(itemAmount - 1);
                                    if (item.getAmount() <= 0)
                                    {
                                        inventory.remove(itemName);
                                        JOptionPane.showMessageDialog(ItemInventoryScreen.this,
                                                itemName + " has been removed from the inventory!");
                                    }
                                }
                            }
                            else if (pet instanceof Panda)
                            {
                                if (((Panda) pet).getIsEvolve())
                                {
                                    JOptionPane.showMessageDialog(ItemInventoryScreen.this,
                                            "Your pet is already evolved.");
                                }
                                else
                                {
                                    player.giveGift(pet, item);
                                    JOptionPane.showMessageDialog(ItemInventoryScreen.this,
                                            itemName + " has been given to the pet!");
                                    item.setAmount(itemAmount - 1);
                                    if (item.getAmount() <= 0)
                                    {
                                        inventory.remove(itemName);
                                        JOptionPane.showMessageDialog(ItemInventoryScreen.this,
                                                itemName + " has been removed from the inventory!");
                                    }
                                }
                            }
                            else
                            {
                                JOptionPane.showMessageDialog(ItemInventoryScreen.this,
                                        "Your pet does not evolve.");
                            }
                        }
                        else if (item.getType() == 1) { // Type 1: Decrease amount and remove if depleted
                            item.setAmount(itemAmount - 1);

                            if (item.getAmount() <= 0) {
                                inventory.remove(itemName);
                                JOptionPane.showMessageDialog(ItemInventoryScreen.this,
                                        itemName + " has been removed from the inventory!");
                            }
                        } else if (item.getType() == 0) { // Type 0: Do not decrease amount
                            JOptionPane.showMessageDialog(ItemInventoryScreen.this,
                                    itemName + " is reusable and remains in the inventory!");
                        }

                        // Refresh the inventory display
                        displayInventory(inventory);
                    } else {
                        JOptionPane.showMessageDialog(ItemInventoryScreen.this,
                                itemName + " is out of stock and cannot be used.");
                    }

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

    private ImageIcon resizeIcon(ImageIcon icon) {
        Image img = icon.getImage();
        Image reSizedImg = img.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        return new ImageIcon(reSizedImg);
    }
}