package gson;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.tranmanhhung.myplacearound.MapsActivity;
import com.example.tranmanhhung.myplacearound.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;

import adapter.GeneralAdapter;
import fragment.GeneralFragment;
import fragment.ShowDialog;
import sql.SQLite;
import type.support.ShowTrueType;
import utils.InformationsPlace;

public class GetDataGeneral extends AsyncTask<String, Void, ArrayList<InformationsPlace>> {


    public static ArrayList<InformationsPlace> arrayInforPlace;
    Context mContext;
    GoogleMap mMap;
    LatLng myLatLng;
    String mType = "";
    SQLite mSQLite;
    ShowDialog showDialog;

    public GetDataGeneral(Context context, LatLng locTouch, GoogleMap gM, ShowDialog showDialog) {
        mContext = context;
        myLatLng = locTouch;
        mMap = gM;
        mSQLite = new SQLite(context);
        this.showDialog = showDialog;
    }

    public GetDataGeneral(Context context, LatLng locTouch, GoogleMap gM, String type, ShowDialog showDialog) {
        mContext = context;
        myLatLng = locTouch;
        mMap = gM;
        mType = type;
        mSQLite = new SQLite(context);
        this.showDialog = showDialog;
    }


    @Override
    protected ArrayList<InformationsPlace> doInBackground(String... params) {
        arrayInforPlace = new ArrayList<InformationsPlace>();
        //Check internet is online
        if (mSQLite.isOnline()) {
            String data = null;
            try {
                data = downloadUrl(params[0]);
                try {
                    Gson gson = new Gson();
                    JsonArray jsonArray = gson.fromJson(data, DataJson.class).results;
                    Log.e("Json Download", jsonArray.toString());

                    for (JsonElement mElement : jsonArray) {
                        Log.e("Json mElement", mElement.toString());
                        Results results = gson.fromJson(mElement, Results.class);

                        String name = results.name.toString();
                        name = name.replace("\"", "");
                        String id = results.place_id.toString();
                        String rating = String.valueOf(results.rating);
                        JsonElement geometry = results.geometry;
                        String address = results.vicinity.toString();
                        address = address.replace("\"", "");
                        Location loc = gson.fromJson(gson.fromJson(geometry, Geometry.class).location, Location.class);
                        String lat = loc.lat.toString();
                        String lng = loc.lng.toString();

                        double dis = CalculationByDistance(
                                Double.valueOf(lat)
                                , Double.valueOf(lng)
                                , myLatLng.latitude
                                , myLatLng.longitude);
                        dis = Math.round(dis * 100);
                        dis /= 100;

                        InformationsPlace infomationsPlace = new InformationsPlace();
                        infomationsPlace.setAddress(address);
                        infomationsPlace.setLat(lat);
                        infomationsPlace.setLng(lng);
                        infomationsPlace.setId(id);
                        infomationsPlace.setDistance(String.valueOf(dis));
                        infomationsPlace.setName(name);
                        infomationsPlace.setRating(rating);
                        if (mType.compareTo("") != 0) {
                            mSQLite.ThemDuLieu(name, address, String.valueOf(dis), lat, lng, mType);
                        }
                        arrayInforPlace.add(infomationsPlace);
                    }
                } catch (Exception e) {
//                Toast.makeText(mContext,"Can't get data1", Toast.LENGTH_SHORT).show();
                    Log.e("Can't get data1", "Can't get data1");
                }
            } catch (IOException e) {
//            Toast.makeText(mContext,"Can't get data2", Toast.LENGTH_SHORT).show();
                Log.e("Can't get data 2", "Can't get data 2");
                e.printStackTrace();
            }
        } else
        // if internet not connect
        {
            try {
                for (InformationsPlace inf : mSQLite.LayDuLieu()) {
                    if (mType.compareTo(inf.getType()) == 0) {
                        if (CalculationByDistance(Double.valueOf(inf.getLat())
                                , Double.valueOf(inf.getLng())
                                , myLatLng.latitude, myLatLng.longitude) <= 5) {
                            arrayInforPlace.add(inf);
                        }
                    }
                }
            } catch (Exception e) {
                Log.e("Loi lay co so du lieu", e.toString());
            }
        }
        return arrayInforPlace;
    }

    protected String downloadUrl(String strUrl) throws IOException {
        String data = "";
        Log.e("Link Page", strUrl);
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
            Log.e("String Sb", sb.toString());
            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            data = sb.toString();
            br.close();
        } catch (Exception e) {
            Log.e("Exception while downloading url", e.toString());
            //iStream.close();
            //urlConnection.disconnect();
        }
        return data;
    }

    @Override
    protected void onPostExecute(ArrayList<InformationsPlace> infomationsPlaces) {
        super.onPostExecute(infomationsPlaces);
        try {
            showDialog.ShowDialog(false);
            //in ViewPager
            GeneralAdapter generalAdapter = new GeneralAdapter(mContext, R.layout.layout_items_place_listview, arrayInforPlace);
            GeneralFragment.lvPlace.setAdapter(generalAdapter);
            new ShowTrueType(mMap, infomationsPlaces, mType);
        } catch (Exception e) {
            Toast.makeText(mContext, "Can't show.", Toast.LENGTH_SHORT).show();
        }
    }

    public double CalculationByDistance(double latStart, double lngStart, double latEnd, double lngEnd) {
        int Radius = 6371;// radius of earth in Km
        double lat1 = latStart;
        double lat2 = latEnd;
        double lon1 = lngStart;
        double lon2 = lngEnd;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
                * Math.sin(dLon / 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        double valueResult = Radius * c;
        double km = valueResult / 1;
        DecimalFormat newFormat = new DecimalFormat("####");
        int kmInDec = Integer.valueOf(newFormat.format(km));
        double meter = valueResult % 1000;
        int meterInDec = Integer.valueOf(newFormat.format(meter));
        Log.i("Radius Value", "" + valueResult + "   KM  " + kmInDec
                + " Meter   " + meterInDec);

        return Radius * c;
    }


}
