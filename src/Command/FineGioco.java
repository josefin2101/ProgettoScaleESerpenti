package Command;

import Observer.Gioco;

public class FineGioco implements Azione{

    @Override
    public void applicaAzione(Player player) {
        Gioco.setFineGioco();
        System.out.println("Il Player "+player.getId()+" ha vinto la partita!");
    }
}