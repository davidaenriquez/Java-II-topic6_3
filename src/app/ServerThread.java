package app;

import java.io.IOException;

/**
 * The ServerThread class represents a thread that starts an instance of the Server.
 * It extends the Thread class and overrides the run() method to execute the server logic.
 */
public class ServerThread extends Thread {

    /**
     * Override the run() method to execute the server logic.
     */
    @Override
    public void run() {
        // Create an instance of a Server
        Server server = new Server();

        try {
            // Start the Server on port 6666 (which will not return until the Shutdown Command is received)
            server.start(6666);

            // On exit, clean everything up which will exit this thread
            server.cleanup();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}