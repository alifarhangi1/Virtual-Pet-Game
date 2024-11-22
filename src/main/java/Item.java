public class Item
{
    private String name = "";
    private int healAmount;
    private int happyAmount;
    private int fullAmount;
    private int energyAmount;
    private int MaxHpControl;
    private int MaxEnergyControl;

    String getName()
    {
        return name;
    }

    int getHealAmount()
    {
        return healAmount;
    }

    int getHappyAmount()
    {
        return happyAmount;
    }

    public int getMaxEnergyControl()
    {
        return MaxEnergyControl;
    }

    public int getMaxHpControl()
    {
        return MaxHpControl;
    }

    public int getEnergyAmount()
    {
        return energyAmount;
    }

    public int getFullAmount()
    {
        return fullAmount;
    }
}
