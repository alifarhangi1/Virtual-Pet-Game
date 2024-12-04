package managers;

import misc.Pet;
import misc.Player;
import screens.PetStatusScreen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class GameManager {
    private Pet pet; // Active pet
    private List<Pet> pets; // All pets owned by the player
    private Timer timer; // Timer for periodic updates
    private Timer timerStatus; // Timer for
    private LocalDateTime startTime; // Game start time
    private Player player; // scrap.Player managing the pets
    private PetStatusScreen statusScreen;
    private static GameManager instance; // Singleton instance

    public GameManager(Player player)
    {
        this.player = player;
        this.pets = player.getPetList(); // Load pets from player
        this.pet = player.getPet(); // Set the active pet
        this.startTime = LocalDateTime.now();

        // Start a timer to periodically update the pet and manage gameplay
        this.timer = new Timer(10000, new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                if (pet != null) {
                    pet.update();
                }

            }
        });

        this.timerStatus = new Timer(1000, new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                if (pet != null)
                {
                    checkPetStatus();
                    updatePlayTime();
                }

            }
        });
        this.timer.start();
        this.timerStatus.start();
    }

    public static GameManager getInstance(Player player)
    {
        if (instance == null)
        {
            instance = new GameManager(player);
        }
        return instance;
    }

    public static GameManager getInstance()
    {
        if (instance == null) {
            throw new IllegalStateException("GameManager is not initialized yet.");
        }
        return instance;
    }

    public void updatePlayTime()
    {
        // Calculate elapsed playtime
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
        if (index >= 0 && index < pets.size())
        {
            pet = pets.get(index);
            player.setPlayerPet(index); // Update active pet in the scrap.Player
        }
    }

    public List<Pet> getPetArr()
    {
        return pets;
    }

    public void checkPetStatus()
    {
        if (pet == null) return;

        if (pet.getHP() <= 0)
        {
            timer.stop();
            System.out.println("Game Over! Your pet has run out of health.");
        } else if (pet.getFullness() <= 0)
        {
            System.out.println(pet.getName() + " is starving! Feed it quickly.");
        }
        else if (pet.getEnergy() <= 0)
        {
            System.out.println(pet.getName() + " is exhausted! Let it rest.");
        }
    }

    public ImageIcon resizeIcon(ImageIcon icon) {
        Image img = icon.getImage();
        Image resizedImg = img.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImg);
    }

    public Player getPlayer()
    {
        return player;
    }
}

