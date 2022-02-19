package it.unicam.cs.main;

import it.unicam.cs.amministrazione.Amministrazione;
import it.unicam.cs.amministrazione.GestoreTag;
import it.unicam.cs.amministrazione.GestoreToponimi;
import it.unicam.cs.assistenza.Assistenza;
import it.unicam.cs.esperienza.GestoreEsperienze;
import it.unicam.cs.storage.DBManager;
import it.unicam.cs.utente.Associazione;
import it.unicam.cs.utente.Cicerone;
import it.unicam.cs.utente.GestoreAccount;
import it.unicam.cs.utente.Turista;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class App {

    public static GestoreEsperienze gestoreEsperienze;
    public static GestoreAccount gestoreAccount;
    public static GestoreToponimi gestoreToponimi;
    public static GestoreTag gestoreTag;

    public static Cicerone cicerone;
    public static Associazione associazione;
    public static Turista turista;
    public static Amministrazione amministrazione;
    public static Assistenza assistenza;


    public static void main(String[] args) throws SQLException {
        //SETUP
        gestoreEsperienze = new GestoreEsperienze();
        gestoreAccount = new GestoreAccount();
        gestoreToponimi = new GestoreToponimi();
        assistenza = new Assistenza();
        gestoreTag = new GestoreTag();
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

    private static void visualizzaMenuAmministrazione() throws SQLException {
        int sceltaMenu = -1;
        Scanner inputScanner = new Scanner(System.in);
        do{
            System.out.println("\n-----------MENU PRINCIPALE AMMINISTRAZIONE-----------");
            System.out.println("Scegli cosa vuoi fare:");
            System.out.println("1) Effettua ricerca con filtri");
            System.out.println("2) Definisci Tag");
            System.out.println("3) Approva Tag");
            System.out.println("4) Aggiungi Toponimo");
            System.out.println("5) Elimina Toponimo");
            System.out.println("6) Elimina Account");
            System.out.println("0) Esci");
            System.out.println(">");
            sceltaMenu = Integer.parseInt(inputScanner.nextLine());

            switch (sceltaMenu){
                case 1: {
                    amministrazione.effettuaRicerca();
                }break;
                case 2:{
                    amministrazione.definireTag();
                }break;
                case 3:{
                    amministrazione.approvareTag();
                }break;
                case 4:{
                    amministrazione.aggiungiToponimo();
                }break;
                case 5:{
                    amministrazione.eliminaToponimo();
                }break;
                case 6:{
                    try {
                        amministrazione.eliminaAccount();
                    }catch(SQLException e){
                        e.printStackTrace();
                    }
                }break;
            }
        }while(sceltaMenu!=0);
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
                    turista.effettuaRicerca();
                }break;
                case 2:{
                    turista.visualizzaProfilo();
                }break;
                case 3:{
                    turista.contattaAssistenza();
                }
            }
        }while(sceltaMenu!=0);
    }

    private static void visualizzaMenuAssociazione() {
        int sceltaMenu = -1;
        Scanner inputScanner = new Scanner(System.in);
        do{
            System.out.println("\n-----------MENU PRINCIPALE ASSOCIAZIONE-----------");
            System.out.println("Benvenuto " + associazione.getNome());
            System.out.println("Scegli cosa vuoi fare:");
            System.out.println("1) Effettua ricerca con filtri");
            System.out.println("2) Visualizza profilo");
            System.out.println("3) Contatta assistenza");
            System.out.println("4) Aggiungi esperienza");
            System.out.println("5) Aggiungi Cicerone alla tua associazione");
            System.out.println("6) Elimina Cicerone dalla tua associazione");
            System.out.println("7) Proponi Tag");
            System.out.println("0) Esci");
            System.out.println(">");
            sceltaMenu = Integer.parseInt(inputScanner.nextLine());

            switch (sceltaMenu){
                case 1: {
                    associazione.effettuaRicerca();
                }break;
                case 2:{
                    associazione.visualizzaProfilo();
                }break;
                case 3:{
                    associazione.contattaAssistenza();
                }break;
                case 4:{
                    associazione.aggiungiEsperienza();
                }break;
                case 5:{
                    try {
                        associazione.associaCicerone();
                    }catch(SQLException e){
                        e.printStackTrace();
                    }
                }break;
                case 6:{
                    associazione.rimuoviCicerone();
                }break;
                case 7:{
                    associazione.proporreTag();
                }
            }
        }while(sceltaMenu!=0);
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
            System.out.println("4) Aggiungi esperienza");
            System.out.println("5) Modifica disponibilità");
            System.out.println("6) Proponi tag");
            System.out.println("0) Esci");
            System.out.println(">");
            sceltaMenu = Integer.parseInt(inputScanner.nextLine());

            switch (sceltaMenu){
                case 1: {
                    cicerone.effettuaRicerca();
                }break;
                case 2:{
                    cicerone.visualizzaProfilo();
                }break;
                case 3:{
                    cicerone.contattaAssistenza();
                }break;
                case 4:{
                    cicerone.aggiungiEsperienza();
                }break;
                case 5:{
                    cicerone.modificaDisponibilita();
                }break;
                case 6:{
                    cicerone.proporreTag();
                }break;
            }
        }while(sceltaMenu!=0);
    }

}
