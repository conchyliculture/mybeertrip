package fr.renzo.mybeertrip.databases;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.MergeCursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Environment;
import android.util.Log;
import fr.renzo.mybeertrip.Beer;
import fr.renzo.mybeertrip.R;

public class BeerDBDatabase implements MyBeerTripDatabase{
	
	private static final String TAG = "MyBeerTripDatabase";

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
	public static final String T_BEERS = "beers";
	public static final String C_BEERS_BARCODE = "barcode";
	public static final String C_BEERS_ID = "id";
	public static final String C_BEERS_NAME = "title";
	
	private SQLiteDatabase sqlh;
	private Context context;

	private HashMap<String, Integer> mapBeerColNameToIndex;

	public BeerDBDatabase(Context context) {
		this.context = context;
		String dbpath = copyDB("beed.db");//TODO BAD STRING EN DUR
		this.sqlh = SQLiteDatabase.openDatabase(dbpath, null, SQLiteDatabase.OPEN_READWRITE);
		
		this.mapBeerColNameToIndex = doMapBeerColNameToIndex();
		Iterator iterator = mapBeerColNameToIndex.keySet().iterator();
		 
		while (iterator.hasNext()) {
		   String key = iterator.next().toString();
		   String value = mapBeerColNameToIndex.get(key).toString();
		 
		  Log.d(TAG,key + " " + value);
		}
		Log.d(TAG,"Map count = "+this.mapBeerColNameToIndex.size());
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
		
		Cursor c = myRawQuery("SELECT * FROM "+T_BEERS);
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
		
		tempsqlh.execSQL("ALTER TABLE "+T_BEERS+" ADD COLUMN "+C_BEERS_BARCODE+" VARCHAR(255)");
		Log.d(TAG,"Added column");
		tempsqlh.execSQL("UPDATE "+T_BEERS+" SET "+C_BEERS_BARCODE+" = '54050051' WHERE "+C_BEERS_ID+" = 1201 ");
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
				Log.d(TAG,query+""+objects.toString());
				c = this.sqlh.rawQuery(query, objects);		
			} catch (SQLiteException e) {
				if (c!= null){
					c.close();
				}
				Log.d(TAG,"SQL error "+e.getLocalizedMessage());
			}
		return c;
	}
	
	public Beer makeBeerFromStarQuery(Cursor c){
		Beer b = new Beer();
		int count = c.getColumnCount();
		for (int i = 0; i < count; i++) {
			String colname = c.getColumnName(i);
			int colindex = mapBeerColNameToIndex.get(colname);
			switch (colname) {
			case C_BEERS_BARCODE:
				b.setBarcode(c.getString(colindex));
				break;
			case C_BEERS_NAME:
				b.setName(c.getString(colindex));
				break;

			default:
				break;
			}
		}
		return b;
	}
	
	@Override
	public ArrayList<Beer> findBeersByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Beer findBeerByBarcode(String barcode) {
		Beer resbeer = null;
		Cursor c= myRawQuery("SELECT * from "+T_BEERS+" WHERE "+
									C_BEERS_BARCODE + " = ?", barcode);
		if (c.moveToFirst()){
			resbeer = makeBeerFromStarQuery(c);
		}
		
		c.close();
		
		return resbeer;
	}

	@Override
	public Cursor rawQuery(String query, String[] objects) {
		return myRawQuery(query,objects);
	}

	@Override
	public Cursor searchBeersByName(String constraint) {
		Log.d(TAG,"Search beers with name "+constraint);
		return myRawQuery("SELECT "+C_BEERS_ID+" AS _id, "+C_BEERS_NAME +" FROM "+T_BEERS+
				" WHERE "+C_BEERS_NAME+" LIKE ?", "%"+constraint+"%");
		
	}


}
