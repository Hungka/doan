package type.support;

import android.os.AsyncTask;
import android.util.Log;

import com.example.tranmanhhung.myplacearound.DirectionActivity;
import com.google.android.gms.maps.model.LatLng;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import interface_.GetLinkDirection;
import sql.ThreeRoute;

/**
 * Created by TranManhHung on 04-May-16.
 */
public class SaveRouter extends AsyncTask<String, Void, String> {
    LatLng start;
    LatLng destination;

    public SaveRouter(LatLng st, LatLng des) {
        start = st;
        destination = des;

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
    protected String doInBackground(String... params) {
        try {

            downloadUrl(params[0]);

        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Error SaveRouter", e.toString());
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        ThreeRoute th = new ThreeRoute();


    }


}
