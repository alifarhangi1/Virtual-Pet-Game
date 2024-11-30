

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {

    GamePanel gamePanel;
    public Tile[] tile;

    public int mapTileNum[][];

    public TileManager(GamePanel gamePanel, String levelName){
        this.gamePanel = gamePanel;

        tile = new Tile[10];
        mapTileNum = new int[gamePanel.maxWorldCol][gamePanel.maxWorldRow];

        getTileImage();

        // Determine which map to load based on the level name
        switch (levelName) {
            case "Maze Madness":
                loadMap("map_maze.txt");
                break;

            case "Kung Fu Chaos":
                loadMap("map_kungfu.txt");
                break;

        }


    }

    public void getTileImage(){

        try{

            tile[0] = new Tile();
            tile[0].image = ImageIO.read(getClass().getResourceAsStream("tree.png"));
            tile[0].collision = true;

            tile[1] = new Tile();
            tile[1].image = ImageIO.read(getClass().getResourceAsStream("wall.png"));
            tile[1].collision = true;

            tile[2] = new Tile();
            tile[2].image = ImageIO.read(getClass().getResourceAsStream("water01.png"));
            tile[2].collision = true;

            tile[3] = new Tile();
            tile[3].image = ImageIO.read(getClass().getResourceAsStream("road00.png"));

            tile[4] = new Tile();
            tile[4].image = ImageIO.read(getClass().getResourceAsStream("grass01.png"));


            tile[5] = new Tile();
            tile[5].image = ImageIO.read(getClass().getResourceAsStream("path.jpg"));

            tile[6] = new Tile();
            tile[6].image = ImageIO.read(getClass().getResourceAsStream("hut.png"));
            tile[6].collision = true;




        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void loadMap(String mapName){

        try{
            InputStream inputStream = getClass().getResourceAsStream(mapName);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            int col = 0;
            int row = 0;

            while(col < gamePanel.maxWorldCol && row < gamePanel.maxWorldRow){

                String line = bufferedReader.readLine(); // read txt file line

                while(col < gamePanel.maxWorldCol){

                    String numbers[] = line.split(" ");

                    int num = Integer.parseInt(numbers[col]);

                    mapTileNum[col][row] = num; // Store extracted number
                    col++;




                    // Continue until everything in numbers[] is stored in mapTileNum[][]
                }
                if(col == gamePanel.maxWorldCol){
                    col = 0;
                    row++;

                }
            }
            bufferedReader.close();


        }catch (Exception e){

        }
    }
    public void draw(Graphics2D g2D){

       int worldCol = 0;
       int worldRow = 0;


       while(worldCol < gamePanel.maxWorldCol && worldRow < gamePanel.maxWorldRow){

           int tileNum = mapTileNum[worldCol][worldRow];

           // Implementing Camera

           int worldX = worldCol * gamePanel.tileSize;
           int worldY = worldRow * gamePanel.tileSize;
           int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
           int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;


           //EFFICIENT GAME RENDERING (Place draw in this if statement)
           if(worldX + gamePanel.tileSize> gamePanel.player.worldX - gamePanel.player.screenX &&
                    worldX - gamePanel.tileSize < gamePanel.player.worldX + gamePanel.player.screenX &&
                    worldY + gamePanel.tileSize > gamePanel.player.worldY - gamePanel.player.screenY &&
                    worldY - gamePanel.tileSize < gamePanel.player.worldY + gamePanel.player.screenY){

                 g2D.drawImage(tile[tileNum].image,screenX,screenY,gamePanel.tileSize, gamePanel.tileSize, null);
        }




           worldCol++;

           if(worldCol == gamePanel.maxWorldCol){
               worldCol = 0;
               worldRow++;
           }

       }

    }
}
