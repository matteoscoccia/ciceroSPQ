package it.unicam.cs;

import java.sql.*;
import java.util.ArrayList;

public class DBManager {

    private static DBManager instance;
    private String url;
    private String user;
    private String pwd;
    private static Connection conn = null;


    public void setDBManager(String url, String user, String pwd) {
        this.url = url;
        this.user = user;
        this.pwd = pwd;
    }

    public static DBManager getInstance() {
        if (instance == null) {
            instance = new DBManager();
        }
        return instance;
    }

    public void connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Where is your MySQL JDBC Driver? " + "Include in your library path!");
            e.printStackTrace();
        }
        try {
            conn = DriverManager.getConnection(url, user, pwd);
            System.out.println("Database connected, ready to go!");
        } catch (SQLException e) {
            System.out.println("Problems in opening a connection to the DB");
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            conn.close();
        } catch (SQLException e) {
            System.out.println("Problems in closing the connection to the DB");
            e.printStackTrace();
        }
    }

    public boolean DBtest() {
        Boolean result = true;
        try {
            if (conn == null || !conn.isClosed()) {
                connect();
                result = false;
            }
            DatabaseMetaData data = conn.getMetaData();
           System.out.println("Details on DBMS - " + data.getDatabaseProductName() + "\n" + "  version:  "
                    + data.getDriverMajorVersion() + "\n" //+ "  catalogs: " + data.getCatalogs().getCursorName() + "\n"
                   + "  schemas:  " + data.getSchemas().getRow() + "\n");
            close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public int getTableCount(String table) {
        int count = -1;
        try {
            Statement s = conn.createStatement();
            ResultSet r = s.executeQuery("SELECT COUNT(*) AS rowcount FROM " + table);
            r.next();
            count = r.getInt("rowcount");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("MyTable has " + count + " row(s).");

        return count;
    }

    public static ArrayList<String> trovaCiceroniAssociati(String emailAssociazione) {
        ArrayList<String> ciceroni = new ArrayList<>();

        try {
            Statement s = conn.createStatement();
            ResultSet r = s.executeQuery("SELECT email FROM utente WHERE emailAssociazione = '" + emailAssociazione+"'");
            while(r.next()){ciceroni.add(r.getString("email"));};
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ciceroni;
    }


}
