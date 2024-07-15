package FactoryMethod.Caselle;

import Command.AzioneSosta;
import Enumerations.TipoSosta;

public class SostaLocanda extends Casella{

    public SostaLocanda(int numeroCasella) {
        super(numeroCasella, new AzioneSosta(TipoSosta.locanda));
    }
}