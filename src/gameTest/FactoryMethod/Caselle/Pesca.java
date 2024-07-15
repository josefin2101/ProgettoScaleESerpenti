package gameTest.FactoryMethod.Caselle;

import gameTest.Command.AzionePesca;
import gameTest.Enumerations.TipoCarta;

import java.util.Random;

public class Pesca extends Casella{
    private static final TipoCarta[] CARTE = TipoCarta.values();
    private static final Random RANDOM = new Random();

    private static TipoCarta getRandomCarta() {
        int indiceCasuale = RANDOM.nextInt(CARTE.length);
        return CARTE[indiceCasuale];
    }

    public Pesca(int numeroCasella) {

        super(numeroCasella, new AzionePesca(getRandomCarta()));
    }
}