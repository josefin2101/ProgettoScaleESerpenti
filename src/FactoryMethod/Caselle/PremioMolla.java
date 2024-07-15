package FactoryMethod.Caselle;

import Command.AzionePremio;
import Enumerations.TipoPremio;

public class PremioMolla extends Casella{

    public PremioMolla(int numeroCasella) {
        super(numeroCasella, new AzionePremio(TipoPremio.molla));
    }
}