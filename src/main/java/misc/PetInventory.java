package misc;

import screens.GameScreenManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class PetInventory extends JPanel {
    private GameScreenManager manager;
    private Player player;
    private ArrayList<JLabel> labels;
    private ImageIcon[] icons;
    private JPanel gridPanel;  // We'll use this instead of 'panel'
    public int petNumber = 8;

    public PetInventory(GameScreenManager manager, Player player) {
        this.manager = manager;
        this.player = player;

        setLayout(new BorderLayout());

// Create background panel with gif
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


    private void initializeBackButton() {
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> manager.showPetStatusScreen());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);  // Add to this panel instead of mainPanel
    }

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

    private ImageIcon resizeIcon(ImageIcon icon) {
        Image img = icon.getImage();
        Image reSizedImg = img.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        return new ImageIcon(reSizedImg);
    }

}

