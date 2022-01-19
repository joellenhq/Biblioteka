package client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Vector;

import interfejsy.ICallback;
import interfejsy.IChat;

import java.util.Scanner;

public class ChatClient {
	private Scanner userInput = new Scanner(System.in);
	String username;
	IChat remoteObject; // a remote object reference
	ICallback callback;

	public static void main(String[] args) {
		if (args.length < 1) {
			System.out.println("Usage: ChatClient <server host name>");
			System.exit(-1);
		}
		new ChatClient(args[0]);
	}

	public ChatClient(String hostname) {
		System.out.println("Enter client name: ");
		if (userInput.hasNextLine())
			username = userInput.nextLine();
		Registry reg; // the remote objects registry
		try {
			// getting a reference to the object names registry
			reg = LocateRegistry.getRegistry(hostname);
			// find a remote object by its name
			remoteObject = (IChat) reg.lookup("ChatServer");
			callback = new ClientCallback();
			// calling methods of a remote object
			remoteObject.register(username, callback);
			loop();
			remoteObject.unregister(username);
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
	}

	void inform() {
		String line;
		System.out.print("Enter the user name filter regular expression: ");
		if (userInput.hasNextLine()) {
			line = userInput.nextLine();
			Vector<String> vec = null;
			try {
				vec = remoteObject.inform(line);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			System.out.println("There are " + vec.size() + " user(s):");
			for (String s : vec)
				System.out.println(" - " + s);
		}
	}

	private void propagate() {
		String uname;
		System.out.print("Enter the name of addressee: ");
		if (userInput.hasNextLine()) {
			uname = userInput.nextLine();
			String utext;
			System.out.print("Enter the text you want to send him: ");
			if (userInput.hasNextLine()) {
				utext = userInput.nextLine();
				try {
					remoteObject.propagate(uname, utext);
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private void list() {
		String lista="";
		String condition;
		while (true) {
		System.out.println(prompt() + "[a]ll, [b]orrowed, [r]eturned,[q]uit: ");
		if (userInput.hasNextLine()) {
			condition = userInput.nextLine();
			if (!condition.matches("[abrq]")) {
				System.out.println("You entered invalid command !");
				continue;
			}
			if(condition.matches("[q]")){
				break;
			}
		
			try {
				lista=remoteObject.list(condition);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			System.out.println(lista);
		}
		}
	}
	private void borrow() {
		String id="";
		String message="";
		System.out.print("Enter the number of the book: ");
		if (userInput.hasNextLine()) { 
			id = userInput.nextLine();
		}
		try {
			message=remoteObject.borrow(id,username);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		System.out.println(message);
	}
	private void returnBook() {
		String id="";
		String message="";
		System.out.print("Enter the number of the book: ");
		if (userInput.hasNextLine()) {
			id = userInput.nextLine();
		}
		try {
			message=remoteObject.returnBook(id);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		System.out.println(message);
	}
	
	private void waitList() {
		String id="";
		String message="";
		System.out.print("Enter the number of the book: ");
		if (userInput.hasNextLine()) {
			id = userInput.nextLine();
		}
		try {
			message=remoteObject.waitList(id);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		System.out.println(message);
	}
	 
	private String prompt() {
		return "[" + username + "] > ";
	}

	void loop() {
		while (true) {
			String line;
			System.out.println(prompt() + "[i]nform, [p]ropagate, [q]uit, [l]ist, [b]orrow, [r]eturn, [w]aitlist: ");
			if (userInput.hasNextLine()) {
				line = userInput.nextLine();
				if (!line.matches("[ipqlbrw]")) {
					System.out.println("You entered invalid command !");
					continue;
				}
				switch (line) {
				case "i":
					inform();
					break;
				case "p":
					propagate();
					break;
				case "l":
					list();
					break;
				case "b":
					borrow();
					break;
				case "r":
					returnBook();
					break;	
				case "w":
					waitList();
					break;	
				case "q":
					return;
				}
			}
		}
	}

}

