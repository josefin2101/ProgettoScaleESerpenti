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

    public int getId() {
        return id;
    }


    public void turno() {
        if(turniAttesa!=0){
            turniAttesa-=1;
        }
        else {
            lanciaDadi();
            int nuovaPosizione = (posizione + ultimoLancio) > 100 ? (tabellone.getSize() - ((posizione + ultimoLancio) - tabellone.getSize())) : posizione + ultimoLancio;
            System.out.println("Il Player " + id + " ha fatto " + ultimoLancio + " e si dirige verso la casella " + nuovaPosizione);
            new Azione.Movimento().applica(this);
            controllaCasellaDiArrivo(posizione);
        }
    }

    public void controllaCasellaDiArrivo(int posizione){
        if(Gioco.getCaselleSpeciali().containsKey(posizione)) {
            Casella c = Gioco.getCaselleSpeciali().get(posizione);
            c.applyAction(this);
        }
    }



    public void attendiTurni(int i) {
        setTurniAttesa(i);
        if(divietoDiSosta){
            setTurniAttesa(0);
            divietoDiSosta=false;
        }
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
       new Azione.LanciaDadi().applica(this);
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

    public void setUltimoLancio(int i) {
        ultimoLancio=i;
    }

    public void setTurniAttesa(int turniAttesa) {
        this.turniAttesa = turniAttesa;
    }
}
