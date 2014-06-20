package fr.renzo.mybeertrip.databases.beerdb;

public class BeerDBConstants {
	/* CREATE TABLE "beers" ("id" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, 
	 * "key" varchar(255) NOT NULL, 
	 * "title" varchar(255) NOT NULL, 
	 * "synonyms" varchar(255), 
	 * "web" varchar(255), 
	 * "since" integer, 
	 * "seasonal" boolean DEFAULT 'f' NOT NULL, 
	 * "limited" boolean DEFAULT 'f' NOT NULL, 
	 * "kcal" decimal, 
	 * "abv" decimal, 
	 * "og" decimal, 
	 * "srm" integer, 
	 * "ibu" integer, 
	 * "brewery_id" integer, 
	 * "brand_id" integer, 
	 * "grade" integer DEFAULT 4 NOT NULL, 
	 * "txt" varchar(255), 
	 * "txt_auto" boolean DEFAULT 'f' NOT NULL, 
	 * "country_id" integer NOT NULL, 
	 * "region_id" integer, 
	 * "city_id" integer, 
	 * "created_at" datetime NOT NULL, 
	 * "updated_at" datetime NOT NULL);
	 */
	//TODO
	public static final String T_BEERS = "beers";
	public static final String C_BEERS_BARCODE = "barcode";
	public static final String C_BEERS_ID = "id";
	public static final String C_BEERS_KEY = "key";
	public static final String C_BEERS_NAME = "title";
	public static final String C_BEERS_ABV = "abv";
	public static final String C_BEERS_BREWERY_ID = "brewery_id";
	public static final String C_BEERS_TXT = "txt";
	public static final String C_BEERS_COUNTRY_ID = "country_id";
	public static final String C_BEERS_REGION_ID = "region_id";
	public static final String C_BEERS_CITY_ID = "city_id";
	public static final String C_BEERS_CREATED_AT = "created_at";
	public static final String C_BEERS_UPDATED_AT = "updated_at";
	
	/* CREATE TABLE "breweries" ("id" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, 
	 * "key" varchar(255) NOT NULL, 
	 * "title" varchar(255) NOT NULL, 
	 * "synonyms" varchar(255), 
	 * "address" varchar(255), 
	 * "since" integer, 
	 * "closed" integer, 
	 * "prod" integer, 
	 * "prod_grade" integer, 
	 * "grade" integer DEFAULT 4 NOT NULL, 
	 * "txt" varchar(255), 
	 * "txt_auto" boolean DEFAULT 'f' NOT NULL, 
	 * "web" varchar(255), 
	 * "wikipedia" varchar(255), 
	 * "indie" boolean, 
	 * "abinbev" boolean, 
	 * "sabmiller" boolean, 
	 * "heineken" boolean, 
	 * "carlsberg" boolean, 
	 * "molsoncoors" boolean, 
	 * "diageo" boolean, 
	 * "country_id" integer NOT NULL, 
	 * "region_id" integer, 
	 * "city_id" integer, 
	 * "created_at" datetime NOT NULL, 
	 * "updated_at" datetime NOT NULL);
	 */
	//TODO
	public static final String T_BREWERIES = "breweries";
	public static final String C_BREWERIES_ID = "id";
	public static final String C_BREWERIES_NAME = "title";
	public static final String C_BREWERIES = "abv";
	
	/* CREATE TABLE "countries" ("id" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, 
	 * "title" varchar(255) NOT NULL, 
	 * "key" varchar(255) NOT NULL, 
	 * "code" varchar(255) NOT NULL, 
	 * "synonyms" varchar(255), 
	 * "pop" integer NOT NULL, 
	 * "area" integer NOT NULL, 
	 * "continent_id" integer, 
	 * "country_id" integer, 
	 * "s" boolean DEFAULT 'f' NOT NULL, 
	 * "c" boolean DEFAULT 'f' NOT NULL, 
	 * "d" boolean DEFAULT 'f' NOT NULL, 
	 * "motor" varchar(255), 
	 * "iso2" varchar(255), 
	 * "iso3" varchar(255), 
	 * "fifa" varchar(255), 
	 * "net" varchar(255), 
	 * "wikipedia" varchar(255), 
	 * "created_at" datetime NOT NULL, 
	 * "updated_at" datetime NOT NULL);
	 */
	//TODO
	public static final String T_COUNTRIES = "countries";
	public static final String C_COUNTRIES_ID = "id";
	public static final String C_COUNTRIES_NAME = "title";
	public static final String C_COUNTRIES_POP = "pop";
	
	
	public static final String DATE_FORMAT = "yyyy-MM-dd hh:mm:ss.SSS";
}
