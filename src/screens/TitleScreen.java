package screens;

import javax.swing.*;
import javax.sound.sampled.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

public class TitleScreen extends JPanel {
    private JFrame frame;
    private JPanel mainContainer;
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
    private final int BUTTON_WIDTH = 350;
    private final int BUTTON_HEIGHT = 125;

    public TitleScreen() {
        frame = new JFrame("Pet Quest");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(800, 600));
        frame.setPreferredSize(new Dimension(1024, 768));

        mainContainer = new JPanel(new CardLayout());
        setupUI();

        frame.add(mainContainer);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        playIntroAudio();
        playBackgroundMusic();

        // Add window listener to handle resizing
        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                resizeComponents();
            }
        });
    }

    private void setupUI() {
        loadImages();

        // Main game panel with background
        JPanel gamePanel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Image bg = backgroundIcon.getImage();
                g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
            }
        };

        // Use GridBagLayout for precise control
        GridBagConstraints gbc = new GridBagConstraints();

        // Logo section (top)
        JLabel logoLabel = new JLabel(logo);
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(15, 0, 0, 0); // Top padding of 50 pixels
        gamePanel.add(logoLabel, gbc);

        // Buttons section (middle)
        JPanel buttonPanel = createButtonPanel();
        gbc.gridy = 1;
        gbc.weighty = 0.6;
        gbc.anchor = GridBagConstraints.CENTER;
        gamePanel.add(buttonPanel, gbc);

        // Credits section (bottom)
        JPanel creditsPanel = createCreditsPanel();
        gbc.gridy = 2;
        gbc.weighty = 0.1;
        gbc.anchor = GridBagConstraints.SOUTH;
        gamePanel.add(creditsPanel, gbc);

        mainContainer.add(gamePanel, "titleScreen");
        mainContainer.add(new SaveScreen(), "saveScreen");
    }

    private void loadImages() {
        // Load and scale images based on screen size
        backgroundIcon = new ImageIcon("group50/src/assets/visuals/background.gif");
        logo = new ImageIcon("group50/src/assets/visuals/logo.png");

        // Load button images
        playButtonDefaultIcon = loadScaledImage("group50/src/assets/visuals/playButtonDefault.png");
        playButtonHoverIcon = loadScaledImage("group50/src/assets/visuals/playButtonHover.png");
        tutorialButtonDefaultIcon = loadScaledImage("group50/src/assets/visuals/tutorialButtonDefault.png");
        tutorialButtonHoverIcon = loadScaledImage("group50/src/assets/visuals/tutorialButtonHover.png");
        parentalControlButtonDefaultIcon = loadScaledImage("group50/src/assets/visuals/parentalControlButtonDefault.png");
        parentalControlButtonHoverIcon = loadScaledImage("group50/src/assets/visuals/parentalControlButtonHover.png");

        // Scale logo initially
        scaleLogo();
    }

    private ImageIcon loadScaledImage(String path) {
        return new ImageIcon(
                new ImageIcon(path).getImage().getScaledInstance(
                        BUTTON_WIDTH, BUTTON_HEIGHT, Image.SCALE_SMOOTH
                )
        );
    }

    private void scaleLogo() {
        Image img = logo.getImage();
        int frameHeight = frame.getHeight();
        // Logo height should be approximately 30% of frame height
        int targetHeight = (int)(frameHeight * 0.5);  // Increased from 0.2
        double ratio = (double)img.getWidth(null) / img.getHeight(null);
        int targetWidth = (int)(targetHeight * ratio);

        Image scaledImage = img.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
        logo = new ImageIcon(scaledImage);
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setOpaque(false);

        // Create buttons with proper spacing
        buttonPanel.add(Box.createVerticalGlue());
        buttonPanel.add(createButton(playButtonDefaultIcon, playButtonHoverIcon, e -> {
            CardLayout cl = (CardLayout) mainContainer.getLayout();
            cl.show(mainContainer, "saveScreen");
        }));
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        buttonPanel.add(createButton(tutorialButtonDefaultIcon, tutorialButtonHoverIcon, null));
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        buttonPanel.add(createButton(parentalControlButtonDefaultIcon, parentalControlButtonHoverIcon, null));
        buttonPanel.add(Box.createVerticalGlue());

        return buttonPanel;
    }

    private JLabel createButton(ImageIcon defaultIcon, ImageIcon hoverIcon, ActionListener action) {
        JLabel button = new JLabel(defaultIcon);
        button.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setIcon(hoverIcon);
                playHoverSound();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setIcon(defaultIcon);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if (action != null) {
                    action.actionPerformed(new ActionEvent(button, ActionEvent.ACTION_PERFORMED, "click"));
                }
            }
        });

        return button;
    }

    private JPanel createCreditsPanel() {
        JPanel creditsPanel = new JPanel(new BorderLayout());
        creditsPanel.setOpaque(false);
        creditsPanel.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));

        JLabel teamLabel = createStyledLabel("TEAM 50 CS 2212 FALL 2024 WESTERN UNIVERSITY", true, 16);
        JLabel namesLabel = createStyledLabel(
                "Adam Yassine, Ali Farhangi, Luca Duarte, Robin (Sangjae) Lee, Yazan Abushirbi",
                false,
                12
        );

        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftPanel.setOpaque(false);
        leftPanel.add(teamLabel);

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.setOpaque(false);
        rightPanel.add(namesLabel);

        creditsPanel.add(leftPanel, BorderLayout.WEST);
        creditsPanel.add(rightPanel, BorderLayout.EAST);

        return creditsPanel;
    }

    private JLabel createStyledLabel(String text, boolean isTeamLabel, int size) {
        JLabel label = new JLabel(text);
        label.setForeground(new Color(255, 215, 0));  // Gold color
        int style = isTeamLabel ? (Font.BOLD | Font.ITALIC) : Font.BOLD;
        label.setFont(new Font("Monospaced", style, size));
        return label;
    }

    private void resizeComponents() {
        scaleLogo();
        mainContainer.revalidate();
        mainContainer.repaint();
    }

    private void playIntroAudio() {
        if (!introPlayed) {
            playAudio("group50/src/assets/audio/pet_quest_intro.wav");
            introPlayed = true;
        }
    }

    private void playHoverSound() {
        playAudio("group50/src/assets/audio/menu_hover.wav");
    }

    private void playBackgroundMusic() {
        try {
            File bgmFile = new File("group50/src/assets/audio/menu_bgm.wav");
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(bgmFile);
            bgmClip = AudioSystem.getClip();
            bgmClip.open(audioStream);
            bgmClip.loop(Clip.LOOP_CONTINUOUSLY);
            bgmClip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    private void playAudio(String path) {
        try {
            File audioFile = new File(path);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void stopBackgroundMusic() {
        if (bgmClip != null && bgmClip.isRunning()) {
            bgmClip.stop();
            bgmClip.close();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TitleScreen::new);
    }
}