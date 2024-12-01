import javax.swing.*;

/**
 * The MainMinigame class serves as the entry point for the game and handles the
 * initialization of the game window and the selected level.
 */
public class MainMinigame {

    /**
     * The main method starts the game by displaying the level selection screen.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        new LevelSelect();
    }

    /**
     * Constructs a MainMinigame instance and initializes the game window for the selected level.
     *
     * @param levelName the name of the level to load and start
     */
    public MainMinigame(String levelName) {
        // Create the game window
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Store the JFrame globally for access throughout the application
        WindowManager.setWindow(window);

        // Create the GamePanel with the specified level
        GamePanel gamePanel = new GamePanel(levelName);
        window.add(gamePanel);

        // Finalize the window setup
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        // Set up the game and start the game loop
        gamePanel.setUpGame();
        gamePanel.startGameThread();
    }
}

