package interfejsy;


import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Vector;

public interface IChat extends Remote {
   boolean register(String nick, ICallback n) throws RemoteException;
   boolean unregister(String nick) throws RemoteException;
   boolean propagate(String nick, String message) throws RemoteException;
   String list(String condition) throws RemoteException;
   String borrow(String id,String nick) throws RemoteException;
   String returnBook(String id) throws RemoteException;
   String waitList(String id) throws RemoteException;
   Vector<String> inform(String nick) throws RemoteException;
}
