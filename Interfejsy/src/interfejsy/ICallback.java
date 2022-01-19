package interfejsy;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ICallback extends Remote {
	public void inform(String nick, String text) throws RemoteException;

	public void inform2(String nextClient, String string) throws RemoteException;
}
