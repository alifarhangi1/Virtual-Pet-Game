package src;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Inventory {

    private JFrame frame;
    private JPanel backgroundPanel;
    private JPanel panel;
    private ArrayList<JLabel> labels; // Store dynamically created labels
    private ImageIcon[] icons;
    public int petNumber = 8; // Adjust this variable to adjust amount of pet slots

    public static void main(String[] args)
    {
        new Inventory();
    }

    Inventory()
    {
        frame = new JFrame();
        frame.setLayout(new BorderLayout()); // Use BorderLayout to center the inventory panel
        frame.setSize(800, 600); // Set a default size for the frame

        // Create a custom background panel
        backgroundPanel = new BackgroundPanel("group50/src/Icons/inventoryBackground.gif");
        backgroundPanel.setLayout(new BorderLayout());

        icons = new ImageIcon[8];
        icons [0] = resizeIcon(new ImageIcon("group50/src/Icons/wolf.png"));
        icons [1] = resizeIcon(new ImageIcon("group50/src/Icons/panda.png"));
        icons [2] = resizeIcon(new ImageIcon("group50/src/Icons/owl.png"));
        labels = new ArrayList<>();


        initializeLabels();
        initializePanel();

        // Create a wrapper panel to add padding
        JPanel outerPanel = new JPanel(new BorderLayout());
        outerPanel.setBorder(BorderFactory.createEmptyBorder(50, 30, 50, 30)); // Add padding (top, left, bottom, right)
        outerPanel.setOpaque(false); // Make outer panel transparent
        outerPanel.add(panel, BorderLayout.CENTER);

        backgroundPanel.add(outerPanel, BorderLayout.CENTER);

        frame.add(backgroundPanel, BorderLayout.CENTER);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private void initializePanel()
    {
        panel = new JPanel(new GridLayout(2, petNumber / 2)); // Adjust grid to number of pets
        panel.setBackground(new Color(0, 0, 0, 150));
        panel.setOpaque(true);
        panel.setBorder(BorderFactory.createLineBorder(Color.white));

        // Add all dynamically created labels to the panel
        for (JLabel label : labels)
        {
            panel.add(label);
        }
    }

    private void initializeLabels() {
        // Create labels based on petNumber and add icons
        for (int i = 0; i < petNumber; i++)
        {
            ImageIcon icon = (i < icons.length) ? icons[i] : null; // Handle if more pets than icons
            JLabel label = initializeLabel(icon);
            labels.add(label);
        }
    }

    private JLabel initializeLabel(ImageIcon icon)
    {
        JLabel label = new JLabel();
        label.setBorder(BorderFactory.createLineBorder(Color.white));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);

        if (icon != null)
        {
            label.setIcon(icon);
        }
        else
        {
            ImageIcon placeholder = resizeIcon(new ImageIcon("src/Icons/logo.png"));
            label.setIcon(placeholder); // Placeholder for pets without icons
        }

        return label;
    }

    private ImageIcon resizeIcon(ImageIcon icon)
    {
        Image img = icon.getImage();
        Image reSizedImg = img.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        return new ImageIcon(reSizedImg);
    }

    private void unlock(int index)
    {
        if (GameManager.getInstance().getMyMap().get(index))
        {
            labels.get(index).setIcon(icons[index]);
        }
    }
}

