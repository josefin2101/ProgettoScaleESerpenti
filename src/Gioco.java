import java.util.*;


public class Gioco {
    private Tabellone tabellone;
    private static boolean inizioGioco=false;
    private static Map<Integer, Casella> caselleSpeciali;
    private static boolean doppioDado;
    private static List<Player> giocatori;
    private static ListIterator<Player> turno;
    private static boolean fineGioco;

    public Gioco(int righe, int colonne,int numGiocatori, int numScale, int numSerpenti, int numCaselleSpeciali, boolean doppio){
        doppioDado=doppio;
        giocatori=new LinkedList<>();

        fineGioco=false;

        setupTabellone(righe,colonne,numScale,numSerpenti,numCaselleSpeciali);
        for(int i=0; i<numGiocatori; i++)
            giocatori.add(new Player(i, tabellone));
        turno=giocatori.listIterator();
    }

    public void setupTabellone(int righe, int colonne, int numScale, int numSerpenti, int numCaselleSpeciali) {
        tabellone = new Tabellone(righe, colonne);
        int numCaselle = tabellone.getSize();

        caselleSpeciali = new HashMap<>();

        caselleSpeciali.put(numCaselle, CreazioneCaselleFactory.creaCasellaFinale(numCaselle));


        for (int i = 0; i < numScale; i++) {
            int casellaDiPartenza = getRandomPosition(numCaselle);
            while (casellaDiPartenza>=90 || caselleSpeciali.containsKey(casellaDiPartenza)) {
                casellaDiPartenza = getRandomPosition(numCaselle);
            }
            System.out.println("Casella di partenza scala: "+casellaDiPartenza);
            int casellaDiArrivo = getRandomPosition(numCaselle);
            while (casellaDiArrivo <= casellaDiPartenza || caselleSpeciali.containsKey(casellaDiArrivo) || casellaDiArrivo>casellaDiPartenza+30) {
                casellaDiArrivo = getRandomPosition(numCaselle);
            }
            System.out.println("Casella di arrivo scala: "+casellaDiArrivo);
            Casella cArrivo= CreazioneCaselleFactory.creaCasellaNormale(casellaDiArrivo);
            Casella c=CreazioneCaselleFactory.creaScala(casellaDiPartenza, casellaDiArrivo);
            caselleSpeciali.put(casellaDiPartenza, c);
            caselleSpeciali.put(casellaDiArrivo, cArrivo);
            tabellone.addCasella(c);
            tabellone.addCasella(cArrivo);
        }

        for (int i = 0; i < numSerpenti; i++) {
            int casellaDiPartenza = getRandomPosition(numCaselle);
            while (casellaDiPartenza<=10 || caselleSpeciali.containsKey(casellaDiPartenza)) {
                casellaDiPartenza = getRandomPosition(numCaselle);
            }
            System.out.println("Casella di partenza serpente: "+casellaDiPartenza);
            int casellaDiArrivo = getRandomPosition(numCaselle);
            while (casellaDiArrivo >= casellaDiPartenza || caselleSpeciali.containsKey(casellaDiArrivo) || casellaDiArrivo>casellaDiPartenza-10) {
                casellaDiArrivo = getRandomPosition(numCaselle);
            }
            System.out.println("Casella di arrivo serpente: "+casellaDiArrivo);
            Casella cArrivo=CreazioneCaselleFactory.creaCasellaNormale(casellaDiArrivo);
            Casella c=CreazioneCaselleFactory.creaSerpente(casellaDiPartenza, casellaDiArrivo);
            caselleSpeciali.put(casellaDiPartenza, c);
            caselleSpeciali.put(casellaDiArrivo, CreazioneCaselleFactory.creaCasellaNormale(casellaDiArrivo));
            tabellone.addCasella(c);
            tabellone.addCasella(cArrivo);
        }

        for (int i = 0; i < numCaselleSpeciali; i++) {
            int position = getRandomPosition(numCaselle);
            while (caselleSpeciali.containsKey(position)) {
                position = getRandomPosition(numCaselle);
            }
            Casella c=CreazioneCaselleFactory.randomSpeciale(position);
            tabellone.addCasella(c);
            caselleSpeciali.put(position, c);
        }

        for (int i = 1; i <= numCaselle; i++) {
            if (!caselleSpeciali.containsKey(i)) {
                tabellone.addCasella(CreazioneCaselleFactory.creaCasellaNormale(i));
            }
        }

        inizioGioco=true;
        System.out.println("Inizia il gioco!");
    }

    private int getRandomPosition(int numCaselle) {
        int position;
        do {
            position = new Random().nextInt(numCaselle) + 1;
        } while (caselleSpeciali.containsKey(position));
        return position;
    }

    public static boolean isDoppioDado() {
        return doppioDado;
    }

    public static void setFineGioco(){
        fineGioco=true;
    }

    public static Map<Integer, Casella> getCaselleSpeciali() {
        return caselleSpeciali;
    }

    public static void main(String[] args) throws InterruptedException {
        Scanner scanner=new Scanner(System.in);
        System.out.println("Scegliere il numero di righe: ");
        int nRighe=scanner.nextInt();
        System.out.println("Scegliere il numero di colonne: ");
        int nColonne=scanner.nextInt();
        System.out.println("Scegliere il numero di giocatori: ");
        int numGiocatori=scanner.nextInt();
        System.out.println("Sceglier il numero di Scale: ");
        int nScale=scanner.nextInt();
        System.out.println("Sceglier il numero di Serpenti: ");
        int nSerpenti=scanner.nextInt();
        System.out.println("Sceglier il numero di caselle speciali: ");
        int nSpeciali=scanner.nextInt();

        System.out.println("Quanti dadi si vogliono usare? (scegliere tra 1 e 2): ");
        int dadi=scanner.nextInt();
        while (dadi!=1 && dadi!=2){
            System.out.println("Hai inserito un numero incorretto di dadi, inserisci nuovamente il numero: ");
            dadi=scanner.nextInt();
        }
        boolean doppio = (dadi == 2);


        new Gioco(nRighe, nColonne, numGiocatori, nScale, nSerpenti, nSpeciali, doppio);
        if(inizioGioco) {
            while (!fineGioco) {
                Player p = turno.next();
                p.turno();
                Thread.sleep(1000);
                if (!turno.hasNext()) {
                    turno = giocatori.listIterator();
                }
            }
        }
    }
}


