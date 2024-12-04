// Package declaration indicating that this class is part of the 'screens' package
package test;

// Importing necessary Swing components for building the graphical user interface
import javax.swing.*;

// Importing classes for playing sound effects and background music
import javax.sound.sampled.*;

// Importing classes for working with layout and drawing components
import java.awt.*;

// Importing event classes for handling mouse interactions
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

// Importing classes for file operations
import java.io.File;
import java.io.IOException;


public class MainMenuScreen extends JPanel {
    // Variable to track the currently displayed slide index
    private int currentSlideIndex = 0;

    /**
     * Array containing the file paths for slide images used in the application.
     * Each element represents an image associated with a specific screen or feature.
     */
    private final String[] slideImages = {
            "src/assets/visuals/PetSelectScreenMM.png",  // Slide for the Pet Select Screen
            "src/assets/visuals/MiniGameScreenMM.png",   // Slide for the Mini Game Screen
            "src/assets/visuals/InventoryScreenMM.png",  // Slide for the Inventory Screen
            "src/assets/visuals/PlayerScoreScreenMM.png" // Slide for the Player Score Screen
    };

    /**
     * Two-dimensional array containing file paths for button icons.
     * Each row represents a set of icons (default and hover) for a specific button.
     * The first column contains default icons, and the second column contains hover icons.
     */
    private final String[][] buttonIcons = {
            { // Icons for the "Pet Roster" button
                    "src/assets/visuals/petRosterButtonDefault.png",  // Default icon
                    "src/assets/visuals/petRosterButtonHover.png"    // Hover icon
            },
            { // Icons for the "Mini Game" button
                    "src/assets/visuals/miniGameButtonDefault.png",  // Default icon
                    "src/assets/visuals/miniGameButtonHover.png"     // Hover icon
            },
            { // Icons for the "Inventory" button
                    "src/assets/visuals/inventoryButtonDefault.png", // Default icon
                    "src/assets/visuals/inventoryButtonHover.png"    // Hover icon
            },
            { // Icons for the "Player Score" button
                    "src/assets/visuals/playerScoreButtonDefault.png", // Default icon
                    "src/assets/visuals/playerScoreButtonHover.png"    // Hover icon
            }
    };

    // JLabel to display the current slide image in the UI
    private JLabel slideImageLabel;

    // JLabel for the title displayed above the slides
    private JLabel titleLabel; // Title label

    // JButton for navigating to the previous slide
    private JButton previousButton;

    // JButton for navigating to the next slide
    private JButton nextButton;

    // JLabel for the "Back" button, typically used to return to a previous menu or screen
    private JLabel backButton;

    // JButton for performing an action related to the current slide (e.g., starting a game or opening a menu)
    private JButton actionButton; // Button below each slide

    // ImageIcon for the background image of the screen
    private ImageIcon backgroundIcon;


    /**
     * Constructs the main menu screen for the application.
     * The screen includes a title, slide image, navigation buttons (back, previous, next),
     * and an action button that triggers actions based on the current slide.
     * The layout is manually positioned, and the first slide content is initialized.
     */
    public MainMenuScreen() {
        // Set layout to null for manual positioning of components
        setLayout(null);

        // Load the background image for the screen
        backgroundIcon = new ImageIcon("src/assets/visuals/background.gif");

        // Title Label: Displays the title of the main menu at the top center
        titleLabel = new JLabel(new ImageIcon(resizeImage("src/assets/visuals/MainMenuTitle.png", 900, 80)));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER); // Center-align the title
        titleLabel.setBounds(550, 70, 900, 80); // Set position and size for the title
        add(titleLabel); // Add the title label to the screen

        // Slide Image Label: Displays an image for the current slide (feature previews)
        slideImageLabel = new JLabel();
        slideImageLabel.setHorizontalAlignment(SwingConstants.CENTER); // Center-align the slide image
        slideImageLabel.setBounds(460, 120, 1100, 750); // Position the slide image below the title
        add(slideImageLabel); // Add the slide image label to the screen

        // Back Button: Allows the user to navigate back to the previous menu or screen
        backButton = createBackButton();
        backButton.setBounds(5, 50, 160, 100); // Position the back button in the top-left corner
        add(backButton); // Add the back button to the screen

        // Previous Button: Navigates to the previous slide
        previousButton = createNavigationButton(
                "src/assets/visuals/backArrowButtonDefault.png",  // Path to the default icon
                "src/assets/visuals/backArrowButtonHover.png",    // Path to the hover icon
                -1 // Direction for navigation (-1 for previous slide)
        );
        previousButton.setBounds(270, 420, 170, 110); // Position the button to the left of the slide image
        add(previousButton); // Add the previous button to the screen

        // Next Button: Navigates to the next slide
        nextButton = createNavigationButton(
                "src/assets/visuals/nextArrowButtonDefault.png",  // Path to the default icon
                "src/assets/visuals/nextArrowButtonHover.png",    // Path to the hover icon
                1 // Direction for navigation (1 for next slide)
        );
        nextButton.setBounds(1580, 420, 170, 110); // Position the button to the right of the slide image
        add(nextButton); // Add the next button to the screen

        // Action Button: Performs an action related to the current slide (e.g., starts a game or opens a menu)
        actionButton = new JButton();
        actionButton.setBounds(820, 820, 330, 118); // Position the button below the slide image
        actionButton.setBorderPainted(false); // Remove the button's border for a cleaner look
        actionButton.setContentAreaFilled(false); // Make the button's background transparent
        actionButton.setFocusPainted(false); // Remove the focus indicator
        actionButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Set the cursor to a hand icon for interactivity
        add(actionButton); // Add the action button to the screen

        // Initialize the first slide with its content (image, associated actions, etc.)
        updateSlideContent();
    }


    /**
     * Paints the background image for the panel.
     * This method overrides the default `paintComponent` behavior to draw a custom background
     * image that resizes dynamically to fit the panel's dimensions.
     *
     * @param g The `Graphics` object used for drawing the background image.
     */
    @Override
    protected void paintComponent(Graphics g) {
        // Call the superclass method to ensure proper rendering of the panel
        super.paintComponent(g);

        // Draw the background image to fill the entire panel
        g.drawImage(backgroundIcon.getImage(), 0, 0, getWidth(), getHeight(), this);
    }


    /**
     * Creates an interactive "Back" button.
     * The button has hover and click effects, including visual icon changes and sound effects.
     * When clicked, it navigates back to the "SaveScreen" using a `CardLayout`.
     *
     * @return A JLabel configured as the "Back" button.
     */
    private JLabel createBackButton() {
        // Create a new JLabel to represent the back button
        JLabel backButton = new JLabel();

        // Set the default icon for the back button
        backButton.setIcon(new ImageIcon(resizeImage("src/assets/visuals/woodButtonDefault.png", 160, 100)));

        // Set the cursor to a hand icon to indicate interactivity
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Add mouse listeners for hover and click behavior
        backButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                // Play hover sound effect when the mouse enters the button
                playHoverSound();

                // Change to the hover icon
                backButton.setIcon(new ImageIcon(resizeImage("src/assets/visuals/woodButtonHover.png", 160, 100)));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Revert to the default icon when the mouse exits the button
                backButton.setIcon(new ImageIcon(resizeImage("src/assets/visuals/woodButtonDefault.png", 160, 100)));
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                // Play the click sound effect when the back button is clicked
                playSound("src/assets/audio/back_button_click.wav");

                // Navigate back to the "SaveScreen" using the parent container's CardLayout
                CardLayout cardLayout = (CardLayout) getParent().getLayout();
                cardLayout.show(getParent(), "saveScreen");
            }
        });

        // Return the configured back button
        return backButton;
    }


    /**
     * Creates a navigation button for sliding between content.
     * The button includes hover effects, click sounds, and an action listener for navigation.
     *
     * @param defaultIconPath The file path for the default button icon.
     * @param hoverIconPath   The file path for the hover button icon.
     * @param direction       The direction of navigation: -1 for previous, 1 for next.
     * @return A configured JButton with navigation functionality.
     */
    private JButton createNavigationButton(String defaultIconPath, String hoverIconPath, int direction) {
        // Create a new JButton with the default icon resized to 170x110 pixels
        JButton button = new JButton(new ImageIcon(resizeImage(defaultIconPath, 170, 110)));

        // Remove default button decorations for a cleaner look
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);

        // Set the cursor to a hand icon to indicate interactivity
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Add an action listener for button clicks
        button.addActionListener(e -> {
            // Play the appropriate sound effect based on the navigation direction
            if (direction == -1) {
                playSound("src/assets/audio/backButtonClickPS.wav"); // Back button sound
            } else if (direction == 1) {
                playSound("src/assets/audio/nextButtonClickPS.wav"); // Next button sound
            }

            // Navigate to the next or previous slide
            navigateSlides(direction);
        });

        // Add a mouse listener for hover effects
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                // Play the hover sound effect and change the icon to the hover icon
                playHoverSound();
                button.setIcon(new ImageIcon(resizeImage(hoverIconPath, 170, 110)));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Revert to the default icon when the mouse exits the button
                button.setIcon(new ImageIcon(resizeImage(defaultIconPath, 170, 110)));
            }
        });

        // Return the fully configured navigation button
        return button;
    }


    /**
     * Navigates between slides based on the specified direction.
     * Handles wrapping around when reaching the beginning or end of the slides.
     *
     * @param direction The direction of navigation: -1 for previous, 1 for next.
     */
    private void navigateSlides(int direction) {
        // Update the current slide index based on the direction
        currentSlideIndex += direction;

        // Wrap around if the index goes out of bounds
        if (currentSlideIndex < 0) {
            currentSlideIndex = slideImages.length - 1; // Go to the last slide
        } else if (currentSlideIndex >= slideImages.length) {
            currentSlideIndex = 0; // Go to the first slide
        }

        // Update the slide content to reflect the new index
        updateSlideContent();
    }


    /**
     * Updates the content of the slide based on the current slide index.
     * This includes updating the slide image and configuring the action button
     * with the appropriate icons and hover/click behaviors for the current slide.
     */
    private void updateSlideContent() {
        // Update the slide image using the current slide index
        ImageIcon slideIcon = new ImageIcon(resizeImage(slideImages[currentSlideIndex], 1100, 600));
        slideImageLabel.setIcon(slideIcon); // Set the updated image to the slide image label

        // Retrieve the button icons for the current slide
        String[] icons = buttonIcons[currentSlideIndex];
        ImageIcon defaultIcon = new ImageIcon(resizeImage(icons[0], 330, 118)); // Default button icon
        ImageIcon hoverIcon = new ImageIcon(resizeImage(icons[1], 330, 118));   // Hover button icon

        // Update the action button's default icon
        actionButton.setIcon(defaultIcon);

        // Add hover and click behaviors to the action button
        actionButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                // Change to hover icon and play hover sound when mouse enters the button
                playHoverSound();
                actionButton.setIcon(hoverIcon);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Revert to default icon when mouse exits the button
                actionButton.setIcon(defaultIcon);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                // Play button click sound when the action button is clicked
                playSound("src/assets/audio/button_click.wav");

                // Placeholder for slide-specific action logic
                System.out.println("Action button clicked for slide " + currentSlideIndex);
            }
        });
    }

    /**
     * Resizes an image to the specified width and height.
     *
     * @param imagePath The file path of the image to resize.
     * @param width     The desired width of the resized image.
     * @param height    The desired height of the resized image.
     * @return A resized `Image` object.
     */
    private Image resizeImage(String imagePath, int width, int height) {
        // Load the image from the specified file path
        Image img = new ImageIcon(imagePath).getImage();

        // Scale the image to the desired dimensions using smooth scaling
        return img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
    }


    /**
     * Plays the hover sound effect when the mouse enters a button or interactive component.
     * The method loads the sound file, creates an audio stream, and plays the clip.
     * This method is specific to the hover sound effect.
     */
    private void playHoverSound() {
        try {
            // Specify the path to the hover sound file
            File audioFile = new File("src/assets/audio/menu_hover.wav");

            // Create an audio input stream from the file
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);

            // Create a clip to play the audio
            Clip clip = AudioSystem.getClip();

            // Open the audio stream and start playing the clip
            clip.open(audioStream);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            // Print the stack trace for debugging if an error occurs
            e.printStackTrace();
        }
    }


    /**
     * Plays a sound effect from the specified file path.
     * This method can be used for various sound effects like button clicks or other interactions.
     *
     * @param soundFilePath The file path of the sound file to be played.
     */
    private void playSound(String soundFilePath) {
        try {
            // Specify the path to the sound file
            File soundFile = new File(soundFilePath);

            // Create an audio input stream from the file
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);

            // Create a clip to play the audio
            Clip clip = AudioSystem.getClip();

            // Open the audio stream and start playing the clip
            clip.open(audioStream);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            // Print the stack trace for debugging if an error occurs
            e.printStackTrace();
        }
    }

}
