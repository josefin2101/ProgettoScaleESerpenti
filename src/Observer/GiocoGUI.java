package Observer;
import DTO.ConfigurazioneGioco;
import DTO.ConfigurazioneHandler;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class GiocoGUI extends JFrame implements Osservatore {
    private Gioco gioco;
    private BoardPanel PannelloTabellone;
    private final JTextArea areaMessaggi;
    private final JButton LanciaDadiButton;
    private final JButton SalvaConfigButton;
    private final JButton CaricaConfigButton;



    public GiocoGUI(Gioco gioco) {
        this.gioco = gioco;
        this.gioco.attach(this);

        setTitle("Scale e Serpenti");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);


        PannelloTabellone = new BoardPanel(gioco);
        areaMessaggi = new JTextArea(5, 20);
        areaMessaggi.setEditable(false);
        JScrollPane pannelloOutput = new JScrollPane(areaMessaggi);
        add(pannelloOutput, BorderLayout.SOUTH);
        PrintStream ps = new PrintStream(new OutputStreamInterfaccia(areaMessaggi));
        System.setOut(ps);
        System.setErr(ps);

        JPanel ComandiPanel = new JPanel();
        LanciaDadiButton = new JButton("Lancia i dadi");

        if(!gioco.isAvanzamentoAutomatico()) {
        LanciaDadiButton.addActionListener(e -> gioco.nextTurn());
        ComandiPanel.add(LanciaDadiButton);
        }
        SalvaConfigButton=new JButton("Salva questa configurazione");
        CaricaConfigButton=new JButton("Carica configurazione");


        SalvaConfigButton.addActionListener(e-> salvaConfigurazione());
        CaricaConfigButton.addActionListener(e-> caricaConfigurazione());

        ComandiPanel.add(SalvaConfigButton);

        BoardPanel.LegendaPanel PannelloLegenda = new BoardPanel.LegendaPanel();

        JPanel PannelloLaterale = new JPanel();
        PannelloLaterale.setLayout(new BorderLayout());
        PannelloLaterale.add(PannelloLegenda, BorderLayout.NORTH);
        PannelloLaterale.add(new JScrollPane(areaMessaggi), BorderLayout.CENTER);

        add(PannelloTabellone, BorderLayout.CENTER);
        add(new JScrollPane(areaMessaggi), BorderLayout.SOUTH);
        add(PannelloLaterale, BorderLayout.EAST);
        add(ComandiPanel, BorderLayout.NORTH);

        update();
    }

    @Override
    public void update() {
        PannelloTabellone.repaint();
    }

    private static class OutputStreamInterfaccia extends OutputStream {
        private final JTextArea areaDiTesto;

        public OutputStreamInterfaccia(JTextArea textArea) {
            this.areaDiTesto = textArea;
        }

        @Override
        public void write(int b) {
            areaDiTesto.append(String.valueOf((char) b));
            areaDiTesto.setCaretPosition(areaDiTesto.getDocument().getLength());
        }
    }
    private void salvaConfigurazione() {
        JFileChooser scegliFile = new JFileChooser();
        int scelta = scegliFile.showSaveDialog(this);

        if (scelta == JFileChooser.APPROVE_OPTION) {
            File fileDaSalvare = scegliFile.getSelectedFile();
            try {
                ConfigurazioneHandler.salvaConfigurazioneGioco(gioco, fileDaSalvare.getAbsolutePath());
                JOptionPane.showMessageDialog(this, "Configurazione salvata con successo!");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Errore nel salvataggio della configurazione. Errore: " + ex.getMessage());
            }
        }
    }
    private void caricaConfigurazione() {
        JFileChooser scegliFile = new JFileChooser();
        int scelta = scegliFile.showOpenDialog(this);

        if (scelta == JFileChooser.APPROVE_OPTION) {
            File fileDaCaricare = scegliFile.getSelectedFile();
            try {
                gioco = ConfigurazioneHandler.caricaConfigurazioneGioco(fileDaCaricare.getAbsolutePath());
                PannelloTabellone.setGioco(gioco);
                gioco.attach(this);
                revalidate();
                repaint();
                JOptionPane.showMessageDialog(this, "Configurazione caricata con successo!");
            } catch (IOException | ClassNotFoundException | InterruptedException ex) {
                JOptionPane.showMessageDialog(this, "Errore nel caricamento della configurazione. Errore: " + ex.getMessage());
            }
        }
    }



    public static class Main {
        public static void main(String[] args) {
            SwingUtilities.invokeLater(() -> {
                int scelta = sceltaIniziale();

                if (scelta == 0) {
                    ConfigurazioneGioco config = caricaConfigurazioneDaFile();
                    if (config != null) {
                        try {
                            avviaGiocoConConfigurazione(config);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                } else if (scelta == 1) {
                    ConfigurazioneGioco config = creaNuovaConfigurazione();
                    try {
                        avviaGiocoConConfigurazione(config);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        }

        private static void avviaGiocoConConfigurazione(ConfigurazioneGioco config) throws InterruptedException {
            Gioco gioco = ConfigurazioneHandler.fromConfigurazioneGioco(config);

            GiocoGUI gui = new GiocoGUI(gioco);
            gui.setVisible(true);

            if(config.isAutomatico())
                gioco.avanzaAutomaticamente();
        }

        private static ConfigurazioneGioco creaNuovaConfigurazione() {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Scegliere il numero di righe: ");
            int nRighe = scanner.nextInt();
            System.out.println("Scegliere il numero di colonne: ");
            int nColonne = scanner.nextInt();
            System.out.println("Scegliere il numero di giocatori: ");
            int numGiocatori = scanner.nextInt();
            System.out.println("Scegliere il numero di Scale: ");
            int nScale = scanner.nextInt();
            System.out.println("Scegliere il numero di Serpenti: ");
            int nSerpenti = scanner.nextInt();

            boolean manuale;
            String risposta1;
            do {
                System.out.println("Vuoi configurare manualmente scale e serpenti?(y/n) ");
                risposta1 = scanner.next();
            } while (!risposta1.equals("y") && !risposta1.equals("n") );
            manuale= risposta1.equals("y");

            System.out.println("Scegliere il numero di caselle speciali: ");
            int nSpeciali = scanner.nextInt();


            System.out.println("Quanti dadi si vogliono usare? (scegliere tra 1 e 2): ");
            int dadi = scanner.nextInt();
            while (dadi != 1 && dadi != 2) {
                System.out.println("Hai inserito un numero incorretto di dadi, inserisci nuovamente il numero: ");
                dadi = scanner.nextInt();
            }
            boolean doppio = (dadi == 2);

            boolean auto;
            String risposta2;

            do {
                System.out.println("Si vuole avanzare automaticamente?(y/n) ");
                risposta2=scanner.next();
            }while (!risposta2.equals("y") && !risposta2.equals("n") );
            auto=risposta2.equals("y");

            System.out.println("Parametri inseriti: ");
            System.out.println("Righe: " + nRighe);
            System.out.println("Colonne: " + nColonne);
            System.out.println("Giocatori: " + numGiocatori);
            System.out.println("Scale: " + nScale);
            System.out.println("Serpenti: " + nSerpenti);
            System.out.println("Manuale: " + manuale);
            System.out.println("Caselle speciali: " + nSpeciali);
            System.out.println("Doppio dado: " + doppio);
            System.out.println("Avanzamento automatico: " + auto);


            return new ConfigurazioneGioco(nRighe,nColonne, numGiocatori, nScale, nSerpenti, nSpeciali, doppio, manuale, auto);
        }

        private static ConfigurazioneGioco caricaConfigurazioneDaFile() {
            JFileChooser scegliFile = new JFileChooser();
            int scelta = scegliFile.showOpenDialog(null);

            if (scelta == JFileChooser.APPROVE_OPTION) {
                try {
                    return ConfigurazioneHandler.caricaConfigurazione(scegliFile.getSelectedFile().getAbsolutePath());
                } catch (IOException | ClassNotFoundException ex) {
                    JOptionPane.showMessageDialog(null, "Errore nel caricamento della configurazione: " + ex.getMessage());
                }
            }
            return null;
        }

        private static int sceltaIniziale() {
            String[] possibiliScelte = {"Carica Configurazione", "Crea Nuova Configurazione"};
            return JOptionPane.showOptionDialog(null,
                    "Scegli un'opzione:",
                    "Configurazione Gioco",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    possibiliScelte,
                    possibiliScelte[0]);
        }


    }


}
