package fr.renzo.mybeertrip.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import fr.renzo.mybeertrip.Beer;
import fr.renzo.mybeertrip.R;

public class SelectedBeerFragment extends Fragment {
	private AddDrinkActivity adddrinkactivity;
	private View wholeview;
	private Beer beer;

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		adddrinkactivity=(AddDrinkActivity) getActivity();
		wholeview=inflater.inflate(R.layout.fragment_show_beer,null);
		
		beer = adddrinkactivity.getSelectedBeer(); 
		
		TextView beername = (TextView) wholeview.findViewById(R.id.show_beer_textview);
		beername.setText(beer.getName());
		
		return wholeview ;
	}
}
