package interfejsy;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Book implements Serializable{

	private String author;
	private String name;
	private int ISBN;
	private boolean available=true;
	private List<String> waitList = new ArrayList<>();
	
	public Book(String author, String name, int ISBN) {
		this.author = author;
		this.ISBN = ISBN;
		this.name=name;
		
	}
	
	public boolean getState() {
		return available;
	}
	
	public String getName() {
		return name;
	}
	
	public String getAuthor() {
		return author;
	}
	
	public String getList() {
		return waitList.toString();
	}
	public int getListSize() {
		return waitList.size();
	}
	public void setState(boolean change) {
		available=change;
	}
	
	public void wait(String name) {
		waitList.add(name);
	}
	public void deleteList(){
		//IChat client=getClient();
		waitList.remove(0);
	}
	public boolean checkList() {
		return waitList.isEmpty();
	}
	public String getClient() {
		return waitList.get(0);
	}
}
