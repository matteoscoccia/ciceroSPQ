package it.unicam.cs;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
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

    public static void eliminareEsperienza(Esperienza esperienzaDaEliminare){
        try {
            Statement s = conn.createStatement();
            ResultSet r = s.executeQuery("DELETE FROM esperienza WHERE titolo='"+esperienzaDaEliminare.getTitolo()+"' AND descrizione = '"+esperienzaDaEliminare.getDescrizione()+"' " );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Cicerone> listaCiceroni (){
        ArrayList<Cicerone> listaCiceroni = new ArrayList<>();

        try {
            Statement s = conn.createStatement();
            ResultSet r = s.executeQuery("SELECT FROM utente WHERE tipo = 'c' " );
            while(r.next()){
                //TODO CONTROLLARE NOMI TABELLE NEL DB
                listaCiceroni.add(new Cicerone(r.getString("nome"),r.getString("cognome"),r.getString("email"),r.getString("password"),r.getString("emailassociazione")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaCiceroni;
    }

    public static ArrayList<Cicerone> listaCiceroniAssociati(String emailAssociazione){
        ArrayList<Cicerone> listaCiceroni = new ArrayList<>();
        try {
            Statement s = conn.createStatement();
            ResultSet r = s.executeQuery("SELECT FROM utente WHERE emailAssociazione = '"+emailAssociazione+"' " );
            while(r.next()){
                //TODO CONTROLLARE NOMI TABELLE NEL DB
                listaCiceroni.add(new Cicerone(r.getString("nome"),r.getString("cognome"),r.getString("email"),r.getString("password"),r.getString("emailAssociazione")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaCiceroni;
    }

    public static void eliminareCiceroneAssociazione (String emailCicerone){
        try {
            Statement s = conn.createStatement();
            ResultSet r = s.executeQuery("UPDATE utente SET emailAssociazione = NULL WHERE email = '"+emailCicerone+"'" );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Date visualizzaDisponibilitaCicerone ( String emailCicerone){
        try {
            Statement s = conn.createStatement();
            ResultSet r = s.executeQuery("SELECT disponibilità WHERE emailCicerone = '"+emailCicerone+"'" );
            return r.getDate("data");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new Date(0);
    }

    public static void modoficaDisponibilità ( String emailCicerone, Date nuovaData){
        try {
            Statement s = conn.createStatement();
            ResultSet r = s.executeQuery("UPDATE disponibilita SET data = '"+nuovaData+"' WHERE email = '"+emailCicerone+"'" );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
