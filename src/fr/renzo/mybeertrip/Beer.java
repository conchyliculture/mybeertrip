package fr.renzo.mybeertrip;

import fr.renzo.mybeertrip.databases.MyBeerTripDatabase;

public class Beer {
    private String name;
    private BeerColor color;
    private Brewery brewery;
    private String barcode;
    //TODO picturesw


   	public Beer() {
		// TODO Auto-generated constructor stub
	}


	public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public BeerColor getColor() {
        return color;
    }


    public void setColor(BeerColor color) {
        this.color = color;
    }


    public Brewery getBrewery() {
        return brewery;
    }


    public void setBrewery(Brewery brewery) {
        this.brewery = brewery;
    }


    public String getBarcode() {
        return barcode;
    }

	public void setBarcode(String string) {
		this.barcode= string;
		
	}
}
