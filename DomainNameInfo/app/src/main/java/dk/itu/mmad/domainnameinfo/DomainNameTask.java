package dk.itu.mmad.domainnameinfo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import android.os.AsyncTask;
import android.widget.TextView;

public class DomainNameTask extends AsyncTask<String, Void, String> {
	
	private TextView tv;
	
	public DomainNameTask(TextView tv) {
		this.tv = tv;
	}

	@Override
	protected String doInBackground(String... params) {

		Socket socket = null;
		InputStream inputStream = null;
		OutputStream outputStream = null;
		StringBuilder sb = new StringBuilder();
		try {
			// Finding the right whois server can be a complex task.
			// Markmonitor provides info for sites like google.com and facebook.com.
			// socket = new Socket("whois.verisign-grs.com", 43);
			socket = new Socket("whois.markmonitor.com", 43);

			outputStream = socket.getOutputStream();
			PrintWriter writer = new PrintWriter(outputStream, true);
			writer.println(params[0]);
			
			inputStream = socket.getInputStream();

			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(socket != null) {
					socket.close();
				}
				if (inputStream != null) {
					inputStream.close();
				}
				if (outputStream != null) {
					outputStream.close();
				}
			} catch (IOException e) {
				// Tough luck
			}
		}
		return sb.toString();
	}

	@Override
	protected void onPostExecute(String result) {
		tv.setText(result);
	}

}
