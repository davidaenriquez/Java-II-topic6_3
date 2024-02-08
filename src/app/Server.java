package app;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	
	private ServerSocket serverSocket;
	private Socket clientSocket;
	private PrintWriter out;
	private BufferedReader in;
	
	/**
	 * Start the Server and wait for the connections on the specified port.
	 * 
	 * @param port
	 * @throws IOException
	 */
	public void start(int port) throws IOException {
		// Wait for a Client connection
		System.out.println("Waiting for a Client connection..........");
		serverSocket = new ServerSocket(port);
		clientSocket = serverSocket.accept();
		
		// If you get here then a Client connected to this Server
		// so create some input and output network buffers
		System.out.println("Received a Client connected on port " + clientSocket.getLocalPort());
		out = new PrintWriter(clientSocket.getOutputStream(), true);
		in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		
		// Wait for Command (string that is terminated by a line feed character)
		String inputLine;
		while((inputLine = in.readLine()) != null) {
			// If period command then shut the Server down
			if (".".equals(inputLine)) {
				System.out.println("Got a message to shut the Server down.");
				out.println("QUIT");
				break;
			}
			else {
				// Echo an acknowledgement back to the Client
				// that Command was processed successfully
				System.out.println("Got a message of: " + inputLine);
				out.println("OK");
			}
		}
		// Exit message that Server is shut down
		System.out.println("Server is shut down.");
	}
	
	/**
	 * Cleanup logic to close all the network connections.
	 * 
	 * @throws IOException
	 */
	public void cleanup() throws IOException {
		// Close all input and output network buffers and sockets
		in.close();
		out.close();
		clientSocket.close();
		serverSocket.close();
	}

	
	public static void main(String[] args) throws IOException {
		// Create an instance of the server
		// Start the Server on port 6666 (which will not return until the Shutdown Command is received)
		// and then on exit clean everything up
		Server server = new Server();
		server.start(6666);
		server.cleanup();
	}

}
