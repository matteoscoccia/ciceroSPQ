package it.unicam.cs;

import java.util.ArrayList;
import java.util.Date;

public class Esperienza {
    private final String id;
    private  String titolo;
    private  String descrizione;
    private Date data;
    private int postiDisponibili;
    private float prezzo;
    private int postiMinimi;
    private int postiMassimi;
    private String emailOrganizzatore;
    private ArrayList<Tag> tag;
    private ArrayList<Partecipante> partecipanti;
    private Toponimo toponimo;
    private ArrayList<Tappa> tappe;
    private Cicerone guida;

    public Esperienza(String id, String titolo, String descrizione, Date data) {
        this.id = id;
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.data = data;
    }

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

    public String getEmailOrganizzatore() {
        return emailOrganizzatore;
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
        this.guida = ciceroneDaAssociare;
    }
    public void associaToponimo (Toponimo toponimoDaAssociare){
        //TODO
    }

    public void setTag(ArrayList<Tag> listaTag) {
        this.tag = listaTag;
    }

    public void setToponimo(Toponimo toponimo) {
        this.toponimo = toponimo;
    }

}
