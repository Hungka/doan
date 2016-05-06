package sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import utils.InformationsPlace;

/**
 * Created by TranManhHung on 04-May-16.
 */
public class SQLRoute extends SQLiteOpenHelper {
    Context mContext;

    final String TABLE = "direct";
    final String CAR = "car";
    final String WALK = "walk";
    final String BUS = "bus";
    final String NAME = "name";

    public SQLRoute(Context context) {
        super(context, "direct", null, 1);
        mContext = context;
    }

    public SQLRoute(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE direct (  name TEXT Primary key, car TEXT , bus TEXT , walk TEXT )";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists direct");
    }

    public void ThemDuLieu(String name, String car, String bus, String walk) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME, name);
        values.put(CAR, car);
        values.put(BUS, bus);
        values.put(WALK, walk);

        if (db.insert(TABLE, null, values) != -1) {
            //Toast.makeText(mContext, " Thêm thành công!", Toast.LENGTH_SHORT).show();
            Log.e("Them thanh cong", "Them du lieu thanh cong");
        } else {
            //   Toast.makeText(mContext, "Thêm thất bại!", Toast.LENGTH_SHORT).show();
            Log.e("Them that bai ", "Them du lieu that bai");
        }
        db.close();
    }

    public ThreeRoute LayDuLieu() {
        ThreeRoute threeRoute = new ThreeRoute();
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "select * from " + TABLE;

        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String name = cursor.getString(0);
            String car = cursor.getString(1);
            String bus = cursor.getString(2);
            String walk = cursor.getString(3);

            threeRoute.setName(name);
            threeRoute.setBus(bus);
            threeRoute.setCar(car);
            threeRoute.setWalk(walk);

            cursor.moveToNext();
        }
        Log.e("Lay du lieu thanh cong", "dfjsdfsd");
        return threeRoute;
    }
}