package it.unicam.cs.utente;

import it.unicam.cs.storage.DBManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class GestoreAccount {

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);


    public void iscrizione(){
        //TODO implementare il corpo del metodo??
    }

    public static boolean controllaFormato(String email) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        return matcher.find();
    }

    public void aggiungiCicerone(Associazione associazione) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        boolean valido = false;
        String email;
        do{
            System.out.println("Inserisci email del cicerone che vuoi aggiungere: ['exit' per uscire]");
            email = scanner.nextLine();
            if(email.equals("exit")) break;
            if(DBManager.esistenzaCicerone(email)) {
                if (!DBManager.controlloAssociazione(email)) {
                    DBManager.addCiceroneTo(email, associazione);
                    System.out.println("Cicerone associato correttamente");
                    valido = true;
                } else System.out.println("Cicerone gia associato ad una associazione");
            } else System.out.println("Email non associata a nessun cicerone");
        } while(!valido);
    }

    public static void adminEliminaAccount() throws SQLException {
        System.out.println("Per eliminare un account scrivere l' E-mail:\n");
        Scanner scanner = new Scanner(System.in);
        String emailDaEliminare = scanner.nextLine();
        if(DBManager.controllaEsistenza(emailDaEliminare)) {
            System.out.println("Fornire motivazione:\n");
            String motivazione = scanner.nextLine();
            System.out.println("Vuoi confermare? [SI/NO]\n");
            if(scanner.nextLine().equals("SI")) {
                DBManager.eliminaUtente(emailDaEliminare, motivazione);
                System.out.println("Utente eliminato correttamente");
            } else if(scanner.nextLine().equals("NO")) System.out.println("Utente non eliminato");
        } else System.out.println("Account inesistente");
    }

    public static void utenteEliminaAccount(Utente utente) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Sei sicuro di volere eliminare il tuo account: [SI/NO]\n");
        if(scanner.nextLine().equals("SI")) DBManager.eliminaUtente(utente.getEmail(),"");
        System.out.println("Account eliminato");
    }

    public void rimuoviCicerone(Associazione associazione){
        Scanner scanner = new Scanner(System.in);
        String emailCiceroneEliminare,conferma;
        do{
            System.out.println("Inserire email cicerone da eliminare");
            emailCiceroneEliminare = scanner.nextLine();
            System.out.println("Cicerone da eliminare : " +emailCiceroneEliminare);
            System.out.println("Confermare S/N");
            conferma = scanner.nextLine();
        }while(!(conferma.equals("S") || conferma.equals("N")));
        if(conferma.equals("S")){
            if(verificaAppartenenza(emailCiceroneEliminare,associazione)){
                DBManager.eliminareCiceroneAssociazione(emailCiceroneEliminare);
                System.out.println("Cicerone eliminato dalla propria associazione");
            }else{
                System.out.println("Il cicerone inserito non appartiene all'associazione");
            }
        }else{
            System.out.println("\nOperazione annullata");
        }

    }

    private boolean verificaAppartenenza(String emailCicerone, Associazione associazione){
        ArrayList<Cicerone> listaCiceroni = DBManager.listaCiceroni();
        for (Cicerone cicerone:listaCiceroni) {
            if(cicerone.getEmail().equals(emailCicerone) && cicerone.getEmailAssociazione().equals(associazione.getEmail()))
                return true;
        }
        return false;
    }

}
