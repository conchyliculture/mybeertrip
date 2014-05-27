package fr.renzo.mybeertrip;

import java.io.Serializable;


public class Beer implements Serializable{

	private static final long serialVersionUID = -3512688813404580801L;
	private String name;
    private Brewery brewery;
    private String barcode;
	private float abv;
    //TODO picturesw


   	public Beer() {}


	public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
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


	public void setABV(float abv) {
		this.abv = abv;
	}


	public void setCountry(Object object) {
		// TODO Auto-generated method stub
		
	}


	public void setRegion(Object object) {
		// TODO Auto-generated method stub
		
	}


	public void setCity(Object object) {
		// TODO Auto-generated method stub
		
	}
}
