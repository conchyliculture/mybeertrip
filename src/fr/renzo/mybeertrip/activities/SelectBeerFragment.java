package fr.renzo.mybeertrip.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import fr.renzo.mybeertrip.MyBeerTrip;
import fr.renzo.mybeertrip.R;
import fr.renzo.mybeertrip.databases.MyBeerTripDatabase;
import fr.renzo.mybeertrip.databases.SearchBeerNameCursorAdapter;

public class SelectBeerFragment extends Fragment {
	private Context context;
	private View wholeview;
	private AutoCompleteTextView searchbeernameview;
	private MyBeerTripDatabase dbh;

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		context=getActivity();
		this.dbh = ((MyBeerTrip)getActivity().getApplication()).getMyBeerTripDatabaseHandler();
		wholeview=inflater.inflate(R.layout.fragment_search_beer_tab,null);
		if (savedInstanceState==null) {
			searchbeernameview = (AutoCompleteTextView) wholeview.findViewById(R.id.fragment_search_beer_autocomplete_name);
			searchbeernameview.setAdapter(new SearchBeerNameCursorAdapter(context, null, dbh));
			
		}
		return wholeview ;
	}
}
