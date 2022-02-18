package it.unicam.cs.amministrazione;

import it.unicam.cs.esperienza.Tag;
import it.unicam.cs.storage.DBManager;

import java.util.ArrayList;
import java.util.Scanner;

public class GestoreAmministrazione {
    Scanner input = new Scanner(System.in);
    public void approvareTag(){
        ArrayList<Tag> tagApprovare = DBManager.controlloTagApprovare();
        if(tagApprovare.isEmpty()){
            System.out.println("Non ci sono tag da approvare");
        }else{
            for(int i=1;i<=tagApprovare.size();i++){
                System.out.println(i+" | "+tagApprovare.get(i-1).getName() );
            }
            int choice;
            do {
                do {
                    System.out.println("0 per uscire ");
                    System.out.println("Inserire il numero del tag da approvare");
                    choice = Integer.parseInt(input.nextLine());
                } while (choice < 0 || choice > tagApprovare.size());
                if(choice==0) return;
                else{
                    String conferma;
                    do{
                        System.out.println("Confermare inserimento Tag "+tagApprovare.get(choice-1).getName()+ " S/N");
                        conferma = input.nextLine();
                    }while(!(conferma.equals("S") || conferma.equals("N")));
                    if(conferma.equals("S")) DBManager.aggiungiTag(tagApprovare.get(choice-1));
                    else{
                        System.out.println("Approvazione tag annullata ");
                    }
                }
            }while(choice!=0);
        }
    }
}
