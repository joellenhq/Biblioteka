package client;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import interfejsy.ICallback;

public class ClientCallback extends UnicastRemoteObject implements ICallback {
	public ClientCallback() throws RemoteException {
		super();
	}
	// metoda implementujaca interfejs ICallback
	public void inform(String nick, String text) throws RemoteException {
		System.out.println(nick + " wrote: " + text);   
	}
	public void inform2(String nick, String text) throws RemoteException {
		System.out.println(nick + ": " + text);   
	}
}

