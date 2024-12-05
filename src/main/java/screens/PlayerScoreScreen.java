package screens;

import managers.GameManager;
import misc.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Manages and displays real-time player statistics including score and playtime.
 * Implements dynamic updates through swing timer functionality.
 *
 * @author Robin Lee
 * @version 1.0
 */
public class PlayerScoreScreen
{
    private Player player;
    private GameManager gameManager;

    /**
     * Initializes score screen with player data and game manager connection.
     *
     * @param player The player whose stats will be displayed
     * @throws IllegalArgumentException if player instance is null
     */
    public PlayerScoreScreen(Player player) {
        this.player = player;
        this.gameManager = null; // Get managers.GameManager instance
    }

    /**
     * Function to displayScreen
     */
    public void displayScreen() {
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
}