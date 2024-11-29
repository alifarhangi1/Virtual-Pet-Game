

public class AssetSetter {

    GamePanel gamePanel;
    String selectedLevel;

    public AssetSetter(GamePanel gamePanel, String selectedLevel){

        this.gamePanel = gamePanel;
        this.selectedLevel = selectedLevel;

    }

    public void setObject(){
        if(selectedLevel.equals("Maze Madness")){
            gamePanel.obj[0] = new OBJ_Minotaur();
            gamePanel.obj[0].worldX = (10 * gamePanel.tileSize) ;  // col number x tile size
            gamePanel.obj[0].worldY = 10 * gamePanel.tileSize;   // row number x tile size

            gamePanel.obj[1] = new OBJ_EvolutionFruit();
            gamePanel.obj[1].worldX = 10 * gamePanel.tileSize;
            gamePanel.obj[1].worldY = 12 * gamePanel.tileSize;
        }
        else if(selectedLevel.equals("Kung Fu Chaos")){
            System.out.println("Kung Fu Chaos");
        }
        else if(selectedLevel.equals("Dragon Duel")){
            System.out.println("Dragon Duel");
        }
    }

    public void setNPC(){
        if(selectedLevel.equals("Kung Fu Chaos")){
            gamePanel.npc[0] = new NPC_Samurai(gamePanel);
            gamePanel.npc[0].worldX = gamePanel.tileSize*20;
            gamePanel.npc[0].worldY = gamePanel.tileSize*20;

            gamePanel.npc[1] = new NPC_Samurai(gamePanel);
            gamePanel.npc[1].worldX = gamePanel.tileSize*15;
            gamePanel.npc[1].worldY = gamePanel.tileSize*30;

            gamePanel.npc[2] = new NPC_Samurai(gamePanel);
            gamePanel.npc[2].worldX = gamePanel.tileSize*40;
            gamePanel.npc[2].worldY = gamePanel.tileSize*8;

            gamePanel.npc[3] = new NPC_Samurai(gamePanel);
            gamePanel.npc[3].worldX = gamePanel.tileSize*15;
            gamePanel.npc[3].worldY = gamePanel.tileSize*42;

            gamePanel.npc[4] = new NPC_Samurai(gamePanel);
            gamePanel.npc[4].worldX = gamePanel.tileSize*40;
            gamePanel.npc[4].worldY = gamePanel.tileSize*35;

            gamePanel.npc[5] = new NPC_Samurai(gamePanel);
            gamePanel.npc[5].worldX = gamePanel.tileSize*20;
            gamePanel.npc[5].worldY = gamePanel.tileSize*20;

            gamePanel.npc[6] = new NPC_Samurai(gamePanel);
            gamePanel.npc[6].worldX = gamePanel.tileSize*15;
            gamePanel.npc[6].worldY = gamePanel.tileSize*30;

            gamePanel.npc[7] = new NPC_Samurai(gamePanel);
            gamePanel.npc[7].worldX = gamePanel.tileSize*40;
            gamePanel.npc[7].worldY = gamePanel.tileSize*8;

            gamePanel.npc[8] = new NPC_Samurai(gamePanel);
            gamePanel.npc[8].worldX = gamePanel.tileSize*15;
            gamePanel.npc[8].worldY = gamePanel.tileSize*42;

            gamePanel.npc[9] = new NPC_Samurai(gamePanel);
            gamePanel.npc[9].worldX = gamePanel.tileSize*28;
            gamePanel.npc[9].worldY = gamePanel.tileSize*35;
        }
    }

    public void setMusic(){
        if(selectedLevel.equals("Kung Fu Chaos")){
            try{
                gamePanel.musicPlayer.playMusic("kungfuchaos.wav");

            }catch (Exception e){
                System.out.println("Classpath: " + System.getProperty("java.class.path"));
                System.out.println("Resource: " + getClass().getClassLoader().getResource("kungfuchaos.wav"));
            }



        }
    }
}
