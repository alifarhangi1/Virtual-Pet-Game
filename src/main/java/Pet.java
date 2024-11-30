import javax.swing.*;

public class Pet
{
    private String name;
    private String type;
    private int HP;
    private int fullness;
    private int petCooldown;
    private int happiness;
    private int energy;
    private boolean[] states = new boolean[4];
    private int maxHP;
    private int maxEnergy;
    private ImageIcon[] images;

    public Pet(String name, String type)
    {
        this.name = name;
        this.type = type;
        this.HP = 100;
        this.fullness = 100;
        this.petCooldown = 0;
        this.happiness = 100;
        this.energy = 100;
        this.maxHP = 100;
        this.maxEnergy = 100;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getHunger() {
        return fullness;
    }

    public int getHappiness() {
        return happiness;
    }

    public int getEnergy() {
        return energy;
    }

    public int getHP() {
        return HP;
    }

    public int getMaxHP() {
        return maxHP;
    }

    public int getMaxEnergy() {
        return maxEnergy;
    }

    public int getPetCooldown() {
        return petCooldown;
    }

    public void setCooldown(int i) {
        petCooldown = i;
    }

    // Actions that affect the pet's stats
    public void giveGift(int healAmount, int happinessAmount)
    {
        fullness = Math.min(100, fullness + 10);
        happiness = Math.min(100, happiness + 10);
    }


    public void feed(Item item) {
        fullness = Math.min(100, fullness + item.getFullAmount());
        happiness = Math.min(100, happiness + item.getHappyAmount());
        HP = Math.min(100, HP + item.getHealAmount());
    }

    public void play() {
        happiness = Math.min(100, happiness + 10);
        energy = Math.max(0, energy - 15);
        fullness = Math.max(0, fullness - 5);
        maxEnergy += 5;
    }

    public void sleep() {
        energy = Math.min(maxEnergy, energy + 20);
        fullness = Math.max(0, fullness - 10);
    }

    public void vet() {
        happiness = Math.max(0, happiness - 20);
        fullness = Math.min(100, fullness + 10);
        energy = Math.min(maxEnergy, energy + 15);
        HP = Math.min(maxHP, HP + 10);
    }

    public void exercise()
    {
        maxHP += 5;
        maxEnergy += 10;
        energy = Math.max(0, energy - 15);
    }

    // Update method to decrease stats over time
    public void update()
    {
        fullness = Math.max(0, fullness - 2);
        happiness = Math.max(0, happiness - 1);
        energy = Math.max(0, energy - 1);

        // Take damage if hunger is too low
        if (fullness <= 20)
        {
            HP = Math.max(0, HP - 5); // Lose 5 HP per update
        }

        // Heal if hunger is above 50
        if (fullness > 50 && HP < maxHP)
        {
            HP = Math.min(maxHP, HP + 1); // Regain 1 HP per update
        }
    }

    public void setMaxHP(int maxHP)
    {
        this.maxHP = maxHP;
    }

    public void setHP(int HP)
    {
        this.HP = HP;
    }

    public void setFullness(int fullness)
    {
        this.fullness = fullness;
    }

    public void setHappiness(int happiness)
    {
        this.happiness = happiness;
    }

    public void setEnergy(int energy)
    {
        this.energy = energy;
    }

    public void setMaxEnergy(int maxEnergy)
    {
        this.maxEnergy = maxEnergy;
    }

    public void setImages(ImageIcon[] images)
    {
        this.images = images;
    }

    public ImageIcon[] getImages()
    {
        return this.images;
    }



}
