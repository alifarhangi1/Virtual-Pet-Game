package misc;

import javax.swing.*;
/**
 * Represents the Item class
 * Items are used to modify happiness, energy, and Max HP
 *
 * @author Sangjae
 * @version 1
 */
public class Item {
    /**
     * name of the Item
     */
    private String name = "";
    /**
     * heal amount of the item
     */
    private int healAmount;
    /**
     * how much happiness it heals
     */
    private int happyAmount;
    /**
     * how much energy it heals
     */
    private int fullAmount;
    /**
     * how much sleep it heals
     */
    private int energyAmount;
    /**
     * increase max hp
     */
    private int MaxHpControl;
    /**
     * increase max energy
     */
    private int MaxEnergyControl;
    /**
     * description of the item
     */
    private String description;
    /**
     * String of the image path
     */
    private String image;
    /**
     * type of item
     */
    private int type;
    /**
     * amount of item present in the inventory
     */
    private int amount;


    /**
     * Empty Constructor for Database
     */
    public Item() {}

    /**
     * constructor for the Item
     * @param description
     */
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

    public void setImage(String imagePath) {
        this.image = imagePath;
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

    public String getImage() {
        return this.image;
    }

    public int getType() {
        return type;
    }

    public int getAmount() {
        return amount;
    }
}
