package de.ones.lunch.data;

public class UserTimeItem {
	private int id;
	private String firstname;
	private Long time;
	
	public UserTimeItem(int i, String f, Long t) {
		id = i;
		firstname = f;
		time = t;
	}
	
	public int getId() {
		return id;
	}
	
	public String getFirstname() {
		return firstname;
	}
	
	public Long getTime() {
		return time;
	}
	
}
