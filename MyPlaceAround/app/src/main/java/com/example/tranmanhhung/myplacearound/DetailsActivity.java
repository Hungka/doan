package com.example.tranmanhhung.myplacearound;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.liuguangqiang.progressbar.CircleProgressBar;
import com.liuguangqiang.swipeback.SwipeBackActivity;
import com.liuguangqiang.swipeback.SwipeBackLayout;

import adapter.DetailsFragmentAdapter;
import gson.GetDataDetails;

public class DetailsActivity extends SwipeBackActivity {
    private SwipeBackLayout swipeBackLayout;
    private CircleProgressBar progressBar;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        setDragEdge(SwipeBackLayout.DragEdge.TOP);

       // initViews();
        tvName = (TextView) findViewById(R.id.tvdtTitle);
        tvAddtress = (TextView) findViewById(R.id.tvdtAddress);
        tvDistance = (TextView) findViewById(R.id.tvdtDistance);
        tvPhoneNumbers = (TextView) findViewById(R.id.tvdtPhoneNumbers);
        tvWebsite = (TextView) findViewById(R.id.tvdtWebsite);
        viewPager = (ViewPager) findViewById(R.id.vpdt);
        btnDirections = (Button) findViewById(R.id.btnDirections);

        try {
            DetailsFragmentAdapter fragmentAdapter = new DetailsFragmentAdapter(getSupportFragmentManager(), count);
            viewPager.setAdapter(fragmentAdapter);
        } catch (Exception e) {
            Log.e("Null View Pager", e.toString());
        }


        final Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("Place");
        try {

            tvName.setText(bundle.getString("name"));
            tvAddtress.setText(bundle.getString("address"));
            tvDistance.setText(bundle.getString("distance"));
            lat = bundle.getString("lat");
            lng = bundle.getString("lng");
            id_ = bundle.getString("id");
            id_ = id_.replace("\"", "");
            String linkDetails = "https://maps.googleapis.com/maps/api/place/details/json?key=AIzaSyBrKcTILPHqW09IOvVmYqn5RnWUjJVXZGI" + "&placeid=" + id_;
            new GetDataDetails(
                    getApplicationContext()
                    , tvPhoneNumbers, tvWebsite)
                    .execute(linkDetails);

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Khong co du lieu", Toast.LENGTH_SHORT).show();

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

        tvPhoneNumbers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = (String) tvPhoneNumbers.getText();
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse(url));
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                startActivity(intent);
            }
        });

        btnDirections.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bd = new Bundle();
                bd.putString("lat", lat);
                bd.putString("lng", lng);
                Intent in = new Intent(getApplicationContext(), DirectionActivity.class);
                in.putExtra("loc", bd);
                startActivity(in);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }
//    private void initViews() {
//        progressBar = (CircleProgressBar) findViewById(R.id.progressbar1);
//        swipeBackLayout = (SwipeBackLayout) findViewById(R.id.swipe_layout);
//        swipeBackLayout.setEnableFlingBack(false);
//
//        swipeBackLayout.setOnPullToBackListener(new SwipeBackLayout.SwipeBackListener() {
//            @Override
//            public void onViewPositionChanged(float fractionAnchor, float fractionScreen) {
//                progressBar.setProgress((int) (progressBar.getMax() * fractionAnchor));
//            }
//        });
//    }
}
