package gameTest.Observer;

import gameTest.FactoryMethod.Caselle.Casella;

import java.util.HashMap;
import java.util.Map;
public class Tabellone {
    private int righe;
    private int colonne;
    private Map<Integer, Casella> caselle;
    private int size;

    public Tabellone(int righe, int colonne) {
        this.righe = righe;
        this.colonne = colonne;
        size=righe*colonne;
        this.caselle = new HashMap<>();
    }

    public void addCasella(Casella c) {
        caselle.put(c.getNumeroCasella(), c);
    }

    public int getSize(){
        return size;
    }

    public int getRighe() {
        return righe;
    }

    public int getColonne() {
        return colonne;
    }

}
