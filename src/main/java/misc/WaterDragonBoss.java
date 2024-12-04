package misc;


import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.URL;

public class WaterDragonBoss implements KeyListener {

    // Class-level variables
    private JFrame frame;
    private JPanel mainPanel;
    private Color backgroundColor;
    private HealthBarLabel bossHealthBarLabel;
    private HealthBarLabel playerHealthBarLabel;
    private JLabel monkeyLabel;
    public JLabel gifLabel;
    private ImageIcon gifIcon;
    private ImageIcon monkeyIconOriginal;
    private ImageIcon monkeyIconAttack;
    private int playerHealth;
    Timer playerHealthDecayTimer;
    MusicPlayerMinigame musicPlayer = new MusicPlayerMinigame();
    Graphics2D g2D;
    private DrawingPanel drawingPanel;
    Clip bgmClip;


    WaterDragonBoss() {

        playBackgroundMusic("/audio/dragonduel.wav");
        playerHealth = 100;
        initializeFrame();
        initializeMainPanel();
        createBossHealthBarPanel();
        createPlayerHealthBarPanel();
        createGifPanel();
        createMonkeyPanel();

        frame.addKeyListener(this);

        frame.add(mainPanel, BorderLayout.CENTER);
        frame.setVisible(true);

        startPlayerHealthDecay();
    }

    private void initializeFrame() {
        // Get the default screen device
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();

        // Create a new JFrame
        frame = new JFrame("Boss Health Bar");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setUndecorated(true); // Remove title bar for full-screen
        frame.setLayout(new BorderLayout());

        // Set the JFrame to full-screen mode
        gd.setFullScreenWindow(frame);

        // Ensure the layout matches the full-screen dimensions
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize(screenSize);
    }

    private void initializeMainPanel() {
        // Set the predominant background color
        backgroundColor = new Color(31, 95, 191);

        // Create the main panel with a background color
        mainPanel = new JPanel();
        mainPanel.setBackground(backgroundColor);
        mainPanel.setLayout(new BorderLayout());

        drawingPanel = new DrawingPanel();
        drawingPanel.setBackground(backgroundColor); // Match background
        mainPanel.add(drawingPanel, BorderLayout.CENTER);
    }

    private void createBossHealthBarPanel() {
        // Boss health state
        int maxHealth = 5000;
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
        gifIcon = new ImageIcon(getClass().getResource("/visuals/waterDragon.gif"));
        gifLabel = new JLabel(gifIcon);

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
            playerHealthDecayTimer.stop();
//            musicPlayer.stopSound();
            musicPlayer.stopMusic();
            drawingPanel.setGameStatus(true, false); // Game won
            playSound("/audio/levelup.wav");
        } else if (playerHealthBarLabel.getCurrentHealth() <= 0) {
            playerHealthDecayTimer.stop();
//            musicPlayer.stopSound();
            musicPlayer.stopMusic();
            drawingPanel.setGameStatus(false, true); // Game lost
            playSound("/audio/gameover.wav");
        }
    }

    private void playSound(String musicFilePath) {
        try {
            // Load the music file from the provided file path
            URL musicFile = getClass().getResource(musicFilePath);

            // Create an audio stream to read the music file
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(musicFile);

            // Obtain a Clip to play the audio
            bgmClip = AudioSystem.getClip();

            // Open the audio stream in the Clip
            bgmClip.open(audioStream);

            bgmClip.start(); // Start playing the background music
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            // Log any exceptions that occur during music playback
            e.printStackTrace();
        }
    }

    private void playBackgroundMusic(String musicFilePath) {
        try {
            // Load the music file from the provided file path
            URL musicFile = getClass().getResource(musicFilePath);

            // Create an audio stream to read the music file
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(musicFile);

            // Obtain a Clip to play the audio
            bgmClip = AudioSystem.getClip();

            // Open the audio stream in the Clip
            bgmClip.open(audioStream);

            // Set the Clip to loop continuously and start playback
            bgmClip.loop(Clip.LOOP_CONTINUOUSLY);
            bgmClip.start(); // Start playing the background music
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            // Log any exceptions that occur during music playback
            e.printStackTrace();
        }
    }

    public void stopMusic() {
        if (bgmClip != null) {
            bgmClip.stop();
            bgmClip.close();
            bgmClip = null; // Clear the reference to free resources
        }
    }



    // ADJUST MONKEY SPRITES BASED ON ATTACK HERE
    private void createMonkeyPanel() {
        // Load original and attack monkey icons
        monkeyIconOriginal = resizeIcon(new ImageIcon(getClass().getResource("/visuals/Monkey(8).png")));
        monkeyIconAttack = resizeIcon(new ImageIcon(getClass().getResource("/visuals/Monkey(7).png")));

        // Initialize monkeyLabel with the original icon
        monkeyLabel = new JLabel(monkeyIconOriginal);

        // Create attack button
        JLabel attackButton = new JLabel("ATTACK!");
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

                if(playerHealth != 0){
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
        playerHealthDecayTimer = new Timer(5000, e -> {
            playSound("/audio/dragongrowl.wav");
            playerHealthBarLabel.decreaseHealth(5);
            playerHealth = playerHealth - 5;
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

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
            playerHealthDecayTimer.stop();
            frame.dispose();
//            musicPlayer.stopSound();
            stopMusic();
            new LevelSelect();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

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
//            WaterDragonBoss waterDragonBoss = new WaterDragonBoss();
            if (currentHealth < 0) {
                currentHealth = 0;
//                waterDragonBoss.gifIcon = null;
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

class DrawingPanel extends JPanel {
    private boolean gameWon;
    private boolean gameLost;

    public void setGameStatus(boolean gameWon, boolean gameLost) {
        this.gameWon = gameWon;
        this.gameLost = gameLost;
        repaint(); // Trigger a repaint to update the drawing
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;

        // Victory screen
        if (gameWon) {
            g2D.setFont(new Font("Papyrus", Font.PLAIN, 40));
            g2D.setColor(Color.WHITE);

            String text = "You Win!";
            int textLength = (int) g2D.getFontMetrics().getStringBounds(text, g2D).getWidth();
            int x = getWidth() / 2 - textLength / 2;
            int y = getHeight() / 2;

            String text2 = "Press 'esc' to return to menu";
            int textLength2 = (int) g2D.getFontMetrics().getStringBounds(text2, g2D).getWidth();
            int x2 = getWidth() / 2 - textLength2 / 2;
            int y2 = getHeight() / 2 + 50;

            g2D.drawString(text, x, y);
            g2D.drawString(text2, x2, y2);
        } else if (gameLost) {
            g2D.setFont(new Font("Papyrus", Font.PLAIN, 40));
            g2D.setColor(Color.WHITE);

            String text = "You Lost! Try Again!";
            int textLength = (int) g2D.getFontMetrics().getStringBounds(text, g2D).getWidth();
            int x = getWidth() / 2 - textLength / 2;
            int y = getHeight() / 2;

            String text2 = "Press 'esc' to return to menu";
            int textLength2 = (int) g2D.getFontMetrics().getStringBounds(text2, g2D).getWidth();
            int x2 = getWidth() / 2 - textLength2 / 2;
            int y2 = getHeight() / 2 + 50;

            g2D.drawString(text, x, y);
            g2D.drawString(text2, x2, y2);
        }
    }
}
