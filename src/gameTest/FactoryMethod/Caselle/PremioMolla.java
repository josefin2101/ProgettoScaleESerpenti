package gameTest.FactoryMethod.Caselle;

import gameTest.Command.AzionePremio;
import gameTest.Enumerations.TipoPremio;

public class PremioMolla extends Casella{

    public PremioMolla(int numeroCasella) {
        super(numeroCasella, new AzionePremio(TipoPremio.molla));
    }
}