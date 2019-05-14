package me.victorml.npaw.model;

public class HostInfo {

    private String name;
    private float charge;

    public HostInfo(String name, float charge) {
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

    public void setCharge(float charge) {
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
