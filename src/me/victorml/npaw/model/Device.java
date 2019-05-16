package me.victorml.npaw.model;

import me.victorml.npaw.model.hosts.HostManager;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class Device {

    private String name;
    private String pluginVersion;
    private int pingTime;
    private Map<String, HostInfo> hosts = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    // Data that are part of run time info
    private transient int currentConnetions = 0;
    private transient ArrayList<String> connections = new ArrayList<>();
    private transient int maxSize = 100;


    public Device(String name, String pluginVersion, int pingTime) {
        this.name = name;
        this.pluginVersion = pluginVersion;
        this.pingTime = pingTime;
        this.currentConnetions = 0;
    }

    public Device(String name, String pluginVersion, int pingTime, Map<String, HostInfo> hosts) {
        this.name = name;
        this.pluginVersion = pluginVersion;
        this.pingTime = pingTime;
        this.hosts = hosts;
        this.currentConnetions = 0;
    }

    /**
     * Pre-calculate the probabilistic to distribute connections and
     * Create the clusters if they are not running
     */
    public void populateConnections() {
        // I do it this way because calculating it for each connection
        // its very resource demanding specially because of (sync - currentConnections)
        this.connections = new ArrayList<>();
        for (HostInfo hi : hosts.values()) {

            // Try to create the host if it does not exist!
            HostManager.getInstance().createHostByName(hi.getName());

            // Populate connections
            int i = 0;
            do {
                connections.add(hi.getName());
                i++;
            } while (i < hi.getCharge());
        }

        // Verify that there are no nulls! If so
        // report a warning! And add set a maxSize to avoid errors
        int size = connections.size();

        if(size != 100){
            System.err.println("The device: " + this.name + " has up to " + size + " connections filled! \n" +
                    "The distribution is not as expected over 100%");
            System.err.println("Consider checking the configuration to solve this problem!");
            System.err.println("Set the Device clusters charge so the sum of all of them is 100 [Non-Floating point numbers]");
        }

        this.maxSize = size;
    }

    /**
     * Get the (Cluster - host) for the current connection
     *
     * @param connection Connection number
     * @return String - Host/Cluster name
     */
    public String getHostNameForConnection(int connection) {
        return connections.get(connection % maxSize);
    }

    /**
     * Get the current connections - It is synchronized
     * important to do that but resource demanding! Keep that in mind.
     *
     * @return int with currentConnections
     */
    public synchronized int getCurrentConnetions() {
        return currentConnetions;
    }

    /**
     * Set the current connections to a certain ammount - It is synchronized
     * important to do that but it is resource demanding! Keep that in mind.
     *
     * @param connections amount of connections to set
     */
    public synchronized void setCurrentConnetions(int connections) {
        this.currentConnetions = connections;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPluginVersion() {
        return pluginVersion;
    }

    public void setPluginVersion(String pluginVersion) {
        this.pluginVersion = pluginVersion;
    }

    public int getPingTime() {
        return pingTime;
    }

    public void setPingTime(int pingTime) {
        this.pingTime = pingTime;
    }

    public Map<String, HostInfo> getHosts() {
        return hosts;
    }

    public void setHosts(Map<String, HostInfo> hosts) {
        this.hosts = hosts;
    }

    @Override
    public String toString() {
        StringBuilder r = new StringBuilder(
                "Device{" +
                "name='" + name + '\'' +
                ", pluginVersion='" + pluginVersion + '\'' +
                ", pingTime=" + pingTime +
                ", currentConnetions=" + currentConnetions +
                ", connections=" + connections);

        this.hosts.forEach((key, value) -> {
            r.append("HostName=").append(key).append("\n");
            r.append(value.toString());
        });

        return r.toString();
    }
}
