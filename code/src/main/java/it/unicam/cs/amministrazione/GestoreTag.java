package it.unicam.cs.amministrazione;

import it.unicam.cs.esperienza.Tag;
import it.unicam.cs.storage.DBManager;

import java.util.ArrayList;
import java.util.Scanner;

public class GestoreTag {

    /**
     * Controlla se il tag è gia registrato nel DB
     * @param tagDaControllare tag da controllare
     * @return true se il tag è gia registrato, false altrimenti
     */
    public static boolean controlloTag(Tag tagDaControllare){
        return DBManager.controlloTag(tagDaControllare);
    }

    public static void aggiungiNuovoTag(Tag tag){
        DBManager.aggiungiTag(tag);
    }

    public static void rimuoviTagDaApprovare(Tag tag){
        DBManager.rimuoviTagDaApprovare(tag);
    }

    public static ArrayList<Tag> getTagDaApprovare(){
        return DBManager.controlloTagApprovare();
    }

    public static void proponiNuovoTag(Tag tagDaProporre){
        DBManager.proponiNuovoTag(tagDaProporre);
    }

    public static void proponiTag(){
        Scanner input = new Scanner(System.in);
        System.out.println("Inserire il tag che si vuole proporre");
        String conferma;
        String nomeTag = input.nextLine();
        Tag tag = new Tag(nomeTag);
        if(!controlloTag(tag)){
            do {
                System.out.println("Confermare il tag S/N " + tag.getName());
                conferma = input.nextLine();
            }while(!(conferma.equals("S") || conferma.equals("N")));
            if(conferma.equals("S")){
                proponiNuovoTag(tag);
                System.out.println("TAG IN ATTESA DI APPROVAZIONE ");
            }
        }else{
            System.out.println("Tag già presente ");
        }

    }

}
