package fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.tranmanhhung.myplacearound.MapsActivity;
import com.example.tranmanhhung.myplacearound.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import java.util.zip.Inflater;

import type.support.GetDirections;

/**
 * Created by TranManhHung on 18-Apr-16.
 */
@SuppressLint("ValidFragment")
public class DirectionViewPager extends Fragment {
    public ListView lvDirectionText;
    public GoogleMap map;
    String link;

    public DirectionViewPager(GoogleMap map, LatLng or, LatLng des, String mode) {
        this.map = map;
        link = getDirectionsUrl(or, des, mode);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_direction_text, container, false);
            lvDirectionText = (ListView) view.findViewById(R.id.lvDriection);
           // map = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mMap)).getMap();
            //mMap.setMyLocationEnabled(true);
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(MapsActivity.myLatitude, MapsActivity.myLongitude), 14));
            GetDirections getDirections = new GetDirections(getContext(),map, lvDirectionText);
            getDirections.execute(link);
            return view;
    }

    public String getDirectionsUrl(LatLng origin, LatLng dest, String mode) {

        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        // Sensor enabled
        String sensor = "sensor=false";
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + sensor + "&mode=" + mode+"&language-vn-vi";
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
