package dk.itu.mmad.jsonreaderexample;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.JsonToken;
import android.widget.ArrayAdapter;

public class CitiesTask extends AsyncTask<String, Void, List<String>> {

	private ListActivity activity;
	private Throwable error;

	public CitiesTask(ListActivity activity) {
		this.activity = activity;
	}

	@Override
	protected List<String> doInBackground(String... urls) {
		InputStream inputStream = null;
		JsonReader jsonReader = null;

		try {
			URL url = new URL(urls[0]);
			URLConnection connection = url.openConnection();

			inputStream = connection.getInputStream();

			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

			jsonReader = new JsonReader(reader);

			return readJSON(jsonReader);

		} catch (MalformedURLException e) {
			error = e;
		} catch (IOException e) {
			error = e;
		} finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
				if (jsonReader != null) {
					jsonReader.close();
				}
			} catch (Exception e) {
				// Just too bad
			}
		}

		return null;
	}

	private List<String> readJSON(JsonReader jsonReader) throws IOException {

		List<String> result = new ArrayList<String>();

		jsonReader.beginObject();
		jsonReader.skipValue();
		jsonReader.beginArray();
		while (jsonReader.peek() != JsonToken.END_ARRAY) {
            StringBuilder sb = new StringBuilder();
            jsonReader.beginObject();
            while (jsonReader.peek() != JsonToken.END_OBJECT) {
                String name = jsonReader.nextName();
				if (name.equals("toponymName")) {
					sb.append(jsonReader.nextString());
				} else if (name.equals("population")) {
					sb.append(", population: " + jsonReader.nextInt());
					result.add(sb.toString());
				} else {
					jsonReader.skipValue();
				}
			}
			jsonReader.endObject();
		}

		return result;
	}

	@Override
	protected void onPostExecute(List<String> result) {
		if (result != null) {
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity, android.R.layout.simple_list_item_1,
					result);
			activity.setListAdapter(adapter);
		} else if (error != null) {
			Builder builder = new AlertDialog.Builder(activity);
			builder.setTitle("Error");
			builder.setMessage(error.getMessage());
			builder.setPositiveButton("OK", new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
			});
			builder.show();
		}
	}

}
