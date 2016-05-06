package sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import utils.InformationsPlace;

/**
 * Created by TranManhHung on 09-Apr-16.
 */
public class SQLite extends SQLiteOpenHelper {

    Context mContext;
    final String TABLE = "danhsach";
    final String NAME = "name";
    final String DISTANCE = "distance";
    final String ADDRESS = "address";
    final String LAT = "lat";
    final String LNG = "lng";
    final String TYPE = "type";

    public SQLite(Context context) {
        super(context, "danhsach", null, 1);
        mContext = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE danhsach (  name TEXT Primary key, address TEXT , distance TEXT , lat TEXT , lng TEXT , type TEXT )";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists danhsach");
    }

    public void ThemDuLieu(String name, String add, String dis, String lat, String lng, String type) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME, name);
        values.put(ADDRESS, add);
        values.put(DISTANCE, dis);
        values.put(LAT, lat);
        values.put(LNG, lng);
        values.put(TYPE, type);
        if (db.insert(TABLE, null, values) != -1) {
            //Toast.makeText(mContext, " Thêm thành công!", Toast.LENGTH_SHORT).show();
            Log.e("Them thanh cong", "Them du lieu thanh cong");
        } else {
            //   Toast.makeText(mContext, "Thêm thất bại!", Toast.LENGTH_SHORT).show();
            Log.e("Them that bai ", "Them du lieu that bai");
        }
        db.close();
    }


    public void ThemDuLieu(String name, String add, String dis, String lat, String lng) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME, name);
        values.put(ADDRESS, add);
        values.put(DISTANCE, dis);
        values.put(LAT, lat);
        values.put(LNG, lng);
        if (db.insert(TABLE, null, values) != -1) {
            //Toast.makeText(mContext, " Thêm thành công!", Toast.LENGTH_SHORT).show();
            Log.e("THem thanh cong", "Them du lieu thanh cong");
        } else {
            //   Toast.makeText(mContext, "Thêm thất bại!", Toast.LENGTH_SHORT).show();
            Log.e("THem that bai ", "Them du lieu that bai");
        }
        db.close();
    }

    public ArrayList<InformationsPlace> LayDuLieu() {
        ArrayList<InformationsPlace> list = new ArrayList<InformationsPlace>();
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "select * from " + TABLE;

        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String name = cursor.getString(0);
            String address = cursor.getString(1);
            String distance = cursor.getString(2);
            String lat = cursor.getString(3);
            String lng = cursor.getString(4);
            String type = cursor.getString(5);
            InformationsPlace info = new InformationsPlace();
            info.setName(name);
            info.setAddress(address);
            info.setDistance(distance);
            info.setLat(lat);
            info.setLng(lng);
            try {
                info.setType(type);
            } catch (Exception e) {
                Log.e("Loi khong co kieu du lieu", e.toString());
            }
            list.add(info);

            cursor.moveToNext();
        }
        Log.e("Lay du lieu thanh cong", "dfjsdfsd");
        return list;
    }

    public void deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE, null, null);
        Toast.makeText(mContext, "Đã xóa tất cả", Toast.LENGTH_SHORT).show();
        db.close();
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) mContext.getSystemService(mContext.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
