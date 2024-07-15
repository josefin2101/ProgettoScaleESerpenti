package gameTest.FactoryMethod.Caselle;

import gameTest.Command.FineGioco;

public class Finale extends Casella{

    public Finale(int numeroCasella){
        super(numeroCasella, new FineGioco());
    }
}