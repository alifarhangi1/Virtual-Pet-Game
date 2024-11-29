
import javax.swing.*;

public class WindowManager {
    private static JFrame window;

    public static void setWindow(JFrame window) {
        WindowManager.window = window;
    }

    public static JFrame getWindow() {
        return window;
    }
}
