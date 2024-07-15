package Command;

public class  AzioneSerpente implements Azione {
        private final int arrivo;

        public AzioneSerpente(int arrivo) {
            this.arrivo = arrivo;
        }

        @Override
        public void applicaAzione(Player player) {
            new Movimento(true, arrivo).applicaAzione(player);
            System.out.println("Il Player "+player.getId()+" è scivolato sul serpente fino alla casella:" + arrivo);
            player.controllaCasellaDiArrivo(arrivo);
        }

}
