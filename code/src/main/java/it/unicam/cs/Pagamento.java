package it.unicam.cs;

public class Pagamento {

    private final float importo;
    private final Turista turista;
    private final Esperienza esperienza;

    Pagamento(float importo, Turista turista, Esperienza esperienza){
        this.importo = importo;
        this.turista = turista;
        this.esperienza = esperienza;
    }

    // todo add other methods to complete a payment

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
