package adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.tranmanhhung.myplacearound.R;

import java.util.List;

import utils.Line;

/**
 * Created by TranManhHung on 20-Apr-16.
 */
public class TextDirectionAdapter extends ArrayAdapter<Line> {
    Context context;
    int res;
    List<Line> lineList;

    public TextDirectionAdapter(Context context, int resource, List<Line> objects) {
        super(context, resource, objects);
        this.context = context;
        res = resource;
        lineList = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context
                    .getApplicationContext()
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(res, parent, false);
            Line line = lineList.get(position);

            TextView tvLineText = (TextView) convertView.findViewById(R.id.tvLineText);
            TextView tvLineDis = (TextView) convertView.findViewById(R.id.tvLineDistance);
            TextView tvLineTime = (TextView) convertView.findViewById(R.id.tvLineTime);
            TextView tvLineMode = (TextView) convertView.findViewById(R.id.tvLineMode);

            tvLineText.setText(line.getHtml_instructions());
            tvLineDis.setText(line.getDistance());
            tvLineTime.setText(line.getDuration());
            tvLineMode.setText(line.getTravel_mode());
        }
        return convertView;
    }
}
