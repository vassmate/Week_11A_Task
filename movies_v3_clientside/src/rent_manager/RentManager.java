package rent_manager;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

import serializable_classes.Book;
import serializable_classes.Game;
import serializable_classes.Movie;
import serializable_classes.Product;

public class RentManager {
	enum Command {
		GET, PUT, EXIT
	}

	List<Product> productList;
	private Command command;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private DataOutputStream dataOut;
	private DataInputStream dataIn;
	private Socket connection;

	public RentManager(List<Product> pList) {
		this.productList = pList;
	}

	// 1. Setup connection with the server
	// 2. Setup streams to the server
	// 3. While connections are live check for input from user
	// 4. Before the loop is aborted with the EXIT command it will close the
	// connections and shuts down the streams
	public void connectToServer() {
		try {
			setupConnection();
			setupStreams();
			whileConnsUp();
			closeAll();
		} catch (IOException ioE) {
			clientMessage("Something went wrong...");
		} catch (ClassNotFoundException e) {
			clientMessage("The server didn't send data!");
		}

	}

	// Sets up the connection with the server
	private void setupConnection() throws IOException {
		connection = new Socket("127.0.0.1", 8461);
		clientMessage("Connected to server!");
	}

	// Sets up the streams for communication with the server
	private void setupStreams() throws IOException {
		dataOut = new DataOutputStream(connection.getOutputStream());
		dataIn = new DataInputStream(connection.getInputStream());
		output = new ObjectOutputStream(connection.getOutputStream());
		input = new ObjectInputStream(connection.getInputStream());
		clientMessage("Streams are now setup!");
	}

	// Sends a list of Products to the server
	private void putData(List<Product> prodList) throws IOException {
		output.writeObject(prodList);
		clientMessage("Data sent to the server!");
	}

	// Receives the data from the server
	@SuppressWarnings("unchecked")
	private void getData() throws ClassNotFoundException, IOException {
		List<Product> incomingList = (List<Product>) input.readObject();
		clientMessage("Received data:");
		clientMessage(incomingList.toString());
		clientMessage("Persons in products:");
		printOutPersons(incomingList);
	}

	// Prints out the Persons in different products
	private void printOutPersons(List<Product> prodList) {
		int game_counter = 1;
		int movie_counter = 1;
		int book_counter = 1;
		for (Product product : prodList) {
			if (product instanceof Game) {
				Game game = (Game) product;
				clientMessage("GAM" + game_counter + " staff: " + game.getStaff().toString());
				++game_counter;
			} else if (product instanceof Movie) {
				Movie movie = (Movie) product;
				clientMessage("MOV" + movie_counter + " cast: " + movie.getCast().toString());
			} else if (product instanceof Book) {
				Book book = (Book) product;
				clientMessage("BOO" + book_counter + " author: " + book.getAuthor().toString());
			}
		}
	}

	// Switch-case for the commands
	// Sends the chosen command to the server
	// Initiates the putData() or getData() methods depending on the command
	private void switchToCommand() throws IOException, ClassNotFoundException {
		switch (command) {
		case PUT:
			dataOut.writeUTF("PUT");
			putData(productList);
			break;
		case GET:
			dataOut.writeUTF("GET");
			getData();
			break;
		case EXIT:
			dataOut.writeUTF("EXIT");
			break;
		default:
			break;
		}
	}

	// Checks for input from the user
	// If it gets EXIT command from the user it will first send it to the
	// server and it will break the loop just after that
	private void whileConnsUp() throws IOException, ClassNotFoundException {
		System.out.println("" + dataIn.readUTF());
		clientMessage("Please eneter one of these commands to proceed:");
		String inFromConsole = null;
		Scanner inScan = new Scanner(System.in);
		do {
			clientMessage("Valid commands: <GET, PUT, EXIT>");
			inFromConsole = inScan.nextLine();
			if (inFromConsole.equals("GET")) {
				command = Command.GET;
				switchToCommand();
			} else if (inFromConsole.equals("PUT")) {
				command = Command.PUT;
				switchToCommand();
			} else if (inFromConsole.equals("EXIT")) {
				command = Command.EXIT;
				switchToCommand();
			} else {
				clientMessage("Enter a valid command!");
			}
		} while (!(inFromConsole.equals("EXIT")));
		inScan.close();
	}

	// Closes the connection between the server as well as the streams
	private void closeAll() throws IOException {
		clientMessage("Closing connections...");
		input.close();
		output.close();
		dataOut.close();
		dataIn.close();
		connection.close();
	}

	// Prints out the messages on the console
	public void clientMessage(String meassge) {
		System.out.println("[CLIENT] " + meassge);
	}
}
