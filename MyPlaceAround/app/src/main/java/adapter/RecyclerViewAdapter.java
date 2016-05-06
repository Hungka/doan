package adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tranmanhhung.myplacearound.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.zip.Inflater;

import fragment.GeneralFragment;
import fragment.MapFragment;
import fragment.ShowDialog;
import gson.GetDataGeneral;
import utils.GPSTracker;

import static com.example.tranmanhhung.myplacearound.R.color.black;

/**
 * Created by TranManhHung on 15-Apr-16.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter {
    Context context;
    GPSTracker gps;
    GoogleMap mMap;
    public String[] list;
    public static double myLatitude;
    public static double myLongitude;
    public static final String LINK = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?radius=5000&language-vn-vi&key=AIzaSyBrKcTILPHqW09IOvVmYqn5RnWUjJVXZGI&location=";
    public ShowDialog showDialog;
    public static int pos = 0;

    public RecyclerViewAdapter(Context context, String[] list, GoogleMap map) {
        this.list = list;
        this.context = context;
        mMap = map;

        gps = new GPSTracker(context);
        // check if GPS enabled
        if (gps.canGetLocation()) {
            myLatitude = gps.getLatitude();
            myLongitude = gps.getLongitude();
            Toast.makeText(context
                    , "Your Location is - \nLat: " + myLatitude + "\nLong: " + myLongitude
                    , Toast.LENGTH_LONG).show();
        } else {
            gps.showSettingsAlert();
        }
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.layout_items_recyclerview, parent, false);
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder item = (ItemViewHolder) holder;
        item.tv.setText(list[position]);
    }


    @Override
    public int getItemCount() {
        return list.length;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, AdapterView.OnItemClickListener {
        public TextView tv;
        public ItemViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.tvRecycler);
            tv.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            pos = getAdapterPosition();
            mMap.clear();
            showDialog = new ShowDialog(context);
            final LatLng LATLNG = new LatLng(myLatitude, myLongitude);
            new GetDataGeneral(context
                    , LATLNG
                    , MapFragment.mMap
                    , tv.getText().toString()
                    , showDialog)
                    .execute(LINK + myLatitude + "," + myLongitude + "&type=" + tv.getText().toString());
            GeneralFragment.tvDrawerSelect.setText(tv.getText().toString().toUpperCase());
            showDialog.ShowDialog(true);
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            TextView textView = (TextView) parent.getChildAt(position);
            textView.setTextColor(Color.parseColor("#ff0000"));
        }
    }

}
