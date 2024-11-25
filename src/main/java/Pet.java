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
    private boolean isSick;

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
        this.isSick = false;
    }

    // Getters and setters
    public String getName()
    {
        return name;
    }
    public int getHunger()
    {
        return fullness;
    }
    public int getHappiness()
    {
        return happiness;
    }
    public int getEnergy()
    {
        return energy;
    }

    public int getHP()
    {
        return HP;
    }

    public int getMaxHP()
    {
        return maxHP;
    }

    public int getMaxEnergy()
    {
        return maxEnergy;
    }

    public int getPetCooldown()
    {
        return petCooldown;
    }
    public void sick()
    {
        isSick = true;
    }

    public void setCooldown(int i)
    {
        petCooldown = i;
    }

    // Actions that affect the pet's stats
    public void giveGift(int healAmount, int happinessAmount)
    {
        fullness = Math.min(100, fullness + 10);
        happiness = Math.min(100, happiness + 10);
    }

    public void feed()
    {
        fullness = Math.min(100, fullness + 10);
        happiness = Math.min(100, happiness + 10);
    }

    public void play()
    {
        happiness = Math.min(100, happiness + 10);
        energy = Math.max(0, energy - 15);
        fullness = Math.max(0, fullness - 5);
        maxEnergy = maxEnergy + 5;
    }

    public void sleep()
    {
        energy = Math.min(maxEnergy, energy + 20);
        fullness = Math.max(0, fullness - 10);
    }

    public void vet()
    {
        happiness = Math.min(100, happiness - 20);
        fullness = Math.min(100, fullness + 10);
        energy = Math.max(0, energy + 15);
        HP = Math.min(maxHP, HP + 10);
    }

    public void exercise()
    {
        maxHP = maxHP + 5;
        maxEnergy = maxEnergy + 10;
        energy = Math.max(0, energy - 15);
    }

    public void getGift(Item item)
    {
        happiness = Math.min(100, happiness + item.getHappyAmount());
        HP = Math.min(maxHP, HP + item.getHealAmount());
        energy = Math.min(maxEnergy, energy + item.getEnergyAmount());
        maxHP = maxHP + item.getMaxHpControl();
        energy = maxEnergy + item.getEnergyAmount();
        fullness = Math.min(100, fullness + item.getFullAmount());
    }

    // Update method to decrease stats over time
    public void update()
    {
        fullness = Math.min(100, fullness - 2);
        happiness = Math.max(0, happiness - 1);
        energy = Math.max(0, energy - 1);
        if (fullness > 50)
        {
            HP = Math.min(100, HP + 1);
        }
    }
}
