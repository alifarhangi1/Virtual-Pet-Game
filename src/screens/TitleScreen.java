package screens;

import javax.swing.*;
import javax.sound.sampled.*;
import java.awt.*;
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

    public TitleScreen() {
        frame = new JFrame();
        frame.setSize(500, 500);

        mainContainer = new JPanel(new CardLayout());

        // Background image
        backgroundIcon = new ImageIcon("src/assets/visuals/background.gif");

        // Logo
        logo = new ImageIcon("src/assets/visuals/logo.png");

        // Play button icons
        playButtonDefaultIcon = new ImageIcon(
                new ImageIcon("src/assets/visuals/playButtonDefault.png").getImage().getScaledInstance(350, 125,
                        Image.SCALE_SMOOTH));
        playButtonHoverIcon = new ImageIcon(
                new ImageIcon("src/assets/visuals/playButtonHover.png").getImage().getScaledInstance(350, 125,
                        Image.SCALE_SMOOTH));

        // Tutorial button icons
        tutorialButtonDefaultIcon = new ImageIcon(
                new ImageIcon("src/assets/visuals/tutorialButtonDefault.png").getImage()
                        .getScaledInstance(350, 125, Image.SCALE_SMOOTH));
        tutorialButtonHoverIcon = new ImageIcon(new ImageIcon("src/assets/visuals/tutorialButtonHover.png").getImage()
                .getScaledInstance(350, 125, Image.SCALE_SMOOTH));

        // Parental Control button icons
        parentalControlButtonDefaultIcon = new ImageIcon(
                new ImageIcon("src/assets/visuals/parentalControlButtonDefault.png")
                        .getImage().getScaledInstance(350, 125, Image.SCALE_SMOOTH));
        parentalControlButtonHoverIcon = new ImageIcon(
                new ImageIcon("src/assets/visuals/parentalControlButtonHover.png").getImage()
                        .getScaledInstance(350, 125, Image.SCALE_SMOOTH));

        // Main panel setup
        titlePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundIcon.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };

        Image img = logo.getImage();
        Image reSizedImg = img.getScaledInstance(500, 500, Image.SCALE_SMOOTH);
        logo = new ImageIcon(reSizedImg);

        label = new JLabel();
        label.setIcon(logo);

        titlePanel.setLayout(new BorderLayout());

        logoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
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

        JLabel bottomLeftLabel = createLabel("TEAM 50 CS 2212 FALL 2024 WESTERN UNIVERSITY", true, 20);
        JLabel bottomRightLabel = createLabel(
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
        frame.setVisible(true);

        playIntroAudio();
        playBackgroundMusic();
    }

    // Method to play the intro audio only once per run
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

    // Method to play hover sound effect
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

    // Method to play background music in a loop
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

    // Method to stop the background music
    private void stopBackgroundMusic() {
        if (bgmClip != null && bgmClip.isRunning()) {
            bgmClip.stop();
            bgmClip.close();
        }
    }

    // Create Play button with hover effect
    private JLabel createPlayButton() {
        JLabel playButton = new JLabel();
        playButton.setIcon(playButtonDefaultIcon);
        playButton.setPreferredSize(new Dimension(350, 125));
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
                // Switch to save screen
                CardLayout cardLayout = (CardLayout) mainContainer.getLayout();
                cardLayout.show(mainContainer, "saveScreen");
            }
        });

        return playButton;
    }

    // Create Tutorial button with hover effect
    private JLabel createTutorialButton() {
        JLabel tutorialButton = new JLabel();
        tutorialButton.setIcon(tutorialButtonDefaultIcon);
        tutorialButton.setPreferredSize(new Dimension(350, 125));
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
        });

        return tutorialButton;
    }

    // Create Parental Control button with hover effect
    private JLabel createParentalControlButton() {
        JLabel parentalControlButton = new JLabel();
        parentalControlButton.setIcon(parentalControlButtonDefaultIcon);
        parentalControlButton.setPreferredSize(new Dimension(350, 125));
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
        });

        return parentalControlButton;
    }

    // Helper method to style bottom labels
    private JLabel createLabel(String text, boolean isLeftLabel, int fontSize) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setOpaque(false);
        label.setForeground(new Color(255, 215, 0)); // Gold color
        label.setFont(new Font("Monospaced", Font.BOLD | (isLeftLabel ? Font.ITALIC : Font.PLAIN), fontSize));
        return label;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TitleScreen::new);
    }
}