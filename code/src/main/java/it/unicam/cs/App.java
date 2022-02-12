package it.unicam.cs;

import java.sql.*;

public class App {

    public static GestoreEsperienze gestoreEsperienze;
    public static GestoreAccount gestoreAccount;
    public static ResultSet rs;
    public static Statement stmt;

    public static void main(String[] args){
        gestoreEsperienze = new GestoreEsperienze();
        gestoreAccount = new GestoreAccount();
        DBManager dbManager = DBManager.getInstance();
        dbManager.setDBManager("jdbc:mysql://127.0.0.1:3306/cicero", "admin", "admin");
        dbManager.DBtest();
    }

}
