package it.unicam.cs;

public class GestoreToponimi {

    /**
     * Controlla se il genitore è esistente nel DB
     * @param toponimoDaControllare toponimo da controllare
     * @return true se il toponimo esiste nel DB, false altrimenti
     */
    public boolean controllaToponimoGenitore(String toponimoDaControllare){
        //todo implements
        return false;
    }

    /**
     * Aggiunge un nuovo toponimo al DB
     * @param toponimoFiglio toponimo figlio
     * @param toponimoGenitore toponimo genitore
     */
    public void registraToponimo(String toponimoFiglio, String toponimoGenitore){
        //todo implements
    }

    /**
     * Controlla che il toponimo esista nel DB
     * @param toponimoDaEliminare toponimo da eliminare
     * @return true se il toponimo esiste nel DB, false altrimenti
     */
    public boolean controllaEsistenzaToponimo(String toponimoDaEliminare){
        //todo implement
        return false;
    }

    /**
     * Controlla che il toponimo in input non abbia figli
     * @param toponimoDaEliminare toponimo da eliminare
     * @return true se ha figli, false altrimenti
     */
    public boolean controllaEsistenzaFigli(String toponimoDaEliminare){
        //todo implement
        return false;
    }

}
