package gameTest.FactoryMethod.Caselle;

import gameTest.Command.NessunAzione;

public class Normale extends Casella{

    public Normale(int numeroCasella) {
        super(numeroCasella, new NessunAzione());
    }
}