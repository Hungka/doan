package adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tranmanhhung.myplacearound.R;

import java.util.ArrayList;

import utils.Type;

/**
 * Created by TranManhHung on 26-Mar-16.
 */
public class ListTypeAdapter extends ArrayAdapter {

    Context mContext;
    int res;
    ArrayList<Type> listTpye;


    public ListTypeAdapter(Context context, int resource,ArrayList<Type> objects) {
        super(context, resource, objects);
        mContext = context;
        res = resource;
        listTpye = objects;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null)
        {
            LayoutInflater mInflater = (LayoutInflater) mContext.getApplicationContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(res, parent, false);
        }

        Type inf = listTpye.get(position);
        TextView tvName = (TextView)convertView.findViewById(R.id.tvType);
        ImageView imageView =(ImageView)convertView.findViewById(R.id.ivType);

        tvName.setText( inf.getName());
        imageView.setImageDrawable(inf.getImage());


        return convertView;
    }
}
