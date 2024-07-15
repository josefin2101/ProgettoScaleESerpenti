package DTO;

import Observer.Gioco;

import java.io.*;

public class ConfigurazioneHandler {

    public static void salvaConfigurazione(ConfigurazioneGioco config, String filePath) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(config);
        }
    }

    public static ConfigurazioneGioco caricaConfigurazione(String filePath) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            return (ConfigurazioneGioco) ois.readObject();
        }
    }

    public static Gioco fromConfigurazioneGioco(ConfigurazioneGioco config) throws InterruptedException {
        return new Gioco(
                config.getNRighe(),
                config.getNColonne(),
                config.getNumGiocatori(),
                config.getNScale(),
                config.getNSerpenti(),
                config.getNSpeciali(),
                config.isDoppio(),
                config.isManuale(),
                config.isAutomatico()
        );
    }

    public static ConfigurazioneGioco toConfigurazioneGioco(Gioco gioco) {
        return new ConfigurazioneGioco(
                gioco.getTabellone().getRighe(),
                gioco.getTabellone().getColonne(),
                gioco.getNumGiocatori(),
                gioco.getnScale(),
                gioco.getnSerpenti(),
                gioco.getnSpeciali(),
                gioco.isDoppioDado(),
                gioco.isManuale(),
                gioco.isAvanzamentoAutomatico()
        );
    }

    public static void salvaConfigurazioneGioco(Gioco gioco, String filePath) throws IOException {
        ConfigurazioneGioco config = toConfigurazioneGioco(gioco);
        salvaConfigurazione(config, filePath);
    }

    public static Gioco caricaConfigurazioneGioco(String filePath) throws IOException, ClassNotFoundException, InterruptedException {
        ConfigurazioneGioco config = caricaConfigurazione(filePath);
        return fromConfigurazioneGioco(config);
    }
}
