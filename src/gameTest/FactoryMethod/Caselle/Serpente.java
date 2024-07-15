package gameTest.FactoryMethod.Caselle;

import gameTest.Command.AzioneSerpente;

public class Serpente extends Casella{

    public Serpente(int casellaPartenza,int arrivo) {
        super(casellaPartenza, new AzioneSerpente(arrivo));
    }
}