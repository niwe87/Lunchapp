package de.ones.lunch;

import java.util.ArrayList;
import java.util.Date;

import tools.DBAccess;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import de.ones.lunch.data.DataVault;
import de.ones.lunch.data.RestaurantUserItem;
import de.ones.lunch.data.UserTimeItem;

public class DinerFutureActivity extends Activity {
	private DataVault dataV = DataVault.getInstance();
	private DBAccess db;
	private ArrayList<UserTimeItem> dinerUsers;
	private LinearLayout dinerfutList;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dinerfuture);
		
		db = dataV.getDb();
		
		dinerfutList = (LinearLayout)findViewById(R.id.dinerFutureList);
		
		RestaurantUserItem rui = db.getRestaurantUser(Integer.parseInt(dataV.getUser()));
		dinerUsers = db.getRestUserByDay(rui.getRestId(), rui.getWhenTime(), Integer.parseInt(dataV.getUser()));
		
		
		TextView temp;
		for (int i = 0; i < dinerUsers.size(); i++) {
			temp = new TextView(this);
			Date tempDate = new Date(dinerUsers.get(i).getTime());
			
			temp.setText(dinerUsers.get(i).getFirstname()+" "+tempDate.getHours()+":"+tempDate.getMinutes());
			dinerfutList.addView(temp);
		}
	}
}
