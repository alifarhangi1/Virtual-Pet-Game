package screens;

import managers.GameManager;
import misc.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class PlayerScoreScreen {
    private Player player;
    private GameManager gameManager;

    public PlayerScoreScreen(Player player)
    {
        this.player = player;
        this.gameManager = GameManager.getInstance(); // Get managers.GameManager instance
    }

    // Function to display the screen
    public void displayScreen()
    {
        // Create the frame
        JFrame frame = new JFrame("Player Score Screen");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        // Create the main panel
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2, 10, 10)); // 2x2 layout for alignment
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Create the score label and value
        JLabel scoreLabel = new JLabel("Player Score", SwingConstants.CENTER);
        JLabel scoreValue = new JLabel("0", SwingConstants.CENTER);
        scoreValue.setFont(new Font("Arial", Font.BOLD, 24));

        // Create the level label and value
        JLabel levelLabel = new JLabel("Play Time (seconds)", SwingConstants.CENTER);
        JLabel levelValue = new JLabel("0", SwingConstants.CENTER);
        levelValue.setFont(new Font("Arial", Font.BOLD, 24));

        // Create a sub-panel for the Back button with FlowLayout
        JPanel backButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        backButtonPanel.setPreferredSize(new Dimension(100,50));
        backButtonPanel.setOpaque(false); // Make it transparent if needed

        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.BOLD, 16));
        backButton.setPreferredSize(new Dimension(100, 50)); // Set desired size
        backButton.setBackground(Color.RED);
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        backButton.setOpaque(true);

        // Action listener for Back button
        backButton.addActionListener(e -> frame.dispose()); // Lambda version

        backButtonPanel.add(backButton); // Add the button to its sub-panel

        // Add components to the panel
        panel.add(scoreLabel);
        panel.add(levelLabel);
        panel.add(backButtonPanel);
        panel.add(scoreValue);
        panel.add(levelValue);

        // Add the panel to the frame
        frame.add(panel);

        // Timer to update the score and level dynamically
        Timer timer = new Timer(1000, new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                long playTime = gameManager.getPlayTime();
                Random rand = new Random();
                player.setScore(player.getScore() + 3 + rand.nextInt(3) + 1);
                scoreValue.setText(String.valueOf(player.getScore()));
                levelValue.setText(String.valueOf(playTime));
            }
        });

        timer.start(); // Start the timer
        frame.setVisible(true); // Show the frame
    }
}