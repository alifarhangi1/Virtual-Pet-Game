import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * The KeyHandler class handles keyboard input from the user,
 * detecting key presses and releases to control the game.
 */
public class KeyHandler implements KeyListener {

    /** Flags to indicate the state of movement keys (W, A, S, D). */
    public boolean upPressed, downPressed, leftPressed, rightPressed;

    /** Flag to indicate if the escape key (ESC) is pressed. */
    public boolean escPressed;

    /** The GamePanel associated with this KeyHandler. */
    GamePanel gamePanel;

    /**
     * Constructs a KeyHandler with the specified GamePanel.
     *
     * @param gamePanel the GamePanel to associate with this KeyHandler
     */
    public KeyHandler(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    /**
     * Invoked when a key is typed. This method is not used in the current implementation.
     *
     * @param e the KeyEvent associated with the key typed
     */
    @Override
    public void keyTyped(KeyEvent e) {
        // Intentionally left blank
    }

    /**
     * Invoked when a key is pressed. Updates movement flags and handles
     * game-specific actions such as pausing or interacting with the game state.
     *
     * @param e the KeyEvent associated with the key pressed
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        // Movement keys
        if (code == KeyEvent.VK_W) {
            upPressed = true;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = true;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = true;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = true;
        }

        // Continue dialogue or toggle pause state
        if (code == KeyEvent.VK_C) {
            gamePanel.togglePause();
        }
    }

    /**
     * Invoked when a key is released. Updates movement flags and handles
     * actions such as exiting the level or stopping the music.
     *
     * @param e the KeyEvent associated with the key released
     */
    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        // Movement keys
        if (code == KeyEvent.VK_W) {
            upPressed = false;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = false;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = false;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = false;
        }

        // Close level and stop music
        if (gamePanel.levelName.equals("Maze Madness") || gamePanel.levelName.equals("Kung Fu Chaos") || gamePanel.levelName.equals("Dragon Duel")) {
            if (code == KeyEvent.VK_ESCAPE) {
                WindowManager.getWindow().dispose(); // Close the game window
                gamePanel.musicPlayer.stopMusic();   // Stop the background music
                new LevelSelect();                  // Open the level selection screen
            }
        }
    }
}


//import java.awt.event.KeyEvent;
//import java.awt.event.KeyListener;
//
//public class KeyHandler implements KeyListener {
//
//    public boolean upPressed, downPressed, leftPressed, rightPressed;
//    public boolean escPressed;
//    GamePanel gamePanel;
//    public KeyHandler(GamePanel gamePanel){
//        this.gamePanel = gamePanel;
//    }
//    @Override
//    public void keyTyped(KeyEvent e) {
//    }
//
//    @Override
//    public void keyPressed(KeyEvent e) {
//        int code = e.getKeyCode();
//
//
//        if(code == KeyEvent.VK_W){
//            upPressed = true;
//        }
//        if(code == KeyEvent.VK_S){
//            downPressed = true;
//        }
//        if(code == KeyEvent.VK_D){
//            rightPressed = true;
//        }
//        if(code == KeyEvent.VK_A){
//            leftPressed = true;
//        }
//
//
//        // Continue from dialogue state
//        if(code == KeyEvent.VK_C){
//            gamePanel.togglePause();
////            gamePanel.assetSetter.setMusic();
//        }
//
//    }
//
//    @Override
//    public void keyReleased(KeyEvent e) {
//        int code = e.getKeyCode();
//
//        if(code == KeyEvent.VK_W){
//            upPressed = false;
//        }
//        if(code == KeyEvent.VK_S){
//            downPressed = false;
//        }
//        if(code == KeyEvent.VK_D){
//            rightPressed = false;
//        }
//        if(code == KeyEvent.VK_A){
//            leftPressed = false;
//        }
//
//        // Close level
//        if(gamePanel.levelName.equals("Maze Madness") || gamePanel.levelName.equals("Kung Fu Chaos") || gamePanel.levelName.equals("Dragon Duel")){
//            if(code == KeyEvent.VK_ESCAPE){
//                WindowManager.getWindow().dispose();
//                gamePanel.musicPlayer.stopMusic();
//                new LevelSelect();
//            }
//        }
//    }
//}
