package fr.renzo.mybeertrip.activities;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar.Tab;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import fr.renzo.mybeertrip.Beer;
import fr.renzo.mybeertrip.R;
import fr.renzo.mybeertrip.MyBeerTrip;
import fr.renzo.mybeertrip.databases.MyBeerTripDatabase;


public class AddDrinkActivity extends  ActionBarActivity {



	private MyBeerTripDatabase beerdbh;
	private EditText textbeername;
	private SelectBeerFragment selectBeerFragment;
	private SelectPlaceFragment selectPlaceFragment;
	private SelectPictureFragment selectPictureFragment;
	private SelectNoteFragment selectNoteFragment;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle("Add a new Drink");
		ActionBar bar = getSupportActionBar();
		bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		setContentView(R.layout.activity_add_drink);
		
//		Button scanbarcode = (Button) findViewById(R.id.fragment_search_beer_scan_barcode_button);
//		scanbarcode.setOnClickListener(new ScanBeerClickListener());

		selectBeerFragment = new SelectBeerFragment();
		selectPlaceFragment = new SelectPlaceFragment();
		selectNoteFragment = new SelectNoteFragment();
		selectPictureFragment = new SelectPictureFragment();

		Tab selectBeerTab = bar.newTab().setText("Select Beer");
		Tab selectPlaceTab = bar.newTab().setText("Select Place");
		Tab selectNoteTab = bar.newTab().setText("Add Note");
		Tab selectPictureTab = bar.newTab().setText("Add Picture");

		selectBeerTab.setTabListener(new DrinkTabsListener<SelectBeerFragment>(
				this,"selectbeer",SelectBeerFragment.class)
				);
		selectPlaceTab.setTabListener(new DrinkTabsListener<SelectPlaceFragment>(
				this,"selectplace",SelectPlaceFragment.class)
				);
		selectNoteTab.setTabListener(new DrinkTabsListener<SelectNoteFragment>(
				this,"selectnote",SelectNoteFragment.class)
				);
		selectPictureTab.setTabListener(new DrinkTabsListener<SelectPictureFragment>(
				this,"selectpic",SelectPictureFragment.class)
				);

		bar.addTab(selectBeerTab);
		bar.addTab(selectPlaceTab);
		bar.addTab(selectNoteTab);
		bar.addTab(selectPictureTab);
	}

	public static class DrinkTabsListener<T extends Fragment> implements ActionBar.TabListener {
		private Fragment mFragment;
		private final Activity mActivity;
		private final String mTag;
		private final Class<T> mClass;

		/** Constructor used each time a new tab is created.
		 * @param activity  The host Activity, used to instantiate the fragment
		 * @param tag  The identifier tag for the fragment
		 * @param clz  The fragment's Class, used to instantiate the fragment
		 */
		public DrinkTabsListener(Activity activity, String tag, Class<T> clz) {
			mActivity = activity;
			mTag = tag;
			mClass = clz;
		}

		/* The following are each of the ActionBar.TabListener callbacks */

		public void onTabSelected(Tab tab, FragmentTransaction ft) {
			// Check if the fragment is already initialized
			if (mFragment == null) {
				// If not, instantiate and add it to the activity
				mFragment = Fragment.instantiate(mActivity, mClass.getName());
				ft.add(android.R.id.content, mFragment, mTag);
			} else {
				// If it exists, simply attach it in order to show it
				ft.attach(mFragment);
			}
		}

		public void onTabUnselected(Tab tab, FragmentTransaction ft) {
			if (mFragment != null) {
				// Detach the fragment, because another one is being attached
				ft.detach(mFragment);
			}
		}

		public void onTabReselected(Tab tab, FragmentTransaction ft) {
			// User selected the already selected tab. Usually do nothing.
		}
	}
	public class ScanBeerClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			IntentIntegrator integrator = new IntentIntegrator(AddDrinkActivity.this);
			integrator.initiateScan();

		}

	}
//		public void onActivityResult(int requestCode, int resultCode, Intent intent) {
//			  IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
//			  if (scanResult != null) {
//				  String res = scanResult.getContents();
//				  Beer b = this.beerdbh.findBeerByBarcode(res);
//				  this.textbeername.setText(b.getName());
//			  }
//			  
//			}

}
