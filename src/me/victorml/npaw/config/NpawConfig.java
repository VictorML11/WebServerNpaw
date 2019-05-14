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

    private static NpawConfig instance;
    private FileUtil futil;
    private File configFile;
    private Map<String, Client> clients = new ConcurrentSkipListMap<>(String.CASE_INSENSITIVE_ORDER);

    private NpawConfig() {
        configFile = new File("config.json");
        futil = new FileUtil(configFile);
    }

    public boolean existsClient(String client) {
        return this.clients.containsKey(client);
    }

    public Client getClient(String client) {
        return this.clients.get(client);
    }

    public void setUpClients(Map<String, Client> clients) {

        //Make sure all is case insensitive since a human can put in the keys upper case
        Map<String, Client> clientes = new ConcurrentSkipListMap<>(String.CASE_INSENSITIVE_ORDER);
        clientes.putAll(clients);
        for (Client c : clientes.values()) {
            Map<String, Device> devices = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
            devices.putAll(c.getTargetDevices());
            c.setTargetDevices(devices);
            for (Device d : c.getTargetDevices().values()) {
                Map<String, HostInfo> hosts = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
                hosts.putAll(d.getHosts());
                d.setHosts(hosts);
                //Populate the array of connections and HostManager
                d.populateConnections();

            }
        }
        this.clients = clientes;
    }

    public ArrayList<String> getClusters(){
        //Using lambdas to get what clusters exists with no repetitions
        return clients.values().stream().flatMap(c ->
                c.getTargetDevices().values().stream()).flatMap(d ->
                d.getHosts().keySet().stream()).distinct().collect
                (Collectors.toCollection(ArrayList::new));

    }


    /**
     * Load all NpawConfig from System File
     *
     * @return Map<String, Client>
     */
    public Map<String, Client> loadClientsFromFile() {
        String content = futil.load();


        if (content == null) { // We will never reach this just in case something goes wrong
            System.out.println("Fatal ERROR! the config is completely null even the default one!");
            return new HashMap<>();
        }

        // Load from json
        return Main.GSON_CONFIG.fromJson(content, new TypeToken<Map<String, Client>>() {}.getType());
    }

    /* Save the current Clients Map data to the System File
     */
    public void saveClientsToFile() {
        String content = Main.GSON_CONFIG.toJson(this.clients);
        futil.save(content);
    }


    /**
     * Singleton instance for NpawConfig data
     *
     * @return NpawConfig
     */
    public static NpawConfig getInstance() {
        synchronized (NpawConfig.class) {
            if (instance == null)
                instance = new NpawConfig();
        }
        return instance;
    }

    public Map<String, Client> getClients() {
        return clients;
    }

    public void setClients(Map<String, Client> clients) {
        this.clients = clients;
    }
}
