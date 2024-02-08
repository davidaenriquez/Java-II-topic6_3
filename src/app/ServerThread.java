package app;

import java.io.IOException;

public class ServerThread extends Thread {
	// You need to override the run() to put the code that will run in this thread
	public void run() {
		// Create an instance of a Server
		// Start the Server on port 6666 (which will not return until the Shutdown Command is received)
		// and then on exit clean everything up which will exit this thread
		Server server = new Server();
		try {
			server.start(6666);
			server.cleanup();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
