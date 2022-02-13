package it.unicam.cs;

import java.net.StandardSocketOptions;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class App {

    public static GestoreEsperienze gestoreEsperienze;
    public static GestoreAccount gestoreAccount;

    public static Cicerone cicerone;
    public static Associazione associazione;
    public static Turista turista;
    public static Amministrazione amministrazione;
    public static Assistenza assistenza;


    public static void main(String[] args){
        //SETUP
        gestoreEsperienze = new GestoreEsperienze();
        gestoreAccount = new GestoreAccount();
        assistenza = new Assistenza();
        Scanner inputScanner = new Scanner(System.in);

        //DB SETUP
        DBManager dbManager = DBManager.getInstance();
        dbManager.setDBManager("jdbc:mysql://localhost:3306/cicero", "admin", "admin");
        dbManager.DBtest();
        dbManager.connect();

        int sceltaUtente = 0;
        do{
           System.out.println("\n\n-----------------CICERO-----------------");
           System.out.println("Come vuoi autenticarti?");
           System.out.println("1)Cicerone");
           System.out.println("2)Associazione");
           System.out.println("3)Turista");
           System.out.println("4)Amministrazione");
           System.out.println("0)Esci");
           System.out.println(">");
           sceltaUtente = Integer.parseInt(inputScanner.nextLine());

           switch (sceltaUtente){
               case 1: {cicerone = new Cicerone("Mario", "Rossi", "mario.rossi@gmail.com", "password", "fai@gmail.com");
                        visualizzaMenuCicerone();}
                        break;
               case 2: {associazione = new Associazione("FAI", null, "fai@gmail.com" , "1234" );
                        ArrayList<String> elencoCiceroni = DBManager.trovaCiceroniAssociati("fai@gmail.com");
                        associazione.setEmailCiceroniAssociati(elencoCiceroni);
                        visualizzaMenuAssociazione();}
                        break;
               case 3: {
                   turista = new Turista("Lucia", "Verdi", "lucia.verdi@gmail.com", "pass123");
                   visualizzaMenuTurista();
               }break;
               case 4: {
                   amministrazione = new Amministrazione("admin@cicero.com");
                   visualizzaMenuAmministrazione();
               }break;
           }

        }while(sceltaUtente<0 || sceltaUtente >4);

        //System.out.println("\n\n\nCi sono " + dbManager.getTableCount("utente") + " utenti registrati\n\n");
        dbManager.close();
    }

    private static void visualizzaMenuAmministrazione() {
        //TODO IMPLEMENTARE MENU AMMINISTRAZIONE
    }

    private static void visualizzaMenuTurista() {
        int sceltaMenu = -1;
        Scanner inputScanner = new Scanner(System.in);
        do{
            System.out.println("\n-----------MENU PRINCIPALE TURISTA-----------");
            System.out.println("Benvenuto " + turista.getNome() + " " + turista.getCognome());
            System.out.println("Scegli cosa vuoi fare:");
            System.out.println("1) Effettua ricerca con filtri");
            System.out.println("2) Visualizza profilo");
            System.out.println("3) Contatta assistenza");
            System.out.println("0) Esci");
            System.out.println(">");
            sceltaMenu = Integer.parseInt(inputScanner.nextLine());

            switch (sceltaMenu){
                case 1: {
                    //TODO COLLEGARE AL METODO RICERCA CON FILTRI
                    System.out.println("Hai selezionato ricerca con filtri");
                }break;
                case 2:{
                    //TODO COLLEGARE AL METODO VISUALIZZA PROFILO
                    System.out.println("Hai selezionato visualizza profilo");
                }break;
                case 3:{
                    System.out.println("Hai selezionato contatta assistenza");
                    Assistenza.getAssistenza(turista);
                }
            }
        }while(sceltaMenu!=0);
    }

    private static void visualizzaMenuAssociazione() {
        //TODO IMPLEMENTARE MENU
        System.out.println("ELENCO CICERONI ASSOCIATI FAI:\n");
        for (String s: associazione.getEmailCiceroniAssociati()) {
                System.out.println(s);
        }
    }

    private static void visualizzaMenuCicerone() {
        int sceltaMenu = -1;
        Scanner inputScanner = new Scanner(System.in);
        do{
            System.out.println("\n-----------MENU PRINCIPALE CICERONE-----------");
            System.out.println("Benvenuto " + cicerone.getNome() + " " + cicerone.getCognome());
            System.out.println("Scegli cosa vuoi fare:");
            System.out.println("1) Effettua ricerca con filtri");
            System.out.println("2) Visualizza profilo");
            System.out.println("3) Contatta assistenza");
            System.out.println("0) Esci");
            System.out.println(">");
            sceltaMenu = Integer.parseInt(inputScanner.nextLine());

            switch (sceltaMenu){
                case 1: {
                    //TODO COLLEGARE AL METODO RICERCA CON FILTRI
                    System.out.println("Hai selezionato ricerca con filtri");
                }break;
                case 2:{
                    //TODO COLLEGARE AL METODO VISUALIZZA PROFILO
                    System.out.println("Hai selezionato visualizza profilo");
                }break;
                case 3:{
                    System.out.println("Hai selezionato contatta assistenza");
                    Assistenza.getAssistenza(cicerone);
                }
            }
        }while(sceltaMenu!=0);
    }

}
