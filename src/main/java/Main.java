import managers.DatabaseManager;
import managers.ScreenManager;
import managers.AudioManager;
import screens.*;
import screens.TutorialScreen;

/**
 * Main method for the game, initializes the manager classes for audio({@link AudioManager}) and screen manipulation({@link ScreenManager}),
 * also adds all the different screens to the screen manager.
 *
 * @version 1.0
 * @author Luca Duarte
 * @author Robin Lee
 * @author Ali Farhangi
 * @author Adam Yassine
 * @author Yazan Abushirbi
 * @see AudioManager
 * @see ScreenManager
 * @see TitleScreen
 * @see SaveScreen
 * @see NewSaveScreen
 * @see LoadSaveScreen
 * @see ParentalControlScreen
 */
public class Main
{
    public static void main(String[] args) {
        /** Initialize managers */
        AudioManager audioManager = AudioManager.getInstance();
        DatabaseManager databaseManager = DatabaseManager.getInstance();
        ScreenManager screenManager = ScreenManager.getInstance();


        /** Audio manager plays the intro and menu music */
        audioManager.playIntroSound();
        audioManager.startBackgroundMusic();

        /** Add all screens to manager */
        screenManager.addScreen("title", new TitleScreen());
        screenManager.addScreen("save", new SaveScreen());
        screenManager.addScreen("newgame", new NewSaveScreen());
        screenManager.addScreen("loadgame", new LoadSaveScreen());
        screenManager.addScreen("parental", new ParentalControlScreen());
        screenManager.addScreen("tutorial", new TutorialScreen());

        /** Show main frame and initial screen */
        screenManager.showMainFrame();
        screenManager.showScreen("title");
    }
}

