package misc;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class WaterDragonBoss {

    // Class-level variables
    private JFrame frame;
    private JPanel mainPanel;
    private Color backgroundColor;
    private HealthBarLabel bossHealthBarLabel;
    private HealthBarLabel playerHealthBarLabel;
    private JLabel monkeyLabel;
    private ImageIcon monkeyIconOriginal;
    private ImageIcon monkeyIconAttack;
    private Player player;
    private int playerHealth;

    public static void main(String[] args) {
        new WaterDragonBoss();
    }

    WaterDragonBoss() {
        // player = new player(); // Initialize the player
        //playerHealth = player.getPet().getHP(); // Get health from player's pet
        playerHealth = 100;
        initializeFrame();
        initializeMainPanel();
        createBossHealthBarPanel();
        createPlayerHealthBarPanel();
        createGifPanel();
        createMonkeyPanel();

        frame.add(mainPanel, BorderLayout.CENTER);
        frame.setVisible(true);

        startPlayerHealthDecay();
    }

    private void initializeFrame() {
        // Create a new JFrame
        frame = new JFrame("Boss Health Bar");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());
    }

    private void initializeMainPanel() {
        // Set the predominant background color
        backgroundColor = new Color(31, 95, 191);

        // Create the main panel with a background color
        mainPanel = new JPanel();
        mainPanel.setBackground(backgroundColor);
        mainPanel.setLayout(new BorderLayout());
    }

    private void createBossHealthBarPanel() {
        // Boss health state
        int maxHealth = 300;
        bossHealthBarLabel = new HealthBarLabel(maxHealth);

        // Create a label for the boss health title
        JLabel healthLabel = new JLabel("KAELTHARION THE ABYSSBOUND", SwingConstants.CENTER);
        healthLabel.setForeground(new Color(255, 215, 0)); // Gold/Amber color
        healthLabel.setFont(new Font("Serif", Font.BOLD, 26)); // bold serif font

        // Add padding and a new background color to the title
        JPanel bossTitlePanel  = new JPanel(new BorderLayout());
        bossTitlePanel .setBackground(new Color(0, 64, 128)); // Dark teal background for contrast
        bossTitlePanel .add(healthLabel, BorderLayout.CENTER);
        bossTitlePanel .setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0)); // Top padding: 20, Bottom padding: 10

        // Add the health bar below the title
        JPanel bossHealthPanel  = new JPanel(new BorderLayout());
        bossHealthPanel .setBackground(backgroundColor);
        bossHealthPanel .add(bossTitlePanel , BorderLayout.NORTH);
        bossHealthPanel .add(bossHealthBarLabel, BorderLayout.CENTER);

        // Add the health panel to the top of the frame
        frame.add(bossHealthPanel , BorderLayout.NORTH);
    }

    private void createPlayerHealthBarPanel()
    {
        playerHealthBarLabel = new HealthBarLabel(playerHealth);

        JLabel playerLabel = new JLabel("PLAYER HEALTH", SwingConstants.CENTER);
        playerLabel.setForeground(Color.WHITE);
        playerLabel.setFont(new Font("Serif", Font.BOLD, 20));

        JPanel playerHealthPanel = new JPanel(new BorderLayout());
        playerHealthPanel.setBackground(backgroundColor);
        playerHealthPanel.add(playerLabel, BorderLayout.NORTH);
        playerHealthPanel.add(playerHealthBarLabel, BorderLayout.CENTER);

        frame.add(playerHealthPanel, BorderLayout.SOUTH);
    }

    private void createGifPanel() {
        // Add the GIF to the northeast corner
        ImageIcon gifIcon = new ImageIcon(getClass().getResource("/visuals/waterDragon.gif"));
        JLabel gifLabel = new JLabel(gifIcon);

        gifLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel gifPanel = new JPanel(new BorderLayout());
        gifPanel.setPreferredSize(new Dimension(gifIcon.getIconWidth(), gifIcon.getIconHeight()));
        gifPanel.setMinimumSize(new Dimension(gifIcon.getIconWidth(), gifIcon.getIconHeight()));

        gifPanel.setOpaque(true); // Ensure the background matches
        gifPanel.add(gifLabel, BorderLayout.NORTH); // Position at the top
        mainPanel.add(gifPanel, BorderLayout.EAST); // Place in the northeast corner
    }

    private void checkGameOver()
    {
        if (bossHealthBarLabel.getCurrentHealth() <= 0) {
            JOptionPane.showMessageDialog(frame, "You Win!");
            System.exit(0);
        } else if (playerHealthBarLabel.getCurrentHealth() <= 0) {
            JOptionPane.showMessageDialog(frame, "You Lose!");
            System.exit(0);
        }
    }

    // ADJUST MONKEY SPRITES BASED ON ATTACK HERE
    private void createMonkeyPanel() {
        // Load original and attack monkey icons
        monkeyIconOriginal = resizeIcon(new ImageIcon(getClass().getResource("/visuals/Monkey8.png")));
        monkeyIconAttack = resizeIcon(new ImageIcon(getClass().getResource("/visuals/Monkey7.png")));

        // Initialize monkeyLabel with the original icon
        monkeyLabel = new JLabel(monkeyIconOriginal);

        // Create attack button
        JLabel attackButton = new JLabel("MASH!");
        attackButton.setForeground(Color.WHITE);
        attackButton.setBackground(new Color(139, 0, 0)); // Dark red color
        attackButton.setOpaque(true);
        attackButton.setFont(new Font("Serif", Font.BOLD, 20));
        attackButton.setHorizontalAlignment(SwingConstants.CENTER);
        attackButton.setVerticalAlignment(SwingConstants.CENTER);
        attackButton.setPreferredSize(new Dimension(150, 50));
        attackButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        attackButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Decrease the boss health
                bossHealthBarLabel.decreaseHealth(10);

                // Change monkey icon to attack image
                monkeyLabel.setIcon(monkeyIconAttack);

                // Create a timer to revert back after 1 second
                Timer timer = new Timer(500, evt -> monkeyLabel.setIcon(monkeyIconOriginal));
                timer.setRepeats(false);
                timer.start();

                checkGameOver();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                attackButton.setBackground(new Color(178, 34, 34)); // Lighter red on hover
            }

            @Override
            public void mouseExited(MouseEvent e) {
                attackButton.setBackground(new Color(139, 0, 0)); // Original dark red
            }
        });

        // Place the button in the bottom left
        JPanel containerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); // Align button to the left
        containerPanel.setBackground(backgroundColor); // Match background color
        containerPanel.add(monkeyLabel);
        containerPanel.add(attackButton);

        // Add the panel to the bottom of the main panel
        mainPanel.add(containerPanel, BorderLayout.SOUTH);
    }

    private void startPlayerHealthDecay()
    {
        Timer playerHealthDecayTimer = new Timer(1000, e -> {
            playerHealthBarLabel.decreaseHealth(2); // scrap.Player loses 2 health per second
            checkGameOver();
        });
        playerHealthDecayTimer.start();
    }

    private ImageIcon resizeIcon(ImageIcon icon)
    {
        Image img = icon.getImage();
        Image reSizedImg = img.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        return new ImageIcon(reSizedImg);
    }

    // Custom JLabel for the boss health bar
    static class HealthBarLabel extends JLabel {
        private int maxHealth;
        private int currentHealth;

        public HealthBarLabel(int maxHealth) {
            this.maxHealth = maxHealth;
            this.currentHealth = maxHealth;
            setPreferredSize(new Dimension(600, 30));
        }

        public void decreaseHealth(int amount) {
            currentHealth -= amount;
            if (currentHealth < 0) {
                currentHealth = 0;
            }
            repaint();
        }

        public int getCurrentHealth() {
            return currentHealth;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            g.setColor(new Color(0, 0, 139));
            g.fillRect(0, 0, getWidth(), getHeight());

            g.setColor(new Color(139, 0, 0));
            int healthWidth = (int) ((double) currentHealth / maxHealth * getWidth());
            g.fillRect(0, 0, healthWidth, getHeight());

            // Draw a black border around the health bar
            g.setColor(Color.BLACK);
            g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
        }
    }
}

