package adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import fragment.ImageViewFragment;
import fragment.ReviewsFragment;

/**
 * Created by TranManhHung on 07-Apr-16.
 */
public class DetailsFragmentAdapter extends FragmentPagerAdapter {
    int count;
    public DetailsFragmentAdapter(FragmentManager fm, int count) {
        super(fm); this.count = count;
    }



    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new ReviewsFragment();
            case 1:
                return new ImageViewFragment();
        }

        return null;
    }

    @Override
    public int getCount() {
        return count;
    }
}
