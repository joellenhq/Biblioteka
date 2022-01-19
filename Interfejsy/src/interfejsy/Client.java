package interfejsy;

import java.util.ArrayList;
import java.util.List;

public class Client {
	private String name;
	ICallback remoteObject;
	private List<String> myBooks=new ArrayList<>();
	public Client(String name, ICallback remoteObject) {	
		this.name = name;
		this.remoteObject = remoteObject;
	}
	public String getName() {
		return name;
	}
	public ICallback getReference() {
		return remoteObject;
	}
	public String getList() {
		return myBooks.toString();
	}
	public void addList(String name) {
		myBooks.add(name);
	}
	public void removeList(String name) {
		myBooks.remove(new String(name));
	}
	
}
