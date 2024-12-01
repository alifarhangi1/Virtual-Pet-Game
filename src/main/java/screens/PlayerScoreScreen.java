package screens;

import managers.GameManager;
import misc.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PlayerScoreScreen {
    private Player player;
    private GameManager gameManager;

    public PlayerScoreScreen(Player player) {
        this.player = player;
        this.gameManager = GameManager.getInstance(player); // Get managers.GameManager instance
    }

    // Function to display the screen
    public void displayScreen() {
        // Create the frame
        JFrame frame = new JFrame("scrap.Player Score Screen");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        // Create the main panel
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2, 10, 10)); // 2x2 layout for alignment
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Create the score label and value
        JLabel scoreLabel = new JLabel("scrap.Player Score", SwingConstants.CENTER);
        JLabel scoreValue = new JLabel("0", SwingConstants.CENTER);
        scoreValue.setFont(new Font("Arial", Font.BOLD, 24));

        // Create the level label and value
        JLabel levelLabel = new JLabel("Play Time (seconds)", SwingConstants.CENTER);
        JLabel levelValue = new JLabel("0", SwingConstants.CENTER);
        levelValue.setFont(new Font("Arial", Font.BOLD, 24));

        // Add components to the panel
        panel.add(scoreLabel);
        panel.add(levelLabel);
        panel.add(scoreValue);
        panel.add(levelValue);

        // Add the panel to the frame
        frame.add(panel);

        // Timer to update the score and level dynamically
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                // Update play time from managers.GameManager
                long playTime = gameManager.getPlayTime();
                scoreValue.setText(String.valueOf(player.getScore()));
                levelValue.setText(String.valueOf(playTime));
            }
        });

        timer.start(); // Start the timer
        frame.setVisible(true); // Show the frame
    }

    public static void main(String[] args)
    {
        // Mock scrap.Player instance
        Player player = new Player();
        player.setScore(0); // Set initial score

        // Create an instance of the game and display the screen
        PlayerScoreScreen gameScreen = new PlayerScoreScreen(player);
        gameScreen.displayScreen();
    }
}