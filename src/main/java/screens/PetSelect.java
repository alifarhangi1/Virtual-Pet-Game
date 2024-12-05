package screens;

// Importing necessary Swing components for GUI creation
import managers.AudioManager;
import managers.DatabaseManager;
import managers.GameManager;
import managers.ScreenManager;
import misc.*;

import javax.swing.*;

// Importing AWT components for layouts and GUI styling
import java.awt.*;

// Importing AWT event classes for handling mouse events and component events
import java.awt.event.*;

// Importing Java Sound API classes for audio playback
import javax.sound.sampled.*;

// Importing File and IOException classes for handling file operations
import java.io.IOException;
import java.net.URL;

/**
 * A GUI screen that displays and first 3 starter pets
 *
 * @version 5.0
 * @author Luca Duarte
 * @authro Yazan
 */
public class PetSelect extends JPanel {

    /**
     * Constants for pet selection in the game.
     * These arrays define the available pets, their categories (starter or unlockable),
     * and the corresponding image file paths for display in the UI.
     */

    // Array of starter pets available at the beginning of the game
    private static final String[] STARTER_PETS = {"Panda", "Wolf", "Owl"};

    // Array of pets that are unlockable as the game progresses
    private static final String[] UNLOCKABLE_PETS = {"Minotaur", "Tiger", "Dragon"};

    // Array of pets that have been unlocked (initially includes all unlockable pets for simplicity)
    private static final String[] UNLOCKED_PETS = {"Minotaur", "Tiger", "Dragon"};

    // Array of image file paths corresponding to the starter pets
    private static final String[] STARTER_IMAGES = {
            "/visuals/pandaSlotDefault.png", // Image for Panda
            "/visuals/wolfSlotDefault.png",  // Image for Wolf
            "/visuals/owlSlotDefault.png"    // Image for Owl
    };

    // Array of image file paths corresponding to the unlockable pets
    private static final String[] UNLOCKABLE_IMAGES = {
            "/visuals/minotaurSlotLock.png", // Locked image for Minotaur
            "/visuals/tigerSlotLock.png",    // Locked image for Tiger
            "/visuals/dragonSlotLock.png"    // Locked image for Dragon
    };

    // Array of currently displayed pets, initialized with starter pets
    private String[] currentPets = STARTER_PETS;

    // Array of images corresponding to the currently displayed pets
    private String[] currentImages = STARTER_IMAGES;

    // Current page index for navigation (0 = starter pets, 1 = unlockable pets)
    private int currentPage = 0;

    // Label to display the currently selected pet's name
    private JLabel selectedPetLabel;

    // Panel to hold and display pet slots dynamically
    private JPanel petSlotPanel;

    // Labels for displaying GIFs corresponding to specific pets
    private JLabel wolfGifLabel; // GIF for the Wolf pet
    private JLabel owlGifLabel;  // GIF for the Owl pet

    // Labels for additional pet GIFs
    private JLabel pandaGifLabel, minotaurGifLabel, tigerGifLabel, dragonGifLabel;

    // Path to the background image used in the pet selection screen
    private static final String BACKGROUND_PATH = "/visuals/petSelectBG2.gif";

    // Clip to manage background music playback
    private Clip bgmClip;

    // UI Components that need to be resized
    private JLabel titleLabel;
    private JButton backButton;
    private JButton nextButton;
    private JButton mainMenuButton;
    private JButton titleMenuButton;

    private GameManager gm;
    private DatabaseManager databaseManager;
    private ScreenManager screenManager;
    private AudioManager audioManager;

    /**
     * Constructor for the PetSelect class.
     * Initializes the pet selection screen by setting up the layout, components,
     * and audio features (introductory audio and background music).
     */
    public PetSelect(GameManager gm) {
        /** Grabs instance of player passed by new game screen */
        this.gm = gm;
        screenManager = ScreenManager.getInstance();
        audioManager = AudioManager.getInstance();

        /** Grabs instance of DatabaseManager */
        databaseManager = DatabaseManager.getInstance();

        // Use null layout for precise manual positioning of components
        setLayout(null);

        // Set the background to be transparent
        setOpaque(false);

        // Set up UI components for the pet selection screen
        setupComponents();

        // Add a component listener to handle resizing
        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                updateLayout();
            }

            @Override
            public void componentShown(ComponentEvent e) {
                updateLayout();
            }
        });

        // Play the introductory audio for the pet selection screen
        playIntroAudio("/audio/petSelect_AI_Intro.wav");

        // Start looping background music
        playBackgroundMusic("/audio/petSelect_bgm.wav");
    }

    /**
     * Sets up the components for the pet selection screen.
     * This includes adding the title, selected pet label, pet slots, and navigation buttons.
     * The method arranges components using manual positioning (null layout) to achieve
     * a visually appealing layout for the screen.
     */
    private void setupComponents() {
        // Title Label
        titleLabel = new JLabel();
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel); // Add the title label to the screen

        // Selected Pet Label
        selectedPetLabel = new JLabel("No Pet Selected", SwingConstants.CENTER);
        selectedPetLabel.setFont(new Font("Monospaced", Font.BOLD, 30)); // Set font style and size
        selectedPetLabel.setForeground(Color.WHITE); // Set text color to white
        add(selectedPetLabel); // Add the selected pet label to the screen

        // Pet Slot Panel
        petSlotPanel = new JPanel();
        petSlotPanel.setOpaque(false); // Make the panel transparent
        petSlotPanel.setLayout(null); // Use null layout for precise positioning
        add(petSlotPanel); // Add the pet slot panel to the screen

        // Back Button
        backButton = createNavigationButton(
                "/visuals/backArrowButtonDefault.png", // Default button image
                "/visuals/backArrowButtonHover.png",  // Hover button image
                () -> navigatePets(-1), // Action to navigate to the previous page
                "/audio/backButtonClickPS.wav" // Click sound for the back button
        );
        add(backButton); // Add the back button to the screen

        // Next Button
        nextButton = createNavigationButton(
                "/visuals/nextArrowButtonDefault.png", // Default button image
                "/visuals/nextArrowButtonHover.png",  // Hover button image
                () -> navigatePets(1), // Action to navigate to the next page
                "/audio/nextButtonClickPS.wav" // Click sound for the next button
        );
        add(nextButton); // Add the next button to the screen

        // Main Menu Button
        mainMenuButton = createNavigationButton(
                "/visuals/mainMenuButtonDefault.png", // Default button image
                "/visuals/mainMenuButtonHover.png",  // Hover button image
                this::goToMainMenu, // Action to navigate to the main menu
                "/audio/mainMenuClick.wav" // Click sound for the main menu button
        );
        add(mainMenuButton); // Add the Main Menu button to the screen

        // Title Menu Button
        titleMenuButton = createNavigationButton(
                "/visuals/titleMenuButtonDefault.png", // Default button image
                "/visuals/titleMenuButtonHover.png",  // Hover button image
                this::goToTitleMenu, // Action to navigate to the title menu
                "/audio/titleMenuClick.wav" // Click sound for the title menu button
        );
        titleMenuButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                audioManager.playBackButtonClickSound();
                stopBackgroundMusic();
                screenManager.showScreen("title");
                audioManager.startBackgroundMusic();
            }
        });
        add(titleMenuButton); // Add the Title Menu button to the screen

        // Initially update the layout
        updatePetSlots();
        updateLayout();
    }

    /**
     * Updates the layout of all components based on the current size of the panel.
     */
    private void updateLayout() {
        int panelWidth = getWidth();
        int panelHeight = getHeight();

        // Avoid updating layout if the panel size is not set yet
        if (panelWidth <= 0 || panelHeight <= 0) {
            return;
        }

        // Title Label
        int titleWidth = (int) (panelWidth * 0.6);
        int titleHeight = (int) (panelHeight * 0.08);
        int titleX = (panelWidth - titleWidth) / 2;
        int titleY = (int) (panelHeight * 0.02);
        titleLabel.setBounds(titleX, titleY, titleWidth, titleHeight);

        // Update title image
        ImageIcon titleIcon = new ImageIcon(new ImageIcon(getClass().getResource("/visuals/petSelectTitle.png"))
                .getImage().getScaledInstance(titleWidth, titleHeight, Image.SCALE_SMOOTH));
        titleLabel.setIcon(titleIcon);

        // Selected Pet Label
        int selectedPetWidth = (int) (panelWidth * 0.3);
        int selectedPetHeight = (int) (panelHeight * 0.05);
        int selectedPetX = (panelWidth - selectedPetWidth) / 2;
        int selectedPetY = titleY + titleHeight + (int) (panelHeight * 0.02);
        selectedPetLabel.setBounds(selectedPetX, selectedPetY, selectedPetWidth, selectedPetHeight);

        // Pet Slot Panel
        int petSlotPanelWidth = (int) (panelWidth * 0.8);
        int petSlotPanelHeight = (int) (panelHeight * 0.4);
        int petSlotPanelX = (panelWidth - petSlotPanelWidth) / 2;
        int petSlotPanelY = selectedPetY + selectedPetHeight + (int) (panelHeight * 0.05);
        petSlotPanel.setBounds(petSlotPanelX, petSlotPanelY, petSlotPanelWidth, petSlotPanelHeight);

        // Update pet slots
        updatePetSlots();

        // Navigation Buttons
        int navButtonWidth = (int) (panelWidth * 0.05);
        int navButtonHeight = (int) (panelHeight * 0.08);
        int backButtonX = (int) (panelWidth * 0.02);
        int backButtonY = petSlotPanelY + (petSlotPanelHeight - navButtonHeight) / 2;
        backButton.setBounds(backButtonX, backButtonY, navButtonWidth, navButtonHeight);

        int nextButtonX = panelWidth - navButtonWidth - backButtonX;
        nextButton.setBounds(nextButtonX, backButtonY, navButtonWidth, navButtonHeight);

        // Update navigation button images
        updateButtonImage(backButton, "/visuals/backArrowButtonDefault.png", navButtonWidth, navButtonHeight);
        updateButtonImage(nextButton, "/visuals/nextArrowButtonDefault.png", navButtonWidth, navButtonHeight);

        // Main Menu and Title Menu Buttons
        int menuButtonWidth = (int) (panelWidth * 0.1);
        int menuButtonHeight = (int) (panelHeight * 0.08);
        int menuButtonX = (int) (panelWidth * 0.02);
        int menuButtonY = (int) (panelHeight * 0.02);
        mainMenuButton.setBounds(menuButtonX, menuButtonY, menuButtonWidth, menuButtonHeight);

        int titleMenuButtonY = menuButtonY + menuButtonHeight + (int) (panelHeight * 0.02);
        titleMenuButton.setBounds(menuButtonX, titleMenuButtonY, menuButtonWidth, menuButtonHeight);

        // Update menu button images
        updateButtonImage(mainMenuButton, "/visuals/mainMenuButtonDefault.png", menuButtonWidth, menuButtonHeight);
        updateButtonImage(titleMenuButton, "/visuals/titleMenuButtonDefault.png", menuButtonWidth, menuButtonHeight);

        // Update GIF positions if any are displayed
        updateGifPositions();

        revalidate();
        repaint();
    }

    /**
     * Updates a navigation button's image based on the current size.
     *
     * @param button      The JButton to update.
     * @param imagePath   The path to the default image.
     * @param width       The desired width.
     * @param height      The desired height.
     */
    private void updateButtonImage(JButton button, String imagePath, int width, int height) {
        ImageIcon icon = new ImageIcon(new ImageIcon(getClass().getResource(imagePath))
                .getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
        button.setIcon(icon);
    }

    /**
     * Updates a GIF label's position, size, and image scaling.
     *
     * @param gifLabel The JLabel containing the GIF.
     * @param gifPath  The path to the GIF file.
     * @param x        The x-coordinate of the label.
     * @param y        The y-coordinate of the label.
     * @param width    The width of the label.
     * @param height   The height of the label.
     */
    private void updateGifLabel(JLabel gifLabel, String gifPath, int x, int y, int width, int height) {
        if (gifLabel != null) {
            gifLabel.setBounds(x, y, width, height);

            // Scale the GIF image
            ImageIcon gifIcon = new ImageIcon(new ImageIcon(getClass().getResource(gifPath))
                    .getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));
            gifLabel.setIcon(gifIcon);
        }
    }


    /**
     * Updates the positions and sizes of the displayed GIFs based on panel size.
     */
    private void updateGifPositions() {
        int panelWidth = getWidth();
        int panelHeight = getHeight();

        // Avoid updating if panel size is not set yet
        if (panelWidth <= 0 || panelHeight <= 0) {
            return;
        }

        int gifWidth = (int) (panelWidth * 0.25);
        int gifHeight = (int) (panelHeight * 0.3);
        int gifX = (panelWidth - gifWidth) / 2;
        int gifY = (int) (panelHeight * 0.15);

        // Update GIF labels and scale images
        updateGifLabel(wolfGifLabel, "/visuals/wolfSelectGo.gif", gifX, gifY, gifWidth, gifHeight);
        updateGifLabel(owlGifLabel, "/visuals/owlSelectGo.gif", gifX, gifY, gifWidth, gifHeight);
        updateGifLabel(pandaGifLabel, "/visuals/pandaSelectGo.gif", gifX, gifY, gifWidth, gifHeight);
        updateGifLabel(minotaurGifLabel, "/visuals/minotaurSelectGo.gif", gifX, gifY, gifWidth, gifHeight);
        updateGifLabel(tigerGifLabel, "/visuals/tigerSelectGo.gif", gifX, gifY, gifWidth, gifHeight);
        updateGifLabel(dragonGifLabel, "/visuals/dragonSelectGo.gif", gifX, gifY, gifWidth, gifHeight);
    }

    /**
     * Updates the pet slots displayed on the screen.
     * This method dynamically populates the `petSlotPanel` with pet slots and optional info buttons
     * based on the currently selected page (starter pets or unlockable pets).
     * It clears existing components, calculates positions, and adds new components for each pet.
     */
    private void updatePetSlots() {
        // Clear all existing components from the petSlotPanel
        petSlotPanel.removeAll();

        int panelWidth = petSlotPanel.getWidth();
        int panelHeight = petSlotPanel.getHeight();

        // Avoid updating if panel size is not set yet
        if (panelWidth <= 0 || panelHeight <= 0) {
            return;
        }

        // Number of pets to display
        int numPets = currentPets.length;

        // Calculate dimensions for pet slots
        int slotWidth = (int) (panelWidth * 0.25);
        int slotHeight = (int) (panelHeight * 0.75);
        int spacing = (panelWidth - numPets * slotWidth) / (numPets + 1);
        int slotY = (panelHeight - slotHeight) / 2;

        // Loop through all pets in the current page to dynamically add pet slots
        for (int i = 0; i < currentPets.length; i++) {
            int slotX = spacing + i * (slotWidth + spacing);

            // Create a pet slot for the current pet and set its position
            JLabel petSlot = createPetSlot(currentPets[i], currentImages[i], slotWidth, slotHeight);
            petSlot.setBounds(slotX, slotY, slotWidth, slotHeight);
            petSlotPanel.add(petSlot); // Add the pet slot to the panel

            // Add an info button for starter pets (page 0) or unlocked pets (page 2)
            if (currentPage == 0 || currentPage == 2) {
                // Create an info button for the current pet
                JLabel infoButton = createInfoButton(currentPets[i], (int) (slotWidth * 0.8), (int) (panelHeight * 0.1));

                // Position the info button centered below the pet slot
                int buttonWidth = (int) (slotWidth * 0.8);
                int buttonHeight = (int) (panelHeight * 0.1);
                int buttonX = slotX + (slotWidth - buttonWidth) / 2;
                int buttonY = slotY + slotHeight + 5; // 5 pixels below the pet slot

                infoButton.setBounds(buttonX, buttonY, buttonWidth, buttonHeight);
                petSlotPanel.add(infoButton); // Add the info button to the panel
            }
        }

        petSlotPanel.revalidate();
        petSlotPanel.repaint();
    }


    /**
     * Creates an interactive pet slot for the pet selection screen.
     * The slot displays the pet's image, allows for hover effects, and plays sounds
     * when clicked or hovered over. Locked pets show a lock icon and are not selectable.
     *
     * @param petName   The name of the pet to be displayed in the slot.
     * @param imagePath The file path to the default image for the pet.
     * @param width     The width of the pet slot.
     * @param height    The height of the pet slot.
     * @return A JLabel representing the interactive pet slot.
     */
    private JLabel createPetSlot(String petName, String imagePath, int width, int height) {
        // Initialize the JLabel for the pet slot
        JLabel petSlot = new JLabel();
        petSlot.setOpaque(false); // Make the slot transparent

        // Determine if the slot should be locked based on the current page and pet type
        boolean isLocked = (currentPage == 1) &&
                (petName.equals("Dragon") || petName.equals("Tiger") || petName.equals("Minotaur"));

        // Determine the image to display for the slot
        String imagePathToUse = isLocked
                ? (petName.equals("Dragon") ? "/visuals/dragonSlotLock.png" :
                petName.equals("Tiger") ? "/visuals/tigerSlotLock.png" :
                        petName.equals("Minotaur") ? "/visuals/minotaurSlotLock.png" : null)
                : imagePath;

        // If no image is found, log an error and return an empty slot
        if (imagePathToUse == null) {
            System.err.println("No image path found for pet: " + petName);
            return petSlot; // Return an empty slot
        }

        // Load and scale the image for the slot
        ImageIcon defaultIcon = new ImageIcon(new ImageIcon(getClass().getResource(imagePathToUse))
                .getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
        petSlot.setIcon(defaultIcon);
        petSlot.setHorizontalAlignment(SwingConstants.CENTER); // Center-align the image
        petSlot.setToolTipText(isLocked ? "Locked: " + petName : petName); // Tooltip to show if the pet is locked

        // Add interactivity for the pet slot
        petSlot.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Only handle clicks if the slot is not locked
                if (!isLocked) {
                    // Play the general click sound
                    playSound("/audio/petSlotClick.wav");

                    // Update the selected pet label
                    selectedPetLabel.setText("Selected: " + petName);

                    // Determine and play the specific selection audio for the pet
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
                    playSound(petAudio); // Play the specific pet's audio
                    handleGifDisplay(petName); // Display the GIF for the selected pet
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                // Play hover sound when the mouse enters the slot
                playSound("/audio/petSlotHover.wav");

                // Change to a hover icon if the slot is not locked
                if (!isLocked) {
                    ImageIcon hoverIcon = new ImageIcon(new ImageIcon(getClass().getResource("/visuals/" + petName.toLowerCase() + "SlotHover.png"))
                            .getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
                    petSlot.setIcon(hoverIcon);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Reset to the default icon when the mouse exits the slot
                petSlot.setIcon(defaultIcon);
            }
        });

        return petSlot; // Return the configured pet slot
    }

    /**
     * Handles the display of GIF animations based on the selected pet.
     *
     * @param petName The name of the selected pet.
     */
    private void handleGifDisplay(String petName) {
        // Remove all existing GIFs to prevent overlapping animations
        removeAllGifs();

        // Display the GIF corresponding to the selected pet's name
        switch (petName) {
            case "Wolf":
                wolfGifLabel = displayGif("/visuals/wolfSelectGo.gif");
                break;
            case "Owl":
                owlGifLabel = displayGif("/visuals/owlSelectGo.gif");
                break;
            case "Panda":
                pandaGifLabel = displayGif("/visuals/pandaSelectGo.gif");
                break;
            case "Minotaur":
                minotaurGifLabel = displayGif("/visuals/minotaurSelectGo.gif");
                break;
            case "Tiger":
                tigerGifLabel = displayGif("/visuals/tigerSelectGo.gif");
                break;
            case "Dragon":
                dragonGifLabel = displayGif("/visuals/dragonSelectGo.gif");
                break;
        }

        revalidate();
        repaint();
    }

    /**
     * Displays a GIF animation on the screen.
     *
     * @param gifPath The path to the GIF file.
     * @return The JLabel containing the GIF.
     */
    private JLabel displayGif(String gifPath) {
        JLabel gifLabel = new JLabel(new ImageIcon(getClass().getResource(gifPath)));
        add(gifLabel);
        updateGifPositions();
        return gifLabel;
    }

    /**
     * Removes all displayed GIFs from the screen.
     */
    private void removeAllGifs() {
        if (wolfGifLabel != null) {
            remove(wolfGifLabel);
            wolfGifLabel = null;
        }
        if (owlGifLabel != null) {
            remove(owlGifLabel);
            owlGifLabel = null;
        }
        if (pandaGifLabel != null) {
            remove(pandaGifLabel);
            pandaGifLabel = null;
        }
        if (minotaurGifLabel != null) {
            remove(minotaurGifLabel);
            minotaurGifLabel = null;
        }
        if (tigerGifLabel != null) {
            remove(tigerGifLabel);
            tigerGifLabel = null;
        }
        if (dragonGifLabel != null) {
            remove(dragonGifLabel);
            dragonGifLabel = null;
        }
    }

    /**
     * Plays the introductory audio for the pet selection screen.
     * The audio file specified by the given file path is loaded and played once.
     * This method handles potential exceptions such as unsupported file formats or IO issues.
     *
     * @param audioFilePath The file path of the audio file to be played.
     */
    private void playIntroAudio(String audioFilePath) {
        try {
            // Load the audio file
            URL audioFile = getClass().getResource(audioFilePath);

            // Create an audio stream from the file
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);

            // Obtain a Clip to play the audio
            Clip clip = AudioSystem.getClip();

            // Open the audio stream in the Clip and start playback
            clip.open(audioStream);
            clip.start(); // Play the audio once
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            // Print the stack trace for debugging if an exception occurs
            e.printStackTrace();
        }
    }

    /**
     * Plays a sound effect from a specified file path.
     * This method is used for short, one-time sounds like button clicks or hover effects.
     * It loads the audio file, creates an audio stream, and plays it once.
     *
     * @param soundFilePath The file path of the sound file to be played.
     */
    private void playSound(String soundFilePath) {
        try {
            // Load the sound file from the provided file path
            URL soundFile = getClass().getResource(soundFilePath);

            // Create an audio stream to read the sound file
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);

            // Obtain a Clip to play the audio
            Clip clip = AudioSystem.getClip();

            // Open the audio stream in the Clip and start playback
            clip.open(audioStream);
            clip.start(); // Play the sound once
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            // Log any exceptions that occur during sound playback
            e.printStackTrace();
        }
    }

    /**
     * Plays background music from a specified file path in a continuous loop.
     * This method is used to play longer audio tracks that enhance the atmosphere of the screen.
     * The music loops indefinitely until explicitly stopped.
     *
     * @param musicFilePath The file path of the music file to be played.
     */
    private void playBackgroundMusic(String musicFilePath) {
        try {
            // Load the music file from the provided file path
            URL musicFile = getClass().getResource(musicFilePath);

            // Create an audio stream to read the music file
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(musicFile);

            // Obtain a Clip to play the audio
            bgmClip = AudioSystem.getClip();

            // Open the audio stream in the Clip
            bgmClip.open(audioStream);

            // Set the Clip to loop continuously and start playback
            bgmClip.loop(Clip.LOOP_CONTINUOUSLY);
            bgmClip.start(); // Start playing the background music
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            // Log any exceptions that occur during music playback
            e.printStackTrace();
        }
    }

    /**
     * Stops the background music if it is currently playing.
     * This method checks whether the `bgmClip` is initialized and running.
     * If so, it stops the playback and releases the audio resources.
     *
     * This is useful for scenarios where the music needs to be paused or muted,
     * such as during screen transitions or when the user chooses to disable background music.
     */
    private void stopBackgroundMusic() {
        // Check if the background music clip is initialized and currently playing
        if (bgmClip != null && bgmClip.isRunning()) {
            // Stop the music playback
            bgmClip.stop();

            // Release resources associated with the clip
            bgmClip.close();
        }
    }

    /**
     * Navigates to the Main Menu screen.
     * This method plays a click sound and executes the logic for transitioning to the main menu.
     * Currently, it shows a placeholder message dialog. Replace the placeholder with the actual
     * logic for navigating to the main menu.
     */
    private void goToMainMenu() {
        // Play the sound associated with the Main Menu button click
        playSound("/audio/mainMenuClick.wav");

        // Display a placeholder message to indicate the button action
        JOptionPane.showMessageDialog(this, "Main Menu button clicked!");
    }

    /**
     * Navigates to the Title Menu screen.
     * This method plays a click sound and executes the logic for transitioning to the title menu.
     * Currently, it shows a placeholder message dialog. Replace the placeholder with the actual
     * logic for navigating to the title menu.
     */
    private void goToTitleMenu() {
        // Play the sound associated with the Title Menu button click
        playSound("/audio/titleMenuClick.wav");

        // Display a placeholder message to indicate the button action
        JOptionPane.showMessageDialog(this, "Title Menu button clicked!");
    }


    /**
     * Creates an information button for a specific pet.
     * The button changes its icon when hovered over and displays a description panel
     * with additional information about the pet. It also plays a hover sound effect.
     *
     * @param petName The name of the pet for which the info button is created.
     * @param width   The width of the info button.
     * @param height  The height of the info button.
     * @return A JLabel representing the interactive information button for the specified pet.
     */
    private JLabel createInfoButton(String petName, int width, int height) {
        // Initialize the info button as a JLabel
        JLabel buttonLabel = new JLabel();

        // Panel to display additional information when hovering over the button
        JPanel hoverInfoPanel = new JPanel(); // Transparent panel for hover text
        JLabel hoverInfoLabel = new JLabel(); // Label inside the hover panel for text display

        // Configure the hover info panel
        hoverInfoPanel.setLayout(new BorderLayout());
        hoverInfoPanel.setOpaque(true); // Make the panel opaque
        hoverInfoPanel.setBackground(new Color(0, 0, 0, 200)); // Semi-transparent black background
        hoverInfoPanel.setVisible(false); // Initially hide the panel

        // Style the text inside the hover panel
        hoverInfoLabel.setFont(new Font("Monospaced", Font.BOLD, 20)); // Bold and clear font for visibility
        hoverInfoLabel.setForeground(Color.WHITE); // White text color for readability
        hoverInfoLabel.setHorizontalAlignment(SwingConstants.CENTER); // Center-align the text
        hoverInfoPanel.add(hoverInfoLabel, BorderLayout.CENTER); // Add the label to the hover panel

        // Variables to store icons for the button's default and hover states
        ImageIcon defaultIcon;
        ImageIcon hoverIcon;

        // Assign icons and hover text based on the pet's name
        switch (petName) {
            case "Panda":
                defaultIcon = new ImageIcon(new ImageIcon(getClass().getResource("/visuals/pandaButtonInfoDefault.png"))
                        .getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
                hoverIcon = new ImageIcon(new ImageIcon(getClass().getResource("/visuals/pandaButtonInfoHover.png"))
                        .getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
                hoverInfoLabel.setText("<html><span style='color:green;'>Peaceful Protector</span>, skilled with bamboo staff and inner energy.</html>");
                break;

            case "Wolf":
                defaultIcon = new ImageIcon(new ImageIcon(getClass().getResource("/visuals/wolfButtonIconDefault.png"))
                        .getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
                hoverIcon = new ImageIcon(new ImageIcon(getClass().getResource("/visuals/wolfButtonIconHover.png"))
                        .getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
                hoverInfoLabel.setText("<html><span style='color:blue;'>Alpha Explorer</span>, brave and resourceful in adventures.</html>");
                break;

            case "Owl":
                defaultIcon = new ImageIcon(new ImageIcon(getClass().getResource("/visuals/owlButtonIconDefault.png"))
                        .getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
                hoverIcon = new ImageIcon(new ImageIcon(getClass().getResource("/visuals/owlButtonIconHover.png"))
                        .getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
                hoverInfoLabel.setText("<html><span style='color:yellow;'>Wise Jungle Sage</span>, carrying its treasured book of secrets.</html>");
                break;

            case "Minotaur":
                defaultIcon = new ImageIcon(new ImageIcon(getClass().getResource("/visuals/minotaurButtonIconDefault.png"))
                        .getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
                hoverIcon = new ImageIcon(new ImageIcon(getClass().getResource("/visuals/minotaurButtonIconHover.png"))
                        .getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
                hoverInfoLabel.setText("<html><span style='color:#F4A460;'>Beast of Labyrinth</span>, power forged in ancient mazes and protector of forgotten paths.</html>");
                break;

            case "Tiger":
                defaultIcon = new ImageIcon(new ImageIcon(getClass().getResource("/visuals/tigerButtonIconDefault.png"))
                        .getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
                hoverIcon = new ImageIcon(new ImageIcon(getClass().getResource("/visuals/tigerButtonIconHover.png"))
                        .getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
                hoverInfoLabel.setText("<html><span style='color:orange;'>Ultimate Fury</span>, a majestic and fierce ruler of the untamed wilderness.</html>");
                break;

            case "Dragon":
                defaultIcon = new ImageIcon(new ImageIcon(getClass().getResource("/visuals/dragonButtonIconDefault.png"))
                        .getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
                hoverIcon = new ImageIcon(new ImageIcon(getClass().getResource("/visuals/dragonButtonIconHover.png"))
                        .getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
                hoverInfoLabel.setText("<html><span style='color:red;'>Ancient Flame Bearer</span>, a mythical legend of supreme destruction and mystery.</html>");
                break;

            default:
                return null; // Return null if the pet name is not recognized
        }

        // Set the default icon for the button
        buttonLabel.setIcon(defaultIcon);
        buttonLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Add hover behavior for the button
        buttonLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                // Change the button to its hover icon
                buttonLabel.setIcon(hoverIcon);

                // Play the hover sound effect
                playSound("/audio/petIconButtonHover.wav");

                // Dynamically position and display the hover info panel
                int panelWidth = getWidth();
                int panelHeight = (int) (getHeight() * 0.05);
                int panelX = 0; // Start from the left edge
                int panelY = (int) (getHeight() * 0.9); // Position at the bottom

                hoverInfoPanel.setBounds(panelX, panelY, panelWidth, panelHeight);
                hoverInfoPanel.setVisible(true);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Revert to the default icon and hide the hover info panel
                buttonLabel.setIcon(defaultIcon);
                hoverInfoPanel.setVisible(false);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                String name = JOptionPane.showInputDialog("What will be the name of your pet?");

                if (name == null || name.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please enter your pet name.",
                            "Invalid Input", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Pet newPet;
                if (petName.equals("Panda")) {
                    newPet = new Panda(name);
                } else if (petName.equals("Owl")) {
                    newPet = new Owl(name);
                } else {
                    newPet = new Wolf(name);
                }

                // Add the pet to the player's pet list
                gm.getPlayer().addPet(newPet);

                // Set the newly added pet as the active pet
                gm.getPlayer().setPet(newPet);

                if (!databaseManager.getAllPlayers().contains(gm.getPlayer())) {
                    databaseManager.addPlayer(gm.getPlayer());
                }


                // Save changes to the database
                databaseManager.saveAllChanges();

                if (!screenManager.hasScreen("main")) {
                    screenManager.addScreen("main", new MainMenuScreen(gm));
                }
                stopBackgroundMusic();
                screenManager.showScreen("main");
                audioManager.startBackgroundMusic();
            }
        });

        // Add the hover info panel to the main panel
        add(hoverInfoPanel);

        return buttonLabel; // Return the configured info button
    }

    /**
     * Creates a navigation button with hover and click functionality.
     * The button changes its icon when hovered over, plays a sound effect on hover and click,
     * and executes a specified action when clicked.
     *
     * @param defaultPath    The file path to the default button image.
     * @param hoverPath      The file path to the hover button image.
     * @param action         The action to execute when the button is clicked.
     * @param clickSoundPath The file path to the sound effect to play on button click.
     * @return A JButton configured with hover and click behavior.
     */
    private JButton createNavigationButton(String defaultPath, String hoverPath, Runnable action, String clickSoundPath) {
        // Create a button with the default icon
        JButton button = new JButton();
        button.setIcon(new ImageIcon(getClass().getResource(defaultPath)));

        // Remove default button border, background, and focus indicators
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);

        // Set the cursor to a hand icon to indicate interactivity
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Store the paths for later use in resizing
        button.putClientProperty("defaultPath", defaultPath);
        button.putClientProperty("hoverPath", hoverPath);

        // Add mouse listeners for hover effects
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                // Change to the hover icon when the mouse enters the button
                ImageIcon hoverIcon = new ImageIcon(new ImageIcon(getClass().getResource(hoverPath))
                        .getImage().getScaledInstance(button.getWidth(), button.getHeight(), Image.SCALE_SMOOTH));
                button.setIcon(hoverIcon);
                playSound("/audio/menu_hover.wav"); // Play the hover sound effect
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Revert to the default icon when the mouse exits the button
                ImageIcon defaultIcon = new ImageIcon(new ImageIcon(getClass().getResource(defaultPath))
                        .getImage().getScaledInstance(button.getWidth(), button.getHeight(), Image.SCALE_SMOOTH));
                button.setIcon(defaultIcon);
            }
        });

        // Add a click listener for button actions
        button.addActionListener(e -> {
            playSound(clickSoundPath); // Play the button click sound
            action.run(); // Execute the specified action
        });

        return button; // Return the configured button
    }

    /**
     * Navigates between pet selection pages (starter pets and unlockable pets).
     * Updates the displayed pets and their corresponding images based on the specified direction.
     *
     * @param direction An integer indicating the direction of navigation:
     *                  -1 for previous page, 1 for next page.
     */
    private void navigatePets(int direction) {
        // Calculate the new page index, ensuring it stays within the valid range (0 to 1)
        currentPage = Math.max(0, Math.min(currentPage + direction, 1));

        // Update the current pets and images based on the current page
        if (currentPage == 0) {
            // Display starter pets on page 0
            currentPets = STARTER_PETS;
            currentImages = STARTER_IMAGES;
        } else if (currentPage == 1) {
            // Display unlockable pets on page 1
            currentPets = UNLOCKABLE_PETS;
            currentImages = UNLOCKABLE_IMAGES;
        }

        // Refresh the pet slots to reflect the updated selection
        updatePetSlots();
    }

    /**
     * Paints the background of the component with a specified image.
     * This method overrides the `paintComponent` method of `JPanel` to draw a custom background
     * image that scales to fit the panel's dimensions.
     *
     * @param g The `Graphics` object used for drawing the component.
     */
    @Override
    protected void paintComponent(Graphics g) {
        // Call the superclass method to ensure the component is properly rendered
        super.paintComponent(g);

        // Load the background image using the predefined file path
        ImageIcon backgroundIcon = new ImageIcon(getClass().getResource(BACKGROUND_PATH));

        // Draw the image to fill the entire panel
        g.drawImage(backgroundIcon.getImage(), 0, 0, getWidth(), getHeight(), this);
    }

//    /**
//     * The entry point for the application.
//     * Sets up the main application window and initializes the `PetSelect` screen.
//     *
//     * @param args Command-line arguments (not used in this application).
//     */
//    public static void main(String[] args) {
//        // Create a new JFrame for the application window
//        JFrame frame = new JFrame("Pet Select");
//
//        // Set the default close operation to exit the application when the window is closed
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//
//        // Set the size of the window to match a common screen size
//        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximize the window
//
//        // Add the PetSelect panel to the frame
//        frame.add(new PetSelect());
//
//        // Make the window visible
//        frame.setVisible(true);
//    }
}
