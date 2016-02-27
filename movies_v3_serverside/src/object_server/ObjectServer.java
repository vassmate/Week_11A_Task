package object_server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import serializable_classes.Product;

public class ObjectServer {

	enum ServerMode {
		LOAD, SAVE
	}

	private ServerMode mode;
	private File file = new File("C:/Users/VMate/Workspace/movies_v3_serverside/saves/products.ser");
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private DataInputStream dataIn;
	private DataOutputStream dataOut;
	private ServerSocket server;
	private Socket connection;

	// 1. Start the server
	// 2. Wait for connections
	// 3. After the client connected set up the streams
	// 4. Wait for commands from client
	// 5. Safely shut down the streams and the connection
	// 6. Finally break the loop
	public void run() {
		try {
			server = new ServerSocket(8461, 2);
			while (true) {
				try {
					waitForConnection();
					setupStreams();
					whileConnsUp();
					closeAll();
					break;
				} catch (EOFException eofException) {
					serverMessage("EOFException raised! Server disconnected!");
					break;
				} catch (IOException ioException) {
					serverMessage("IOException raised! Server disconnected!");
					break;
				} catch (ClassNotFoundException e) {
					serverMessage("Unknown object/class recieved! Serialization aborted!");
					e.printStackTrace();
				}
			}
		} catch (IOException ioException) {
			serverMessage("Server ended connection...");
		}
	}

	// Wait for client to connect
	private void waitForConnection() throws IOException {
		serverMessage("Waiting for client to connect...");
		connection = server.accept();
		serverMessage("Client connected from: " + connection.getRemoteSocketAddress());
	}

	// Setup the Data and Object streams between the sever and client
	private void setupStreams() throws IOException {
		dataIn = new DataInputStream(connection.getInputStream());
		dataOut = new DataOutputStream(connection.getOutputStream());
		input = new ObjectInputStream(connection.getInputStream());
		output = new ObjectOutputStream(connection.getOutputStream());
		serverMessage("Streams are now setup!");
	}

	// Save the incoming Object stream data to a .sr file
	@SuppressWarnings("unchecked")
	private void save() throws ClassNotFoundException, IOException {
		List<Product> productList = (List<Product>) input.readObject();
		serverMessage("Data to save: ");
		serverMessage(productList.toString());
		SerializeObjects.serializeObj(productList);
		serverMessage("Received data processed!");
	}

	// Load data from an existing .sr file
	// If the file not exists sends a warning to the client
	private void load() throws IOException {
		serverMessage("Sending data to client from this file:");
		serverMessage("Location: " + file);
		List<Product> stuff = DeserializeObjects.deserializeObj(file);
		output.writeObject(stuff);
		serverMessage("Data sent!");
	}

	// Switch-case for the server modes
	private void switchMode() throws ClassNotFoundException, IOException {
		switch (mode) {
		case SAVE:
			save();
			break;
		case LOAD:
			load();
			break;
		default:
			break;
		}
	}

	// While the connection is live the loop checks for incoming commands
	private void whileConnsUp() throws IOException, ClassNotFoundException {
		checkIfFileExists();
		String inCom = (String) dataIn.readUTF();
		while (!(inCom.equals("EXIT"))) {
			if (inCom.equals("PUT")) {
				mode = ServerMode.SAVE;
				switchMode();
			} else if (inCom.equals("GET")) {
				mode = ServerMode.LOAD;
				switchMode();
			}
			inCom = (String) dataIn.readUTF();
		}
	}

	// If the client sent an EXIT command the whileConnsUp() method shuts down
	// and the closeAll() method closes the streams and connections safely
	private void closeAll() throws IOException {
		serverMessage("Closing connections...");
		input.close();
		output.close();
		dataIn.close();
		dataOut.close();
		connection.close();
	}

	// Checks if the save file is exists or not
	// Warns the client side
	private void checkIfFileExists() throws IOException {
		if (file.exists()) {
			dataOut.writeUTF("[SERVER] Save file is apparent!");
		} else if (!(file.exists())) {
			dataOut.writeUTF("[SERVER WARNING] Save file is not apparent! Use PUT command to save data!");
		}
	}

	// Simple method for showing messages on console
	public void serverMessage(String meassge) {
		System.out.println("[SERVER] " + meassge);
	}
}
