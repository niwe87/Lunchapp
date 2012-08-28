package de.ones.lunch;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class FavInfoActivity extends Activity {
	TextView tv;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.favinfo);
		tv = (TextView)findViewById(R.id.favInfoTxt);
		
		Bundle bund = getIntent().getExtras();
		tv.setText(bund.getString("txt"));
	}
	
}
