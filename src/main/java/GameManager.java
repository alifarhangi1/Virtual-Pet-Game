import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class GameManager
{
    private Pet pet;
    private Pet[] pets = new Pet[8];
    private Timer timer;
    private LocalDateTime startTime; // Track when the game started
    private player player;
    public HashMap<Integer, Boolean> miniGame;
    private static GameManager instance;

    public GameManager()
    {
        player = new player();
        startTime = LocalDateTime.now();
        timer = new Timer(1000, new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                pet.update();
                checkPetStatus();
                // Additional code to update the UI based on pet’s status
            }
        });
        timer.start();
    }

    public void updatePlayTime()
    {
        // Calculate the difference in seconds between the current time and the start time
        long secondsElapsed = ChronoUnit.SECONDS.between(startTime, LocalDateTime.now());
        player.setPlayTime(secondsElapsed);
    }

    public long getPlayTime()
    {
        updatePlayTime();
        return player.getPlayTime();
    }

    public static GameManager getInstance()
    {
        if (instance == null)
        {
            instance = new GameManager();
        }
        return instance;
    }

    public HashMap<Integer, Boolean> getMyMap()
    {
        return miniGame;
    }

    public void setPet(int index)
    {
        pet = pets[index];
    }

    private void checkPetStatus()
    {
        if (pet.getHunger() >= 100 || pet.getEnergy() <= 0)
        {
            timer.stop();
            System.out.println("Game Over! Your pet needs better care.");
        }
    }
}

