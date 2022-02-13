package it.unicam.cs;

import java.util.ArrayList;

public class Toponimo {

    private String nome;
    private ArrayList<Toponimo> children;

    public Toponimo(String s) {
        this.nome = s;
    }

    public String getNome() {
        return nome;
    }
}
