//package scrap;
//
//import misc.Wolf;
//
//import java.util.HashMap;
//
//public class Player
//{
//
//    private int score;
//    private HashMap<String, Item> inventory;
//    private Pet[] petList;
//    private long playTime;
//    private Pet playerPet;
//    private String id;
//    private String password;
//    public boolean[] miniGame;
//
//
//    Player()
//    {
//        this.score = 0;
//        this.inventory = new HashMap<>();
//        Pet Wolf = new Wolf("JohnDo");
//        Pet Owl = new Wolf("JamesDo");
//        Pet Panda = new Pet("Alex", "Human");
//        this.petList = new Pet[] {Wolf, Owl, Panda};
//        this.playTime = 0;
//        this.playerPet = null;
//        this.miniGame = new boolean[petList.length];
//    }
//
//    Pet getPet()
//    {
//        return this.playerPet;
//    }
//
//    public boolean[] getMiniGame()
//    {
//         return this.miniGame;
//    }
//
//    void feedPet (Pet pet, Item item)
//    {
//        if (inventory.containsKey(item.getName()))
//        {
//            pet.feed(item);
//        }
//    }
//
//    void giveGift(Pet pet, Item item)
//    {
//        if (inventory.containsKey(item.getName()))
//        {
//            if (item.getType() == 2)
//            {
//                if (pet instanceof Wolf)
//                {
//                    ((Wolf) pet).Evolve();
//                }
//                else if (pet instanceof Owl)
//                {
//                    ((Owl) pet).Evolve();
//                }
//                else if (pet instanceof Panda)
//                {
//                    ((Panda) pet).Evolve();
//                }
//
//                return;
//            }
//            // Apply the gift's healing and happiness effects
//            pet.giveGift(item.getHealAmount(), item.getHappyAmount());
//        }
//    }
//
//    void exercisePet(Pet pet)
//    {
//        pet.exercise();
//    }
//
//    void takeToVet(Pet pet)
//    {
//        pet.vet();
//    }
//
//    void playWithPet(Pet pet)
//    {
//        pet.play();
//    }
//
//    void increaseScore(int score)
//    {
//        this.score += score;
//    }
//
//    void decreaseScore(int score)
//    {
//        this.score -= score;
//    }
//
//    public long getPlayTime()
//    {
//        return playTime;
//    }
//
//    int getScore()
//    {
//        return this.score;
//    }
//
//    void addItem(Item item)
//    {
//        inventory.put(item.getName(), item);
//    }
//
//    public void setPlayTime(long playTime)
//    {
//        this.playTime = playTime;
//    }
//
//    public HashMap<String, Item> getInventory() { return inventory; }
//
//    public void setPlayerPet(int index)
//    {
//        if (index >= 0 && index < petList.length && miniGame[index])
//        {
//            this.playerPet = petList[index];
//        }
//    }
//
//    public void finishMinigame(int index)
//    {
//        miniGame[index] = true;
//    }
//
//    public Pet[] getPetList() { return petList; }
//
//    public void setScore(int score)
//    {
//        this.score = score;
//    }
//}
