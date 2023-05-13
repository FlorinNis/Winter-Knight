package Object;

import Entity.Entity;
import Main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Heart extends Entity {
    GamePanel gp;

    public Heart(GamePanel gp) {
        super(gp);

        name = "Heart";
        image = setup("/Hp/Low");
        image2 = setup("/Hp/Half");
        image3 = setup("/Hp/Full");

    }
}

