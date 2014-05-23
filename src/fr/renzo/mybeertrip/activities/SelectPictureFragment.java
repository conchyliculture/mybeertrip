package fr.renzo.mybeertrip.activities;

import fr.renzo.mybeertrip.R;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SelectPictureFragment extends Fragment {
	private Context context;
	private View wholeview;

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		context=getActivity();
		wholeview=inflater.inflate(R.layout.fragment_make_pic,null);


		return wholeview ;
	}
}
