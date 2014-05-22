package fr.renzo.mybeertrip.databases;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SearchBeerNameCursorAdapter extends CursorAdapter {
	private LayoutInflater inflater;
	private MyBeerTripDatabase dbh;

	public SearchBeerNameCursorAdapter(Context context, Cursor c, MyBeerTripDatabase dbh) {
		super(context, c);
		this.dbh = dbh;
	}

	@Override
	public void bindView(View view, Context ctx, Cursor cursor) {
		String t = cursor.getString(1);
	    ((TextView) view).setText(t);
	}

	@Override
	public View newView(Context ctx, Cursor cursor, ViewGroup parent) {
		inflater = LayoutInflater.from(ctx);
	    final TextView view = (TextView) inflater.inflate(android.R.layout.simple_dropdown_item_1line, parent, false);
	    return view;
	}
	@Override
	public String convertToString(Cursor cursor) {
	    return cursor.getString(1);
	}
	
	@Override
	public Cursor runQueryOnBackgroundThread(CharSequence constraint) {
		if (constraint == null)
			return null;
		Cursor rescursor = dbh.searchBeersByName(constraint.toString());
		return rescursor;		
	}

}
