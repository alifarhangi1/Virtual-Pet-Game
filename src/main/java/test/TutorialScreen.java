// Package declaration indicating this class belongs to the 'screens' package
package test;

// Importing Swing components for creating the graphical user interface
import javax.swing.*;

// Importing the LineBorder class for creating customizable borders
import javax.swing.border.LineBorder;

// Importing AWT components for layouts, colors, and basic GUI operations
import java.awt.*;

// Importing event classes to handle mouse interactions (e.g., clicks, hovers)
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

// Importing classes for playing sound effects and background music
import javax.sound.sampled.*;

// Importing classes for handling file input/output operations
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;


public class TutorialScreen extends JPanel {
    // Index to track the current slide being displayed in the tutorial
    private int currentSlideIndex = 0;

    /**
     * Array containing file paths to the images for the tutorial slides.
     * Each image corresponds to a specific topic or step in the tutorial.
     */
    private final String[] slideImages = {
            "/visuals/TutorialPic1.png",
            "/visuals/TutorialPic2.png",
            "/visuals/TutorialPic3.png",
            "/visuals/TutorialPic4.png",
            "/visuals/TutorialPic5.png",
            "/visuals/TutorialPic6.png",
            "/visuals/TutorialPic7.png",
            "/visuals/TutorialPic8.png",
            "/visuals/TutorialPic9.png",
            "/visuals/TutorialPic10.png",
            "/visuals/TutorialPic11.png",
            "/visuals/TutorialPic12.png",
            "/visuals/TutorialPic13.png",
            "/visuals/TutorialPic14.png",
            "/visuals/TutorialPic15.png",
            "/visuals/TutorialPic16.png",
            "/visuals/TutorialPic17.png",
            "/visuals/TutorialPic18.png",
            "/visuals/TutorialPic19.png",
            "/visuals/TutorialPic20.png",
            "/visuals/TutorialPic21.png",
            "/visuals/TutorialPic22.png",
            "/visuals/TutorialPic23.png",
            "/visuals/TutorialPic24.png",
            "/visuals/TutorialPic25.png",
            "/visuals/TutorialPic26.png",
            "/visuals/TutorialPic27.png",
            "/visuals/TutorialPic28.png",
            "/visuals/TutorialPic29.png",
            "/visuals/TutorialPic30.png",
            "/visuals/TutorialPic31.png",
            "/visuals/TutorialPic32.png",
            "/visuals/TutorialPic33.png",
            "/visuals/TutorialPic34.png",
            "/visuals/TutorialPic35.png",
            "/visuals/TutorialPic36.png",
            "/visuals/TutorialPic37.png",
            "/visuals/TutorialPic38.png"


    };

    /**
     * Array containing descriptive text for each tutorial slide.
     * These texts are displayed alongside their corresponding slide images
     * and provide detailed explanations of the tutorial steps.
     */
    private final String[] slideTexts = {
            "Welcome to the Pet Quest Tutorial! Here, you will learn about the three main options: Play, Tutorial, and Parental Control.",
            "The Play button is the gateway to your adventure! Start your journey, explore exciting worlds, and face thrilling challenges.",
            "The Tutorial button, which you are currently viewing, guides you through the features and gameplay of Pet Quest.",
            "The Parental Control button allows guardians to set time limits and manage your gaming experience, ensuring a balanced playtime.",
            "Under the Play menu, you can choose between New Game or Load Game. Starting a New Game allows you to set out on a fresh adventure and dive into the exciting world of Pet Quest from the very beginning.",
            "The Load Game option lets you pick up right where you left off, continuing your epic journey with all your progress and achievements intact.",
            "The Back button provides an easy way to return to the main menu, allowing you to perfectly switch between the three main options of Pet Quest.",
            "Upon selecting \"Load Game,\" you will transition to the Main Menu, where you can resume any previously saved progress. The Main Menu offers four options for exploration and interaction: Pet Roster, Mini Game, Inventory, and Player Score.",
            "Use the \"Back\" Button to navigate to the previous screen.",
            "Use the \"Next\" Button to move forward to the next screen.",
            "Use the \"Action\" Button to select your desired option. In Pet Roster, your adventure begins. Here, you are greeted by three unique starter pets, each with distinct traits and personalities, ready to join you on your exciting journey.",
            "In the Mini Game, you are offered three unique and thrilling levels: Maze Madness, where you navigate a maze to find rare treasures; Dragon Duel, where you battle a fierce dragon to claim victory; and Kung Fu Chaos, where you fight enemies to save the village and restore peace.",
            "In the Inventory section, you can view and manage your collection of items, gifts, and newly unlocked pets, all earned through Mini Game quests. Use these resources strategically to enhance your adventure and gain an edge in your journey.",
            "In the Player Score section, you can track your progress and view your achievements, allowing you to monitor your performance and celebrate your successes.",
            "In one of the Mini Game levels, you control your character using the standard WSAD keys: W to move up, S to move down, A to move left, and D to move right, ensuring a fluid gameplay.",
            "Another level in the Mini Game is the Dragon Duel, where you face an intense and challenging battle against the formidable dragon, Kaeltharion the Abyssbound. To win, you must rapidly click the \"Attack\" button to reflect damage on the BOSS.",
            "Beware! If you stop rapidly clicking the \"Attack\" button, the dragon will counter, striking back and draining your health. You must stay strong and fight to the end, never stop until you claim victory and defeat the mighty boss!",
            "As you rapidly click, you’ll see the dragon’s health bar slowly decrease with each attack, while your own health bar is displayed below. Keep a close eye on both your health and the dragon's to strategize effectively. Prepare to endure the wrath of the formidable Kaeltharion the Abyssbound, and fight with all your might to determine who will emerge victorious in this intense and epic battle.",
            "In Pet Select, this is where your adventure begins. Here, you are welcomed by three unique starter pets, each with their own special traits, ready to join you on your journey.",
            "On the next page, you’ll find three locked mystery pets. Unlock these special pets by completing exciting and rewarding quests.",
            "Choose from three unique starter pets, each featuring a distinctive personality, dynamic visiuals and sound, and a stunning evolved form that sets them apart on your journey.",
            "Hover over each pet's name icon to uncover fun, unique details and hidden traits, helping you discover the perfect companion for your journey.",
            "After selecting your pet, you transition to the Pet Interaction Screen, a lively hub where you will care for and bond with your new companion. Here, you'll perform a variety of activities to ensure your pet stays healthy, happy, and thriving. Whether it's feeding them delicious treats, helping them rest and recover, or keeping them active and energized, this is where your journey together truly begins.",
            "Feed/Give Gift - Treat your pet to a delightful item that boosts their happiness, fills their tummy, and maybe… sparks an evolution!",
            "Sleep - Tuck your pet in for some well-deserved rest. During this time, they'll recharge while interaction takes a pause.",
            "Take to Vet - Keep your pet in peak condition! A quick visit to the vet restores their health and ensures they're always at their best.",
            "Exercise - Get your pet moving! Exercise improves their health, builds stamina, and keeps them full of energy for adventures ahead.",
            "Play - Dive into fun activities with your pet, bringing endless joy and boosting their happiness bar to new heights!",
            "Name Your Pet - Personalize your companion with a unique name, making them truly your own. Let their legacy begin!",
            "Back Button - Exit the pet interaction screen and prepare for the next exciting chapter in your journey together!",
            "To access the Parental Controls menu, simply enter the correct Parental Passcode. A pop-up window will prompt you to input the code. Keep it ready and secure for seamless access!",
            "Inside the Parental Controls menu, you'll find the Time Limit Toggle Switch conveniently located on the left panel.",
            "Turn the switch ON to enable the time limit feature. Without activating this switch, setting a time limit won't be possible.",
            "After enabling the time limit, enter your desired duration (in minutes) in the Time Input Box. For instance, type \"30\" for a 30-minute session. Then, click the Set Timer button to activate the limit. Make sure to input a valid number to proceed!",
            "A confirmation popup will appear, confirming that your timer has been successfully activated.",
            "To return your pet to your inventory, click the \"Revive Pets\" button located on the left panel. A pop-up message will confirm that your pet has been successfully revived.",
            "When the time limit expires, a notification appears, giving you the option to either exit the program or return to the main menu, allowing for a seamless decision on your next step.",
            "Congratulations! You've reached the end of the tutorial. You're now fully ready to embark on an exciting journey with your pet and confidently navigate all the features and controls. It's time to dive in, explore, and create unforgettable memories, your adventure awaits in Pet Quest!"



    };

    // JLabel to display the current tutorial slide image
    private JLabel slideImageLabel;

    // JTextArea to display the descriptive text for the current tutorial slide
    private JTextArea slideTextArea;

    // JLabel for the "Back" button, typically used to return to the main menu or previous screen
    private JLabel backButton;

    // JButton to navigate to the next tutorial slide
    private JButton nextButton;

    // JButton to navigate to the previous tutorial slide
    private JButton previousButton;

    // ImageIcon to hold the background image for the tutorial screen
    private ImageIcon backgroundIcon;

    /**
     * Constructs the tutorial screen for the application.
     * The screen includes a slide image, descriptive text, navigation buttons,
     * and a background image. Users can navigate through the tutorial slides using
     * the "Next" and "Previous" buttons, or return to the title screen with the "Back" button.
     */
    public TutorialScreen() {
        // Set the layout to null for manual positioning of components
        setLayout(null);

        // Load the background image for the tutorial screen
        backgroundIcon = new ImageIcon(getClass().getResource("/visuals/background.gif"));

        // Slide Image Label: Displays the current tutorial slide image
        slideImageLabel = new JLabel();
        slideImageLabel.setHorizontalAlignment(SwingConstants.CENTER); // Center-align the image
        slideImageLabel.setBounds(460, 200, 1000, 600); // Position the image in the center of the screen
        slideImageLabel.setBorder(new LineBorder(new Color(255, 215, 0), 5)); // Add a gold border for visual enhancement
        add(slideImageLabel); // Add the slide image label to the screen

        // Slide Text Area: Displays descriptive text for the current tutorial slide
        slideTextArea = new JTextArea();
        slideTextArea.setEditable(false); // Prevent user input
        slideTextArea.setWrapStyleWord(true); // Wrap words at word boundaries
        slideTextArea.setLineWrap(true); // Enable line wrapping
        slideTextArea.setForeground(Color.WHITE); // Set text color to white
        slideTextArea.setFont(new Font("Monospaced", Font.BOLD, 20)); // Use a bold, monospaced font for better readability
        slideTextArea.setOpaque(false); // Make the text area background transparent
        slideTextArea.setBounds(460, 820, 1000, 100); // Position below the slide image
        add(slideTextArea); // Add the text area to the screen

        // Background Panel for Text: Provides a semi-transparent background for the text area
        JPanel textBackgroundPanel = new JPanel();
        textBackgroundPanel.setBounds(460, 820, 1000, 160); // Same size as the text area
        textBackgroundPanel.setBackground(new Color(0, 0, 0, 150)); // Semi-transparent black background
        textBackgroundPanel.setOpaque(true); // Ensure the panel is visible
        textBackgroundPanel.setLayout(new BorderLayout()); // Use BorderLayout to align components
        textBackgroundPanel.add(slideTextArea, BorderLayout.CENTER); // Add the text area to the panel
        add(textBackgroundPanel); // Add the text background panel to the screen

        // Back Button: Navigates back to the title screen
        backButton = createBackButton();
        backButton.setBounds(50, 50, 160, 100); // Position in the top-left corner
        add(backButton); // Add the back button to the screen

        // Previous Button: Navigates to the previous tutorial slide
        previousButton = createNavigationButton(
                "/visuals/backArrowButtonDefault.png",  // Default icon path
                "/visuals/backArrowButtonHover.png",    // Hover icon path
                -1 // Direction for navigation (-1 for previous slide)
        );
        previousButton.setBounds(270, 420, 170, 110); // Position to the left of the slide image
        add(previousButton); // Add the previous button to the screen

        // Next Button: Navigates to the next tutorial slide
        nextButton = createNavigationButton(
                "/visuals/nextArrowButtonDefault.png",  // Default icon path
                "/visuals/nextArrowButtonHover.png",    // Hover icon path
                1 // Direction for navigation (1 for next slide)
        );
        nextButton.setBounds(1480, 420, 170, 110); // Position to the right of the slide image
        add(nextButton); // Add the next button to the screen

        // Initialize the first slide content (image and text)
        updateSlideContent();
    }


    /**
     * Paints the background image for the tutorial screen.
     * Overrides the default `paintComponent` method to draw a custom background that scales
     * dynamically to fit the entire panel.
     *
     * @param g The `Graphics` object used for drawing.
     */
    @Override
    protected void paintComponent(Graphics g) {
        // Call the superclass method to ensure proper rendering of the panel
        super.paintComponent(g);

        // Draw the background image, scaled to the current width and height of the panel
        g.drawImage(backgroundIcon.getImage(), 0, 0, getWidth(), getHeight(), this);
    }

    /**
     * Creates an interactive "Back" button for navigating back to the title screen.
     * The button includes hover effects and plays a click sound when clicked.
     * On click, it transitions to the "titleScreen" using a `CardLayout`.
     *
     * @return A JLabel configured as the "Back" button.
     */
    private JLabel createBackButton() {
        // Create a new JLabel to represent the back button
        JLabel backButton = new JLabel();

        // Set the default icon for the back button, resized to 160x100 pixels
        backButton.setIcon(new ImageIcon(resizeImage(String.valueOf(getClass().getResource("/visuals/woodButtonDefault.png")), 160, 100)));

        // Set the cursor to a hand icon to indicate interactivity
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Add mouse listeners for hover and click behaviors
        backButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                // Change the icon to the hover version when the mouse enters the button
                backButton.setIcon(new ImageIcon(resizeImage(String.valueOf(getClass().getResource("/visuals/woodButtonHover.png")), 160, 100)));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Revert to the default icon when the mouse exits the button
                backButton.setIcon(new ImageIcon(resizeImage(String.valueOf(getClass().getResource("/visuals/woodButtonDefault.png")), 160, 100)));
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                // Play the back button click sound
                playSound("/audio/back_button_click.wav");

                // Transition to the "titleScreen" using the parent container's CardLayout
                CardLayout cardLayout = (CardLayout) getParent().getLayout();
                cardLayout.show(getParent(), "titleScreen");
            }
        });

        // Return the configured back button
        return backButton;
    }

    /**
     * Creates a navigation button for moving between slides.
     * The button includes hover effects, click sounds, and an action listener to handle navigation logic.
     *
     * @param defaultIconPath The file path for the default button icon.
     * @param hoverIconPath   The file path for the hover button icon.
     * @param direction       The direction of navigation: -1 for previous, 1 for next.
     * @return A configured `JButton` with navigation functionality.
     */
    private JButton createNavigationButton(String defaultIconPath, String hoverIconPath, int direction) {
        // Create a button with the default icon resized to 170x110 pixels
        JButton button = new JButton(new ImageIcon(resizeImage(String.valueOf(getClass().getResource(defaultIconPath)), 170, 110)));

        // Remove default button decorations for a cleaner look
        button.setBorderPainted(false);  // Disable border painting
        button.setContentAreaFilled(false);  // Disable background fill
        button.setFocusPainted(false);  // Disable focus highlighting

        // Set the cursor to a hand icon to indicate interactivity
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Add an action listener to handle navigation and sound effects
        button.addActionListener(e -> {
            if (direction == -1) {
                // Play the back button sound for navigating to the previous slide
                playSound("/audio/backButtonClickPS.wav");
            } else if (direction == 1) {
                // Play the next button sound for navigating to the next slide
                playSound("/audio/nextButtonClickPS.wav");
            }

            // Navigate to the next or previous slide based on the direction
            navigateSlides(direction);
        });

        // Add a mouse listener to handle hover effects
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                // Change the button icon to the hover version when the mouse enters
                button.setIcon(new ImageIcon(resizeImage(String.valueOf(getClass().getResource(hoverIconPath)), 170, 110)));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Revert to the default icon when the mouse exits
                button.setIcon(new ImageIcon(resizeImage(String.valueOf(getClass().getResource(defaultIconPath)), 170, 110)));
            }
        });

        // Return the fully configured navigation button
        return button;
    }

    /**
     * Navigates to the next or previous slide based on the direction provided.
     * Wraps around the slide index to loop through the slides when reaching the start or end.
     *
     * @param direction An integer specifying the navigation direction:
     *                  -1 for the previous slide, 1 for the next slide.
     */
    private void navigateSlides(int direction) {
        // Adjust the current slide index by the given direction
        currentSlideIndex += direction;

        // Wrap around to the last slide if navigating backward from the first slide
        if (currentSlideIndex < 0) {
            currentSlideIndex = slideImages.length - 1; // Set to the last slide index
        }

        // Wrap around to the first slide if navigating forward from the last slide
        else if (currentSlideIndex >= slideImages.length) {
            currentSlideIndex = 0; // Reset to the first slide index
        }

        // Update the displayed slide content
        updateSlideContent();
    }


    /**
     * Updates the content of the tutorial screen based on the current slide index.
     * Sets the slide image and corresponding descriptive text.
     */
    private void updateSlideContent() {
        // Load and set the current slide image
        ImageIcon slideIcon = new ImageIcon(resizeImage(String.valueOf(getClass().getResource(slideImages[currentSlideIndex])), 1000, 600));
        slideImageLabel.setIcon(slideIcon); // Set the slide image in the label

        // Set the corresponding text for the current slide
        slideTextArea.setText(slideTexts[currentSlideIndex]);
    }

    /**
     * Resizes an image to the specified dimensions.
     *
     * @param imagePath The file path of the image to resize.
     * @param width     The desired width of the resized image.
     * @param height    The desired height of the resized image.
     * @return A resized `Image` object.
     */
    private Image resizeImage(String imagePath, int width, int height) {
        URL imageUrl = getClass().getResource(imagePath);
        if (imageUrl == null) {
            System.err.println("Image not found: " + imagePath);
            return new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        }
        Image img = new ImageIcon(imageUrl).getImage();
        return img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
    }


    /**
     * Plays a sound from the specified file path.
     * Used for feedback during interactions like button clicks or navigation.
     *
     * @param soundFilePath The file path of the sound file to be played.
     */
    private void playSound(String soundFilePath) {
        try {
            // Load the sound file
            URL soundFile = getClass().getResource(soundFilePath);

            // Create an audio input stream from the sound file
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);

            // Create a clip to play the audio
            Clip clip = AudioSystem.getClip();

            // Open and play the clip
            clip.open(audioStream);
            clip.start();
        }

        // Handle exceptions for unsupported audio formats, I/O errors, or unavailable lines
        catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace(); // Print the stack trace for debugging
        }
    }

    public static void main(String[] args) {
        // Ensure Swing components are created on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            // Create the main application frame
            JFrame frame = new JFrame("Pet Quest Tutorial");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // Set the frame to full screen
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

            // Create an instance of TutorialScreen
            TutorialScreen tutorialScreen = new TutorialScreen();

            // Add the tutorial screen to the frame
            frame.add(tutorialScreen);

            // Make the frame visible
            frame.setVisible(true);
        });
    }

}
