import javax.swing.*;

public class Item {
    private String name = "";
    private int healAmount;
    private int happyAmount;
    private int fullAmount;
    private int energyAmount;
    private int MaxHpControl;
    private int MaxEnergyControl;
    private String description;
    private ImageIcon img;
    private int type;
    private int amount;

    public Item(String description) {
        this.description = description;
        this.healAmount = 0;
        this.happyAmount = 0;
        this.fullAmount = 0;
        this.energyAmount = 0;
        this.MaxHpControl = 0;
        this.MaxEnergyControl = 0;
        this.type = 0;
        this.amount = 0;
    }

    public void setImage(ImageIcon icon) {
        this.img = icon;
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

    public void setType(int type) {
        this.type = type;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getHealAmount() {
        return healAmount;
    }

    public int getHappyAmount() {
        return happyAmount;
    }

    public int getMaxEnergyControl() {
        return MaxEnergyControl;
    }

    public int getMaxHpControl() {
        return MaxHpControl;
    }

    public int getEnergyAmount() {
        return energyAmount;
    }

    public int getFullAmount() {
        return fullAmount;
    }

    public String getDescription() {
        return description;
    }

    public ImageIcon getImg() {
        return img;
    }

    public int getType() {
        return type;
    }

    public int getAmount() {
        return amount;
    }
}
