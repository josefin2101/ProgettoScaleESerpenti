package FactoryMethod.Caselle;

import Command.AzioneSosta;
import Enumerations.TipoSosta;

public class SostaPanchina extends Casella{

    public SostaPanchina(int numeroCasella) {
        super(numeroCasella, new AzioneSosta(TipoSosta.panchina));
    }
}

