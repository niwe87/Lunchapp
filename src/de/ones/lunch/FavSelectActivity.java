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
import de.ones.lunch.data.DataVault;
import de.ones.lunch.data.FavoriteItem;
import de.ones.lunch.data.RestaurantItem;
import de.ones.lunch.viewCompontents.FavSelectListElement;

public class FavSelectActivity extends Activity implements OnClickListener{
	private DBAccess db;
	private DataVault dataV = DataVault.getInstance();
	private ArrayList<RestaurantItem> favRestList;
	private ArrayList<FavoriteItem> favList;
	private LinearLayout ll;
	private Button addRest;
	
	private FavSelectListElement favElements[];
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.favselect);
		
		db = dataV.getDb();
		addRest = (Button)findViewById(R.id.addRestBtn);
		addRest.setOnClickListener(this);
		ll = (LinearLayout)findViewById(R.id.favLinScroll);
		favRestList = db.getRestaurants();
		favList = db.getFavorites();
		createList(ll);
	}

	private void createList(LinearLayout ll){
		favElements = new FavSelectListElement[favRestList.size()];
		
		FavSelectListElement tempFavSelBtn;
		for(int i=0;i<favRestList.size();i++){
			tempFavSelBtn = new FavSelectListElement(this, favRestList.get(i));
			for(int j=0;j<favList.size();j++){
				if(favList.get(j).getRestId() == favRestList.get(i).getId())
				{
					tempFavSelBtn.setSelection();
					break;
				}
			}
			
			ll.addView(tempFavSelBtn);
			favElements[i] = tempFavSelBtn;
		}
	}
	
	public void onClick(View v) {
		if(v == addRest){
			Intent intent = new Intent(this, AddRestaurantActivity.class);
			startActivity(intent);
		}
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		
		for(int i=0;i<favElements.length;i++){
			if(favElements[i].isSelected()){
				db.addFavorite(favElements[i].getRestId());
			}else{
				db.deleteFavorite(favElements[i].getRestId());
			}
		}
	}
	
}
