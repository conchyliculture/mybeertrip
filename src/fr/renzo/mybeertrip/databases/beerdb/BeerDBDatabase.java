package fr.renzo.mybeertrip.databases.beerdb;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteStatement;
import android.os.Environment;
import android.util.Log;
import fr.renzo.mybeertrip.Beer;
import fr.renzo.mybeertrip.Brewery;
import fr.renzo.mybeertrip.Country;
import fr.renzo.mybeertrip.R;
import fr.renzo.mybeertrip.databases.MyBeerTripDatabase;

public class BeerDBDatabase implements MyBeerTripDatabase{

	private static final String TAG = "BeerDBDatabase";




	private SQLiteDatabase sqlh;
	private Context context;

	private HashMap<String, Integer> mapBeerColNameToIndex;

	public BeerDBDatabase(Context context) {
		this.context = context;
		String dbpath = copyDB("beed.db");//TODO BAD STRING EN DUR
		this.sqlh = SQLiteDatabase.openDatabase(dbpath, null, SQLiteDatabase.OPEN_READWRITE);

		//		this.mapBeerColNameToIndex = doMapBeerColNameToIndex();
		//		Iterator iterator = mapBeerColNameToIndex.keySet().iterator();
		//		 
		//		while (iterator.hasNext()) {
		//		   String key = iterator.next().toString();
		//		   String value = mapBeerColNameToIndex.get(key).toString();
		//		 
		//		  Log.d(TAG,key + " " + value);
		//		}
		//		Log.d(TAG,"Map count = "+this.mapBeerColNameToIndex.size());
	}

	private String copyDB(String dbpath) {
		AssetManager am = context.getAssets();
		String respath = null;
		try {
			InputStream in = am.open("beer.db");//TODO
			File outFile = new File(Environment.getExternalStorageDirectory(),context.getString(R.string.beer_db_file_external_path));
			// LOL TODO

			if (outFile.exists()) {
				outFile.delete();
			}
			{
				FileOutputStream out = new FileOutputStream(outFile);

				byte[] buffer = new byte[1024];
				int read;
				while((read = in.read(buffer)) != -1){
					out.write(buffer, 0, read);
				}
				in.close();
				out.flush();
				out.close();
			}

			respath= outFile.getAbsolutePath();
			patchDB(respath);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return respath;
	}

	private HashMap<String, Integer> doMapBeerColNameToIndex (){
		HashMap<String, Integer> map = new HashMap<String,Integer>();

		Cursor c = myRawQuery("SELECT * FROM "+BeerDBConstants.T_BEERS);
		if (c.moveToFirst()){
			int count = c.getColumnCount();
			for (int i = 0; i < count; i++) {
				String name = c.getColumnName(i);
				map.put(name, i);
			}
		}

		return map;
	}

	private void patchDB(String dbpath) {
		SQLiteDatabase tempsqlh = SQLiteDatabase.openDatabase(dbpath, null, SQLiteDatabase.OPEN_READWRITE);

		tempsqlh.execSQL("ALTER TABLE "+BeerDBConstants.T_BEERS+
				" ADD COLUMN "+BeerDBConstants.C_BEERS_BARCODE+" VARCHAR(255)");
		Log.d(TAG,"Added column");
		tempsqlh.execSQL("UPDATE "+BeerDBConstants.T_BEERS+
				" SET "+BeerDBConstants.C_BEERS_BARCODE+" = '54050051' "+
				" WHERE "+BeerDBConstants.C_BEERS_ID+" = 1201 ");
		tempsqlh.close();
	}


	public Cursor myRawQuery(String query)  {
		return myRawQuery(query,new String[0]);
	}

	public Cursor myRawQuery(String query, String param1)  {
		return myRawQuery(query,new String[]{param1});
	}

	public Cursor myRawQuery(String query, String[] objects )  {
		Cursor c=null;
		try {
			Log.d(TAG,query);
			c = this.sqlh.rawQuery(query, objects);		
		} catch (SQLiteException e) {
			if (c!= null){
				c.close();
			}
			Log.d(TAG,"SQL error "+e.getLocalizedMessage());
		}
		return c;
	}

	//	public Beer makeBeerFromStarQuery(Cursor c){
	//		Beer b = new Beer();
	//		int count = c.getColumnCount();
	//		for (int i = 0; i < count; i++) {
	//			String colname = c.getColumnName(i);
	//			int colindex = mapBeerColNameToIndex.get(colname);
	//			switch (colname) {
	//			case BeerDBConstants.C_BEERS_BARCODE:
	//				b.setBarcode(c.getString(colindex));
	//				break;
	//			case BeerDBConstants.C_BEERS_NAME:
	//				b.setName(c.getString(colindex));
	//				break;
	//
	//			default:
	//				break;
	//			}
	//		}
	//		return b;
	//	}

	@Override
	public Cursor rawQuery(String query, String[] objects) {
		return myRawQuery(query,objects);
	}

	@Override
	public Cursor searchBeersByName(String constraint) {
		Log.d(TAG,"Search beers with name "+constraint);
		return myRawQuery("SELECT "+BeerDBConstants.C_BEERS_ID+" AS _id, "+
				BeerDBConstants.C_BEERS_NAME +" FROM "+
				BeerDBConstants.T_BEERS+
				" WHERE "+BeerDBConstants.C_BEERS_NAME+" LIKE ?",
				"%"+constraint+"%");

	}
	@Override
	public Cursor searchBreweriesByName(String constraint) {
		return myRawQuery("SELECT "+BeerDBConstants.C_BREWERIES_ID+" AS _id, "+
				BeerDBConstants.C_BREWERIES_NAME +
				" FROM "+BeerDBConstants.T_BREWERIES+
				" WHERE "+BeerDBConstants.C_BREWERIES_NAME+" LIKE ? "+
				" ORDER BY "+BeerDBConstants.C_BREWERIES_NAME,
				"%"+constraint+"%");
	}

	private Cursor getAllBeerDataFromString(String search) {
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
		Cursor c = myRawQuery("SELECT "+BeerDBConstants.C_BEERS_ID+", "
				+BeerDBConstants.C_BEERS_NAME+", "
				+BeerDBConstants.C_BEERS_ABV+", "
				+BeerDBConstants.C_BEERS_BREWERY_ID+", "
				+BeerDBConstants.C_BEERS_TXT+", "
				+BeerDBConstants.C_BEERS_COUNTRY_ID+", "
				+BeerDBConstants.C_BEERS_REGION_ID+", "
				+BeerDBConstants.C_BEERS_CITY_ID
				+" FROM "+BeerDBConstants.T_BEERS+" " + search);

		return c;
	}

	@Override
	public Beer getBeerById(long _id) {
		Beer b = null;
		Cursor c = getAllBeerDataFromString("WHERE "+BeerDBConstants.C_BEERS_ID+" = "+_id);
		if (c.moveToFirst()) {
			int i=0; //TODO
			b= new Beer();
			b.setName(c.getString(++i));
			b.setAbv(c.getFloat(++i));
			b.setBrewery(getBreweryById(c.getLong(++i))); 
			b.setDescription(c.getString(++i));
			b.setCountry(getCountryById(c.getLong(++i))); 			
		}
		c.close();
		return b;
	}

	@Override
	public Beer getBeerByBarcode(String barcode) {
		Beer resbeer = null;
		if (barcode != null) {
			Cursor c= myRawQuery("SELECT "+BeerDBConstants.C_BEERS_ID+
					" from "+BeerDBConstants.T_BEERS+
					" WHERE "+
					BeerDBConstants.C_BEERS_BARCODE + " = ?", 
					barcode);
			if (c.moveToFirst()){
				resbeer = getBeerById(c.getLong(0));
			}
			c.close();
		}

		return resbeer;
	}

	public Beer getBeerByName(String name) {
		Beer resbeer = null;
		if (name != null) {
			Cursor c= myRawQuery("SELECT "+BeerDBConstants.C_BEERS_ID+
					" from "+BeerDBConstants.T_BEERS+
					" WHERE "+
					BeerDBConstants.C_BEERS_NAME + " = ?", 
					name);
			if (c.moveToFirst()) {
				resbeer=getBeerById(c.getLong(0));
			}
			c.close();
		}
		return resbeer;
	}

	@Override
	public long addBeer(Beer b) {
		//TODO
		SQLiteStatement stmt = sqlh.compileStatement("INSERT INTO "+BeerDBConstants.T_BEERS+ "("+
				BeerDBConstants.C_BEERS_ID+", "
				+BeerDBConstants.C_BEERS_NAME+", "
				+BeerDBConstants.C_BEERS_KEY+", "
				+BeerDBConstants.C_BEERS_ABV+", "
				+BeerDBConstants.C_BEERS_BREWERY_ID+", "
				+BeerDBConstants.C_BEERS_TXT+", "
				+BeerDBConstants.C_BEERS_CREATED_AT+", "
				+BeerDBConstants.C_BEERS_UPDATED_AT+", "
				+BeerDBConstants.C_BEERS_COUNTRY_ID //  CAREFUL OF DA COMA
				+") VALUES ("+
				"NULL, "+ // ID
				"?, "+ // NAME
				"?, "+ // KEY
				"?, "+ // ABV
				"?, "+ // BREWERY_ID
				"?, "+ // DESCR
				"?, "+ // CREATED_AT
				"?, "+ // UPDATED_AT
				"? "+ // COUNTRY_ID CAREFUL OF DA COMA
				")");
		int i=0;
		stmt.bindString(++i, b.getName());
		stmt.bindString(++i, b.getKey());
		stmt.bindDouble(++i, b.getAbv());
		stmt.bindLong(++i, b.getBrewery().getId());
		stmt.bindString(++i, b.getDescription());
		stmt.bindString(++i, new SimpleDateFormat(BeerDBConstants.DATE_FORMAT).format(new Date(System.currentTimeMillis())));
		stmt.bindString(++i, new SimpleDateFormat(BeerDBConstants.DATE_FORMAT).format(new Date(System.currentTimeMillis())));
		stmt.bindLong(++i, b.getCountry().getId());
		long returnid=stmt.executeInsert();

		return returnid;


	}

	@Override
	public Cursor searchCountriesByName(String string) {
		return myRawQuery("SELECT "+BeerDBConstants.C_COUNTRIES_ID+" AS _id, "+
				BeerDBConstants.C_COUNTRIES_NAME +" FROM "+
				BeerDBConstants.T_COUNTRIES+
				" WHERE "+BeerDBConstants.C_COUNTRIES_NAME+" LIKE ? "+
				" ORDER BY "+BeerDBConstants.C_COUNTRIES_POP+" DESC", 
				"%"+string+"%");
	}

	private Cursor getAllCountryDataFromString(String search) {
		/* CREATE TABLE "countries" (
		 * "id" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, 
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
		Cursor c = myRawQuery("SELECT "+BeerDBConstants.C_COUNTRIES_ID+", "
				+BeerDBConstants.C_COUNTRIES_NAME
				+" FROM "+BeerDBConstants.T_COUNTRIES+" " + search);

		return c;
	}

	@Override
	public Country getCountryById(long _id) {
		Country country = null;
		Cursor c = getAllCountryDataFromString("WHERE "+BeerDBConstants.C_COUNTRIES_ID+" = "+_id);
		if (c.moveToFirst()) {
			int i=0; //TODO
			country= new Country();
			country.setId(c.getLong(i++));
			country.setName(c.getString(i++));
		}
		c.close();
		return country;
	}

	public Country getCountryByName(String name) {
		Country res=null;
		if (name != null) {
			Cursor c = myRawQuery("SELECT "+BeerDBConstants.C_COUNTRIES_ID+
					" FROM "+BeerDBConstants.T_COUNTRIES+
					" WHERE "+BeerDBConstants.C_COUNTRIES_NAME+" = ? "+
					" ORDER BY "+BeerDBConstants.C_COUNTRIES_NAME,
					name);
			if (c.moveToFirst()) {
				res=getCountryById(c.getLong(0));
			}
			c.close();
		}
		return res;
	}


	private Cursor getAllBreweryDataFromString(String search) {
		/* CREATE TABLE "breweries" (
		 * "id" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, 
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
		Cursor c = myRawQuery("SELECT "+BeerDBConstants.C_BREWERIES_ID+", "
				+BeerDBConstants.C_BREWERIES_NAME
				+" FROM "+BeerDBConstants.T_BREWERIES+" " + search);

		return c;
	}

	@Override
	public Brewery getBreweryById(long _id) {
		Brewery brewery = null;
		Cursor c = getAllBreweryDataFromString(" WHERE "+BeerDBConstants.C_BREWERIES_ID+" = "+_id);
		if (c.moveToFirst()) {
			int i=0; //TODO 
			brewery= new Brewery();
			brewery.setId(c.getLong(i++));
			brewery.setName(c.getString(i++));
		}
		c.close();
		return brewery;
	}

	public Brewery getBreweryByName(String name) {
		Brewery res= null;
		if (name != null) {
			Cursor c = myRawQuery("SELECT "+BeerDBConstants.C_BREWERIES_ID+
					" FROM "+BeerDBConstants.T_BREWERIES+
					" WHERE "+BeerDBConstants.C_BREWERIES_NAME+" = ? "+
					" ORDER BY "+BeerDBConstants.C_BREWERIES_NAME,
					name);
			if (c.moveToFirst()) {
				res=getBreweryById(c.getLong(0));
			}
			c.close();
		}
		return res;
	}

}
