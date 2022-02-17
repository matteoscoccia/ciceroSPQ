package it.unicam.cs.esperienza;

import it.unicam.cs.amministrazione.Amministrazione;
import it.unicam.cs.pagamento.Pagamento;
import it.unicam.cs.storage.DBManager;
import it.unicam.cs.utente.Turista;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;


public class GestoreEsperienze {

    Scanner inputScanner;

    public GestoreEsperienze(){
        this.inputScanner = new Scanner(System.in);
    }

    public void prenotaEsperienza(Esperienza esperienza, Turista turista){
        int postiDisponibili = esperienza.getPostiDisponibili();
        int numPartecipanti;
        ArrayList<Partecipante> partecipanti = new ArrayList<>();
        do{
            System.out.println("\n Posti disponibili per questa esperienza : " +postiDisponibili);
            System.out.println("\n Inserire numero di partecipanti");
            numPartecipanti = Integer.parseInt(inputScanner.nextLine());
        } while(numPartecipanti<1 || numPartecipanti>postiDisponibili);
        while (numPartecipanti>0){
            System.out.println("\n Inserire nome partecipante \n");
            String nome = inputScanner.nextLine();
            System.out.println("\n Inserire cognome partecipante \n");
            String cognome = inputScanner.nextLine();
            System.out.println("\n Inserire e-mail partecipante \n");
            String email = inputScanner.nextLine();
            partecipanti.add(new Partecipante(nome,cognome,email));
            numPartecipanti--;
        }
        this.creaRiepilogoEsperienza(esperienza,partecipanti.size());
        String conferma;
        do{
            System.out.println("\n Confermare prenotazione? S/N ");
            conferma = inputScanner.nextLine();
        } while(conferma.equals("S") || conferma.equals("N"));
        if(conferma.equals("S")){
            Pagamento pagamento = new Pagamento((partecipanti.size() * esperienza.getPrezzo()), turista, esperienza);
            esperienza.setPostiDisponibili(esperienza.getPostiDisponibili()-partecipanti.size());
            System.out.println("\n PAGAMENTO IN CORSO ...");
            //TODO CONTROLLARE CHE L'INTERAZIONE COL DB FUNZIONI
            DBManager.registraPagamento(pagamento);
            DBManager.aggiornaPostiEsperienza(esperienza);
            DBManager.registraPartecipanti(esperienza, partecipanti);
            System.out.println("\n PAGAMENTO EFFETTUATO CON SUCCESO");
        } else{
            System.out.println("\n ANNULLAMENTO PRENOTAZIONE IN CORSO ... ");
            System.out.println("\n PRENOTAZIONE ANNULLATA");
        }
    }

    public void creaRiepilogoEsperienza(Esperienza esperienza, int numPartecipanti){
        System.out.println("\n------------------------------------------------------------------");
        System.out.println("\n Titolo : " +esperienza.getTitolo());
        System.out.println("\n Descrizione : " +esperienza.getDescrizione());
        System.out.println("\n Prezzo : " +esperienza.getPrezzo());
        System.out.println("\n Totale euro : " +(numPartecipanti*esperienza.getPrezzo()));
        System.out.println("\n-------------------------------------------------------------------");
    }

    public void creaRiepilogo(Esperienza esperienza, String titolo, String descrizione, int prezzo){
        // TODO (titolo, descrizione e prezzo non servono se si prendono i dati di esperienza dal DB)
    }

    public Esperienza aggiungiEsperienza(){
        System.out.println("\nTitolo esperienza: ");
        String titolo = inputScanner.nextLine();
        System.out.println("\nDescrizione esperienza: ");
        String descrizione = inputScanner.nextLine();
        System.out.println("\nData esperienza: (GG/MM/AAAA)");
        String tempDate = inputScanner.nextLine();
        Date data = new Date();
        try {
            data = new SimpleDateFormat("dd/MM/yyyy").parse(tempDate);
        }catch (ParseException e){
            System.out.println("\nData non corretta");
        }
        int id = ThreadLocalRandom.current().nextInt(10000, 500000);//Genera numero casuale

        Esperienza nuovaEsperienza = new Esperienza(id, titolo, descrizione, data);

        System.out.println("\n\nInserimento tappe: \n");
        ArrayList<Tappa> tappeEsperienza = inserimentoTappe(nuovaEsperienza);

        System.out.println("\nInserimento tag: \n");
        ArrayList<Tag> tagEsperienza = inserimentoTag(nuovaEsperienza);

        Toponimo toponimo = visualizzaElencoToponimi();
        nuovaEsperienza.setToponimo(toponimo);

        System.out.println("\nInserire posti minimi: ");
        int postiMinimi = Integer.parseInt(inputScanner.nextLine());

        System.out.println("\nInserire posti massimi: ");
        int postiMassimi = Integer.parseInt(inputScanner.nextLine());

        nuovaEsperienza.setPosti(postiMinimi, postiMassimi);

        nuovaEsperienza.setPostiDisponibili(postiMassimi);

        System.out.println("\nPrezzo esperienza: ");
        float prezzoEsperienza = Float.parseFloat(inputScanner.nextLine());
        nuovaEsperienza.setPrezzo(prezzoEsperienza);

        String conferma;
        do{
            System.out.println("\n\nConfermare inserimento? S/N");
            conferma = inputScanner.nextLine();
        }while(!(conferma.equals("S") || conferma.equals("N")));

        if(conferma.equals("S")) {
            try {
                DBManager.registraEsperienza(nuovaEsperienza);
                DBManager.registraTappe(nuovaEsperienza, tappeEsperienza);
                DBManager.registraTag(nuovaEsperienza, tagEsperienza);
            } catch(Exception e){System.out.println(e);}
            return nuovaEsperienza;
        }else {
            System.out.println("Esperienza non creata");
            return null;
        }
    }

    private Toponimo visualizzaElencoToponimi() {
        //TODO funziona ma andrebbe gestito se un toponimo non ha figli (magari stampare solamente se stesso)
        String genitore = "Italia";
        boolean hasChosen = false;

        while(!hasChosen) {
            try {
                ArrayList<String> elencoToponimiDisponibili = new ArrayList<>();
                elencoToponimiDisponibili = DBManager.selezionaToponimiFigli(genitore);
                System.out.println("\nToponimi collegati a " + genitore + ":");
                int count = 1;
                for (String toponimo :
                        elencoToponimiDisponibili) {
                    System.out.println(count + ") " + toponimo);
                    count++;
                }
                System.out.println("Per scegliere un toponimo inserire il numero corrispondente");
                System.out.println("Per espandere un toponimo inserire '+' seguito dal numero corrispondente");
                String sceltaInserita = inputScanner.nextLine();
                int toponimoScelto;
                if (sceltaInserita.charAt(0) > 48 && sceltaInserita.charAt(0) < 57) {//Se la scelta è un numero allora il toponimo è selezionato
                    toponimoScelto = Integer.parseInt(sceltaInserita) - 1;
                    System.out.println("Selezionato: " + elencoToponimiDisponibili.get(toponimoScelto));
                    hasChosen = true;
                    return new Toponimo(elencoToponimiDisponibili.get(toponimoScelto));//Viene costruito e restituito il toponimo selezionato
                } else if (sceltaInserita.startsWith("+")) {//Il toponimo va espanso
                    sceltaInserita = sceltaInserita.substring(1);//Rimuovo il "+"
                    toponimoScelto = Integer.parseInt(sceltaInserita) - 1;
                    genitore = elencoToponimiDisponibili.get(toponimoScelto); //Inserisco il nuovo toponimo di cui cercare i figli
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        return null;
    }


    public ArrayList<Tappa> inserimentoTappe(Esperienza nuovaEsperienza){
        ArrayList<Tappa> tappeEsperienza = new ArrayList<>();
        boolean continuaInserimento;
        do{
            System.out.println("\nNome tappa: ");
            String nomeTappa = inputScanner.nextLine();
            System.out.println("\nDescrizione tappa: ");
            String descrizioneTappa = inputScanner.nextLine();
            System.out.println("\nIndirizzo: ");
            String indirizzoTappa = inputScanner.nextLine();
            Tappa nuovaTappa = new Tappa(nomeTappa, descrizioneTappa, indirizzoTappa);
            nuovaEsperienza.aggiungiTappa(nuovaTappa);
            tappeEsperienza.add(nuovaTappa);
            String choice;
            do{
                System.out.println("\n\nContinuare inserimento? S/N");
                choice = inputScanner.nextLine();
            }while(!(choice.equals("S") || choice.equals("N")));
            continuaInserimento = !choice.equals("N");

        }while(continuaInserimento);
        return tappeEsperienza; //Elenco delle tappe da salvare nel database
    }

    private ArrayList<Tag> inserimentoTag(Esperienza nuovaEsperienza) {
        ArrayList<Tag> listaTag = DBManager.leggiTagRegistrati();//Preleva i tag dal DB
        ArrayList<Tag> listaTagEsperienza = new ArrayList<>();

        boolean continuaInserimento;
        int count;
        do{
            count = 1;
            System.out.println("\nTag Disponibili:");
            for (Tag t:
                 listaTag) {
                System.out.println(count+")"+t.getName());
                count++;
            }
            System.out.println("Inserire numero tag: ");
            int scelta = Integer.parseInt(inputScanner.nextLine());
            listaTagEsperienza.add(listaTag.get(scelta-1));
            String choice;
            do{
                System.out.println("\n\nContinuare inserimento? S/N");
                choice = inputScanner.nextLine();
            } while(!(choice.equals("S") || choice.equals("N")));
            continuaInserimento = !choice.equals("N");
        } while(continuaInserimento);
        nuovaEsperienza.setTag(listaTagEsperienza);
        return listaTagEsperienza;
    }

    public void rimuoviEsperienza(Esperienza esperienzaDaEliminare, Amministrazione amministrazione){
        if( esperienzaDaEliminare.getPostiMassimi()-esperienzaDaEliminare.getPostiDisponibili()!= 0) {
            System.out.println("Impossibile eliminare esperienza");
            return;
        }
        String conferma;
        do{
            System.out.println("Confermare eliminazione esperienza S/N");
            conferma = inputScanner.nextLine();
        }while(!(conferma.equals("S") || conferma.equals("N")));
        if(conferma.equals("S")) DBManager.eliminareEsperienza(esperienzaDaEliminare);
        else System.out.println("Esperienzia non eliminata");
    }

    public void condividiEsperienza(Esperienza esperienza){
        String s = "\nCopia e condividi il seguente testo:" +
                "\nPartecipa anche tu a questa esperienza!" +
                "\nTitolo: " + esperienza.getTitolo() +
                "\nDescrizione: " + esperienza.getDescrizione() +
                "\nIdentificativo: " + esperienza.getId();
        System.out.println(s);
    }



    public void ricercaConFiltri(){
        String parolaChiave,conferma;
        do {
            System.out.println("Inserire parola chiave da ricercare");
            parolaChiave = inputScanner.nextLine();
            System.out.println("\n Vuoi ricerca con ricerca con filtri? S/N");
            conferma = inputScanner.nextLine();
        }while(!(conferma.equals("S") || conferma.equals("N")));
        if(conferma.equals("S")){
            Tag tag = scegliTag(DBManager.listaTag());
            Toponimo toponimo = scegliToponimo(DBManager.listaToponimo());
            Date data = scegliData(DBManager.listaDate());
            DBManager.ricercaConFiltri(tag,toponimo, (java.sql.Date) data,parolaChiave);
        }else{
            DBManager.ricercaParolaChiave(parolaChiave);
        }


    }
    private Tag scegliTag(ArrayList<Tag> listaTag){
        for(int i=0;i<listaTag.size();i++){
            System.out.println("" +i+"|" +listaTag.get(i));
        }
        int n;
        do{
            System.out.println("Inserire numero del tag desiderato");
            n = Integer.parseInt(inputScanner.nextLine());
        }while(n<0 || n>(listaTag.size()-1));
        return listaTag.get(n);
    }
    private Toponimo scegliToponimo(ArrayList<Toponimo> listaToponimi){
        for(int i=0;i<listaToponimi.size();i++){
            System.out.println("" +i+"|" +listaToponimi.get(i));
        }
        int n;
        do{
            System.out.println("Inserire numero del tag desiderato");
            n = Integer.parseInt(inputScanner.nextLine());
        }while(n<0 || n>(listaToponimi.size()-1));
        return listaToponimi.get(n);
    }
    private Date scegliData(ArrayList<Date> listaDate){
        for(int i=0;i<listaDate.size();i++){
            System.out.println("" +i+"|" +listaDate.get(i));
        }
        int n;
        do{
            System.out.println("Inserire numero del tag desiderato");
            n = Integer.parseInt(inputScanner.nextLine());
        }while(n<0 || n>(listaDate.size()-1));
        return listaDate.get(n);
    }
}