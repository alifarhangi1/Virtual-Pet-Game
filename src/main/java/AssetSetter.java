/**
 * The AssetSetter class is responsible for setting game assets such as objects, NPCs, and music
 * based on the selected game level.
 */
public class AssetSetter {

    /** The game panel associated with this asset setter. */
    GamePanel gamePanel;

    /** The name of the selected level. */
    String selectedLevel;

    /**
     * Constructs an AssetSetter instance with the specified game panel and selected level.
     *
     * @param gamePanel     the game panel to associate with this asset setter
     * @param selectedLevel the name of the selected game level
     */
    public AssetSetter(GamePanel gamePanel, String selectedLevel) {
        this.gamePanel = gamePanel;
        this.selectedLevel = selectedLevel;
    }

    /**
     * Sets objects in the game world based on the selected level.
     * Initializes specific objects with their world coordinates.
     */
    public void setObject() {
        if (selectedLevel.equals("Maze Madness")) {
            gamePanel.obj[0] = new OBJ_EvolutionFruit();
            gamePanel.obj[0].worldX = 19 * gamePanel.tileSize;
            gamePanel.obj[0].worldY = 10 * gamePanel.tileSize;
        }
    }

    /**
     * Sets non-player characters (NPCs) in the game world based on the selected level.
     * Positions NPCs with predefined world coordinates and attributes.
     */
    public void setNPC() {
        // Set NPCs for Kung Fu Chaos level
        if (selectedLevel.equals("Kung Fu Chaos")) {
            gamePanel.npc[0] = new NPC_Samurai(gamePanel);
            gamePanel.npc[0].worldX = gamePanel.tileSize * 20;
            gamePanel.npc[0].worldY = gamePanel.tileSize * 10;
            gamePanel.npc[0].attackable = true;

            gamePanel.npc[1] = new NPC_Samurai(gamePanel);
            gamePanel.npc[1].worldX = gamePanel.tileSize*15;
            gamePanel.npc[1].worldY = gamePanel.tileSize*15;
            gamePanel.npc[1].attackable = true;

            gamePanel.npc[2] = new NPC_Samurai(gamePanel);
            gamePanel.npc[2].worldX = gamePanel.tileSize*40;
            gamePanel.npc[2].worldY = gamePanel.tileSize*20;
            gamePanel.npc[2].attackable = true;

            gamePanel.npc[3] = new NPC_Samurai(gamePanel);
            gamePanel.npc[3].worldX = gamePanel.tileSize*15;
            gamePanel.npc[3].worldY = gamePanel.tileSize*25;
            gamePanel.npc[3].attackable = true;

            gamePanel.npc[4] = new NPC_Samurai(gamePanel);
            gamePanel.npc[4].worldX = gamePanel.tileSize*40;
            gamePanel.npc[4].worldY = gamePanel.tileSize*30;
            gamePanel.npc[4].attackable = true;

            gamePanel.npc[5] = new NPC_Samurai(gamePanel);
            gamePanel.npc[5].worldX = gamePanel.tileSize*20;
            gamePanel.npc[5].worldY = gamePanel.tileSize*20;
            gamePanel.npc[5].attackable = true;

            gamePanel.npc[6] = new NPC_Samurai(gamePanel);
            gamePanel.npc[6].worldX = gamePanel.tileSize*15;
            gamePanel.npc[6].worldY = gamePanel.tileSize*30;
            gamePanel.npc[6].attackable = true;

            gamePanel.npc[7] = new NPC_Samurai(gamePanel);
            gamePanel.npc[7].worldX = gamePanel.tileSize*40;
            gamePanel.npc[7].worldY = gamePanel.tileSize*25;
            gamePanel.npc[7].attackable = true;

            gamePanel.npc[8] = new NPC_Samurai(gamePanel);
            gamePanel.npc[8].worldX = gamePanel.tileSize*15;
            gamePanel.npc[8].worldY = gamePanel.tileSize*10;
            gamePanel.npc[8].attackable = true;

            gamePanel.npc[9] = new NPC_Samurai(gamePanel);
            gamePanel.npc[9].worldX = gamePanel.tileSize*28;
            gamePanel.npc[9].worldY = gamePanel.tileSize*10;
            gamePanel.npc[9].attackable = true;

            gamePanel.npc[10] = new NPC_Villager(gamePanel);
            gamePanel.npc[10].worldX = gamePanel.tileSize*25;
            gamePanel.npc[10].worldY = gamePanel.tileSize*41;
        }
        // Set NPCs for Maze Madness level
        else if (selectedLevel.equals("Maze Madness")) {
            gamePanel.npc[0] = new NPC_Minotaur(gamePanel);
            gamePanel.npc[0].worldX = 25 * gamePanel.tileSize;  // col number x tile size
            gamePanel.npc[0].worldY = 38 * gamePanel.tileSize;  // row number x tile size
        }
    }

    /**
     * Sets background music for the game based on the selected level.
     * Handles exceptions if music resources cannot be loaded.
     */
    public void setMusic() {
        if (selectedLevel.equals("Kung Fu Chaos")) {
            try {
                gamePanel.musicPlayer.playMusic("kungfuchaos.wav");
            } catch (Exception e) {
                System.out.println("Classpath: " + System.getProperty("java.class.path"));
                System.out.println("Resource: " + getClass().getClassLoader().getResource("kungfuchaos.wav"));
            }
        } else if (selectedLevel.equals("Maze Madness")) {
            try {
                gamePanel.musicPlayer.playMusic("mazemadness.wav");
            } catch (Exception e) {
                System.out.println("Classpath: " + System.getProperty("java.class.path"));
                System.out.println("Resource: " + getClass().getClassLoader().getResource("mazemadness.wav"));
            }
        } else if (selectedLevel.equals("Dragon Duel")) {
            try {
                gamePanel.musicPlayer.playMusic("dragonduel.wav");
            } catch (Exception e) {
                System.out.println("Classpath: " + System.getProperty("java.class.path"));
                System.out.println("Resource: " + getClass().getClassLoader().getResource("dragonduel.wav"));
            }
        }
    }
}

//public class AssetSetter {
//
//    GamePanel gamePanel;
//    String selectedLevel;
//
//    public AssetSetter(GamePanel gamePanel, String selectedLevel){
//
//        this.gamePanel = gamePanel;
//        this.selectedLevel = selectedLevel;
//
//    }
//
//    public void setObject(){
//        if(selectedLevel.equals("Maze Madness")){
//            gamePanel.obj[0] = new OBJ_EvolutionFruit();
//            gamePanel.obj[0].worldX = 19 * gamePanel.tileSize;
//            gamePanel.obj[0].worldY = 10 * gamePanel.tileSize;
//        }
//        else if(selectedLevel.equals("Kung Fu Chaos")){
//            System.out.println("Kung Fu Chaos");
//        }
//        else if(selectedLevel.equals("Dragon Duel")){
//            System.out.println("Dragon Duel");
//        }
//    }
//
//    public void setNPC(){
//
//        // Set NPCs for Kung Fu Chaos level
//        if(selectedLevel.equals("Kung Fu Chaos")){
//            gamePanel.npc[0] = new NPC_Samurai(gamePanel);
//            gamePanel.npc[0].worldX = gamePanel.tileSize*20;
//            gamePanel.npc[0].worldY = gamePanel.tileSize*10;
//            gamePanel.npc[0].attackable = true;
//
//            gamePanel.npc[1] = new NPC_Samurai(gamePanel);
//            gamePanel.npc[1].worldX = gamePanel.tileSize*15;
//            gamePanel.npc[1].worldY = gamePanel.tileSize*15;
//            gamePanel.npc[1].attackable = true;
//
//            gamePanel.npc[2] = new NPC_Samurai(gamePanel);
//            gamePanel.npc[2].worldX = gamePanel.tileSize*40;
//            gamePanel.npc[2].worldY = gamePanel.tileSize*20;
//            gamePanel.npc[2].attackable = true;
//
//            gamePanel.npc[3] = new NPC_Samurai(gamePanel);
//            gamePanel.npc[3].worldX = gamePanel.tileSize*15;
//            gamePanel.npc[3].worldY = gamePanel.tileSize*25;
//            gamePanel.npc[3].attackable = true;
//
//            gamePanel.npc[4] = new NPC_Samurai(gamePanel);
//            gamePanel.npc[4].worldX = gamePanel.tileSize*40;
//            gamePanel.npc[4].worldY = gamePanel.tileSize*30;
//            gamePanel.npc[4].attackable = true;
//
//            gamePanel.npc[5] = new NPC_Samurai(gamePanel);
//            gamePanel.npc[5].worldX = gamePanel.tileSize*20;
//            gamePanel.npc[5].worldY = gamePanel.tileSize*20;
//            gamePanel.npc[5].attackable = true;
//
//            gamePanel.npc[6] = new NPC_Samurai(gamePanel);
//            gamePanel.npc[6].worldX = gamePanel.tileSize*15;
//            gamePanel.npc[6].worldY = gamePanel.tileSize*30;
//            gamePanel.npc[6].attackable = true;
//
//            gamePanel.npc[7] = new NPC_Samurai(gamePanel);
//            gamePanel.npc[7].worldX = gamePanel.tileSize*40;
//            gamePanel.npc[7].worldY = gamePanel.tileSize*25;
//            gamePanel.npc[7].attackable = true;
//
//            gamePanel.npc[8] = new NPC_Samurai(gamePanel);
//            gamePanel.npc[8].worldX = gamePanel.tileSize*15;
//            gamePanel.npc[8].worldY = gamePanel.tileSize*10;
//            gamePanel.npc[8].attackable = true;
//
//            gamePanel.npc[9] = new NPC_Samurai(gamePanel);
//            gamePanel.npc[9].worldX = gamePanel.tileSize*28;
//            gamePanel.npc[9].worldY = gamePanel.tileSize*10;
//            gamePanel.npc[9].attackable = true;
//
//            gamePanel.npc[10] = new NPC_Villager(gamePanel);
//            gamePanel.npc[10].worldX = gamePanel.tileSize*25;
//            gamePanel.npc[10].worldY = gamePanel.tileSize*41;
//        }
//        // Set Npcs for Maze madness level
//        else if(selectedLevel.equals("Maze Madness")){
//            gamePanel.npc[0] = new NPC_Minotaur(gamePanel);
//            gamePanel.npc[0].worldX = 25 * gamePanel.tileSize ;  // col number x tile size
//            gamePanel.npc[0].worldY = 38 * gamePanel.tileSize;   // row number x tile size
//        }
//    }
//
//    public void setMusic(){
//        if(selectedLevel.equals("Kung Fu Chaos")){
//            try{
//                gamePanel.musicPlayer.playMusic("kungfuchaos.wav");
//
//            }catch (Exception e){
//                System.out.println("Classpath: " + System.getProperty("java.class.path"));
//                System.out.println("Resource: " + getClass().getClassLoader().getResource("kungfuchaos.wav"));
//            }
//        }
//        else if(selectedLevel.equals("Maze Madness")){
//            try{
//                gamePanel.musicPlayer.playMusic("mazemadness.wav");
//
//            }catch (Exception e){
//                System.out.println("Classpath: " + System.getProperty("java.class.path"));
//                System.out.println("Resource: " + getClass().getClassLoader().getResource("mazemadness.wav"));
//            }
//        }
//        else if(selectedLevel.equals("Dragon Duel")){
//            try{
//                gamePanel.musicPlayer.playMusic("dragonduel.wav");
//
//            }catch (Exception e){
//                System.out.println("Classpath: " + System.getProperty("java.class.path"));
//                System.out.println("Resource: " + getClass().getClassLoader().getResource("dragonduel.wav"));
//            }
//        }
//    }
//}
