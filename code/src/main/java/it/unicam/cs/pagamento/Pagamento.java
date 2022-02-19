package it.unicam.cs.pagamento;

import it.unicam.cs.esperienza.Esperienza;
import it.unicam.cs.utente.Turista;

public class Pagamento {

    private final float importo;
    private final Turista turista;
    private final Esperienza esperienza;

    public Pagamento(float importo, Turista turista, Esperienza esperienza){
        this.importo = importo;
        this.turista = turista;
        this.esperienza = esperienza;
    }

    public float getImporto(){
       return this.importo;
    }

    public Turista getTurista(){
        return this.turista;
    }

    public Esperienza getEsperienza(){
        return this.esperienza;
    }

}
