package fr.renzo.mybeertrip.activities;

import fr.renzo.mybeertrip.R;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class SearchBeerActivity extends FragmentActivity {
	protected static final String TAG = "SearchBeerActivity";
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle("Search Beer");

	
		setContentView(R.layout.activity_search_beer);
		
	}
	
	
}
