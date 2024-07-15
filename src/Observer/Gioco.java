package Observer;

import Command.Player;
import FactoryMethod.Caselle.Casella;
import FactoryMethod.CreazioneCaselleFactory;

import javax.swing.*;
import java.util.*;


public class Gioco implements Soggetto {
    private List<Osservatore> osservatori;
    private static Tabellone tabellone;
    private static int numGiocatori;
    private static int nScale;
    private static int nSerpenti;
    private static int nSpeciali;
    private static boolean inizioGioco = false;
    private static Map<Integer, Casella> caselleSpeciali;
    private static boolean doppioDado;
    private static List<Player> giocatori;
    private static ListIterator<Player> turno;
    private static boolean fineGioco;
    private static boolean manuale;
    private boolean avanzamentoAutomatico;


    public Gioco(int righe, int colonne, int numGiocatori, int numScale, int numSerpenti, int numCaselleSpeciali, boolean doppio, boolean manuale, boolean avanzamentoAutomatico) throws InterruptedException {
        doppioDado = doppio;
        giocatori = new LinkedList<>();
        osservatori = new ArrayList<>();
        Gioco.manuale=manuale;
        Gioco.numGiocatori=numGiocatori;
        nScale=numScale;
        nSerpenti=numSerpenti;
        nSpeciali=numCaselleSpeciali;
        this.avanzamentoAutomatico=avanzamentoAutomatico;


        fineGioco = false;

        setupTabellone(righe, colonne, nScale, nSerpenti, nSpeciali);
        for (int i = 0; i < Gioco.numGiocatori; i++)
            giocatori.add(new Player(i, tabellone, this));
        turno = giocatori.listIterator();
        inizioGioco = true;
        notifyObservers();

        if (avanzamentoAutomatico) {
            avanzaAutomaticamente();
        }
    }


    public void attach(Osservatore o) {
        osservatori.add(o);
    }

    @Override
    public void detach(Osservatore o) {
        osservatori.remove(o);
    }


    @Override
    public void notifyObservers() {
        for (Osservatore o : osservatori) {
            o.update();
        }
    }


    public void setupTabellone(int righe, int colonne, int numScale, int numSerpenti, int numCaselleSpeciali) {
        tabellone = new Tabellone(righe, colonne);
        int numCaselle = tabellone.getSize();

        caselleSpeciali = new HashMap<>();

        caselleSpeciali.put(numCaselle, CreazioneCaselleFactory.creaCasellaFinale(numCaselle));

        if(!manuale) {
            for (int i = 0; i < numScale; i++) {
                int casellaDiPartenza = getRandomPosition(numCaselle);
                while (casellaDiPartenza >= 90 || caselleSpeciali.containsKey(casellaDiPartenza)) {
                    casellaDiPartenza = getRandomPosition(numCaselle);
                }
                int casellaDiArrivo = getRandomPosition(numCaselle);
                while (casellaDiArrivo <= casellaDiPartenza || caselleSpeciali.containsKey(casellaDiArrivo) || casellaDiArrivo > casellaDiPartenza + 30) {
                    casellaDiArrivo = getRandomPosition(numCaselle);
                }
                Casella cArrivo = CreazioneCaselleFactory.creaCasellaNormale(casellaDiArrivo);
                Casella c = CreazioneCaselleFactory.creaScala(casellaDiPartenza, casellaDiArrivo);
                caselleSpeciali.put(casellaDiPartenza, c);
                caselleSpeciali.put(casellaDiArrivo, cArrivo);
                tabellone.addCasella(c);
                tabellone.addCasella(cArrivo);
            }

            for (int i = 0; i < numSerpenti; i++) {
                int casellaDiPartenza = getRandomPosition(numCaselle);
                while (casellaDiPartenza <= 10 || caselleSpeciali.containsKey(casellaDiPartenza)) {
                    casellaDiPartenza = getRandomPosition(numCaselle);
                }
                int casellaDiArrivo = getRandomPosition(numCaselle);
                while (casellaDiArrivo >= casellaDiPartenza || caselleSpeciali.containsKey(casellaDiArrivo) || casellaDiArrivo > casellaDiPartenza - 10) {
                    casellaDiArrivo = getRandomPosition(numCaselle);
                }
                Casella cArrivo = CreazioneCaselleFactory.creaCasellaNormale(casellaDiArrivo);
                Casella c = CreazioneCaselleFactory.creaSerpente(casellaDiPartenza, casellaDiArrivo);
                caselleSpeciali.put(casellaDiPartenza, c);
                caselleSpeciali.put(casellaDiArrivo, CreazioneCaselleFactory.creaCasellaNormale(casellaDiArrivo));
                tabellone.addCasella(c);
                tabellone.addCasella(cArrivo);
            }
        }else{
            setManualmente(numScale, numSerpenti);
        }
        for (int i = 0; i < numCaselleSpeciali; i++) {
            int position = getRandomPosition(numCaselle);
            while (caselleSpeciali.containsKey(position)) {
                position = getRandomPosition(numCaselle);
            }
            Casella c = CreazioneCaselleFactory.randomSpeciale(position);
            tabellone.addCasella(c);
            caselleSpeciali.put(position, c);
        }

        for (int i = 1; i <= numCaselle; i++) {
            if (!caselleSpeciali.containsKey(i)) {
                tabellone.addCasella(CreazioneCaselleFactory.creaCasellaNormale(i));
            }
        }

        inizioGioco = true;
        System.out.println("Inizia il gioco!");


    }

    public void avanzaAutomaticamente(){
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                while (!fineGioco) {
                    Thread.sleep(1000);
                    nextTurn();

                }
                return null;
            }

            @Override
            protected void done() {
                notifyObservers();
            }
        };
        worker.execute();
    }

    private void setManualmente(int numScale, int numSerpenti) {
        int numCaselle=tabellone.getSize();
        Scanner scanner=new Scanner(System.in);
        for(int scala=0; scala<numScale;scala++){
            System.out.println("Scegliere la casella da cui deve partire la scala "+ scala+1 +". Scegliere un numero da 0 a "+ numCaselle);
            int casellaDiPartenza=scanner.nextInt();
            while(casellaDiPartenza < 0 || casellaDiPartenza > numCaselle || caselleSpeciali.containsKey(casellaDiPartenza)){
                System.out.println("Scegliere la casella da cui deve partire la scala"+ scala+1+". Scegliere un numero da 0 a "+ numCaselle+". Attenzione a scegliere caselle nel giusto range e a non scegliere caselle scelte precedentemente ");
                casellaDiPartenza=scanner.nextInt();
            }
            System.out.println("Scegliere la casella in cui deve arrivare la scala"+ scala++ +". Scegliere un numero da "+casellaDiPartenza+" a "+ numCaselle);
            int casellaDiArrivo=scanner.nextInt();
            while(casellaDiArrivo < 0 || casellaDiArrivo > numCaselle || caselleSpeciali.containsKey(casellaDiArrivo)){
                System.out.println("Scegliere la casella in cui deve arrivare la scala"+ scala++ +". Scegliere un numero da "+casellaDiPartenza+" a "+ numCaselle+". Attenzione a scegliere caselle nel giusto range e a non scegliere caselle scelte precedentemente ");
                casellaDiPartenza=scanner.nextInt();
            }
            Casella cArrivo = CreazioneCaselleFactory.creaCasellaNormale(casellaDiArrivo);
            Casella c = CreazioneCaselleFactory.creaScala(casellaDiPartenza, casellaDiArrivo);
            caselleSpeciali.put(casellaDiPartenza, c);
            caselleSpeciali.put(casellaDiArrivo, cArrivo);
            tabellone.addCasella(c);
            tabellone.addCasella(cArrivo);
        }

        for(int serpente=0; serpente<numSerpenti; serpente++){
            System.out.println("Scegliere la casella da cui deve partire il serpente"+ serpente+1+". Scegliere un numero da 0 a "+ numCaselle);
            int casellaDiPartenza=scanner.nextInt();
            while(casellaDiPartenza < 0 || casellaDiPartenza > numCaselle || caselleSpeciali.containsKey(casellaDiPartenza)){
                System.out.println("Scegliere la casella da cui deve partire il serpente"+ serpente+1+". Scegliere un numero da 0 a "+ numCaselle+". Attenzione a scegliere caselle nel giusto range e a non scegliere caselle scelte precedentemente ");
                casellaDiPartenza=scanner.nextInt();
            }
            System.out.println("Scegliere la casella in cui deve arrivare il serpente"+ serpente+1+". Scegliere un numero da "+casellaDiPartenza+" a "+ numCaselle);
            int casellaDiArrivo=scanner.nextInt();
            while(casellaDiArrivo < 0 || casellaDiArrivo > numCaselle || caselleSpeciali.containsKey(casellaDiArrivo)){
                System.out.println("Scegliere la casella in cui deve arrivare il serpente"+ serpente+1+". Scegliere un numero da 0 a "+casellaDiPartenza+". Attenzione a scegliere caselle nel giusto range e a non scegliere caselle scelte precedentemente ");
                casellaDiPartenza=scanner.nextInt();
            }
            Casella cArrivo = CreazioneCaselleFactory.creaCasellaNormale(casellaDiArrivo);
            Casella c = CreazioneCaselleFactory.creaSerpente(casellaDiPartenza, casellaDiArrivo);
            caselleSpeciali.put(casellaDiPartenza, c);
            caselleSpeciali.put(casellaDiArrivo, cArrivo);
            tabellone.addCasella(c);
            tabellone.addCasella(cArrivo);
        }


    }

    private int getRandomPosition(int numCaselle) {
        int position;
        do {
            position = new Random().nextInt(numCaselle) + 1;
        } while (caselleSpeciali.containsKey(position));
        return position;
    }


    public void nextTurn() {
        if (inizioGioco && !fineGioco) {
            if (!turno.hasNext()) {
                turno = giocatori.listIterator();
            }else {
                Player p = turno.next();
                p.turno();
            }
            notifyObservers();

        }
    }

    public static int getNumGiocatori() {
        return numGiocatori;
    }

    public static int getnScale() {
        return nScale;
    }

    public static int getnSerpenti() {
        return nSerpenti;
    }

    public static int getnSpeciali() {
        return nSpeciali;
    }

    public static boolean isManuale() {
        return manuale;
    }

    public boolean isAvanzamentoAutomatico() {
        return avanzamentoAutomatico;
    }

    public static boolean isDoppioDado() {
        return doppioDado;
    }

    public static void setFineGioco() {
        fineGioco = true;
    }

    public static Map<Integer, Casella> getCaselleSpeciali() {
        return caselleSpeciali;
    }

    public Tabellone getTabellone() {
        return tabellone;
    }

    public static List<Player> getGiocatori() {
        return giocatori;
    }
}