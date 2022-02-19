package it.unicam.cs.esperienza;

import it.unicam.cs.storage.DBManager;
import it.unicam.cs.utente.Cicerone;

import java.util.ArrayList;
import java.util.Date;

public class Esperienza {
    private final int id;
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
    public Cicerone guida;

    public Esperienza(int id, String titolo, String descrizione, Date data) {
        this.id = id;
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.data = data;
        this.tappe = new ArrayList<>();
    }

    public int getId(){
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
        tappe.add(tappaDaAggiungere);
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
        DBManager.registraGuidaEsperienza(ciceroneDaAssociare, id);
    }

    public void setTag(ArrayList<Tag> listaTag) {
        this.tag = listaTag;
    }

    public void setToponimo(Toponimo toponimo) {
        this.toponimo = toponimo;
    }

    public Toponimo getToponimo() {
        return this.toponimo;
    }

    public void setTappe(ArrayList<Tappa> tappe){
        this.tappe = tappe;
    }
}
