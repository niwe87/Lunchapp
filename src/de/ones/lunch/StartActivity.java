package de.ones.lunch;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import threads.LoadRestaurantsDB;
import threads.LoadRestaurantsUsersDB;
import threads.LoadUsersDB;
import tools.DBAccess;
import tools.DBConnector;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;
import de.ones.lunch.data.DataVault;
import de.ones.lunch.data.FavoriteItem;
import de.ones.lunch.data.RestaurantItem;
import de.ones.lunch.data.RestaurantUserItem;
import de.ones.lunch.viewCompontents.StartCheckList;

public class StartActivity extends Activity implements OnClickListener{
	private DataVault dataV = DataVault.getInstance();
	private StartCheckList favList;
	private DBAccess db;
	
	private TimePicker tp;
	private int actMin = 0;
	private int actHour = 13;
	
	private RestaurantUserItem rui;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	
    	setContentView(R.layout.start);
    	
    	db = new DBAccess(this, "Yeehaa");
    	dataV.setDb(db);
    	
    	//start loading data from database
    	LoadUsersDB ludb = new LoadUsersDB(this, db);
    	ludb.start();
    	
    	LoadRestaurantsDB lrdb = new LoadRestaurantsDB(this, db);
    	lrdb.start();
    	
    	transmitData();
    	
    	LoadRestaurantsUsersDB lrudb = new LoadRestaurantsUsersDB(this, db);
    	lrudb.start();
    	
    	favList = (StartCheckList)findViewById(R.id.favlist);
    	
    	tp = (TimePicker) findViewById(R.id.timePicker);
    	tp.setDescendantFocusability(TimePicker.FOCUS_BLOCK_DESCENDANTS);
//    	tp.setCurrentMinute(actMin);
//    	tp.setCurrentHour(actHour);
    	tp.setIs24HourView(true);
    	tp.setOnTimeChangedListener(tpotcl);
    	
    	Button essenBtn = (Button)findViewById(R.id.essenBtn);
    	essenBtn.setOnClickListener(this);
    	
    	Button allBtn = (Button)findViewById(R.id.allBtn); 
    	allBtn.setOnClickListener(this);
    	
    }
    
	private TimePicker.OnTimeChangedListener tpotcl = new TimePicker.OnTimeChangedListener() {
		public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
			updateTime(hourOfDay, minute);
		};
	};
	
	private void updateTime(int hr, int min){		
		if(actMin != min){
			if(actMin>min || min == 59) min -=14;
			else if(actMin < min)
			{
				if(min == 46) min = 0;
				else min += 14; 
			}
			
			actMin = min;
			actHour = hr;
			tp.setCurrentMinute(min);
		}
	}
	
	private void updateTimePicker() {
		if(rui == null){
			actMin = 0;
			actHour = 13;
		}else{
			actHour = new Date(rui.getWhenTime()).getHours();
			actMin = new Date(rui.getWhenTime()).getMinutes();
		}
		
		tp.setCurrentMinute(actMin);
		tp.setCurrentHour(actHour);
	}
	
	private void createFavList(){
		favList.flush();
		if(rui == null){
			favList.setActRest(-1);
		}else{
			favList.setActRest(rui.getRestId());
		}
		ArrayList<FavoriteItem> favArr = db.getFavorites();
		RestaurantItem ri;
		for (int i = 0; i < favArr.size(); i++) {
			ri = db.getRestaurantById(favArr.get(i).getRestId());
			favList.addBtn(ri);
		}
	}
	
	private void transmitData(){
		RestaurantUserItem rui = db.getRestaurantUser(Integer.parseInt(dataV.getUser()));
		if(rui != null){		
			List<NameValuePair> nvp = new ArrayList<NameValuePair>();
			nvp.add(new BasicNameValuePair("user_id", String.valueOf(rui.getUserId())));
			nvp.add(new BasicNameValuePair("rest_id", String.valueOf(rui.getRestId())));
			nvp.add(new BasicNameValuePair("when_time", String.valueOf(rui.getWhenTime()/1000)));
			DBConnector.postFunc(nvp, 1);
//			DBConnector.transmitRestUsertoDB();
		}
	}
	
	public void loadingDone(){
		Log.d("nweber", "lalalalalal");
	}
	
	public void onClick(View v) {
		Intent intent = null;
		
		switch(v.getId()){
			case R.id.allBtn: intent = new Intent(this, FavSelectActivity.class);
				break;
			case R.id.essenBtn: if(favList.getActRest() == -1){
									Toast favChoose = Toast.makeText(this, R.string.startFavChoose, 1000);
									favChoose.setGravity(1, 0, -100);
									favChoose.show();
									return;
								}
								Calendar c = Calendar.getInstance();
						    	Date dat = new Date(c.get(Calendar.YEAR)-1900,c.get(Calendar.MONTH),c.get(Calendar.DATE));
								dat.setHours(tp.getCurrentHour());
								dat.setMinutes(tp.getCurrentMinute());
								dat.setSeconds(0);
								
								db.addRestaurantUser(new RestaurantUserItem(favList.getActRest(), Integer.parseInt(dataV.getUser()), dat.getTime()));
								
								intent = new Intent(this, DinerInfoActivity.class);
				break;
			default: break;
		}
		
		if(intent != null)
		{
			startActivity(intent);
		}		
	}
	
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		rui = db.getRestaurantUser(Integer.parseInt(dataV.getUser()));
		
		updateTimePicker();
		createFavList();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		
		transmitData();
		
		db.close();
	}

}
