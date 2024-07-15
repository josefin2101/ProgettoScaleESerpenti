package gameTest.FactoryMethod.Caselle;

import gameTest.Command.AzioneSosta;
import gameTest.Enumerations.TipoSosta;

public class SostaLocanda extends Casella{

    public SostaLocanda(int numeroCasella) {
        super(numeroCasella, new AzioneSosta(TipoSosta.locanda));
    }
}