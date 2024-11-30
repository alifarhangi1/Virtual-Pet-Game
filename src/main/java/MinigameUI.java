
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class MinigameUI {

    GamePanel gamePanel;
    Font enemyCounterFont;
    BufferedImage enemiesLogo;
    Graphics2D g2D;
    public boolean gameFinished;
    public String currentDialogue = "";

    public MinigameUI(GamePanel gamePanel){
        this.gamePanel = gamePanel;

            enemyCounterFont = new Font("Papyrus",Font.PLAIN,40);
            try{
                enemiesLogo = ImageIO.read(getClass().getResourceAsStream("samuraiSymbol.png"));
            }catch (IOException e){
                e.printStackTrace();
            }
    }

    public void draw(Graphics2D g2D){

        this.g2D = g2D;
        // Level win screen
        if(gameFinished){
            g2D.setFont(enemyCounterFont);
            g2D.setColor(Color.white);

            String text;
            int textLength;
            int x;
            int y;

            String text2;
            int textLength2;
            int x2;
            int y2;

            text = "You Win!";
            textLength = (int)g2D.getFontMetrics().getStringBounds(text,g2D).getWidth();
            x = gamePanel.screenWidth/2 - textLength/2;
            y = gamePanel.screenHeight/2 - textLength/2;

            text2 = "Press 'esc' to return to menu";
            textLength2 = (int)g2D.getFontMetrics().getStringBounds(text2,g2D).getWidth();
            x2 = gamePanel.screenWidth/2 - textLength2/2;
            y2 = gamePanel.screenHeight/2 - textLength2/2;

            g2D.drawString(text,x,y);
            g2D.drawString(text2,x2,y2);

            if(gamePanel.keyHandler.escPressed){
                gamePanel.mainWindow.dispose();
            }
        }
        else if(gamePanel.dialogueState){
            drawDialogueState();
        }
        else{
            // Level specific graphics
            if(gamePanel.levelName.equals("Kung Fu Chaos")){
                g2D.setFont(enemyCounterFont);
                g2D.setColor(Color.white);
                g2D.drawImage(enemiesLogo,gamePanel.tileSize/2,gamePanel.tileSize/2,gamePanel.tileSize,gamePanel.tileSize, null);
                g2D.drawString("x " + gamePanel.player.enemiesLeft,74,65);
            }
        }


    }

    public void drawDialogueState(){
        // Dialogue window
        int x = gamePanel.tileSize*2;
        int y = gamePanel.tileSize/2;
        int width = gamePanel.screenWidth - (gamePanel.tileSize*4);
        int height = gamePanel.tileSize*4;
        drawWindow(x,y,width,height);

        // Dialogue
        g2D.setFont(new Font("Papyrus",Font.PLAIN,15));
        x += gamePanel.tileSize;
        y += gamePanel.tileSize;
        String[] lines = currentDialogue.split("\n");
        for (String line : lines) {
            g2D.drawString(line, x, y);
            y += g2D.getFontMetrics().getHeight(); // Move to the next line
        }

    }

    public void drawWindow(int x, int y, int width, int height){

        //Main panel
        Color color = new Color(0,0,0,220);
        g2D.setColor(color);
        g2D.fillRoundRect(x,y,width,height,35,35);
        //Border
        color = new Color(255,255,255);
        g2D.setColor(color);
        g2D.setStroke(new BasicStroke(5));
        g2D.drawRoundRect(x+5,y+5,width-10,height-10,25,25);
    }
}
