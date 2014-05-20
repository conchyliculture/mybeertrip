package fr.renzo.mybeertrip.databases;

import fr.renzo.mybeertrip.Beer;

public interface MyBeerTripDatabase {
	public Beer findBeer(String name);

	public Beer getBeerFromBarcode(String res);
}
