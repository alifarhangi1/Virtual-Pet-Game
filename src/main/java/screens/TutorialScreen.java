// Package declaration indicating this class belongs to the 'screens' package
package screens;

// Importing Swing components for creating the graphical user interface
import managers.ScreenManager;

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
import java.io.IOException;
import java.net.URL;

/**
 * A Swing-based tutorial screen for the Pet Quest game that displays a series of tutorial slides
 * with images, descriptive text, and navigation buttons.
 *
 * The tutorial provides comprehensive guidance on game features, including:
 * - Main menu options
 * - Game modes
 * - Pet interaction
 * - Gameplay mechanics
 * - Parental controls
 * @author Yazan
 * @version 1
 */
public class TutorialScreen extends JPanel
{
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

    private JLabel slideImageLabel;
    private JTextArea slideTextArea;
    private JLabel backButton;
    private JButton nextButton;
    private JButton previousButton;
    private ImageIcon backgroundIcon;
    private ScreenManager screenManager;

    /**
     * Constructs the TutorialScreen, initializing the user interface with:
     * - A background image
     * - Navigation buttons (Previous, Next)
     * - A back button
     * - A slide image display
     * - A text area for slide descriptions
     *
     * Sets up the layout, styling, and initial content for the tutorial screen.
     */
    public TutorialScreen() {
        setLayout(new BorderLayout());
        screenManager = ScreenManager.getInstance();

        // Load background image using getResource()
        backgroundIcon = new ImageIcon(getClass().getResource("/visuals/background.gif"));

        // Top Panel for Back Button
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setOpaque(false);
        backButton = createBackButton();
        topPanel.add(backButton);
        add(topPanel, BorderLayout.NORTH);

        // Central Panel for Slide and Navigation Buttons
        JPanel centralPanel = new JPanel(new BorderLayout());
        centralPanel.setOpaque(false);

        // Previous Button
        previousButton = createNavigationButton(
                "/visuals/backArrowButtonDefault.png",
                "/visuals/backArrowButtonHover.png",
                -1
        );
        centralPanel.add(previousButton, BorderLayout.WEST);

        // Next Button
        nextButton = createNavigationButton(
                "/visuals/nextArrowButtonDefault.png",
                "/visuals/nextArrowButtonHover.png",
                1
        );
        centralPanel.add(nextButton, BorderLayout.EAST);

        // Slide Content Panel
        JPanel slideContentPanel = new JPanel(new BorderLayout());
        slideContentPanel.setOpaque(false);

        // Slide Image Label
        slideImageLabel = new JLabel();
        slideImageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        slideImageLabel.setBorder(new LineBorder(new Color(255, 215, 0), 5));
        slideContentPanel.add(slideImageLabel, BorderLayout.CENTER);

        // Slide Text Area
        slideTextArea = new JTextArea(5, 50);
        slideTextArea.setEditable(false);
        slideTextArea.setWrapStyleWord(true);
        slideTextArea.setLineWrap(true);
        slideTextArea.setForeground(Color.WHITE);
        slideTextArea.setFont(new Font("Monospaced", Font.BOLD, 20));
        slideTextArea.setOpaque(false);

        // Background Panel for Text
        JPanel textBackgroundPanel = new JPanel(new BorderLayout());
        textBackgroundPanel.setBackground(new Color(0, 0, 0, 150));
        textBackgroundPanel.setOpaque(true);
        textBackgroundPanel.add(slideTextArea, BorderLayout.CENTER);

        slideContentPanel.add(textBackgroundPanel, BorderLayout.SOUTH);
        centralPanel.add(slideContentPanel, BorderLayout.CENTER);

        add(centralPanel, BorderLayout.CENTER);

        // Initialize the first slide content
        updateSlideContent();
    }

    /**
     * Overrides the default painting method to draw the background image.
     * Ensures the background image scales to fill the entire panel.
     *
     * @param g the Graphics context used for painting
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundIcon.getImage(), 0, 0, getWidth(), getHeight(), this);
    }

    /**
     * Creates the back button with custom styling and interaction.
     * Handles mouse hover and click events for the button.
     *
     * @return a JLabel configured as the back button
     */
    private JLabel createBackButton() {
        JLabel backButton = new JLabel();
        backButton.setIcon(new ImageIcon(resizeImage("/visuals/woodButtonDefault.png", 160, 100)));
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        backButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                backButton.setIcon(new ImageIcon(resizeImage("/visuals/woodButtonHover.png", 160, 100)));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                backButton.setIcon(new ImageIcon(resizeImage("/visuals/woodButtonDefault.png", 160, 100)));
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                playSound("/audio/back_button_click.wav");
                screenManager.showScreen("title");
            }
        });

        return backButton;
    }

    /**
     * Creates navigation buttons (Previous and Next) with custom styling and interactions.
     *
     * @param defaultIconPath path to the default button icon
     * @param hoverIconPath path to the hover state button icon
     * @param direction navigation direction (-1 for previous, 1 for next)
     * @return a JButton configured for slide navigation
     */
    private JButton createNavigationButton(String defaultIconPath, String hoverIconPath, int direction) {
        JButton button = new JButton(new ImageIcon(resizeImage(defaultIconPath, 170, 110)));
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addActionListener(e -> {
            if (direction == -1) {
                playSound("/audio/backButtonClickPS.wav");
            } else if (direction == 1) {
                playSound("/audio/nextButtonClickPS.wav");
            }
            navigateSlides(direction);
        });

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setIcon(new ImageIcon(resizeImage(hoverIconPath, 170, 110)));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setIcon(new ImageIcon(resizeImage(defaultIconPath, 170, 110)));
            }
        });

        return button;
    }

    /**
     * Navigates through tutorial slides based on the given direction.
     * Handles circular navigation, wrapping around to the first or last slide when appropriate.
     *
     * @param direction navigation direction (-1 for previous, 1 for next)
     */
    private void navigateSlides(int direction) {
        currentSlideIndex += direction;

        if (currentSlideIndex < 0) {
            currentSlideIndex = slideImages.length - 1;
        } else if (currentSlideIndex >= slideImages.length) {
            currentSlideIndex = 0;
        }

        updateSlideContent();
    }

    /**
     * Updates the slide content by setting the current slide's image and descriptive text.
     * Called after each slide navigation to refresh the display.
     */
    private void updateSlideContent() {
        ImageIcon slideIcon = new ImageIcon(resizeImage(slideImages[currentSlideIndex], 1000, 600));
        slideImageLabel.setIcon(slideIcon);
        slideTextArea.setText(slideTexts[currentSlideIndex]);
    }

    /**
     * Resizes an image to the specified width and height while maintaining its aspect ratio.
     *
     * @param imagePath path to the image resource
     * @param width desired width of the resized image
     * @param height desired height of the resized image
     * @return a scaled Image instance
     * @throws RuntimeException if the image resource cannot be found
     */
    private Image resizeImage(String imagePath, int width, int height) {
        URL imageUrl = getClass().getResource(imagePath);
        if (imageUrl == null) {
            throw new RuntimeException("Image not found: " + imagePath);
        }
        Image img = new ImageIcon(imageUrl).getImage();
        return img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
    }

    /**
     * Plays a sound effect from the specified audio file path.
     * Used for button click and navigation sounds.
     *
     * @param soundFilePath path to the sound file resource
     */
    private void playSound(String soundFilePath) {
        try {
            URL soundUrl = getClass().getResource(soundFilePath);
            if (soundUrl == null) {
                System.err.println("Sound file not found: " + soundFilePath);
                return;
            }

            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundUrl);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}
