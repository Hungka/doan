package adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import fragment.GeneralFragment;
import fragment.MapFragment;


public class FragmentMainAdapter extends FragmentPagerAdapter {


    public FragmentMainAdapter(FragmentManager fm)
    {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                return new MapFragment();
            case 1:
                return new GeneralFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
