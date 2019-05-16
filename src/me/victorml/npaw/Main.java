package me.victorml.npaw;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.victorml.npaw.config.NpawConfig;
import me.victorml.npaw.logic.NpawServer;
import me.victorml.npaw.model.Client;
import me.victorml.npaw.model.hosts.HostManager;

import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static final Gson GSON_CONFIG = new GsonBuilder()
            .setPrettyPrinting()
            .serializeNulls()
            .disableHtmlEscaping()
            .excludeFieldsWithModifiers(Modifier.TRANSIENT, Modifier.VOLATILE)
            .create();

    private static final int PORT = 8080;
    private static final int MAX_THREADS = 400; //128 - 258
    private static final int BACKLOG = 500; //150 - 300

    private static final String RELOAD = "reload";
    private static final String EXIT = "exit";


    private static NpawConfig npawConfig;
    private static HostManager hostManager;


    public static void main(String[] args) {

        // Load configuration
        System.out.println("\n[INFO] Searching for configuration...");
        npawConfig = NpawConfig.getInstance();
        hostManager = HostManager.getInstance();

        Map<String, Client> clients = npawConfig.loadClientsFromFile();
        npawConfig.setUpClients(clients);
        // Save config in case the file didn't exists
        npawConfig.saveClientsToFile();

        System.out.println("\n[INFO] Starting the WebServer...");
        //Create the Server at PORT & limited THREAD count
        new Thread(new NpawServer(PORT, MAX_THREADS, BACKLOG)).start();

        // Wait a little and listen for incoming commands
        try {
            Thread.sleep(1000);
            listenForCommands();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private static void listenForCommands(){

        Scanner sc = new Scanner(System.in);
        String cmd;

        do{
            System.out.println("\nAvailable Commands : [ RELOAD , EXIT ] :");
            cmd = sc.nextLine();

            if(cmd.equalsIgnoreCase(RELOAD)) {

                Map<String, Client> clients = npawConfig.loadClientsFromFile();
                npawConfig.setUpClients(clients);
                npawConfig.saveClientsToFile();

                // Host manager: We will make sure to delete de clusters that
                // are not longer in config since they have been deleted they are in memory and
                // we want to free it
                hostManager.reloadHosts();

                System.out.println("config.json reloaded correctly!");

            }else{
                System.err.println("The Command " + cmd + " does not exists");
            }

        }while(!cmd.equalsIgnoreCase(EXIT));

    }
}
