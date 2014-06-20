package fr.renzo.mybeertrip.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import fr.renzo.mybeertrip.Beer;
import fr.renzo.mybeertrip.Brewery;
import fr.renzo.mybeertrip.Country;
import fr.renzo.mybeertrip.MyBeerTrip;
import fr.renzo.mybeertrip.R;
import fr.renzo.mybeertrip.databases.MyBeerTripDatabase;
import fr.renzo.mybeertrip.databases.SearchBreweryCursorAdapter;
import fr.renzo.mybeertrip.databases.SearchCountryCursorAdapter;
import fr.renzo.mybeertrip.utils.ScanBeerClickListener;

public class AddBeerActivity extends Activity {
	private static final String TAG = "AddBeerActivity";
	private EditText beer_name_view;
	private AutoCompleteTextView beer_country_view;
	private AutoCompleteTextView brewery_view;
	private EditText beer_abv_view;
	private Button beer_barcode_button;
	private EditText beer_description_view;
	private CheckBox beer_seasonal_box;
	private CheckBox beer_special_box;

	private String beer_name="";
	private float beer_abv=0;
	private String beer_description="";
	private String beer_barcode="";
	private Brewery beer_brewery;
	private Country beer_country;

	private MyBeerTripDatabase dbh;
	protected boolean beer_seasonal;
	protected boolean beer_special;
	private int beer_country_id;
	
	



	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle("Add a new Beer");
		setContentView(R.layout.activity_add_beer);

		this.dbh = ((MyBeerTrip) getApplication()).getMyBeerTripDatabaseHandler();

		
			this.beer_name_view = (EditText) findViewById(R.id.add_beer_name);
			this.beer_country_view = (AutoCompleteTextView)findViewById(R.id.add_beer_country);
			this.brewery_view = (AutoCompleteTextView) findViewById(R.id.add_beer_brewery);
			this.beer_abv_view = (EditText) findViewById(R.id.add_beer_abv);
			this.beer_barcode_button = (Button) findViewById(R.id.add_beer_add_barcode);
			this.beer_description_view = (EditText) findViewById(R.id.add_beer_description);
			this.beer_seasonal_box = (CheckBox) findViewById(R.id.add_beer_seasonal);
			this.beer_special_box = (CheckBox) findViewById(R.id.add_beer_limited);

		
			this.beer_name_view.addTextChangedListener(new TextWatcher() {
				@Override
				public void onTextChanged(CharSequence s, int start, int before, int count) {beer_name=s.toString();}
				@Override
				public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
				@Override
				public void afterTextChanged(Editable s) {
					beer_name=s.toString();}
			});

			this.beer_country_view.setAdapter(new SearchCountryCursorAdapter(this,null,dbh));
			this.beer_country_view.setOnItemClickListener(new CountryOnItemClickListener());

			this.brewery_view.setAdapter(new SearchBreweryCursorAdapter(this, null,dbh));
			this.brewery_view.setOnItemClickListener(new BreweryOnItemClickListener());
			this.brewery_view.setOnLongClickListener(new View.OnLongClickListener() {

				@Override
				public boolean onLongClick(View v) {
					Intent myIntent = new Intent(AddBeerActivity.this, AddBreweryActivity.class);
					myIntent.putExtra("brewery_name",  (String) brewery_view.getText().toString());
					startActivity(myIntent);
					return true;
				}
			});


			this.beer_abv_view.addTextChangedListener(new TextWatcher() {
				@Override
				public void onTextChanged(CharSequence s, int start, int before, int count) {}
				@Override
				public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
				@Override
				public void afterTextChanged(Editable s) {
					String text = s.toString();
					if (text.matches("^[0-9,\\.]+$"))
						beer_abv=new Float(s.toString().toUpperCase());
				}
			});

			this.beer_barcode_button.setOnClickListener(new ScanBeerClickListener(this));

			this.beer_description_view.addTextChangedListener(new TextWatcher() {
				@Override
				public void onTextChanged(CharSequence s, int start, int before, int count) {}
				@Override
				public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
				@Override
				public void afterTextChanged(Editable s) {
					String text = s.toString();
					beer_description=text;}
			});
			this.beer_seasonal_box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					beer_seasonal = isChecked;
				}
			});
			this.beer_special_box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					beer_special = isChecked;
				}
			});
		

	}

	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
		if (scanResult != null) {
			String res = scanResult.getContents();
			if (res!=null) { // Check we didn't just escape scanning thing
				this.beer_barcode = res;
				this.beer_barcode_button.setText(res);
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu items for use in the action bar
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.add_beer_activity_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		switch (item.getItemId()) {
		case R.id.action_add_beer:
			long resid = addBeer();
			Log.d(TAG,"added beer "+resid);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}


	private long addBeer() {
		if (this.beer_name == "") {
			showMessage("Please enter a beer name");
			return -1;
		}
		if (this.beer_country_id == -1) {
			showMessage("Please select this beer's country of origin");
			return -1;
		}

		Beer b = new Beer();
		b.setName(beer_name);
		if (beer_abv>0)
			b.setAbv(beer_abv);
		if (beer_barcode!="")
			b.setBarcode(beer_barcode);
		if (beer_description!="")
			b.setDescription(beer_description);
		b.setSeasonal(beer_seasonal);
		b.setSpecial(beer_special);
		b.setCountry(beer_country);
		b.setBrewery(beer_brewery);
		long resid = dbh.addBeer(b);
		b.setId(resid);
		return resid;
	}
	private void showMessage(String string) {
		Toast.makeText(this, string, Toast.LENGTH_LONG).show();
	}

	public class BreweryOnItemClickListener implements OnItemClickListener {
		// Handles clicks on an item in the random article list
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			beer_brewery = dbh.getBreweryByName(((TextView) view).getText().toString());
		}
	}
	public class CountryOnItemClickListener implements OnItemClickListener {
		// Handles clicks on an item in the random article list
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			beer_country=dbh.getCountryByName(((TextView) view).getText().toString());
		}
	}
}
