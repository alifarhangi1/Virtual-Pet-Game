import javax.swing.*;

public class Item
{
    private String name = "";
    private int healAmount;
    private int happyAmount;
    private int fullAmount;
    private int energyAmount;
    private int MaxHpControl;
    private int MaxEnergyControl;
    private String discription;
    private ImageIcon img;

    public Item(ImageIcon icon, String s)
    {
    this.img = icon;
    this.discription = s;
    this.healAmount = 0;
    this.happyAmount = 0;
    this.fullAmount = 0;
    this.energyAmount = 0;
    this.MaxHpControl = 0;
    this.MaxEnergyControl = 0;
    }

    public void setHealAmount(int healAmount) {
        this.healAmount = healAmount;
    }

    public void setHappyAmount(int happyAmount) {
        this.happyAmount = happyAmount;
    }

    public void setFullAmount(int fullAmount) {
        this.fullAmount = fullAmount;
    }

    public void setEnergyAmount(int energyAmount) {
        this.energyAmount = energyAmount;
    }

    public void setMaxHpControl(int maxHpControl) {
        MaxHpControl = maxHpControl;
    }

    public void setMaxEnergyControl(int maxEnergyControl) {
        MaxEnergyControl = maxEnergyControl;
    }

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
    int getMaxEnergyControl() { return MaxEnergyControl; }
    int getMaxHpControl()
    {
        return MaxHpControl;
    }
    int getEnergyAmount()
    {
        return energyAmount;
    }
    int getFullAmount()
    {
        return fullAmount;
    }
    String getDiscription() { return discription; }
    ImageIcon getImg() { return img; }
}
