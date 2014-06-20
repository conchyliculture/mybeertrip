package fr.renzo.mybeertrip.utils;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.View.OnClickListener;

import com.google.zxing.integration.android.IntentIntegrator;


public class ScanBeerClickListener implements OnClickListener {
	
	private Activity activity;
	
	private Fragment fragment;

	public ScanBeerClickListener(Activity c) {
		super();
		this.activity = c;
	}
	
	public ScanBeerClickListener(Fragment f) {
		super();
		this.fragment = f;
	}
	
	@Override
	public void onClick(View v) {
		IntentIntegrator integrator=null;
		if (fragment!=null) {
			integrator = new IntentIntegrator(fragment);
		} else if (activity != null) {
			integrator = new IntentIntegrator(activity);
		} else {
			return;
		}
		integrator.initiateScan();
	}
}