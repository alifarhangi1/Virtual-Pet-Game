package screens;

import javax.swing.*;
import java.awt.*;

public class ParentalControlScreen extends JPanel {
    private ImageIcon backgroundIcon;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Parental Control Screen");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 500);
            frame.setContentPane(new ParentalControlScreen());
            frame.setVisible(true);
        });
    }

    public ParentalControlScreen() {
        // Set layout for the main panel
        setLayout(new BorderLayout());

        // Background image
        backgroundIcon = new ImageIcon("src/assets/visuals/background.gif");

        // Set up components
        setupComponents();
    }

    private void setupComponents() {
        // Create a content panel with a background
        JPanel contentPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundIcon.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        contentPanel.setLayout(new GridBagLayout());
        contentPanel.setBackground(new Color(139, 69, 19)); // Brown background

        // Create the bordered box panel
        JPanel boxPanel = new JPanel();
        boxPanel.setLayout(new BorderLayout());
        boxPanel.setBackground(new Color(139, 69, 19));
        boxPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        boxPanel.setPreferredSize(new Dimension(700, 400));

        // Create the control and statistics panels
        JPanel controlPanel = createControlPanel();
        JPanel statisticsPanel = createStatisticsPanel();

        // Add control and statistics panels to the box
        boxPanel.add(controlPanel, BorderLayout.WEST);
        boxPanel.add(statisticsPanel, BorderLayout.CENTER);

        // Add the box panel to the content panel
        contentPanel.add(boxPanel);

        // Add the content panel to the main panel
        add(contentPanel, BorderLayout.CENTER);
    }

    private JPanel createControlPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(true);
        panel.setBackground(new Color(139, 69, 19));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Group Name Label
        JLabel groupNameLabel = new JLabel("Group Name");
        groupNameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        groupNameLabel.setForeground(new Color(255, 255, 255));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(groupNameLabel, gbc);

        // Rounded Toggle Switch for "Time Limit On/Off"
        JComponent toggleSwitch = createRoundedToggleSwitch();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panel.add(toggleSwitch, gbc);

        // Time Limit On/Off Label
        JLabel timeLimitLabel = new JLabel("Time Limit On/Off");
        timeLimitLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        timeLimitLabel.setForeground(new Color(255, 255, 255));
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(timeLimitLabel, gbc);

        // Time Input Label
        JLabel timeInputLabel = new JLabel("Time Input:");
        timeInputLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        timeInputLabel.setForeground(new Color(255, 255, 255));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        panel.add(timeInputLabel, gbc);

        // Time Input Field
        JTextField timeInputField = new JTextField(10);
        timeInputField.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
        gbc.gridy = 3;
        panel.add(timeInputField, gbc);

        // Reset Button
        JButton resetButton = createStyledButton("Reset Setting");
        resetButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));
        gbc.gridy = 4;
        panel.add(resetButton, gbc);

        // Revive Pet Button
        JButton revivePetButton = createStyledButton("REVIVE PET");
        revivePetButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));
        gbc.gridy = 5;
        panel.add(revivePetButton, gbc);

        return panel;
    }

    private JPanel createStatisticsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(true);
        panel.setBackground(new Color(139, 69, 19));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Padding for aesthetics


        // Statistics Box
        JPanel statsBox = new JPanel();
        statsBox.setLayout(new BoxLayout(statsBox, BoxLayout.Y_AXIS));
        statsBox.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));
        statsBox.setBackground(Color.WHITE);
        statsBox.setOpaque(true);

        // Statistics Header
        JLabel statsLabel = new JLabel("Statistics");
        statsLabel.setFont(new Font("Arial", Font.BOLD, 20));
        statsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        statsLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        statsBox.add(statsLabel);

        // Total Playtime Label
        JLabel totalPlaytimeLabel = new JLabel("Total Playtime: " + gameManager.getPlayTime()); // Display playtime
        totalPlaytimeLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        totalPlaytimeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        statsBox.add(totalPlaytimeLabel);

// Average Playtime Label
        JLabel averagePlaytimeLabel = new JLabel("Average Playtime:");
        averagePlaytimeLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        averagePlaytimeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        statsBox.add(averagePlaytimeLabel);

        // Add the stats box to the panel
        panel.add(statsBox, BorderLayout.CENTER);

        return panel;
    }

    private JComponent createRoundedToggleSwitch() {
        JToggleButton toggleSwitch = new JToggleButton();
        toggleSwitch.setPreferredSize(new Dimension(50, 25));
        toggleSwitch.setFocusPainted(false);
        toggleSwitch.setBorderPainted(false);
        toggleSwitch.setContentAreaFilled(false);

        toggleSwitch.setUI(new javax.swing.plaf.basic.BasicToggleButtonUI() {
            @Override
            public void paint(Graphics g, JComponent c) {
                Graphics2D g2d = (Graphics2D) g.create();
                JToggleButton b = (JToggleButton) c;

                // Enable anti-aliasing for smooth edges
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Background (rounded border only)
                g2d.setColor(b.isSelected() ? Color.GREEN : Color.LIGHT_GRAY);
                g2d.fillRoundRect(0, 0, b.getWidth(), b.getHeight(), b.getHeight(), b.getHeight());

                // Border
                g2d.setColor(Color.BLACK);
                g2d.drawRoundRect(0, 0, b.getWidth() - 1, b.getHeight() - 1, b.getHeight(), b.getHeight());

                // Knob
                int knobWidth = b.getHeight() - 4;
                int knobX = b.isSelected() ? b.getWidth() - knobWidth - 2 : 2;
                g2d.setColor(Color.WHITE);
                g2d.fillOval(knobX, 2, knobWidth, knobWidth);

                g2d.dispose();
            }
        });

        toggleSwitch.addActionListener(e -> toggleSwitch.repaint());
        return toggleSwitch;
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(150, 40));
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBackground(Color.WHITE);
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(200, 200, 200)); // Light gray
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(Color.WHITE); // Back to white
            }
        });

        return button;
    }
}
