package fr.renzo.mybeertrip.databases;

import android.database.Cursor;
import fr.renzo.mybeertrip.Beer;
import fr.renzo.mybeertrip.Brewery;
import fr.renzo.mybeertrip.Country;

public interface MyBeerTripDatabase {
	
	

	public Cursor rawQuery(String query, String[] objects );
	
//	public ArrayList<Beer> findBeersByName(String name);
	public Beer getBeerByBarcode(String res);

	public Cursor searchBeersByName(String constraint);
	public Cursor searchBreweriesByName(String constraint);

	public Beer getBeerById(long _id);
	public Beer getBeerByName(String name);

	public long addBeer(Beer b);


	public Cursor searchCountriesByName(String string);

	public Country getCountryByName(String charSequence);
	public Country getCountryById(long _id);

	public Brewery getBreweryByName(String string);
	public Brewery getBreweryById(long _id);

}
