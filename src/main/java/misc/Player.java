package misc;

import misc.Item;
import misc.Pet;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Represents a Player in the pet game. Each player has a username, password, score, inventory, a list of pets,
 * a selected pet, and total playtime.
 *
 * @author Sangjae Lee
 * @version 1.0
 */
public class Player
{
    private long totalPlaytime;
    private int score;
    private HashMap<String, Item> inventory;
    private ArrayList<Pet> petList;
    private long playTime;
    private Pet pet;
    private String username;
    private String passwordString;
    private int login;

    /**
     * Default constructor with minimal initialization.
     */
    public Player()
    {
        this.username = null;
        this.passwordString = null;
        this.score = 0;
        this.inventory = new HashMap<>();
        this.petList = new ArrayList<Pet>();
        this.playTime = 0;
        this.pet = null;
        this.login = 1;
        this.totalPlaytime = 0;
    }

    /**
     * Constructor that initializes a Player with a username and password.
     *
     * @param id       the username of the player
     * @param password the password of the player as a character array
     */
    public Player(String id, char[] password)
    {
        this.username = id;
        this.passwordString = new String(password);
        this.score = 0;
        this.inventory = new HashMap<>();
        this.petList = new ArrayList<>();
        this.playTime = 0;
        this.pet = null;
        this.login = 1;
        this.totalPlaytime = 0;
    }

    // Comprehensive setters for each field
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPasswordString(String passwordString) {
        this.passwordString = passwordString;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setInventory(HashMap<String, Item> inventory) { this.inventory = inventory != null ? inventory : new HashMap<>(); }

    public void setPlayTime(long playTime) {
        this.playTime = playTime;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public String getUsername() {
        return username;
    }

    public String getPasswordString() {
        return passwordString;
    }

    public int getScore() {
        return this.score;
    }

    public long getPlayTime() {
        return playTime;
    }

    public long getTotalPlaytime() {
        return totalPlaytime;
    }

    public HashMap<String, Item> getInventory() {
        return inventory;
    }

    public void addPet(Pet pet)
    {
        this.petList.add(pet);
    }

    public Pet getPet() {
        return this.pet;
    }

    /**
     * Gives a gift to the specified pet using an item from the player's inventory.
     *
     * @param pet  the pet to receive the gift
     * @param item the item to give
     */
    public void giveGift(Pet pet, Item item) {
        if (inventory.containsKey(item.getName()))
        {
            pet.giveGift(item.getFullAmount(),item.getHappyAmount(), item.getMaxHpControl());
        }
    }

    public int getLogin() {
        return login;
    }

    public void setLogin(int login) {
        this.login = login;
    }

    public void setTotalPlaytime(long totalPlaytime) {
        this.totalPlaytime = totalPlaytime;
    }

    public void addLogin() {
        this.login++;
    }

    public void exercisePet(Pet pet) {
        pet.exercise();
    }

    void takeToVet(Pet pet) {
        pet.vet();
    }

    void playWithPet(Pet pet) {
        pet.play();
    }

    void increaseScore(int score) {
        this.score += score;
    }

    void decreaseScore(int score) {
        this.score -= score;
    }

    public void addItem(Item item) {
        inventory.put(item.getName(), item);
    }

    public void addTotalPlaytime() {
        totalPlaytime += playTime;
    }

    /**
     * Sets the currently active pet for the player by its index in the pet list.
     *
     * @param index the index of the pet in the pet list
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    public void setPlayerPet(int index) {
        if (petList != null && index >= 0 && index < petList.size())
        {
            this.pet = petList.get(index);
        }
    }

    /**
     * Provides a string representation of the player's current state.
     *
     * @return a string summarizing the player's username, score, inventory size, pet list size, and playtime
     */
    @Override
    public String toString() {
        return "scrap.Player{" +
                "username='" + username + '\'' +
                ", score=" + score +
                ", inventorySize=" + (inventory != null ? inventory.size() : 0) +
                ", petListSize=" + (petList != null ? petList.size() : 0) +
                ", playTime=" + playTime +
                '}';
    }

    public void setPetList(ArrayList<Pet> petList) {
        this.petList = petList;
    }

    public ArrayList<Pet> getPetList() {
        return petList;
    }
}