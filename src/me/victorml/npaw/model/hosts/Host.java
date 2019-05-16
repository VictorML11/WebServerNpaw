package me.victorml.npaw.model.hosts;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

public class Host {

    private String name;
    private Queue<HostResponse> responses = new ConcurrentLinkedDeque<>();

    public Host(String name) {
        this.name = name;
    }

    /**
     * Add a request for certain ping time to the queue of responses
     * @param protocol protocol to use
     * @param time ping time response
     * @return HostResponse to be processed
     */
    public HostResponse addRequest(String protocol, int time) {
        HostResponse hr = new HostResponse(this.name, time);
        responses.add(hr);
        return hr;
    }

    public String getName() {
        return name;
    }

    public int getSize(){
        return this.responses.size();
    }

    @Override
    public String toString() {
        return "Host{" +
                "name='" + name + '\'' +
                '}';
    }
}
