package Main;


import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.SQLException;

import Entity.NPC_OldMan;
import Entity.Player;

import static Main.Main.c;

public class KeyHandler implements KeyListener{

    GamePanel gp;
    Player p;
    public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed;
    //DEBUG
    boolean checkDrawTime = false;
    boolean fulscreen = false;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {


        int code = e.getKeyCode();

        //Title State
        if(gp.gameState == gp.titleScreen) {

            if(gp.ui.titleScreenState == 0) {
                if (code == KeyEvent.VK_W) {
                    gp.ui.commandNum--;
                    gp.playSF(4);
                    if (gp.ui.commandNum < 0) {
                        gp.ui.commandNum = 2;
                    }
                }
                if (code == KeyEvent.VK_S) {
                    gp.ui.commandNum++;
                    gp.playSF(4);
                    if (gp.ui.commandNum > 2) {
                        gp.ui.commandNum = 0;
                    }
                }
                if (code == KeyEvent.VK_ENTER) {
                    if (gp.ui.commandNum == 0) {
                        gp.gameState = gp.playState;
                        gp.playSF(6);
                        //gp.playMusic(0);
                    }
                    if (gp.ui.commandNum == 1) {
                        //later
                    }
                    /*
                    if (gp.ui.commandNum == 2) {
                        gp.ui.titleScreenState = 2;
                    }

                     */
                    if (gp.ui.commandNum == 2) {
                        System.exit(0);
                    }
                }
            }
            else if(gp.ui.titleScreenState == 1) {
                //stuff
            }
            /*
            else if(gp.ui.titleScreenState == 2) {
                if (code == KeyEvent.VK_W) {
                    gp.ui.commandNum--;
                    gp.playSF(4);
                    if (gp.ui.commandNum < 0) {
                        gp.ui.commandNum = 2;
                    }
                }
                if (code == KeyEvent.VK_S) {
                    gp.ui.commandNum++;
                    gp.playSF(4);
                    if (gp.ui.commandNum > 2) {
                        gp.ui.commandNum = 0;
                    }
                }
                if (code == KeyEvent.VK_ENTER) {
                    if (gp.ui.commandNum == 0) {
                        if(fulscreen == false) fulscreen = true; else fulscreen = false;
                    }
                    if (gp.ui.commandNum == 1) {
                        //later
                    }
                    if (gp.ui.commandNum == 2) {
                        gp.ui.titleScreenState = 0;
                    }
                }
            }

             */
        }
        //Play state
        if(gp.gameState == gp.playState) {

            if (code == KeyEvent.VK_W) {
                upPressed = true;
            }
            if (code == KeyEvent.VK_S) {
                downPressed = true;
            }
            if (code == KeyEvent.VK_A) {
                leftPressed = true;
            }
            if (code == KeyEvent.VK_D) {
                rightPressed = true;
            }
            if( code == KeyEvent.VK_ENTER) {
                enterPressed = true;
            }
            if (code == KeyEvent.VK_ESCAPE) {
                gp.gameState = gp.optionState;
            }

            //DEBUG
            if (code == KeyEvent.VK_BACK_SPACE && checkDrawTime == false) {
                checkDrawTime = true;
            } else if (code == KeyEvent.VK_BACK_SPACE && checkDrawTime == true) {
                checkDrawTime = false;
            }
            if(code == KeyEvent.VK_R) {
                switch(gp.currentMap) {
                    case 0:
                        gp.tileM.loadMap("/Map/map.txt", 0);
                    case 1:
                        gp.tileM.loadMap("/Map/dungeon1.txt", 1);
                }
            }
        }
        //Pause state
        else if(gp.gameState == gp.pauseState) {
            if (code == KeyEvent.VK_ESCAPE) {
                gp.gameState = gp.playState;
            }
        }
        //Dialogue state
        else if(gp.gameState == gp.dialogueState) {
            if(code == KeyEvent.VK_ENTER) {
                gp.gameState = gp.playState;
                enterPressed = true;
            }
        }
        //option state
        else if(gp.gameState == gp.optionState) {
            optionsState(code);
        }
        else if(gp.gameState == gp.gameOverState) {
            gameOverState(code);
        }
    }
    public void optionsState(int code) {

        if(code == KeyEvent.VK_ESCAPE) {
            gp.gameState = gp.playState;
        }
        if(code == KeyEvent.VK_ENTER) {
            enterPressed = true;
        }

        int maxCommandNum = 0;
        switch(gp.ui.subState) {
            case 0: maxCommandNum = 5; break;
            case 3: maxCommandNum = 1; break;
        }

        if(code == KeyEvent.VK_W) {
            gp.ui.commandNum--;
            gp.playSF(4);
            if(gp.ui.commandNum < 0) {
                gp.ui.commandNum = maxCommandNum;
            }
        }
        if(code == KeyEvent.VK_S) {
            gp.ui.commandNum++;
            gp.playSF(4);
            if(gp.ui.commandNum > maxCommandNum) {
                gp.ui.commandNum = 0;
            }
        }
        if(code == KeyEvent.VK_A) {
            if(gp.ui.subState == 0) {
                if(gp.ui.commandNum == 1 && gp.music.volumeScale > 0) {
                    gp.music.volumeScale--;
                    gp.music.checkVolume();
                    gp.playSF(4);
                }
                if(gp.ui.commandNum == 2 && gp.se.volumeScale > 0) {
                    gp.se.volumeScale--;
                    try {
                        DataBase.insertRecord(c, gp.se.volumeScale);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    gp.playSF(4);
                }
            }
        }
        if(code == KeyEvent.VK_D) {
            if(gp.ui.subState == 0) {
                if(gp.ui.commandNum == 1 && gp.music.volumeScale < 5) {
                    gp.music.volumeScale++;
                    gp.music.checkVolume();
                    gp.playSF(4);
                }
                if(gp.ui.commandNum == 2 && gp.se.volumeScale < 5) {
                    gp.se.volumeScale++;
                    try {
                        DataBase.insertRecord(c, gp.se.volumeScale);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    gp.playSF(4);
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

        int code = e.getKeyCode();

        if(code == KeyEvent.VK_W){
            upPressed = false;
        }
        if(code == KeyEvent.VK_S){
            downPressed = false;
        }
        if(code == KeyEvent.VK_A){
            leftPressed = false;
        }
        if(code == KeyEvent.VK_D){
            rightPressed = false;
        }
    }
    public void gameOverState(int code) {
        if(code == KeyEvent.VK_W) {
            gp.ui.commandNum--;
            if(gp.ui.commandNum < 0) {
                gp.ui.commandNum = 1;
            }
            gp.playSF(4);
        }
        if(code == KeyEvent.VK_S){
            gp.ui.commandNum++;
            if(gp.ui.commandNum > 1) {
                gp.ui.commandNum = 0;
            }
            gp.playSF(4);
        }
        if(code == KeyEvent.VK_ENTER) {
            if(gp.ui.commandNum == 0) {
                gp.gameState = gp.playState;
                gp.retry();
            }
            else if(gp.ui.commandNum == 1) {
                gp.gameState = gp.titleScreen;
                gp.retry();
            }
        }
    }
}
