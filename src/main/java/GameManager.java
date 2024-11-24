import javax.swing.*;
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
    private Player player;

    private static GameManager instance;

    private GameManager(Player player)
    {
        this.player = player;
        this.pets = player.getPetList(); // Initialize pets from Player
        this.pet = player.getPet();      // Set the active pet
        this.startTime = LocalDateTime.now();

        // Start a timer to periodically update the pet and check status
        this.timer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (pet != null) {
                    pet.update();
                    checkPetStatus();
                }
            }
        });
        this.timer.start();
    }

    public static GameManager getInstance(Player player)
    {
        if (instance == null)
        {
            instance = new GameManager(player);
        }
        return instance;
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

    public void setPet(int index)
    {
        if (index >= 0 && index < pets.length) {
            pet = pets[index];
            player.setPlayerPet(index); // Update the Player's pet
        }
    }

    public Pet[] getPetArr()
    {
        return pets;
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

