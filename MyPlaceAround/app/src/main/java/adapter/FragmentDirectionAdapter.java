package adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.tranmanhhung.myplacearound.DirectionActivity;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import fragment.DirectionViewPager;

/**
 * Created by TranManhHung on 18-Apr-16.
 */
public class FragmentDirectionAdapter extends FragmentPagerAdapter {
    LatLng start;
    LatLng des;
    GoogleMap googleMap;
    public FragmentDirectionAdapter(FragmentManager fm,GoogleMap map, LatLng start, LatLng des) {
        super(fm);
        googleMap = map;
        this.start = start;
        this.des = des;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: {
                return new DirectionViewPager(googleMap,start, des, "driving");
            }
            case 1: {
                return new DirectionViewPager(googleMap,start, des, "transit");
            }
            case 2: {
                return new DirectionViewPager(googleMap,start, des, "walking");
            }
            case 3: {
                return new DirectionViewPager(googleMap,start, des, "bicycling");
            }
        }
        return new DirectionViewPager(googleMap,start, des, "driving");
    }

    @Override
    public int getCount() {
        return 4;
    }
}
