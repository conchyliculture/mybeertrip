package fr.renzo.mybeertrip.activities;

import java.io.File;

import fr.renzo.mybeertrip.MyBeerTrip;
import fr.renzo.mybeertrip.R;
import fr.renzo.mybeertrip.databases.MyBeerTripDatabase;
import fr.renzo.mybeertrip.databases.beerdb.BeerDBDatabase;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		createEnv();
		
		((MyBeerTrip) getApplication()).setMyBeerTripDatabaseHandler(new BeerDBDatabase(this));
		setContentView(R.layout.activity_main);

		
		Button scanbeerbutton = (Button) findViewById(R.id.buttonAddDrink);
		scanbeerbutton.setOnClickListener(new AddDrinkClickListener());
		
		Button addbeerbutton = (Button) findViewById(R.id.buttonAddBeer);
		addbeerbutton.setOnClickListener(new AddBeerClickListener());
		
		Button searchbeerbutton = (Button) findViewById(R.id.buttonSearchBeer);
		searchbeerbutton.setOnClickListener(new SearchBeerClickListener());
		
		Button showdrinksbutton = (Button) findViewById(R.id.buttonShowDrinks);
		showdrinksbutton.setOnClickListener(new ShowDrinksClickListener());
		
		Button statisticsbutton = (Button) findViewById(R.id.buttonStatistics);
		statisticsbutton.setOnClickListener(new ShowStatisticsClickListener());
	}
	
	
	private void createEnv() {
		File f =new File(Environment.getExternalStorageDirectory(),getApplicationContext().getString(R.string.appdir));
		createDir(f);
	}
	private void createDir(File f) {
		boolean res;
		if (!f.exists()) {
			res= f.mkdirs();
			if (!res) {
				Toast.makeText(this, "Problem creating directory: "+f.getAbsolutePath(), Toast.LENGTH_LONG).show(); // TODO
				finish();          
				moveTaskToBack(true);
			}
		}

	}


	public class AddDrinkClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			Intent i = new Intent(MainActivity.this, AddDrinkActivity.class);
			startActivity(i);
		}
	}
	public class AddBeerClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			Intent i = new Intent(MainActivity.this, AddBeerActivity.class);
			startActivity(i);
		}
	}
	public class SearchBeerClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			//TODO
//			Intent i = new Intent(MainActivity.this, AddDrinkActivity.class);
//			startActivity(i);
		}
	}
	public class ShowDrinksClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			//TODO
//			Intent i = new Intent(MainActivity.this, AddDrinkActivity.class);
//			startActivity(i);
		}
	}public class ShowStatisticsClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			//TODO
//			Intent i = new Intent(MainActivity.this, AddDrinkActivity.class);
//			startActivity(i);
		}
	}
}
