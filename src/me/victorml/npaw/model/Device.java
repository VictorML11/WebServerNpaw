package me.victorml.npaw.model;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class Device {

    private String name;
    private String pluginVersion;
    private int pingTime;
    private Map<String, HostInfo> hosts = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    private transient int currentConnetions = 0;
    private transient ArrayList<String> connections = new ArrayList<>();


    public Device(String name, String pluginVersion, int pingTime) {
        this.name = name;
        this.pluginVersion = pluginVersion;
        this.pingTime = pingTime;
        this.currentConnetions = 0;
    }

    public Device(String name,String pluginVersion, int pingTime, Map<String, HostInfo> hosts) {
        this.name = name;
        this.pluginVersion = pluginVersion;
        this.pingTime = pingTime;
        this.hosts = hosts;
        this.currentConnetions = 0;
    }

    public void populateConnections(){
        this.connections = new ArrayList<>();
        for(HostInfo hi : hosts.values()){
            //Try to create the host if it does not exist!
            HostManager.getInstance().createHostByName(hi.getName());
            int i = 0;
            do{
                connections.add(hi.getName());
                i++;
            }while(i < hi.getCharge());
        }
    }


    public String getHostNameForConnection(int currentConnetions){
        return connections.get(currentConnetions%100);
    }


    public synchronized int getCurrentConnetions() {
        return currentConnetions;
    }

    public synchronized void setCurrentConnetions(int currentConnetions) {
        this.currentConnetions = currentConnetions;
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
        return "Device{" +
                "pluginVersion='" + pluginVersion + '\'' +
                ", pingTime=" + pingTime +
                ", hosts=" + hosts +
                '}';
    }
}
