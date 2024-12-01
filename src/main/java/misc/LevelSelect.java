package misc;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * The misc.LevelSelect class provides a user interface for selecting levels in the game.
 * It displays level icons, names, and navigation controls, and handles user interactions.
 */
public class LevelSelect implements MouseListener {

    /** The main JFrame for the level selection screen. */
    JFrame frame;

    /** The main panel for the level selection interface. */
    JPanel mainPanel;

    /** The label for displaying the selected level's image. */
    JLabel imageLabel;

    /** Icons for the different levels. */
    ImageIcon icon1, icon2, icon3;

    /** Icons for the return button in normal and hover states. */
    ImageIcon returnIcon, retrunIconHover;

    /** Panel containing level navigation controls. */
    JPanel levelSelectContainer;

    /** Labels for the left and right navigation arrows. */
    JLabel arrowRLabel, arrowLLabel;

    /** Icons for the left and right navigation arrows. */
    ImageIcon arrowR, arrowL;

    /** Label displaying the selected level's name. */
    JLabel levelName;

    /** Label at the bottom of the screen prompting the user to select a level. */
    JLabel bottomLabel;

    /** The return button for navigating back to the main menu. */
    JLabel returnButton;

    /** Array of level names available for selection. */
    String[] levelNames;

    /** Array of icons corresponding to the levels. */
    ImageIcon[] levelIcons;

    /** The index of the currently selected level. */
    int currentIndex;

    /** Constant for the "Maze Madness" level. */
    public static final String LEVEL_MAZE_MADNESS = "Maze Madness";

    /** Constant for the "Kung Fu Chaos" level. */
    public static final String LEVEL_KUNG_FU_CHAOS = "Kung Fu Chaos";

    /** Constant for the "Dragon Duel" level. */
    public static final String LEVEL_DRAGON_DUEL = "Dragon Duel";

    /** The music player for background music and sound effects. */
    private MusicPlayerMinigame musicPlayer;

    /**
     * Constructs a misc.LevelSelect instance and initializes the level selection screen.
     */
    public LevelSelect() {
        // Initialize JFrame
        frame = new JFrame();
        frame.setSize(1000, 1000);

        // Play background music
        musicPlayer = new MusicPlayerMinigame();
        musicPlayer.playMusic("audio/levelselectbackgroundmusic.wav");

        // Initialize Main Panel
        mainPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 500, 15));
        mainPanel.setBackground(Color.BLACK);

        // Create Level Icon
        createLevelIcon();

        // Create Level Select
        createLevelSelect();

        // Create "Select level" label
        createBottomLabel();

        // Initialize levels and icons
        levelNames = new String[]{LEVEL_MAZE_MADNESS, LEVEL_KUNG_FU_CHAOS, LEVEL_DRAGON_DUEL};
        levelIcons = new ImageIcon[]{icon2, icon1, icon3};
        currentIndex = 0; // Start with the first level

        // Add components to the panel and attach the panel to the frame
        mainPanel.add(imageLabel);
        mainPanel.add(bottomLabel, FlowLayout.CENTER);
        mainPanel.add(levelSelectContainer, FlowLayout.CENTER);

        frame.add(mainPanel);

        addReturnButton();

        makeFullScreen(frame);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    /**
     * Makes the provided JFrame fullscreen.
     *
     * @param frame the JFrame to make fullscreen
     */
    private static void makeFullScreen(JFrame frame) {
        frame.setUndecorated(true);

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();

        if (gd.isFullScreenSupported()) {
            gd.setFullScreenWindow(frame);
        } else {
            frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        }
    }

    /**
     * Adds a return button to the main panel.
     */
    private void addReturnButton() {
        returnButton = new JLabel();
        returnIcon = new ImageIcon(getClass().getResource("visuals/returnButton.png"));
        retrunIconHover = new ImageIcon(getClass().getResource("visuals/returnButtonHover.png"));
        returnButton.setIcon(returnIcon);
        returnButton.setOpaque(false);

        returnButton.addMouseListener(this);

        mainPanel.add(returnButton);
    }

    /**
     * Initializes the level selection container with navigation controls and level names.
     */
    private void createLevelSelect() {
        levelSelectContainer = new JPanel(new FlowLayout(FlowLayout.CENTER, 100, 10));
        levelSelectContainer.setBackground(Color.black);

        arrowL = new ImageIcon(getClass().getResource("visuals/arrowLeft.png"));
        arrowR = new ImageIcon(getClass().getResource("visuals/arrowRight.png"));
        arrowLLabel = new JLabel(arrowL);
        arrowRLabel = new JLabel(arrowR);

        levelSelectContainer.add(arrowLLabel);
        setLevelName("Maze Madness");
        levelSelectContainer.add(levelName);
        levelSelectContainer.add(arrowRLabel);

        arrowLLabel.addMouseListener(this);
        arrowRLabel.addMouseListener(this);

        levelSelectContainer.setOpaque(true);
    }

    /**
     * Initializes level icons and their associated labels.
     */
    private void createLevelIcon() {
        icon1 = resizeImage(new ImageIcon(getClass().getResource("visuals/KungFuChaos.png")));
        icon2 = resizeImage(new ImageIcon(getClass().getResource("visuals/maze.png")));
        icon3 = resizeImage(new ImageIcon(getClass().getResource("visuals/dragon.png")));

        imageLabel = new JLabel();
        imageLabel.setPreferredSize(new Dimension(icon1.getIconWidth(), icon1.getIconHeight()));

        imageLabel.setIcon(icon2);
    }

    /**
     * Initializes the bottom label prompting the user to select a level.
     */
    private void createBottomLabel() {
        bottomLabel = new JLabel();
        bottomLabel.setBackground(Color.black);
        bottomLabel.setForeground(Color.orange);
        bottomLabel.setFont(new Font("Papyrus", Font.ITALIC, 54));
        bottomLabel.setText("Select Your Level!");
        bottomLabel.setOpaque(true);
    }

    /**
     * Sets the displayed level name.
     *
     * @param title the name of the level to display
     */
    private void setLevelName(String title) {
        if (levelName != null) {
            levelName.setText(title);
        } else {
            levelName = new JLabel();
            levelName.setBackground(Color.black);
            levelName.setForeground(Color.white);
            levelName.setFont(new Font("Papyrus", Font.BOLD, 54));
            levelName.setText(title);
            levelName.addMouseListener(this);
            levelName.setOpaque(true);

            levelName.setPreferredSize(new Dimension(400, 80));
            levelName.setHorizontalAlignment(SwingConstants.CENTER);
        }
    }

    /**
     * Resizes the provided image icon to a fixed dimension.
     *
     * @param icon the ImageIcon to resize
     * @return the resized ImageIcon
     */
    private ImageIcon resizeImage(ImageIcon icon) {
        Image img = icon.getImage();
        Image reSizedImg = img.getScaledInstance(500, 500, Image.SCALE_SMOOTH);
        return new ImageIcon(reSizedImg);
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {
        Object source = e.getSource();

        if (source == levelName || source == returnButton || source == arrowLLabel || source == arrowRLabel) {
            musicPlayer.playSound("audio/buttonclick.wav");
        }

        if (source == arrowLLabel) {
            currentIndex = (currentIndex - 1 + levelNames.length) % levelNames.length;
        } else if (source == arrowRLabel) {
            currentIndex = (currentIndex + 1) % levelNames.length;
        }

        if (source == returnButton) {
            frame.dispose();
            musicPlayer.stopMusic();
        }

        imageLabel.setIcon(levelIcons[currentIndex]);
        setLevelName(levelNames[currentIndex]);

        mainPanel.revalidate();
        mainPanel.repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        Object source = e.getSource();

        if (source == levelName) {
            String selectedLevel = levelNames[currentIndex];
            musicPlayer.stopMusic();

            if (selectedLevel.equals("Dragon Duel")) {
                // new Dragon();
            } else {
                new MainMinigame(selectedLevel);
                new GamePanel(selectedLevel);
            }

            frame.dispose();
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        Object source = e.getSource();

        if (source == levelName) {
            levelName.setBackground(new Color(240, 240, 240));
            levelName.setForeground(Color.BLACK);
            levelName.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 2));
            levelName.repaint();
        }

        if (source == returnButton) {
            returnButton.setIcon(retrunIconHover);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        Object source = e.getSource();

        if (source == levelName) {
            levelName.setBackground(Color.BLACK);
            levelName.setForeground(Color.WHITE);
            levelName.setBorder(null);
            levelName.repaint();
        }

        if (source == returnButton) {
            returnButton.setIcon(returnIcon);
        }
    }
}



