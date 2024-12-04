package managers;

import misc.GamePanel;
import misc.Tile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * The managers.TileManager class is responsible for managing and rendering tiles in the game world.
 * It loads tile images, handles map data, and ensures efficient rendering based on the player's position.
 */
public class TileManager {

    /** Reference to the game panel. */
    GamePanel gamePanel;

    /** Array of tiles representing the different tile types in the game. */
    public Tile[] tile;

    /** 2D array representing the tile numbers for the game map. */
    public int[][] mapTileNum;

    /**
     * Constructs a managers.TileManager instance and initializes the tile data and map.
     *
     * @param gamePanel the misc.GamePanel instance associated with the managers.TileManager
     * @param levelName the name of the level to load
     */
    public TileManager(GamePanel gamePanel, String levelName) {
        this.gamePanel = gamePanel;

        tile = new Tile[10];
        mapTileNum = new int[gamePanel.maxWorldCol][gamePanel.maxWorldRow];

        getTileImage();

        // Load the appropriate map based on the level name
        switch (levelName) {
            case "Maze Madness" -> loadMap("map_maze.txt");
            case "Kung Fu Chaos" -> loadMap("map_kungfu.txt");
        }
    }

    /**
     * Loads the tile images and sets their collision properties.
     */
    public void getTileImage() {
        try {
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(getClass().getResourceAsStream("/visuals/tree.png"));
            tile[0].collision = true;

            tile[1] = new Tile();
            tile[1].image = ImageIO.read(getClass().getResourceAsStream("/visuals/wall.png"));
            tile[1].collision = true;

            tile[2] = new Tile();
            tile[2].image = ImageIO.read(getClass().getResourceAsStream("/visuals/water01.png"));
            tile[2].collision = true;

            tile[3] = new Tile();
            tile[3].image = ImageIO.read(getClass().getResourceAsStream("/visuals/road00.png"));

            tile[4] = new Tile();
            tile[4].image = ImageIO.read(getClass().getResourceAsStream("/visuals/grass01.png"));

            tile[5] = new Tile();
            tile[5].image = ImageIO.read(getClass().getResourceAsStream("/visuals/path.jpg"));

            tile[6] = new Tile();
            tile[6].image = ImageIO.read(getClass().getResourceAsStream("/visuals/hut.png"));
            tile[6].collision = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the map data from a text file and populates the mapTileNum array.
     *
     * @param mapName the name of the map file to load
     */
    public void loadMap(String mapName) {
        try {
            InputStream inputStream = getClass().getResourceAsStream("/" + mapName);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            int col = 0;
            int row = 0;

            while (col < gamePanel.maxWorldCol && row < gamePanel.maxWorldRow) {
                String line = bufferedReader.readLine(); // Read a line from the map file

                while (col < gamePanel.maxWorldCol) {
                    String[] numbers = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);
                    mapTileNum[col][row] = num; // Store the tile number
                    col++;
                }

                if (col == gamePanel.maxWorldCol) {
                    col = 0;
                    row++;
                }
            }
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Draws the tiles on the screen, rendering only those within the player's viewport for efficiency.
     *
     * @param g2D the Graphics2D object used for rendering
     */
    public void draw(Graphics2D g2D) {
        int worldCol = 0;
        int worldRow = 0;

        while (worldCol < gamePanel.maxWorldCol && worldRow < gamePanel.maxWorldRow) {
            int tileNum = mapTileNum[worldCol][worldRow];

            // Calculate the world and screen positions of the tile
            int worldX = worldCol * gamePanel.tileSize;
            int worldY = worldRow * gamePanel.tileSize;
            int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
            int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;

            // Render the tile if it is within the viewport
            if (worldX + gamePanel.tileSize > gamePanel.player.worldX - gamePanel.player.screenX &&
                    worldX - gamePanel.tileSize < gamePanel.player.worldX + gamePanel.player.screenX &&
                    worldY + gamePanel.tileSize > gamePanel.player.worldY - gamePanel.player.screenY &&
                    worldY - gamePanel.tileSize < gamePanel.player.worldY + gamePanel.player.screenY) {

                g2D.drawImage(tile[tileNum].image, screenX, screenY, gamePanel.tileSize, gamePanel.tileSize, null);
            }

            worldCol++;

            if (worldCol == gamePanel.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }
        }
    }
}

