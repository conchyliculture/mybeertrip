package fr.renzo.mybeertrip.activities;

import java.io.File;

import fr.renzo.mybeertrip.MyBeerTrip;
import fr.renzo.mybeertrip.R;
import fr.renzo.mybeertrip.databases.BeerDBDatabase;
import fr.renzo.mybeertrip.databases.MyBeerTripDatabase;
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
}
