package Command;


import Enumerations.TipoSosta;

public class AzioneSosta implements Azione {

    private final TipoSosta tipoSosta;

    public AzioneSosta(TipoSosta tipo) {
        tipoSosta=tipo;
    }

    @Override
    public void applicaAzione(Player player) {
        if (player.usaDivietoDiSosta()) {
            player.setTurniAttesa(0);
            System.out.println("Il Player "+player.getId()+" usa la sua carta divieto di sosta, annullata la sosta!");
        } else {
            if (tipoSosta.equals(TipoSosta.locanda)) {
                player.setTurniAttesa(3);
                System.out.println("Il Player " + player.getId() + " è appena arrivato in una locanda, attende 3 turni");
            } else {
                player.setTurniAttesa(1);
                System.out.println("Il Player si siede su una panchina, attende 1 turno");
            }
        }
    }
}