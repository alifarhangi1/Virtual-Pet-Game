import java.util.HashMap;

public class player
{

    private int score;
    private HashMap<String, Item> inventory;
    private Pet[] petList;
    private long playTime;
    private Pet playerPet;
    private String id;
    private String password;


    player()
    {
        this.score = 0;
        this.inventory = new HashMap<>();
        this.petList = GameManager.getInstance().getPetArr();
        this.playTime = 0;
        this.playerPet = petList[0];
    }

    Pet getPet()
    {
        return this.playerPet;
    }

    void feedPet (Pet pet, Item item)
    {
        if (inventory.containsKey(item.getName()))
        {
            pet.feed(item.getHealAmount(), item.getHappyAmount());
        }
    }

    void giveGift(Pet pet, Item item)
    {
        if (inventory.containsKey(item.getName()))
        {
            pet.feed(item.getHealAmount(), item.getHappyAmount());
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

    public void updatePet(int index)
    {
        this.playerPet = petList[index];
    }

    /* have no clue until the minigame is updated
    void playMinigame(Minigame)
    {

    }*/


}
