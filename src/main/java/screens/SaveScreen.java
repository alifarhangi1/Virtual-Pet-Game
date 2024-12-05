package screens;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;

/**
 * A specialized screen for handling game save functionality.
 * Extends TitleScreen to maintain consistent UI theming while providing
 * save-specific features.
 *
 * @version 1.0
 * @author Luca Duarte
 */
public class SaveScreen extends TitleScreen
{
    /**
     * Constructs a new save screen with customized button layout.
     * Initializes back button dimensions and replaces default buttons
     * with save-specific options.
     */
    public SaveScreen()
    {
        super();
        initializeBackButtonDimensions();
        replaceButtons();
    }

    /**
     * Reconstructs the button layout with specific spacing and positioning.
     * Creates a centered arrangement of game control buttons with consistent padding.
     */
    private void replaceButtons() {
        // Clear existing buttons from parent
        buttonPanel.removeAll();

        // Create game buttons row
        JPanel buttonRowPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonRowPanel.setOpaque(false);
        buttonRowPanel.add(createGameButton("newGameButton"));
        buttonRowPanel.add(createGameButton("loadGameButton"));

        // Create back button panel
        JPanel backButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        backButtonPanel.setOpaque(false);
        backButtonPanel.add(createBackButton("title"));

        // Add components with spacing
        buttonPanel.add(buttonRowPanel);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        buttonPanel.add(backButtonPanel);

        buttonPanel.setBorder(BorderFactory.createEmptyBorder(40, 0, 0, 0)); // Top padding: 20px
    }

    /**
     * Creates an interactive game button with hover effects and sound feedback.
     *
     * @param buttonName Identifier for the button resources and functionality
     * @return Configured JLabel acting as a button
     */
    private JLabel createGameButton(final String buttonName) {
        JLabel button = new JLabel();
        button.putClientProperty("buttonName", buttonName);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Initial sizing will be handled in onResize
        ImageIcon defaultIcon = new ImageIcon(getClass().getResource("/visuals/" + buttonName + "Default.png"));
        ImageIcon hoverIcon = new ImageIcon(getClass().getResource("/visuals/" + buttonName + "Hover.png"));

        button.setIcon(defaultIcon);
        button.putClientProperty("defaultIcon", defaultIcon);
        button.putClientProperty("hoverIcon", hoverIcon);

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                JLabel button = (JLabel) e.getComponent();
                ImageIcon hoverIcon = (ImageIcon) button.getClientProperty("hoverIcon");
                Dimension currentSize = button.getSize();
                ImageIcon scaledHoverIcon = createScaledIcon(
                        "/visuals/" + buttonName + "Hover.png",
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
        });

        if (Objects.equals(buttonName, "newGameButton")) {
            button.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    audioManager.playButtonClickSound();
                    screenManager.showScreen("newgame");
                }
            });
        } else {
            button.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    audioManager.playButtonClickSound();
                    screenManager.showScreen("loadgame");
                }
            });
        }

        return button;
    }

    /**
     * Handles dynamic resizing of all UI components while maintaining aspect ratios.
     * Called automatically when the window size changes.
     *
     * @param width The new width of the window
     * @param height The new height of the window
     */
    @Override
    protected void onResize(int width, int height)
    {
        // First resize game buttons using parent's calculations
        int[] buttonDimensions = calculateButtonDimensions(width, height);
        int newButtonWidth = buttonDimensions[0];
        int newButtonHeight = buttonDimensions[1];

        int[] backButtonDimensions = resizeBackButton();

        // Update game buttons
        JPanel buttonRowPanel = (JPanel)buttonPanel.getComponent(0);
        for (Component comp : buttonRowPanel.getComponents()) {
            if (comp instanceof JLabel) {
                JLabel button = (JLabel)comp;
                String buttonName = (String)button.getClientProperty("buttonName");
                if (buttonName != null) {
                    ImageIcon defaultIcon = createScaledIcon("/visuals/" + buttonName + "Default.png",
                            newButtonWidth, newButtonHeight);
                    button.setIcon(defaultIcon);
                    button.putClientProperty("defaultIcon", defaultIcon);
                    setButtonSize(button, new Dimension(newButtonWidth, newButtonHeight));
                }
            }
        }

        // Update back button
        JPanel backButtonPanel = (JPanel)buttonPanel.getComponent(2);
        if (backButtonPanel.getComponentCount() > 0) {
            JLabel backButton = (JLabel)backButtonPanel.getComponent(0);
            ImageIcon defaultIcon = createScaledIcon("/visuals/woodButtonDefault.png",
                    backButtonDimensions[0], backButtonDimensions[1]);
            backButton.setIcon(defaultIcon);
            backButton.putClientProperty("defaultIcon", defaultIcon);
            setButtonSize(backButton, new Dimension(backButtonDimensions[0], backButtonDimensions[1]));
        }

        revalidate();
        repaint();
    }
}