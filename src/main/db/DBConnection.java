package main.db;

import java.sql.*;

public class DBConnection {
    static ResultSet resultSet = null;
    static Statement stmt = null;
    static Connection connection = null;

    public static void connect() {
        String dbname = "pushdatabase";
        String user = "postgres";
        //Password should not be stored in plain text! But this database is so far only used locally.
        String pwd = "123";

        // Connection details
        String connectionStr =
                "user=" + user + "&" +
                        "port=5432&" +
                        "password=" + pwd + "";

        String host = "jdbc:postgresql://localhost";
        String connectionURL =
                host + "/" + dbname +
                        "?sslmode=require&ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory&" +
                        connectionStr;

        try {
            // Load driver for PostgreSQL
            Class.forName("org.postgresql.Driver");
            // Create a connection to the database
            connection = DriverManager.getConnection(host + "/" + dbname
                    + "?" + connectionStr);

        }
        catch (SQLException | ClassNotFoundException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public static PreparedStatement prepareStmt (String s) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement(s);
        return stmt;
    }

    public static void close() {
        try { resultSet.close(); } catch (Exception e) { }
        try { stmt.close(); } catch (Exception e) {  }
        try { connection.close(); } catch (Exception e) { }
    }
}


