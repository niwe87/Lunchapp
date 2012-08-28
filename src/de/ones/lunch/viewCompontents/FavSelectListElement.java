package de.ones.lunch.viewCompontents;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import de.ones.lunch.FavInfoActivity;
import de.ones.lunch.data.RestaurantItem;

public class FavSelectListElement extends LinearLayout implements OnClickListener{
	private RestaurantItem restItem;
	private CheckBox cb;
	private TextView tv;
	private Button iBtn;
	
	private Context myCont;
	
	public FavSelectListElement(Context context, RestaurantItem ri){
		super(context);
		restItem = ri;
		myCont = context;
		
		this.setOnClickListener(this);
		
		this.setBackgroundColor(Color.rgb(33, 33, 33));
		this.setOrientation(LinearLayout.HORIZONTAL);
		LayoutParams llp = new LayoutParams(LayoutParams.MATCH_PARENT, 50);
		llp.setMargins(1, 1, 1, 1);
		this.setLayoutParams(llp);
		
		cb = new CheckBox(context);
		cb.setClickable(false);
		LayoutParams cblp = new LayoutParams(0,LayoutParams.MATCH_PARENT);
		cblp.weight = 1;
		cb.setLayoutParams(cblp);
		//cb.setGravity(Gravity.CENTER);
//		cb.setOnClickListener(this);
		this.addView(cb);
		
		tv = new TextView(context);
		LayoutParams tvlp = new LayoutParams(0,LayoutParams.MATCH_PARENT);
		tvlp.setMargins(10, 0, 0, 0);
		tvlp.weight = 4;
		tv.setLayoutParams(tvlp);
		tv.setText(ri.getName());
		tv.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
		this.addView(tv);
		
		iBtn = new Button(context);
		LayoutParams btnlp = new LayoutParams(0,LayoutParams.MATCH_PARENT);
		btnlp.weight = 4;
		iBtn.setLayoutParams(btnlp);
		iBtn.setText("info");
		//iBtn.setGravity(Gravity.CENTER);
		iBtn.setOnClickListener(this);
		this.addView(iBtn);
	}
	
	public void onClick(View v) {
		if(v == iBtn){
			 if(restItem.getLink().length() ==0)
			 {
				 Intent intent = new Intent(myCont, FavInfoActivity.class);
				 intent.putExtra("txt", restItem.getInfo());
				 myCont.startActivity(intent);
			 }else{
				 myCont.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(restItem.getLink())));

			 }
		}else{
			cb.setChecked(!cb.isChecked());
		}
	}
	
	public void setSelection(){
		cb.setChecked(true);
	}
	
	public int getRestId(){
		return restItem.getId();
	}
	
	public boolean isSelected(){
		return cb.isChecked();
	}
}
