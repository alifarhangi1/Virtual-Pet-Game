import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameManager
{
    private Pet pet;
    private Timer timer;

    public GameManager(String petName)
    {
        pet = new Pet(petName);

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

