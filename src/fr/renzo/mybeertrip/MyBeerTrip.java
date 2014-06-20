package fr.renzo.mybeertrip;


import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import fr.renzo.mybeertrip.databases.MyBeerTripDatabase;
import fr.renzo.mybeertrip.databases.beerdb.BeerDBDatabase;

public class MyBeerTrip extends Application {

    @SuppressWarnings("unused")
	private static final String TAG = "MyBeerTrip";
    public static SharedPreferences config;
	public MyBeerTripDatabase dbhandler;
	
    public void onCreate(){
    	super.onCreate();
    	    	
    	config = PreferenceManager.getDefaultSharedPreferences(this);  	
    	
    	
    }

	public MyBeerTripDatabase getMyBeerTripDatabaseHandler() {
		return this.dbhandler;
	}

	public void setMyBeerTripDatabaseHandler(MyBeerTripDatabase dbh) {
		this.dbhandler= dbh;
		
	}
}