package me.victorml.npaw;

import me.victorml.npaw.config.NpawConfig;
import me.victorml.npaw.logic.NpawServer;
import me.victorml.npaw.model.Client;

import java.util.Map;

public class Main {

    private static final int PORT = 8080;
    private static final int MAX_THREADS = 256; //128
    private static final int BACKLOG = 300; //150

    private static NpawConfig npawConfig;


    public static void main(String[] args) {

        // Load configuration
        npawConfig = NpawConfig.getInstance();
        // Save config in case the file didnt exists
        Map<String, Client> clients = npawConfig.loadClientsFromFile();
        npawConfig.setUpClients(clients);
        npawConfig.saveClientsToFile();

        //Create the Server at PORT & limited THREAD count
        new Thread(new NpawServer(PORT, MAX_THREADS, BACKLOG)).start();

        //TODO: Command to load the config runtime from a file


    }
}
