package screens;

import managers.AudioManager;
import managers.DatabaseManager;
import managers.GameManager;
import managers.ScreenManager;
import misc.*;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

public class PetStatusScreen extends JPanel {
    private GameScreenManager manager;
    private GameManager gameManager;
    private DatabaseManager databaseManager;
    private Player player;
    private Timer petImageTimer;
    private Timer sleepTimer;
    private Timer refreshTimer;
    private boolean isSleeping = false; // Track toggle state
    private ImageIcon backgroundIcon;
    private static Clip bgmClip;
    private JLabel petImageLabel;
    private JTextField petNameField;
    private ScreenManager screenManager;
    private AudioManager audioManager;
    private GameManager gm;

    public PetStatusScreen(GameScreenManager manager, GameManager gm)
    {
        this.gm = GameManager.getInstance();;
        this.manager = manager;
        this.player =  gm.getPlayer();
        databaseManager = DatabaseManager.getInstance();
        screenManager = ScreenManager.getInstance();
        audioManager = AudioManager.getInstance();

        // Music
        playBackgroundMusic("/audio/inventory_bgm.wav");

        // Set layout for the panel
        setLayout(new BorderLayout());

        backgroundIcon = new ImageIcon(getClass().getResource("/visuals/background.gif"));

        // Create top panel for back button
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));  // 10 pixel gap, 5 pixel padding
        topPanel.setOpaque(false);

        // Create back button JLabel
        JLabel backButton = new JLabel("Back");
        backButton.setOpaque(true);
        backButton.setBackground(Color.RED);
        backButton.setForeground(Color.WHITE);
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        backButton.setHorizontalAlignment(SwingConstants.CENTER);
        backButton.setPreferredSize(new Dimension(80, 30)); // Adjust size as needed

        JLabel petInventoryButton = new JLabel("Pet Inventory");
        petInventoryButton.setOpaque(true);
        petInventoryButton.setBackground(new Color(70, 130, 180));  // Steel blue
        petInventoryButton.setForeground(Color.WHITE);
        petInventoryButton.setFont(new Font("Arial", Font.BOLD, 14));
        petInventoryButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        petInventoryButton.setHorizontalAlignment(SwingConstants.CENTER);
        petInventoryButton.setPreferredSize(new Dimension(120, 30));

        // Add back button to topPanel in the EAST
        topPanel.add(petInventoryButton);
        topPanel.add(backButton);

        // Add topPanel to the main panel in the NORTH
        add(topPanel, BorderLayout.NORTH);

        // Add MouseListener to backButton
        backButton.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e) {
                playSound("/audio/button_click.wav");
                // Stop any timers
                if (sleepTimer != null) sleepTimer.stop();
                if (refreshTimer != null) refreshTimer.stop();
                if (petImageTimer != null) petImageTimer.stop();

                // Stop and close the background music
                if (bgmClip != null) {
                    bgmClip.stop();
                    bgmClip.close();
                    bgmClip = null;
                }

                // Explicitly stop and nullify all timers
                if (sleepTimer != null) {
                    sleepTimer.stop();
                    sleepTimer = null;
                }
                if (refreshTimer != null) {
                    refreshTimer.stop();
                    refreshTimer = null;
                }
                if (petImageTimer != null) {
                    petImageTimer.stop();
                    petImageTimer = null;
                }

                MainMenuScreen menu = new MainMenuScreen(gm);
                JFrame frame = screenManager.getMainFrame();
                frame.getContentPane().removeAll(); // Clear the current screen
                frame.add(menu);
                frame.revalidate(); // Refresh the frame
                frame.repaint();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                backButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                playSound("/audio/menu_hover.wav");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                backButton.setCursor(Cursor.getDefaultCursor());
            }


        });

        petInventoryButton.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e) {
                playSound("/audio/button_click.wav");
                manager.showPetInventoryScreen();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                petInventoryButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                playSound("/audio/menu_hover.wav");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                petInventoryButton.setCursor(Cursor.getDefaultCursor());
            }
        });


        // Left panel for status bars
        JPanel leftPanel = new JPanel(new GridLayout(4, 1, 10, 10)); // Adjusted layout
        leftPanel.setOpaque(false);

        // Create progress labels
        ProgressLabel healthBar = createProgressLabel("Health", player.getPet().getHP(), player.getPet().getMaxHP());
        ProgressLabel energyBar = createProgressLabel("Energy", player.getPet().getEnergy(), player.getPet().getMaxEnergy());
        ProgressLabel fullnessBar = createProgressLabel("Fullness", player.getPet().getFullness(), 100);
        ProgressLabel happinessBar = createProgressLabel("Happiness", player.getPet().getHappiness(), 100);

        // Add progress labels to the panel
        leftPanel.add(healthBar);
        leftPanel.add(energyBar);
        leftPanel.add(fullnessBar);
        leftPanel.add(happinessBar);
        add(leftPanel, BorderLayout.WEST);

        // Center panel for image placeholder
        JPanel centerPanel = new JPanel();
        centerPanel.setOpaque(false);
        petImageLabel = new JLabel(); // Store reference in class field
        petImageLabel.setBackground(new Color(255, 250, 205));
        petImageLabel.setOpaque(true);
        petImageLabel.setBorder(BorderFactory.createLineBorder(Color.black));
        petImageLabel.setPreferredSize(new Dimension(800, 800));
        petImageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        centerPanel.add(petImageLabel);
        add(centerPanel, BorderLayout.CENTER);

        // Bottom panel for labels and pet name
        JPanel bottomPanel = new JPanel(new GridLayout(2, 4, 10, 10));
        bottomPanel.setOpaque(false);

        // Action labels
        JLabel feedButton = createStyledLabel("Feed/Give Gift", new Color(255, 250, 205));
        JLabel sleepButton = createStyledLabel("Sleep", new Color(255, 250, 205));
        JLabel vetButton = createStyledLabel("Take To Vet", new Color(255, 250, 205));
        JLabel playButton = createStyledLabel("Play", new Color(255, 250, 205));
        JLabel exerciseButton = createStyledLabel("Exercise", new Color(255, 250, 205));

        feedButton.setForeground(Color.black);
        sleepButton.setForeground(Color.black);
        vetButton.setForeground(Color.black);
        playButton.setForeground(Color.black);
        exerciseButton.setForeground(Color.black);

        // Pet name field
        JTextField petNameField = new JTextField(player.getPet().getName());
        petNameField.setEditable(false);

        bottomPanel.add(feedButton);
        bottomPanel.add(sleepButton);
        bottomPanel.add(vetButton);
        bottomPanel.add(petNameField);
        bottomPanel.add(playButton);
        bottomPanel.add(exerciseButton);

        add(bottomPanel, BorderLayout.SOUTH);

        // Add label actions using MouseListener
        feedButton.addMouseListener(createActionMouseListener(() -> manager.showItemInventoryScreen(), true));
        sleepButton.addMouseListener(createActionMouseListener(() -> toggleSleep(player.getPet(), sleepButton, healthBar, energyBar, fullnessBar, happinessBar), false));
        vetButton.addMouseListener(createActionMouseListener(() -> updateStatsAfterAction(() -> player.getPet().vet(), healthBar, energyBar, fullnessBar, happinessBar), true));
        playButton.addMouseListener(createActionMouseListener(() -> updateStatsAfterAction(() -> player.getPet().play(), healthBar, energyBar, fullnessBar, happinessBar), true));
        exerciseButton.addMouseListener(createActionMouseListener(() -> updateStatsAfterAction(() -> player.getPet().exercise(), healthBar, energyBar, fullnessBar, happinessBar), true));

        sleepTimer = new Timer(100, e -> updatePetImage(player.getPet(), petImageLabel));
        refreshTimer = new Timer(100, e ->
        {
            updateStats(healthBar, energyBar, fullnessBar, happinessBar);
            databaseManager.saveDatabase();
        });

        sleepTimer.start();
        refreshTimer.start();

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
                toggleSleep(player.getPet(), sleepButton, healthBar, energyBar, fullnessBar, happinessBar);
            }
        });

        // Vet shortcut (Ctrl + V)
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_DOWN_MASK), "vet");
        actionMap.put("vet", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateStatsAfterAction(() -> player.getPet().vet(), healthBar, energyBar, fullnessBar, happinessBar);
            }
        });

        // Play shortcut (Ctrl + P)
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_DOWN_MASK), "play");
        actionMap.put("play", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateStatsAfterAction(() -> player.getPet().play(), healthBar, energyBar, fullnessBar, happinessBar);
            }
        });

        // Exercise shortcut (Ctrl + E)
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_DOWN_MASK), "exercise");
        actionMap.put("exercise", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateStatsAfterAction(() -> player.getPet().exercise(), healthBar, energyBar, fullnessBar, happinessBar);
            }
        });

        feedButton.setToolTipText("Feed your pet (Ctrl+F)");
        sleepButton.setToolTipText("Toggle sleep mode (Ctrl+S)");
        vetButton.setToolTipText("Take to vet (Ctrl+V)");
        playButton.setToolTipText("Play with pet (Ctrl+P)");
        exerciseButton.setToolTipText("Exercise pet (Ctrl+E)");

        // Start the pet image timer
        petImageTimer = new Timer(100, e -> updatePetImage(player.getPet(), petImageLabel));
        petImageTimer.start();

        // Start the refresh timer
        refreshTimer = new Timer(1000, e -> {
            // Optionally decrease pet stats over time here
            updateStats(healthBar, energyBar, fullnessBar, happinessBar);
        });
        refreshTimer.start();
    }

    public void refreshPetDisplay() {
        Pet currentPet = player.getPet();

        // Update pet name field
        for (Component comp : getComponents()) {
            if (comp instanceof JPanel) {
                JPanel panel = (JPanel) comp;
                for (Component innerComp : panel.getComponents()) {
                    if (innerComp instanceof JTextField) {
                        ((JTextField) innerComp).setText(currentPet.getName());
                    }
                }
            }
        }

        // Force image update
        if (petImageTimer != null)
        {
            petImageTimer.stop();
            petImageTimer = new Timer(100, e -> updatePetImage(currentPet, petImageLabel));
            petImageTimer.start();
        }
    }

    private JLabel findPetImageLabel()
    {
        // Find the center panel and get the pet image label
        for (Component comp : getComponents())
        {
            if (comp instanceof JPanel)
            {
                JPanel panel = (JPanel) comp;
                if (panel.getLayout() instanceof BorderLayout)
                {
                    return (JLabel) panel.getComponent(0);
                }
            }
        }
        return null;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundIcon != null) {
            // Draw the background image
            g.drawImage(backgroundIcon.getImage(), 0, 0, getWidth(), getHeight(), this);
        }

    }

    private ProgressLabel createProgressLabel(String name, int currentValue, int maxValue) {
        ProgressLabel progressLabel = new ProgressLabel(name, currentValue, maxValue);
        progressLabel.setPreferredSize(new Dimension(200, 30)); // Adjust the size as needed
        return progressLabel;
    }

    private void updateStatusLabel(ProgressLabel progressLabel, int currentValue, int maxValue) {
        progressLabel.setCurrentValue(currentValue);
        progressLabel.setMaxValue(maxValue);
        progressLabel.repaint();
    }

    private void updateStatsAfterAction(Runnable action, ProgressLabel healthBar, ProgressLabel energyBar, ProgressLabel fullnessBar, ProgressLabel happinessBar) {
        action.run(); // Perform the action (e.g., sleep)
        Pet pet = player.getPet();
        updateStatusLabel(healthBar, pet.getHP(), pet.getMaxHP());
        updateStatusLabel(energyBar, pet.getEnergy(), pet.getMaxEnergy());
        updateStatusLabel(fullnessBar, pet.getFullness(), 100);
        updateStatusLabel(happinessBar, pet.getHappiness(), 100);
    }

    private void updateStats(ProgressLabel healthBar, ProgressLabel energyBar, ProgressLabel fullnessBar, ProgressLabel happinessBar) {
        Pet pet = player.getPet();
        updateStatusLabel(healthBar, pet.getHP(), pet.getMaxHP());
        updateStatusLabel(energyBar, pet.getEnergy(), pet.getMaxEnergy());
        updateStatusLabel(fullnessBar, pet.getFullness(), 100);
        updateStatusLabel(happinessBar, pet.getHappiness(), 100);
    }

    private void toggleSleep(Pet pet, JLabel sleepButton, ProgressLabel healthBar, ProgressLabel energyBar, ProgressLabel fullnessBar, ProgressLabel happinessBar) {
        if (isSleeping) {
            // Stop sleeping
            if (sleepTimer != null) {
                sleepTimer.stop();
                sleepTimer = null;
            }
            isSleeping = false;
            sleepButton.setText("Sleep");
        } else {
            // Start sleeping
            sleepTimer = new Timer(5000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    updateStatsAfterAction(pet::sleep, healthBar, energyBar, fullnessBar, happinessBar);
                }
            });
            sleepTimer.start();
            isSleeping = true;
            sleepButton.setText("Wake Up");
        }
    }

    private void updatePetImage(Pet pet, JLabel petImageLabel) {
        if (isSleeping) {
            // Show sleep sprite when sleeping
            petImageLabel.setIcon(resizeIcon(new ImageIcon(getClass().getResource(pet.getImages()[3])))); // Sleep state
        } else if (pet.getHP() <= 0) {
            // Show dead sprite when health is zero or below
            petImageLabel.setIcon(resizeIcon(new ImageIcon(getClass().getResource(pet.getImages()[1])))); // Dead state
        } else if (pet.getHappiness() <= 50) {
            // Show sad sprite when happiness is low
            petImageLabel.setIcon(resizeIcon(new ImageIcon(getClass().getResource(pet.getImages()[2])))); // Sad state
        } else {
            // Show happy sprite otherwise
            petImageLabel.setIcon(resizeIcon(new ImageIcon(getClass().getResource(pet.getImages()[0])))); // Happy state
        }
    }

    private ImageIcon resizeIcon(ImageIcon icon) {
        Image img = icon.getImage();
        Image reSizedImg = img.getScaledInstance(450, 450, Image.SCALE_SMOOTH);
        return new ImageIcon(reSizedImg);
    }

    private void playSound(String soundFilePath) {
        try {
            URL soundFile = getClass().getResource(soundFilePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    private void playBackgroundMusic(String musicFilePath) {
        try {
            // Stop existing bgmClip if it's playing
            if (bgmClip != null && bgmClip.isRunning()) {
                bgmClip.stop();
                bgmClip.close();
                bgmClip = null;
            }

            URL musicFile = getClass().getResource(musicFilePath);
            if (musicFile == null) {
                System.err.println("Music file not found: " + musicFilePath);
                return;
            }
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(musicFile);
            bgmClip = AudioSystem.getClip();
            bgmClip.open(audioStream);
            bgmClip.loop(Clip.LOOP_CONTINUOUSLY); // Loop the music continuously
            bgmClip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void refreshPetStatus() {
        this.repaint();
    }

    private JLabel createStyledLabel(String text, Color color) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setOpaque(true);
        label.setBackground(color);
        label.setForeground(Color.BLACK); // Text color
        label.setFont(new Font("Arial", Font.BOLD, 14)); // Font styling
        label.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Optional border
        return label;
    }

    private MouseListener createActionMouseListener(Runnable action, boolean disabledWhenSleeping)
    {
        return new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                JLabel button = (JLabel) e.getSource();
                if (button.isEnabled())
                {
                    if (disabledWhenSleeping && isSleeping)
                    {
                        JOptionPane.showMessageDialog(PetStatusScreen.this,
                                "Your pet is currently sleeping!", "Pet Sleeping",
                                JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }

                    action.run();
                    playSound("/audio/button_click.wav");

                    // Add cooldown for vet and exercise buttons
                    if (button.getText().equals("Take To Vet") ||
                            button.getText().equals("Play")) {
                        button.setEnabled(false);
                        Timer cooldown = new Timer(60000, event ->
                        {
                            button.setEnabled(true);
                        });
                        cooldown.setRepeats(false);
                        cooldown.start();
                    }
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                ((JLabel) e.getSource()).setCursor(new Cursor(Cursor.HAND_CURSOR)); // Change cursor to hand
                playSound("/audio/menu_hover.wav");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                ((JLabel) e.getSource()).setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); // Reset cursor
            }
        };
    }
}



class ProgressLabel extends JLabel {
    private int currentValue;
    private int maxValue;
    private String labelText;
    private boolean warningShown = false;
    private Timer vetCooldown;
    private Timer exerciseCooldown;

    public ProgressLabel(String labelText, int currentValue, int maxValue) {
        this.labelText = labelText;
        this.currentValue = currentValue;
        this.maxValue = maxValue;
        setOpaque(false); // We will handle the background ourselves
    }

    public void setCurrentValue(int currentValue) {
        this.currentValue = currentValue;
        repaint();
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        // Draw the background
        Graphics2D g2 = (Graphics2D) g;
        int width = getWidth();
        int height = getHeight();

        // Calculate the fill percentage
        float percentage = (float) currentValue / maxValue;
        int fillWidth = (int) (width * percentage);

        // Determine color based on percentage
        Color fillColor;
        if (percentage > 0.7) {
            fillColor = new Color(100, 200, 100); // Green
        } else if (percentage > 0.3) {
            fillColor = new Color(255, 215, 0); // Yellow
        } else {
            fillColor = new Color(255, 69, 0); // Red
        }

        // Draw the filled part
        g2.setColor(fillColor);
        g2.fillRect(0, 0, fillWidth, height);

        // Draw the empty part
        g2.setColor(Color.LIGHT_GRAY);
        g2.fillRect(fillWidth, 0, width - fillWidth, height);

        // Draw the border
        g2.setColor(Color.BLACK);
        g2.drawRect(0, 0, width - 1, height - 1);

        // Draw the text
        String text = labelText + ": " + currentValue + "/" + maxValue;
        FontMetrics fm = g2.getFontMetrics();
        int textWidth = fm.stringWidth(text);
        int textHeight = fm.getAscent();

        g2.setColor(Color.BLACK);
        g2.drawString(text, (width - textWidth) / 2, (height + textHeight) / 2 - 2);

        if (percentage <= 0.25 && !warningShown) {
            warningShown = true;
            JOptionPane.showMessageDialog(null, labelText + " is critically low!", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (percentage > 0.25) {
            warningShown = false;
        }
    }
}
