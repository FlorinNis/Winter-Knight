package Main;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.Buffer;
import java.util.Objects;

import Entity.Entity;
import Entity.Player;
import Object.Heart;

import javax.imageio.ImageIO;

public class UI {

    Player p;
    GamePanel gp;
    Font Ancient_Modern_Tales, maruMonica;

    BufferedImage heart_full, heart_half, heart_blank, bg;

    public boolean messageOn = false;
    public String message = "";
    public boolean showKey = false;
    public int messageCounter = 0;
    private Graphics2D g2;
    public String currentDialogue = "";
    public int commandNum = 0;
    public int titleScreenState = 0; //0: NEW GAME, 1:SETTINGS
    int subState = 0;

    public UI(GamePanel gp) {

        this.gp = gp;

        try {
            InputStream is = getClass().getResourceAsStream("/Font/AncientModernTales-a7Po.ttf");
            Ancient_Modern_Tales = Font.createFont(Font.TRUETYPE_FONT, is);
            is = getClass().getResourceAsStream("/Font/x12y16pxMaruMonica.ttf");
            maruMonica = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        //Create hud obj
        Entity heart = new Heart(gp);
        heart_full = heart.image3;
        heart_half = heart.image2;
        heart_blank = heart.image;
    }

    public void showMessage(String text) {

        message = text;
        messageOn = true;
    }
    public void draw(Graphics2D g2) {

        this.g2 = g2;

        //TitleState
        if(gp.gameState == gp.titleScreen) {
            drawTitleScreen();
        }

        //playState
        if(gp.gameState == gp.playState) {
            drawPlayerLife();
        }
        //dialogueState
        if(gp.gameState == gp.dialogueState) {
            drawPlayerLife();
            g2.setFont(maruMonica);
            g2.setColor(Color.black);
            drawDialogueScreen();
        }
        //nextDialogueState
        if(gp.gameState == gp.nextDialogueState) {
            g2.setFont(maruMonica);
            g2.setColor(Color.black);
            drawDialogueScreen();
        }
        //option state
        if(gp.gameState == gp.optionState) {
            drawOptionsScreen();
        }
        //GAME OVER
        if(gp.gameState == gp.gameOverState) {
            drawGameOverScreen();
        }
    }
    public void drawGameOverScreen() {

        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        int x;
        int y;
        String text;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 110f));

        text = "You Died :(";
        g2.setColor(Color.black);
        x = getXForCenteredText(text);
        y = gp.tileSize*4;
        g2.drawString(text, x, y);
        //main
        g2.setColor(Color.white);
        g2.drawString(text, x-4, y-4);

        //retry
        g2.setFont(g2.getFont().deriveFont(50f));
        text = "Retry";
        x = getXForCenteredText(text);
        y += gp.tileSize * 4;
        g2.drawString(text, x, y);
        if(commandNum == 0) {
            g2.drawString(">", x-40, y);
        }

        //back to title screen
        text = "Quit";
        x = getXForCenteredText(text);
        y += 55;
        g2.drawString(text, x, y);
        if(commandNum == 1) {
            g2.drawString(">", x-40, y);
        }
    }
    public void drawTitleScreen() {

        if(titleScreenState == 0) {
            g2.setColor(new Color(70, 120, 80));
            //g2.drawImage(bg, 0, 0, gp.screenWidth, gp.screenHeight, null);
            g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

            //Title Name
            g2.setFont(Ancient_Modern_Tales.deriveFont(Font.BOLD, 96F));
            String text = "Winter Knight";
            int x = getXForCenteredText(text);
            int y = gp.tileSize * 3;

            //SHADOW
            g2.setColor(Color.black);
            g2.drawString(text, x + 5, y + 5);
            //MAIN COLOR
            g2.setColor(Color.WHITE);
            g2.drawString(text, x, y);

            //PLAYER IMAGE
            x = gp.screenWidth;
            y += gp.tileSize * 2;
            g2.drawImage(gp.player.stand1, x, y, gp.tileSize * 2, gp.tileSize * 2, null);

            //MENU
            g2.setFont(maruMonica.deriveFont(Font.BOLD, 48F));

            text = "New Game";
            x = getXForCenteredText(text);
            y += gp.tileSize * 2;
            g2.drawString(text, x, y);
            if (commandNum == 0) {
                g2.drawString(">", x - gp.tileSize, y);
            }

            text = "Load Game";
            x = getXForCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if (commandNum == 1) {
                g2.drawString(">", x - gp.tileSize, y);
            }

            /*
            text = "Settings";
            x = getXForCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if (commandNum == 2) {
                g2.drawString(">", x - gp.tileSize, y);
            }

             */
            text = "Quit Game";
            x = getXForCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if (commandNum == 2) {
                g2.drawString(">", x - gp.tileSize, y);
            }
        }
        else if(titleScreenState == 1) {

            //stuff

        }
        else if(titleScreenState == 2) {

            //BACKGROUND
            g2.setColor(new Color(70, 120, 80));
            g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

            //TITLE SETTINGS
            g2.setFont(Ancient_Modern_Tales.deriveFont(Font.BOLD, 96F));
            String text = "Settings";
            int x = getXForCenteredText(text);
            int y = gp.tileSize * 3;

            //SHADOW
            g2.setColor(Color.black);
            g2.drawString(text, x + 5, y + 5);

            //TITLE
            g2.setColor(Color.WHITE);
            g2.drawString(text, x, y);

            //MENU SELECTIONS
            g2.setFont(maruMonica.deriveFont(Font.BOLD, 48F));
            text = "FullScreen";
            x = getXForCenteredText(text);
            y += gp.tileSize * 2;
            g2.drawString(text, x, y);
            if (commandNum == 0) {
                g2.drawString(">", x - gp.tileSize, y);
            }

            text = "Sound";
            x = getXForCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if (commandNum == 1) {
                g2.drawString(">", x - gp.tileSize, y);
            }

            text = "Back";
            x = getXForCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if (commandNum == 2) {
                g2.drawString(">", x - gp.tileSize, y);
            }
        }
    }
    public void drawPauseScreen() {

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 80));
        String text = "PAUSED";
        int x = getXForCenteredText(text);
        int y = gp.screenHeight/2;


        g2.drawString(text, x, y);
    }
    public void drawDialogueScreen() {

        //WINDOW
        int x = gp.tileSize * 2;
        int y = gp.tileSize / 2;
        int width = gp.screenWidth - (gp.tileSize * 4);
        int height = gp.tileSize * 4;

        drawSubWindow(x, y, width, height);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 28F));
        x += gp.tileSize;
        y += gp.tileSize;

        for(String line : currentDialogue.split("\n")) {
            g2.drawString(line, x, y);
            y += 40;
        }
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 15F));
        String enter = "Press enter to continue...";
        g2.drawString(enter, gp.tileSize*14, gp.tileSize*4);

    }
    public void drawOptionsScreen() {
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(32F));

        //sub win
        int frameX = gp.tileSize*6;
        int frameY = gp.tileSize;
        int frameWidth = gp.tileSize*8;
        int frameHeight = gp.tileSize*10;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        switch(subState) {
            case 0: options_top(frameX, frameY); break;
            case 1: options_fullScreenNotification(frameX, frameY); break;
            case 2: options_control(frameX, frameY); break;
            case 3: options_endGameConfirmation(frameX, frameY); break;
        }

        gp.keyH.enterPressed = false;
    }
    public void options_top(int frameX, int frameY) {

        int textX;
        int textY;

        //TITLE
        String text = "Options";
        textX = getXForCenteredText(text);
        textY = frameY + gp.tileSize;
        g2.drawString(text, textX, textY);

        //full screen on/of
        textX = frameX + gp.tileSize;
        textY += gp.tileSize * 2;
        g2.drawString("Full Screen", textX, textY);
        if(commandNum == 0) {
            g2.drawString(">", textX-25, textY);
            if(gp.keyH.enterPressed == true) {
                if(gp.fulScreenOn == false) {
                    gp.fulScreenOn = true;
                    gp.setFullScreen();
                }
                else if(gp.fulScreenOn == true) {
                    gp.fulScreenOn = false;
                }
                subState = 1;
            }
        }

        //Music
        textY += gp.tileSize;
        g2.drawString("Music", textX, textY);
        if(commandNum == 1) {
            g2.drawString(">", textX-25, textY);
        }

        //SE
        textY += gp.tileSize;
        g2.drawString("SE", textX, textY);
        if(commandNum == 2) {
            g2.drawString(">", textX-25, textY);
        }

        //Control
        textY += gp.tileSize;
        g2.drawString("Control", textX, textY);
        if(commandNum == 3) {
            g2.drawString(">", textX-25, textY);
            if(gp.keyH.enterPressed == true) {
                subState = 2;
                commandNum = 0;
            }
        }

        //End game
        textY += gp.tileSize;
        g2.drawString("End Game", textX, textY);
        if(commandNum == 4) {
            g2.drawString(">", textX-25, textY);
            if(gp.keyH.enterPressed == true) {
                subState = 3;
                commandNum = 0;
            }
        }

        //Back
        textY += gp.tileSize*2;
        g2.drawString("Back", textX, textY);
        if(commandNum == 5) {
            g2.drawString(">", textX-25, textY);
            if(gp.keyH.enterPressed == true) {
                gp.gameState = gp.playState;
                commandNum = 0;
            }
        }

        //full screen check box
        textX = frameX + (int)(gp.tileSize * 4.5);
        textY = frameY + gp.tileSize * 2 + 24;
        g2.setStroke(new BasicStroke(3));
        g2.drawRect(textX, textY, 24, 24);
        if(gp.fulScreenOn == true) {
            g2.fillRect(textX, textY, 24, 24);
        }


        //music volume
        textY += gp.tileSize;
        g2.drawRect(textX, textY, 120, 24);
        int volumeWidth = 24 * gp.music.volumeScale;
        g2.fillRect(textX, textY, volumeWidth, 24);

        //se volume
        textY += gp.tileSize;
        g2.drawRect(textX, textY, 120, 24);
        volumeWidth = 24 * gp.se.volumeScale;
        g2.fillRect(textX, textY, volumeWidth, 24);

    }
    public void options_fullScreenNotification(int frameX, int frameY) {

        int textX = frameY + gp.tileSize*6;
        int textY = frameY + gp.tileSize*3;

        currentDialogue = "The change will take \neffect after restarting \nthe game.";

        for(String line: currentDialogue.split("\n")) {
            g2.drawString(line, textX, textY);
            textY += 40;
        }

        //back
        textY = frameY + gp.tileSize*9;
        g2.drawString("Back", textX, textY);
        if(commandNum == 0) {
            g2.drawString(">", textX-25, textY);
            if(gp.keyH.enterPressed == true) {
                subState = 0;
            }
        }

    }
    public void options_control(int frameX, int frameY) {

        int textX;
        int textY;

        //title
        String text = "Controls";
        textX = getXForCenteredText(text);
        textY = frameY + gp.tileSize;
        g2.drawString(text, textX, textY);

        textX = frameX + gp.tileSize;
        textY += gp.tileSize;
        g2.drawString("Move", textX, textY); textY+=gp.tileSize;
        g2.drawString("Confirm/Attack", textX, textY); textY+=gp.tileSize;
        g2.drawString("Options", textX, textY); textY+=gp.tileSize;

        textX = frameX + gp.tileSize*6;
        textY = frameY + gp.tileSize*2;
        g2.drawString("WASD", textX, textY); textY+=gp.tileSize;
        g2.drawString("ENTER", textX, textY); textY+=gp.tileSize;
        g2.drawString("ESC", textX, textY); textY+=gp.tileSize;

        //back
        textX = frameX + gp.tileSize;
        textY = frameY + gp.tileSize*9;
        g2.drawString("Back", textX, textY);
        if(commandNum == 0) {
            g2.drawString(">", textX-25, textY);
            if(gp.keyH.enterPressed == true) {
                subState = 0;
                commandNum = 3;
            }
        }

    }
    public void options_endGameConfirmation(int frameX, int frameY) {

        int textX = frameX + gp.tileSize;
        int textY = frameY + gp.tileSize*3;

        currentDialogue = "Quit the game and \nreturn to the title screen?";

        for(String line: currentDialogue.split("\n")) {
            g2.drawString(line, textX, textY);
            textY += 40;
        }

        //yes
        String text = "Yes!";
        textX = getXForCenteredText(text);
        textY += gp.tileSize*3;
        g2.drawString(text, textX, textY);
        if(commandNum == 0) {
            g2.drawString(">", textX-25, textY);
            if(gp.keyH.enterPressed == true) {
                subState = 0;
                gp.gameState = gp.titleScreen;
            }
        }
        //no
        text = "Nope";
        textX = getXForCenteredText(text);
        textY += gp.tileSize;
        g2.drawString(text, textX, textY);
        if(commandNum == 1) {
            g2.drawString(">", textX-25, textY);
            if(gp.keyH.enterPressed == true) {
                subState = 0;
                commandNum = 4;
            }
        }
    }
    public void drawSubWindow(int x, int y, int width, int height) {

        Color c = new Color(0, 0, 0, 210);
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 35, 35);

        c = new Color(255, 255, 255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x+5, y+5, width-10, height-10, 25,25);


    }
    public void drawPlayerLife() {

        int x = gp.tileSize / 2;
        int y = gp.tileSize / 2;
        int i = 0;

        //Draw blank heart
        while(i < gp.player.maxLife/2) {
            g2.drawImage(heart_blank, x, y, null);
            i++;
            x += gp.tileSize;
        }

        //reset
        x = gp.tileSize/2;
        y = gp.tileSize/2;
        i = 0;

        //draw current life
        while(i < gp.player.life) {
            g2.drawImage(heart_half, x, y, null);
            i++;
            if(i < gp.player.life) {
                g2.drawImage(heart_full, x, y, null);
            }
            i++;
            x += gp.tileSize;
        }
    }
    public int getXForCenteredText(String text) {

        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth/2 - length/2;
        return x;
    }
}
