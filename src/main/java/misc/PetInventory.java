package misc;

import managers.GameManager;
import screens.GameScreenManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * The PetInventory class represents a user interface screen for managing the player's pets.
 * It displays the player's pet inventory in a grid layout, allows selection of a pet,
 * and includes a back button to navigate to the previous screen.
 *
 * @author Sangjae Lee
 * @version 1.0
 */
public class PetInventory extends JPanel
{
    /**
     * Manages transitions between game screens.
     */
    private GameScreenManager manager;

    /**
     * Represents the player owning the pets.
     */
    private Player player;

    /**
     * List of labels used to display pets in the grid.
     */
    private ArrayList<JLabel> labels;

    /**
     * Stores icons for displaying pet images.
     */
    private ImageIcon[] icons;

    /**
     * The panel used to organize pet labels in a grid layout.
     */
    private JPanel gridPanel;

    /**
     * Number of pet slots available in the inventory.
     */
    public int petNumber = 8;

    /**
     * Constructs the PetInventory screen.
     *
     * @param manager the game screen manager for transitioning between screens.
     * @param gm  the manager whose player whose pets are displayed in the inventory.
     */
    public PetInventory(GameScreenManager manager, GameManager gm) {
        this.manager = manager;
        this.player = gm.getPlayer();

        setLayout(new BorderLayout());

        BackgroundPanel backgroundPanel = new BackgroundPanel("/visuals/inventoryBackground.gif");
        backgroundPanel.setLayout(new BorderLayout());

        icons = new ImageIcon[8];
        for (int i = 0; i < player.getPetList().size(); i++) {
            icons[i] = new ImageIcon(player.getPetList().get(i).getImages()[0]);
        }

        labels = new ArrayList<>();
        initializeLabels();

        // Create the grid panel
        JPanel gridPanel = new JPanel(new GridLayout(2, petNumber / 2));
        gridPanel.setBackground(new Color(0, 0, 0, 150));
        gridPanel.setOpaque(true);
        gridPanel.setBorder(BorderFactory.createLineBorder(Color.white));

        for (JLabel label : labels) {
            gridPanel.add(label);
        }

        // Create wrapper panel with padding
        JPanel outerPanel = new JPanel(new BorderLayout());
        outerPanel.setBorder(BorderFactory.createEmptyBorder(50, 30, 50, 30));
        outerPanel.setOpaque(false);
        outerPanel.add(gridPanel, BorderLayout.CENTER);

        // Add back button
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> manager.showPetStatusScreen());
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.add(backButton);

        backgroundPanel.add(outerPanel, BorderLayout.CENTER);
        backgroundPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(backgroundPanel);
    }

    /**
     * Initializes the grid panel and populates it with pet labels.
     */
    private void initializePanel() {
        gridPanel = new JPanel(new GridLayout(2, petNumber / 2));
        gridPanel.setBackground(new Color(0, 0, 0, 150));
        gridPanel.setOpaque(true);
        gridPanel.setBorder(BorderFactory.createLineBorder(Color.white));

        for (JLabel label : labels) {
            gridPanel.add(label);
        }

        add(gridPanel, BorderLayout.CENTER);
    }

    /**
     * Initializes the back button and sets its functionality.
     */
    private void initializeBackButton() {
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> manager.showPetStatusScreen());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);  // Add to this panel instead of mainPanel
    }

    /**
     * Initializes the labels for each pet in the player's inventory.
     * Labels are clickable, display pet images, and highlight on hover.
     */
    public void initializeLabels() {
        ArrayList<Pet> petList = player.getPetList();

        // Create labels based on petNumber and add icons
        for (int i = 0; i < petNumber; i++) {
            JLabel label = new JLabel();
            label.setBorder(BorderFactory.createLineBorder(Color.white));
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setVerticalAlignment(SwingConstants.CENTER);

            if (i < petList.size()) {
                // Get pet's image and create icon
                String imgPath = petList.get(i).getImages()[0];
                ImageIcon icon = resizeIcon(new ImageIcon(getClass().getResource(imgPath)));
                label.setIcon(icon);

                // Add click listener for pet selection
                final int index = i;
                label.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        player.setPet(petList.get(index));
                        JOptionPane.showMessageDialog(PetInventory.this,
                                "Selected " + petList.get(index).getName() + " as current pet!");
                        manager.showPetStatusScreen();
                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {
                        label.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 2));
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        label.setBorder(BorderFactory.createLineBorder(Color.WHITE));
                    }
                });
            } else {
                // Show placeholder for empty slots
                ImageIcon placeholder = resizeIcon(new ImageIcon(getClass().getResource("/visuals/logo.png")));
                label.setIcon(placeholder);
            }

            labels.add(label);
        }
    }

    /**
     * Creates and initializes a label with the specified icon.
     *
     * @param icon the icon to display on the label.
     * @return the initialized JLabel.
     */
    private JLabel initializeLabel(ImageIcon icon) {
        JLabel label = new JLabel();
        label.setBorder(BorderFactory.createLineBorder(Color.white));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);

        if (icon != null) {
            label.setIcon(icon);
        } else {
            ImageIcon placeholder = resizeIcon(new ImageIcon("/visuals/logo.png"));
            label.setIcon(placeholder); // Placeholder for pets without icons
        }

        return label;
    }

    /**
     * Resizes an image icon to a specified size.
     *
     * @param icon the original image icon.
     * @return the resized image icon.
     */
    private ImageIcon resizeIcon(ImageIcon icon) {
        Image img = icon.getImage();
        Image reSizedImg = img.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        return new ImageIcon(reSizedImg);
    }

}

