package fr.renzo.mybeertrip.activities;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import fr.renzo.mybeertrip.Beer;
import fr.renzo.mybeertrip.R;
import fr.renzo.mybeertrip.MyBeerTrip;
import fr.renzo.mybeertrip.databases.MyBeerTripDatabase;

public class AddDrinkActivity extends Activity {
	

	
	private MyBeerTripDatabase beerdbh;
	private EditText textbeername;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_drink);
		this.beerdbh = ((MyBeerTrip) getApplication()).getMyBeerTripDatabaseHandler();
		
		this.textbeername = (EditText) findViewById(R.id.editBeerName);
		
		Button scanbeerbutton = (Button) findViewById(R.id.buttonScanBarcode);
		scanbeerbutton.setOnClickListener(new ScanBeerClickListener());
	}
	public class ScanBeerClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			IntentIntegrator integrator = new IntentIntegrator(AddDrinkActivity.this);
			integrator.initiateScan();

		}

	}
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		  IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
		  if (scanResult != null) {
			  String res = scanResult.getContents();
			  Beer b = this.beerdbh.findBeerByBarcode(res);
			  this.textbeername.setText(b.getName());
		  }
		  
		}
	
}
