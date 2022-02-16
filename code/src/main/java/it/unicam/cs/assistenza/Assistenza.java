package it.unicam.cs.assistenza;

import it.unicam.cs.utente.Utente;

import java.util.Scanner;

public class Assistenza {

    public static void getAssistenza(Utente utente){
        System.out.println("""
                Seleziona una categoria: [1-2-3-4]
                1) Non riesco ad accedere
                2) La guida non si è comportata in modo corretto
                3) Non so come prenotare un'esperienza
                4) Altro""");
        Scanner scanner = new Scanner(System.in);
        int categoria = scanner.nextInt();
        while(categoria > 4 || categoria < 1){
            System.out.println("Numero non valido. Inserisci [1-2-3-4]:\n");
            categoria = scanner.nextInt();
        }
        System.out.println("Descrivi il tuo problema:\n");
        String problema = scanner.nextLine();
        System.out.println(
                "Riepilogo del form:"
                +"\nCategoria: "+categoria
                +"\nDescrizione del problema: "+problema
                +"\nVuoi inoltrare la richiesta? [SI/NO]");
        if(scanner.nextLine().equals("SI"))
            inoltraRichiesta(utente.getEmail(), categoria, problema);
        else if(scanner.nextLine().equals("NO"))
            System.out.println("Richiesta annullata");
    }

    private static void inoltraRichiesta(String emailUtente, int categoria, String problema){
        // inoltro di un file di testo con server mail
        System.out.println("Richiesta di assistenza inoltrata!");
    }

}
