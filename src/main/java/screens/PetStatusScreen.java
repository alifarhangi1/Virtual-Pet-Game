package screens;

import misc.Pet;
import misc.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PetStatusScreen extends JPanel
{
    private GameScreenManager manager;
    private Player player;
    private Timer sleepTimer;
    private Timer refreshTimer;
    private boolean isSleeping = false; // Track toggle state

    public PetStatusScreen(GameScreenManager manager, Player player) {
        this.manager = manager;
        this.player = player;

        // Set layout for the panel
        setLayout(new BorderLayout());

        // Top panel for title
        JPanel topPanel = new JPanel();
        JLabel titleLabel = new JLabel("misc.Pet Status and Interaction Screen");
        topPanel.add(titleLabel);
        add(topPanel, BorderLayout.NORTH);

        // Left panel for status bars
        JPanel leftPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        Pet pet = player.getPet();

        // Create status bars
        JProgressBar healthBar = createStatusBar("Health", pet.getHP(), pet.getMaxHP());
        JProgressBar energyBar = createStatusBar("Sleep", pet.getEnergy(), pet.getMaxEnergy());
        JProgressBar fullnessBar = createStatusBar("Fullness", pet.getHunger(), 100);
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
        JLabel petImageLabel  = new JLabel();
        petImageLabel .setBorder(BorderFactory.createLineBorder(Color.BLACK));
        petImageLabel .setPreferredSize(new Dimension(300, 300));
        petImageLabel .setHorizontalAlignment(SwingConstants.CENTER);
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

        // misc.Pet name field
        JTextField petNameField = new JTextField(pet.getName());
        petNameField.setEditable(false);

        bottomPanel.add(feedButton);
        bottomPanel.add(sleepButton);
        bottomPanel.add(vetButton);
        bottomPanel.add(petNameField);
        bottomPanel.add(playButton);
        bottomPanel.add(exerciseButton);

        add(bottomPanel, BorderLayout.SOUTH);

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

    }

    private JProgressBar createStatusBar(String name, int currentValue, int maxValue)
    {
        JProgressBar progressBar = new JProgressBar(0, maxValue);
        progressBar.setValue(currentValue);
        progressBar.setStringPainted(true);
        progressBar.setString(currentValue + "/" + maxValue);
        return progressBar;
    }

    private void updateStatusBar(JProgressBar progressBar, int currentValue, int maxValue) {
        progressBar.setMaximum(maxValue);
        progressBar.setValue(currentValue);
        progressBar.setString(currentValue + "/" + maxValue);
    }

    private void updateStatsAfterAction(Runnable action, JProgressBar healthBar, JProgressBar energyBar, JProgressBar fullnessBar, JProgressBar happinessBar)
    {
        action.run(); // Perform the action (e.g., feed, play)
        Pet pet = player.getPet();
        updateStatusBar(healthBar, pet.getHP(), pet.getMaxHP());
        updateStatusBar(energyBar, pet.getEnergy(), pet.getMaxEnergy());
        updateStatusBar(fullnessBar, pet.getHunger(), 100);
        updateStatusBar(happinessBar, pet.getHappiness(), 100);
    }
    private void updateStats(Runnable action, JProgressBar healthBar, JProgressBar energyBar, JProgressBar fullnessBar, JProgressBar happinessBar)
    {
        Pet pet = player.getPet();
        updateStatusBar(healthBar, pet.getHP(), pet.getMaxHP());
        updateStatusBar(energyBar, pet.getEnergy(), pet.getMaxEnergy());
        updateStatusBar(fullnessBar, pet.getHunger(), 100);
        updateStatusBar(happinessBar, pet.getHappiness(), 100);
    }


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
    private void updatePetImage(Pet pet, JLabel petImageLabel)
    {
        if (isSleeping)
        {
            // Show sleep sprite when sleeping
            petImageLabel.setIcon(resizeIcon(pet.getImages()[3])); // Sleep state
        } else if (pet.getHP() <= 0)
        {
            // Show dead sprite when health is zero or below
            petImageLabel.setIcon(resizeIcon(pet.getImages()[1])); // Dead state
        }
        else if (pet.getHappiness() <= 50)
        {
            // Show sad sprite when happiness is low
            petImageLabel.setIcon(resizeIcon(pet.getImages()[2])); // Sad state
        }
        else
        {
            // Show happy sprite otherwise
            petImageLabel.setIcon(resizeIcon(pet.getImages()[0])); // Happy state
        }
    }

    private ImageIcon resizeIcon(ImageIcon icon)
    {
        Image img = icon.getImage();
        Image reSizedImg = img.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        return new ImageIcon(reSizedImg);
    }
    public void refreshPetStatus()
    {
        this.repaint();
    }
}
