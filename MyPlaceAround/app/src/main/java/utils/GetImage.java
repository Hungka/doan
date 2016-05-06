package utils;

import android.os.AsyncTask;

import java.util.ArrayList;

/**
 * Created by TranManhHung on 05-Apr-16.
 */
public class GetImage extends AsyncTask<String, Void, Void> {
    ArrayList<String> listImg;

    @Override
    protected Void doInBackground(String... params) {

        return null;
    }

    public GetImage(ArrayList<String> list)
    {
        listImg = list;
    }

}
