package FactoryMethod.Caselle;

import Command.FineGioco;

public class Finale extends Casella{

    public Finale(int numeroCasella){
        super(numeroCasella, new FineGioco());
    }
}