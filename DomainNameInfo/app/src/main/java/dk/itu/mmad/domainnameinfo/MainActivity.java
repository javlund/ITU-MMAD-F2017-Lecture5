package dk.itu.mmad.domainnameinfo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

	EditText txt;
	TextView result;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		txt = (EditText) findViewById(R.id.txt);
		result = (TextView) findViewById(R.id.result);
	}

	
	public void search(View v) {
		new DomainNameTask(result).execute(txt.getText().toString());
	}

}
