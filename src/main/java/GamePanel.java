
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class GamePanel extends JPanel implements Runnable{

    JFrame mainWindow;
    // SCREEN SETTINGS
    final int originalTileSize = 16; // 16x16 title
    final int scale = 3; // Make character 48x48
    public final int tileSize = originalTileSize * scale; // 48x48 tile
    public final int maxScreenCol = 20; // 16 48x48 tiles vertically
    public final int maxScreenRow = 12; // 12 48x48 tiles horizontally
    public final int screenWidth = tileSize*maxScreenCol; // 960 pixels
    public final int screenHeight = tileSize*maxScreenRow; // 576 pixels

    // WORLD SETTINGS

    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;

    // For FULL SCREEN
    int screenWidth2 = screenWidth;
    int screenHeight2 = screenHeight;
    BufferedImage fullScreen;
    Graphics2D g2D;


    // Dialogue state (when talking to npcs)
    public boolean dialogueState;


    // Selected level name

    public String levelName;


    // Music player

    public MusicPlayer musicPlayer = new MusicPlayer();


    //FPS 60 = Screen is updated 60 times per second, kinda crazy when you think about it
    double FPS = 60;

    TileManager tileManager;

    KeyHandler keyHandler = new KeyHandler(this);


    public CollisionChecker collisionChecker = new CollisionChecker(this);

    // Asset setter
    public AssetSetter assetSetter;
    // UI
    public MinigameUI ui = new MinigameUI(this);

    //Game thread
    Thread gameThread;



    // ENTITY and OBJECT
    public Player player = new Player(this, keyHandler);
    public SuperObject obj[] = new SuperObject[10]; // amount of objects at the same time in game
    public Entity npc[] = new Entity[11];




    public GamePanel(String levelName){

        this.levelName = levelName;
//        this.mainWindow = mainWindow;

        tileManager = new TileManager(this, levelName);

        assetSetter = new AssetSetter(this, levelName);

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true); // Enabling this can improve games rendering performance
        this.addKeyListener(keyHandler);

        this.setFocusable(true); // Enable GamePanel to receive key input
    }


    // Call this method to set up stuff in game
    public void setUpGame(){
        assetSetter.setObject();
        assetSetter.setNPC();

        fullScreen = new BufferedImage(screenWidth,screenHeight,BufferedImage.TYPE_INT_ARGB);
        g2D = (Graphics2D)fullScreen.getGraphics();

        setFullScreen();

        // ADD MUSIC HERE
        if(levelName.equals("Maze Madness") || levelName.equals("Kung Fu Chaos") || levelName.equals("Dragon Duel")) {
            assetSetter.setMusic();
        }



        // Set enemies left for Kung Fu Chaos Level
        if(levelName.equals("Kung Fu Chaos")){
            player.enemiesLeft = npc.length-1;

        }

    }

    public void setFullScreen(){
       // Get local screen size
        GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice graphicsDevice = graphicsEnvironment.getDefaultScreenDevice();
        graphicsDevice.setFullScreenWindow(WindowManager.getWindow());

        // Get full screen width and height
        screenWidth2 = WindowManager.getWindow().getWidth();
        screenHeight2 = WindowManager.getWindow().getHeight();
    }

    public void startGameThread(){

        gameThread = new Thread(this);
        gameThread.start(); // calls run() method
    }

    @Override
    public void run() {
        // Implement Game Loop (VERY IMPORTANT!!!!!!!!!!)
        // 1. UPDATE: update information such as character position
        // 2. DRAW: draw the screen with the updated information

        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while(gameThread != null){

            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if(delta >=1 ){
                update();
                drawToTempScreen(); // draw everything to buffered image
                repaint(); // draw the buffered image to the screen
                delta--;
            }
        }
    }

    public void update(){
        if(dialogueState){
            return;
        }
        //PLAYER
        player.update();

        //NPC
        for(int i = 0; i < npc.length; i++){
            if(npc[i] != null){
                npc[i].update();
            }
        }
    }

    // Call to get out of the paused state
    public void togglePause() {
        dialogueState = !dialogueState; // Toggle the pause state
    }

    public void drawToTempScreen(){
        //TILE
        tileManager.draw(g2D);
        //OBJECTS
        for(int i = 0; i < obj.length; i++){
            if(obj[i] != null){
                obj[i].draw(g2D, this);
            }
        }

        //NPC
        for(int i = 0; i < npc.length; i++){
            if(npc[i] != null){
                npc[i].draw(g2D);
            }
        }

        //PLAYER
        player.draw(g2D);

        // UI
        ui.draw(g2D);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Clear the previous frame
        g.drawImage(fullScreen, 0, 0, screenWidth2, screenHeight2, null);
    }



}
