package me.victorml.npaw;

import me.victorml.npaw.config.NpawConfig;
import me.victorml.npaw.logic.NpawServer;
import me.victorml.npaw.model.Client;
import me.victorml.npaw.model.HostManager;

import java.util.Map;
import java.util.Scanner;

public class Main {

    private static final int PORT = 8080;
    private static final int MAX_THREADS = 400; //128 - 258
    private static final int BACKLOG = 500; //150 - 300

    private static NpawConfig npawConfig;
    private static HostManager hostManager;


    public static void main(String[] args) {

        // Load configuration
        npawConfig = NpawConfig.getInstance();
        hostManager = HostManager.getInstance();

        // Save config in case the file didnt exists
        Map<String, Client> clients = npawConfig.loadClientsFromFile();
        npawConfig.setUpClients(clients);
        npawConfig.saveClientsToFile();

        //Create the Server at PORT & limited THREAD count
        new Thread(new NpawServer(PORT, MAX_THREADS, BACKLOG)).start();
        /*

        Scanner sc = new Scanner(System.in);
        String comand = sc.nextLine();

        do{
            if(comand.equalsIgnoreCase("reload")){
                clients = npawConfig.loadClientsFromFile();
                npawConfig.setUpClients(clients);
                npawConfig.saveClientsToFile();

            }else{
                System.err.println("The Command " + comand + " does not exists");
            }

        }while(!comand.equalsIgnoreCase("exit"));*/





        //TODO: Command to load the config runtime from a file


    }
}
