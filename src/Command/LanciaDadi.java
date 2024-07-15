package Command;

import java.util.Random;

public class LanciaDadi implements Azione{


    @Override
    public void applicaAzione(Player player) {
        player.setUltimoLancio(0);
        int primoDado=new Random().nextInt(6)+1;
        int ultimoLancio=player.getUltimoLancio();
        ultimoLancio+=primoDado;
        if(Gioco.isDoppioDado() && player.getPosizione()<=player.getTabellone().getSize()-6){
            int secondoDado=new Random().nextInt(6)+1;
            ultimoLancio+=secondoDado;
        }
        while(ultimoLancio%12==0){
            System.out.println("Il Player "+player.getId()+" ha fatto 2 volte 6. Rilancia i dadi!");
            player.setUltimoLancio(ultimoLancio);
            int nuovaPos = player.getPosizione() + player.getUltimoLancio();
            if (nuovaPos > player.getTabellone().getSize())
                nuovaPos = player.getTabellone().getSize() - (nuovaPos - player.getTabellone().getSize());
            new Movimento().applicaAzione(player);
            player.controllaCasellaDiArrivo(nuovaPos);
            player.setUltimoLancio(0);
            primoDado=new Random().nextInt(1, 7);
            int secondoDado=new Random().nextInt(1, 7);
            ultimoLancio+=primoDado+secondoDado;
        }
        player.setUltimoLancio(ultimoLancio);
    }
}