package misc;

import managers.GameManager;
import managers.TileManager;
import managers.WindowManager;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * The misc.GamePanel class serves as the main panel for the game, handling
 * rendering, updating, and managing game objects, NPCs, and user interactions.
 */
public class GamePanel extends JPanel implements Runnable {

    /** The main window frame of the game. */
    JFrame mainWindow;

    // Screen settings
    /** Original tile size (16x16 pixels). */
    final int originalTileSize = 16;

    /** Scale factor for upscaling tiles and characters. */
    final int scale = 3;

    /** misc.Tile size after scaling (48x48 pixels). */
    public final int tileSize = originalTileSize * scale;

    /** Maximum number of columns on the screen. */
    public final int maxScreenCol = 20;

    /** Maximum number of rows on the screen. */
    public final int maxScreenRow = 12;

    /** Screen width in pixels. */
    public final int screenWidth = tileSize * maxScreenCol;

    /** Screen height in pixels. */
    public final int screenHeight = tileSize * maxScreenRow;

    // World settings
    /** Maximum number of columns in the game world. */
    public final int maxWorldCol = 50;

    /** Maximum number of rows in the game world. */
    public final int maxWorldRow = 50;

    // Full screen settings
    public int screenWidth2 = screenWidth;
    public int screenHeight2 = screenHeight;
    public BufferedImage fullScreen;
    public Graphics2D g2D;

    /** Dialogue state to determine if the game is paused for dialogue. */
    public boolean dialogueState;

    /** Name of the selected level. */
    public String levelName;

    /** Music player for managing game audio. */
    public MusicPlayerMinigame musicPlayer = new MusicPlayerMinigame();

    /** Frames per second (FPS) for the game loop. */
    double FPS = 60;

    /** Manages tiles for the current level. */
    public TileManager tileManager;

    /** Handles user input via the keyboard. */
    KeyHandler keyHandler = new KeyHandler(this);

    /** Handles collision detection. */
    public CollisionChecker collisionChecker = new CollisionChecker(this);

    /** Manages game assets such as objects and NPCs. */
    public AssetSetter assetSetter;

    /** Manages the user interface (UI) of the game. */
    public MinigameUI ui = new MinigameUI(this);

    /** The game thread for managing the game loop. */
    Thread gameThread;

    // Entities and objects
    /** The player character. */
    public PlayerMinigame player = new PlayerMinigame(this, keyHandler);

    /** Array of objects in the game world. */
    public SuperObject obj[] = new SuperObject[10];

    /** Array of NPCs in the game world. */
    public Entity npc[] = new Entity[11];

    /**
     * Constructs a misc.GamePanel with the specified level name.
     *
     * @param levelName the name of the selected game level
     */
    public GamePanel(String levelName)
    {
        this.levelName = levelName;
        tileManager = new TileManager(this, levelName);
        assetSetter = new AssetSetter(this, levelName);

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true); // Improves rendering performance
        this.addKeyListener(keyHandler);
        this.setFocusable(true); // Enables the panel to receive key input
    }

    /**
     * Sets up the game by initializing objects, NPCs, music, and fullscreen settings.
     */
    public void setUpGame() {
        assetSetter.setObject();
        assetSetter.setNPC();

        fullScreen = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
        g2D = (Graphics2D) fullScreen.getGraphics();

//        setFullScreen();

        // Add music for levels with specific names
        if (levelName.equals("Maze Madness") || levelName.equals("Kung Fu Chaos") || levelName.equals("Dragon Duel")) {
            assetSetter.setMusic();
        }

        // Initialize the number of enemies left for the "Kung Fu Chaos" level
        if (levelName.equals("Kung Fu Chaos")) {
            player.enemiesLeft = npc.length - 1;
        }
    }

    /**
     * Sets the game to fullscreen mode by adjusting the screen size.
     */
//    public void setFullScreen() {
//        GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
//        GraphicsDevice graphicsDevice = graphicsEnvironment.getDefaultScreenDevice();
//        graphicsDevice.setFullScreenWindow(WindowManager.getWindow());
//
//        screenWidth2 = WindowManager.getWindow().getWidth();
//        screenHeight2 = WindowManager.getWindow().getHeight();
//    }

    /**
     * Starts the game thread, which executes the game loop.
     */
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start(); // Calls the run() method
    }

    /**
     * Implements the game loop for updating and rendering the game.
     */
    @Override
    public void run() {
        double drawInterval = 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                drawToTempScreen(); // Draw everything to the buffered image
                repaint(); // Draw the buffered image to the screen
                delta--;
            }
        }
    }

    /**
     * Updates the state of the game, including the player and NPCs.
     */
    public void update() {
        if (dialogueState) {
            return;
        }
        player.update(); // Update the player

        // Update NPCs
        for (int i = 0; i < npc.length; i++) {
            if (npc[i] != null) {
                npc[i].update();
            }
        }
    }

    /**
     * Toggles the dialogue (pause) state of the game.
     */
    public void togglePause() {
        dialogueState = !dialogueState; // Toggle the pause state
    }

    /**
     * Draws game elements (tiles, objects, NPCs, player, and UI) to a temporary screen.
     */
    public void drawToTempScreen() {
        tileManager.draw(g2D); // Draw tiles

        // Draw objects
        for (int i = 0; i < obj.length; i++) {
            if (obj[i] != null) {
                obj[i].draw(g2D, this);
            }
        }

        // Draw NPCs
        for (int i = 0; i < npc.length; i++) {
            if (npc[i] != null) {
                npc[i].draw(g2D);
            }
        }

        player.draw(g2D); // Draw the player

        ui.draw(g2D); // Draw the user interface
    }

    /**
     * Paints the current game screen onto the panel.
     *
     * @param g the Graphics object used for drawing
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g); // Clear the previous frame
        g.drawImage(fullScreen, 0, 0, screenWidth2, screenHeight2, null);
    }
}

