package fr.renzo.mybeertrip.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import fr.renzo.mybeertrip.Beer;
import fr.renzo.mybeertrip.MyBeerTrip;
import fr.renzo.mybeertrip.R;
import fr.renzo.mybeertrip.databases.MyBeerTripDatabase;



public class AddDrinkActivity extends  ActionBarActivity implements SelectBeerFragment.OnBeerSelectedListener,
SelectedBeerFragment.OnDeleteBeerListener{

	private static final String TAG = "AddDrinkActivity";
	
	private static final String LAST_SELECTED_BEER_NAME = "LAST_SELECTED_BEER_NAME";
	
	private static final String FRAGMENT_SELECT_BEER_TAG = "fragmentselectbeer";
	private static final String FRAGMENT_SELECT_PLACE_TAG = "fragmentselectplace";
	private MyBeerTripDatabase beerdbh;	
	private Beer selectedbeer;
	private ActionBar bar;
	private SharedPreferences prefs;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.prefs = getPreferences(0);

		setTitle("Add a new Drink");
		bar = getSupportActionBar();
		bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		setContentView(R.layout.activity_add_drink);

		this.beerdbh = ((MyBeerTrip)getApplication()).getMyBeerTripDatabaseHandler();



		Tab selectPlaceTab = bar.newTab().setText("Select Place");
		Tab selectNoteTab = bar.newTab().setText("Add Note");
		Tab selectPictureTab = bar.newTab().setText("Add Picture");


		Tab selectBeerTab = bar.newTab()
				.setTag(FRAGMENT_SELECT_BEER_TAG)
				.setText("Select Beer");
		selectBeerTab.setTabListener(new DrinkTabsListener<SelectBeerFragment>(
				this,FRAGMENT_SELECT_BEER_TAG,SelectBeerFragment.class)
				);
		bar.addTab(selectBeerTab);

		if (prefs.contains(LAST_SELECTED_BEER_NAME)) {
			Beer b = this.beerdbh.getBeerByName(prefs.getString(LAST_SELECTED_BEER_NAME, null));
			setSelectedBeer(b);
		} 

		selectPlaceTab.setTabListener(new DrinkTabsListener<SelectPlaceFragment>(
				this,FRAGMENT_SELECT_PLACE_TAG,SelectPlaceFragment.class)
				);
		bar.addTab(selectPlaceTab);

		selectNoteTab.setTabListener(new DrinkTabsListener<SelectNoteFragment>(
				this,"selectnote",SelectNoteFragment.class)
				);
		bar.addTab(selectNoteTab);

		selectPictureTab.setTabListener(new DrinkTabsListener<SelectPictureFragment>(
				this,"selectpic",SelectPictureFragment.class)
				);
		bar.addTab(selectPictureTab);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu items for use in the action bar
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.add_drink_activity_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		switch (item.getItemId()) {
		case R.id.action_add_drink:
			addDrink();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}


	private void addDrink() {
		if (this.selectedbeer == null) {
			showMessage("Please select a beer first");
		}

	}

	private void showMessage(String string) {
		Toast.makeText(this, string, Toast.LENGTH_LONG).show();
	}

	public static class DrinkTabsListener<T extends Fragment> implements ActionBar.TabListener {

		private Fragment mFragment;
		private final ActionBarActivity mActivity;
		private final String mTag;
		private final Class<T> mClass;

		/** Constructor used each time a new tab is created.
		 * @param activity  The host Activity, used to instantiate the fragment
		 * @param tag  The identifier tag for the fragment
		 * @param clz  The fragment's Class, used to instantiate the fragment
		 */
		public DrinkTabsListener(ActionBarActivity activity, String tag, Class<T> clz) {
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
			mFragment = mActivity.getSupportFragmentManager()
					.findFragmentByTag(mTag);
			if (mFragment != null) {
				// Detach the fragment, because another one is being attached
				ft.detach(mFragment);
			}
		}

		public void onTabReselected(Tab tab, FragmentTransaction ft) {
			// User selected the already selected tab. Usually do nothing.
		}
	}

	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
		if (scanResult != null) {
			String res = scanResult.getContents();
			Log.d(TAG,"Activity +"+res);
			if (res!=null) { // Check we didn't just escape scanning thing
				Beer b = this.beerdbh.getBeerByBarcode(res);
				if (b != null) {
					setSelectedBeer(b);
				} else {
					Toast.makeText(this, "Can't find this beer in Database", Toast.LENGTH_LONG).show();
				}
			}
		}

	}
	public void setSelectedBeer(Beer b) {
		if (b != null) {

			SelectedBeerFragment sbf = new SelectedBeerFragment();
			FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
			transaction.replace(android.R.id.content, sbf, FRAGMENT_SELECT_BEER_TAG);
			transaction.commit();

			this.selectedbeer = b;
			this.prefs.edit().putString(LAST_SELECTED_BEER_NAME, b.getName()).commit();

		}
	}
	public void unSelectBeer(){
		SelectBeerFragment sbf = new SelectBeerFragment();
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		transaction.replace(android.R.id.content, sbf, FRAGMENT_SELECT_BEER_TAG);
		transaction.commit();

		this.selectedbeer = null;
		this.prefs.edit().remove(LAST_SELECTED_BEER_NAME).commit();	
	}

	public Beer getSelectedBeer() {
		return this.selectedbeer;
	}
	
	
	

	@Override
	public void onBeerSelected(Beer beer) {
		setSelectedBeer(beer);
	}

	@Override
	public void onBeerDeleted() {
		unSelectBeer();

	}


}
