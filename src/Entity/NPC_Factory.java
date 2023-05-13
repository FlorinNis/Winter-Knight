package Entity;

import Main.GamePanel;

public class NPC_Factory {

    public Entity getNPC(NPC_Type type, GamePanel gp) {

        return switch (type) {
            case BOY -> new NPC_boy(gp);
            case OLDMAN -> new NPC_OldMan(gp);
        };
    }
}
