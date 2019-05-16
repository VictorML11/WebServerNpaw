package me.victorml.npaw.model;

public class HostInfo {

    private String name;
    private int charge;

    public HostInfo(String name, int charge) {
        this.name = name;
        this.charge = charge;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getCharge() {
        return charge;
    }

    public void setCharge(int charge) {
        this.charge = charge;
    }

    @Override
    public String toString() {
        return "HostInfo{" +
                "name='" + name + '\'' +
                ", charge=" + charge +
                '}';
    }
}
