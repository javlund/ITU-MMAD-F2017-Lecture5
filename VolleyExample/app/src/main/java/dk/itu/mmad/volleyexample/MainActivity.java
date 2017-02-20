package dk.itu.mmad.volleyexample;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ListActivity {

    public static final String TAG = "georeq";

    private RequestQueue requestQueue;

    private String host = "http://api.geonames.org/citiesJSON";
    private String queryString = "?north=57&south=54&east=13&west=7&lang=en&username=mmad";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestQueue = Volley.newRequestQueue(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, host + queryString, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                List<String> result = new ArrayList<String>();
                try {
                    JSONArray names = response.getJSONArray("geonames");
                    for (int i = 0; i < names.length(); i++) {
                        JSONObject name = names.getJSONObject(i);
                        String str = name.getString("toponymName") + ", population: " + name.getInt("population");
                        result.add(str);
                    }
                } catch(JSONException e) {
                    showError(e);
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1,
                        result);
                setListAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                showError(error);
            }
        });
        request.setTag(TAG);

        requestQueue.add(request);
    }

    @Override
    protected void onStop() {
        super.onStop();

        if(requestQueue != null) {
            requestQueue.cancelAll(TAG);
        }
    }

    private void showError(Exception e) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Error");
        builder.setMessage(e.getMessage());
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }



}
