import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MainMenu implements MouseListener {
    JFrame frame;
    JPanel mainPanel;
    JLabel imageLabel;
    ImageIcon icon1, icon2, icon3;
    JPanel levelSelectContainer;
    JLabel arrowRLabel, arrowLLabel;
    ImageIcon arrowR, arrowL;
    JLabel levelName;
    String[] levelNames;
    ImageIcon[] levelIcons;
    int currentIndex;

    MainMenu(){
        // Initialize JFrame
        frame = new JFrame();
        frame.setSize(1000,1000);

        // Initialize Main Panel
        mainPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 500,15));
        mainPanel.setBackground(Color.BLACK);

        // Create Level Icon
        createLevelIcon();

        // Create Level Select
        createLevelSelect();

        // Initialize levels and icons
        levelNames = new String[]{"Maze Madness", "Kung Fu Chaos", "Dragon Duel"};
        levelIcons = new ImageIcon[]{icon2, icon1, icon3}; // Ensure icons are loaded before this
        currentIndex = 0; // Start with the first level

        // Add components to the panel and attach the panel to the frame
        mainPanel.add(imageLabel);
        mainPanel.add(levelSelectContainer,FlowLayout.CENTER);
        frame.add(mainPanel);

//        frame.pack();
        frame.setVisible(true);
    }

    private void createLevelSelect(){

        // Initialize levelSelectContainer
        levelSelectContainer = new JPanel(new FlowLayout(FlowLayout.CENTER,100,10));
        levelSelectContainer.setBackground(Color.black);

        // Initialize left and right arrow buttons
        arrowL = new ImageIcon("src/arrowLeft.png");
        arrowR = new ImageIcon("src/arrowRight.png");
        arrowLLabel = new JLabel(arrowL);
        arrowRLabel = new JLabel(arrowR);

        // Add arrows and level name to container
        levelSelectContainer.add(arrowLLabel);
        // Initialize to first level name
        setLevelName("Maze Madness");
        levelSelectContainer.add(levelName);
        levelSelectContainer.add(arrowRLabel);

        arrowLLabel.addMouseListener(this);
        arrowRLabel.addMouseListener(this);

        levelSelectContainer.setOpaque(true);
    }

    private void createLevelIcon(){
        // Initialize Level Icons
        icon1 = resizeImage(new ImageIcon("src/KungFuChaos.png"));
        icon2 = resizeImage(new ImageIcon("src/maze.png"));
        icon3 = resizeImage(new ImageIcon("src/dragon.png"));

        // Initialize Image Label
        imageLabel = new JLabel();
        imageLabel.setPreferredSize(new Dimension(icon1.getIconWidth(),icon1.getIconHeight()));

        imageLabel.setIcon(icon2);
    }



    private void setLevelName(String title){
        if (levelName != null) {
            levelName.setText(title);
        } else {
            levelName = new JLabel();
            levelName.setBackground(Color.black);
            levelName.setForeground(Color.white);
            levelName.setFont(new Font("Papyrus", Font.BOLD, 54));
            levelName.setText(title);
            levelName.addMouseListener(this);
            levelName.setOpaque(true);

            // Set a fixed size for the level name label
            levelName.setPreferredSize(new Dimension(400, 80)); // Adjust width/height as needed
            levelName.setHorizontalAlignment(SwingConstants.CENTER); // Center-align text
        }
    }

    private ImageIcon resizeImage(ImageIcon icon){
        Image img = icon.getImage();
        Image reSizedImg = img.getScaledInstance(500, 500, Image.SCALE_SMOOTH);
        icon = new ImageIcon(reSizedImg);

        return icon;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        Object source = e.getSource();

        // Set colour for button when level is selected
        if (source == levelName) {
            // Set a bolder color for the pressed state
            levelName.setBackground(new Color(180, 180, 180)); // Medium gray for noticeable change
            levelName.setForeground(Color.BLACK); // Keep black text for contrast

            // Add a darker, more defined border
            levelName.setBorder(BorderFactory.createLineBorder(new Color(100, 100, 100), 3)); // Dark gray border with 3px width

            levelName.repaint(); // Ensure changes are reflected

            // Place the object or call the method to initialize the class the level is going to
            // Example: new MazeLevel();
        }

        // Check which arrow was clicked and update the index with circular array
        if (source == arrowLLabel) {
            // Move to the previous index (wrap around if needed)
            currentIndex = (currentIndex - 1 + levelNames.length) % levelNames.length;
        } else if (source == arrowRLabel) {
            // Move to the next index (wrap around if needed)
            currentIndex = (currentIndex + 1) % levelNames.length;
        }

        // Update the image and level name
        imageLabel.setIcon(levelIcons[currentIndex]);
        setLevelName(levelNames[currentIndex]);

        // Refresh the UI
        mainPanel.revalidate();
        mainPanel.repaint();
    }


    @Override
    public void mouseReleased(MouseEvent e) {
        Object source = e.getSource();

        if (source == levelName) {
            // Revert to hover state
            levelName.setBackground(new Color(240, 240, 240)); // Light gray, hover color
            levelName.setForeground(Color.BLACK); // Black text
            levelName.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 2)); // Hover border

            levelName.repaint(); // Ensure changes are reflected
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        Object source = e.getSource();

        if (source == levelName) {
            // Set a near-white background color
            levelName.setBackground(new Color(240, 240, 240)); // Light gray, almost white
            levelName.setForeground(Color.BLACK); // Black text for contrast

            // Add a border
            levelName.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 2)); // Light gray border with 2px width

            levelName.repaint(); // Ensure changes are reflected
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        Object source = e.getSource();

        if (source == levelName) {
            // Reset to the original black background and white text
            levelName.setBackground(Color.BLACK);
            levelName.setForeground(Color.WHITE);

            // Remove the border
            levelName.setBorder(null);

            levelName.repaint(); // Ensure changes are reflected
        }
    }
}
