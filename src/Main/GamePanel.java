package Main;

import Entity.Entity;
import Entity.Player;
import Tiles.TileManager;
import TratareExceptii.CustomException;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class GamePanel extends JPanel implements Runnable {

    //screen settings
    final int originalTileSize = 16; //16x16 tile
    int scale = 3;

    public final int tileSize = originalTileSize * scale; //48x48 tile
    public final int maxScreenCol = 20;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol;  //960 pixels
    public final int screenHeight = tileSize * maxScreenRow; //578 pixels

    //world settings
    public final int maxWorldCol = 60;
    public final int maxWorldRow = 56;
    public final int maxMap = 10;
    public int currentMap = 0;

    //FOR full screen
    int screenWidth2 = screenWidth;
    int screenHeight2 = screenHeight;
    BufferedImage tempScreen;
    Graphics2D g2;
    public boolean fulScreenOn = false;

    //FPS
    int FPS = 60;

    //System
    TileManager tileM = new TileManager(this);
    public KeyHandler keyH = new KeyHandler(this);
    Sound music = new Sound();
    Sound se = new Sound();
    public EventHandler eHandler = new EventHandler(this);
    Thread gameThread;


    //entity and object

    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public UI ui = new UI(this);

    //Singleton
    public Player player = Player.getInstance(this, keyH);

    public Entity obj[][] = new Entity[maxMap][10];
    public Entity npc[][] = new Entity[maxMap][10];
    public Entity monster[][] = new Entity[maxMap][20];
    ArrayList<Entity> entityList = new ArrayList<>();

    //GAMESTATE
    public int gameState;
    public final int titleScreen = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;
    public final int nextDialogueState = 4;
    public final int optionState = 5;
    public final int gameOverState = 6;


    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void setupGame() {

        aSetter.setObject();
        aSetter.setNPC();
        aSetter.setMonster();
        playMusic(0);
        music.fc.setValue(-20.0f);
        gameState = titleScreen;

        tempScreen = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
        g2 = (Graphics2D)tempScreen.getGraphics();



    }
    public void setFullScreen() {
        //get local screen device
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        gd.setFullScreenWindow(Main.window);

        //get full screen width and height
        screenWidth2 = Main.window.getWidth();;
        screenHeight2 = Main.window.getHeight();
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        double drawInterval = 1000000000/FPS; // 1 miliard nanoseconds/60  0.01666 seconds
        double nextDrawTime = System.nanoTime() + drawInterval;
        long lastTime = System.nanoTime();
        long currentTime;
        int timer = 0;
        int drawCount = 0;

        while(gameThread != null) {

            currentTime = System.nanoTime();

            timer += (currentTime - lastTime);
            lastTime = currentTime;

            //UPDATE: update info such as character position
            update();

            //DRAW: draw the screen with the updated information
            drawToTempScreen();
            drawToScreen();

            drawCount++;

            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime/1000000;

                if(remainingTime < 0) {
                    remainingTime = 0;
                }
                Thread.sleep((long) remainingTime);

                nextDrawTime += drawInterval;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if(timer >= 1000000000) {
                System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }
    public void update() {

        if(gameState == playState) {
            player.update();
            //npc

            for(int i = 0; i < npc[1].length; i++) {
                if(npc[currentMap][i] != null) {
                    npc[currentMap][i].update();
                }
            }
            for(int i = 0; i < monster.length; i++) {
                if(monster[currentMap][i] != null) {
                    monster[currentMap][i].update();
                }
            }
        }
        if(gameState == pauseState) {

        }
        if(gameState == dialogueState) {
            player.update();
        }
        if(gameState == titleScreen) {
            if(keyH.fulscreen == true) setFullScreen();
        }

    }
    public void drawToTempScreen() {

        //DEBUG
        long drawStart = 0;
        if (keyH.checkDrawTime == true) {
            drawStart = System.nanoTime();
        }

        //TitleScreen
        if (gameState == titleScreen) {
            ui.draw(g2);
        }

        //Others
        else {
            //tile
            tileM.draw(g2);

            //add entity to list
            entityList.add(player);

            for(int i = 0; i < npc[1].length; i++) {
                if(npc[currentMap][i] != null) {
                    entityList.add(npc[currentMap][i]);
                }
            }

            for(int i = 0; i < obj[1].length; i++) {
                if(obj[currentMap][i] != null) {
                    entityList.add(obj[currentMap][i]);
                }
            }

            for(int i = 0; i < monster[1].length; i++) {
                if(monster[currentMap][i] != null) {
                    entityList.add(monster[currentMap][i]);
                }
            }

            //sort
            Collections.sort(entityList, new Comparator<Entity>() {

                @Override
                public int compare(Entity e1, Entity e2) {

                    int result = Integer.compare(e1.worldY, e2.worldY);
                    return result;
                }
            });

            //draw entity
            for(int i = 0; i < entityList.size(); i++) {
                entityList.get(i).draw(g2);
            }
            //EMPTY entity list
            for(int i = 0; i < entityList.size(); i++) {
                entityList.remove(i);
            }

            //UI
            ui.draw(g2);

            //DEBUG
            if (keyH.checkDrawTime == true) {
                long drawEnd = System.nanoTime();
                long passed = drawEnd - drawStart;

                g2.setFont(new Font("Arial", Font.PLAIN, 20));
                g2.setColor(Color.white);
                g2.drawString("Draw Time: " + passed, 10, 400);
                System.out.println("Draw Time: " + passed);
                int x = 10;
                int y = 400;
                int lineHeight = 20;

                g2.drawString("WorldX" + player.worldX, x, y); y += lineHeight;
                g2.drawString("WorldY" + player.worldY, x, y); y += lineHeight;
                g2.drawString("Coloana" + (player.worldX + player.solidArea.x)/tileSize, x, y); y += lineHeight;
                g2.drawString("Rand" + (player.worldY + player.solidArea.y)/tileSize, x, y); y += lineHeight;
            }
        }
    }
    public void drawToScreen() {
        Graphics g = getGraphics();
        g.drawImage(tempScreen, 0, 0, screenWidth2, screenHeight2, null);
        g.dispose();
    }

    public void playMusic(int i) {

        try {
            music.setFile(i);
        } catch (CustomException e) {
            e.printStackTrace();
        }
        music.play();
        music.loop();
    }

    public void stopMusic() {
        music.stop();
    }

    public void playSF(int i) {

        try {
            se.setFile(i);
        } catch (CustomException e) {
            e.printStackTrace();
        }
        se.play();
    }
    public void retry() {

        player.defaultPositions();
        player.restoreLife();
    }
}
