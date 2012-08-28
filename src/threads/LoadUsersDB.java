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
import de.ones.lunch.data.UserItem;

public class LoadUsersDB extends Thread {
	private Context context;
	private DBAccess db;
	
	public LoadUsersDB(Context cont, DBAccess d) {
		context = cont;
		db		= d;
	}
	
	@Override
	public void run() {
		
		InputStream inSt= DBConnector.getFunc(3);
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
			UserItem ui;
			for(int i = 0; i<ja.length();i++){
				int id = Integer.parseInt(ja.getJSONObject(i).getString("id"));
				String nam = ja.getJSONObject(i).getString("name");
				
				ui = new UserItem(id, nam);
				db.addUser(ui);
			}
		}catch(JSONException e){
			Log.d("nweber", "error LoadUsersDB "+e.getMessage());
		}
		
		
	}

}
