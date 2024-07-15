package FactoryMethod.Caselle;

import Command.NessunAzione;

public class Normale extends Casella{

    public Normale(int numeroCasella) {
        super(numeroCasella, new NessunAzione());
    }
}