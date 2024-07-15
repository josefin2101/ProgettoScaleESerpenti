package DTO;

import java.io.Serializable;

public class ConfigurazioneGioco implements Serializable {
    private int nRighe;
    private int nColonne;
    private int numGiocatori;
    private int nScale;
    private int nSerpenti;
    private int nSpeciali;
    private boolean doppio;
    private boolean manuale;
    private boolean automatico;

    public ConfigurazioneGioco(int nRighe, int nColonne, int numGiocatori, int nScale, int nSerpenti, int nSpeciali, boolean doppio, boolean manuale, boolean automatico) {
        this.nRighe = nRighe;
        this.nColonne = nColonne;
        this.numGiocatori = numGiocatori;
        this.nScale = nScale;
        this.nSerpenti = nSerpenti;
        this.nSpeciali = nSpeciali;
        this.doppio = doppio;
        this.manuale = manuale;
        this.automatico=automatico;
    }

    public int getNRighe() { return nRighe; }

    public int getNColonne() { return nColonne; }

    public int getNumGiocatori() { return numGiocatori; }

    public int getNScale() { return nScale; }

    public int getNSerpenti() { return nSerpenti; }

    public int getNSpeciali() { return nSpeciali; }

    public boolean isDoppio() { return doppio; }

    public boolean isManuale() { return manuale; }

    public boolean isAutomatico() {
        return automatico;
    }
}

