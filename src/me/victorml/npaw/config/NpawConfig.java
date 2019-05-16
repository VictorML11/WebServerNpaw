package me.victorml.npaw.config;

import com.google.gson.reflect.TypeToken;
import me.victorml.npaw.Main;
import me.victorml.npaw.model.Client;
import me.victorml.npaw.model.Device;
import me.victorml.npaw.model.HostInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.stream.Collectors;

public class NpawConfig {


    private FileUtil futil;
    private Map<String, Client> clients = new ConcurrentSkipListMap<>(String.CASE_INSENSITIVE_ORDER);

    private static NpawConfig instance;

    private NpawConfig() {
        File configFile = new File("config.json");
        futil = new FileUtil(configFile);
    }

    /**
     * Singleton instance for NpawConfig data
     * @return NpawConfig
     */
    public static NpawConfig getInstance() {
        synchronized (NpawConfig.class) {
            if (instance == null)
                instance = new NpawConfig();
        }
        return instance;
    }

    /**
     * Get a Client from its AccountCode
     * @param client client accountCode
     * @return Client
     */
    public Client getClient(String client) {
        return this.clients.get(client);
    }


    /**
     * Get all the clusters that are running among client devices
     * @return ArrayList of clusters in live
     */
    public ArrayList<String> getClusters(){
        //Using lambdas to get what clusters exists with no repetitions
        return clients.values().stream().flatMap(c ->
                c.getTargetDevices().values().stream()).flatMap(d ->
                d.getHosts().keySet().stream()).distinct().collect
                (Collectors.toCollection(ArrayList::new));

    }


    /**
     * Get Clients
     * @return Map<String, Client> clients
     */
    public Map<String, Client> getClients() {
        return clients;
    }

    /**
     * Set current clients to a new one
     * @param clients Clients
     */
    public void setClients(Map<String, Client> clients) {
        this.clients = clients;
    }


    ///////////////////
    // CONFIGURATION //
    ///////////////////

    /**
     * Load all NpawConfig from System File
     * @return Map<String, Client>
     */
    public Map<String, Client> loadClientsFromFile() {
        String content = futil.load();

        if (content == null) {
            // We will never reach this just in case something goes wrong
            System.out.println("Fatal ERROR! the config is completely null even the default one!");
            return new HashMap<>();
        }

        // Load from json using typeToken
        return Main.GSON_CONFIG.fromJson(content, new TypeToken<Map<String, Client>>() {}.getType());
    }

    /**
     *  Save the current Clients Map data to the System File
     */
    public void saveClientsToFile() {
        String content = Main.GSON_CONFIG.toJson(this.clients);
        futil.save(content);
    }

    /**
     * Set up all the configuration of clients making sure the keys are insensitive to upper case
     * This is done since config.json can be modified and anybody could put a key in upperCase
     * @param clients Current Clients Map
     */
    public void setUpClients(Map<String,Client> clients) {

        // Clients to case insensitive
        Map<String, Client> clientes = new ConcurrentSkipListMap<>(String.CASE_INSENSITIVE_ORDER);
        clientes.putAll(clients);

        for (Client c : clientes.values()) {
            // Client devices to case insensitive
            Map<String, Device> devices = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
            devices.putAll(c.getTargetDevices());
            c.setTargetDevices(devices);
            for (Device d : c.getTargetDevices().values()) {
                // Device's hosts to case insensitive
                Map<String, HostInfo> hosts = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
                hosts.putAll(d.getHosts());
                d.setHosts(hosts);
                //Populate the array of connections and HostManager
                d.populateConnections();
            }
        }
        // Set the case insensitive config
        this.clients = clientes;
    }

}
