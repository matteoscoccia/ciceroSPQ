package it.unicam.cs;

public class Esperienza {
    private final String id;
    private  String titolo;
    private  String descrizione;
    private final Date data;
    private int postiDisponibili;
    private float prezzo;
    private int postiMinimi;
    private int postiMassimi;
    private final String emaiOrganizzatore;
    private final ArrayList<Tag> tag;

    public String getId(){
        return this.id;
    }

    public String getTitolo() {
        return titolo;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public Date getData() {
        return data;
    }

    public int getPostiDisponibili() {
        return postiDisponibili;
    }

    public float getPrezzo() {
        return prezzo;
    }

    public int getPostiMinimi() {
        return postiMinimi;
    }

    public int getPostiMassimi() {
        return postiMassimi;
    }

    public String getEmaiOrganizzatore() {
        return emaiOrganizzatore;
    }

    public ArrayList<Tag> getTag() {
        return tag;
    }

    public void aggiungiTappa(Tappa tappaDaAggiungere){

    }
    public void aggiungiPartecipante (Partecipante partecipanteDaAggiungere){

    }
    public void setPrezzo(float prezzo){
        this.prezzo=prezzo;
    }
    public void  setPostiDisponibili(int postiDisponibili){
        this.postiDisponibili = postiDisponibili;
    }
    public void setPosti (int postiMinimi,int postiMassimi){
        this.postiMinimi = postiMinimi;
        this.postiMassimi = postiMassimi;
    }
    public void setDescrizione (String descrizione){
        this.descrizione=descrizione;
    }
    public void setTitolo (String titolo){
        this.titolo=titolo;
    }
    public void associaCicerone (Cicerone ciceroneDaAssociare){
        //TODO
    }
    public void associaToponimo (Toponimo toponimoDaAssociare){
        //TODO
    }
}
