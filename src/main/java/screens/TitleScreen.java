package screens;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import managers.AudioManager;
import managers.ScreenManager;

public class TitleScreen extends GameScreen {
    protected JPanel buttonPanel;
    protected JPanel buttonPanelContainer;
    private ImageIcon playButtonDefaultIcon;
    private ImageIcon playButtonHoverIcon;
    private ImageIcon tutorialButtonDefaultIcon;
    private ImageIcon tutorialButtonHoverIcon;
    private ImageIcon parentalControlButtonDefaultIcon;
    private ImageIcon parentalControlButtonHoverIcon;
    protected static final double BUTTON_WIDTH_SCALE = 0.19;
    protected static final double BUTTON_HEIGHT_SCALE = 0.13;
    protected static final int MIN_BUTTON_WIDTH = 190;
    protected static final int MIN_BUTTON_HEIGHT = 65;
    protected static final int VERTICAL_SPACING = 20;
    protected int originalButtonWidth;
    protected int originalButtonHeight;

    public TitleScreen() {
        initializeComponents();
        setupLayout();
        calculateAndSetInitialSizes();
        createAndAddButtons();
        finalizeSetup();
    }

    @Override
    protected void initializeComponents() {
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setOpaque(false);

        buttonPanelContainer = new JPanel(new GridBagLayout());
        buttonPanelContainer.setOpaque(false);
    }

    protected void setupLayout() {
        buttonPanelContainer.add(buttonPanel);
        contentPanel.add(buttonPanelContainer);
    }

    protected void calculateAndSetInitialSizes() {
        // Get initial dimensions
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int initialWidth = (int)(screenSize.width * 0.8);
        int initialHeight = (int)(screenSize.height * 0.8);

        // Get original button dimensions
        ImageIcon originalIcon = new ImageIcon(getClass().getResource("/visuals/playButtonDefault.png"));
        originalButtonWidth = originalIcon.getIconWidth();
        originalButtonHeight = originalIcon.getIconHeight();

        // Calculate initial button sizes
        int[] buttonDimensions = calculateButtonDimensions(initialWidth, initialHeight);
        initializeButtonImages(buttonDimensions[0], buttonDimensions[1]);
    }

    protected void createAndAddButtons() {
        JLabel playButton = createPlayButton();
        JLabel tutorialButton = createTutorialButton();
        JLabel parentalControlButton = createParentalControlButton();

        // Get current button dimensions from icons
        Dimension buttonSize = new Dimension(
                playButtonDefaultIcon.getIconWidth(),
                playButtonDefaultIcon.getIconHeight()
        );

        // Set sizes for all buttons
        setButtonSize(playButton, buttonSize);
        setButtonSize(tutorialButton, buttonSize);
        setButtonSize(parentalControlButton, buttonSize);

        // Add buttons with spacing
        buttonPanel.add(playButton);
        buttonPanel.add(Box.createVerticalStrut(VERTICAL_SPACING));
        buttonPanel.add(tutorialButton);
        buttonPanel.add(Box.createVerticalStrut(VERTICAL_SPACING));
        buttonPanel.add(parentalControlButton);
    }

    private void finalizeSetup() {
        revalidate();
        repaint();
    }

    protected int[] calculateButtonDimensions(int width, int height) {
        int availableHeight = height * 2/3;
        int availableWidth = width * 2/3;

        double maxButtonWidth = Math.max(availableWidth * BUTTON_WIDTH_SCALE, MIN_BUTTON_WIDTH);
        double maxButtonHeight = Math.max((availableHeight - 2 * VERTICAL_SPACING) / 3, MIN_BUTTON_HEIGHT);

        double widthScaleFactor = maxButtonWidth / originalButtonWidth;
        double heightScaleFactor = maxButtonHeight / originalButtonHeight;
        double buttonScaleFactor = Math.min(widthScaleFactor, heightScaleFactor) * 1.2;

        int finalWidth = Math.max((int)(originalButtonWidth * buttonScaleFactor), MIN_BUTTON_WIDTH);
        int finalHeight = Math.max((int)(originalButtonHeight * buttonScaleFactor), MIN_BUTTON_HEIGHT);

        return new int[]{finalWidth, finalHeight};
    }

    private void initializeButtonImages(int width, int height) {
        playButtonDefaultIcon = createScaledIcon("/visuals/playButtonDefault.png", width, height);
        playButtonHoverIcon = createScaledIcon("/visuals/playButtonHover.png", width, height);
        tutorialButtonDefaultIcon = createScaledIcon("/visuals/tutorialButtonDefault.png", width, height);
        tutorialButtonHoverIcon = createScaledIcon("/visuals/tutorialButtonHover.png", width, height);
        parentalControlButtonDefaultIcon = createScaledIcon("/visuals/parentalControlButtonDefault.png", width, height);
        parentalControlButtonHoverIcon = createScaledIcon("/visuals/parentalControlButtonHover.png", width, height);
    }


    private JLabel createPlayButton() {
        JLabel playButton = new JLabel(playButtonDefaultIcon);
        playButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        addButtonListeners(playButton, playButtonHoverIcon, playButtonDefaultIcon);
        playButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                screenManager.showScreen("save");
            }
        });
        return playButton;
    }

    private JLabel createTutorialButton() {
        JLabel tutorialButton = new JLabel(tutorialButtonDefaultIcon);
        tutorialButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        addButtonListeners(tutorialButton, tutorialButtonHoverIcon, tutorialButtonDefaultIcon);
        return tutorialButton;
    }

    private JLabel createParentalControlButton() {
        JLabel parentalControlButton = new JLabel(parentalControlButtonDefaultIcon);
        parentalControlButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        addButtonListeners(parentalControlButton, parentalControlButtonHoverIcon, parentalControlButtonDefaultIcon);
        parentalControlButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                audioManager.playButtonClickSound();
                if (databaseManager.getParentalPasword().isEmpty()) {
                    String password = JOptionPane.showInputDialog("Create parental password: ");
                    databaseManager.updateParentalPassword(password);
                } else {
                    String password = JOptionPane.showInputDialog("Input parental password: ");
                    if (password.equals(databaseManager.getParentalPasword())) {
                    } else {
                        JOptionPane.showMessageDialog(null, "Wrong password, try again.");
                    }
                }
            }
        });
        return parentalControlButton;
    }

    protected void addButtonListeners(final JLabel button, final ImageIcon hoverIcon, final ImageIcon defaultIcon) {
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setIcon(hoverIcon);
                audioManager.playHoverSound();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setIcon(defaultIcon);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                audioManager.playButtonClickSound();
            }
        });
    }

    @Override
    protected void onResize(int width, int height) {
        int[] newDimensions = calculateButtonDimensions(width, height);
        int newWidth = newDimensions[0];
        int newHeight = newDimensions[1];

        // Resize button images
        initializeButtonImages(newWidth, newHeight);

        // Update button sizes
        Dimension newSize = new Dimension(newWidth, newHeight);
        for (Component comp : buttonPanel.getComponents()) {
            if (comp instanceof JLabel) {
                setButtonSize((JLabel)comp, newSize);
            }
        }

        // Update container size
        int totalHeight = (newHeight * 3) + (VERTICAL_SPACING * 2);
        buttonPanelContainer.setPreferredSize(new Dimension(width, totalHeight));

        // Revalidate components
        buttonPanel.revalidate();
        buttonPanelContainer.revalidate();
        contentPanel.revalidate();
    }
}