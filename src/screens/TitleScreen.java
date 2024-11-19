package screens;

import javax.swing.*;
import javax.sound.sampled.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

public class TitleScreen {

    private JFrame frame;
    private JPanel mainContainer;
    private JPanel titlePanel;
    private JPanel logoPanel;
    private JPanel buttonPanelContainer;
    private JPanel buttonPanel;
    private JLabel label;
    private ImageIcon backgroundIcon;
    private ImageIcon logo;
    private ImageIcon playButtonDefaultIcon;
    private ImageIcon playButtonHoverIcon;
    private ImageIcon tutorialButtonDefaultIcon;
    private ImageIcon tutorialButtonHoverIcon;
    private ImageIcon parentalControlButtonDefaultIcon;
    private ImageIcon parentalControlButtonHoverIcon;
    private Clip bgmClip;
    private static boolean introPlayed = false;
    private static final double LOGO_SCALE = 0.4;
    private static final double BUTTON_WIDTH_SCALE = 0.19;
    private static final double BUTTON_HEIGHT_SCALE = 0.13;
    private static final double TEXT_BASE_SCALE = 0.02; // Base scale for text size relative to window height
    private int originalLogoWidth;
    private int originalLogoHeight;
    private int originalButtonWidth;
    private int originalButtonHeight;
    private static final int MIN_BUTTON_WIDTH = 200;
    private static final int MIN_BUTTON_HEIGHT = 70;
    private static final int MIN_FONT_SIZE = 10;
    private JLabel bottomLeftLabel;
    private JLabel bottomRightLabel;
    public static int currentLogoWidth;
    public static int currentLogoHeight;
    public static int currentLogoY;

    public TitleScreen() {
        frame = new JFrame();
        frame.setMinimumSize(new Dimension(800, 600));
        frame.setPreferredSize(new Dimension(1024, 768));
        frame.addComponentListener(new ComponentAdapter(){
            @Override
            public void componentResized(ComponentEvent e) {
                resizeComponents();
            }
        });

        mainContainer = new JPanel(new CardLayout());

        initializeImages();

        originalLogoWidth = logo.getIconWidth();
        originalLogoHeight = logo.getIconHeight();
        originalButtonWidth = new ImageIcon("src/assets/visuals/playButtonDefault.png").getIconWidth();
        originalButtonHeight = new ImageIcon("src/assets/visuals/playButtonDefault.png").getIconHeight();

        // Main panel setup
        titlePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundIcon.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };

        titlePanel.setLayout(new BorderLayout());

        logoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        label = new JLabel();
        logoPanel.add(label);
        logoPanel.setOpaque(false);

        buttonPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        buttonPanel.setOpaque(false);

        JLabel playButton = createPlayButton();
        JLabel tutorialButton = createTutorialButton();
        JLabel parentalControlButton = createParentalControlButton();

        buttonPanel.add(playButton);
        buttonPanel.add(tutorialButton);
        buttonPanel.add(parentalControlButton);

        buttonPanelContainer = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanelContainer.setPreferredSize(new Dimension(200, 200));
        buttonPanelContainer.add(buttonPanel);
        buttonPanelContainer.setOpaque(false);

        // Initialize bottom labels with default sizes
        bottomLeftLabel = createLabel("TEAM 50 CS 2212 FALL 2024 WESTERN UNIVERSITY", true, 20);
        bottomRightLabel = createLabel(
                "Adam Yassine, Ali Farhangi, Luca Duarte, Robin (Sangjae) Lee, Yazan Abushirbi", false, 15);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setOpaque(false);

        JPanel bottomLeftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        bottomLeftPanel.setOpaque(false);
        bottomLeftPanel.add(bottomLeftLabel);

        JPanel bottomRightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        bottomRightPanel.setOpaque(false);
        bottomRightPanel.add(bottomRightLabel);

        bottomPanel.add(bottomLeftPanel, BorderLayout.WEST);
        bottomPanel.add(bottomRightPanel, BorderLayout.EAST);

        titlePanel.add(logoPanel, BorderLayout.NORTH);
        titlePanel.add(buttonPanelContainer, BorderLayout.CENTER);
        titlePanel.add(bottomPanel, BorderLayout.SOUTH);

        mainContainer.add(titlePanel, "titleScreen");
        mainContainer.add(new SaveScreen(), "saveScreen");

        frame.add(mainContainer);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();

        resizeComponents();

        frame.setVisible(true);

        playIntroAudio();
        playBackgroundMusic();
    }

    private void resizeComponents() {
        int windowWidth = frame.getWidth();
        int windowHeight = frame.getHeight();

        // Resize logo
        double scaleFactor = Math.min(
                (windowWidth * LOGO_SCALE) / originalLogoWidth,
                (windowHeight * LOGO_SCALE) / originalLogoHeight
        );

        int newLogoWidth = (int)(originalLogoWidth * scaleFactor);
        int newLogoHeight = (int)(originalLogoHeight * scaleFactor);

        currentLogoWidth = newLogoWidth;
        currentLogoHeight = newLogoHeight;
        currentLogoY = logoPanel.getY();

        Image resizedLogo = logo.getImage().getScaledInstance(
                newLogoWidth,
                newLogoHeight,
                Image.SCALE_SMOOTH
        );
        label.setIcon(new ImageIcon(resizedLogo));

        // Resize buttons
        double maxButtonWidth = Math.max(windowWidth * BUTTON_WIDTH_SCALE, MIN_BUTTON_WIDTH);
        double maxButtonHeight = Math.max(windowHeight * BUTTON_HEIGHT_SCALE, MIN_BUTTON_HEIGHT);

        double widthScaleFactor = maxButtonWidth / originalButtonWidth;
        double heightScaleFactor = maxButtonHeight / originalButtonHeight;

        double buttonScaleFactor = Math.max(
                Math.min(widthScaleFactor, heightScaleFactor),
                Math.max(MIN_BUTTON_WIDTH / (double)originalButtonWidth,
                        MIN_BUTTON_HEIGHT / (double)originalButtonHeight)
        );

        int newButtonWidth = (int)(originalButtonWidth * buttonScaleFactor);
        int newButtonHeight = (int)(originalButtonHeight * buttonScaleFactor);

        newButtonWidth = Math.max(newButtonWidth, MIN_BUTTON_WIDTH);
        newButtonHeight = Math.max(newButtonHeight, MIN_BUTTON_HEIGHT);

        // Adjust button panel layout
        if (windowWidth < 800) {
            buttonPanel.setLayout(new GridLayout(3, 1, 5, 5));
        } else {
            buttonPanel.setLayout(new GridLayout(3, 1, 10, 10));
        }

        // Resize text
        int leftLabelSize = Math.max((int)(windowHeight * TEXT_BASE_SCALE * 1.2), MIN_FONT_SIZE);
        int rightLabelSize = Math.max((int)(windowHeight * TEXT_BASE_SCALE), MIN_FONT_SIZE);

        bottomLeftLabel.setFont(new Font("Monospaced", Font.BOLD | Font.ITALIC, leftLabelSize));
        bottomRightLabel.setFont(new Font("Monospaced", Font.BOLD, rightLabelSize));

        // Resize button images
        resizeButtonImages(newButtonWidth, newButtonHeight);

        // Update buttons
        Component[] buttons = buttonPanel.getComponents();
        if (buttons.length >= 3) {
            ((JLabel)buttons[0]).setIcon(playButtonDefaultIcon);
            buttons[0].setPreferredSize(new Dimension(newButtonWidth, newButtonHeight));

            ((JLabel)buttons[1]).setIcon(tutorialButtonDefaultIcon);
            buttons[1].setPreferredSize(new Dimension(newButtonWidth, newButtonHeight));

            ((JLabel)buttons[2]).setIcon(parentalControlButtonDefaultIcon);
            buttons[2].setPreferredSize(new Dimension(newButtonWidth, newButtonHeight));
        }

        buttonPanel.revalidate();
        buttonPanelContainer.revalidate();
        frame.revalidate();
        frame.repaint();
    }

    private void initializeImages() {
        // Background image
        backgroundIcon = new ImageIcon("src/assets/visuals/background.gif");

        // Logo
        logo = new ImageIcon("src/assets/visuals/logo.png");

        // Initialize button icons with initial sizes based on preferred frame size
        int initialButtonWidth = (int)(frame.getPreferredSize().width * BUTTON_WIDTH_SCALE);
        int initialButtonHeight = (int)(frame.getPreferredSize().height * BUTTON_HEIGHT_SCALE);

        // Play button icons
        playButtonDefaultIcon = new ImageIcon(
                new ImageIcon("src/assets/visuals/playButtonDefault.png")
                        .getImage().getScaledInstance(initialButtonWidth, initialButtonHeight, Image.SCALE_SMOOTH));
        playButtonHoverIcon = new ImageIcon(
                new ImageIcon("src/assets/visuals/playButtonHover.png")
                        .getImage().getScaledInstance(initialButtonWidth, initialButtonHeight, Image.SCALE_SMOOTH));

        // Tutorial button icons
        tutorialButtonDefaultIcon = new ImageIcon(
                new ImageIcon("src/assets/visuals/tutorialButtonDefault.png")
                        .getImage().getScaledInstance(initialButtonWidth, initialButtonHeight, Image.SCALE_SMOOTH));
        tutorialButtonHoverIcon = new ImageIcon(
                new ImageIcon("src/assets/visuals/tutorialButtonHover.png")
                        .getImage().getScaledInstance(initialButtonWidth, initialButtonHeight, Image.SCALE_SMOOTH));

        // Parental Control button icons
        parentalControlButtonDefaultIcon = new ImageIcon(
                new ImageIcon("src/assets/visuals/parentalControlButtonDefault.png")
                        .getImage().getScaledInstance(initialButtonWidth, initialButtonHeight, Image.SCALE_SMOOTH));
        parentalControlButtonHoverIcon = new ImageIcon(
                new ImageIcon("src/assets/visuals/parentalControlButtonHover.png")
                        .getImage().getScaledInstance(initialButtonWidth, initialButtonHeight, Image.SCALE_SMOOTH));
    }

    private void resizeButtonImages(int width, int height) {
        // Resize play button icons
        playButtonDefaultIcon = new ImageIcon(
                new ImageIcon("src/assets/visuals/playButtonDefault.png")
                        .getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
        playButtonHoverIcon = new ImageIcon(
                new ImageIcon("src/assets/visuals/playButtonHover.png")
                        .getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));

        // Resize tutorial button icons
        tutorialButtonDefaultIcon = new ImageIcon(
                new ImageIcon("src/assets/visuals/tutorialButtonDefault.png")
                        .getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
        tutorialButtonHoverIcon = new ImageIcon(
                new ImageIcon("src/assets/visuals/tutorialButtonHover.png")
                        .getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));

        // Resize parental control button icons
        parentalControlButtonDefaultIcon = new ImageIcon(
                new ImageIcon("src/assets/visuals/parentalControlButtonDefault.png")
                        .getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
        parentalControlButtonHoverIcon = new ImageIcon(
                new ImageIcon("src/assets/visuals/parentalControlButtonHover.png")
                        .getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
    }

    private void playIntroAudio() {
        if (!introPlayed) {
            try {
                File audioFile = new File("src/assets/audio/pet_quest_intro.wav");
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
                Clip clip = AudioSystem.getClip();
                clip.open(audioStream);
                clip.start();
                introPlayed = true;
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                e.printStackTrace();
            }
        }
    }

    private void playHoverSound() {
        try {
            File audioFile = new File("src/assets/audio/menu_hover.wav");
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    private void playButtonClickSound() {
        try {
            File audioFile = new File("src/assets/audio/button_click.wav");
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public static void playBackButtonClickSound() {
        try {
            File audioFile = new File("src/assets/audio/back_button_click.wav");
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    private void playBackgroundMusic() {
        try {
            File bgmFile = new File("src/assets/audio/menu_bgm.wav");
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(bgmFile);
            bgmClip = AudioSystem.getClip();
            bgmClip.open(audioStream);
            bgmClip.loop(Clip.LOOP_CONTINUOUSLY);
            bgmClip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    private void stopBackgroundMusic() {
        if (bgmClip != null && bgmClip.isRunning()) {
            bgmClip.stop();
            bgmClip.close();
        }
    }

    private JLabel createPlayButton() {
        JLabel playButton = new JLabel();
        playButton.setIcon(playButtonDefaultIcon);
        playButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        playButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                playButton.setIcon(playButtonHoverIcon);
                playHoverSound();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                playButton.setIcon(playButtonDefaultIcon);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                playButtonClickSound();
                CardLayout cardLayout = (CardLayout) mainContainer.getLayout();
                cardLayout.show(mainContainer, "saveScreen");
            }
        });

        return playButton;
    }

    private JLabel createTutorialButton() {
        JLabel tutorialButton = new JLabel();
        tutorialButton.setIcon(tutorialButtonDefaultIcon);
        tutorialButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        tutorialButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                tutorialButton.setIcon(tutorialButtonHoverIcon);
                playHoverSound();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                tutorialButton.setIcon(tutorialButtonDefaultIcon);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                playButtonClickSound();
                // Add logic for tutorial button
            }
        });

        return tutorialButton;
    }

    private JLabel createParentalControlButton() {
        JLabel parentalControlButton = new JLabel();
        parentalControlButton.setIcon(parentalControlButtonDefaultIcon);
        parentalControlButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        parentalControlButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                parentalControlButton.setIcon(parentalControlButtonHoverIcon);
                playHoverSound();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                parentalControlButton.setIcon(parentalControlButtonDefaultIcon);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                playButtonClickSound();
                // Add logic for parental control button
            }
        });

        return parentalControlButton;
    }

    private JLabel createLabel(String text, boolean isLeftLabel, int fontSize) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setOpaque(false);
        label.setForeground(new Color(255, 215, 0));
        label.setFont(new Font("Monospaced", Font.BOLD | (isLeftLabel ? Font.ITALIC : Font.PLAIN), fontSize));
        return label;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TitleScreen::new);
    }
}