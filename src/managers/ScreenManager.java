package managers;

import screens.GameScreen;
import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class ScreenManager {
    private static ScreenManager instance;
    private final JFrame mainFrame;
    private final CardLayout cardLayout;
    private final JPanel mainPanel;
    private final Map<String, GameScreen> screens;

    private ScreenManager() {
        mainFrame = new JFrame("Pet Quest");
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        screens = new HashMap<>();

        mainFrame.setMinimumSize(new Dimension(800, 600));
        mainFrame.setPreferredSize(new Dimension(1024, 768));
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.add(mainPanel);
    }

    public static ScreenManager getInstance() {
        if (instance == null) {
            instance = new ScreenManager();
        }
        return instance;
    }

    public void addScreen(String name, GameScreen screen) {
        screens.put(name, screen);
        mainPanel.add(screen, name);
    }

    public void showScreen(String name) {
        cardLayout.show(mainPanel, name);
        if (screens.containsKey(name)) {
            screens.get(name).onShow();
        }
    }

    public void showMainFrame() {
        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }

    public void cleanup() {
        AudioManager.getInstance().cleanup();
        screens.values().forEach(GameScreen::cleanup);
    }
}