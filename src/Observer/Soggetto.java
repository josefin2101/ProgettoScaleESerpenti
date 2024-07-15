package Observer;

public interface Soggetto {
    void attach(Osservatore o);
    void detach(Osservatore o);
    void notifyObservers();
}
