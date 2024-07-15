package gameTest.Command;

import gameTest.FactoryMethod.Caselle.Casella;
import gameTest.Observer.Gioco;
import gameTest.Observer.Tabellone;

import java.awt.*;

public class Player {
    private int id;
    private int ultimoLancio;
    private boolean divietoDiSosta=false;
    private int posizione;
    private int ultimaPosizione;
    private Tabellone tabellone;
    private int turniAttesa=0;
    private Color colorePlayer;

    public Player(int id, Tabellone tabellone, Gioco gioco) {
        this.id = id;
        this.posizione=0;
        ultimaPosizione=0;
        this.ultimoLancio=0;
        this.tabellone=tabellone;
        if(id%6==0){
            colorePlayer=Color.BLACK;
        } else if(id%6==1){
            colorePlayer=Color.GRAY;
        } else if(id%6==2){
            colorePlayer=Color.CYAN;
        } else if (id%6==3) {
            colorePlayer=Color.BLUE;
        } else if (id%6==4) {
            colorePlayer=Color.GREEN;
        } else{
            colorePlayer=Color.RED;
        }
    }




    public void turno() {
        if(turniAttesa!=0){
            turniAttesa-=1;
            System.out.println("Il Player "+ getId() + " ha sostato un turno, ne rimangono da sostare:"+ turniAttesa);
        }
        else {
            lanciaDadi();
            int nuovaPosizione = (posizione + ultimoLancio) > 100 ? (tabellone.getSize() - ((posizione + ultimoLancio) - tabellone.getSize())) : posizione + ultimoLancio;
            System.out.println("Il Player " + id + " ha fatto " + ultimoLancio + " e si dirige verso la casella " + nuovaPosizione);
            new Movimento().applicaAzione(this);
            controllaCasellaDiArrivo(nuovaPosizione);
        }
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

    public Color getColorePlayer() {
        return colorePlayer;
    }

    public void setUltimoLancio(int i) {
        ultimoLancio=i;
    }

    public void setTurniAttesa(int turniAttesa) {
        this.turniAttesa = turniAttesa;
    }
}
