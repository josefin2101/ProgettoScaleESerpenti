package FactoryMethod.Caselle;

import Command.AzioneSerpente;

public class Serpente extends Casella{

    public Serpente(int casellaPartenza,int arrivo) {
        super(casellaPartenza, new AzioneSerpente(arrivo));
    }
}