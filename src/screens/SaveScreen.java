package screens;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

public class SaveScreen extends JPanel {
    private ImageIcon backgroundIcon;
    private ImageIcon logo;
    private JLabel label;
    private JPanel logoPanel;
    private JPanel buttonPanel;
    private JPanel buttonPanelContainer;
    private ImageIcon newButtonDefaultIcon;
    private ImageIcon newButtonHoverIcon;
    private ImageIcon loadButtonDefaultIcon;
    private ImageIcon loadButtonHoverIcon;
    private ImageIcon backButtonDefaultIcon;
    private ImageIcon backButtonHoverIcon;

    public SaveScreen() {
        // Set layout for the main panel
        setLayout(new BorderLayout());

        // Background image
        backgroundIcon = new ImageIcon("src/assets/visuals/background.gif");

        // Logo
        logo = new ImageIcon("src/assets/visuals/logo.png");

        // New Save button icons
        newButtonDefaultIcon = new ImageIcon(
                new ImageIcon("src/assets/visuals/playButtonDefault.png").getImage().getScaledInstance(350, 125,
                        Image.SCALE_SMOOTH));
        newButtonHoverIcon = new ImageIcon(
                new ImageIcon("src/assets/visuals/playButtonHover.png").getImage().getScaledInstance(350, 125,
                        Image.SCALE_SMOOTH));

        // Load button icons
        loadButtonDefaultIcon = new ImageIcon(
                new ImageIcon("src/assets/visuals/tutorialButtonDefault.png").getImage()
                        .getScaledInstance(350, 125, Image.SCALE_SMOOTH));
        loadButtonHoverIcon = new ImageIcon(
                new ImageIcon("src/assets/visuals/tutorialButtonHover.png").getImage()
                        .getScaledInstance(350, 125, Image.SCALE_SMOOTH));

        // Back button icons
        backButtonDefaultIcon = new ImageIcon(
                new ImageIcon("src/assets/visuals/parentalControlButtonDefault.png")
                        .getImage().getScaledInstance(350, 125, Image.SCALE_SMOOTH));
        backButtonHoverIcon = new ImageIcon(
                new ImageIcon("src/assets/visuals/parentalControlButtonHover.png").getImage()
                        .getScaledInstance(350, 125, Image.SCALE_SMOOTH));

        setOpaque(false);

        JPanel contentPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundIcon.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };

        // Scale logo
        Image img = logo.getImage();
        Image reSizedImg = img.getScaledInstance(300, 300, Image.SCALE_SMOOTH);
        logo = new ImageIcon(reSizedImg);

        label = new JLabel();
        label.setIcon(logo);

        contentPanel.setLayout(new BorderLayout());

        // Create a wrapper panel for the logo with padding at the bottom
        logoPanel = new JPanel(new BorderLayout());
        JPanel logoWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        logoWrapper.add(label);
        logoWrapper.setOpaque(false);
        logoPanel.add(logoWrapper, BorderLayout.NORTH);
        // Add padding below the logo
        logoPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        logoPanel.setOpaque(false);

        buttonPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        buttonPanel.setOpaque(false);

        JLabel newButton = createNewButton();
        JLabel loadButton = createLoadButton();
        JLabel backButton = createBackButton();

        buttonPanel.add(newButton);
        buttonPanel.add(loadButton);
        buttonPanel.add(backButton);

        // Create a wrapper panel for the buttons with padding
        buttonPanelContainer = new JPanel(new BorderLayout());
        JPanel buttonWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonWrapper.add(buttonPanel);
        buttonWrapper.setOpaque(false);

        // Add padding above and below the buttons
        buttonPanelContainer.add(Box.createRigidArea(new Dimension(0, 50)), BorderLayout.NORTH);
        buttonPanelContainer.add(buttonWrapper, BorderLayout.CENTER);
        buttonPanelContainer.add(Box.createRigidArea(new Dimension(0, 50)), BorderLayout.SOUTH);
        buttonPanelContainer.setOpaque(false);

        contentPanel.add(logoPanel, BorderLayout.NORTH);
        contentPanel.add(buttonPanelContainer, BorderLayout.CENTER);

        add(contentPanel, BorderLayout.CENTER);
    }

    private JLabel createNewButton() {
        JLabel playButton = new JLabel();
        playButton.setIcon(newButtonDefaultIcon);
        playButton.setPreferredSize(new Dimension(350, 125));
        playButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        playButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                playButton.setIcon(newButtonHoverIcon);
                playHoverSound();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                playButton.setIcon(newButtonDefaultIcon);
            }
        });

        return playButton;
    }

    private JLabel createLoadButton() {
        JLabel tutorialButton = new JLabel();
        tutorialButton.setIcon(loadButtonDefaultIcon);
        tutorialButton.setPreferredSize(new Dimension(350, 125));
        tutorialButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        tutorialButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                tutorialButton.setIcon(loadButtonHoverIcon);
                playHoverSound();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                tutorialButton.setIcon(loadButtonDefaultIcon);
            }
        });

        return tutorialButton;
    }

    private JLabel createBackButton() {
        JLabel parentalControlButton = new JLabel();
        parentalControlButton.setIcon(backButtonDefaultIcon);
        parentalControlButton.setPreferredSize(new Dimension(350, 125));
        parentalControlButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        parentalControlButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                parentalControlButton.setIcon(backButtonHoverIcon);
                playHoverSound();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                parentalControlButton.setIcon(backButtonDefaultIcon);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                CardLayout cardLayout = (CardLayout) getParent().getLayout();
                cardLayout.show(getParent(), "titleScreen");
            }
        });

        return parentalControlButton;
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
}