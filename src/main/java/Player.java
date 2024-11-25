import java.util.HashMap;

public class Player
{

    private int score;
    private HashMap<String, Item> inventory;
    private Pet[] petList;
    private long playTime;
    private Pet playerPet;
    private String id;
    private String password;
    public boolean[] miniGame;


    Player()
    {
        this.score = 0;
        this.inventory = new HashMap<>();
        Pet John = new Pet("JohnDo", "Human");
        Pet Steve = new Pet("steve", "Human");
        Pet Alex = new Pet("Alex", "Human");
        this.petList = new Pet[] {John, Steve, Alex};
        this.playTime = 0;
        this.playerPet = null;
        this.miniGame = new boolean[petList.length];
    }

    Pet getPet()
    {
        return this.playerPet;
    }

    public boolean[] getMiniGame()
    {
         return this.miniGame;
    }

    void feedPet (Pet pet, Item item)
    {
        if (inventory.containsKey(item.getName()))
        {
            pet.feed();
        }
    }

    void giveGift(Pet pet, Item item)
    {
        if (inventory.containsKey(item.getName()))
        {
            pet.giveGift(item.getHealAmount(), item.getHappyAmount());
        }
    }

    void exercisePet(Pet pet)
    {
        pet.exercise();
    }

    void takeToVet(Pet pet)
    {
        pet.vet();
    }

    void playWithPet(Pet pet)
    {
        pet.play();
    }

    void increaseScore(int score)
    {
        this.score += score;
    }

    void decreaseScore(int score)
    {
        this.score -= score;
    }

    public long getPlayTime()
    {
        return playTime;
    }

    int getScore()
    {
        return this.score;
    }

    void addItem(Item item)
    {
        inventory.put(item.getName(), item);
    }

    public void setPlayTime(long playTime)
    {
        this.playTime = playTime;
    }

    public HashMap<String, Item> getInventory() { return inventory; }

    public void setPlayerPet(int index)
    {
        if (index >= 0 && index < petList.length && miniGame[index]) {
            this.playerPet = petList[index];
        }
    }

    public void finishMinigame(int index)
    {
        miniGame[index] = true;
    }

    public Pet[] getPetList() { return petList; }
}
