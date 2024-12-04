package misc;

import misc.Item;
import misc.Pet;

import java.util.HashMap;
import java.util.List;

public class Player {
    private int score;
    private HashMap<String, Item> inventory;
    private List<Pet> petList;
    private long playTime;
    private Pet pet;
    private String username;
    private String passwordString;

    // Default constructor with minimal initialization
    public Player()
    {
        this.username = null;
        this.passwordString = null;
        this.score = 0;
        this.inventory = new HashMap<>();
        this.playTime = 0;
        this.pet = null;
    }

    // Existing constructor
    public Player(String id, char[] password)
    {
        this.username = id;
        this.passwordString = new String(password);
        this.score = 0;
        this.inventory = new HashMap<>();
        this.playTime = 0;
        this.pet = null;
    }

    public void addPet(Pet pet)
    {
        this.petList.add(pet);
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

    public void setInventory(HashMap<String, Item> inventory)
    {
        this.inventory = inventory != null ? inventory : new HashMap<>();
    }

    public void setPlayTime(long playTime) {
        this.playTime = playTime;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    // Existing getters remain the same
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

    public HashMap<String, Item> getInventory() {
        return inventory;
    }

    public List<Pet> getPetList() {
        return petList;
    }

    public Pet getPet() {
        return this.pet;
    }

    // Existing methods for player interactions remain the same
    public void giveGift(Pet pet, Item item)
    {
        if (inventory.containsKey(item.getName()))
        {
            pet.giveGift(item.getFullAmount(), item.getHappyAmount(), item.getMaxHpControl());
        }
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

    public void setPlayerPet(int index)
    {
        if (petList != null && index >= 0 && index < petList.size()) {
            this.pet = petList.get(index);
        }
    }

    @Override
    public String toString()
    {
        return "scrap.Player{" +
                "username='" + username + '\'' +
                ", score=" + score +
                ", inventorySize=" + (inventory != null ? inventory.size() : 0) +
                ", petListSize=" + (petList != null ? petList.size() : 0) +
                ", playTime=" + playTime +
                '}';
    }
}