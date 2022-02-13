package it.unicam.cs;

import java.util.Date;
import java.util.Scanner;

import static it.unicam.cs.App.gestoreEsperienze;

public class Cicerone extends Utente{

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
    }

    public void visualizzaProfilo(){
        System.out.println("Nome : " +this.getNome());
        System.out.println("Cognome : " +this.getCognome());
        System.out.println("Email : " +this.getEmail());
    }

    public void modificaDisponibilità(){
        String conferma;
        Date nuovaData;
        int anno,mese,giorno;
        Scanner scanner = new Scanner(System.in);
        System.out.println("La tua attuale disponibilità è : " +DBManager.visualizzaDisponibilitaCicerone(this.getEmail()));
        System.out.println("Inserire nuova disponibilità ");
        System.out.println("Inserire giorno ");
        giorno= Integer.parseInt(scanner.nextLine());
        System.out.println("Inserire mese ");
        mese= Integer.parseInt(scanner.nextLine());
        System.out.println("Inserire anno ");
        anno= Integer.parseInt(scanner.nextLine());
        do{
            System.out.println("Nuova disponibilità: " +giorno+"/"+mese+"/"+anno);
            System.out.println("Confermare S/N");
            conferma = scanner.nextLine();
        }while( !(conferma.equals("S") || conferma.equals("N")));
        if(conferma.equals("S")){
            //TODO VEDERE ERRORE CHE IN REALTA NON SEMBRA ERRORE
            nuovaData = new Date(anno,mese,giorno);
            DBManager.modoficaDisponibilità(this.getEmail(), (java.sql.Date) nuovaData);
            System.out.println("Disponibilità modificata ");
        }else System.out.println("Disponibilità non modificata");
    }
}
