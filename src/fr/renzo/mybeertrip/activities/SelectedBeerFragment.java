package fr.renzo.mybeertrip.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import fr.renzo.mybeertrip.Beer;
import fr.renzo.mybeertrip.R;
import fr.renzo.mybeertrip.activities.SelectBeerFragment.OnBeerSelectedListener;

public class SelectedBeerFragment extends Fragment {
	private static final String TAG = "SelectedBeerFragment";
	private AddDrinkActivity adddrinkactivity;
	private View wholeview;
	private Beer beer;
	
	OnDeleteBeerListener mCallback;

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		adddrinkactivity=(AddDrinkActivity) getActivity();
		wholeview=inflater.inflate(R.layout.fragment_show_beer,null);
		
		beer = adddrinkactivity.getSelectedBeer();
		if (beer==null) {
			Log.d(TAG,"fail");
		}
		LinearLayout layout=(LinearLayout) wholeview.findViewById(R.id.show_beer_fragment_layout);
		Button delete = new Button(getActivity());
		delete.setText("Clear beer choice");
		delete.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mCallback.onBeerDeleted();
			}
		});
		layout.addView(delete);
		TextView beername = (TextView) wholeview.findViewById(R.id.show_beer_textview);
		beername.setText(beer.getName());
		
		return wholeview ;
	}
	
	
	
	public interface OnDeleteBeerListener {
        public void onBeerDeleted();
    }
	@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnDeleteBeerListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnDeleteBeerListener");
        }
    }

}
