package me.victorml.npaw.model.hosts;

import me.victorml.npaw.config.NpawConfig;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

public class HostManager {

    private Map<String, Host> hosts;
    private static HostManager instance;

    private HostManager() {
        hosts = new ConcurrentSkipListMap<>(String.CASE_INSENSITIVE_ORDER);
    }

    public Host getHostByName(String name){
        return this.hosts.get(name);
    }

    public void createHostByName(String name){
        if(!this.hosts.containsKey(name)){
            this.hosts.put(name, new Host(name));
        }
    }

    public void reloadHosts(){
        NpawConfig npawConfig = NpawConfig.getInstance();
        ArrayList<String> currentClusters = npawConfig.getClusters();
        //Remove the keys that are not in live now! It means they have been removed in the reload
        for (String cluster : hosts.keySet()) {
            if (!currentClusters.contains(cluster)) {
                this.hosts.remove(cluster);
            }
        }
    }


    /**
     * Singleton instance for HostManager data
     *
     * @return HostManager
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
