package fragment;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tranmanhhung.myplacearound.DetailsActivity;
import com.example.tranmanhhung.myplacearound.DirectionActivity;
import com.example.tranmanhhung.myplacearound.MapsActivity;
import com.example.tranmanhhung.myplacearound.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import adapter.AutoCompleteAdapter;
import adapter.RecyclerViewAdapter;
import gson.GetDataGeneral;
import utils.GPSTracker;
import utils.InformationsPlace;


public class MapFragment extends Fragment implements GoogleMap.OnMapClickListener, GoogleMap.OnMarkerClickListener, View.OnClickListener {
    public static GoogleMap mMap;
    // public static AutoCompleteTextView autoCompleteTextView;
    //  public static AutoCompleteAdapter autoCompleteAdapter;

    public static RecyclerView recycler;
    public RecyclerViewAdapter recyclerAdapter;
    public RecyclerView.LayoutManager layoutManager;
    public static double myLatitude;
    public static double myLongitude;
    public static final String LINK = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?radius=5000&language-vn-vi&key=AIzaSyBrKcTILPHqW09IOvVmYqn5RnWUjJVXZGI&location=";
    public static LinearLayout linearLayout;
    TextView tvMapDetails;
    TextView tvMapDirections;
    ShowDialog showDialog;
    GPSTracker gps;
    LatLng latLng;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gps = new GPSTracker(getContext());
        // check if GPS enabled
        if (gps.canGetLocation()) {
            myLatitude = gps.getLatitude();
            myLongitude = gps.getLongitude();
            Toast.makeText(getContext()
                    , "Your Location is - \nLat: " + myLatitude + "\nLong: " + myLongitude
                    , Toast.LENGTH_LONG).show();
        }// else {
           // gps.showSettingsAlert();
       // }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_maps, container, false);

        // Google Maps
        mMap = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map)).getMap();
        //mMap.setMyLocationEnabled(true);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(MapsActivity.myLatitude, MapsActivity.myLongitude), 14));

        showDialog = new ShowDialog(getContext());
        recycler = (RecyclerView) view.findViewById(R.id.recycler);
        linearLayout = (LinearLayout) view.findViewById(R.id.layoutMapDetailsDirections);
        linearLayout.setVisibility(View.GONE);
        tvMapDetails = (TextView) view.findViewById(R.id.tvMapDetails);
        tvMapDirections = (TextView) view.findViewById(R.id.tvMapDirections);

        tvMapDirections.setOnClickListener(this);
        tvMapDetails.setOnClickListener(this);


        mMap.setOnMapClickListener(this);
        mMap.setOnMarkerClickListener(this);
        //recycler.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recycler.setLayoutManager(layoutManager);
        recyclerAdapter = new RecyclerViewAdapter(getContext(), MapsActivity.listType, mMap);
        recycler.setAdapter(recyclerAdapter);
        recycler.setScrollBarSize(0);
        return view;

    }


    @Override
    public void onMapClick(LatLng latLng) {
        TouchOnMap(RecyclerViewAdapter.pos, latLng);
//        mMap.clear();
//        MapsActivity.showDialog.ShowDialog(true);
//        String lat = String.valueOf(latLng.latitude);
//        String lng = String.valueOf(latLng.longitude);
//        Toast.makeText(getContext(), lat + ", " + lng, Toast.LENGTH_LONG).show();
//        //String linkGeneral = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?radius=5000&language-vn-vi&key=AIzaSyA61FgkFX4r0131E59W_zxANonP2BF_FV8&location=";
//
//        new GetDataGeneral(getContext(), latLng, mMap, showDialog).
//                execute("https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="
//                        + lat + "," + lng + "&radius=5000&language-vn-vi&key=AIzaSyBrKcTILPHqW09IOvVmYqn5RnWUjJVXZGI");
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13));
//        showDialog.ShowDialog(true);
//

    }

    public void TouchOnMap(int position, LatLng latLng) {
        String[] listType = new String[]
                {"airport"
                        , "atm"
                        , "bank"
                        , "bar"
                        , "bus_station"
                        , "church"
                        , "coffee"
                        , "clothing_store"
                        , "dentist"
                        , "food"
                        , "hospital"
                        , "laundry"
                        , "lodging"
                        , "park"
                        , "parking"
                        , "police"
                        , "school"
                        , "shopping_mall"
                        , "store"
                        , "spa"
                        , "taxi_stand"
                        , "travel_agency"

                };

        LatLng mLat = new LatLng(myLatitude, myLongitude);
        mMap.clear();
        //MapsActivity.showDialog.ShowDialog(true);
        new GetDataGeneral(getContext(),
                mLat, MapFragment.mMap, listType[position], showDialog)
                .execute(LINK + latLng.latitude + "," + latLng.longitude + "&type=" + listType[position]);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13));
        showDialog.ShowDialog(true);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        linearLayout.setVisibility(View.VISIBLE);
        latLng = marker.getPosition();
        //Toast.makeText(getContext(),lng.toString(),Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public void onClick(View v) {

        InformationsPlace info = null;
        for (int i = 0; i < GetDataGeneral.arrayInforPlace.size(); i++) {
            info = GetDataGeneral.arrayInforPlace.get(i);
            if (Double.valueOf(info.getLat()) == latLng.latitude) {
                if (Double.valueOf(info.getLng()) == latLng.longitude) {
                    break;
                }
            }
        }

        switch (v.getId()) {
            case R.id.tvMapDetails: {
                if (isOnline()) {
                    Bundle bundle = new Bundle();
                    bundle.putString("name", info.getName());
                    bundle.putString("lat", info.getLat());
                    bundle.putString("lng", info.getLng());
                    bundle.putString("address", info.getAddress());
                    bundle.putString("distance", info.getDistance());
                    //String id_ = info.getId();
                    bundle.putString("id", info.getId());
                    Intent intent = new Intent(getContext(), DetailsActivity.class);
                    intent.putExtra("Place", bundle);
                    startActivity(intent);
                } else {
                    Toast.makeText(getContext(), "Can't connect internet", Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case R.id.tvMapDirections: {
                Bundle bd = new Bundle();
                bd.putString("lat", info.getLat());
                bd.putString("lng", info.getLng());
                Intent in = new Intent(getContext(), DirectionActivity.class);
                in.putExtra("loc", bd);
                startActivity(in);
                break;
            }
        }
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getActivity().getSystemService(getActivity().CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    @Override
    public void onResume() {
        super.onResume();
        mMap = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map)).getMap();
        //mMap.setMyLocationEnabled(true);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(MapsActivity.myLatitude, MapsActivity.myLongitude), 14));
    }
}

//        //AutoComplete
//        autoCompleteTextView = (AutoCompleteTextView) view.findViewById(R.id.autoCompleteTextView);
//        autoCompleteAdapter = new AutoCompleteAdapter(getContext(), R.layout.layout_item_auto_complete);
//        autoCompleteTextView.setAdapter(autoCompleteAdapter);
//
//        //set onClick when touch item in list
//        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                String itemTouch = (String) parent.getItemAtPosition(position);
//                Toast.makeText(getContext(), itemTouch, Toast.LENGTH_SHORT).show();
//                //hide_keyboard(getActivity());
//
//            }
//        });