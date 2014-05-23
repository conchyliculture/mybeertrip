package fr.renzo.mybeertrip.activities;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;
import fr.renzo.mybeertrip.Beer;
import fr.renzo.mybeertrip.MyBeerTrip;
import fr.renzo.mybeertrip.R;
import fr.renzo.mybeertrip.databases.MyBeerTripDatabase;
import fr.renzo.mybeertrip.databases.SearchBeerNameCursorAdapter;

public class SelectBeerFragment extends Fragment {
	protected static final String TAG = "SelectBeerFragment";
	private AddDrinkActivity adddrinkactivity;
	private View wholeview;
	private AutoCompleteTextView searchbeernameview;
	private MyBeerTripDatabase dbh;

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		adddrinkactivity=(AddDrinkActivity) getActivity();
		
		this.dbh = adddrinkactivity.getMyBeerTripDatabase();
		
		wholeview=inflater.inflate(R.layout.fragment_search_beer,null);
		if (savedInstanceState==null) {
			searchbeernameview = (AutoCompleteTextView) wholeview.findViewById(R.id.fragment_search_beer_autocomplete_name);
			searchbeernameview.setAdapter(new SearchBeerNameCursorAdapter(adddrinkactivity, null, this.dbh));
			searchbeernameview.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position,
						long id) {
					// c has two columns : _id and name)
					Cursor c = (Cursor) parent.getItemAtPosition(position);
					long _id = c.getLong(0);
					setSelectedBeer(_id);
					
				}
			});
			
			Button scanbarcode = (Button) wholeview.findViewById(R.id.fragment_search_beer_scan_barcode_button);
			scanbarcode.setOnClickListener(new ScanBeerClickListener());
			
			
		}
		return wholeview ;
	}

	protected void setSelectedBeer(long _id) {
		Beer b = this.dbh.getBeerById(_id);
		this.adddrinkactivity.setSelectedBeer(b);
		
	}


	
	public class ScanBeerClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			IntentIntegrator integrator = new IntentIntegrator(SelectBeerFragment.this.adddrinkactivity);
			integrator.initiateScan();

		}

	}
	
}
