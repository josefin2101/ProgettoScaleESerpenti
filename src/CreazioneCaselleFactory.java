import java.util.Random;

public class CreazioneCaselleFactory {

    public static Casella.Finale creaCasellaFinale(int numeroCasella){
        return new Casella.Finale(numeroCasella);
    }
    public static Casella.Normale creaCasellaNormale(int numeroCasella) {
        return new Casella.Normale(numeroCasella);
    }

    public static Casella.Scala creaScala(int numeroCasella,int arrivo) {
        return new Casella.Scala(numeroCasella, arrivo);
    }

    public static Casella.Serpente creaSerpente(int numeroCasella, int arrivo) {
        return new Casella.Serpente(numeroCasella, arrivo);
    }

    public static Casella.SostaLocanda creaSostaLocanda(int numeroCasella){
        return new Casella.SostaLocanda(numeroCasella);
    }

    public static Casella.SostaPanchina creaSostaPanchina(int numeroCasella){
        return new Casella.SostaPanchina(numeroCasella);
    }

    public static Casella.Pesca creaPesca(int numeroCasella){
        return new Casella.Pesca(numeroCasella);
    }

    public static Casella randomSpeciale(int numeroCasella){
        TipoCasellaSpeciale[] tipi=TipoCasellaSpeciale.values();
        int indiceCasuale = new Random().nextInt(tipi.length);
        return getSpecialeByName(tipi[indiceCasuale], numeroCasella);
    }

    private static Casella getSpecialeByName(TipoCasellaSpeciale tipo, int numeroCasella){
        if(tipo.equals(TipoCasellaSpeciale.casellaLocanda)){
            return creaSostaLocanda(numeroCasella);
        }
        else if (tipo.equals(TipoCasellaSpeciale.casellaPanchina)){
            return creaSostaPanchina(numeroCasella);
        }
        return creaPesca(numeroCasella);
    }
}
