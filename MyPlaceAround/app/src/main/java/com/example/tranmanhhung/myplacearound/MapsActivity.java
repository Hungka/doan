package com.example.tranmanhhung.myplacearound;

import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

import adapter.FragmentMainAdapter;
import adapter.ListTypeAdapter;
import dialog.DialogAutoText;
import fragment.GeneralFragment;
import fragment.ShowDialog;
import utils.GPSTracker;
import gson.GetDataGeneral;
import utils.Type;
import fragment.MapFragment;


public class MapsActivity extends FragmentActivity implements AdapterView.OnItemClickListener, View.OnClickListener {
    // String key = "AIzaSyDBt17fZKOqx92uxYfOYKFBkdKL540G5a8";
    //AIzaSyA61FgkFX4r0131E59W_zxANonP2BF_FV8
    //AIzaSyB0y6l7cTNd8l9hM5-68wJXBmKx5FCAtG8
    // public static String  linkGeneral = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?radius=5000&language-vn-vi&key=AIzaSyA61FgkFX4r0131E59W_zxANonP2BF_FV8&location=";
    ArrayList<Type> arrayListType;
     GPSTracker gps;
    FragmentMainAdapter fragmentAdapter;
    FragmentManager fm = getSupportFragmentManager();
    public static final String LINK = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?radius=5000&language-vn-vi&key=AIzaSyBrKcTILPHqW09IOvVmYqn5RnWUjJVXZGI&location=";
    public static String type = "";
    public static String name = "";
    public static DrawerLayout mDrawerLayout;
    public static ViewPager viewPager;
    public static ListView lvDrawerType;
    public static double myLatitude;
    public static double myLongitude;
    public static ShowDialog showDialog;
    public ImageView imgMenu;
    public ImageView imgSearch;
    public ImageView imgLocation;
    public ImageView imgDetails;
    public ImageView imgAdd;
    public ImageView imgSub;
    public static String[] listType;
    FrameLayout fmLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mains);

        showDialog = new ShowDialog(this);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        lvDrawerType = (ListView) findViewById(R.id.lvListType);
        imgLocation = (ImageView) findViewById(R.id.imgLocation);
        imgMenu = (ImageView) findViewById(R.id.imgMenu);
        imgLocation = (ImageView) findViewById(R.id.imgLocation);
        imgSearch = (ImageView) findViewById(R.id.imgSearch);
        imgDetails = (ImageView) findViewById(R.id.imgDetails);
        imgAdd = (ImageView) findViewById(R.id.imgadd);
        imgSub = (ImageView) findViewById(R.id.imgsub);
        fmLayout = (FrameLayout) findViewById(R.id.fmDetails);
        // add item in List Drar
        fragmentAdapter = new FragmentMainAdapter(fm);
        viewPager.setAdapter(fragmentAdapter);
        ListType();
        ListTypeAdapter listTypeAdapter = new ListTypeAdapter(this, R.layout.layout_items_drawer_type, arrayListType);
        lvDrawerType.setAdapter(listTypeAdapter);
        lvDrawerType.setOnItemClickListener(this);
        imgLocation.setOnClickListener(this);
        imgMenu.setOnClickListener(this);
        imgSearch.setOnClickListener(this);
        imgSub.setOnClickListener(this);
        imgAdd.setOnClickListener(this);
        fmLayout.setOnClickListener(this);
        // Get My Locations
        gps = new GPSTracker(this);
        // check if GPS enabled
        if (gps.canGetLocation()) {
            myLatitude = gps.getLatitude();
            myLongitude = gps.getLongitude();
            Toast.makeText(getApplicationContext()
                    , "Your Location is - \nLat: " + myLatitude + "\nLong: " + myLongitude
                    , Toast.LENGTH_LONG).show();
        }
//        } else {
//            gps.showSettingsAlert();
//        }

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (viewPager.getCurrentItem() == 0) {
                    imgDetails.setVisibility(View.VISIBLE);
                } else {
                    imgDetails.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        MapFragment.mMap.clear();
        mDrawerLayout.closeDrawers();
        type = arrayListType.get(position).getName();
        final LatLng LATLNG = new LatLng(myLatitude, myLongitude);
        new GetDataGeneral(getApplicationContext(),
                LATLNG, MapFragment.mMap, type, showDialog)
                .execute(LINK + myLatitude + "," + myLongitude + "&type=" + type);
        GeneralFragment.tvDrawerSelect.setText(type.toUpperCase());
        showDialog.ShowDialog(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgMenu: {
                mDrawerLayout.openDrawer(Gravity.START);
                break;
            }
            case R.id.imgLocation: {
                MapFragment.mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(MapsActivity.myLatitude, MapsActivity.myLongitude), 14));
                break;
            }
            case R.id.fmDetails: {
                if (GetDataGeneral.arrayInforPlace == null)
                    Toast.makeText(getApplicationContext(), "No Data", Toast.LENGTH_SHORT).show();
                else {
                    if (viewPager.getCurrentItem() == 0) {
                        viewPager.setCurrentItem(1);
                    } else {
                        viewPager.setCurrentItem(0);
                    }
                }
                break;
            }
            case R.id.imgSearch: {
                DialogAutoText dialogAutoText = new DialogAutoText();
                dialogAutoText.show(fm, "Search Place");
                break;
            }

            case R.id.imgadd: {
                MapFragment.mMap.moveCamera(CameraUpdateFactory.zoomIn());
                break;
            }
            case R.id.imgsub: {
                MapFragment.mMap.moveCamera(CameraUpdateFactory.zoomOut());
                break;
            }
        }
    }

    public void ListType() {
        arrayListType = new ArrayList<>();
        Drawable[] drawables = new Drawable[]
                {
                        this.getResources().getDrawable(R.drawable.airport),
                        this.getResources().getDrawable(R.drawable.atm),
                        this.getResources().getDrawable(R.drawable.bank),
                        this.getResources().getDrawable(R.drawable.bar),
                        this.getResources().getDrawable(R.drawable.bus),
                        this.getResources().getDrawable(R.drawable.church),
                        this.getResources().getDrawable(R.drawable.coffee),
                        this.getResources().getDrawable(R.drawable.clothing_store),
                        this.getResources().getDrawable(R.drawable.dentis),
                        this.getResources().getDrawable(R.drawable.food),
                        this.getResources().getDrawable(R.drawable.hospital),
                        this.getResources().getDrawable(R.drawable.laundry),
                        this.getResources().getDrawable(R.drawable.logding),
                        this.getResources().getDrawable(R.drawable.park),
                        this.getResources().getDrawable(R.drawable.parking),
                        this.getResources().getDrawable(R.drawable.police),
                        this.getResources().getDrawable(R.drawable.school),
                        this.getResources().getDrawable(R.drawable.shopping_mall),
                        this.getResources().getDrawable(R.drawable.store),
                        this.getResources().getDrawable(R.drawable.spa),
                        this.getResources().getDrawable(R.drawable.taxi_stand),
                        this.getResources().getDrawable(R.drawable.travel_agency),


                };
        listType = new String[]
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


        for (int i = 0; i < listType.length; i++) {
            Type type = new Type();
            type.setName(listType[i]);
            type.setImage(drawables[i]);
            arrayListType.add(type);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        gps = new GPSTracker(this);
        // check if GPS enabled
        if (gps.canGetLocation()) {
            myLatitude = gps.getLatitude();
            myLongitude = gps.getLongitude();
            Toast.makeText(getApplicationContext()
                    , "Your Location is - \nLat: " + myLatitude + "\nLong: " + myLongitude
                    , Toast.LENGTH_LONG).show();
        }
    }
}
