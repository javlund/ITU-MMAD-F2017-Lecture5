package dk.itu.mmad.examplepost;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import android.os.AsyncTask;
import android.widget.TextView;
import dk.itu.mmad.examplepost.ConnectTask.Param;

public class ConnectTask extends AsyncTask<Param, Void, String> {

	private String resource;
	private TextView resultView;

	public ConnectTask(String resource, TextView resultView) {
		this.resource = resource;
		this.resultView = resultView;
	}

	@Override
	protected String doInBackground(Param... params) {
		String queryString = makeQueryString(params);
		
		InputStream inputStream = null;
		OutputStream outputStream = null;
		BufferedReader reader = null;
		try {
			URL url = new URL(resource);
			URLConnection connection = url.openConnection();
			
			((HttpURLConnection)connection).setRequestMethod("POST");
			
			outputStream = connection.getOutputStream();
			PrintWriter writer = new PrintWriter(outputStream);
			writer.write(queryString);
			writer.flush();
			writer.close();
			
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
	
	private String makeQueryString(Param... params) {
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
		return queryString.toString();
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
