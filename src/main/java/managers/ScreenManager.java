package managers;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Class responsible for managing the different screens of the game and their sizing
 *
 * @author Luca Duarte
 */
public class ScreenManager {
    private static ScreenManager instance;
    /** main frame used for the game's window */
    private final JFrame mainFrame;
    /** card layout setup for switching between different screens */
    private final CardLayout cardLayout;
    /** main panel used for the different panels */
    private final JPanel mainPanel;
    /** list of all the screens */
    private final Map<String, JPanel> screens;

    /**
     * ScreenManager constructor, creates the main frame used by the game
     */
    private ScreenManager() {
        mainFrame = new JFrame("misc.Pet Quest");
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        screens = new HashMap<>();

        mainFrame.setMinimumSize(new Dimension(800, 600));
        mainFrame.setPreferredSize(new Dimension(1024, 768));
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.add(mainPanel);
    }

    /**
     * @return class instance
     */
    public static ScreenManager getInstance() {
        if (instance == null) {
            instance = new ScreenManager();
        }
        return instance;
    }

    /**
     * Adds new screen to the list of screens
     *
     * @param name screen name
     * @param screen panel object representing the screen
     */
    public void addScreen(String name, JPanel screen) {
        if (!screens.containsKey(name)) {
            screens.put(name, screen);
            mainPanel.add(screen, name);
        }
    }

    /**
     * Checks if given screen already exists
     *
     * @param name name of the screen
     * @return true if screen exists, false if not
     */
    public boolean hasScreen(String name) {
        if (screens.containsKey(name)) {
            return true;
        }
        return false;
    }

    /**
     * Displays a screen in the game
     *
     * @param name name of the screen
     */
    public void showScreen(String name) {
        cardLayout.show(mainPanel, name);
        if (screens.containsKey(name)) {
            screens.get(name).show();
        }
    }

    /**
     * Displays main frame of the game
     */
    public void showMainFrame() {
        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }

    /**
     * Deletes screen from card layout
     *
     * @param name screen to delete
     */
    public void deleteScreen(String name) {
        screens.remove(name);
    }

}