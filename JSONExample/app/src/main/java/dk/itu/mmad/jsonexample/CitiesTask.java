package dk.itu.mmad.jsonexample;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.AsyncTask;
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
		

		try {
			URL url = new URL(urls[0]);
			URLConnection connection = url.openConnection();

			inputStream = connection.getInputStream();

			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

			String line = null;
			StringBuilder sb = new StringBuilder();
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}

			return readJSON(sb.toString());

		} catch (MalformedURLException e) {
			error = e;
		} catch (IOException e) {
			error = e;
		} catch (JSONException e) {
			error = e;
		} finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (Exception e) {
				// Just too bad
			}
		}
		return null;
	}

	private List<String> readJSON(String input) throws JSONException {
        List<String> result = new ArrayList<String>();

        JSONObject jsonObject = new JSONObject(input);
        JSONArray names = jsonObject.getJSONArray("geonames");
        for (int i = 0; i < names.length(); i++) {
            JSONObject name = names.getJSONObject(i);
            String str = name.getString("toponymName") + ", population: " + name.getInt("population");
            result.add(str);
        }
        return result;
    }

	@Override
	protected void onPostExecute(List<String> result) {
		if (result != null) {
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity, android.R.layout.simple_list_item_1,
					result);
			activity.setListAdapter(adapter);
		} else if(error != null) {
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
