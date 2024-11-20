package screens;

import javax.swing.*;
import java.awt.*;

public class NewSaveScreen extends GameScreen {
    private ImageIcon backgroundIcon;
    private JPanel boxPanel;

    public NewSaveScreen() {
    }

    @Override
    protected void initializeComponents() {
        boxPanel = new JPanel(new SpringLayout());
        String[] labels = {"Username: ", "Password: "};
        for (int i = 0; i < labels.length; i++) {
            JLabel label = new JLabel(labels[i], JLabel.TRAILING);
            boxPanel.add(label);
            JTextField textField = new JTextField(10);
            label.setLabelFor(textField);
            boxPanel.add(textField);
        }
        SpringUtilities.makeCompactGrid(boxPanel, labels.length, 2, 6, 6, 6, 6);

    }
}
