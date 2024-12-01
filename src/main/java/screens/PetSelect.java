package screens;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;


public class PetSelect extends JPanel {
    private static final String[] STARTER_PETS = {"Panda", "Wolf", "Owl"};
    private static final String[] UNLOCKABLE_PETS = {"Minotaur", "Tiger", "Dragon"};
    private static final String[] UNLOCKED_PETS = {"Minotaur", "Tiger", "Dragon"};

    private static final String[] STARTER_IMAGES = {
            "/visuals/pandaSlotDefault.png",
            "/visuals/wolfSlotDefault.png",
            "/visuals/owlSlotDefault.png"
    };

    private static final String[] UNLOCKABLE_IMAGES = {
            "/visuals/minotaurSlotLock.png",
            "/visuals/tigerSlotLock.png",
            "/visuals/dragonSlotLock.png"
    };



    private String[] currentPets = STARTER_PETS; // Start with starter pets
    private String[] currentImages = STARTER_IMAGES;
    private int currentPage = 0; // Page index for navigation
    private JLabel selectedPetLabel;
    private JPanel petSlotPanel;
    private JLabel wolfGifLabel;
    private JLabel owlGifLabel;

    private static final String BACKGROUND_PATH = "/visuals/petSelectBG2.gif";

    private Clip bgmClip; // To manage the background music

    public PetSelect() {
        setLayout(null); // Use null layout for manual positioning
        setupComponents();
        playIntroAudio("/audio/petSelect_AI_Intro.wav"); // Play the intro audio
        playBackgroundMusic("/audio/petSelect_bgm.wav"); // Start looping background music
    }

    private void setupComponents() {
        // Background setup
        setOpaque(false);

        // Add the title at the top center
        JLabel titleLabel = new JLabel();
        ImageIcon titleIcon = new ImageIcon(new ImageIcon(getClass().getResource("/visuals/petSelectTitle.png"))
                .getImage().getScaledInstance(900, 80, Image.SCALE_SMOOTH)); // Scale the title image
        titleLabel.setIcon(titleIcon);
        titleLabel.setBounds(510, 15, 900, 80); // Position the title (centered at the top)
        add(titleLabel);

        // Top section: Selected pet display
        selectedPetLabel = new JLabel("No Pet Selected", SwingConstants.CENTER);
        selectedPetLabel.setFont(new Font("Monospaced", Font.BOLD, 30));
        selectedPetLabel.setForeground(Color.WHITE);
        selectedPetLabel.setBounds(757, 491, 400, 50); // Position selected pet label below the title
        add(selectedPetLabel);

        // Center section: Pet slots and navigation
        petSlotPanel = new JPanel();
        petSlotPanel.setOpaque(false);
        petSlotPanel.setLayout(null); // Use null layout for custom positioning
        petSlotPanel.setBounds(350, 535, 1200, 500); // Adjust size and position for pet slots and buttons
        updatePetSlots();
        add(petSlotPanel);

        // Back and Next buttons

        JButton backButton = createNavigationButton(
                "/visuals/backArrowButtonDefault.png",
                "/visuals/backArrowButtonHover.png",
                () -> navigatePets(-1),
                "/audio/backButtonClickPS.wav" // Back button click sound
        );
        backButton.setBounds(110, 700, 170, 110); // Position back button
        add(backButton);

        JButton nextButton = createNavigationButton(
                "/visuals/nextArrowButtonDefault.png",
                "/visuals/nextArrowButtonHover.png",
                () -> navigatePets(1),
                "/audio/nextButtonClickPS.wav" // Next button click sound
        );
        nextButton.setBounds(1620, 700, 170, 110); // Position next button
        add(nextButton);

    }

    private void updatePetSlots() {
        petSlotPanel.removeAll(); // Clear existing components

        int slotX = 45, slotY = 10, slotWidth = 320, slotHeight = 420, slotSpacing = 400;
        int buttonHeight = 50, buttonSpacing = 5; // Spacing between slot and button

        // Add pet slots dynamically based on the current page
        for (int i = 0; i < currentPets.length; i++) {
            JLabel petSlot = createPetSlot(currentPets[i], currentImages[i]);
            petSlot.setBounds(slotX + i * slotSpacing, slotY, slotWidth, slotHeight);
            petSlotPanel.add(petSlot);

            // Add info buttons dynamically for starter pets (page 0) and unlocked pets (page 2)
            if (currentPage == 0 || currentPage == 2) {
                JLabel infoButton = createInfoButton(currentPets[i]);
                infoButton.setBounds(
                        slotX + i * slotSpacing + (slotWidth - 280) / 2, // Center the button below the slot
                        slotY + slotHeight + buttonSpacing, // Position below the slot
                        280, // Button width
                        buttonHeight // Button height
                );
                petSlotPanel.add(infoButton);
            }
        }

        petSlotPanel.revalidate();
        petSlotPanel.repaint();
    }



    private void playIntroAudio(String audioFilePath) {
        try {
            URL audioFile = getClass().getResource(audioFilePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start(); // Play the audio once
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }




    private void playSound(String soundFilePath) {
        try {
            URL soundFile = getClass().getResource(soundFilePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    private void playBackgroundMusic(String musicFilePath) {
        try {
            URL musicFile = getClass().getResource(musicFilePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(musicFile);
            bgmClip = AudioSystem.getClip();
            bgmClip.open(audioStream);
            bgmClip.loop(Clip.LOOP_CONTINUOUSLY); // Loop the music continuously
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


    private JLabel createInfoButton(String petName) {
        JLabel buttonLabel = new JLabel();
        JPanel hoverInfoPanel = new JPanel(); // Panel for hover info text
        JLabel hoverInfoLabel = new JLabel(); // Label for the text inside the panel

        // Set panel properties for hover text
        hoverInfoPanel.setLayout(new BorderLayout());
        hoverInfoPanel.setOpaque(true);
        hoverInfoPanel.setBackground(new Color(0, 0, 0, 200)); // Semi-transparent black background
        hoverInfoPanel.setVisible(false); // Initially hidden

        // Set font and style for hover text
        hoverInfoLabel.setFont(new Font("Monospaced", Font.BOLD, 20)); // Bigger font for better visibility
        hoverInfoLabel.setForeground(Color.WHITE);
        hoverInfoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        hoverInfoPanel.add(hoverInfoLabel, BorderLayout.CENTER);

        // Set icons and hover text based on petName
        ImageIcon defaultIcon;
        ImageIcon hoverIcon;

        switch (petName) {
            case "Panda":
                defaultIcon = new ImageIcon(new ImageIcon(getClass().getResource("/visuals/pandaButtonInfoDefault.png"))
                        .getImage().getScaledInstance(280, 50, Image.SCALE_SMOOTH));
                hoverIcon = new ImageIcon(new ImageIcon(getClass().getResource("/visuals/pandaButtonInfoHover.png"))
                        .getImage().getScaledInstance(280, 50, Image.SCALE_SMOOTH));
                hoverInfoLabel.setText("<html><span style='color:green;'>Peaceful Protector</span>, skilled with bamboo staff and inner energy.</html>");
                break;

            case "Wolf":
                defaultIcon = new ImageIcon(new ImageIcon(getClass().getResource("/visuals/wolfButtonIconDefault.png"))
                        .getImage().getScaledInstance(280, 50, Image.SCALE_SMOOTH));
                hoverIcon = new ImageIcon(new ImageIcon(getClass().getResource("/visuals/wolfButtonIconHover.png"))
                        .getImage().getScaledInstance(280, 50, Image.SCALE_SMOOTH));
                hoverInfoLabel.setText("<html><span style='color:blue;'>Alpha Explorer</span>, brave and resourceful in adventures.</html>");
                break;

            case "Owl":
                defaultIcon = new ImageIcon(new ImageIcon(getClass().getResource("/visuals/owlButtonIconDefault.png"))
                        .getImage().getScaledInstance(280, 50, Image.SCALE_SMOOTH));
                hoverIcon = new ImageIcon(new ImageIcon(getClass().getResource("/visuals/owlButtonIconHover.png"))
                        .getImage().getScaledInstance(280, 50, Image.SCALE_SMOOTH));
                hoverInfoLabel.setText("<html><span style='color:yellow;'>Wise Jungle Sage</span>, carrying its treasured book of secrets.</html>");
                break;

            case "Minotaur":
                defaultIcon = new ImageIcon(new ImageIcon(getClass().getResource("/visuals/minotaurButtonIconDefault.png"))
                        .getImage().getScaledInstance(280, 50, Image.SCALE_SMOOTH));
                hoverIcon = new ImageIcon(new ImageIcon(getClass().getResource("/visuals/minotaurButtonIconHover.png"))
                        .getImage().getScaledInstance(280, 50, Image.SCALE_SMOOTH));
                hoverInfoLabel.setText("<html><span style='color:#F4A460;'>Beast of Labyrinth</span>, power forged in ancient mazes and protector of forgotten paths.</html>");
                break;

            case "Tiger":
                defaultIcon = new ImageIcon(new ImageIcon(getClass().getResource("/visuals/tigerButtonIconDefault.png"))
                        .getImage().getScaledInstance(280, 50, Image.SCALE_SMOOTH));
                hoverIcon = new ImageIcon(new ImageIcon(getClass().getResource("/visuals/tigerButtonIconHover.png"))
                        .getImage().getScaledInstance(280, 50, Image.SCALE_SMOOTH));
                hoverInfoLabel.setText("<html><span style='color:orange;'>Ultimate Fury</span>, a majestic and fierce ruler of the untamed wilderness.</html>");
                break;

            case "Dragon":
                defaultIcon = new ImageIcon(new ImageIcon(getClass().getResource("/visuals/dragonButtonIconDefault.png"))
                        .getImage().getScaledInstance(280, 50, Image.SCALE_SMOOTH));
                hoverIcon = new ImageIcon(new ImageIcon(getClass().getResource("/visuals/dragonButtonIconHover.png"))
                        .getImage().getScaledInstance(280, 50, Image.SCALE_SMOOTH));
                hoverInfoLabel.setText("<html><span style='color:red;'>Ancient Flame Bearer</span>, a mythical legend of supreme destruction and mystery.</html>");
                break;

            default:
                return null;
        }

        buttonLabel.setIcon(defaultIcon);

        // Add hover behavior
        buttonLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                buttonLabel.setIcon(hoverIcon);

                // Play hover sound
                playSound("/audio/petIconButtonHover.wav");

                // Position the hover info panel dynamically near the title
                int panelWidth = 1100; // Flexible width
                int panelHeight = 30; // Flexible height
                int panelX = 510 + (900 - panelWidth) / 2; // Centered under title
                int panelY = 100; // Below the petSelectTitle

                hoverInfoPanel.setBounds(panelX, panelY, panelWidth, panelHeight);
                hoverInfoPanel.setVisible(true);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                buttonLabel.setIcon(defaultIcon);
                hoverInfoPanel.setVisible(false);
            }
        });

        add(hoverInfoPanel); // Add hover info panel to the main panel
        return buttonLabel;
    }



    private JLabel createPetSlot(String petName, String imagePath) {
        JLabel petSlot = new JLabel();
        petSlot.setPreferredSize(new Dimension(320, 420));
        petSlot.setOpaque(false);

        // Determine if the slot should be locked
        boolean isLocked = (currentPage == 1) &&
                (petName.equals("Dragon") || petName.equals("Tiger") || petName.equals("Minotaur"));

        // Determine the image to use
        String imagePathToUse = isLocked ?
                (petName.equals("Dragon") ? "/visuals/dragonSlotLock.png" :
                        petName.equals("Tiger") ? "/visuals/tigerSlotLock.png" :
                                petName.equals("Minotaur") ? "/visuals/minotaurSlotLock.png" : null)
                : imagePath;

        if (imagePathToUse == null) {
            System.err.println("No image path found for pet: " + petName);
            return petSlot; // Return an empty slot if no image is found
        }

        // Load the image and set it on the slot
        ImageIcon defaultIcon = new ImageIcon(new ImageIcon(imagePathToUse).getImage().getScaledInstance(320, 420, Image.SCALE_SMOOTH));
        petSlot.setIcon(defaultIcon);
        petSlot.setHorizontalAlignment(SwingConstants.CENTER);
        petSlot.setToolTipText(isLocked ? "Locked: " + petName : petName);

        // Add interactivity for all pet slots
        petSlot.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!isLocked) {
                    playSound("/audio/petSlotClick.wav"); // Play general pet slot click sound
                    selectedPetLabel.setText("Selected: " + petName);

                    // Play the specific pet's selection audio
                    String petAudio = "";
                    switch (petName) {
                        case "Panda":
                            petAudio = "/audio/pandaSelected.wav";
                            break;
                        case "Wolf":
                            petAudio = "/audio/wolfSelected.wav";
                            break;
                        case "Owl":
                            petAudio = "/audio/owlSelected.wav";
                            break;
                        case "Minotaur":
                            petAudio = "/audio/minotaurSelected.wav";
                            break;
                        case "Tiger":
                            petAudio = "/audio/tigerSelected.wav";
                            break;
                        case "Dragon":
                            petAudio = "/audio/dragonSelected.wav";
                            break;
                    }
                    playSound(petAudio);
                    handleGifDisplay(petName);
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                // Play hover sound
                playSound("/audio/petSlotHover.wav");

                // Change to hover icon if not locked
                if (!isLocked) {
                    petSlot.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/visuals/" + petName.toLowerCase() + "SlotHover.png"))
                            .getImage().getScaledInstance(320, 420, Image.SCALE_SMOOTH)));
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Reset to default icon
                petSlot.setIcon(defaultIcon);
            }
        });

        return petSlot;
    }



    private void handleGifDisplay(String petName) {
        // Remove all existing GIFs
        removeWolfGif();
        removeOwlGif();
        removePandaGif();
        removeMinotaurGif();
        removeTigerGif();
        removeDragonGif();

        // Display the GIF corresponding to the selected pet
        if (petName.equals("Wolf")) {
            displayWolfGif();
        }

        else if (petName.equals("Owl")) {
            displayOwlGif();
        }

        else if (petName.equals("Panda")) {
            displayPandaGif();
        }

        else if (petName.equals("Minotaur")) {
            displayMinotaurGif();
        }

        else if (petName.equals("Tiger")) {
            displayTigerGif();
        }

        else if (petName.equals("Dragon")) {
            displayDragonGif();
        }
    }



    private void displayWolfGif() {
        if (wolfGifLabel == null) {
            wolfGifLabel = new JLabel(new ImageIcon(getClass().getResource("/visuals/wolfSelectGo.gif")));
            wolfGifLabel.setBounds(770, 140, 350, 350);
            add(wolfGifLabel);
            revalidate();
            repaint();
        }
    }

    private void removeWolfGif() {
        if (wolfGifLabel != null) {
            remove(wolfGifLabel);
            wolfGifLabel = null;
            revalidate();
            repaint();
        }
    }

    private void displayOwlGif() {
        if (owlGifLabel == null) {
            owlGifLabel = new JLabel(new ImageIcon(getClass().getResource("/visuals/owlSelectGo.gif")));
            owlGifLabel.setBounds(770, 140, 350, 355);
            add(owlGifLabel);
            revalidate();
            repaint();
        }
    }

    private void removeOwlGif() {
        if (owlGifLabel != null) {
            remove(owlGifLabel);
            owlGifLabel = null;
            revalidate();
            repaint();
        }
    }

    // New methods for Panda GIF
    private JLabel pandaGifLabel; // Label to store Panda GIF

    private void displayPandaGif() {
        if (pandaGifLabel == null) {
            pandaGifLabel = new JLabel(new ImageIcon(getClass().getResource("/visuals/pandaSelectGo.gif")));
            pandaGifLabel.setBounds(770, 140, 350, 370); // Position similar to Wolf and Owl
            add(pandaGifLabel);
            revalidate();
            repaint();
        }
    }

    private void removePandaGif() {
        if (pandaGifLabel != null) {
            remove(pandaGifLabel);
            pandaGifLabel = null;
            revalidate();
            repaint();
        }
    }



    // Labels to store GIFs
    private JLabel minotaurGifLabel, tigerGifLabel, dragonGifLabel;

    // Display Minotaur GIF
    private void displayMinotaurGif() {
        if (minotaurGifLabel == null) {
            minotaurGifLabel = new JLabel(new ImageIcon(getClass().getResource("/visuals/minotaurSelectGo.gif")));
            minotaurGifLabel.setBounds(770, 140, 350, 350);
            add(minotaurGifLabel);
            revalidate();
            repaint();
        }
    }

    // Remove Minotaur GIF
    private void removeMinotaurGif() {
        if (minotaurGifLabel != null) {
            remove(minotaurGifLabel);
            minotaurGifLabel = null;
            revalidate();
            repaint();
        }
    }

    // Display Tiger GIF
    private void displayTigerGif() {
        if (tigerGifLabel == null) {
            tigerGifLabel = new JLabel(new ImageIcon(getClass().getResource("/visuals/tigerSelectGo.gif")));
            tigerGifLabel.setBounds(770, 140, 350, 350);
            add(tigerGifLabel);
            revalidate();
            repaint();
        }
    }

    // Remove Tiger GIF
    private void removeTigerGif() {
        if (tigerGifLabel != null) {
            remove(tigerGifLabel);
            tigerGifLabel = null;
            revalidate();
            repaint();
        }
    }

    // Display Dragon GIF
    private void displayDragonGif() {
        if (dragonGifLabel == null) {
            dragonGifLabel = new JLabel(new ImageIcon(getClass().getResource("/visuals/dragonSelectGo.gif")));
            dragonGifLabel.setBounds(770, 140, 400, 380);
            add(dragonGifLabel);
            revalidate();
            repaint();
        }
    }

    // Remove Dragon GIF
    private void removeDragonGif() {
        if (dragonGifLabel != null) {
            remove(dragonGifLabel);
            dragonGifLabel = null;
            revalidate();
            repaint();
        }
    }




    private JButton createNavigationButton(String defaultPath, String hoverPath, Runnable action, String clickSoundPath) {
        JButton button = new JButton(new ImageIcon(new ImageIcon(defaultPath).getImage().getScaledInstance(170, 110, Image.SCALE_SMOOTH)));
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setIcon(new ImageIcon(new ImageIcon(hoverPath).getImage().getScaledInstance(170, 110, Image.SCALE_SMOOTH)));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setIcon(new ImageIcon(new ImageIcon(defaultPath).getImage().getScaledInstance(170, 110, Image.SCALE_SMOOTH)));
            }
        });

        button.addActionListener(e -> {
            playSound(clickSoundPath); // Play the button click sound
            action.run(); // Perform the button's action
        });

        return button;
    }


    private void navigatePets(int direction) {
        // Allow navigation between two pages only
        currentPage = Math.max(0, Math.min(currentPage + direction, 1));

        // Update pets and images based on the current page
        if (currentPage == 0) {
            currentPets = STARTER_PETS;
            currentImages = STARTER_IMAGES;
        } else if (currentPage == 1) {
            currentPets = UNLOCKABLE_PETS;
            currentImages = UNLOCKABLE_IMAGES;
        }

        updatePetSlots();
    }



    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ImageIcon backgroundIcon = new ImageIcon(BACKGROUND_PATH);
        g.drawImage(backgroundIcon.getImage(), 0, 0, getWidth(), getHeight(), this);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Pet Select");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1920, 1080);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.add(new PetSelect());
        frame.setVisible(true);
    }
}
