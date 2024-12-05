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

    /**
     * Manages the core game logic, including pet management, gameplay timers, and player interactions.
     * Implements a singleton pattern to ensure a single instance.
     *
     * @version 1.0
     * @author Sangjae Lee
     */
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

    /**
     * Constructs a new GameManager instance for the given player.
     * Initializes pets, timers, and sets the active pet.
     *
     * @param player the player managing the pets
     */
    public static GameManager getInstance(Player player)
    {
        if (instance == null)
        {
            instance = new GameManager(player);
        }
        return instance;
    }

    /**
     * Updates the player's total playtime by calculating the elapsed time.
     */
    public void updatePlayTime()
    {
        // Calculate elapsed playtime
        long secondsElapsed = ChronoUnit.SECONDS.between(startTime, LocalDateTime.now());
        player.setPlayTime(secondsElapsed);
    }

    /**
     * Retrieves the total playtime of the player.
     *
     * @return the total playtime in seconds
     */
    public long getPlayTime()
    {
        updatePlayTime();
        return player.getPlayTime();
    }

    /**
     * Sets the active pet based on its index in the player's pet list.
     *
     * @param index the index of the pet in the list
     */
    public void setPet(int index)
    {
        if (index >= 0 && index < pets.size())
        {
            pet = pets.get(index);
            player.setPlayerPet(index); // Update active pet in the scrap.Player
        }
    }

    /**
     * Retrieves the list of all pets owned by the player.
     *
     * @return the list of pets
     */
    public List<Pet> getPetArr()
    {
        return pets;
    }

    /**
     * Checks the status of the active pet and stops the game if the pet's health reaches zero.
     * Prints warnings if the pet's fullness or energy is low.
     */
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

    /**
     * Resizes an ImageIcon to a specified width and height.
     *
     * @param icon the ImageIcon to resize
     * @return the resized ImageIcon
     */
    public ImageIcon resizeIcon(ImageIcon icon) {
        Image img = icon.getImage();
        Image resizedImg = img.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImg);
    }

    /**
     * Retrieves the player associated with this GameManager.
     *
     * @return the player
     */
    public Player getPlayer()
    {
        return player;
    }
}

