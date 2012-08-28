package de.ones.lunch.viewCompontents;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import de.ones.lunch.data.RestaurantItem;

public class StartFavBtn extends LinearLayout{
	private RestaurantItem restItem;
	private RadioButton cb;
	private TextView tv;
	
	public StartFavBtn(Context context, RestaurantItem ri){
		super(context);
		restItem = ri;
		
		this.setBackgroundColor(Color.rgb(33, 33, 33));
		this.setOrientation(LinearLayout.HORIZONTAL);
		LayoutParams llp = new LayoutParams(LayoutParams.MATCH_PARENT, 40);
		llp.setMargins(1, 1, 1, 1);
		this.setLayoutParams(llp);
		
		cb = new RadioButton(context);
		LayoutParams cblp = new LayoutParams(0,LayoutParams.MATCH_PARENT);
		cblp.weight = 1;
		cb.setLayoutParams(cblp);
		cb.setClickable(false);
		//cb.setGravity(Gravity.CENTER);
		this.addView(cb);
		
		tv = new TextView(context);
		LayoutParams tvlp = new LayoutParams(0,LayoutParams.MATCH_PARENT);
		tvlp.setMargins(10, 0, 0, 0);
		tvlp.weight = 4;
		tv.setLayoutParams(tvlp);
		tv.setText(ri.getName());
		tv.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
		this.addView(tv);
	}
	
	public void setSelection(boolean value){
		cb.setChecked(value);
	}
	
	public int getRestId(){
		return restItem.getId();
	}
	
	public boolean isSelected(){
		return cb.isChecked();
	}
}
