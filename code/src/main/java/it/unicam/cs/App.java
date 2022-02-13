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
                    //TODO COLLEGARE AL METODO RICERCA CON FILTRI
                    System.out.println("Hai selezionato ricerca con filtri");
                }break;
                case 2:{
                    //TODO COLLEGARE AL METODO DEFINISCI TAG
                    System.out.println("Hai selezionato definisci Tag");
                }break;
                case 3:{
                    //TODO COLLEGARE AL METODO APPROVA TAG
                    System.out.println("Hai selezionato approva Tag");
                }break;
                case 4:{
                    //TODO COLLEGARE AL METODO AGGIUNGI TOPONIMO
                    System.out.println("Hai selezionato aggiungi Toponimo");
                }break;
                case 5:{
                    //TODO COLLEGARE AL METODO ELIMINA TOPONIMO
                    System.out.println("Hai selezionato elimina Toponimo");
                }break;
                case 6:{
                    //TODO COLLEGARE AL METODO ELIMINA ACCOUNT
                    System.out.println("Hai selezionato elimina Account");
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
                    //TODO COLLEGARE AL METODO RICERCA CON FILTRI
                    System.out.println("Hai selezionato ricerca con filtri");
                }break;
                case 2:{
                    //TODO COLLEGARE AL METODO VISUALIZZA PROFILO
                    System.out.println("Hai selezionato visualizza profilo");
                }break;
                case 3:{
                    //TODO COLLEGARE AL METODO ASSISTENZA
                    System.out.println("Hai selezionato contatta assistenza");
                    Assistenza.getAssistenza(turista);
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
                    //TODO COLLEGARE AL METODO RICERCA CON FILTRI
                    System.out.println("Hai selezionato ricerca con filtri");
                }break;
                case 2:{
                    //TODO COLLEGARE AL METODO VISUALIZZA PROFILO
                    System.out.println("Hai selezionato visualizza profilo");
                }break;
                case 3:{
                    //TODO COLLEGARE AL METODO ASSISTENZA
                    System.out.println("Hai selezionato contatta assistenza");
                }break;
                case 4:{
                    //TODO COLLEGARE AL METODO AGGIUNGI ESPERIENZA
                    System.out.println("Hai selezionato aggiungi esperienza");
                }break;
                case 5:{
                    //TODO COLLEGARE AL METODO AGGIUNGI CICERONE AD ASSOCIAZIONE
                    System.out.println("Hai selezionato aggiungi cicerone alla tua associazione");
                }break;
                case 6:{
                    //TODO COLLEGARE AL METODO ELIMINA CICERONE DA ASSOCIAZIONE
                    System.out.println("Hai selezionato elimina un cicerone dalla tua associazione");
                }break;
                case 7:{
                    //TODO COLLEGARE AL METODO PROPONI TAG
                    System.out.println("Hai selezionato proponi Tag");
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
                    //TODO COLLEGARE AL METODO RICERCA CON FILTRI
                    System.out.println("Hai selezionato ricerca con filtri");
                }break;
                case 2:{
                    //TODO COLLEGARE AL METODO VISUALIZZA PROFILO
                    System.out.println("Hai selezionato visualizza profilo");
                }break;
                case 3:{
                    //TODO COLLEGARE AL METODO ASSISTENZA
                    System.out.println("Hai selezionato contatta assistenza");
                    Assistenza.getAssistenza(cicerone);
                }break;
                case 4:{
                    //TODO COLLEGARE AL METODO AGGIUNGI ESPERIENZA
                    System.out.println("Hai selezionato aggiungi esperienza");
                    cicerone.aggiungiEsperienza();
                }break;
                case 5:{
                    //TODO COLLEGARE AL METODO MODIFICA DISPONIBILITA
                    System.out.println("Hai selezionato modifica disponibilita");
                }break;
                case 6:{
                    //TODO COLLEGARE AL METODO PROPONI TAG
                    System.out.println("Hai selezionato proponi tag");
                }break;
            }
        }while(sceltaMenu!=0);
    }

}
