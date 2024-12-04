package misc;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

/**
 * The misc.MinigameUI class is responsible for rendering the user interface (UI)
 * for the game, including dialogue windows, level-specific graphics, and
 * victory screens.
 */
public class MinigameUI {

    /** The misc.GamePanel instance associated with this UI. */
    GamePanel gamePanel;

    /** The font used for displaying the enemy counter and other text. */
    Font enemyCounterFont;

    /** The logo image for the enemies counter. */
    BufferedImage enemiesLogo;

    /** Graphics2D object for rendering UI elements. */
    Graphics2D g2D;

    /** Flag to indicate if the game has been finished. */
    public boolean gameFinished;

    /** The current dialogue text to display. */
    public String currentDialogue = "";

    /**
     * Constructs a misc.MinigameUI instance and initializes resources such as fonts
     * and images.
     *
     * @param gamePanel the misc.GamePanel instance associated with this UI
     */
    public MinigameUI(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        enemyCounterFont = new Font("Papyrus", Font.PLAIN, 40);
        try {
            InputStream is = getClass().getResourceAsStream("/visuals/samuraiSymbol.png");
            if (is == null) {
                throw new IOException("Resource not found: /visuals/samuraiSymbol.png");
            }
            enemiesLogo = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Draws the UI elements based on the current game state.
     *
     * @param g2D the Graphics2D object used to render UI elements
     */
    public void draw(Graphics2D g2D) {
        this.g2D = g2D;

        // Victory screen
        if (gameFinished) {
            g2D.setFont(enemyCounterFont);
            g2D.setColor(Color.white);

            String text = "You Win!";
            int textLength = (int) g2D.getFontMetrics().getStringBounds(text, g2D).getWidth();
            int x = gamePanel.screenWidth / 2 - textLength / 2;
            int y = gamePanel.screenHeight / 2;

            String text2 = "Press 'esc' to return to menu";
            int textLength2 = (int) g2D.getFontMetrics().getStringBounds(text2, g2D).getWidth();
            int x2 = gamePanel.screenWidth / 2 - textLength2 / 2;
            int y2 = gamePanel.screenHeight / 2 + 50;

            g2D.drawString(text, x, y);
            g2D.drawString(text2, x2, y2);

            if (gamePanel.keyHandler.escPressed) {
                gamePanel.mainWindow.dispose();
            }
        }
        // Dialogue state
        else if (gamePanel.dialogueState) {
            drawDialogueState();
        }
        // Level-specific graphics
        else {
            if (gamePanel.levelName.equals("Kung Fu Chaos")) {
                g2D.setFont(enemyCounterFont);
                g2D.setColor(Color.white);
                g2D.drawImage(enemiesLogo, gamePanel.tileSize / 2, gamePanel.tileSize / 2, gamePanel.tileSize, gamePanel.tileSize, null);
                g2D.drawString("x " + gamePanel.player.enemiesLeft, 74, 65);
            }
        }
    }

    /**
     * Draws the dialogue window with the current dialogue text.
     */
    public void drawDialogueState() {
        // Dialogue window dimensions
        int x = gamePanel.tileSize * 2;
        int y = gamePanel.tileSize / 2;
        int width = gamePanel.screenWidth - (gamePanel.tileSize * 4);
        int height = gamePanel.tileSize * 4;

        drawWindow(x, y, width, height);

        // Render dialogue text
        g2D.setFont(new Font("Papyrus", Font.PLAIN, 15));
        x += gamePanel.tileSize;
        y += gamePanel.tileSize;
        String[] lines = currentDialogue.split("\n");
        for (String line : lines) {
            g2D.drawString(line, x, y);
            y += g2D.getFontMetrics().getHeight(); // Move to the next line
        }
    }

    /**
     * Draws a window with rounded corners for the UI.
     *
     * @param x      the x-coordinate of the window
     * @param y      the y-coordinate of the window
     * @param width  the width of the window
     * @param height the height of the window
     */
    public void drawWindow(int x, int y, int width, int height) {
        // Main panel
        Color color = new Color(0, 0, 0, 220); // Semi-transparent black
        g2D.setColor(color);
        g2D.fillRoundRect(x, y, width, height, 35, 35);

        // Border
        color = new Color(255, 255, 255); // White border
        g2D.setColor(color);
        g2D.setStroke(new BasicStroke(5));
        g2D.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);
    }
}

