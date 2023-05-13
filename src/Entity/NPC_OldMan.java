package Entity;

import Main.GamePanel;

import java.util.Random;

public class NPC_OldMan extends Entity {

    public int text = 0;

    public NPC_OldMan(GamePanel gp) {
        super(gp);

        direction = "down";
        speed = 1;

        getImage();
        setDialogue();
    }

    public void getImage(){

        //stand1 = setup("/NPC/OldMan2/oldman_down_1");
        //stand2 = setup("/NPC/OldMan2/oldman_down_1");
        right1 = setup("/NPC/OldMan2/oldman_right_1");
        right2 = setup("/NPC/OldMan2/oldman_right_2");
        up1 = setup("/NPC/OldMan2/oldman_up_1");
        up2 = setup("/NPC/OldMan2/oldman_up_2");
        left1 = setup("/NPC/OldMan2/oldman_left_1");
        left2 = setup("/NPC/OldMan2/oldman_left_2");
        down1 = setup("/NPC/OldMan2/oldman_down_1");
        down2 = setup("/NPC/OldMan2/oldman_down_2");
    }
    public void setDialogue() {

        dialogues[0] = "Hey!";
        dialogues[1] = "When did you get here?";
        dialogues[2] = "Where were you hiding?";
        dialogues[3] = "Does not matter right now.";
        dialogues[4] = "I need your help!";
        dialogues[5] = "You see, there are monsters lurking around \n the forest.";
        dialogues[6] = "This is the safest place I could find.";
        dialogues[7] = "My house is just up there.";
        dialogues[8] = "But...";
        dialogues[9] = "...";
        dialogues[10] = "My grandson...";
        dialogues[11] = "He was behind me just now...";
        dialogues[12] = "But then he disappeared...";
        dialogues[13] = "Please!";
        dialogues[14] = "Please find him!";
        dialogues[15] = "I'm sure to reward you if you help me!";

    }
    public void setAction() {

        actionLockCounter ++;

        if (actionLockCounter == 120) {

            Random random = new Random();
            int i = random.nextInt(0, 100) + 1;
            System.out.println("I = " + i);

            if (i <= 25) {
                direction = "up";
            }
            if (i > 25 && i <= 50) {
                direction = "down";
            }
            if (i > 50 && i <= 75) {
                direction = "left";
            }
            if (i > 75 && i <= 100) {
                direction = "right";
            }
            //if (i > 100 && i <= 125) {
            //    direction = "stand";
            //}
            actionLockCounter = 0;
        }
    }
    public void speak() {

        if(text == 0) {
            super.speak();
            if(dialogueIndex == 15) {
                text++;
            }
        } else if(text == 1) {
                dialogueIndex = 15;
                super.speak();
                text++;
        } else if(text == 2) {
            gp.gameState = gp.playState;
            text = 1;
        }
    }


}
