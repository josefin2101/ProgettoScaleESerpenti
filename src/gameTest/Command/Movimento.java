package gameTest.Command;

public class Movimento implements Azione{
    private boolean movimentoSpeciale;
    private int arrivo;

        public Movimento() {
        movimentoSpeciale = false;
        arrivo = 0;
    }
        public Movimento(boolean bool, int arrivo){
        movimentoSpeciale=bool;
        this.arrivo=arrivo;
    }


    @Override
    public void applicaAzione(Player player) {
        int nuovaPosizione;
        if(!movimentoSpeciale) {
            nuovaPosizione = player.getPosizione() + player.getUltimoLancio();
            if (nuovaPosizione > player.getTabellone().getSize()) {
                nuovaPosizione = player.getTabellone().getSize() - (nuovaPosizione - player.getTabellone().getSize());
            }

        }else {
            nuovaPosizione=arrivo;
        }
        player.setUltimaPosizione(player.getPosizione());
        player.setPosizione(nuovaPosizione);
        System.out.println("Il Player " + player.getId() + " si trova ora sulla casella " + player.getPosizione());
    }

}