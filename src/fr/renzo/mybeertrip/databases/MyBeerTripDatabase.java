package fr.renzo.mybeertrip.databases;

import java.util.ArrayList;

import fr.renzo.mybeertrip.Beer;

public interface MyBeerTripDatabase {
	
	public ArrayList<Beer> findBeersByName(String name);
	public Beer findBeerByBarcode(String res);
}
