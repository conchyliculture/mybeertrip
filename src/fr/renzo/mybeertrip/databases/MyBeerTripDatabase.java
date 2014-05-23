package fr.renzo.mybeertrip.databases;

import java.util.ArrayList;

import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.util.Log;
import android.widget.Adapter;
import android.widget.ListAdapter;
import fr.renzo.mybeertrip.Beer;

public interface MyBeerTripDatabase {
	
	

	public Cursor rawQuery(String query, String[] objects );
	
	public ArrayList<Beer> findBeersByName(String name);
	public Beer findBeerByBarcode(String res);

	public Cursor searchBeersByName(String constraint);

	public Beer getBeerById(long _id);
}
