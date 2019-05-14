package me.victorml.npaw.model;

import java.util.Map;
import java.util.TreeMap;

public class Device {

    private String name;
    private String pluginVersion;
    private int pingTime;
    private Map<String, Host> hosts = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    public Device(String name, String pluginVersion, int pingTime) {
        this.name = name;
        this.pluginVersion = pluginVersion;
        this.pingTime = pingTime;
    }

    public Device(String name,String pluginVersion, int pingTime, Map<String, Host> hosts) {
        this.name = name;
        this.pluginVersion = pluginVersion;
        this.pingTime = pingTime;
        this.hosts = hosts;
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

    public Map<String, Host> getHosts() {
        return hosts;
    }

    public void setHosts(Map<String, Host> hosts) {
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
