package adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tranmanhhung.myplacearound.R;

import java.util.ArrayList;

import utils.InformationsPlace;

public class GeneralAdapter extends ArrayAdapter<InformationsPlace> {

    Context mContext;
    int res;
    ArrayList<InformationsPlace> arrayListInfo = new ArrayList<>();

    public GeneralAdapter(Context context, int resource, ArrayList<InformationsPlace> objects) {
        super(context, resource, objects);
        mContext = context;
        res = resource;
        arrayListInfo = (ArrayList<InformationsPlace>) objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) mContext.getApplicationContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(res, parent, false);
        }
        ImageView img = (ImageView) convertView.findViewById(R.id.imgType);
        TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
        TextView tvAddress = (TextView) convertView.findViewById(R.id.tvAddress);
        TextView tvDistance = (TextView) convertView.findViewById(R.id.tvDistance);

        InformationsPlace mInfo = arrayListInfo.get(position);

        tvAddress.setText(mInfo.getAddress());
        tvName.setText(mInfo.getName());
        tvDistance.setText(mInfo.getDistance());
        int flag = 0;
        switch (mInfo.getType()) {
            case "airport": {
                flag = R.drawable.airport;
                break;
            }
            case "atm": {
                flag = R.drawable.atm;
                break;
            }
            case "bank": {
                flag = R.drawable.bank;
                break;
            }
            case "bar": {
                flag = R.drawable.bar;
                break;
            }
            case "bus_station": {
                flag = R.drawable.bus;
                break;
            }
            case "church": {
                flag = R.drawable.church;
                break;
            }
            case "coffee": {
                flag = R.drawable.coffee;
                break;
            }
            case "dentist": {
                flag = R.drawable.dentis;
                break;
            }
            case "hospital": {
                flag = R.drawable.hospital;
                break;
            }
            case "police": {
                flag = R.drawable.police;
                break;
            }

            case "school": {
                flag = R.drawable.school;
                break;
            }
            case "store": {
                flag = R.drawable.store;
                break;
            }
            case "park": {
                flag = R.drawable.park;
                break;
            }
            case "clothing_store": {
                flag = R.drawable.clothing_store;
                break;
            }
            case "lodging": {
                flag = R.drawable.logding;
                break;
            }
            case "parking": {
                flag = R.drawable.parking;
                break;
            }
            case "spa": {
                flag = R.drawable.spa;
                break;
            }
            case "shopping_mall": {
                flag = R.drawable.shopping_mall;
                break;
            }
            case "travel_agency": {
                flag = R.drawable.travel_agency;
                break;
            }
            case "taxi_stand": {
                flag = R.drawable.taxi_stand;
                break;
            }
            case "food": {
                flag = R.drawable.food;
                break;
            }
            case "laundry": {
                flag = R.drawable.laundry;
                break;
            }
        }
        img.setImageResource(flag);

        return convertView;
    }
}
