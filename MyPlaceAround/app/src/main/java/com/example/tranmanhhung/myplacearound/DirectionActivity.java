package com.example.tranmanhhung.myplacearound;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import adapter.FragmentDirectionAdapter;
import fragment.MapFragment;
import interface_.GetLinkDirection;
import interface_.SaveRoute;
import type.support.GetDirections;
import type.support.SaveRouter;
import utils.GPSTracker;

public class DirectionActivity extends FragmentActivity implements View.OnClickListener, GetLinkDirection{

    double myLatitude;
    double myLongitude;
    GoogleMap map;
    String lat;
    String lng;
    LatLng dest;
    LatLng origin;
    public ViewPager viewPager;
    public static String url;
    public ListView lvDirections;
    TextView tvCar;
    TextView tvBus;
    TextView tvWalking;
    Button btnsave;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direction);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        lvDirections = (ListView) findViewById(R.id.lvDirec);
        tvCar = (TextView) findViewById(R.id.tvCar);
        tvBus = (TextView) findViewById(R.id.tvBus);
        tvWalking = (TextView) findViewById(R.id.tvWalk);
        btnsave = (Button)findViewById(R.id.btnSaveroute);
        tvCar.setOnClickListener(this);
        tvBus.setOnClickListener(this);
        tvWalking.setOnClickListener(this);
        btnsave.setOnClickListener(this);

        GetIntent();
        getGPS();
        setUpMapIfNeeded();

        //FragmentDirectionAdapter fr = new FragmentDirectionAdapter(getSupportFragmentManager(), map, origin, dest);
        // viewPager.setAdapter(fr);
    }

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (map == null) {
            // Try to obtain the map from the SupportMapFragment.
            map = ((SupportMapFragment)
                    getSupportFragmentManager()
                            .findFragmentById(R.id.mMap))
                    .getMap();
            if (map != null) {
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(myLatitude, myLongitude), 14));
                ShowMap();
                url = getDirectionsUrl(origin, dest, "driving");
                GetDirections getDirections = new GetDirections(getApplicationContext(), map, lvDirections);
                getDirections.execute(url);
            }
        }
    }

    public void ShowMap() {
        LatLng dest = new LatLng(Double.valueOf(lat), Double.valueOf(lng));
        LatLng origin = new LatLng(myLatitude, myLongitude);
        map.addMarker(new MarkerOptions()
                .position(origin)
                .icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)).title("Start"));
        map.addMarker(new MarkerOptions()
                .position(dest)
                .icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_GREEN)).title("Destination"));
        // check if GPS enabled
        //driving


    }

    public void getGPS() {
        GPSTracker gps = new GPSTracker(this);
        // check if GPS enabled
        if (gps.canGetLocation()) {
            myLatitude = gps.getLatitude();
            myLongitude = gps.getLongitude();

            dest = new LatLng(Double.valueOf(lat), Double.valueOf(lng));
            origin = new LatLng(myLatitude, myLongitude);
//            Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + myLatitude + "\nLong: " + myLongitude, Toast.LENGTH_LONG).show();
//        } else {
//            gps.showSettingsAlert();
//        }
        }
    }

    public void GetIntent() {
        Intent getIntent = getIntent();
        Bundle getBunder = getIntent.getBundleExtra("loc");
        lat = getBunder.getString("lat");
        lng = getBunder.getString("lng");
    }


    @Override
    protected void onResume() {
        super.onResume();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvCar: {
                map.clear();
                url = getDirectionsUrl(origin, dest, "driving");
                GetDirections getDirections = new GetDirections(getApplicationContext(), map, lvDirections);
                getDirections.execute(url);
                tvCar.setTextColor(getResources().getColor(R.color.red));
                tvWalking.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                tvBus.setTextColor(getResources().getColor(R.color.colorPrimaryDark));

                ShowMap();
                break;
            }
            case R.id.tvBus: {
                map.clear();
                url = getDirectionsUrl(origin, dest, "transit");
                GetDirections getDirections = new GetDirections(getApplicationContext(), map, lvDirections);
                getDirections.execute(url);
                tvBus.setTextColor(getResources().getColor(R.color.red));
                tvWalking.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                tvCar.setTextColor(getResources().getColor(R.color.colorPrimaryDark));

                ShowMap();
                break;
            }
            case R.id.tvWalk: {
                map.clear();
              //  url = getDirectionsUrl(origin, dest, "walking");
                url = GetDirctionLink(origin, dest,"walking");
                GetDirections getDirections = new GetDirections(getApplicationContext(), map, lvDirections);
                getDirections.execute(url);
                tvBus.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                tvWalking.setTextColor(getResources().getColor(R.color.red));
                tvCar.setTextColor(getResources().getColor(R.color.colorPrimaryDark));

                ShowMap();
                break;
            }

            case R.id.btnSaveroute:
            {
                new SaveRouter(origin,dest).execute("");
                break;
            }
            default: {
                url = getDirectionsUrl(origin, dest, "driving");
                GetDirections getDirections = new GetDirections(getApplicationContext(), map, lvDirections);
                getDirections.execute(url);
                break;
            }

        }
    }

    public String getDirectionsUrl(LatLng origin, LatLng dest, String mode) {

        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        // Sensor enabled
        String sensor = "sensor=false";
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + sensor + "&mode=" + mode + "&language=vi";
        // Output format
        String output = "json";
        String API = "&API=AIzaSyAzc1VLT3LxPdPwIH4RNhAb6L93Im_bsao";
        //Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;
        //String url = "https://maps.googleapis.com/maps/api/directions/json?origin=10.878602,106.807123&destination=10.773290,106.691670";
        //String url ="http://maps.googleapis.com/maps/api/directions/json?origin=10.878528,106.807177&destination=10.818449,106.658551&alternatives=true&language=vi-VN&sensor=false&mode=transit";
        return url;
    }


    @Override
    public String GetDirctionLink(LatLng origin, LatLng dest,String mode) {
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        // Sensor enabled
        String sensor = "sensor=false";
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + sensor + "&mode=" + mode + "&language=vi";
        // Output format
        String output = "json";
        String API = "&API=AIzaSyAzc1VLT3LxPdPwIH4RNhAb6L93Im_bsao";
        //Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;
        //String url = "https://maps.googleapis.com/maps/api/directions/json?origin=10.878602,106.807123&destination=10.773290,106.691670";
        //String url ="http://maps.googleapis.com/maps/api/directions/json?origin=10.878528,106.807177&destination=10.818449,106.658551&alternatives=true&language=vi-VN&sensor=false&mode=transit";
        return url;
    }
}
