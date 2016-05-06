package fragment;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tranmanhhung.myplacearound.DetailsActivity;
import com.example.tranmanhhung.myplacearound.R;

import gson.GetDataGeneral;
import utils.InformationsPlace;


public class GeneralFragment extends Fragment implements AdapterView.OnItemClickListener {

    public static ListView lvPlace;
    public static TextView tvDrawerSelect;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_general, container, false);
        lvPlace = (ListView) view.findViewById(R.id.lvPlace);
        tvDrawerSelect = (TextView) view.findViewById(R.id.tvTypeChoose);
        lvPlace.setOnItemClickListener(this);
        return view;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        // kiem tra co internet
        if (isOnline()) {

            Bundle bundle = new Bundle();
            InformationsPlace info = GetDataGeneral.arrayInforPlace.get(position);
            bundle.putString("name", info.getName());
            bundle.putString("lat", info.getLat());
            bundle.putString("lng", info.getLng());
            bundle.putString("address", info.getAddress());
            bundle.putString("distance", info.getDistance());
            //String id_ = info.getId();
            bundle.putString("id", info.getId());
            Intent intent = new Intent(getContext(), DetailsActivity.class);
            intent.putExtra("Place", bundle);
            startActivity(intent);
        }
        else {
            Toast.makeText(getContext(),"Can't connect internet", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        getActivity().overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getActivity().getSystemService(getActivity().CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}

