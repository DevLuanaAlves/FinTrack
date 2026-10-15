package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    private static String url = "jdbc:sqlite:fintrack.db";

    public static void setUrl(String novaUrl) {
        url = novaUrl;
    }

    public static String getUrl() {
        return url;
    }

    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(url);
    }
}