package de.ones.lunch;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import tools.DBConnector;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import de.ones.lunch.data.DataVault;

public class LoginActivity extends Activity implements OnClickListener{
	private DataVault dataV;
	private SharedPreferences prefs;
	private Button loginBtn;
	private Button regBtn;
	private EditText fn;
	private EditText ln;
	private TextView lf;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		dataV = DataVault.getInstance();
		
		prefs = this.getPreferences(Context.MODE_PRIVATE);
		
		if(prefs.getString("user", "-1") == "-1"){
			setContentView(R.layout.login);
			
			fn = (EditText) findViewById(R.id.name);
			ln = (EditText) findViewById(R.id.password);
			lf = (TextView) findViewById(R.id.loginFail);
			loginBtn = (Button)findViewById(R.id.login);
			regBtn = (Button)findViewById(R.id.register);
			regBtn.setOnClickListener(this);
			loginBtn.setOnClickListener(this);
			
		}else{
			gotoStart();
		}
	}
	
	private void gotoStart(){
		dataV.setUser(prefs.getString("user", "-1"));
		
		Intent startIntent = new Intent(this, StartActivity.class);
		startActivity(startIntent);
		
		finish();
	}
	
	public void onClick(View v) {
		if(v == loginBtn){
//			String uId = DBConnector.checkLogin(String.valueOf(fn.getText()), String.valueOf(ln.getText()));
			List<NameValuePair> nvp = new ArrayList<NameValuePair>();
			nvp.add(new BasicNameValuePair("name", String.valueOf(fn.getText())));
			nvp.add(new BasicNameValuePair("password", String.valueOf(ln.getText())));
			
			String uId = DBConnector.postReturnFunc(nvp, 2);
			if(uId.equals("-1")){
				lf.setText(R.string.logFail);
				return;
			}
			
			//saves user id
			Editor me = prefs.edit();
	    	me.putString("user", uId);
	    	me.commit();
	    	
	    	gotoStart();
		}else if(v == regBtn){
			Intent registerIntent = new Intent(this, RegisterActivity.class);
			startActivity(registerIntent);
		}
	}

	
}
