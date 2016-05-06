package adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.tranmanhhung.myplacearound.R;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by TranManhHung on 05-Apr-16.
 */
public class ImageDetailsAdapter extends ArrayAdapter<String> {
    Context mContext;
    int res;
    ArrayList<String> list;

    public ImageDetailsAdapter(Context context, int resource, ArrayList<String> objects) {
        super(context, resource, objects);
        mContext = context;
        res = resource;
        list = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) mContext.getApplicationContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(res, parent, false);
        }
        try {
            String link = list.get(position);
            ImageView imageView = (ImageView) convertView.findViewById(R.id.imgdt);
            Glide.with(mContext)
                    .load(link)
                    .override(600, 400)
                    .centerCrop()
                    .into(imageView);

        } catch (Exception e) {
            Log.e("Null Image", e.toString());
        }

        return convertView;
    }
}
