package FactoryMethod;

import Enumerations.TipoCasellaSpeciale;
import FactoryMethod.Caselle.*;


import java.util.Random;

public class CreazioneCaselleFactory {

    public static Finale creaCasellaFinale(int numeroCasella){
        return new Finale(numeroCasella);
    }
    public static Normale creaCasellaNormale(int numeroCasella) {
        return new Normale(numeroCasella);
    }

    public static Scala creaScala(int numeroCasella,int arrivo) {
        return new Scala(numeroCasella, arrivo);
    }

    public static Serpente creaSerpente(int numeroCasella, int arrivo) {
        return new Serpente(numeroCasella, arrivo);
    }

    public static SostaLocanda creaSostaLocanda(int numeroCasella){
        return new SostaLocanda(numeroCasella);
    }

    public static SostaPanchina creaSostaPanchina(int numeroCasella){
        return new SostaPanchina(numeroCasella);
    }
    public static PremioMolla creaPremioMolla(int numeroCasella){
        return new PremioMolla(numeroCasella);
    }
    public static PremioDadi creaPremioDadi(int numeroCasella){
        return new PremioDadi(numeroCasella);
    }



    public static Pesca creaPesca(int numeroCasella){
        return new Pesca(numeroCasella);
    }

    public static Casella randomSpeciale(int numeroCasella){
        TipoCasellaSpeciale[] tipi=TipoCasellaSpeciale.values();
        int indiceCasuale = new Random().nextInt(tipi.length);
        return getSpecialeByName(tipi[indiceCasuale], numeroCasella);
    }

    private static Casella getSpecialeByName(TipoCasellaSpeciale tipo, int numeroCasella){
        if(tipo.equals(TipoCasellaSpeciale.casellaLocanda)){
            return creaSostaLocanda(numeroCasella);
        } else if (tipo.equals(TipoCasellaSpeciale.casellaPanchina)){
            return creaSostaPanchina(numeroCasella);
        } else if(tipo.equals(TipoCasellaSpeciale.casellaPremioDadi)){
            return creaPremioDadi(numeroCasella);
        } else if(tipo.equals(TipoCasellaSpeciale.casellaPremioMolla)){
            return creaPremioMolla(numeroCasella);
        }
        return creaPesca(numeroCasella);
    }
}
