package de.ones.lunch;

import java.util.ArrayList;

import tools.DBAccess;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import de.ones.lunch.data.DataVault;
import de.ones.lunch.data.RestaurantItem;
import de.ones.lunch.data.RestaurantUserItem;
import de.ones.lunch.data.UserItem;

public class DinerInfoActivity extends Activity implements OnClickListener{
	private DataVault dataV = DataVault.getInstance();
	private DBAccess db;
	private LinearLayout dinerList;
	private ArrayList<UserItem> dinerUsers;
	private Button dfBtn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dinerinfo);
		
		db = dataV.getDb();
		
		RestaurantUserItem rui = db.getRestaurantUser(Integer.parseInt(dataV.getUser()));
		RestaurantItem ri = db.getRestaurantById(rui.getRestId());
		
		TextView tv = (TextView)findViewById(R.id.info);
		tv.setText(ri.getInfo());
		
		dinerUsers = db.getRestUserByTime(rui.getRestId(), rui.getWhenTime(), Integer.parseInt(dataV.getUser()));
		
		dinerList = (LinearLayout)findViewById(R.id.dinerList);
		
		for (int i = 0; i < dinerUsers.size(); i++) {
			TextView tempTv = new TextView(this);
			LayoutParams tempTvLp = new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT);
			tempTv.setLayoutParams(tempTvLp);
			tempTv.setText(dinerUsers.get(i).getName());
			dinerList.addView(tempTv);
		}
		
		dfBtn = (Button)findViewById(R.id.dinerfutureBtn);
		dfBtn.setOnClickListener(this);
		
	}
	public void onClick(View v) {
		if(v == dfBtn){
			Intent intent = new Intent(this, DinerFutureActivity.class);
			startActivity(intent);
		}
		
	}
	
}
