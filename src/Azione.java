import java.util.Random;

public interface Azione {
    void applica(Player player);

    class AzioneScala implements Azione {
        private int arrivo;

        public AzioneScala(int arrivo) {
            this.arrivo = arrivo;
        }

        @Override
        public void applica(Player player) {
            new Movimento(true, arrivo).applica(player);
            System.out.println("Il Player "+player.getId()+" ha preso la scala fino alla casella:" + arrivo);
        }
    }

    class AzioneSerpente implements Azione {
        private int arrivo;

        public AzioneSerpente(int arrivo) {
            this.arrivo = arrivo;
        }

        @Override
        public void applica(Player player) {
            new Movimento(true, arrivo).applica(player);
            System.out.println("Il Player "+player.getId()+" è scivolato sul serpente fino alla casella:" + arrivo);
        }
    }

    class AzioneSosta implements Azione {

        private TipoSosta tipoSosta;

        public AzioneSosta(TipoSosta tipo) {
            tipoSosta=tipo;
        }

        @Override
        public void applica(Player player) {
            if(tipoSosta.equals(TipoSosta.locanda)){
                player.attendiTurni(3);
                System.out.println("Il Player "+player.getId()+" è appena arrivato in una locanda, attende 3 turni");
            }
            else{
                player.attendiTurni(1);
                System.out.println("Il Player si siede su una panchina, attende 1 turno");
            }
        }
    }

    class AzionePremio implements Azione{
        private TipoPremio tipoPremio;


        public AzionePremio(TipoPremio tipoPremio) {
            this.tipoPremio = tipoPremio;
        }

        @Override
        public void applica(Player player) {
            if (tipoPremio.equals(TipoPremio.dadi)){
                player.lanciaDadi();
                System.out.println("Il Player "+player.getId()+" è appena arrivato in una casella premio, rilancia i dadi");
            }
            new Movimento().applica(player);
        }
    }

    class AzionePesca implements Azione{
        private TipoCarta carta;

        public AzionePesca(TipoCarta carta){
            this.carta=carta;
        }

        @Override
        public void applica(Player player) {
            System.out.println("Il Player "+player.getId()+" è appena arrivato in una casella Pesca, pesca una carta!");
            if(carta.equals(TipoCarta.panchina))
                new AzioneSosta(TipoSosta.panchina).applica(player);
            else if(carta.equals(TipoCarta.locanda))
                new AzioneSosta(TipoSosta.locanda).applica(player);
            else if(carta.equals(TipoCarta.dadi))
                new AzionePremio(TipoPremio.dadi).applica(player);
            else if(carta.equals(TipoCarta.molla))
                new AzionePremio(TipoPremio.molla).applica(player);
            else{
                player.conservaDivieto();
                System.out.println("Il Player "+player.getId()+" ha trovato una carta divieto, che fortuna!");
            }
        }
    }

    class Movimento implements Azione{
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
        public void applica(Player player) {
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

        }

    }

    class LanciaDadi implements Azione{


        @Override
        public void applica(Player player) {
            player.setUltimoLancio(0);
            int primoDado=new Random().nextInt(1, 7);
            int ultimoLancio=player.getUltimoLancio();
            ultimoLancio+=primoDado;
            if(Gioco.isDoppioDado() && player.getPosizione()<=player.getTabellone().getSize()-6){
                int secondoDado=new Random().nextInt(1, 7);
                ultimoLancio+=secondoDado;
            }
            while(ultimoLancio%12==0){
                player.setUltimoLancio(ultimoLancio);
                int nuovaPos = player.getPosizione() + player.getUltimoLancio();
                if (nuovaPos > player.getTabellone().getSize()) {
                    nuovaPos = player.getTabellone().getSize() - (nuovaPos - player.getTabellone().getSize());
                }
                new Movimento().applica(player);
                player.controllaCasellaDiArrivo(nuovaPos);
                player.setUltimoLancio(0);
                primoDado=new Random().nextInt(1, 7);
                int secondoDado=new Random().nextInt(1, 7);
                ultimoLancio+=primoDado+secondoDado;
            }
            player.setUltimoLancio(ultimoLancio);
        }
    }

    class NessunAzione implements Azione {

        @Override
        public void applica(Player player) {

        }
    }

    class FineGioco implements Azione{

        @Override
        public void applica(Player player) {
            Gioco.setFineGioco();
            System.out.println("Il Player "+player.getId()+" ha vinto la partita!");
        }
    }

}
