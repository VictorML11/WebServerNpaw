package me.victorml.npaw.logic;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class NpawConnection implements Runnable{

    private NpawServer npawServer;
    private Socket client;

    private InputStream in;
    private OutputStream out;

    public NpawConnection(NpawServer npawServer, Socket client) {
        this.npawServer = npawServer;
        this.client = client;
        this.setStreams();
    }


    @Override
    public void run() {

        Request request = Utils.getClientRequest(in);

    }


    private void setStreams(){
        try {
            in = client.getInputStream();
            out = client.getOutputStream();
        } catch (IOException e) {
            System.out.println("Error in the client Socket In/Out");
            e.printStackTrace();
        }
    }



}
