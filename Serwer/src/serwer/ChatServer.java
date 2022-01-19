package serwer;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ChatServer {
	Registry reg; // the remote objects registry
	ChatServant servant; // the servant class 
	
	public static void main(String[] args) {
		try {
		  new ChatServer(); // tu tworzymy obiekt wywolujac konstruktora
		} catch (RemoteException e) {
			e.printStackTrace(); 
			System.exit(1);
	} }
	
	// konstruktor
	protected ChatServer() throws RemoteException {
		try { 
			// create RMI registry working on the 1099 port (default)
		  reg = LocateRegistry.createRegistry(1099); 
		  servant = new ChatServant();   	  // creation of a remote object/
		  reg.rebind("ChatServer", servant);  // linking the name to the object
		  System.out.println("ChatServer READY");
		} catch(RemoteException e) {
		  e.printStackTrace(); 
		  throw e;
	}	}
}
