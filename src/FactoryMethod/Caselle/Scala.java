package FactoryMethod.Caselle;

import Command.AzioneScala;

public class Scala extends Casella{

    public Scala(int casellaPartenza,int arrivo) {
        super(casellaPartenza, new AzioneScala(arrivo));
    }


}