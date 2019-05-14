package me.victorml.npaw.model;

import java.util.UUID;

public class HostResponse {

    private String name;
    private int pingTime;
    private String id;

    public HostResponse(String name, int pingTime) {
        this.name = name;
        this.pingTime = pingTime;
        this.id = UUID.randomUUID().toString().replaceAll("-","");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPingTime() {
        return pingTime;
    }

    public void setPingTime(int pingTime) {
        this.pingTime = pingTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "HostResponse{" +
                "name='" + name + '\'' +
                ", pingTime=" + pingTime +
                ", id='" + id + '\'' +
                '}';
    }
}
