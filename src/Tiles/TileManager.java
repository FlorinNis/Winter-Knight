package Tiles;

import Main.GamePanel;
import Main.UtilityTool;
import TratareExceptii.CustomException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {

    GamePanel gp;
    public Tile[] tile;
    public int mapTileNum[][][];

    public TileManager(GamePanel gp) {
        this.gp = gp;

        tile = new Tile[50];
        mapTileNum = new int[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];


        try {
            getTileImage();
        } catch (CustomException e) {
            e.printStackTrace();
        }
        loadMap("/Map/map.txt", 0);
        loadMap("/Map/dungeon1.txt", 1);
        loadMap("/Map/dungeon2.txt", 2);
    }

    public void getTileImage() throws CustomException {

        setup(0, "snow_tile", false);
        setup(1, "wall", true);
        setup(2, "water", true);
        setup(3, "tree_remake", true);
        setup(4, "road", false);
        setup(5, "port", false);
        setup(6, "wall_house", true);
        setup(7, "door", true);
        setup(8, "roof_left", true);
        setup(9, "roof_right", true);
        setup(10, "roof_top", true);
        setup(11, "floor", false);
        setup(24, "grass/grass", false);
        setup(25, "grass/grass_snow_down", false);
        setup(26, "grass/grass_snow_left", false);
        setup(27, "grass/grass_snow_right", false);
        setup(28, "grass/grass_snow_up", false);
        setup(29, "grass/coltstangasus", false);
        setup(30, "grass/colt_stanga_jos", false);
        setup(31, "grass/colt_dreapta_jos", false);
        setup(32, "grass/colt_dreapta_sus", false);
        setup(34, "void", true);
        setup(40, "floorDungeon", false);


        //water tiles
        setup(12, "Water/water_top", true);
        setup(13, "Water/water_left", true);
        setup(14, "Water/water_right", true);
        setup(15, "Water/water_down", true);
        setup(16, "Water/water_corner_left_up", true);
        setup(17, "Water/water_corner_left_down", true);
        setup(18, "Water/water_corner_right_up", true);
        setup(19, "Water/water_corner_right_down", true);
        setup(22, "Water/water_ice", true);

        //aesthetics
        setup(20, "simple_snow", false);
        setup(21, "rock", true);
        setup(23, "shipwreck", true);

        setup(33, "stairs", false);


    }
    public void setup(int index, String imageName, boolean collision) throws CustomException {

        UtilityTool uTool = new UtilityTool();

        try{
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(getClass().getResourceAsStream("/Tiles/" + imageName + ".png"));
            tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
            tile[index].collision = collision;

        }catch(IOException e) {
            throw new CustomException("Eroare la incarcare tile");
        }
    }
    public void loadMap(String filePath, int map){

        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while(col < gp.maxWorldCol && row < gp.maxWorldRow) {

                String line = br.readLine();

                while(col < gp.maxWorldCol) {
                    String numbers[] = line.split(" ");

                    int num = Integer.parseInt(numbers[col]);

                    mapTileNum[map][col][row] = num;
                    col++;
                }
                if(col == gp.maxWorldCol) {
                    col = 0;
                    row++;
                }
            }
            br.close();
        } catch(Exception e) {
            System.out.println("Map could not be loaded");
        }
    }
    public void draw(Graphics2D g2) {

        int worldCol = 0;
        int worldRow = 0;


        while(worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {

            int tileNum = mapTileNum[gp.currentMap][worldCol][worldRow];

            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            //randeaza doar in jurul caracterului
            if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                    worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                    worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                    worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
                g2.drawImage(tile[tileNum].image, screenX, screenY, null);
            }


            worldCol++;

            if(worldCol == gp.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }
        }
    }
}
