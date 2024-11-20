package screens;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class NewSaveScreen extends GameScreen {
    private ImageIcon backgroundIcon;
    private JPanel boxPanel;

    public NewSaveScreen() {
        initializeComponents();
    }

    @Override
    protected void initializeComponents() {
        try {
            // Load the font file
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File("src/assets/visuals/ProtestRevolution-Regular.ttf"));
            // Derive the font at a specific size
            Font derivedFont = customFont.deriveFont(16f); // 16 is the font size
            boxPanel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                g.setColor(new Color(209, 156, 117));
                g.fillRect(0, 0, getWidth(), getHeight());
                super.paintComponent(g);
            }
        };
            boxPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.WHITE, 2),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));

            boxPanel.setOpaque(false);
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 5, 5, 5); // Padding

            String[] labels = {"Username: ", "Password: "};

            for (int i = 0; i < labels.length; i++) {
                // Label
                gbc.gridx = 0;
                gbc.gridy = i;
                gbc.anchor = GridBagConstraints.EAST;
                JLabel label = new JLabel(labels[i].toUpperCase());
                label.setFont(derivedFont);
                label.setForeground(Color.WHITE);
                boxPanel.add(label, gbc);

                // Text Field
                gbc.gridx = 1;
                gbc.anchor = GridBagConstraints.WEST;
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.weightx = 1.0;
                boxPanel.add(new JTextField(10), gbc);
            }
            contentPanel.add(boxPanel, BorderLayout.CENTER);

        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
    }
}
