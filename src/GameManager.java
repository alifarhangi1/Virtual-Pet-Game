package src;

import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class GameManager
{
    private Pet pet;
    private Pet[] pets = new Pet[8];
    private Timer timer;
    public HashMap<Integer, Boolean> miniGame;
    private static GameManager instance;

    public GameManager()
    {
        // Set up a timer to update pet stats every second
        timer = new Timer(1000, new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                pet.update();
                checkPetStatus();
                // Additional code to update the UI based on pet’s status
            }
        });
        timer.start();
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
        // Update UI as needed
    }

    public void feedPet()
    {
        pet.feed();
        // Update UI as needed
    }

    public void playWithPet()
    {
        pet.play();
        // Update UI as needed
    }

    public void sleepPet()
    {
        pet.sleep();
        // Update UI as needed
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

