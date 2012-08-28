package tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class DBConnector {
	
	private static String url = "http://192.168.99.157/lunchapp/lunchapp_backend/public/";
	public static InputStream getFunc(int type){
		String tempUrl = url;
		
		switch(type){
			case 1: tempUrl = url + "restaurants";
					break;
			case 2: tempUrl = url + "restaurants_users";
					break;
			case 3: tempUrl = url + "users";
					break;
			default: return null;
		}
		
		HttpClient cl = new DefaultHttpClient();
		HttpGet re = new HttpGet(tempUrl);
		
		try {
			HttpResponse resp = cl.execute(re);
			InputStream data = resp.getEntity().getContent();
			return data;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static void postFunc(List<NameValuePair> nv, int readType){
		HttpClient cl = new DefaultHttpClient();
		
		String tempUrl = url;
		switch(readType){
			case 1: tempUrl = url + "user_restaurant_id";
					break;
			case 2: tempUrl = url + "users";
					break;
			default: return;
		}
		
		HttpPost hp = new HttpPost(tempUrl);
		List<NameValuePair> nvp = nv;
		
		try {
			hp.setEntity(new UrlEncodedFormEntity(nvp));
			HttpResponse resp = cl.execute(hp);
			
			InputStream data = resp.getEntity().getContent();
			
			switch(readType){
				case 1: readTransmitStream(data);
				case 2: readCreateUserStream(data);
			}
		} catch (ClientProtocolException e) {
			Log.d("nweber", "fail login");
			e.printStackTrace();
		} catch (IOException e) {
			Log.d("nweber", "fail login");
			e.printStackTrace();
		}
	}
	
	public static String postReturnFunc(List<NameValuePair> nv, int readType){
		HttpClient cl = new DefaultHttpClient();
		
		String tempUrl = url;
		switch(readType){
			case 1: tempUrl = url + "checkUserByName";
					break;
			case 2: tempUrl = url + "login";
					break;
			case 3: tempUrl = url + "restaurants";
					break;
			default: return "-1";
		}
		
		HttpPost hp = new HttpPost(tempUrl);
		List<NameValuePair> nvp = nv;
		
		try {
			hp.setEntity(new UrlEncodedFormEntity(nvp));
			HttpResponse resp = cl.execute(hp);
			
			InputStream data = resp.getEntity().getContent();
			
			switch(readType){
				case 1: return readUserByName(data);
				case 2: return readLogin(data);
				case 3: return readCreateRestaurantStream(data);
				default: return "-1";
			}
		} catch (ClientProtocolException e) {
			Log.d("nweber", "fail login");
			e.printStackTrace();
		} catch (IOException e) {
			Log.d("nweber", "fail login");
			e.printStackTrace();
		}
		return "-1";
	}
	
	private static String readUserByName(InputStream in) {
		BufferedReader reader = null;
    	try {
    		reader = new BufferedReader(new InputStreamReader(in));
    		String line = "";
    		line = reader.readLine(); 
    		
    		try {
    			JSONObject jo = new JSONObject(line);
				//checks if error
    			try{
    				if(String.valueOf(jo.getJSONObject("error").getString("message")).equals("User not found"))	{
    					return "-1";
    				}
    			}catch (JSONException e) {
    				return String.valueOf(jo.getString("id"));
    		    	
    			}
			} catch (JSONException e) {
				e.printStackTrace();
			}
    		
    	} catch (IOException e) {
    		Log.d("nweber", "error create User "+e.getMessage());
    	} finally {
    		if (reader != null) {
    		  try {
    		    reader.close();
    		  } catch (IOException e) {
    			  Log.d("nweber", "error create User "+e.getMessage());
    	      }
    		}
    	}
    	return "-1";
	}

	private static String readLogin(InputStream in) {
		BufferedReader reader = null;
    	try {
    		reader = new BufferedReader(new InputStreamReader(in));
    		String line = "";
    		line = reader.readLine(); 
    		
    		try {
    			JSONObject jo = new JSONObject(line);
				//checks if error
    			try{
    				if(String.valueOf(jo.getJSONObject("error").getString("message")).equals("User not found"))	{
    					return "-1";
    				}
    			}catch (JSONException e) {
    				return String.valueOf(jo.getString("id"));
    		    	
    			}
			} catch (JSONException e) {
				e.printStackTrace();
			}
    		
    	} catch (IOException e) {
    		Log.d("nweber", "error create User "+e.getMessage());
    	} finally {
    		if (reader != null) {
    		  try {
    		    reader.close();
    		  } catch (IOException e) {
    			  Log.d("nweber", "error create User "+e.getMessage());
    	      }
    		}
    	}
    	return "-1";
	}
	
	private static void readTransmitStream(InputStream in){
    	BufferedReader reader = null;
    	try {
    		reader = new BufferedReader(new InputStreamReader(in));
    		String line = "";
    		line = reader.readLine(); 
    	} catch (IOException e) {
    		Log.d("nweber", "error create User "+e.getMessage());
    	} finally {
    		if (reader != null) {
    		  try {
    		    reader.close();
    		  } catch (IOException e) {
    			  Log.d("nweber", "error create User "+e.getMessage());
    	      }
    		}
    	}
    }
	

	private static void readCreateUserStream(InputStream in){
    	BufferedReader reader = null;
    	try {
    		reader = new BufferedReader(new InputStreamReader(in));
    		String line = "";
    		line = reader.readLine(); 
    		
    		Log.d("nweber", line);
    	} catch (IOException e) {
    		Log.d("nweber", "error create User "+e.getMessage());
    	} finally {
    		if (reader != null) {
    		  try {
    		    reader.close();
    		  } catch (IOException e) {
    			  Log.d("nweber", "error create User "+e.getMessage());
    	      }
    		}
    	}
    }
	

	private static String readCreateRestaurantStream(InputStream in){
    	BufferedReader reader = null;
    	try {
    		reader = new BufferedReader(new InputStreamReader(in));
    		String line = "";
    		line = reader.readLine(); 
    		
    		try {
				JSONObject jo = new JSONObject(line);
				String restId = jo.getJSONObject("success").getString("id");
				
				return restId;
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		
    	} catch (IOException e) {
    		Log.d("nweber", "error create User "+e.getMessage());
    	} finally {
    		if (reader != null) {
    		  try {
    		    reader.close();
    		  } catch (IOException e) {
    			  Log.d("nweber", "error create User "+e.getMessage());
    	      }
    		}
    	}
    	return "-1";
    }
	
	
}
