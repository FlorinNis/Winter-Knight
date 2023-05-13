package Main;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {

    public static Connection c;
    public static JFrame window;

    public static void main(String[] args) {

        c = DataBase.getConnection();
        try {
            DatabaseMetaData dbm = c.getMetaData();
            ResultSet table = dbm.getTables(null, null, "Settings", null);
            if(!table.next()) {
                DataBase.createTable(c);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(true);
        window.setTitle("Winter Knight");
        window.setUndecorated(false);

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.setupGame();
        gamePanel.startGameThread();
    }
}
