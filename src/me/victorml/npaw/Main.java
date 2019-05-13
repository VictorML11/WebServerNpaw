package me.victorml.npaw;

import me.victorml.npaw.logic.NpawServer;

public class Main {

    private static final int PORT = 8080;
    private static final int MAX_THREADS = 128;
    private static final int BACKLOG = 150;


    public static void main(String[] args) {

        //Create the Server at PORT & limited THREAD count
        new Thread(new NpawServer(PORT, MAX_THREADS, BACKLOG)).start();

        //TODO: Command to load the config runtime from a file

    }
}
