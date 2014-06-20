package fr.renzo.mybeertrip;

import java.io.Serializable;


public class Beer implements Serializable{

	private static final long serialVersionUID = -3512688813404580801L;
	private String name;
    private Brewery brewery;
    private String barcode="";
	private double abv=0;
    //TODO picturesw
	private Country country;
	private boolean seasonal=false;
	private boolean special=false;
	private String description="";
	private long id=-1;

	public String getKey(){
		return this.name.replaceAll("\\s+","").toLowerCase();
	}

   	public double getAbv() {
		return abv;
	}

	public void setAbv(double abv) {
		this.abv = abv;
	}


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public void setAbv(float abv) {
		this.abv = abv;
	}


	public Object getSpecial() {
		return special;
	}


	public Country getCountry() {
		if (country==null){
    		return new Country();
    	} else {
    		return country;
    	}
	}


	public boolean isSeasonal() {
		return seasonal;
	}


	public String getDescription() {
		return description;
	}


	public Beer() {}


	public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public Brewery getBrewery() {
    	if (brewery==null){
    		return new Brewery();
    	} else {
    		return brewery;
    	}
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


	public void setCountry(Country country) {
		this.country=country;
		
	}


	public void setDescription(String beer_description) {
		this.description = beer_description;
		
	}


	public void setSeasonal(boolean beer_seasonal) {
		this.seasonal = beer_seasonal;
		
	}


	public void setSpecial(boolean beer_special) {
		this.special = beer_special;
		
	}
}
