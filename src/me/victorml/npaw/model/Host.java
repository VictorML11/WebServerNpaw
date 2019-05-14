package me.victorml.npaw.model;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

public class Host {

    private String name;
    private Queue<HostResponse> responses = new ConcurrentLinkedDeque<>();

    public Host(String name) {
        this.name = name;
    }

    public HostResponse addRequest(String protocol, int time) {
        HostResponse hr = new HostResponse(this.name, time);
        responses.add(hr);
        return hr;
    }

    @Override
    public String toString() {
        return "Host{" +
                "name='" + name + '\'' +
                '}';
    }
}
