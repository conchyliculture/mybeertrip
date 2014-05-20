package fr.renzo.mybeertrip.databases;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import fr.renzo.mybeertrip.Beer;
import fr.renzo.mybeertrip.R;

public class BeerDBDatabase implements MyBeerTripDatabase{

	private File sqlitefile;
	private SQLiteDatabase sqlh;
	private Context context;

	public BeerDBDatabase(Context context) {
		this.context = context;
		String dbpath = copyDB("beed.db");//TODO BAD STRING EN DUR
		this.sqlh = SQLiteDatabase.openDatabase(dbpath, null, SQLiteDatabase.OPEN_READWRITE);
		
	}
	
	private String copyDB(String dbpath) {
		AssetManager am = context.getAssets();
		String respath = null;
		try {
			InputStream in = am.open("beer.db");//TODO
			File outFile = new File(Environment.getExternalStorageDirectory(),context.getString(R.string.beer_db_file_external_path));
			if (!outFile.exists()) {
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
		} catch (IOException e) {
			e.printStackTrace();
		}
		return respath;
	}
	
	@Override
	public Beer findBeer(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Beer getBeerFromBarcode(String res) {
		// TODO Auto-generated method stub
		return null;
	}

}
