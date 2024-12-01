package managers;

import javax.swing.*;

/**
 * The managers.WindowManager class provides a centralized way to manage the main game window.
 * It allows storing and retrieving a reference to the current JFrame.
 */
public class WindowManager {

    /** The main game window. */
    private static JFrame window;

    /**
     * Sets the main game window.
     *
     * @param window the JFrame to be managed
     */
    public static void setWindow(JFrame window) {
        WindowManager.window = window;
    }

    /**
     * Retrieves the main game window.
     *
     * @return the managed JFrame
     */
    public static JFrame getWindow() {
        return window;
    }
}
