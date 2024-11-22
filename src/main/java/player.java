import java.util.HashMap;

public class player
{

    private int score;
    private HashMap<String, Item> inventory;
    private Pet[] petList;
    private long playTime;


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

    /* have no clue until the minigame is updated
    void playMinigame(Minigame)
    {

    }*/


}
