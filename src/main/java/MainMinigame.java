

import javax.swing.*;

public class MainMinigame {
    public MainMinigame(String levelName) {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        WindowManager.setWindow(window); // Store the JFrame globally

        GamePanel gamePanel = new GamePanel(levelName);
        window.add(gamePanel);

        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.setUpGame();
        gamePanel.startGameThread();
    }
}

