import managers.ScreenManager;
import managers.AudioManager;
import screens.NewSaveScreen;
//import screens.ParentalControl;
import screens.SaveScreen;
import screens.TitleScreen;
// Import other screens as needed

public class Main {
    public static void main(String[] args) {
        // Initialize managers
        ScreenManager screenManager = ScreenManager.getInstance();
        AudioManager audioManager = AudioManager.getInstance();

        // Start audio
        audioManager.playIntroSound();
        audioManager.startBackgroundMusic();

        // Add screens
        screenManager.addScreen("title", new TitleScreen());
        screenManager.addScreen("save", new SaveScreen());
        screenManager.addScreen("newgame", new NewSaveScreen());
//        screenManager.addScreen("Parental", new ParentalControlScreen());


        // Show main frame and initial screen
        screenManager.showMainFrame();
        screenManager.showScreen("title");
    }
}

