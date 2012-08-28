package de.ones.lunch.data;

public class UserItem {
	private int id;
	private String name;
	
	public UserItem(int i, String f) {
		id = i;
		name = f;
	}
	
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
}
