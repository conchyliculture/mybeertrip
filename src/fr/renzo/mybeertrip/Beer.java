package fr.renzo.mybeertrip;

public class Beer {
    private String name;
    private BeerColor color;
    private Brewery brewery;
    private int barcode;
    //TODO picturesw


    public Beer (String name, BeerColor color, Brewery brewery, int barcode) {
        this.name=name;
        this.color=color;
        this.brewery=brewery;
        this.barcode=barcode;
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


    public int getBarcode() {
        return barcode;
    }


    public void setBarcode(int barcode) {
        this.barcode = barcode;
    }
}
