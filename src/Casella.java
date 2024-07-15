import java.util.Random;

public class Casella implements TabelloneElement {
    private static int numeroCasella;
    private Azione azione;

    public Casella(int numeroCasella, Azione azione) {
        Casella.numeroCasella = numeroCasella;
        this.azione = azione;
    }

    public int getNumeroCasella() {
        return numeroCasella;
    }

    @Override
    public void applyAction(Player player) {
        if (azione != null) {
            azione.applica(player);
        }
    }
    static class Scala extends Casella{

        public Scala(int casellaPartenza,int arrivo) {
            super(casellaPartenza, new Azione.AzioneScala(arrivo));
        }

    }

    static class Serpente extends Casella{

        public Serpente(int casellaPartenza,int arrivo) {
            super(casellaPartenza, new Azione.AzioneSerpente(arrivo));
        }
    }

    static class SostaPanchina extends Casella{

        public SostaPanchina(int numeroCasella) {
            super(numeroCasella, new Azione.AzioneSosta(TipoSosta.panchina));
        }
    }

    static class SostaLocanda extends Casella{

        public SostaLocanda(int numeroCasella) {
            super(numeroCasella, new Azione.AzioneSosta(TipoSosta.locanda));
        }
    }

    static class Pesca extends Casella{
        private static final TipoCarta[] CARTE = TipoCarta.values();
        private static final Random RANDOM = new Random();

        private static TipoCarta getRandomCarta() {
            int indiceCasuale = RANDOM.nextInt(CARTE.length);
            return CARTE[indiceCasuale];
        }

        public Pesca(int numeroCasella) {

            super(numeroCasella, new Azione.AzionePesca(getRandomCarta()));
        }
    }

    static class Normale extends Casella{

        public Normale(int numeroCasella) {
            super(numeroCasella, new Azione.NessunAzione());
        }
    }

    static class Finale extends Casella{

        public Finale(int numeroCasella){
            super(numeroCasella, new Azione.FineGioco());
        }
    }

}
