package type.support;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.example.tranmanhhung.myplacearound.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import adapter.TextDirectionAdapter;
import interface_.SaveRoute;
import utils.InfoLine;
import utils.Line;

/**
 * Created by TranManhHung on 01-Apr-16.
 */
public class GetDirections extends AsyncTask<String, Void, String> implements SaveRoute{
    GoogleMap mMap;
    ListView listView;
    Context context;


    public GetDirections(GoogleMap googleMap) {
        mMap = googleMap;
    }

    public GetDirections(Context context,GoogleMap googleMap, ListView lv) {
        mMap = googleMap;
        listView = lv;
        this.context = context;
    }

    @Override
    protected String doInBackground(String... params) {

        String data = "";
        try {
            // Fetching the data from web service
            data = downloadUrl(params[0]);
        } catch (Exception e) {
            Log.d("Background Task", e.toString());
        }
        return data;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        ParserTask parserTask = new ParserTask();
        // Invokes the thread for parsing the JSON data
        parserTask.execute(result);
    }

    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);
            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();
            // Connecting to url
            urlConnection.connect();
            // Reading data from url
            iStream = urlConnection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));
            StringBuffer sb = new StringBuffer();
            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            data = sb.toString();
            br.close();

        } catch (Exception e) {
            //Log.d("Exception while downloading url", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    @Override
    public void SaveInSQL() {

    }

    private class ParserTask extends AsyncTask<String, Integer, InfoLine> {

        // Parsing the data in non-ui thread
        @Override
        protected InfoLine doInBackground(String... jsonData) {
            JSONObject jObject;
            InfoLine infoLine = null;
            // List<List<HashMap<String, String>>> routes = null;
            try {
                jObject = new JSONObject(jsonData[0]);
                DirectionsJSONParser parser = new DirectionsJSONParser();
                // Starts parsing data
                infoLine = parser.parse(jObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return infoLine;
        }

        // Executes in UI thread, after the parsing process
        @Override
        protected void onPostExecute(InfoLine result) {

            List<List<HashMap<String, String>>> loctions = result.getListPoint();
            List<Line> lineList = result.getListLine();

            ArrayList<LatLng> points = null;
            PolylineOptions lineOptions = null;

            // Traversing through all the routes
            for (int i = 0; i < loctions.size(); i++) {
                points = new ArrayList<LatLng>();
                lineOptions = new PolylineOptions();
                // Fetching i-th route
                List<HashMap<String, String>> path = loctions.get(i);
                // Fetching all the points in i-th route
                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);
                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);
                    points.add(position);
                }
                // Adding all the points in the route to LineOptions
                lineOptions.addAll(points);
                lineOptions.width(8);
                lineOptions.color(Color.BLUE);
            }
            try {
                mMap.addPolyline(lineOptions);
            } catch (Exception e) {
                Log.e("Loi CMNR", e.toString());
            }

            TextDirectionAdapter adapter = new TextDirectionAdapter(context, R.layout.layout_text_direction_adapter,lineList);
            listView.setAdapter(adapter);
            // Drawing polyline in the Google Map for the i-th route
        }
    }
}
