import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PetStatusScreen extends JPanel
{
    private GameScreenManager manager;
    private Player player;

    public PetStatusScreen(GameScreenManager manager, Player player) {
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
        JLabel imagePlaceholder = new JLabel();
        imagePlaceholder.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        imagePlaceholder.setPreferredSize(new Dimension(300, 300));
        imagePlaceholder.setHorizontalAlignment(SwingConstants.CENTER);
        imagePlaceholder.setText("Image Placeholder");
        centerPanel.add(imagePlaceholder);
        add(centerPanel, BorderLayout.CENTER);

        // Bottom panel for buttons and pet name
        JPanel bottomPanel = new JPanel(new GridLayout(2, 4, 10, 10));

        // Action buttons
        JButton feedButton = new JButton("Feed");
        JButton sleepButton = new JButton("Sleep");
        JButton vetButton = new JButton("Take To Vet");
        JButton playButton = new JButton("Play");
        JButton exerciseButton = new JButton("Exercise");
        JButton giftButton = new JButton("Give Gift");

        // Pet name field
        JTextField petNameField = new JTextField(pet.getName());
        petNameField.setEditable(false);

        bottomPanel.add(feedButton);
        bottomPanel.add(sleepButton);
        bottomPanel.add(vetButton);
        bottomPanel.add(petNameField);
        bottomPanel.add(playButton);
        bottomPanel.add(exerciseButton);
        bottomPanel.add(giftButton);

        add(bottomPanel, BorderLayout.SOUTH);

        // Add button actions
        feedButton.addActionListener(e -> updateStatsAfterAction(() -> pet.feed(), healthBar, energyBar, fullnessBar, happinessBar));
        sleepButton.addActionListener(e -> updateStatsAfterAction(() -> pet.sleep(), healthBar, energyBar, fullnessBar, happinessBar));
        vetButton.addActionListener(e -> updateStatsAfterAction(() -> pet.vet(), healthBar, energyBar, fullnessBar, happinessBar));
        playButton.addActionListener(e -> updateStatsAfterAction(() -> pet.play(), healthBar, energyBar, fullnessBar, happinessBar));
        exerciseButton.addActionListener(e -> updateStatsAfterAction(() -> pet.exercise(), healthBar, energyBar, fullnessBar, happinessBar));

        giftButton.addActionListener(e ->
        {
            // Switch to ItemInventoryScreen
            manager.showItemInventoryScreen();
        });
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
}


