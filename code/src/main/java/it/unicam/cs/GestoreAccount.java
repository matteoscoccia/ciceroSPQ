package it.unicam.cs;

import java.sql.Array;
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
            System.out.println("\nInserisci email del cicerone che vuoi aggiungere: ");
            email = scanner.nextLine();
            if(this.esistenzaCicerone(email)) {
                if (this.controlloAssociazione(email)) valido = true;
                else System.out.println("\nCicerone gia associato ad una associazione");
            } else System.out.println("\nEmail non associata a nessun cicerone");

        } while(!valido);
        // todo inserire in DB il cicerone

    }

    private boolean esistenzaCicerone(String emailCicerone) throws SQLException {
       // rs = stmt.executeQuery("SELECT Utente WHERE tipo == 'c' && email == '"+emailCicerone+"'");
        return false;
    }

    private boolean controlloAssociazione(String email){
        // todo: aggiungere attributo listaCiceroni in utente nel diagramma di persistenza
        return false;
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
            }
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
