package serwer;


import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import interfejsy.Book;
import interfejsy.Client;
import interfejsy.ICallback;
import interfejsy.IChat;

public class ChatServant extends UnicastRemoteObject implements IChat {
	 
	private Map<String, ICallback> people = new HashMap<>();
	private Map<Integer,Book> library=new HashMap<>();
	private ArrayList<Client> clients=new ArrayList<>(); 

	// konstruktor
	public ChatServant() throws RemoteException {
		Book book1= new Book("author1","book1",123);
		Book book2= new Book("author1","book2",456);
		Book book3= new Book("author2","book3",789);
		Book book4= new Book("author3","book4",000);
		Book book5= new Book("author4","book5",111);
		
		library.put(0, book1);
		library.put(1, book2);
		library.put(2, book3);
		library.put(3, book4);
		library.put(4, book5);
		
	}

	// Implements propagate() of IChat interface
	public boolean propagate(String nick, String text) throws RemoteException {
		System.out.println("Server.propagate(" + nick + ", " + text + ")");
		ICallback callback = people.get(nick);
		if (callback != null) {
			callback.inform(nick, text);
			return true;
		}
		return false;
	}

	public String list(String condition) {
		String message="books: \n";
		int allBooks=library.size();
		Book a;
		switch (condition) {
			case "a":
				for(int i=0;i<allBooks;i++) {
					a=library.get(i);
					message=showList(a,message)+"\n";
				}
			break;
			case "r":
				for(int i=0;i<allBooks;i++) {
					a=library.get(i);
					if(a.getState()) {
						message=showList(a,message)+"\n";
					}	
				}
			break;
			case "b":
				for(int i=0;i<allBooks;i++) {
					a=library.get(i);
					if(!a.getState()) {
						message=showList(a,message)+"\n";
					}
				}
			break;
		}
		return message;
	}
	
	public String showList(Book a,String message) {
 
		String bookName="";
		String authorName="";
		boolean bookState;
		bookName=a.getName();
		bookState=a.getState();
		String state="";
		if(bookState==true) {
			state="dostepna";
		}
		else state="wypozyczona";
		authorName=a.getAuthor();
		message=message+authorName+", "+bookName+", "+state;
		return message;
	}
	
	public String waitList(String idText) {
		int id=Integer.parseInt(idText);
		Book book=library.get(id);
		String waitlist=book.getList();
		int sizeWaitlist=book.getListSize();
		return sizeWaitlist+waitlist;	
	}
	
	public String borrow(String idText,String nick) throws RemoteException {
		String message="";
		int id=Integer.parseInt(idText);
		Book toBorrow=library.get(id);
		boolean isAvailable=toBorrow.getState();
		if(isAvailable==true) {
			toBorrow.setState(false);
			message="You borrowed1 "+toBorrow.getName();
		}
		else if(isAvailable==false) {
			toBorrow.wait(nick);
			message="You are on the waitlist for the book "+toBorrow.getName();
		}
		else {
			message="No book with given id";
		}
		return message;
	}
	
	public String returnBook(String idText) throws RemoteException {
		String message="";
		int id=Integer.parseInt(idText);
		Book book=library.get(id);
		String nextClient = null;
		book.setState(true);
		if(id<library.size()){
			if(book.checkList()) {//empty
				book.setState(true);
				message="Book returned successfully";
			}
			else {
				nextClient=book.getClient();
				book.deleteList();
				borrow(idText,nextClient);
				ICallback callback = people.get(nextClient);
				if (callback != null) {
					callback.inform2(nextClient, "You borrowed "+book.getName()+" from the waitlist");
				}
				message="Book passed to a person on the waitlist";
			}
		}else message="This book is not from our library";
		return message;
	}
	
	// Implements register() of IChat interface
	public boolean register(String nick, ICallback n) throws RemoteException {
		
		System.out.println("Server.register(): " + nick);
		if (!people.containsKey(nick)) {
			people.put(nick, n);
			
			Client client = new Client(nick,n);
			clients.add(client);

			return true;
		}
		return false;
	}

	// Implements unregister() of IChat interface
	public boolean unregister(String nick) throws RemoteException {
		if (people.remove(nick) != null) {
			System.out.println("Server.unregister(): " + nick);
			
			return true;
		}
		return false;
	}

	// Implements inform() of IChat interface
	public Vector<String> inform(String nick) throws RemoteException {
		Set<String> set = people.keySet();
		Vector<String> v = new Vector<String>();
		for (String s : set)
			if (s.matches(nick))
				v.add(s);
		return v;
	}

}