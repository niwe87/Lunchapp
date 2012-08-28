package de.ones.lunch.data;

import tools.DBAccess;


public class DataVault {
	private static DataVault instance = null;
	
	private DBAccess db;
	private String user;
	
	public DataVault() {
		// TODO Auto-generated constructor stub
	}
	
	public static DataVault getInstance() {
		if(instance == null){
			instance = new DataVault();
		}
		return instance;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public DBAccess getDb() {
		return db;
	}

	public void setDb(DBAccess db) {
		this.db = db;
	}

}
