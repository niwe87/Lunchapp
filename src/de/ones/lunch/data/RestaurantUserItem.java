package de.ones.lunch.data;

import java.util.Date;



public class RestaurantUserItem {
	private int restId;
	private int userId;
	private Date whenTime;
	
	public RestaurantUserItem(int rId, int uId, Long wt) {
		restId = rId;
		userId = uId;
		whenTime = new Date(wt);
	}
	
	public int getRestId() {
		return restId;
	}
	
	public int getUserId() {
		return userId;
	}
	
	public Long getWhenTime() {
		return whenTime.getTime();
	}
	
}
