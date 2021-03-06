package it.unicam.cs.esperienza;

import it.unicam.cs.amministrazione.Amministrazione;
import it.unicam.cs.pagamento.Pagamento;
import it.unicam.cs.storage.DBManager;
import it.unicam.cs.utente.Associazione;
import it.unicam.cs.utente.Cicerone;
import it.unicam.cs.utente.Turista;
import it.unicam.cs.utente.Utente;

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
        } while(!(conferma.equals("S") || conferma.equals("N")));
        if(conferma.equals("S")){
            Pagamento pagamento = new Pagamento((partecipanti.size() * esperienza.getPrezzo()), turista, esperienza);
            esperienza.setPostiDisponibili(esperienza.getPostiDisponibili()-partecipanti.size());
            System.out.println("\n PAGAMENTO IN CORSO ...");
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
            } catch(Exception e){e.printStackTrace();}
            return nuovaEsperienza;
        }else {
            System.out.println("Esperienza non creata");
            return null;
        }
    }

    private Toponimo visualizzaElencoToponimi() {
        String genitore = "Italia";
        boolean hasChosen = false;

        while(!hasChosen) {
            try {
                ArrayList<String> elencoToponimiDisponibili;
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
                if (sceltaInserita.charAt(0) > 48 && sceltaInserita.charAt(0) < 57) {//Se la scelta ?? un numero allora il toponimo ?? selezionato
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
                e.printStackTrace();
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

    public void rimuoviEsperienza(Esperienza esperienzaDaEliminare){
        if( esperienzaDaEliminare.getPostiMassimi()-esperienzaDaEliminare.getPostiDisponibili()!= 0) {
            System.out.println("Impossibile eliminare esperienza: ci sono persone prenotate");
            return;
        }
        String conferma;
        do{
            System.out.println("Confermare eliminazione esperienza S/N");
            conferma = inputScanner.nextLine();
        }while(!(conferma.equals("S") || conferma.equals("N")));
        if(conferma.equals("S")) DBManager.eliminareEsperienza(esperienzaDaEliminare);
        else System.out.println("Esperienza non eliminata");
    }

    public void condividiEsperienza(Esperienza esperienza){
        String s = "\nCopia e condividi il seguente testo:" +
                "\nPartecipa anche tu a questa esperienza!" +
                "\nTitolo: " + esperienza.getTitolo() +
                "\nDescrizione: " + esperienza.getDescrizione() +
                "\nIdentificativo: " + esperienza.getId();
        System.out.println(s);
    }



    public void ricercaConFiltri(Utente utente){
        String parolaChiave,conferma;
        ArrayList<Esperienza> risultatiRicerca;
        do {
            System.out.println("Inserire parola chiave da ricercare");
            parolaChiave = inputScanner.nextLine();
            System.out.println("\n Vuoi aggiungere dei filtri? S/N");
            conferma = inputScanner.nextLine();
        }while(!(conferma.equals("S") || conferma.equals("N")));
        if(conferma.equals("S")){
            Tag tag = scegliTag(DBManager.listaTag());
            Toponimo toponimo = scegliToponimo(DBManager.listaToponimo());
            Date data = scegliData(DBManager.listaDate());
            risultatiRicerca = DBManager.ricercaConFiltri(tag,toponimo, data,parolaChiave);
            int count = 0;

            for (Esperienza e:
                 risultatiRicerca) {
                System.out.println("Esperienza n. "+(count+1));
                System.out.println("TITOLO: " +e.getTitolo());
                System.out.println("DESCRIZIONE: " +e.getDescrizione());
                System.out.println("DATA: " +e.getData());
                System.out.println("PREZZO: " +e.getPrezzo());
                System.out.println("-------------------------------------------------");
                count++;
            }

            visualizzaEsperienza(risultatiRicerca, utente);

        }else{
            risultatiRicerca = DBManager.ricercaParolaChiave(parolaChiave);
            int count = 0;
            for (Esperienza e:
                    risultatiRicerca) {
                System.out.println("Esperienza n. "+(count+1));
                System.out.println("TITOLO: " +e.getTitolo());
                System.out.println("DESCRIZIONE: " +e.getDescrizione());
                System.out.println("DATA: " +e.getData());
                System.out.println("PREZZO: " +e.getPrezzo());
                System.out.println("-------------------------------------------------");
                count++;
            }

            if (risultatiRicerca.size()>0) visualizzaEsperienza(risultatiRicerca, utente);

        }
    }

    private void visualizzaEsperienza(ArrayList<Esperienza> risultatiRicerca, Utente utente) {
        int scelta;
        try {
            do {
                System.out.println("Quale esperienza vuoi visualizzare? [x = nessuna]");
                scelta = Integer.parseInt(inputScanner.nextLine()) - 1;
            }while (scelta<0 || scelta>=risultatiRicerca.size() );

            Esperienza esperienzaScelta = risultatiRicerca.get(scelta);
            DBManager.visualizzaDettagliEsperienza(esperienzaScelta);
            DBManager.visualizzaTagEsperienza(esperienzaScelta);
            DBManager.visualizzaElencoTappe(esperienzaScelta);

            if(utente.getNome().equals("Amministrazione")){
                Amministrazione amministrazione = new Amministrazione("admin");
                int selezione = 0;
                do {
                    System.out.println("Operazioni disponibili: ");
                    System.out.println("1) Elimina esperienza");
                    System.out.println("2) Torna al menu principale");
                    selezione = Integer.parseInt(inputScanner.nextLine());
                }while(selezione<1 || selezione>2);

                if(selezione == 1){
                    amministrazione.eliminaEsperienza(esperienzaScelta);
                }

            }else if(utente instanceof Cicerone){
                Cicerone cicerone = (Cicerone) utente;
                if(esperienzaScelta.guida.getEmail() != null && esperienzaScelta.guida.getEmail().equals(cicerone.getEmail())) {
                    int selezione = 0;
                    do {
                        System.out.println("Operazioni disponibili: ");
                        System.out.println("1) Elimina esperienza");
                        System.out.println("2) Modifica esperienza");
                        System.out.println("3) Torna al menu principale");
                        selezione = Integer.parseInt(inputScanner.nextLine());
                    }while(selezione<1 || selezione>3);

                    switch (selezione) {
                        case 1 -> cicerone.eliminaEsperienza(esperienzaScelta);
                        case 2 -> modificaEsperienza(esperienzaScelta);
                    }
                }else{
                    System.out.println("Non sei autorizzato ad eliminare o modificare questa esperienza");
                }
            }else if(utente instanceof Associazione){
                Associazione associazione = (Associazione) utente;

                int selezione = 0;
                do {
                    System.out.println("Operazioni disponibili: ");
                    System.out.println("1) Elimina esperienza");
                    System.out.println("2) Modifica esperienza");
                    System.out.println("3) Torna al menu principale");
                    selezione = Integer.parseInt(inputScanner.nextLine());
                }while(selezione<1 || selezione>3);

                switch (selezione) {
                    case 1 -> associazione.eliminaEsperienza(esperienzaScelta);
                    case 2 -> modificaEsperienza(esperienzaScelta);
                }
            }else if(utente instanceof  Turista){
                Turista turista = (Turista) utente;
                int selezione = 0;
                do {
                    System.out.println("Operazioni disponibili: ");
                    System.out.println("1) Condividi esperienza");
                    System.out.println("2) Prenota esperienza");
                    System.out.println("3) Torna al menu principale");
                    selezione = Integer.parseInt(inputScanner.nextLine());
                }while(selezione<1 || selezione>3);

                switch (selezione) {
                    case 1 -> condividiEsperienza(esperienzaScelta);
                    case 2 -> turista.prenotaEsperienza(esperienzaScelta);
                }
            }

        }catch(NumberFormatException e){
            System.out.println("Nessuna scelta");
        }
    }

    private Tag scegliTag(ArrayList<Tag> listaTag){
        for(int i=0;i<listaTag.size();i++){
            System.out.println("" +i+"|" +listaTag.get(i).getName());
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
            System.out.println("" +i+"|" +listaToponimi.get(i).getNome());
        }
        int n;
        do{
            System.out.println("Inserire numero del toponimo desiderato");
            n = Integer.parseInt(inputScanner.nextLine());
        }while(n<0 || n>(listaToponimi.size()-1));
        return listaToponimi.get(n);
    }

    private Date scegliData(ArrayList<Date> listaDate){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); // creo l'oggetto

        for(int i=0;i<listaDate.size();i++){
            System.out.println("" +i+"|" +sdf.format(listaDate.get(i)));
        }
        int n;
        do{
            System.out.println("Inserire numero della data desiderata");
            n = Integer.parseInt(inputScanner.nextLine());
        }while(n<0 || n>(listaDate.size()-1));
        return listaDate.get(n);
    }

    private void modificaEsperienza(Esperienza esperienzaDaModificare){
        System.out.println("-------------------------------------");
        System.out.println("Modifiche disponibili: ");
        System.out.println("1) Modifica titolo");
        System.out.println("2) Modifica descrizione");
        System.out.println("3) Modifica data");
        System.out.println("4) Modifica prezzo");
        System.out.println("5) Aggiungi Tag");
        System.out.println("6) Elimina Tag");
        System.out.println("7) Aggiungi Tappa");
        System.out.println("8) Elimina Tappa");
        System.out.println("0) Torna al menu principale");
        int scelta = 0;
        try{
            scelta = Integer.parseInt(inputScanner.nextLine());
        }catch (NumberFormatException e){
            System.out.println("Nessuna scelta");
            return;
        }
        switch (scelta) {
            case 1 -> modificaTitolo(esperienzaDaModificare);
            case 2 -> modificaDescrizione(esperienzaDaModificare);
            case 3 -> modificaData(esperienzaDaModificare);
            case 4 -> modificaPrezzo(esperienzaDaModificare);
            case 5 -> aggiungiTag(esperienzaDaModificare);
            case 6 -> eliminaTag(esperienzaDaModificare);
            case 7 -> aggiungiTappa(esperienzaDaModificare);
            case 8 -> eliminaTappa(esperienzaDaModificare);
        }
    }


    private void modificaTitolo(Esperienza esperienzaDaModificare) {

        System.out.println("Inserisci nuovo titolo: ");
        String nuovoTitolo = inputScanner.nextLine();
        String conferma;
        do{
            System.out.println("Vuoi confermare '" + nuovoTitolo + "'?[S/N]");
            conferma = inputScanner.nextLine();
        }while (!(conferma.equals("S") || conferma.equals("N")));

        if(conferma.equals("S")){
            DBManager.modificaTitoloEsperienza(esperienzaDaModificare, nuovoTitolo);
            System.out.println("Titolo Modificato");
        }else{
            System.out.println("Titolo non modificato");
        }

    }

    private void modificaDescrizione(Esperienza esperienzaDaModificare) {
        System.out.println("Inserisci nuova descrizione: ");
        String nuovaDescrizione = inputScanner.nextLine();
        String conferma;
        do{
            System.out.println("Vuoi confermare '" + nuovaDescrizione + "'?[S/N]");
            conferma = inputScanner.nextLine();
        }while (!(conferma.equals("S") || conferma.equals("N")));

        if(conferma.equals("S")){
            DBManager.modificaDescrizioneEsperienza(esperienzaDaModificare, nuovaDescrizione);
            System.out.println("Descrizione modificata");
        }else{
            System.out.println("Descrizione non modificata");
        }
    }

    private void modificaData(Esperienza esperienzaDaModificare) {

        System.out.println("Inserisci nuova data: ");
        System.out.println("Nuovo giorno: ");
        int giorno = Integer.parseInt(inputScanner.nextLine());
        System.out.println("Nuovo mese: ");
        int mese = Integer.parseInt(inputScanner.nextLine());
        System.out.println("Nuovo anno: ");
        int anno = Integer.parseInt(inputScanner.nextLine());
        Date nuovaData = new Date(anno-1900, mese-1, giorno);
        String conferma;
        do{
            System.out.println("Vuoi confermare '" + nuovaData + "'?[S/N]");
            conferma = inputScanner.nextLine();
        }while (!(conferma.equals("S") || conferma.equals("N")));

        if(conferma.equals("S")){
            DBManager.modificaDataEsperienza(esperienzaDaModificare, nuovaData);
            System.out.println("Data modificata");
        }else{
            System.out.println("Data non modificata");
        }
    }

    private void modificaPrezzo(Esperienza esperienzaDaModificare) {
        System.out.println("Inserisci nuovo prezzo: ");
        float nuovoPrezzo;
        try{
            nuovoPrezzo = Float.parseFloat(inputScanner.nextLine());
        }catch (NumberFormatException e){
            System.out.println("Impossibile modificare");
            return;
        }
        String conferma;
        do{
            System.out.println("Vuoi confermare '" + nuovoPrezzo + "'?[S/N]");
            conferma = inputScanner.nextLine();
        }while (!(conferma.equals("S") || conferma.equals("N")));

        if(conferma.equals("S")){
            DBManager.modificaPrezzoEsperienza(esperienzaDaModificare, nuovoPrezzo);
            System.out.println("Prezzo modificato");
        }else{
            System.out.println("Prezzo non modificato");
        }

    }

    private void aggiungiTag(Esperienza esperienzaDaModificare) {
        System.out.println("Tag disponibili: ");
        ArrayList<Tag> elencoTag = DBManager.listaTag();//Elenco di tutti i tag presenti nel DB
        ArrayList<Tag> tagEsperienza = DBManager.getTagEsperienza(esperienzaDaModificare);//Elenco dei tag dell'esperienza
        //Li rimuovo dall'elenco dei tag disponibili
        for (Tag tagExp:
             tagEsperienza) {
            elencoTag.removeIf(tagTot -> tagExp.getName().equals(tagTot.getName()));
        }
        for(int i = 1; i<=elencoTag.size(); i++){
            System.out.println(i+") "+elencoTag.get(i-1).getName());
        }
        int scelta;

        System.out.println("Inserire numero tag da aggiungere: ");
        scelta = Integer.parseInt(inputScanner.nextLine());
        String conferma;
        do{
            System.out.println("Confermare l'aggiunta del Tag "+elencoTag.get(scelta-1).getName()+ "?[S/N]");
            conferma = inputScanner.nextLine();
        }while (!(conferma.equals("S")||conferma.equals("N")));

        if(conferma.equals("S")){
            ArrayList<Tag> nuovoElencoTag = (ArrayList<Tag>) tagEsperienza.clone();
            nuovoElencoTag.add(elencoTag.get(scelta-1));
            esperienzaDaModificare.setTag(nuovoElencoTag);
            DBManager.aggiungiTagEsperienza(esperienzaDaModificare, elencoTag.get(scelta-1));
            System.out.println("Tag aggiunto");
        }else{
            System.out.println("Tag non aggiunto");
        }

    }

    private void eliminaTag(Esperienza esperienzaDaModificare) {
        ArrayList<Tag> tagEsperienza = DBManager.getTagEsperienza(esperienzaDaModificare);//Elenco dei tag dell'esperienza
        System.out.println("Quale tag vuoi eliminare?");
        for(int i = 1; i<=tagEsperienza.size(); i++){
            System.out.println(i+") "+tagEsperienza.get(i-1).getName());
        }

        int scelta;

        System.out.println("Inserire numero tag da eliminare: ");
        scelta = Integer.parseInt(inputScanner.nextLine());
        String conferma;
        do{
            System.out.println("Confermare l'eliminazione del Tag "+tagEsperienza.get(scelta-1).getName()+ "?[S/N]");
            conferma = inputScanner.nextLine();
        }while (!(conferma.equals("S")||conferma.equals("N")));

        if(conferma.equals("S")){
            ArrayList<Tag> nuovoElencoTag = (ArrayList<Tag>) tagEsperienza.clone();
            nuovoElencoTag.remove(tagEsperienza.get(scelta-1));
            Tag tagDaEliminare = tagEsperienza.get(scelta-1);
            esperienzaDaModificare.setTag(nuovoElencoTag);
            DBManager.eliminaTagEsperienza(esperienzaDaModificare, tagDaEliminare);
            System.out.println("Tag eliminato");
        }else{
            System.out.println("Tag non eliminato");
        }
    }


    private void aggiungiTappa(Esperienza esperienzaDaModificare) {
        System.out.println("Nome tappa: ");
        String nomeTappa = inputScanner.nextLine();
        System.out.println("Descrizione tappa: ");
        String descrizioneTappa = inputScanner.nextLine();
        System.out.println("Indirizzo tappa: ");
        String indirizoTappa = inputScanner.nextLine();

        String conferma;
        do{
            System.out.println("Confermare inserimento? [S/N]");
            conferma = inputScanner.nextLine();
        }while(!(conferma.equals("S") || conferma.equals("N")));

        if(conferma.equals("S")){
            Tappa tappaDaAggiungere = new Tappa(nomeTappa, descrizioneTappa, indirizoTappa);
            esperienzaDaModificare.aggiungiTappa(tappaDaAggiungere);
            DBManager.aggiungiTappaEsperienza(esperienzaDaModificare, tappaDaAggiungere);
            System.out.println("Tappa aggiunta");
        }else{
            System.out.println("Tappa non aggiunta");
        }

    }


    private void eliminaTappa(Esperienza esperienzaDaModificare) {
        ArrayList<Tappa> tappeEsperienza = DBManager.getTappeEsperienza(esperienzaDaModificare);
        System.out.println("Quale tappa vuoi eliminare?");
        for(int i = 1; i<=tappeEsperienza.size(); i++){
            System.out.println(i+") "+tappeEsperienza.get(i-1).getNome());
        }
        int scelta;

        System.out.println("Inserire numero tappa da eliminare: ");
        scelta = Integer.parseInt(inputScanner.nextLine());
        String conferma;
        do{
            System.out.println("Confermare l'eliminazione della tappa "+tappeEsperienza.get(scelta-1).getNome()+ "?[S/N]");
            conferma = inputScanner.nextLine();
        }while (!(conferma.equals("S")||conferma.equals("N")));

        if(conferma.equals("S")){
            ArrayList<Tappa> nuovoElencoTappe = (ArrayList<Tappa>) tappeEsperienza.clone();
            nuovoElencoTappe.remove(tappeEsperienza.get(scelta-1));
            Tappa tappaDaEliminare = tappeEsperienza.get(scelta-1);
            esperienzaDaModificare.setTappe(nuovoElencoTappe);
            DBManager.eliminaTappaEsperienza(esperienzaDaModificare, tappaDaEliminare);
            System.out.println("Tappa eliminata");
        }else{
            System.out.println("Tappa non eliminata");
        }
    }
}