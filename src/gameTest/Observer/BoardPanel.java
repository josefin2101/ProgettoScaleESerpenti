package gameTest.Observer;
import gameTest.Command.Player;
import gameTest.FactoryMethod.Caselle.*;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class BoardPanel extends JPanel {
    private Gioco gioco;
    private final int righe;
    private final int colonne;
    private final int dimensioneCelle;

    public BoardPanel(Gioco gioco) {
        this.gioco = gioco;
        this.righe = gioco.getTabellone().getRighe();
        this.colonne = gioco.getTabellone().getColonne();
        this.dimensioneCelle = 50;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        disegnaTabellone(g);
        disegnaCaselleSpeciali(g);
        dsiegnaNumeriCaselle(g);
        disegnaPlayers(g);
    }

    private void disegnaTabellone(Graphics g) {
        int larghezzaTabellone = colonne * dimensioneCelle;
        int altezzaTabellone = righe * dimensioneCelle;
        int offsetX = (getWidth() - larghezzaTabellone) / 2;
        int offsetY = (getHeight() - altezzaTabellone) / 2;

        for (int riga = 0; riga < righe; riga++) {
            for (int col = 0; col < colonne; col++) {
                int x = offsetX + col * dimensioneCelle;
                int y = offsetY + (righe - 1 - riga) * dimensioneCelle;
                g.drawRect(x, y, dimensioneCelle, dimensioneCelle);
            }
        }
    }

    private void disegnaPlayers(Graphics g) {
        int larghezzaTabellone = colonne * dimensioneCelle;
        int altezzaTabellone = righe * dimensioneCelle;
        int offsetX = (getWidth() - larghezzaTabellone) / 2;
        int offsetY = (getHeight() - altezzaTabellone) / 2;

        for (Player player : Gioco.getGiocatori()) {
            int posizione = player.getPosizione();
            int riga = (posizione - 1) / colonne;
            int colonna = (posizione - 1) % colonne;
            int x = offsetX + colonna * dimensioneCelle + dimensioneCelle / 4;
            int y = offsetY + (righe - 1 - riga) * dimensioneCelle + dimensioneCelle / 4;
            g.setColor(player.getColorePlayer());
            g.fillOval(x, y, dimensioneCelle / 2, dimensioneCelle / 2);
            g.setColor(Color.BLACK);
            g.drawOval(x, y, dimensioneCelle / 2, dimensioneCelle / 2);
        }
    }

    private void disegnaCaselleSpeciali(Graphics g) {
        Map<Integer, Casella> caselleSpeciali = Gioco.getCaselleSpeciali();

        int larghezzaTabellone = colonne * dimensioneCelle;
        int altezzaTabellone = righe * dimensioneCelle;
        int offsetX = (getWidth() - larghezzaTabellone) / 2;
        int offsetY = (getHeight() - altezzaTabellone) / 2;

        for (Map.Entry<Integer, Casella> entry : caselleSpeciali.entrySet()) {
            int posizione = entry.getKey();
            Casella casella = entry.getValue();
            int row = (posizione - 1) / colonne;
            int col = (posizione - 1) % colonne;
            int x = offsetX + col * dimensioneCelle;
            int y = offsetY + (righe - 1 - row) * dimensioneCelle;

            if (casella.getClass().equals(Scala.class)) {
                g.setColor(Color.GREEN);
            } else if (casella.getClass().equals(Serpente.class)) {
                g.setColor(Color.RED);
            } else if(casella.getClass().equals(Finale.class)){
                g.setColor(Color.YELLOW);
            } else if(casella.getClass().equals(Pesca.class)){
                g.setColor(Color.BLUE);
            } else if(casella.getClass().equals(PremioDadi.class)){
                g.setColor(Color.MAGENTA);
            } else if(casella.getClass().equals(PremioMolla.class)){
                g.setColor(Color.LIGHT_GRAY);
            } else if(casella.getClass().equals(SostaLocanda.class)){
                g.setColor(Color.PINK);
            }else{
                g.setColor(Color.ORANGE);
            }

            g.fillRect(x, y, dimensioneCelle, dimensioneCelle);
            g.setColor(Color.BLACK);
            g.drawRect(x, y, dimensioneCelle, dimensioneCelle);
        }
    }

    private void dsiegnaNumeriCaselle(Graphics g) {
        g.setColor(Color.BLACK);

        int larghezzaTabellone = colonne * dimensioneCelle;
        int altezzaTabellone = righe * dimensioneCelle;
        int offsetX = (getWidth() - larghezzaTabellone) / 2;
        int offsetY = (getHeight() - altezzaTabellone) / 2;

        for (int riga = 0; riga < righe; riga++) {
            for (int col = 0; col < colonne; col++) {
                int x = offsetX + col * dimensioneCelle;
                int y = offsetY + (righe - 1 - riga) * dimensioneCelle;
                int posizione = riga * colonne + col + 1;
                g.drawString(String.valueOf(posizione), x + dimensioneCelle / 2 - 10, y + dimensioneCelle / 2 + 5);
            }
        }
    }



    static class LegendaPanel extends JPanel {

        public LegendaPanel() {
            setLayout(new GridLayout(0, 1));

            add(creaLegenda(Color.WHITE, "Casella Normale"));
            add(creaLegenda(Color.GREEN, "Scala: Salta Avanti"));
            add(creaLegenda(Color.RED, "Serpente: Scivola Indietro"));
            add(creaLegenda(Color.YELLOW, "Casella Vittoria"));
            add(creaLegenda(Color.BLUE, "Casella Pesca"));
            add(creaLegenda(Color.MAGENTA, "Casella Dadi"));
            add(creaLegenda(Color.LIGHT_GRAY, "Casella Molla"));
            add(creaLegenda(Color.PINK, "Casella Sosta Locanda: attendi 3 turni"));
            add(creaLegenda(Color.ORANGE, "Casella Sosta Panchina, attendi 1 turno"));

            setBorder(BorderFactory.createTitledBorder("Legenda"));
        }

        private JPanel creaLegenda(Color color, String description) {
            JPanel Pannello = new JPanel();
            Pannello.setLayout(new BorderLayout());

            JLabel colori = new JLabel();
            colori.setOpaque(true);
            colori.setBackground(color);
            colori.setPreferredSize(new Dimension(20, 20));

            JLabel descrizione = new JLabel(description);
            descrizione.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));

            Pannello.add(colori, BorderLayout.WEST);
            Pannello.add(descrizione, BorderLayout.CENTER);

            return Pannello;
        }
    }

    public void setGioco(Gioco gioco) {
        this.gioco = gioco;
    }

}
