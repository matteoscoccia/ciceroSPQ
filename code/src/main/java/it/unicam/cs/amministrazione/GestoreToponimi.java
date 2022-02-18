package it.unicam.cs.amministrazione;

import it.unicam.cs.storage.DBManager;

import java.sql.SQLException;
import java.util.Scanner;

import static it.unicam.cs.main.App.gestoreToponimi;

public class GestoreToponimi {

    /**
     * Controlla se il genitore è esistente nel DB
     * @param toponimoDaControllare toponimo da controllare
     * @return true se il toponimo esiste nel DB, false altrimenti
     */
    public boolean controllaEsistenzaToponimo(String toponimoDaControllare) throws SQLException {
        return DBManager.controllaEsistenzaToponimo(toponimoDaControllare);
    }

    public void registraToponimo() throws SQLException {
        System.out.println("Specificare il nome del toponimo genitore");
        Scanner scanner = new Scanner(System.in);
        String nomeToponimoGenitore = scanner.nextLine();
        if(gestoreToponimi.controllaEsistenzaToponimo(nomeToponimoGenitore)){
            System.out.println("Inserire il nome del toponimo da aggiungere");
            String nomeToponimoDaAggiungere = scanner.nextLine();
            System.out.println("Confermi di voler aggiungere il toponimo "+nomeToponimoDaAggiungere+"con genitore "+nomeToponimoGenitore+"? [SI/NO]");
            if(scanner.nextLine().equals("SI")) DBManager.registraToponimo(nomeToponimoDaAggiungere, nomeToponimoGenitore);
            else System.out.println("Inserimento annullato");
        } else System.out.println("Genitore non esistente");
    }

    public void eliminaToponimo(String toponimoDaEliminare) throws SQLException{
        if(this.controllaEsistenzaToponimo(toponimoDaEliminare)){
            if(this.controllaEsistenzaFigli(toponimoDaEliminare)){
                System.out.println("Confermi di voler eliminare il toponimo '"+toponimoDaEliminare+"'? [SI/NO]");
                Scanner scanner = new Scanner(System.in);
                if(scanner.nextLine().equals("SI")){
                    DBManager.eliminaToponimo(toponimoDaEliminare);
                    System.out.println("Toponimo '"+toponimoDaEliminare+"' eliminato");
                } else System.out.println("Toponimo non eliminato");
            } else System.out.println("Impossibile eliminare, sono presenti sotto-toponimi");
        } else System.out.println("Questo toponimo non è presente nel DataBase");
    }

    public boolean controllaEsistenzaFigli(String toponimoDaControllare) throws SQLException {
        return DBManager.controllaEsistenzaFigli(toponimoDaControllare);
    }
}
