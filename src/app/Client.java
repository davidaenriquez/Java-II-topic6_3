package app;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	private Socket clientSocket;
	private PrintWriter out;
	private BufferedReader in;
	
	/**
	 * Connect to the remote Server on the specified IP Address and Port
	 * 
	 * @param ip
	 * @param port
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public void start(String ip, int port) throws UnknownHostException, IOException {
		
		// Connect to the Remote Server on the specified IP Address and Port
		clientSocket = new Socket(ip, port);
		
		// Create some input and output network buffers to
		// communicate back and forth with the Server
		out = new PrintWriter(clientSocket.getOutputStream(), true);
		in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
	}
	
	/**
	 * Send a Message to the Server.
	 * 
	 * @param msg
	 * @return
	 * @throws IOException
	 */
	public String sendMessage(String msg) throws IOException {
		// Send/Print a Message to Server with a terminating line feed
		out.println(msg);
		
		// Return the response from the Server
		return in.readLine();
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
	}

	/**
	 * Entry method for the Server application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// Create a Client and connect to the remote Server on the specified IP Address and Port
		Client client = new Client();
		try {
			client.start("127.0.0.1", 6666);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Send 10 Messages to the Server
		String response = null;
		for (int count=0; count<10; ++count) {
			// Send Message to the Server and on the 9th one send a Shutdown Command to Server
			String message;
			if (count != 9)
				message = "Hello from Client" + count;
			else
				message = ".";
			try {
				response = client.sendMessage(message);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			// Print out the Server response and if we get a QUIT response exit this program
			System.out.println("Server response was " + response);
			if (response.equals("QUIT"))
				break;
			
			// Sleep for a bit so the Server can run for awhile
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// On exit clean everything up
		try {
			client.cleanup();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
