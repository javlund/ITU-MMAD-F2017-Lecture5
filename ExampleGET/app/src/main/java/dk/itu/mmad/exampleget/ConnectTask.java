package dk.itu.mmad.exampleget;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import android.os.AsyncTask;
import android.widget.TextView;
import dk.itu.mmad.exampleget.ConnectTask.Param;

public class ConnectTask extends AsyncTask<Param, Void, String> {

	private String resource;
	private TextView resultView;

	public ConnectTask(String resource, TextView resultView) {
		this.resource = resource;
		this.resultView = resultView;
	}

	@Override
	protected String doInBackground(Param... params) {
		StringBuilder queryString = new StringBuilder();
		boolean first = true;
		for (Param p : params) {
			if (first) {
				first = false;
			} else {
				queryString.append("&");
			}
			queryString.append(p.name + "=" + p.value);
		}
		InputStream inputStream = null;
		BufferedReader reader = null;
		try {
			URL url = new URL(resource + "?" + queryString.toString());
			URLConnection connection = url.openConnection();
			inputStream = connection.getInputStream();
			reader = new BufferedReader(new InputStreamReader(inputStream));

			String result = reader.readLine();
			return result;

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
				if (reader != null) {
					reader.close();
				}
			} catch (Exception e) {
				// Sorry guys
			}
		}

		return null;
	}
	
	@Override
	protected void onPostExecute(String result) {
		resultView.setText(result);
	}

	public static class Param {
		private String name;
		private String value;

		public Param(String name, String value) {
			this.name = name;
			this.value = value;
		}

	}
}
