package dialog;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tranmanhhung.myplacearound.DirectionActivity;
import com.example.tranmanhhung.myplacearound.R;

import gson.GetDataDetails;

/**
 * Created by TranManhHung on 27-Mar-16.
 */
public class DialogDetails extends DialogFragment {

public DialogDetails(){}


    public TextView tvName;
    public TextView tvAddtress;
    public static TextView tvDistance;
    public static TextView tvPhoneNumbers;
    public static TextView tvWebsite;
    public static ViewPager viewPager;
    public static int count = 2;
    Button btnDirections;
    String lat;
    String lng;
    String id_;
    String name;
    String address;
    String distance;

    @SuppressLint("ValidFragment")
    public DialogDetails(String name, String address, String distance, String lat, String lng, String id)
    {
        this.name = name;
        this.lat = lat;
        this.lng= lng;
        this.address = address;
        this.id_ = id;
        this.distance = distance;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.acitivity_dialog, container, false);

        //TextView textView =(TextView)view.findViewById(R.id.)
        tvName = (TextView) view.findViewById(R.id.tvdtTitle);
        tvAddtress = (TextView) view.findViewById(R.id.tvdtAddress);
        tvDistance = (TextView) view.findViewById(R.id.tvdtDistance);
        tvPhoneNumbers = (TextView) view.findViewById(R.id.tvdtPhoneNumbers);
        tvWebsite = (TextView) view.findViewById(R.id.tvdtWebsite);
        viewPager = (ViewPager) view.findViewById(R.id.vpdt);
        btnDirections = (Button) view.findViewById(R.id.btnViewMap);


        getDialog().setTitle("Detail");
//        final Intent intent = getActivity().getIntent();
//        Bundle bundle = intent.getBundleExtra("Place");
        try {
//
            tvName.setText(name);
            tvAddtress.setText(address);
            tvDistance.setText(distance);
//            lat = bundle.getString("lat");
//            lng = bundle.getString("lng");
//            id_ = bundle.getString("id");
            id_ = id_.replace("\"", "");
            String linkDetails = "https://maps.googleapis.com/maps/api/place/details/json?key=AIzaSyBrKcTILPHqW09IOvVmYqn5RnWUjJVXZGI" + "&placeid=" + id_;
            new GetDataDetails(getContext(), tvPhoneNumbers, tvWebsite).execute(linkDetails);

        } catch (Exception e) {
            Toast.makeText(getContext(), "Khong co du lieu", Toast.LENGTH_SHORT).show();
        }
        try {

            tvWebsite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String link = tvWebsite.getText().toString();
                    Uri uri = Uri.parse(link);
                    Intent i = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(i);
                }
            });
        } catch (Exception e) {
            Log.e("Null Website", e.toString());
        }

        btnDirections.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bd = new Bundle();
                bd.putString("lat", lat);
                bd.putString("lng", lng);
                Intent in = new Intent(getContext(), DirectionActivity.class);
                in.putExtra("loc", bd);
                startActivity(in);
            }
        });

        return view;
    }
}
