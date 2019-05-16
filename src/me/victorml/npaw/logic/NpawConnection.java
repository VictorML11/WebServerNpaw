package me.victorml.npaw.logic;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class NpawConnection implements Runnable {

    private NpawServer npawServer;
    private Socket client;

    private InputStream in;
    private OutputStream out;

    /**
     * Create a new NpawConnection for certain petition
     * @param npawServer server running
     * @param client
     */
    public NpawConnection(NpawServer npawServer, Socket client) {
        this.npawServer = npawServer;
        this.client = client;
    }


    @Override
    public void run() {
        try {

            this.setStreams();
            // Parse the request from the InputStream of the client
            Request request = Utils.getClientRequest(in);

            if (request != null) {
                // Get the response from the host and send it to the client
                Response response = Utils.getResponse(request);
                this.sendResponse(response);
            }

            this.closeStreams();

        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            closeClient();
        }

    }

    /**
     * Send the response from the host to the client
     * @param resp Response to send to the client
     */
    public void sendResponse(Response resp) {
        String sresp = resp.toString();
        PrintWriter writer = new PrintWriter(out);
        writer.write(sresp);
        writer.flush();
    }


    private void setStreams() throws IOException {
        in = client.getInputStream();
        out = client.getOutputStream();
    }

    private void closeStreams() throws IOException {
        in.close();
        out.close();
    }

    private void closeClient(){
        try {
            client.close();
        } catch (IOException e) {
            System.err.println("Error closing client connection socket");
            e.printStackTrace();
        }
    }


}
