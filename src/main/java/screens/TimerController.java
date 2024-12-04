package screens;

import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;

/**
 * The {@code TimerController} class is responsible for managing a timer that enforces a time limit
 * within a graphical application. When the timer expires, it prompts the user with options to
 * either exit the program or return to the main menu.
 *
 * Key features include:
 *   Starting a timer with a specified time limit in minutes.
 *   Displaying a notification dialog when the timer expires.
 *   Switching back to the main game screen or exiting the program upon user selection.
 *   Canceling an active timer when needed.
 *
 * Usage Example:
 * <pre>
 * {@code
 * JFrame parentFrame = new JFrame();
 * TimerController timerController = new TimerController(10, parentFrame); // 10-minute timer
 * }
 *
 * @author Adam Yassine
 * @version 1.0
 */


public class TimerController {
    private Timer timer;
    private int timeLimit; // Time limit in minutes
    private JFrame parentFrame;

    /**
     * Constructs a {@code TimerController} with a specified time limit and parent frame.
     * Immediately starts the timer.
     *
     * @param timeLimit   The time limit in minutes.
     * @param parentFrame The parent JFrame for displaying notifications.
     */
    public TimerController(int timeLimit, JFrame parentFrame) {
        this.timeLimit = timeLimit;
        this.parentFrame = parentFrame;
        startTimer();
    }

    /**
     * Starts the timer with the specified time limit. When the timer expires,
     * it triggers a notification dialog for the user.
     */
    private void startTimer() {
        int totalMilliseconds = timeLimit * 60 * 1000; // Convert minutes to milliseconds

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                showNotification();
            }
        }, totalMilliseconds);
    }

    /**
     * Displays a notification when the timer expires. Provides the user with two options:
     *   Exit the application.
     *   Return to the main menu.
     */
    private void showNotification() {
        timer.cancel();

        int choice = JOptionPane.showOptionDialog(
                parentFrame,
                "Time limit is over. What would you like to do?",
                "Time's Up!",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                new String[]{"Exit Program", "Return to Main Menu"},
                "Exit Program"
        );

        if (choice == JOptionPane.YES_OPTION) {
            System.exit(0); // Exit the application
        } else if (choice == JOptionPane.NO_OPTION) {
            switchToGameScreen();
        }
    }

    /**
     * Cancels the currently running timer, if active.
     * Ensures that no further tasks are executed by the timer.
     */
    public void cancelTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    /**
     * Switches the application back to the main game screen.
     * Replaces the current content of the parent frame with the main menu screen.
     */
    private void switchToGameScreen() {
        TitleScreen TitleScreen = new TitleScreen();
        parentFrame.getContentPane().removeAll();
        parentFrame.getContentPane().add(TitleScreen);
        parentFrame.revalidate();
        parentFrame.repaint();
    }
}
