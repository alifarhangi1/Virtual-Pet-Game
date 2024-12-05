package screens;

import managers.GameManager;
import misc.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Arrays;

public class LoadSaveScreen extends GameScreen {
    private ImageIcon backgroundIcon;
    private JPanel mainPanel;
    private JPanel boxPanel;
    private JButton signUpButton;

    public LoadSaveScreen() {
        initializeComponents();
        initializeBackButtonDimensions();
    }

    @Override
    protected void initializeComponents()
    {
        try
        {
            backgroundIcon = new ImageIcon(getClass().getResource("/visuals/boxbackground.jpg"));
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResource("/visuals/ProtestRevolution-Regular.ttf").openStream());
            Font derivedFont = customFont.deriveFont(16f);
            mainPanel = new JPanel(new BorderLayout());
            boxPanel = new JPanel(new GridBagLayout()) {
                @Override
                protected void paintComponent(Graphics g) {
//                g.setColor(new Color(123, 78, 46));
//                g.fillRect(0, 0, getWidth(), getHeight());
                    super.paintComponent(g);
                    g.drawImage(backgroundIcon.getImage(), 0, 0, boxPanel.getWidth(), boxPanel.getHeight(), this);
                }
            };
            boxPanel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(219, 198, 77), 4),
                    BorderFactory.createEmptyBorder(5, 5, 5, 5)
            ));

            boxPanel.setOpaque(false);
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(15, 5, 5, 5); // Padding

            String[] labels = {"Username: ", "Password: "};
            JTextField textField = new JTextField(10);
            JPasswordField passwordField = new JPasswordField(10);

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
                if (labels[i].equals("Password: ")) {
                    boxPanel.add(passwordField, gbc);
                } else {
                    boxPanel.add(textField, gbc);
                }
            }

            JLabel userExistsLabel = new JLabel("* This user does not exist");
            userExistsLabel.setForeground(Color.RED);
            userExistsLabel.setFont(derivedFont);
            userExistsLabel.setVisible(false);

            JLabel wrongPasswordLabel = new JLabel("* Wrong password, try again");
            wrongPasswordLabel.setForeground(Color.RED);
            wrongPasswordLabel.setFont(derivedFont);
            wrongPasswordLabel.setVisible(false);

            gbc.gridx = 0;
            gbc.gridy = 2;
            gbc.gridwidth = 2;
            gbc.insets = new Insets(10, 5, 5, 5);
            gbc.fill = GridBagConstraints.NONE;
            gbc.anchor = GridBagConstraints.CENTER;

            signUpButton = new JButton("Sign Up");
            signUpButton.setBackground(Color.WHITE);
            signUpButton.setFont(derivedFont);
            signUpButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            signUpButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    Player player = databaseManager.findPlayer(textField.getText());
                    if (player != null) {
                        if (player.getPasswordString().equals(new String(passwordField.getPassword()))) {
                            userExistsLabel.setVisible(false);
                            wrongPasswordLabel.setVisible(false);
                            GameManager.getInstance(player);
                            GameManager gm = GameManager.getInstance();
                            gm.getPlayer().addLogin();
                            gm.getPlayer().addTotalPlaytime();
                            if (screenManager.hasScreen("main")) {
                                screenManager.deleteScreen("main");
                            }
                            screenManager.addScreen("main", new MainMenuScreen(gm));
                            audioManager.stopBackgroundMusic();
                            screenManager.showScreen("main");
                        } else {
                            wrongPasswordLabel.setVisible(true);
                            revalidate();
                            repaint();
                        }
                    } else {
                        wrongPasswordLabel.setVisible(false);
                        userExistsLabel.setVisible(true);
                        boxPanel.revalidate();
                        boxPanel.repaint();
                    }
                }
            });

            JPanel backButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            backButtonPanel.setOpaque(false);
            backButtonPanel.add(createBackButton("save"));
            backButtonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

            boxPanel.add(signUpButton, gbc);
            gbc.gridy = 3;
            boxPanel.add(userExistsLabel, gbc);
            boxPanel.add(wrongPasswordLabel, gbc);
            contentPanel.add(mainPanel);
            mainPanel.add(boxPanel, BorderLayout.CENTER);
            mainPanel.add(backButtonPanel, BorderLayout.SOUTH);
            mainPanel.setBorder(BorderFactory.createEmptyBorder(40, 0, 0, 0));
            mainPanel.setOpaque(false);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResize(int width, int height) {
        int[] backButtonDimensions = resizeBackButton();

        // Update back button
        JPanel backButtonPanel = (JPanel)mainPanel.getComponent(1);
        if (backButtonPanel.getComponentCount() > 0) {
            JLabel backButton = (JLabel)backButtonPanel.getComponent(0);
            ImageIcon defaultIcon = createScaledIcon("/visuals/woodButtonDefault.png",
                    backButtonDimensions[0], backButtonDimensions[1]);
            backButton.setIcon(defaultIcon);
            backButton.putClientProperty("defaultIcon", defaultIcon);
            setButtonSize(backButton, new Dimension(backButtonDimensions[0], backButtonDimensions[1]));
        }

        revalidate();
        repaint();
    }
}
