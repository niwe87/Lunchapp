package de.ones.lunch.viewCompontents;

import java.util.ArrayList;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import de.ones.lunch.data.RestaurantItem;

public class StartCheckList extends LinearLayout implements OnClickListener{
	private Context cont;
	private ArrayList<StartFavBtn> startFavBtnArr = new ArrayList<StartFavBtn>();
	private int actRest = -1;
	
	public StartCheckList(Context context, AttributeSet attrs) {
		super(context, attrs);
		cont = context;
	}
	
	public void onClick(View v) {
		for (int i = 0; i < startFavBtnArr.size(); i++) {
			if(v == startFavBtnArr.get(i)){
				startFavBtnArr.get(i).setSelection(true);
				actRest = startFavBtnArr.get(i).getRestId();
			}else{
				startFavBtnArr.get(i).setSelection(false);
			}
		}
	}
	
	public void addBtn(RestaurantItem ri){
		StartFavBtn sfb = new StartFavBtn(cont, ri);
		if(ri.getId() == actRest)sfb.setSelection(true);
		sfb.setOnClickListener(this);
		this.addView(sfb);
		startFavBtnArr.add(sfb);
	}
	
	public void flush(){
		this.removeAllViews();
		startFavBtnArr = new ArrayList<StartFavBtn>();
	}
	
	public void setActRest(int restId){
		actRest = restId;
	}
	
	public int getActRest(){
		return actRest;
	}
}
