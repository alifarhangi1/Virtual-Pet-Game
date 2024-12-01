package screens;

import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;

public class TimerController {
    private Timer timer;
    private int timeLimit; // Time limit in minutes
    private JFrame parentFrame;

    public TimerController(int timeLimit, JFrame parentFrame) {
        this.timeLimit = timeLimit;
        this.parentFrame = parentFrame;
        startTimer();
    }

    /**
     * Starts the timer with the specified time limit.
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
     * Displays a notification when the timer runs out, giving the user options.
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
     * Cancels the current timer if it's running.
     */
    public void cancelTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    /**
     * Switches to the main game screen.
     */
    private void switchToGameScreen() {
        TitleScreen TitleScreen = new TitleScreen();
        parentFrame.getContentPane().removeAll();
        parentFrame.getContentPane().add(TitleScreen);
        parentFrame.revalidate();
        parentFrame.repaint();
    }
}
