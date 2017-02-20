package dk.itu.mmad.jsonexample;

import android.app.ListActivity;
import android.os.Bundle;

public class MainActivity extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		String host = "http://api.geonames.org/citiesJSON";
		String queryString = "?north=57&south=54&east=13&west=7&lang=en&username=mmad";
		
		new CitiesTask(this).execute(host + queryString);
		
	}
	
	

}
