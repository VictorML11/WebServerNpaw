package me.victorml.npaw.logic;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class NpawServer implements Runnable{

    private ServerSocket webServer;
    private ExecutorService threadPool;

    private int port;
    private int maxThreads;
    private int maxConnections;

    /**
     * Create a new NPawServer instance for accepting petitions
     * @param port port to run the web service
     * @param maxThreads number of threads to use in the pool
     * @param maxConnections maximum length of the queue of incoming connections
     */
    public NpawServer(int port, int maxThreads, int maxConnections) {
        this.port = port;
        this.maxThreads = maxThreads;
        this.maxConnections = maxConnections;
    }

    @Override
    public void run() {
        this.createServer();

        System.out.println("[INFO] Server is running at localhost port: " + this.port);

        while (!Thread.interrupted()) {

            try {
                Socket client = webServer.accept();
                threadPool.execute(new Thread(new NpawConnection(this, client)));
            } catch (IOException e) {
                System.out.println("Error trying to accept a client!");
                e.printStackTrace();
            }
        }
        closeServer();
    }

    private void createServer(){
        try{
            webServer = new ServerSocket(this.port,maxConnections);
            threadPool = Executors.newFixedThreadPool(this.maxThreads);
        } catch (IOException e) {
            System.out.println("Error creating the WebServer at port " + this.port);
            e.printStackTrace();
        }
    }

    /**
     * Close the server
     */
    public void closeServer() {

        // First try to close the ServerSocket
        try {
            webServer.close();
        } catch (IOException e) {
            System.err.println("Error found closing the WebServer socket");
            e.printStackTrace();
        }

        // Now shutdown the ThreadPool
        threadPool.shutdown();
        try {
            if (!threadPool.awaitTermination(10000, TimeUnit.MILLISECONDS)){
                threadPool.shutdownNow();
            }
        } catch (InterruptedException e) {
            System.err.println("Error found shutting down the threadPool");
            e.printStackTrace();
        }
    }


}
