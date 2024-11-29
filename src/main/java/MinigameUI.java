
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class MinigameUI {

    GamePanel gamePanel;
    Font enemyCounterFont;
    BufferedImage enemiesLogo;
    public boolean gameFinished;
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
        else{
            if(gamePanel.levelName.equals("Kung Fu Chaos")){
                g2D.setFont(enemyCounterFont);
                g2D.setColor(Color.white);
                g2D.drawImage(enemiesLogo,gamePanel.tileSize/2,gamePanel.tileSize/2,gamePanel.tileSize,gamePanel.tileSize, null);
                g2D.drawString("x " + gamePanel.player.enemiesLeft,74,65);
            }
        }


    }
}
