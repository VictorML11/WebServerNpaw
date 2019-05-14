package me.victorml.npaw.model;

import java.util.Map;
import java.util.TreeMap;

public class HostManager {

    private Map<String, Host> hosts;
    private static HostManager instance;

    private HostManager() {
        hosts = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    }

    public Host getHostByName(String name){
        return this.hosts.get(name);
    }

    public void createHostByName(String name){
        if(!this.hosts.containsKey(name)){
            this.hosts.put(name, new Host(name));
        }
    }



    /**
     * Singleton instance for NpawConfig data
     *
     * @return NpawConfig
     */
    public static HostManager getInstance() {
        synchronized (HostManager.class) {
            if (instance == null)
                instance = new HostManager();
        }
        return instance;
    }

    @Override
    public String toString() {
        String r = "";
        for(Map.Entry<String,Host> entry : this.hosts.entrySet()){
            r += entry.getKey() + " " + entry.getValue().toString() + "\n";
        }
        return r;
    }
}
