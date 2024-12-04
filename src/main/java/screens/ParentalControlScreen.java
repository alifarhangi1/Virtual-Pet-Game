package screens;

import managers.ScreenManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ParentalControlScreen extends JPanel {
    private ScreenManager screenManager;
    private ImageIcon backgroundIcon;
    private JTextField timeInputField;
    private JButton confirmButton;
    private JButton resetButton;
    private JButton backButton;
    private JToggleButton toggleSwitch; // The toggle switch for Time Limit On/Off
    private TimerController timerController; // Reference to manage the timer

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Parental Control Screen");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 500);
            frame.setContentPane(new ParentalControlScreen());
            frame.setVisible(true);
        });
    }

    /**
     * Constructs the ParentalControlScreen and initializes its layout and components.
     * Sets up the background image, control panel, statistics panel, and buttons.
     */
    public ParentalControlScreen() {
        // Set layout for the main panel
        setLayout(new BorderLayout());
        screenManager = ScreenManager.getInstance();

        // Background image
        backgroundIcon = new ImageIcon(getClass().getResource("/visuals/background.gif"));

        // Set up components
        setupComponents();
    }

    /**
     * Sets up the main components of the screen, including control and statistics panels.
     * Adds these components to the main content panel with a bordered box layout.
     */
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
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        contentPanel.add(boxPanel, gbc); // Adding the box panel

        // Add the back button at the bottom center of the screen
        backButton = createBackButton();
        gbc.gridx = 0; // Centered horizontally
        gbc.gridy = 1; // Placed below the control panel
        gbc.anchor = GridBagConstraints.SOUTH; // Anchor to the bottom of the container
        gbc.insets = new Insets(10, 0, 10, 0); // Add some padding around the button
        contentPanel.add(backButton, gbc);

        // Add the content panel to the main panel
        add(contentPanel, BorderLayout.CENTER);
    }

    /**
     * Creates and configures the control panel containing the time input, toggle switch,
     * and related buttons for managing parental controls.
     *
     * @return A JPanel representing the control panel.
     */
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

        // Toggle Switch for Time Limit
        toggleSwitch = createRoundedToggleSwitch();
        toggleSwitch.addActionListener(e -> {
            if (toggleSwitch.isSelected()) {
                timeInputField.setEnabled(true); // Enable input field
            } else {
                timeInputField.setEnabled(false); // Disable input field
                timeInputField.setText(""); // Clear input field
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panel.add(toggleSwitch, gbc);

        // Time Limit Label
        JLabel timeLimitLabel = new JLabel("Time Limit On/Off");
        timeLimitLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        timeLimitLabel.setForeground(new Color(255, 255, 255));
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(timeLimitLabel, gbc);

        // Time Input Label
        JLabel timeInputLabel = new JLabel("Time Input (minutes):");
        timeInputLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        timeInputLabel.setForeground(new Color(255, 255, 255));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        panel.add(timeInputLabel, gbc);

        // Time Input Field (Initially disabled)
        timeInputField = new JTextField(10);
        timeInputField.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
        timeInputField.setEnabled(false); // Initially disabled
        gbc.gridy = 3;
        panel.add(timeInputField, gbc);

        // Confirm Button (Set Timer)
        confirmButton = createStyledButton("Set");
        confirmButton.setPreferredSize(new Dimension(60, timeInputField.getPreferredSize().height)); // Match height to input field
        confirmButton.setFont(new Font("Arial", Font.BOLD, 12)); // Adjust font size
        confirmButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2)); // Smaller border width
        confirmButton.addActionListener(e -> {
            if (!toggleSwitch.isSelected()) {
                JOptionPane.showMessageDialog(null, "Please enable the time limit first.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                int timeLimit = Integer.parseInt(timeInputField.getText());
                if (timeLimit <= 0) {
                    throw new NumberFormatException();
                }
                if (timerController != null) {
                    timerController.cancelTimer(); // Cancel existing timer
                }
                timerController = new TimerController(timeLimit, (JFrame) SwingUtilities.getWindowAncestor(this));
                JOptionPane.showMessageDialog(null, "Timer set for " + timeLimit + " minutes.", "Timer Started", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Please enter a valid positive number.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            }
        });
        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        panel.add(confirmButton, gbc);

        // Reset Button
        resetButton = createStyledButton("Reset Settings");
        resetButton.setPreferredSize(new Dimension(150, 30)); // Set identical size for both buttons
        resetButton.setFont(new Font("Arial", Font.BOLD, 14)); // Adjust font size
        resetButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2)); // Add border
        resetButton.addActionListener(e -> {
            toggleSwitch.setSelected(false); // Turn off the toggle switch
            timeInputField.setEnabled(false); // Disable input field
            timeInputField.setText(""); // Clear input field
            if (timerController != null) {
                timerController.cancelTimer(); // Cancel any active timer
                timerController = null; // Reset the timer controller
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        panel.add(resetButton, gbc);

        // Revive misc.Pet Button
        JButton revivePetButton = createStyledButton("Revive Pets");
        revivePetButton.setPreferredSize(new Dimension(150, 30)); // Same size as Reset Settings button
        revivePetButton.setFont(new Font("Arial", Font.BOLD, 14)); // Adjust font size
        revivePetButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2)); // Add border
        revivePetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "misc.Pet revived successfully!", "Revive misc.Pet", JOptionPane.INFORMATION_MESSAGE);
                // Add additional functionality for reviving a pet here if needed
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 5; // Place it below the Reset Button
        gbc.gridwidth = 2;
        panel.add(revivePetButton, gbc);


        return panel;


    }

    /**
     * Creates and configures the statistics panel to display playtime information.
     *
     * @return A JPanel representing the statistics panel.
     */
    private JPanel createStatisticsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(true);
        panel.setBackground(new Color(139, 69, 19));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

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
        JLabel totalPlaytimeLabel = new JLabel("Total Playtime: 1h 3m"); // Replace with dynamic data if available
        totalPlaytimeLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        totalPlaytimeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        statsBox.add(totalPlaytimeLabel);

        // Average Playtime Label
        JLabel averagePlaytimeLabel = new JLabel("Average Playtime: 0h 7m"); // Replace with dynamic data if available
        averagePlaytimeLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        averagePlaytimeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        statsBox.add(averagePlaytimeLabel);

        // Add the stats box to the panel
        panel.add(statsBox, BorderLayout.CENTER);

        return panel;
    }

    /**
     * Creates a custom-styled rounded toggle switch with dynamic visual feedback.
     *
     * @return A JToggleButton styled as a rounded toggle switch.
     */
    private JToggleButton createRoundedToggleSwitch() {
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

                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(b.isSelected() ? Color.GREEN : Color.LIGHT_GRAY);
                g2d.fillRoundRect(0, 0, b.getWidth(), b.getHeight(), b.getHeight(), b.getHeight());

                g2d.setColor(Color.BLACK);
                g2d.drawRoundRect(0, 0, b.getWidth() - 1, b.getHeight() - 1, b.getHeight(), b.getHeight());

                int knobWidth = b.getHeight() - 4;
                int knobX = b.isSelected() ? b.getWidth() - knobWidth - 2 : 2;
                g2d.setColor(Color.WHITE);
                g2d.fillOval(knobX, 2, knobWidth, knobWidth);

                g2d.dispose();
            }
        });

        return toggleSwitch;
    }

    /**
     * Creates a styled button with consistent dimensions and appearance.
     *
     * @param text The text to display on the button.
     * @return A JButton styled according to the screen's theme.
     */
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(150, 40));
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBackground(Color.WHITE);
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        return button;
    }


    /**
     * Creates a back button with custom icons and hover effects to navigate to the previous screen.
     *
     * @return A JButton configured as the back button.
     */
    private JButton createBackButton() {
        // Default button icon
        ImageIcon backIcon = new ImageIcon(getClass().getResource("/visuals/woodButtonDefault.png"));
        // Hover button icon
        ImageIcon hoverIcon = new ImageIcon(getClass().getResource("/visuals/woodButtonHover.png"));

        JButton button = new JButton(backIcon);
        button.setPreferredSize(new Dimension(160, 100));
        button.setBorder(null);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);

        // Add hover effect using MouseListener
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                button.setIcon(hoverIcon); // Change to hover icon
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                button.setIcon(backIcon); // Revert to default icon
            }
        });

        // Add ActionListener for button click
        button.addActionListener(e -> switchToGameScreen());

        return button;
    }


    /**
     * Switches the current screen back to the "title" screen using the ScreenManager.
     */
    private void switchToGameScreen() {
        screenManager.showScreen("title");
    }
}
