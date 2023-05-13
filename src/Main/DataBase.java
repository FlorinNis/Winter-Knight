package Main;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DataBase {

    public static void createTable(Connection c) throws SQLException {
        String sql = "" +
                "CREATE TABLE Settings " +
                "(" +
                "SE integer NOT NULL" +
                "); ";
        Statement stmt = c.createStatement();
        stmt.execute(sql);
    }


    public static void insertRecord(Connection c, int k) throws SQLException {
        String sql = "INSERT INTO Settings " +
                "VALUES (?);";
        PreparedStatement pstmt = c.prepareStatement(sql);

        pstmt.setInt(1, k);

        pstmt.executeUpdate();
    }



    public static Connection getConnection() {

        try {
            Class.forName("org.sqlite.JDBC");
            return DriverManager.getConnection("jdbc:sqlite:WinterKnight.db");
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}
