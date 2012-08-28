package de.ones.lunch;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AddRestaurantActivity extends Activity implements OnClickListener{
	
	private EditText nameEdit;
	private EditText infoEdit;
	private EditText linkEdit;
	
	private TextView nameTitle;
	private TextView infoTitle;
	private TextView linkTitle;
	
	private Button restToDb;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addrestaurant);
		
		nameEdit = (EditText)findViewById(R.id.editRestName);
		infoEdit = (EditText)findViewById(R.id.editRestInfo);
		linkEdit = (EditText)findViewById(R.id.editRestLink);
		
		nameTitle = (TextView)findViewById(R.id.addRestName);
		infoTitle = (TextView)findViewById(R.id.addRestInfo);
		linkTitle = (TextView)findViewById(R.id.addRestLink);
		
		restToDb = (Button)findViewById(R.id.restToDb);
		restToDb.setOnClickListener(this);
	}
	
	public void onClick(View v) {
		if(v == restToDb){
//			String tempName = String.valueOf(nameEdit.getText());
//			String tempInfo = String.valueOf(infoEdit.getText());
			String tempLink = String.valueOf(linkEdit.getText());
			
			Log.d("nweber", "http index: " + tempLink.indexOf("http://"));
			
			if(tempLink.indexOf("http://") == -1 || tempLink.indexOf("https://") == -1){
				tempLink = "http://" + tempLink;
			}
			
//			String resp = DataTransmitter.createRestaurant(tempName, tempInfo, tempLink);
//			if(!(resp.equals("-1"))){
//				
//			}
		}
	}
}
