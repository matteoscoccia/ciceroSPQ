package it.unicam.cs;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.UUID;



public class GestoreEsperienze {

    Scanner inputScanner;

    GestoreEsperienze(){
        this.inputScanner = new Scanner(System.in);
    }

    public void prenotaEsperienza(Esperienza esperienza,Turista turista){
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
            System.out.println("\n Confermare esperienza? S/N ");
            conferma = inputScanner.nextLine();
        } while(conferma.equals("S") || conferma.equals("N"));
        if(conferma.equals("S")){
            Pagamento pagamento = new Pagamento((partecipanti.size() * esperienza.getPrezzo()), turista, esperienza);
            System.out.println("\n PAGAMENTO IN CORSO ...");
            System.out.println("\n PAGAMENTO EFFETTUATO CON SUCCESO");
        } else{
            System.out.println("\n ANNULLAMENTO PRENOTAZIONE IN CORSO ... ");
            System.out.println("\n PRENOTAZIONE ANNULLATA");
        }
        //TODO INTERAZIONE CON DB
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
        String id = UUID.randomUUID().toString();

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
            //TODO INSERIRE ESPERIENZA NEL DB
            //TODO INSERIRE TAPPE NEL DB
            //TODO INSERIRE TAG NEL DB
            // TODO correggere: inserire emailGuida
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
        ArrayList<Tag> listaTag = new ArrayList<>();
        boolean continuaInserimento;
        do{
            System.out.println("\nNome tag: ");
            String nomeTag = inputScanner.nextLine();
            Tag nuovoTag = new Tag(nomeTag);
            listaTag.add(nuovoTag);
            String choice;
            do{
                System.out.println("\n\nContinuare inserimento? S/N");
                choice = inputScanner.nextLine();
            } while(!(choice.equals("S") || choice.equals("N")));
            continuaInserimento = !choice.equals("N");
        } while(continuaInserimento);
        nuovaEsperienza.setTag(listaTag);
        return listaTag;
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
}