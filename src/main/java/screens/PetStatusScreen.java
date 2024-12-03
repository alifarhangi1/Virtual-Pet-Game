package screens;

import misc.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

public class PetStatusScreen extends JPanel
{
    private GameScreenManager manager;
    private Player player;
    private Timer sleepTimer;
    private Timer refreshTimer;
    private boolean isSleeping = false; // Track toggle state



        public static void main(String[] args)
        {
            JFrame mainFrame = new JFrame("Pet Game");
            mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            mainFrame.setSize(800, 600);



            Pet pet = new Owl("JohnDo");
            Player player = new Player();
            player.setPet(pet);
            player.finishMinigame(0);
            player.setPlayerPet(0);

            // Mock inventory with items
            HashMap<String, Item> inventory = player.getInventory();
            inventory.put("Bone", new MediumGift());
//        inventory.put("Ball", new Item(new ImageIcon("logo.png"), "A fun toy for your pet."));
//        inventory.put("Fish", new Item(new ImageIcon("logo.png"), "A delicious fish treat."));

            mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            mainFrame.setSize(800, 600);

            GameScreenManager manager = new GameScreenManager(mainFrame, player);

            // Create the PetStatusScreen and pass the manager and player
            PetStatusScreen petStatusScreen = new PetStatusScreen(manager, player);

            // Add the PetStatusScreen to the frame
            mainFrame.add(petStatusScreen);
            mainFrame.setVisible(true);

            // Set the initial screen
            manager.showPetStatusScreen();

            // Create and display the pet status screen
            SwingUtilities.invokeLater(() -> new PetStatusScreen(manager, player));
        }

    public PetStatusScreen(GameScreenManager manager, Player player) {
        this.manager = manager;
        this.player = player;

        // Set layout for the panel
        setLayout(new BorderLayout());


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
        petImageLabel .setPreferredSize(new Dimension(600, 600));
        petImageLabel .setHorizontalAlignment(SwingConstants.CENTER);
        centerPanel.add(petImageLabel);
        add(centerPanel, BorderLayout.CENTER);

        // Bottom panel for buttons and pet name
        // Bottom panel for labels and pet name
        JPanel bottomPanel = new JPanel(new GridLayout(2, 4, 10, 10));

        // Action labels
        JLabel feedButton = createStyledLabel("Feed/Give Gift", Color.ORANGE);
        JLabel sleepButton = createStyledLabel("Sleep", Color.BLUE);
        JLabel vetButton = createStyledLabel("Take To Vet", Color.RED);
        JLabel playButton = createStyledLabel("Play", Color.GREEN);
        JLabel exerciseButton = createStyledLabel("Exercise", Color.MAGENTA);

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


        // Add button actions

        // Add label actions using MouseListener
        feedButton.addMouseListener(createActionMouseListener(() -> manager.showItemInventoryScreen()));
        sleepButton.addMouseListener(createActionMouseListener(() -> toggleSleep(pet, sleepButton, healthBar, energyBar, fullnessBar, happinessBar)));
        vetButton.addMouseListener(createActionMouseListener(() -> updateStatsAfterAction(() -> pet.vet(), healthBar, energyBar, fullnessBar, happinessBar)));
        playButton.addMouseListener(createActionMouseListener(() -> updateStatsAfterAction(() -> pet.play(), healthBar, energyBar, fullnessBar, happinessBar)));
        exerciseButton.addMouseListener(createActionMouseListener(() -> updateStatsAfterAction(() -> pet.exercise(), healthBar, energyBar, fullnessBar, happinessBar)));


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



    private void toggleSleep(Pet pet, JLabel sleepButton, JProgressBar healthBar, JProgressBar energyBar, JProgressBar fullnessBar, JProgressBar happinessBar)
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
        Image reSizedImg = img.getScaledInstance(450, 450, Image.SCALE_SMOOTH);
        return new ImageIcon(reSizedImg);
    }
    private ImageIcon resizeIcon2(ImageIcon icon)
    {
        Image img = icon.getImage();
        Image reSizedImg = img.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        return new ImageIcon(reSizedImg);
    }
    public void refreshPetStatus()
    {
        this.repaint();
    }

    private JLabel createStyledLabel(String text, Color color) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setOpaque(true);
        label.setBackground(color);
        label.setForeground(Color.WHITE); // Text color
        label.setFont(new Font("Arial", Font.BOLD, 14)); // Font styling
        label.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Optional border
        return label;
    }

    private MouseListener createActionMouseListener(Runnable action) {
        return new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                action.run(); // Execute the provided action
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                ((JLabel) e.getSource()).setCursor(new Cursor(Cursor.HAND_CURSOR)); // Change cursor to hand
            }

            @Override
            public void mouseExited(MouseEvent e) {
                ((JLabel) e.getSource()).setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); // Reset cursor
            }
        };
    }


}
