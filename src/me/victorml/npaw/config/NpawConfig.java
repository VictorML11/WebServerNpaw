package me.victorml.npaw.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import me.victorml.npaw.model.Client;
import me.victorml.npaw.model.Device;
import me.victorml.npaw.model.Host;

import java.io.File;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentSkipListMap;

public class NpawConfig {

    private static NpawConfig instance;
    private FileUtil futil;
    private File configFile;
    private Map<String, Client> clients = new ConcurrentSkipListMap<>(String.CASE_INSENSITIVE_ORDER);

    public static final Gson GSON_CONFIG = new GsonBuilder()
            .setPrettyPrinting()
            .serializeNulls()
            .disableHtmlEscaping()
            .excludeFieldsWithModifiers(Modifier.TRANSIENT, Modifier.VOLATILE)
            .create();

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
                Map<String, Host> hosts = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
                hosts.putAll(d.getHosts());
                d.setHosts(hosts);
            }
        }
        this.clients = clientes;
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
        return GSON_CONFIG.fromJson(content, new TypeToken<Map<String, Client>>() {
        }.getType());
    }

    /* Save the current Clients Map data to the System File
     */
    public void saveClientsToFile() {
        String content = GSON_CONFIG.toJson(this.clients);
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
