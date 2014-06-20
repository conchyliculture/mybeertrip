package fr.renzo.mybeertrip.databases;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SearchBreweryCursorAdapter extends CursorAdapter {
	private LayoutInflater inflater;
	private MyBeerTripDatabase dbh;
	
	@SuppressWarnings("deprecation")
	public SearchBreweryCursorAdapter(Context context, Cursor c, MyBeerTripDatabase dbh) {
		super(context, c);
		this.dbh=dbh;
	}

	@Override
	public void bindView(View view, Context ctx, Cursor cursor) {
		String t = cursor.getString(1);
	    ((TextView) view).setText(t);

	}

	@Override
	public View newView(Context ctx, Cursor cursor, ViewGroup parent) {
		inflater = LayoutInflater.from(ctx);
	    TextView view = (TextView) inflater.inflate(android.R.layout.select_dialog_item, parent, false);
	    return view;
	}
	@Override
	public String convertToString(Cursor cursor) {
	    return cursor.getString(1);
	}
	public Cursor runQueryOnBackgroundThread(CharSequence constraint) {
		if (constraint == null)
			return null;

		Cursor c = dbh.searchBreweriesByName(constraint.toString());
		
		return c;
	}

}
