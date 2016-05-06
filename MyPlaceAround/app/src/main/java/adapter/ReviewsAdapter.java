package adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.tranmanhhung.myplacearound.R;

import java.util.ArrayList;


import utils.Reviews;

/**
 * Created by TranManhHung on 05-Apr-16.
 */
public class ReviewsAdapter extends ArrayAdapter<Reviews> {

    Context mContext;
    int res;
    ArrayList<Reviews> list;

    public ReviewsAdapter(Context context, int resource, ArrayList<Reviews> objects) {
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
        Reviews reviews = list.get(position);
        TextView tvAuthor = (TextView) convertView.findViewById(R.id.tvAuthor);
        TextView tvCmt = (TextView) convertView.findViewById(R.id.tvCmt);

        tvAuthor.setText(reviews.getAuthor());
        tvCmt.setText(reviews.getCmt());

        return convertView;
    }
}
