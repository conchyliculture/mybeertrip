package fr.renzo.mybeertrip.activities;

import android.app.Activity;
import android.content.Intent;
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
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import fr.renzo.mybeertrip.Beer;
import fr.renzo.mybeertrip.MyBeerTrip;
import fr.renzo.mybeertrip.R;
import fr.renzo.mybeertrip.databases.MyBeerTripDatabase;


public class AddDrinkActivity extends  ActionBarActivity {

	private static final String TAG = "AddDrinkActivity";
	private MyBeerTripDatabase beerdbh;	
	private Beer selectedbeer;
	private ActionBar bar;
	private Tab selectBeerTab;
	private Tab selectPlaceTab;
	private Tab selectNoteTab;
	private Tab selectPictureTab;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle("Add a new Drink");
		bar = getSupportActionBar();
		bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		setContentView(R.layout.activity_add_drink);
		
		this.beerdbh = ((MyBeerTrip)getApplication()).getMyBeerTripDatabaseHandler();


		selectBeerTab = bar.newTab().setText("Select Beer");
		selectPlaceTab = bar.newTab().setText("Select Place");
		selectNoteTab = bar.newTab().setText("Add Note");
		selectPictureTab = bar.newTab().setText("Add Picture");

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

		public void onActivityResult(int requestCode, int resultCode, Intent intent) {
			  IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
			  if (scanResult != null) {
				  String res = scanResult.getContents();
				  Log.d(TAG,"Activity +"+res);
				  Beer b = this.beerdbh.findBeerByBarcode(res);
				  if (b != null) {
				 	setSelectedBeer(b);
				  } else {
					  Toast.makeText(this, "Can't find this beer in Database", Toast.LENGTH_LONG).show();
				  }
			  }
			  
			}
	public void setSelectedBeer(Beer b) {
		if (b != null) {
			this.bar.removeTab(this.selectBeerTab);
			Tab selectedBeerTab = bar.newTab().setText("Selected Beer");
			
			selectedBeerTab.setTabListener(new DrinkTabsListener<SelectedBeerFragment>(
					this,"selectedbeer",SelectedBeerFragment.class)
					);
			this.bar.addTab(selectedBeerTab,0,true);
			this.selectedbeer = b;
		}
	}

	public MyBeerTripDatabase getMyBeerTripDatabase() {
		
		return this.beerdbh;
	}

	public Beer getSelectedBeer() {
		return this.selectedbeer;
	}

}
