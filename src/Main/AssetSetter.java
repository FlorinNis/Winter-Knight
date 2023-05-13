package Main;

import Enemy.Green_Slime;
import Entity.NPC_Factory;
import Entity.NPC_OldMan;
import Entity.NPC_Type;
import Entity.NPC_boy;
import Object.Key;
import Object.Door;
import Object.Chest;
import Object.HealthUp;

public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp) {

        this.gp = gp;
    }

    public void setObject() {

        int mapNum = 0;

        gp.obj[mapNum][0] = new Key(gp);
        gp.obj[mapNum][0].worldX = 39 * gp.tileSize;
        gp.obj[mapNum][0].worldY = 14 * gp.tileSize;

        //gp.obj[1] = new Chest(gp);
        //gp.obj[1].worldX = 14 * gp.tileSize;
        //gp.obj[1].worldY = 26 * gp.tileSize;

        gp.obj[mapNum][2] = new Door(gp);
        gp.obj[mapNum][2].worldX = 14 * gp.tileSize;
        gp.obj[mapNum][2].worldY = 19 * gp.tileSize;

        gp.obj[mapNum][3] = new HealthUp(gp);
        gp.obj[mapNum][3].worldX = 28 * gp.tileSize;
        gp.obj[mapNum][3].worldY = 37 * gp.tileSize;

        gp.obj[1][4] = new Door(gp);
        gp.obj[1][4].worldX = 14 * gp.tileSize;
        gp.obj[1][4].worldY = 25 * gp.tileSize;

        gp.obj[1][5] = new Chest(gp);
        gp.obj[1][5].worldX = 48 * gp.tileSize;
        gp.obj[1][5].worldY = 28 * gp.tileSize;
    }
    public void setNPC() {

        int mapNum = 0;
        //factory
        NPC_Factory factory = new NPC_Factory();

        gp.npc[mapNum][0] = factory.getNPC(NPC_Type.OLDMAN, gp);
        gp.npc[mapNum][0].worldX = gp.tileSize * 26;
        gp.npc[mapNum][0].worldY = gp.tileSize * 23;

        gp.npc[mapNum][1] = factory.getNPC(NPC_Type.BOY, gp);
        gp.npc[mapNum][1].worldX = gp.tileSize * 15;
        gp.npc[mapNum][1].worldY = gp.tileSize * 9;
    }

    public void setMonster() {

        gp.monster[1][0] = new Green_Slime(gp);
        gp.monster[1][0].worldX = gp.tileSize * 33;
        gp.monster[1][0].worldY = gp.tileSize * 10;

        gp.monster[1][1] = new Green_Slime(gp);
        gp.monster[1][1].worldX = gp.tileSize * 36;
        gp.monster[1][1].worldY = gp.tileSize * 10;

        gp.monster[1][2] = new Green_Slime(gp);
        gp.monster[1][2].worldX = gp.tileSize * 30;
        gp.monster[1][2].worldY = gp.tileSize * 8;

        /*
        gp.monster[0][3] = new Green_Slime(gp);
        gp.monster[0][3].worldX = gp.tileSize * 24;
        gp.monster[0][3].worldY = gp.tileSize * 22;

         */
    }
}
