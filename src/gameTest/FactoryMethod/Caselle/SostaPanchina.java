package gameTest.FactoryMethod.Caselle;

import gameTest.Command.AzioneSosta;
import gameTest.Enumerations.TipoSosta;

public class SostaPanchina extends Casella{

    public SostaPanchina(int numeroCasella) {
        super(numeroCasella, new AzioneSosta(TipoSosta.panchina));
    }
}

