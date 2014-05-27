package fr.renzo.mybeertrip.activities;

import fr.renzo.mybeertrip.R;
import android.app.Activity;
import android.os.Bundle;

public class AddBeerActivity extends Activity {
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle("Add a new Drink");
		setContentView(R.layout.activity_add_beer);
	}
}
