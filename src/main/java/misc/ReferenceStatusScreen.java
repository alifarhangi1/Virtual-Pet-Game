package misc;

import misc.Player;
import screens.GameScreenManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * The ReferenceStatusScreen class represents a GUI for the player's pet's status.
 * <br><br>
 * This screen allows real-time updates to the pet's attributes such as health, energy, fullness,
 * and happiness while providing interactive buttons for actions like feeding, sleeping, and playing.
 *
 * @see GameScreenManager
 * @see Player
 * @see Pet
 * @author Sangjae Lee
 * @version 1.0
 */
public class ReferenceStatusScreen extends JPanel {
    /**
     * The game screen manager for managing screen transitions.
     */
    private GameScreenManager manager;
    /**
     * The player associated with the pet.
     */
    private Player player;
    /**
     * Timer for updating the sleep functionality.
     */
    private Timer sleepTimer;
    /**
     * Timer for refreshing the pet's status in real time.
     */
    private Timer refreshTimer;
    /**
     * Tracks whether the pet is currently sleeping.
     */
    private boolean isSleeping = false; // Track toggle state
    /**
     * Warning threshold for status bars (25%)
     */
    private static final double WARNING_THRESHOLD = 0.25;
    private Map<String, Boolean> warningShown;
    private Map<String, Boolean> isShowingWarning = new HashMap<>();


    /**
     * Constructs for PetStatusScreen object.
     *
     * @param manager the game screen manager
     * @param player  the player whose pet's status is displayed and interacted with
     */
    public ReferenceStatusScreen(GameScreenManager manager, Player player) {
        this.manager = manager;
        this.player = player;

        // Set layout for the panel
        setLayout(new BorderLayout());

        // Top panel for title
        JPanel topPanel = new JPanel();
        JLabel titleLabel = new JLabel("Pet Status and Interaction Screen");
        topPanel.add(titleLabel);
        add(topPanel, BorderLayout.NORTH);

        // Left panel for status bars
        JPanel leftPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        Pet pet = player.getPet();

        // Create status bars
        JProgressBar healthBar = createStatusBar("Health", pet.getHP(), pet.getMaxHP());
        JProgressBar energyBar = createStatusBar("Sleep", pet.getEnergy(), pet.getMaxEnergy());
        JProgressBar fullnessBar = createStatusBar("Fullness", pet.getFullness(), 100);
        JProgressBar happinessBar = createStatusBar("Happiness", pet.getHappiness(), 100);

        leftPanel.add(new JLabel("Health:"));
        leftPanel.add(healthBar);
        leftPanel.add(new JLabel("Sleep:"));
        leftPanel.add(energyBar);
        leftPanel.add(new JLabel("Fullness:"));
        leftPanel.add(fullnessBar);
        leftPanel.add(new JLabel("Happiness:"));
        leftPanel.add(happinessBar);

        add(leftPanel, BorderLayout.WEST);

        // Center panel for image placeholder
        JPanel centerPanel = new JPanel();
        JLabel petImageLabel = new JLabel();
        petImageLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        petImageLabel.setPreferredSize(new Dimension(300, 300));
        petImageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        centerPanel.add(petImageLabel);
        add(centerPanel, BorderLayout.CENTER);

        // Bottom panel for buttons and pet name
        JPanel bottomPanel = new JPanel(new GridLayout(2, 4, 10, 10));

        // Action buttons
        JButton feedButton = new JButton("Feed/Give Gift");
        JButton sleepButton = new JButton("Start Sleeping"); // Updated label for toggle functionality
        JButton vetButton = new JButton("Take To Vet");
        JButton playButton = new JButton("Play");
        JButton exerciseButton = new JButton("Exercise");

        // Pet name field
        JTextField petNameField = new JTextField(pet.getName());
        petNameField.setEditable(false);

        bottomPanel.add(feedButton);
        bottomPanel.add(sleepButton);
        bottomPanel.add(vetButton);
        bottomPanel.add(petNameField);
        bottomPanel.add(playButton);
        bottomPanel.add(exerciseButton);

        add(bottomPanel, BorderLayout.SOUTH);

        warningShown = new HashMap<>();
        warningShown.put("Health", false);
        warningShown.put("Sleep", false);
        warningShown.put("Fullness", false);
        warningShown.put("Happiness", false);

        // Add button actions

        sleepButton.addActionListener(e -> toggleSleep(pet, sleepButton, healthBar, energyBar, fullnessBar, happinessBar));
        vetButton.addActionListener(e -> updateStatsAfterAction(() -> pet.vet(), healthBar, energyBar, fullnessBar, happinessBar));
        playButton.addActionListener(e -> updateStatsAfterAction(() -> pet.play(), healthBar, energyBar, fullnessBar, happinessBar));
        exerciseButton.addActionListener(e -> updateStatsAfterAction(() -> pet.exercise(), healthBar, energyBar, fullnessBar, happinessBar));
        feedButton.addActionListener(e -> manager.showItemInventoryScreen());

        sleepTimer = new Timer(100, e -> updatePetImage(pet, petImageLabel));
        refreshTimer = new Timer(100, e -> updateStats(pet::sleep, healthBar, energyBar, fullnessBar, happinessBar));
        sleepTimer.start();
        refreshTimer.start();

        feedButton.setMnemonic(KeyEvent.VK_F);  // Alt + F
        sleepButton.setMnemonic(KeyEvent.VK_S); // Alt + S
        vetButton.setMnemonic(KeyEvent.VK_V);   // Alt + V
        playButton.setMnemonic(KeyEvent.VK_P);  // Alt + P
        exerciseButton.setMnemonic(KeyEvent.VK_E); // Alt + E

        InputMap inputMap = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = getActionMap();

        // Feed shortcut (Ctrl + F)
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_DOWN_MASK), "feed");
        actionMap.put("feed", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                manager.showItemInventoryScreen();
            }
        });

        // Sleep shortcut (Ctrl + S)
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK), "sleep");
        actionMap.put("sleep", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toggleSleep(pet, sleepButton, healthBar, energyBar, fullnessBar, happinessBar);
            }
        });

        // Vet shortcut (Ctrl + V)
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_DOWN_MASK), "vet");
        actionMap.put("vet", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateStatsAfterAction(() -> pet.vet(), healthBar, energyBar, fullnessBar, happinessBar);
            }
        });

        // Play shortcut (Ctrl + P)
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_DOWN_MASK), "play");
        actionMap.put("play", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateStatsAfterAction(() -> pet.play(), healthBar, energyBar, fullnessBar, happinessBar);
            }
        });

        // Exercise shortcut (Ctrl + E)
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_DOWN_MASK), "exercise");
        actionMap.put("exercise", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateStatsAfterAction(() -> pet.exercise(), healthBar, energyBar, fullnessBar, happinessBar);
            }
        });

        feedButton.setToolTipText("Feed your pet (Ctrl+F or Alt+F)");
        sleepButton.setToolTipText("Toggle sleep mode (Ctrl+S or Alt+S)");
        vetButton.setToolTipText("Take to vet (Ctrl+V or Alt+V)");
        playButton.setToolTipText("Play with pet (Ctrl+P or Alt+P)");
        exerciseButton.setToolTipText("Exercise pet (Ctrl+E or Alt+E)");
    }

    /**
     * Creates a status bar for tracking a pet's status.
     *
     * @param name         the name of the pet
     * @param currentValue the current value of the pet Status
     * @param maxValue     the maximum value of the pet Status
     * @return JProgressBar configured for the attribute
     */
    private JProgressBar createStatusBar(String name, int currentValue, int maxValue) {
        JProgressBar progressBar = new JProgressBar(0, maxValue);
        progressBar.setValue(currentValue);
        progressBar.setStringPainted(true);
        progressBar.setString(currentValue + "/" + maxValue);
        return progressBar;
    }

    /**
     * Updates the value and display of a status bar.
     *
     * @param progressBar  the bar to update
     * @param currentValue the new current value
     * @param maxValue     the new maximum value
     */
    private void updateStatusBar(JProgressBar progressBar, int currentValue, int maxValue) {
        progressBar.setMaximum(maxValue);
        progressBar.setValue(currentValue);
        progressBar.setString(currentValue + "/" + maxValue);

        // Calculate percentage
        double percentage = (double) currentValue / maxValue;

        // Get status name
        String statusName = "";
        Container parent = progressBar.getParent();
        if (parent instanceof JPanel) {
            Component[] components = parent.getComponents();
            for (int i = 0; i < components.length; i++) {
                if (components[i] instanceof JLabel && components[i + 1] == progressBar) {
                    statusName = ((JLabel) components[i]).getText().replace(":", "");
                    break;
                }
            }
        }

        // Ensure the statusName is tracked in isShowingWarning
        isShowingWarning.putIfAbsent(statusName, false);

        if (percentage <= WARNING_THRESHOLD) {
            progressBar.setForeground(Color.RED);

            // Only show warning if it hasn't been shown for this status
            if (!isShowingWarning.get(statusName)) {
                isShowingWarning.put(statusName, true);

                // Show dialog relative to the game window
                String finalStatusName = statusName;
                SwingUtilities.invokeLater(() -> {
                    Window gameWindow = SwingUtilities.getWindowAncestor(this);
                    if (gameWindow != null) {
                        JOptionPane.showMessageDialog(
                                gameWindow,
                                "Warning: " + finalStatusName + " is low (" +
                                        (int)(percentage * 100) + "%)!",
                                "Status Warning",
                                JOptionPane.WARNING_MESSAGE
                        );
                    }
                });
            }
        } else {
            progressBar.setForeground(UIManager.getColor("ProgressBar.foreground"));

            // Reset warning state when value goes above threshold
            if (isShowingWarning.get(statusName)) {
                isShowingWarning.put(statusName, false);
            }
        }
    }
    /**
     * Executes an action and refreshes the status bars to reflect the updated pet attributes.
     *
     * @param action the action to execute
     * @param healthBar the health status bar
     * @param energyBar the energy status bar
     * @param fullnessBar the fullness status bar
     * @param happinessBar the happiness status bar
     */
    private void updateStatsAfterAction(Runnable action, JProgressBar healthBar, JProgressBar energyBar, JProgressBar fullnessBar, JProgressBar happinessBar)
    {
        action.run(); // Perform the action (e.g., feed, play)
        Pet pet = player.getPet();
        updateStatusBar(healthBar, pet.getHP(), pet.getMaxHP());
        updateStatusBar(energyBar, pet.getEnergy(), pet.getMaxEnergy());
        updateStatusBar(fullnessBar, pet.getFullness(), 100);
        updateStatusBar(happinessBar, pet.getHappiness(), 100);
    }
    /**
     * Updates the stats with an action
     *
     * @param action       the action performed, defined as a Runnable
     * @param healthBar    the progress bar for health
     * @param energyBar    the progress bar for energy
     * @param fullnessBar  the progress bar for fullness
     * @param happinessBar the progress bar for happiness
     */
    private void updateStats(Runnable action, JProgressBar healthBar, JProgressBar energyBar, JProgressBar fullnessBar, JProgressBar happinessBar)
    {
        Pet pet = player.getPet();
        updateStatusBar(healthBar, pet.getHP(), pet.getMaxHP());
        updateStatusBar(energyBar, pet.getEnergy(), pet.getMaxEnergy());
        updateStatusBar(fullnessBar, pet.getFullness(), 100);
        updateStatusBar(happinessBar, pet.getHappiness(), 100);
    }

    /**
     * Toggles the sleeping state of the pet, updating relevant stats as well
     *
     * @param pet          the pet object whose state is toggled
     * @param sleepButton  the button to toggle sleeping
     * @param healthBar    the progress bar for health
     * @param energyBar    the progress bar for energy
     * @param fullnessBar  the progress bar for fullness
     * @param happinessBar the progress bar for happiness
     */
    private void toggleSleep(Pet pet, JButton sleepButton, JProgressBar healthBar, JProgressBar energyBar, JProgressBar fullnessBar, JProgressBar happinessBar)
    {
        if (isSleeping)
        {
            // Stop sleeping
            sleepTimer.stop();
            isSleeping = false;
            sleepButton.setText("Start Sleeping");
        }
        else
        {
            // Start sleeping
            sleepTimer = new Timer(5000, new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    updateStatsAfterAction(pet::sleep, healthBar, energyBar, fullnessBar, happinessBar);
                }
            });
            sleepTimer.start();
            isSleeping = true;
            sleepButton.setText("Stop Sleeping");
        }
    }

    /**
     * Updates the displayed pet image based on its current state.
     *
     * @param pet            the pet object whose image is being updated
     * @param petImageLabel  the label to display the pet image
     */
    private void updatePetImage(Pet pet, JLabel petImageLabel)
    {
        if (isSleeping)
        {
            // Show sleep sprite when sleeping
            petImageLabel.setIcon(resizeIcon(new ImageIcon(getClass().getResource(pet.getImages()[3])))); // Sleep state
        } else if (pet.getHP() <= 0)
        {
            // Show dead sprite when health is zero or below
            petImageLabel.setIcon(resizeIcon(new ImageIcon(getClass().getResource(pet.getImages()[1])))); // Dead state
        }
        else if (pet.getHappiness() <= 50)
        {
            // Show sad sprite when happiness is low
            petImageLabel.setIcon(resizeIcon(new ImageIcon(getClass().getResource(pet.getImages()[2])))); // Sad state
        }
        else
        {
            // Show happy sprite otherwise
            petImageLabel.setIcon(resizeIcon(new ImageIcon(getClass().getResource(pet.getImages()[0])))); // Happy state
        }
    }

    /**
     * Resizes the given icon to fit within predefined dimensions.
     *
     * @param icon the icon to resize
     * @return the resized ImageIcon
     */
    private ImageIcon resizeIcon(ImageIcon icon)
    {
        Image img = icon.getImage();
        Image reSizedImg = img.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        return new ImageIcon(reSizedImg);
    }

    /**
     * Refreshes the pet's status on the screen.
     * Useful when updates need to be manually triggered.
     */
    public void refreshPetStatus()
    {
        this.repaint();
    }
}

