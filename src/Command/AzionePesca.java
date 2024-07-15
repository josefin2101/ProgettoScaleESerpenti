package Command;


import Enumerations.TipoCarta;
import Enumerations.TipoPremio;
import Enumerations.TipoSosta;

public class AzionePesca implements Azione{
    private final TipoCarta carta;

    public AzionePesca(TipoCarta carta){
        this.carta=carta;
    }

    @Override
    public void applicaAzione(Player player) {
        System.out.println("Il Player "+player.getId()+" è appena arrivato in una casella Pesca, pesca una carta!");
        if(carta.equals(TipoCarta.panchina)) {
            new AzioneSosta(TipoSosta.panchina).applicaAzione(player);
            System.out.println("Il Player " + player.getId() + " ha trovato una carta panchina");
        }
        else if(carta.equals(TipoCarta.locanda)) {
            new AzioneSosta(TipoSosta.locanda).applicaAzione(player);
            System.out.println("Il Player "+player.getId()+" ha trovato una carta locanda");
        }
        else if(carta.equals(TipoCarta.dadi)) {
            new AzionePremio(TipoPremio.dadi).applicaAzione(player);
            System.out.println("Il Player "+player.getId()+" ha trovato una carta dadi, rilancia i dadi!");
        }
        else if(carta.equals(TipoCarta.molla)) {
            new AzionePremio(TipoPremio.molla).applicaAzione(player);
            System.out.println("Il Player " + player.getId() + " ha trovato una carta molla, rimbalza dell'ultimo lancio che ha fatto!");
        }
        else{
            player.conservaDivieto();
            System.out.println("Il Player "+player.getId()+" ha trovato una carta divieto, che fortuna!");
        }
    }
}