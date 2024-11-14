public class Pet
{
    private String name;
    private int hunger;
    private int happiness;
    private int energy;

    public Pet(String name)
    {
        this.name = name;
        this.hunger = 50;      // default starting value
        this.happiness = 50;
        this.energy = 50;
    }

    // Getters and setters
    public String getName() { return name; }
    public int getHunger() { return hunger; }
    public int getHappiness() { return happiness; }
    public int getEnergy() { return energy; }

    // Actions that affect the pet's stats
    public void feed()
    {
        hunger = Math.max(0, hunger - 10);
        happiness = Math.min(100, happiness + 5);
    }

    public void play()
    {
        happiness = Math.min(100, happiness + 10);
        energy = Math.max(0, energy - 15);
        hunger = Math.min(100, hunger + 5);
    }

    public void sleep()
    {
        energy = Math.min(100, energy + 20);
        hunger = Math.min(100, hunger + 10);
    }

    public void vet()
    {
        happiness = Math.min(100, happiness - 20);
        hunger = Math.min(100, hunger - 10);
        energy = Math.max(0, energy + 15);
    }

    // Update method to decrease stats over time
    public void update()
    {
        hunger = Math.min(100, hunger + 2);
        happiness = Math.max(0, happiness - 1);
        energy = Math.max(0, energy - 1);
    }

}
