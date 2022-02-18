package it.unicam.cs.utente;

import it.unicam.cs.esperienza.Esperienza;
import it.unicam.cs.storage.DBManager;

import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

import static it.unicam.cs.main.App.gestoreAccount;
import static it.unicam.cs.main.App.gestoreEsperienze;

public class Cicerone extends Utente {

    private String emailAssociazione;

    public Cicerone(String nome, String cognome, String email, String password, String emailAssociazione) {
        super(nome, cognome, email, password);
        this.emailAssociazione = emailAssociazione;
    }
    public String getEmailAssociazione(){
        return this.emailAssociazione;
    }

    public void aggiungiEsperienza(){
        Esperienza esperienzaCreata = gestoreEsperienze.aggiungiEsperienza();
        esperienzaCreata.associaCicerone(this);
        System.out.println("Esperienza registrata");
    }

    public void visualizzaProfilo(){
        System.out.println("Nome : " +this.getNome());
        System.out.println("Cognome : " +this.getCognome());
        System.out.println("Email : " +this.getEmail());
    }

    public void modificaDisponibilita(){
        String conferma;
        Date nuovaData;
        int anno,mese,giorno;
        Scanner scanner = new Scanner(System.in);
        System.out.println("La tua attuale disponibilità è : ");
        DBManager.visualizzaDisponibilitaCicerone(this.getEmail());
        System.out.println("Inserire nuova disponibilità ");
        System.out.println("Inserire giorno: ");
        giorno= Integer.parseInt(scanner.nextLine());
        System.out.println("Inserire mese: ");
        mese= Integer.parseInt(scanner.nextLine());
        System.out.println("Inserire anno: ");
        anno= Integer.parseInt(scanner.nextLine());
        do{
            System.out.println("Nuova disponibilità: " +giorno+"/"+mese+"/"+anno);
            System.out.println("Confermare S/N");
            conferma = scanner.nextLine();
        }while( !(conferma.equals("S") || conferma.equals("N")));
        if(conferma.equals("S")){
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.YEAR, anno);
            cal.set(Calendar.MONTH, mese-1);
            cal.set(Calendar.DAY_OF_MONTH, giorno);
            nuovaData = cal.getTime();
            System.out.println(nuovaData);
            DBManager.modificaDisponibilita(this.getEmail(), nuovaData);
            System.out.println("Disponibilità aggiunta ");
        }else System.out.println("Disponibilità non modificata");
    }

    public void eliminaEsperienza(Esperienza esperienzaDaEliminare){
        gestoreEsperienze.rimuoviEsperienza(esperienzaDaEliminare);
    }

    public void proporreTag(){
        gestoreAccount.proponiTag();
    }

}
