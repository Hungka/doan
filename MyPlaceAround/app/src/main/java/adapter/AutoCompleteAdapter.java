package adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import utils.GPSTracker;
import utils.InformationsAutoText;
import fragment.MapFragment;

/**
 * Created by TranManhHung on 24-Mar-16.
 */
public class AutoCompleteAdapter extends ArrayAdapter implements Filterable {
    private static final String LOG_TAG = "Google Places Autocomplete";
    private static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";
    private static final String TYPE_AUTOCOMPLETE = "/autocomplete";
    private static final String OUT_JSON = "/json";
    private static final String API_KEY = "AIzaSyBrKcTILPHqW09IOvVmYqn5RnWUjJVXZGI";
    private static final String TYPE = "&type=address";
    private static final String LOCATION = "&location=";
    private static final String RADIUS = "&radius=10000";
    private ArrayList<String> resultList;
    public static ArrayList<InformationsAutoText> autoTextArrayList = null;
    int res;
    GPSTracker gps;
    public static double myLatitude;
    public static double myLongitude;

    public AutoCompleteAdapter(Context context, int resource) {
        super(context, resource);
        //MapFragment.hide_keyboard((Activity) context);
        res = resource;
        // Get My Locations
        gps = new GPSTracker(context);
// check if GPS enabled
        if (gps.canGetLocation()) {
            myLatitude = gps.getLatitude();
            myLongitude = gps.getLongitude();


            Toast.makeText(context, "Your Location is - \nLat: " + myLatitude + "\nLong: " + myLongitude, Toast.LENGTH_LONG).show();
        } else {
            gps.showSettingsAlert();
        }
    }

    @Override

    public int getCount() {
        return resultList.size();
    }

    @Override

    public String getItem(int index) {
        return resultList.get(index);
    }


    public static ArrayList<String> autocomplete(String input) {
        ArrayList<String> resultList = null;

        HttpURLConnection conn = null;
        StringBuilder jsonResults = new StringBuilder();
        try {
            StringBuilder sb = new StringBuilder(PLACES_API_BASE + TYPE_AUTOCOMPLETE + OUT_JSON);
            sb.append("?key=" + API_KEY + TYPE + LOCATION + myLatitude + "," + myLongitude + RADIUS);
            //sb.append("&components=country:gr");
            sb.append("&language-vn-vi&input=" + URLEncoder.encode(input, "utf8"));

            URL url = new URL(sb.toString());

            System.out.println("URL: " + url);
            conn = (HttpURLConnection) url.openConnection();
            InputStreamReader in = new InputStreamReader(conn.getInputStream());

            // Load the results into a StringBuilder
            int read;
            char[] buff = new char[1024];
            while ((read = in.read(buff)) != -1) {
                jsonResults.append(buff, 0, read);
            }
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error processing Places API URL", e);
            return resultList;
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error connecting to Places API", e);
            return resultList;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        try {

            // Create a JSON object hierarchy from the results
            JSONObject jsonObj = new JSONObject(jsonResults.toString());
            JSONArray predsJsonArray = jsonObj.getJSONArray("predictions");
            // Extract the Place descriptions from the results
            resultList = new ArrayList<String>(predsJsonArray.length());
            autoTextArrayList = new ArrayList<>(predsJsonArray.length());

            for (int i = 0; i < predsJsonArray.length(); i++) {
                InformationsAutoText tempAuto = new InformationsAutoText();
                System.out.println(predsJsonArray.getJSONObject(i).getString("description"));
                System.out.println("============================================================");
                resultList.add(predsJsonArray.getJSONObject(i).getString("description"));
                tempAuto.setName(predsJsonArray.getJSONObject(i).getString("description"));
                tempAuto.setId(predsJsonArray.getJSONObject(i).getString("id"));
                autoTextArrayList.add(tempAuto);
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Cannot process JSON results", e);
        }

        return resultList;
    }

    @Override
    public Filter getFilter() {

        Filter filter = new Filter() {

            @Override

            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint != null) {
                    // Retrieve the autocomplete results.
                    resultList = autocomplete(constraint.toString());
                    // Assign the data to the FilterResults
                    filterResults.values = resultList;
                    filterResults.count = resultList.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && results.count > 0) {
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }
        };
        return filter;
    }
}
