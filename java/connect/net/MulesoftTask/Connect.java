package net.MulesoftTask;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {
//    Connect to a database

    public static void connect() {
        Connection conn = null;
        try {
            // db parameters
            String url = "jdbc:sqlite:D:/SQLite/Mulesoft.db";
            // create a connection to the database
            conn = DriverManager.getConnection(url);

            System.out.println("Connection to SQLite has been established.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
    public static void createNewTable() {
        // SQLite connection string
        //already created database "Mulesoft.db"
        String url = "jdbc:sqlite:D:/SQLite/Mulesoft.db";

        // SQL statement for creating a new table
       /* String sql = "CREATE TABLE IF NOT EXISTS movies (\n"
                + " Name text NOT NULL,\n"
                + " Actor text NOT NULL,\n"
                + " Actress text NOT NULL,\n"
                + " Director text NOT NULL,\n"
                + " ReleaseYear real NOT NULL,\n"
                + ");";  */
        String sql="CREATE TABLE IF NOT EXISTS Movies ( Name text NOT NULL,Actor text NOT NULL, Actress text NOT NULL, Director text NOT NULL, ReleaseYear real NOT NULL)";

        try{
            Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
            //System.out.println("Table created.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void insert(String Name,String Actor ,String Actress,String Director, double ReleaseYear) {
        String sql = "INSERT INTO movies(Name, Actor,Actress,Director,ReleaseYear) VALUES(?,?,?,?,?)";
        String url = "jdbc:sqlite:D:/SQLite/Mulesoft.db";
        try{
            Connection conn = DriverManager.getConnection(url);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, Name);
            pstmt.setString(2, Actor);
            pstmt.setString(3, Actress);
            pstmt.setString(4, Director);
            pstmt.setDouble(5, ReleaseYear);
            pstmt.executeUpdate();
            //System.out.println("Records inserted.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void selectAll(){
        String sql = "SELECT * FROM movies";
        String url = "jdbc:sqlite:D:/SQLite/Mulesoft.db";
        try{
            Connection conn = DriverManager.getConnection(url);
            Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);

            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getString("Name") +  "\t" +
                        rs.getString("Actor") + "\t" +
                        rs.getString("Actress") + "\t" +
                        rs.getString("Director") + "\t" +
                        rs.getDouble("ReleaseYear"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public void selectByActorName(String Actor){
        String sql = "SELECT * FROM movies where Actor=?";
        String url = "jdbc:sqlite:D:/SQLite/Mulesoft.db";
        try{
            Connection conn = DriverManager.getConnection(url);
            PreparedStatement pstmt  = conn.prepareStatement(sql);
            pstmt.setString(1,Actor);

            ResultSet rs    = pstmt.executeQuery();
            rs.next();
            System.out.println(rs.getString("Name") +  "\t" +
                    rs.getString("Actor") + "\t" +
                    rs.getString("Actress") + "\t" +
                    rs.getString("Director") + "\t" +
                    rs.getDouble("ReleaseYear"));
            // loop through the result set

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public static void main(String[] args) {
        createNewDatabase("Mulesoft.db");
        createNewTable();
        sqlite app = new sqlite();

        app.insert("Bahubali","Prabhas","Tamannah","S.S. Rajamouli",2015);
        app.insert("Twilight Saga","Robert Pattinson","Kristen Stiwart","Catherine Hardwicke",2009);
        app.insert("3 Idiots","Aamir Khan","Kareena Kapoor","Rajkumar Hirani",2009);

        app.selectAll();
        app.selectByActorName("Prabhas");
    }
}