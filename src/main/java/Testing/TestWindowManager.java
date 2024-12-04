package Testing;

import managers.WindowManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;

class TestWindowManager {

    private JFrame testWindow;

    @BeforeEach
    void setUp() {
        testWindow = new JFrame("Test Window");
    }

    @Test
    void testSetWindow_SetsWindowSuccessfully() {
        WindowManager.setWindow(testWindow);
        assertEquals(testWindow, WindowManager.getWindow(), "The set window should match the retrieved window");
    }

    @Test
    void testGetWindow_ReturnsNullInitially() {
        WindowManager.setWindow(null);
        assertNull(WindowManager.getWindow(), "Initially, the window should be null");
    }
}
