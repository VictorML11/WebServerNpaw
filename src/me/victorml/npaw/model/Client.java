package me.victorml.npaw.model;

import java.util.Map;
import java.util.TreeMap;

public class Client {

    private String accountCode;
    private Map<String, Device> targetDevices =  new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    public Client(String accountCode) {
        this.accountCode = accountCode;
    }

    public Client(String accountCode, Map<String, Device> targetDevices) {
        this.accountCode = accountCode;
        this.targetDevices = targetDevices;
    }

    public Device getDevice(String device){
        return this.targetDevices.get(device);
    }

    public String getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

    public Map<String, Device> getTargetDevices() {
        return targetDevices;
    }

    public void setTargetDevices(Map<String, Device> targetDevices) {
        this.targetDevices = targetDevices;
    }

    @Override
    public String toString() {
        String r = "Account Code: " + this.accountCode +"\n";
        for(Map.Entry<String, Device> entry : this.targetDevices.entrySet()){
            r += "targetDevice = " + entry.getKey() +"\n";
            r += entry.getValue().toString() +"\n";
        }
        return r;
    }
}
