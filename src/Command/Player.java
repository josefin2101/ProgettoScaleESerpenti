package Command;

import FactoryMethod.*;
import FactoryMethod.Caselle.Casella;


public class Player {
    private int id;
    private int ultimoLancio;
    private boolean divietoDiSosta=false;
    private int posizione;
    private int ultimaPosizione;
    private Tabellone tabellone;
    private int turniAttesa=0;

    public Player(int id, Tabellone tabellone) {
        this.id = id;
        this.posizione=0;
        ultimaPosizione=0;
        this.ultimoLancio=0;
        this.tabellone=tabellone;

    }






    public void controllaCasellaDiArrivo(int posizione){
        if(Gioco.getCaselleSpeciali().containsKey(posizione)) {
            Casella c = Gioco.getCaselleSpeciali().get(posizione);
            c.applicaAzione(this);
        }
    }



    public int getId() {
        return id;
    }

    public int getPosizione() {
        return posizione;
    }

    public void setPosizione(int posizione) {
        this.posizione = posizione;
    }

    public void setUltimaPosizione(int ultimaPosizione) {
        this.ultimaPosizione = ultimaPosizione;
    }

    public void lanciaDadi() {
       new LanciaDadi().applicaAzione(this);
    }

    public int getUltimoLancio() {
        return ultimoLancio;
    }

    public Tabellone getTabellone() {
        return tabellone;
    }

    public void conservaDivieto() {
        divietoDiSosta=true;
    }

    public boolean usaDivietoDiSosta(){
        if (divietoDiSosta) {
            divietoDiSosta = false;
            return true;
        }
        return false;

    }


    public void setUltimoLancio(int i) {
        ultimoLancio=i;
    }

    public void setTurniAttesa(int turniAttesa) {
        this.turniAttesa = turniAttesa;
    }
}
