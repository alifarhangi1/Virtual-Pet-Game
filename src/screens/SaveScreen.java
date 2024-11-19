package screens;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.io.IOException;
import java.awt.*;

public class SaveScreen extends JPanel {
    private ImageIcon backgroundIcon;
    private ImageIcon logo;
    private JLabel label;
    private JPanel logoPanel;
    private JPanel buttonPanel;
    private JPanel buttonPanelContainer;

    private static final String BUTTON_DEFAULT_PATH = "src/assets/visuals/";
    private static final String AUDIO_PATH = "src/assets/audio/";

    // Scaling constants - matched with TitleScreen
    private static final double LOGO_SCALE = 0.4;  // Matched with TitleScreen
    private static final double BUTTON_WIDTH_SCALE = 0.2;
    private static final double BUTTON_HEIGHT_SCALE = 0.12;
    private static final double BACK_BUTTON_WIDTH_SCALE = 0.12;
    private static final double BACK_BUTTON_HEIGHT_SCALE = 0.11;
    private static final int MIN_BUTTON_WIDTH = 180;
    private static final int MIN_BUTTON_HEIGHT = 60;
    private static final int MIN_BACK_BUTTON_WIDTH = 100;
    private static final int MIN_BACK_BUTTON_HEIGHT = 40;

    // Store original dimensions
    private int originalLogoWidth;
    private int originalLogoHeight;
    private int originalButtonWidth;
    private int originalButtonHeight;
    private int originalBackButtonWidth;
    private int originalBackButtonHeight;

    public SaveScreen() {
        setLayout(new BorderLayout());
        initializeImages();

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                resizeComponents();
            }
        });

        setupComponents();
    }

    private void initializeImages() {
        backgroundIcon = new ImageIcon("src/assets/visuals/background.gif");
        logo = new ImageIcon("src/assets/visuals/logo.png");

        // Store original dimensions
        originalLogoWidth = logo.getIconWidth();
        originalLogoHeight = logo.getIconHeight();
        originalButtonWidth = new ImageIcon(BUTTON_DEFAULT_PATH + "newGameButtonDefault.png").getIconWidth();
        originalButtonHeight = new ImageIcon(BUTTON_DEFAULT_PATH + "newGameButtonDefault.png").getIconHeight();
        originalBackButtonWidth = new ImageIcon(BUTTON_DEFAULT_PATH + "woodButtonDefault.png").getIconWidth();
        originalBackButtonHeight = new ImageIcon(BUTTON_DEFAULT_PATH + "woodButtonDefault.png").getIconHeight();
    }

    private void setupComponents() {
        setOpaque(false);

        // Main content panel with background
        JPanel contentPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundIcon.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        contentPanel.setLayout(new BorderLayout());

        // Logo setup
        label = new JLabel();
        label.setIcon(logo);

        logoPanel = new JPanel(new BorderLayout());
        JPanel logoWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER)) {
            @Override
            public Dimension getPreferredSize() {
                // Match the Y position with TitleScreen
                if (TitleScreen.currentLogoY > 0) {
                    return new Dimension(super.getPreferredSize().width, TitleScreen.currentLogoY);
                }
                return super.getPreferredSize();
            }
        };
        logoWrapper.add(label);
        logoWrapper.setOpaque(false);
        logoPanel.add(logoWrapper, BorderLayout.NORTH);
        logoPanel.setOpaque(false);

        // Button setup
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setOpaque(false);

        // Game buttons row
        JPanel buttonRowPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonRowPanel.add(createGameButton("newGameButton"));
        buttonRowPanel.add(createGameButton("loadGameButton"));
        buttonRowPanel.setOpaque(false);

        // Back button
        JPanel backButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        backButtonPanel.add(createBackButton());
        backButtonPanel.setOpaque(false);

        // Add components with reduced spacing
        buttonPanel.add(buttonRowPanel);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        buttonPanel.add(backButtonPanel);

        buttonPanelContainer = new JPanel(new BorderLayout());
        buttonPanelContainer.setOpaque(false);
        buttonPanelContainer.add(buttonPanel, BorderLayout.CENTER);

        contentPanel.add(logoPanel, BorderLayout.NORTH);
        contentPanel.add(buttonPanelContainer, BorderLayout.CENTER);

        add(contentPanel, BorderLayout.CENTER);
    }

    private void resizeComponents() {
        int containerWidth = getWidth();
        int containerHeight = getHeight();

        // Use TitleScreen's logo dimensions if available
        if (TitleScreen.currentLogoWidth > 0 && TitleScreen.currentLogoHeight > 0) {
            Image resizedLogo = logo.getImage().getScaledInstance(
                    TitleScreen.currentLogoWidth,
                    TitleScreen.currentLogoHeight,
                    Image.SCALE_SMOOTH
            );
            label.setIcon(new ImageIcon(resizedLogo));
        } else {
            // Fallback to original sizing logic
            double logoScaleFactor = Math.min(
                    (containerWidth * LOGO_SCALE) / originalLogoWidth,
                    (containerHeight * LOGO_SCALE) / originalLogoHeight
            );

            int newLogoWidth = (int)(originalLogoWidth * logoScaleFactor);
            int newLogoHeight = (int)(originalLogoHeight * logoScaleFactor);

            Image resizedLogo = logo.getImage().getScaledInstance(
                    newLogoWidth, newLogoHeight, Image.SCALE_SMOOTH
            );
            label.setIcon(new ImageIcon(resizedLogo));
        }

        // Resize game buttons
        int newButtonWidth = Math.max((int)(containerWidth * BUTTON_WIDTH_SCALE), MIN_BUTTON_WIDTH);
        int newButtonHeight = Math.max((int)(containerHeight * BUTTON_HEIGHT_SCALE), MIN_BUTTON_HEIGHT);

        // Update game buttons
        for (Component comp : ((JPanel)buttonPanel.getComponent(0)).getComponents()) {
            if (comp instanceof JLabel) {
                JLabel button = (JLabel) comp;
                String buttonName = (String) button.getClientProperty("buttonName");
                if (buttonName != null) {
                    ImageIcon defaultIcon = new ImageIcon(resizeImage(buttonName + "Default.png", newButtonWidth, newButtonHeight));
                    ImageIcon hoverIcon = new ImageIcon(resizeImage(buttonName + "Hover.png", newButtonWidth, newButtonHeight));
                    button.setIcon(defaultIcon);
                    button.putClientProperty("defaultIcon", defaultIcon);
                    button.putClientProperty("hoverIcon", hoverIcon);
                    button.setPreferredSize(new Dimension(newButtonWidth, newButtonHeight));
                }
            }
        }

        // Resize back button
        int newBackButtonWidth = Math.max((int)(containerWidth * BACK_BUTTON_WIDTH_SCALE), MIN_BACK_BUTTON_WIDTH);
        int newBackButtonHeight = Math.max((int)(containerHeight * BACK_BUTTON_HEIGHT_SCALE), MIN_BACK_BUTTON_HEIGHT);

        JPanel backButtonPanel = (JPanel)buttonPanel.getComponent(2);
        if (backButtonPanel.getComponentCount() > 0) {
            JLabel backButton = (JLabel)backButtonPanel.getComponent(0);
            ImageIcon defaultIcon = new ImageIcon(resizeImage("woodButtonDefault.png", newBackButtonWidth, newBackButtonHeight));
            ImageIcon hoverIcon = new ImageIcon(resizeImage("woodButtonHover.png", newBackButtonWidth, newBackButtonHeight));
            backButton.setIcon(defaultIcon);
            backButton.putClientProperty("defaultIcon", defaultIcon);
            backButton.putClientProperty("hoverIcon", hoverIcon);
            backButton.setPreferredSize(new Dimension(newBackButtonWidth, newBackButtonHeight));
        }

        revalidate();
        repaint();
    }

    private JLabel createGameButton(String buttonName) {
        JLabel button = new JLabel();
        button.putClientProperty("buttonName", buttonName);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Set initial icons
        int initialWidth = Math.max((int)(getWidth() * BUTTON_WIDTH_SCALE), MIN_BUTTON_WIDTH);
        int initialHeight = Math.max((int)(getHeight() * BUTTON_HEIGHT_SCALE), MIN_BUTTON_HEIGHT);

        ImageIcon defaultIcon = new ImageIcon(resizeImage(buttonName + "Default.png", initialWidth, initialHeight));
        ImageIcon hoverIcon = new ImageIcon(resizeImage(buttonName + "Hover.png", initialWidth, initialHeight));

        button.setIcon(defaultIcon);
        button.putClientProperty("defaultIcon", defaultIcon);
        button.putClientProperty("hoverIcon", hoverIcon);

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setIcon((ImageIcon)button.getClientProperty("hoverIcon"));
                playHoverSound();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setIcon((ImageIcon)button.getClientProperty("defaultIcon"));
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                playButtonClickSound();
                // Add game button logic here
            }
        });

        return button;
    }

    private JLabel createBackButton() {
        JLabel backButton = new JLabel();
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Set initial icons
        int initialWidth = Math.max((int)(getWidth() * BACK_BUTTON_WIDTH_SCALE), MIN_BACK_BUTTON_WIDTH);
        int initialHeight = Math.max((int)(getHeight() * BACK_BUTTON_HEIGHT_SCALE), MIN_BACK_BUTTON_HEIGHT);

        ImageIcon defaultIcon = new ImageIcon(resizeImage("woodButtonDefault.png", initialWidth, initialHeight));
        ImageIcon hoverIcon = new ImageIcon(resizeImage("woodButtonHover.png", initialWidth, initialHeight));

        backButton.setIcon(defaultIcon);
        backButton.putClientProperty("defaultIcon", defaultIcon);
        backButton.putClientProperty("hoverIcon", hoverIcon);

        backButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                backButton.setIcon((ImageIcon)backButton.getClientProperty("hoverIcon"));
                playHoverSound();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                backButton.setIcon((ImageIcon)backButton.getClientProperty("defaultIcon"));
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                TitleScreen.playBackButtonClickSound();
                CardLayout cardLayout = (CardLayout) getParent().getLayout();
                cardLayout.show(getParent(), "titleScreen");
            }
        });

        return backButton;
    }

    private void playButtonClickSound() {
        try {
            File audioFile = new File(AUDIO_PATH + "button_click.wav");
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    private void playHoverSound() {
        try {
            File audioFile = new File(AUDIO_PATH + "menu_hover.wav");
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    private Image resizeImage(String imagePath, int width, int height) {
        Image img = new ImageIcon(BUTTON_DEFAULT_PATH + imagePath).getImage();
        return img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
    }
}