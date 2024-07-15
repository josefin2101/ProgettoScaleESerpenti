package gameTest.Command;

public class AzioneScala implements Azione {
    private final int arrivo;

    public AzioneScala(int arrivo) {
        this.arrivo = arrivo;
    }

    @Override
    public void applicaAzione(Player player) {
        new Movimento(true, arrivo).applicaAzione(player);
        System.out.println("Il Player "+player.getId()+" ha preso la scala fino alla casella:" + arrivo);
        player.controllaCasellaDiArrivo(arrivo);
    }
}