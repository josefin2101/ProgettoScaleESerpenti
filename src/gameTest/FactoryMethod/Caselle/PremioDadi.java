package gameTest.FactoryMethod.Caselle;

import gameTest.Command.AzionePremio;
import gameTest.Enumerations.TipoPremio;

public class PremioDadi extends Casella{

    public PremioDadi(int numeroCasella) {
        super(numeroCasella, new AzionePremio(TipoPremio.dadi));
    }
}