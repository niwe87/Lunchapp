package de.ones.lunch.data;

public class RestaurantItem {
	private int id;
	private String name;
	private String info;
	private String link;
	
	public RestaurantItem(int i, String nam, String txt, String l) {
		id = i;
		name = nam;
		info = txt;
		link = l;
	}
	
	public int getId() {
		return id;
	}
	public String getInfo() {
		return info;
	}
	public String getName() {
		return name;
	}
	
	public String getLink() {
		return link;
	}
}
