package de.ones.lunch;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import tools.DBConnector;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends Activity implements OnClickListener{
	
	private Button regBtn;
	private EditText nam;
	private EditText fpwd;
	private EditText spwd;
	private TextView fTv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		
		regBtn 	= (Button)findViewById(R.id.regiBtn);
		regBtn.setOnClickListener(this);
		nam		= (EditText)findViewById(R.id.nickname);
		fpwd	= (EditText)findViewById(R.id.firstPwd);
		spwd	= (EditText)findViewById(R.id.secondPwd);
		fTv		= (TextView)findViewById(R.id.registerFail);
	}
	
	public void onClick(View v) {
		if(v == regBtn){
			//checks if user exist
			List<NameValuePair> nvp = new ArrayList<NameValuePair>();
			nvp.add(new BasicNameValuePair("name", String.valueOf(nam.getText())));
			
			if(DBConnector.postReturnFunc(nvp, 1).equals("-1"))
//			if(DBConnector.checkUserByName(String.valueOf(nam.getText())).equals("-1"))
			{
				if(String.valueOf(fpwd.getText()).length() <= 3){
					fTv.setText(R.string.regPwdFailLength);
					return;
				}
				if(!String.valueOf(fpwd.getText()).equals(String.valueOf(spwd.getText()))){
					fTv.setText(R.string.regPwdFail);
					return;
				}
				
				// create new user
				List<NameValuePair> nv = new ArrayList<NameValuePair>();
				nv.add(new BasicNameValuePair("name", String.valueOf(nam.getText())));
				nv.add(new BasicNameValuePair("password", String.valueOf(fpwd.getText())));
				DBConnector.postFunc(nv, 2);
				
				Toast feedBackToast = Toast.makeText(this, R.string.regSuccess, 1000);
				feedBackToast.setGravity(1, 0, -100);
				feedBackToast.show();
				finish();
				
			}else{
				fTv.setText(R.string.regUserExist);
			}
			
			
		}
		
	}
}
