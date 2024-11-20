package screens;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;

public class SaveScreen extends TitleScreen {
    private static final double BACK_BUTTON_WIDTH_SCALE = 0.12;
    private static final double BACK_BUTTON_HEIGHT_SCALE = 0.11;
    private static final int MIN_BACK_BUTTON_WIDTH = 180;
    private static final int MIN_BACK_BUTTON_HEIGHT = 60;
    private int originalBackButtonWidth;
    private int originalBackButtonHeight;

    public SaveScreen() {
        super();
        initializeBackButtonDimensions();
        replaceButtons();
    }

    private void initializeBackButtonDimensions() {
        ImageIcon originalBackButton = new ImageIcon("src/assets/visuals/woodButtonDefault.png");
        originalBackButtonWidth = originalBackButton.getIconWidth();
        originalBackButtonHeight = originalBackButton.getIconHeight();
    }

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
        backButtonPanel.add(createBackButton());

        // Add components with spacing
        buttonPanel.add(buttonRowPanel);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        buttonPanel.add(backButtonPanel);

        buttonPanel.setBorder(BorderFactory.createEmptyBorder(40, 0, 0, 0)); // Top padding: 20px
    }

    private JLabel createGameButton(String buttonName) {
        JLabel button = new JLabel();
        button.putClientProperty("buttonName", buttonName);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Initial sizing will be handled in onResize
        ImageIcon defaultIcon = new ImageIcon("src/assets/visuals/" + buttonName + "Default.png");
        ImageIcon hoverIcon = new ImageIcon("src/assets/visuals/" + buttonName + "Hover.png");

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
                        "src/assets/visuals/" + buttonName + "Hover.png",
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
        }

        return button;
    }

    @Override
    protected void onResize(int width, int height) {
        // First resize game buttons using parent's calculations
        int[] buttonDimensions = calculateButtonDimensions(width, height);
        int newButtonWidth = buttonDimensions[0];
        int newButtonHeight = buttonDimensions[1];

        // Calculate back button dimensions
        double backButtonWidthScaleFactor = Math.max(width * BACK_BUTTON_WIDTH_SCALE / originalBackButtonWidth,
                MIN_BACK_BUTTON_WIDTH / (double)originalBackButtonWidth);
        double backButtonHeightScaleFactor = Math.max(height * BACK_BUTTON_HEIGHT_SCALE / originalBackButtonHeight,
                MIN_BACK_BUTTON_HEIGHT / (double)originalBackButtonHeight);
        double backButtonScaleFactor = Math.min(backButtonWidthScaleFactor, backButtonHeightScaleFactor);

        int newBackButtonWidth = (int)(originalBackButtonWidth * backButtonScaleFactor);
        int newBackButtonHeight = (int)(originalBackButtonHeight * backButtonScaleFactor);

        // Update game buttons
        JPanel buttonRowPanel = (JPanel)buttonPanel.getComponent(0);
        for (Component comp : buttonRowPanel.getComponents()) {
            if (comp instanceof JLabel) {
                JLabel button = (JLabel)comp;
                String buttonName = (String)button.getClientProperty("buttonName");
                if (buttonName != null) {
                    ImageIcon defaultIcon = createScaledIcon("src/assets/visuals/" + buttonName + "Default.png",
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
            ImageIcon defaultIcon = createScaledIcon("src/assets/visuals/woodButtonDefault.png",
                    newBackButtonWidth, newBackButtonHeight);
            backButton.setIcon(defaultIcon);
            backButton.putClientProperty("defaultIcon", defaultIcon);
            setButtonSize(backButton, new Dimension(newBackButtonWidth, newBackButtonHeight));
        }

        revalidate();
        repaint();
    }
}