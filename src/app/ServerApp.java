package app;

/**
 * The ServerApp class demonstrates the usage of ServerThread in the app package.
 * It creates an instance of ServerThread, starts the thread, and monitors its status
 * by printing dots to the console at 5-second intervals until the thread finishes.
 */
public class ServerApp {

    /**
     * The main method serves as the entry point of the ServerApp program.
     * It creates an instance of ServerThread, starts the thread, and monitors its status.
     *
     * @param args Command-line arguments (not used in this program).
     */
    public static void main(String[] args) {
        // Create an instance of ServerThread
        ServerThread serverThread = new ServerThread();

        // Start the thread
        serverThread.start();

        // Print dots while the thread is running
        while (serverThread.isAlive()) {
            System.out.print(".");

            try {
                // Sleep for 5 seconds
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("\nServerApp: ServerThread has finished.");
    }
}