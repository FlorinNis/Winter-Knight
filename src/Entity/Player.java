package Entity;

import Main.GamePanel;
import Main.KeyHandler;
import Main.UI;
import Main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity{

    GamePanel gp;
    KeyHandler keyH;

    //Singleton
    private static Player instance;

    public final int screenX;
    public final int screenY;
    public int hasKey = 0;
    int finalDialog = 0;

    private Player(GamePanel gp, KeyHandler keyH) {

        super(gp);

        this.gp = gp;
        this.keyH = keyH;

        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2 - (gp.tileSize/2);

        solidArea = new Rectangle(8, 16, 32, 32);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        setDefaultValues();
        getPlayerImage();
    }

    //Singleton
    public static Player getInstance(GamePanel gp, KeyHandler keyH) {

        if(instance == null) {
            return new Player(gp, keyH);
        }
        return instance;
    }

    public void getPlayerImage(){

        up1 = setup("/player/up1");
        up2 = setup("/player/up2");
        down1 = setup("/player/down1");
        down2 = setup("/player/down2");
        left1 = setup("/player/left1");
        left2 = setup("/player/left2");
        right1 = setup("/player/right1");
        right2 = setup("/player/right2");
        stand1 = setup("/player/stand1");
        stand2 = setup("/player/stand2");
    }

    public void setDefaultValues() {

        worldX = gp.tileSize * 28;
        worldY = gp.tileSize * 24;
        speed = 4;
        direction = "stand";

        //PLAYER STATUS
        maxLife = 2; //2 - full heart
        life = maxLife;

    }

    public void update() {

        if(keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true || keyH.rightPressed == true || keyH.enterPressed == true) {
            if(keyH.upPressed == true) {
                direction = "up";
            }
            else if(keyH.downPressed == true) {
                direction = "down";
            }
            else if(keyH.leftPressed == true) {
                direction = "left";
            }
            else if(keyH.rightPressed == true) {
                direction = "right";
            }

            //checkTile Collision
            collisionOn = false;
            gp.cChecker.checkTile(this);

            //checkObject collision
            int objIndex = gp.cChecker.checkObject(this, true);
            pickUpObject(objIndex, gp.dialogueState);

            //check npc collision
            int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
            interactNPC(npcIndex);
            System.out.println(npcIndex);

            //check enemy collision
            int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
            contactMonster(monsterIndex);

            //check event
            gp.eHandler.checkEvent();

            gp.keyH.enterPressed = false;

            //if collision is false, knightu se misca
            if(collisionOn == false && keyH.enterPressed == false) {

                switch(direction) {
                    case "up":
                        worldY -= speed;
                        break;
                    case "down":
                        worldY += speed;
                        break;
                    case"left":
                        worldX -= speed;
                        break;
                    case "right":
                        worldX += speed;
                        break;
                }
            }
            gp.keyH.enterPressed = false;

            spriteCounter++;
            if(spriteCounter > 15) {
                if(spriteNum == 1) {
                    spriteNum = 2;
                }
                else if(spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }
        else {
            direction = "stand";
            spriteCounter++;
            if(spriteCounter > 15) {
                if(spriteNum == 1) {
                    spriteNum = 2;
                }
                else if(spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }

        //invincible
        if(invincible == true) {
            invincibleCounter++;
            if(invincibleCounter > 60) {
                invincible = false;
                invincibleCounter = 0;
            }
        }

        if(life <= 0) {
            gp.gameState = gp.gameOverState;
        }

    }
    public void pickUpObject(int i, int gameState) {

        if(i != 999) {

            String objectName = gp.obj[gp.currentMap][i].name;

            switch(objectName) {
                case "Key":
                    gp.gameState = gameState;
                    gp.playSF(2);
                    hasKey++;
                    gp.obj[gp.currentMap][i] = null;
                    gp.ui.currentDialogue = "You picked up a key!";
                    break;
                case "Door":
                    gp.gameState = gameState;
                    if(hasKey > 0) {
                        gp.playSF(3);
                        gp.obj[gp.currentMap][i] =  null;
                        hasKey--;
                        gp.ui.currentDialogue = "You opened the door!";
                    }
                    else {
                        gp.ui.currentDialogue = "You need a key!";
                    }
                    break;
                case "HealthUp":
                    gp.gameState = gameState;
                    maxLife += 2;
                    life += 2;
                    gp.playSF(1);
                    gp.obj[gp.currentMap][i] = null;
                    gp.ui.currentDialogue = "Hp UP!";
                    gp.keyH.enterPressed = false;
                    break;
                case "Chest":
                    gp.gameState = gameState;

                    break;
            }
        }
    }
    public void interactNPC(int i) {

        if (i != 999) {

            if(gp.keyH.enterPressed == true) {
                if(gp.gameState == gp.playState) {
                    gp.gameState = gp.dialogueState;
                    gp.npc[gp.currentMap][i].speak();
                } else if(gp.gameState == gp.dialogueState) {
                    if(gp.npc[gp.currentMap][i].dialogues[gp.npc[gp.currentMap][i].dialogueIndex] != null) {
                        gp.npc[gp.currentMap][i].speak();
                    } else {
                        finalDialog = 1;
                        gp.npc[gp.currentMap][i].dialogueIndex = 0;
                        gp.gameState = gp.playState;
                    }
                }
            }
        }

    }

    public void draw(Graphics2D g2) {

        BufferedImage image = null;

        switch(direction) {
            case "up":
                if(spriteNum == 1) {
                    image = up1;
                }
                if(spriteNum == 2) {
                    image = up2;
                }
                break;
            case "down":
                if(spriteNum == 1) {
                    image = down1;
                }
                if(spriteNum == 2) {
                    image = down2;
                }
                break;
            case "left":
                if(spriteNum == 1) {
                    image = left1;
                }
                if(spriteNum == 2) {
                    image = left2;
                }
                break;
            case "right":
                if(spriteNum == 1) {
                    image = right1;
                }
                if(spriteNum == 2) {
                    image = right2;
                }
                break;
            case "stand":
                if(spriteNum == 1) {
                    image = stand1;
                }
                if(spriteNum == 2) {
                    image = stand2;
                }
        }
        if(invincible == true) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        }
        g2.drawImage(image, screenX, screenY, null);

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

    }

    public void contactMonster(int i) {

        if(i != 999) {

            if(invincible == false) {
                gp.playSF(7);
                life -= 1;
                invincible = true;
            }
        }
    }
    public void defaultPositions() {

        worldX = gp.tileSize * 28;
        worldY = gp.tileSize * 16;
    }
    public void restoreLife() {
        life = maxLife;
        invincible = false;
    }
}
