package fragment;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.tranmanhhung.myplacearound.R;

public class ReviewsFragment extends Fragment {

    public static ListView lvReviews;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reviews, container, false);
        lvReviews =(ListView)view.findViewById(R.id.lvdtReviews);
        return view;
    }
}
