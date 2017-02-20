package dk.itu.mmad.downloadimage;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		ImageView imageView = (ImageView) findViewById(R.id.img);
		
		new DownloadTask(imageView).execute("https://www.android.com/images/logo.png");
	}

}
