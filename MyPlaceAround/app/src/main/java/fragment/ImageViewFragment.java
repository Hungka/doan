package fragment;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.tranmanhhung.myplacearound.R;

public class ImageViewFragment extends Fragment {

    public static ListView lvImage;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image_view, container, false);
        lvImage = (ListView)view.findViewById(R.id.lvdtImage);
        return view;
    }
}
