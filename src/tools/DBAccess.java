package tools;

import java.util.ArrayList;
import java.util.Date;

import de.ones.lunch.data.DataVault;
import de.ones.lunch.data.FavoriteItem;
import de.ones.lunch.data.RestaurantItem;
import de.ones.lunch.data.RestaurantUserItem;
import de.ones.lunch.data.UserItem;
import de.ones.lunch.data.UserTimeItem;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBAccess extends SQLiteOpenHelper {
	private SQLiteDatabase db;
	private DataVault dataV = DataVault.getInstance();
	
	public DBAccess(Context activity, String dbName) {
		super(activity, dbName, null,1);
		
		db = getWritableDatabase();
	}
	
	@Override
	public void onCreate(SQLiteDatabase arg0) {
		try{
			String tableRest = "CREATE TABLE restaurants(id INTEGER PRIMARY KEY NOT NULL, name VARCHAR(255), info TEXT, link TEXT)";
			arg0.execSQL(tableRest);
			String tableRestUser = "CREATE TABLE restaurants_users(restaurants_id INTEGER NOT NULL, users_id INTEGER PRIMARY KEY NOT NULL, when_time LONG NOT NULL)";
			arg0.execSQL(tableRestUser);
			String tableUser = "CREATE TABLE users(id INTEGER PRIMARY KEY NOT NULL, name VARCHAR(255) NOT NULL, pwd VARCHAR(255) NOT NULL )";
			arg0.execSQL(tableUser);
			String tableFavorits = "CREATE TABLE favorits(id INTEGER PRIMARY KEY NOT NULL)";
			arg0.execSQL(tableFavorits);
		}catch(Exception e){
			Log.d("nweber", "error dbaccess oncreate"+e.getMessage());
		}
	}

	public void addUser(UserItem ui){
		ContentValues data = new ContentValues();
		data.put("name", ui.getName());
		data.put("id", ui.getId());
		try{
			db.insertOrThrow("users", null, data);
		}catch(SQLException e){
			Log.d("nweber", "sqlexception" + e.getMessage());
		}
	}
	
	public void addRestaurant(RestaurantItem ri){
		ContentValues data = new ContentValues();
		data.put("name", ri.getName());
		data.put("info", ri.getInfo());
		data.put("id", ri.getId());
		data.put("link", ri.getLink());
		try{
			db.insertOrThrow("restaurants", null, data);
		}catch(SQLException e){
			Log.d("nweber", "sqlexception" + e.getMessage());
		}
	}
	
	public void addRestaurantUser(RestaurantUserItem rui){
		ContentValues data = new ContentValues();
		data.put("restaurants_id", rui.getRestId());
		data.put("users_id", rui.getUserId());
		data.put("when_time", rui.getWhenTime());
		try{
			db.insertOrThrow("restaurants_users", null, data);
		}catch(SQLException e){
			if(dataV.getUser() != String.valueOf(rui.getUserId()))db.update("restaurants_users", data, "users_id="+rui.getUserId(),null);
			Log.d("nweber", "sqlRestUserexception" + e.getMessage());
		}
	}
	
	public RestaurantUserItem getRestaurantUser(int uId){
		RestaurantUserItem rui;
		Cursor cur = db.query("restaurants_users", null, "users_id="+uId, null, null, null, null);
		if(cur.getCount() >= 1)
		{
			cur.moveToFirst();
			rui = new RestaurantUserItem(cur.getInt(0), cur.getInt(1), cur.getLong(2));
			cur.close();
			return rui;
		}
		cur.close();
		
		return null;
	}
	
	public void addFavorite(int favId){
		ContentValues data = new ContentValues();
		data.put("id", favId);
		try{
			db.insertOrThrow("favorits", null, data);
		}catch(SQLException e){
			Log.d("nweber", "sqlexception" + e.getMessage());
		}
	}
	
	public void deleteFavorite(int favId){
		db.delete("favorits", "id="+favId, null);
	}
	
	public RestaurantItem getRestaurantById(int id){
		
		RestaurantItem ri;
		Cursor cur = db.query("restaurants", null, "id="+id, null, null, null, null);
		cur.moveToFirst();
		ri = new RestaurantItem(cur.getInt(0),cur.getString(1), cur.getString(2), cur.getString(3));
		cur.close();
		
		return ri;
	}
	
	public ArrayList<UserTimeItem> getRestUserByDay(int restId, Long whenTime, int userId){
		ArrayList<UserTimeItem> ui = new ArrayList<UserTimeItem>();
		
		Date dat = new Date(whenTime);
		dat.setHours(0);
		dat.setMinutes(0);
		
		Cursor cur = db.query("restaurants_users", null, "restaurants_id="+restId + " AND users_id<>"+userId+ " AND when_time>="+dat.getTime()+" AND when_time<>"+whenTime, null, null, null, null);
		cur.moveToFirst();
		UserTimeItem tempUi;
		for (int i = 0; i < cur.getCount(); i++) {
			Cursor temp = db.query("users", null, "id="+cur.getInt(1), null, null, null, null);
			temp.moveToFirst();
			tempUi = new UserTimeItem(temp.getInt(0), temp.getString(1), cur.getLong(2));
			temp.close();
			ui.add(tempUi);
			cur.moveToNext();
		}
		
		cur.close();
		return ui;
	}
	
	public ArrayList<UserItem> getRestUserByTime(int restId, Long whenTime, int userId){
		ArrayList<UserItem> ui = new ArrayList<UserItem>();
		Cursor cur = db.query("restaurants_users", null, "restaurants_id="+restId + " AND users_id<>"+userId+ " AND when_time="+whenTime, null, null, null, null);
		cur.moveToFirst();
		UserItem tempUi;
		for (int i = 0; i < cur.getCount(); i++) {
			Cursor temp = db.query("users", null, "id="+cur.getInt(1), null, null, null, null);
			temp.moveToFirst();
			tempUi = new UserItem(temp.getInt(0), temp.getString(1));
			temp.close();
			ui.add(tempUi);
			cur.moveToNext();
		}
		
		cur.close();
		return ui;
	}
	
	public ArrayList<UserItem> getRestUserList(int restId){
		ArrayList<UserItem> rul = new ArrayList<UserItem>();
		Cursor cur = db.query("restaurants_users", null, "restaurants_id="+restId, null, null, null, null);
		cur.moveToFirst();
		UserItem ui;
		for(int i = 0; i<cur.getCount();i++){
			Cursor temp = db.query("users", null, "id="+cur.getInt(1), null, null, null, null);
			temp.moveToFirst();
			ui = new UserItem(temp.getInt(0), temp.getString(1));
			temp.close();
			rul.add(ui);
			cur.moveToNext();
		}
		cur.close();
		
		return rul;
	}
	
	public ArrayList<FavoriteItem> getFavorites(){
		ArrayList<FavoriteItem> favArr = new ArrayList<FavoriteItem>();
		
		Cursor cur = db.query("favorits", null, null, null, null, null, null);
		cur.moveToFirst();
		FavoriteItem fi;
		for(int i = 0; i<cur.getCount();i++){
			fi = new FavoriteItem(Integer.parseInt(cur.getString(0)));
			favArr.add(fi);
			cur.moveToNext();
		}
		cur.close();
		
		return favArr;
	}
	
	public ArrayList<RestaurantItem> getRestaurants(){
		ArrayList<RestaurantItem> restArr = new ArrayList<RestaurantItem>();
		
		Cursor cur = db.query("restaurants", null, null, null, null, null, null);
		cur.moveToFirst();
		RestaurantItem ri;
		for(int i = 0; i<cur.getCount();i++){
			ri = new RestaurantItem(Integer.parseInt(cur.getString(0)), cur.getString(1), cur.getString(2), cur.getString(3));
			restArr.add(ri);
			cur.moveToNext();
		}
		cur.close();
		return restArr;
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public synchronized void close() {
		if(db != null){
			db.close();
			db = null;
		}
		super.close();
	}
	
	public void resume(){
		if(db == null)db = getWritableDatabase();
	}
	
}
