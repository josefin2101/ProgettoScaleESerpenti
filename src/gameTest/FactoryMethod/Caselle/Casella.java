package gameTest.FactoryMethod.Caselle;

import gameTest.Command.Azione;
import gameTest.Command.Player;
import gameTest.FactoryMethod.TabelloneElement;


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
    public void applicaAzione(Player player) {
        azione.applicaAzione(player);
    }

}
