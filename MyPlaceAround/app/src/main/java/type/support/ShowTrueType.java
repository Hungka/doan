package type.support;

import android.util.Log;
import android.widget.Toast;

import com.example.tranmanhhung.myplacearound.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import utils.InformationsPlace;

/**
 * Created by TranManhHung on 31-Mar-16.
 */
public class ShowTrueType {

    public ShowTrueType(GoogleMap mMap, ArrayList<InformationsPlace> infomationsPlaces, String type) {
        int flag = R.drawable.ic_flag_3;
        for (InformationsPlace inf : infomationsPlaces) {
            switch (type) {
                case "airport": {
                    flag = R.drawable.ic_flag_airport;
                    break;
                }
                case "atm": {
                    flag = R.drawable.ic_flag_atm;
                    break;
                }
                case "bank": {
                    flag = R.drawable.ic_flag_bank;
                    break;
                }
                case "bar": {
                    flag = R.drawable.ic_flag_bar;
                    break;
                }
                case "bus_station": {
                    flag = R.drawable.ic_flag_bus;
                    break;
                }
                case "church": {
                    flag = R.drawable.ic_flag_church;
                    break;
                }
                case "coffee": {
                    flag = R.drawable.ic_flag_coffee;
                    break;
                }
                case "dentist": {
                    flag = R.drawable.ic_flag_dentis;
                    break;
                }
                case "hospital": {
                    flag = R.drawable.ic_flag_hospital;
                    break;
                }
                case "police": {
                    flag = R.drawable.ic_flag_police;
                    break;
                }

                case "school": {
                    flag = R.drawable.ic_flag_school;
                    break;
                }
                case "store": {
                    flag = R.drawable.ic_flag_store;
                    break;
                }
                case "park": {
                    flag = R.drawable.ic_flag_park1;
                    break;
                }
                case "clothing_store": {
                    flag = R.drawable.ic_flag_clothing_store;
                    break;
                }
                case "lodging": {
                    flag = R.drawable.ic_flag_logding1;
                    break;
                }
                case "parking": {
                    flag = R.drawable.ic_flag_parking1;
                    break;
                }
                case "spa": {
                    flag = R.drawable.ic_flag_spa1;
                    break;
                }
                case "shopping_mall": {
                    flag = R.drawable.ic_flag_shopping_mall1;
                    break;
                }
                case "travel_agency": {
                    flag = R.drawable.ic_flag_travel_agency1;
                    break;
                }
                case "taxi_stand": {
                    flag = R.drawable.ic_flag_taxi_stand1;
                    break;
                }
                case "food": {
                    flag = R.drawable.ic_flag_food;
                    break;
                }
                case "laundry": {
                    flag = R.drawable.ic_flag_laundry;
                    break;
                }
            }
            mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(flag))
                    .position(new LatLng(Double.valueOf(inf.getLat()),
                            Double.valueOf(inf.getLng()))).title(inf.getName()));

        }
    }
}
