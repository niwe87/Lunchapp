package threads;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.json.JSONArray;
import org.json.JSONException;

import tools.DBAccess;
import tools.DBConnector;
import android.content.Context;
import android.util.Log;
import de.ones.lunch.StartActivity;
import de.ones.lunch.data.RestaurantUserItem;

public class LoadRestaurantsUsersDB extends Thread {
	private Context context;
	private DBAccess db;
	
	public LoadRestaurantsUsersDB(Context cont, DBAccess d) {
		context = cont;
		db		= d;
	}
	
	@Override
	public void run() {
//		DBConnector.transmitRestUsertoDB();
		
		InputStream inSt= DBConnector.getFunc(2);
		if(inSt != null)readStream(inSt);
		
	}
	
	private void readStream(InputStream in){
    	BufferedReader reader = null;
    	try {
    		reader = new BufferedReader(new InputStreamReader(in));
    		String line = "";
    		while ((line = reader.readLine()) != null) {
    			parseJSON(line);	
    			((StartActivity) context).loadingDone();
    		}
    	} catch (IOException e) {
    		Log.d("nweber", "error "+e.getMessage());
    		e.printStackTrace();
    	} finally {
    		if (reader != null) {
    		  try {
    		    reader.close();
    		  } catch (IOException e) {
    		    e.printStackTrace();
    	      }
    		}
    	}
    }

	private void parseJSON(String line) {
		try {
			JSONArray ja = new JSONArray(line);
			RestaurantUserItem tempRUI;
			for(int i = 0; i<ja.length();i++){
				int restId = Integer.parseInt(ja.getJSONObject(i).getString("restaurants_id"));
				int userId = Integer.parseInt(ja.getJSONObject(i).getString("users_id"));
				Long when_time = Long.parseLong(ja.getJSONObject(i).getString("when_time"))*1000;
				tempRUI = new RestaurantUserItem(restId, userId,when_time);
				db.addRestaurantUser(tempRUI);
			}
		}catch(JSONException e){
			Log.d("nweber", "error LoadRestaurantsUsersDB "+e.getMessage());
		}
		
		
	}

}
