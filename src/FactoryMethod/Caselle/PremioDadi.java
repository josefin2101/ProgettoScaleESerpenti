package FactoryMethod.Caselle;

import Command.AzionePremio;
import Enumerations.TipoPremio;

public class PremioDadi extends Casella{

    public PremioDadi(int numeroCasella) {
        super(numeroCasella, new AzionePremio(TipoPremio.dadi));
    }
}