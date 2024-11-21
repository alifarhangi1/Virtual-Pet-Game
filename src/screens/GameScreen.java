// GameScreen.java
package screens;

import managers.AudioManager;
import managers.ScreenManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameScreen extends JPanel {
    public AudioManager audioManager;
    public ScreenManager screenManager;
    protected final JPanel contentPanel;
    protected int originalBackButtonWidth;
    protected int originalBackButtonHeight;
    protected static final double BACK_BUTTON_WIDTH_SCALE = 0.12;
    protected static final double BACK_BUTTON_HEIGHT_SCALE = 0.11;
    protected static final int MIN_BACK_BUTTON_WIDTH = 140;
    protected static final int MIN_BACK_BUTTON_HEIGHT = 40;
    private final JPanel logoPanel;
    private final JPanel bottomPanel;
    private final JPanel topPanel;
    private final JLabel logoLabel;
    private final JLabel bottomLeftLabel;
    private final JLabel bottomRightLabel;
    private final ImageIcon backgroundIcon;
    private final ImageIcon logo;
    private final ImageIcon exit;

    // Configuration constants
    private static final double TEXT_BASE_SCALE = 0.02;
    private static final int MIN_FONT_SIZE = 10;
    private static final double LOGO_SCALE = 0.4;

    protected GameScreen() {
        setLayout(new BorderLayout());
        audioManager = AudioManager.getInstance();
        screenManager = ScreenManager.getInstance();

        // Load resources
        backgroundIcon = new ImageIcon("src/assets/visuals/background.gif");
        logo = new ImageIcon("src/assets/visuals/logo.png");
        exit = new ImageIcon("src/assets/visuals/exitButtonDefault.png");

        // Initialize with background
        setBackground(new Color(0, 0, 0, 0));

        // Logo panel setup
        logoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        logoPanel.setOpaque(false);
        logoLabel = new JLabel();
        logoPanel.add(logoLabel);

        // Content panel for child screens
        contentPanel = new JPanel();
        contentPanel.setOpaque(false);

        // Bottom panel setup
        bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setOpaque(false);

        // Top panel setup
        topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);

        // Create bottom labels
        bottomLeftLabel = createLabel("TEAM 50 CS 2212 FALL 2024 WESTERN UNIVERSITY", true);
        bottomRightLabel = createLabel(
                "Adam Yassine, Ali Farhangi, Luca Duarte, Robin (Sangjae) Lee, Yazan Abushirbi", false);

        // Bottom panel layout
        JPanel bottomLeftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        bottomLeftPanel.setOpaque(false);
        bottomLeftPanel.add(bottomLeftLabel);

        JPanel bottomRightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        bottomRightPanel.setOpaque(false);
        bottomRightPanel.add(bottomRightLabel);

        bottomPanel.add(bottomLeftPanel, BorderLayout.WEST);
        bottomPanel.add(bottomRightPanel, BorderLayout.EAST);

        // Arrange panels
        add(logoPanel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        // Add resize listener
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                resizeComponents();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundIcon.getImage(), 0, 0, getWidth(), getHeight(), this);
    }

    protected void initializeBackButtonDimensions() {
        ImageIcon originalBackButton = new ImageIcon("src/assets/visuals/woodButtonDefault.png");
        originalBackButtonWidth = originalBackButton.getIconWidth();
        originalBackButtonHeight = originalBackButton.getIconHeight();
    }

    protected int[] resizeBackButton() {
        // Calculate back button dimensions
        double backButtonWidthScaleFactor = Math.max(getWidth() * BACK_BUTTON_WIDTH_SCALE / originalBackButtonWidth,
                MIN_BACK_BUTTON_WIDTH / (double)originalBackButtonWidth);
        double backButtonHeightScaleFactor = Math.max(getHeight() * BACK_BUTTON_HEIGHT_SCALE / originalBackButtonHeight,
                MIN_BACK_BUTTON_HEIGHT / (double)originalBackButtonHeight);
        double backButtonScaleFactor = Math.min(backButtonWidthScaleFactor, backButtonHeightScaleFactor);

        int newBackButtonWidth = (int)(originalBackButtonWidth * backButtonScaleFactor);
        int newBackButtonHeight = (int)(originalBackButtonHeight * backButtonScaleFactor);

        int[] size = {newBackButtonWidth, newBackButtonHeight};

        return size;
    }

    protected JLabel createBackButton(String screen) {
        JLabel backButton = new JLabel();
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        ImageIcon defaultIcon = new ImageIcon("src/assets/visuals/woodButtonDefault.png");
        backButton.setIcon(defaultIcon);
        backButton.putClientProperty("defaultIcon", defaultIcon);

        backButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                JLabel button = (JLabel) e.getComponent();
                Dimension currentSize = button.getSize();
                // Create scaled hover icon based on current button size
                ImageIcon scaledHoverIcon = createScaledIcon(
                        "src/assets/visuals/woodButtonHover.png",
                        currentSize.width,
                        currentSize.height
                );
                button.setIcon(scaledHoverIcon);
                audioManager.playHoverSound();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                JLabel button = (JLabel) e.getComponent();
                ImageIcon defaultIcon = (ImageIcon) button.getClientProperty("defaultIcon");
                button.setIcon(defaultIcon);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                audioManager.playButtonClickSound();
                screenManager.showScreen(screen);
            }
        });

        return backButton;
    }

    protected ImageIcon createScaledIcon(String path, int width, int height) {
        return new ImageIcon(new ImageIcon(path).getImage()
                .getScaledInstance(width, height, Image.SCALE_SMOOTH));
    }

    private JLabel createLabel(String text, boolean isLeftLabel) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setOpaque(false);
        label.setForeground(new Color(255, 215, 0));
        label.setFont(new Font("Monospaced",
                Font.BOLD | (isLeftLabel ? Font.ITALIC : Font.PLAIN),
                MIN_FONT_SIZE));
        return label;
    }

    protected void setButtonSize(JLabel button, Dimension size) {
        button.setPreferredSize(size);
        button.setMinimumSize(size);
        button.setMaximumSize(size);
    }

    private void resizeComponents() {
        int windowWidth = getWidth();
        int windowHeight = getHeight();

        // Resize logo
        Image originalImage = logo.getImage();
        int originalWidth = logo.getIconWidth();
        int originalHeight = logo.getIconHeight();

        double scaleFactor = Math.min(
                (windowWidth * LOGO_SCALE) / originalWidth,
                (windowHeight * LOGO_SCALE) / originalHeight
        );

        int newLogoWidth = (int)(originalWidth * scaleFactor);
        int newLogoHeight = (int)(originalHeight * scaleFactor);

        Image resizedLogo = originalImage.getScaledInstance(
                newLogoWidth, newLogoHeight, Image.SCALE_SMOOTH);
        logoLabel.setIcon(new ImageIcon(resizedLogo));

        // Resize text
        int leftLabelSize = Math.max((int)(windowHeight * TEXT_BASE_SCALE * 1.2), MIN_FONT_SIZE);
        int rightLabelSize = Math.max((int)(windowHeight * TEXT_BASE_SCALE), MIN_FONT_SIZE);

        bottomLeftLabel.setFont(new Font("Monospaced", Font.BOLD | Font.ITALIC, leftLabelSize));
        bottomRightLabel.setFont(new Font("Monospaced", Font.BOLD, rightLabelSize));

        // Call onResize for child-specific resizing
        onResize(windowWidth, windowHeight);

        revalidate();
        repaint();
    }

    // Optional methods with default empty implementations
    protected void onResize(int width, int height) {
        // Default empty implementation
        // Child classes can override this if they need custom resize behavior
    }

    public void onShow() {
        // Default empty implementation
        // Child classes can override this if they need custom show behavior
    }

    public void cleanup() {
        // Default empty implementation
        // Child classes can override this if they need custom cleanup behavior
    }

    protected void initializeComponents() {
        // Default empty implementation
        // Child classes can override this if they need custom cleanup behavior
    }

}