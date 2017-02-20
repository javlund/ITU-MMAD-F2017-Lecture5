package dk.itu.mmad.examplepost;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import dk.itu.mmad.examplepost.ConnectTask.Param;

public class MainActivity extends Activity {

	EditText name;
	EditText age;
	TextView result;
	
	public final static String RESOURCE = "http://www.itu.dk/people/jacok/MMAD/services/examplepost/";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		name = (EditText) findViewById(R.id.nameedit);
		age = (EditText) findViewById(R.id.ageedit);
		result = (TextView) findViewById(R.id.result);
	}

	public void submit(View v) {
		Param nameParam = new Param("name", name.getText().toString());
		Param ageParam = new Param("age", age.getText().toString());
		
		new ConnectTask(RESOURCE, result).execute(nameParam, ageParam);
		
	}
}
