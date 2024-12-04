package Testing;

import misc.Item;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestItems
{
    @Test
    void testItemConstructor() {
        String description = "A healing potion.";
        Item item = new Item(description);

        assertEquals(description, item.getDescription());
        assertEquals(0, item.getHealAmount());
        assertEquals(0, item.getHappyAmount());
        assertEquals(0, item.getFullAmount());
        assertEquals(0, item.getEnergyAmount());
        assertEquals(0, item.getMaxHpControl());
        assertEquals(0, item.getMaxEnergyControl());
        assertEquals(0, item.getType());
        assertEquals(0, item.getAmount());
    }

    @Test
    void testSetName() {
        Item item = new Item("Test item");
        item.setName("Potion");

        assertEquals("Potion", item.getName());
    }

    @Test
    void testSetHealAmount() {
        Item item = new Item("Healing item");
        item.setHealAmount(50);

        assertEquals(50, item.getHealAmount());
    }

    @Test
    void testSetHappyAmount() {
        Item item = new Item("Happy item");
        item.setHappyAmount(20);

        assertEquals(20, item.getHappyAmount());
    }

    @Test
    void testSetFullAmount() {
        Item item = new Item("Full item");
        item.setFullAmount(30);

        assertEquals(30, item.getFullAmount());
    }

    @Test
    void testSetEnergyAmount() {
        Item item = new Item("Energy item");
        item.setEnergyAmount(40);

        assertEquals(40, item.getEnergyAmount());
    }

    @Test
    void testSetMaxHpControl() {
        Item item = new Item("Max HP item");
        item.setMaxHpControl(100);

        assertEquals(100, item.getMaxHpControl());
    }

    @Test
    void testSetMaxEnergyControl() {
        Item item = new Item("Max Energy item");
        item.setMaxEnergyControl(80);

        assertEquals(80, item.getMaxEnergyControl());
    }

    @Test
    void testSetType() {
        Item item = new Item("Type item");
        item.setType(1);

        assertEquals(1, item.getType());
    }

    @Test
    void testSetAmount() {
        Item item = new Item("Amount item");
        item.setAmount(10);

        assertEquals(10, item.getAmount());
    }

    @Test
    void testSetImage() {
        Item item = new Item("Image item");
        String imgPath = "test.png";
        item.setImage("test.png");

        assertEquals(imgPath, item.getImg());
    }
}
