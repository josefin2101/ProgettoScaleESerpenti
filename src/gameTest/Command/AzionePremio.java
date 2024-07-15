package gameTest.Command;

import gameTest.Enumerations.TipoPremio;

public class AzionePremio implements Azione{
    private final TipoPremio tipoPremio;


    public AzionePremio(TipoPremio tipoPremio) {
        this.tipoPremio = tipoPremio;
    }

    @Override
    public void applicaAzione(Player player) {
        if (tipoPremio.equals(TipoPremio.dadi)){
            System.out.println("Il Player "+player.getId()+" rilancia i dadi");
            player.lanciaDadi();
            player.controllaCasellaDiArrivo(player.getPosizione()+ player.getUltimoLancio());
        }else {
            System.out.println("Il Player "+player.getId()+" rimbalza");
            new Movimento().applicaAzione(player);
            player.controllaCasellaDiArrivo(player.getPosizione());
        }
        new Movimento().applicaAzione(player);
    }
}
